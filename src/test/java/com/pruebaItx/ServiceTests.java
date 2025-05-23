package com.pruebaItx;

import com.pruebaItx.application.port.in.RetrieveSortedProductsUseCase;
import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.Stock;
import com.pruebaItx.infrastructure.adapter.in.web.ProductController;
import com.pruebaItx.infrastructure.adapter.in.web.mapper.ProductWebMapper;
import com.pruebaItx.web.application.entities.Article;
import com.pruebaItx.web.application.entities.ProductsSorted200Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Tests for the ProductService implementation
 */
public class ServiceTests {

    @Mock
    private RetrieveSortedProductsUseCase retrieveSortedProductsUseCase;

    @Mock
    private ProductWebMapper productWebMapper;

    @InjectMocks
    private ProductController productController;

    private List<Product> mockDomainProducts;
    private List<Article> mockArticles;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create mock domain products
        mockDomainProducts =
                Arrays.asList(
                        createDomainProduct(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
                        createDomainProduct(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9),
                        createDomainProduct(3, "RAISED PRINT T-SHIRT", 80, 20, 2, 20),
                        createDomainProduct(4, "PLEATED T-SHIRT", 3, 25, 30, 10),
                        createDomainProduct(5, "CONTRASTING LACE T-SHIRT", 650, 0, 1, 0),
                        createDomainProduct(6, "SLOGAN T-SHIRT", 20, 9, 2, 5));

        // Create mock articles (DTOs)
        mockArticles =
                Arrays.asList(
                        createArticle(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
                        createArticle(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9),
                        createArticle(3, "RAISED PRINT T-SHIRT", 80, 20, 2, 20),
                        createArticle(4, "PLEATED T-SHIRT", 3, 25, 30, 10),
                        createArticle(5, "CONTRASTING LACE T-SHIRT", 650, 0, 1, 0),
                        createArticle(6, "SLOGAN T-SHIRT", 20, 9, 2, 5));
    }

    @AfterEach
    public void cleanup() {
        // Reset mocks to ensure no shared state between tests
        reset(retrieveSortedProductsUseCase, productWebMapper);
    }

    @Test
    public void testRetrieveProductsSorted_SalesFactorOnly() {
        // Setup sorted products for sales factor only (weight 1 for sales, 0 for stock)
        List<Product> sortedDomainProducts =
                Arrays.asList(
                        mockDomainProducts.get(4), // ID 5 (650 sales)
                        mockDomainProducts.get(0), // ID 1 (100 sales)
                        mockDomainProducts.get(2), // ID 3 (80 sales)
                        mockDomainProducts.get(1), // ID 2 (50 sales)
                        mockDomainProducts.get(5), // ID 6 (20 sales)
                        mockDomainProducts.get(3) // ID 4 (3 sales)
                );

        // Setup use case mock
        when(retrieveSortedProductsUseCase.retrieveSortedProducts(1, 0))
                .thenReturn(sortedDomainProducts);

        // Setup mapper mocks
        for (int i = 0; i < sortedDomainProducts.size(); i++) {
            when(productWebMapper.toDto(sortedDomainProducts.get(i)))
                    .thenReturn(mockArticles.get(sortedDomainProducts.get(i).getId() - 1));
        }

        // Call controller
        ResponseEntity<ProductsSorted200Response> response = productController.productsSorted(1, 0);

        List<Article> sortedArticles = response.getBody().getData();

        // Expected order based on sales units (highest to lowest):
        // 5 (650), 1 (100), 3 (80), 2 (50), 6 (20), 4 (3)
        assertEquals(6, sortedArticles.size());
        assertEquals(5, sortedArticles.get(0).getId());
        assertEquals(1, sortedArticles.get(1).getId());
        assertEquals(3, sortedArticles.get(2).getId());
        assertEquals(2, sortedArticles.get(3).getId());
        assertEquals(6, sortedArticles.get(4).getId());
        assertEquals(4, sortedArticles.get(5).getId());
    }

    @Test
    public void testRetrieveProductsSorted_StockFactorOnly() {
        // Products with 3 sizes with stock: IDs 2, 3, 4, 6
        // Products with 2 sizes with stock: ID 1
        // Products with 1 size with stock: ID 5
        List<Product> sortedDomainProducts =
                Arrays.asList(
                        mockDomainProducts.get(1), // ID 2 (3 sizes)
                        mockDomainProducts.get(2), // ID 3 (3 sizes)
                        mockDomainProducts.get(3), // ID 4 (3 sizes)
                        mockDomainProducts.get(5), // ID 6 (3 sizes)
                        mockDomainProducts.get(0), // ID 1 (2 sizes)
                        mockDomainProducts.get(4) // ID 5 (1 size)
                );

        // Setup use case mock
        when(retrieveSortedProductsUseCase.retrieveSortedProducts(0, 1))
                .thenReturn(sortedDomainProducts);

        // Setup mapper mocks
        for (int i = 0; i < sortedDomainProducts.size(); i++) {
            when(productWebMapper.toDto(sortedDomainProducts.get(i)))
                    .thenReturn(mockArticles.get(sortedDomainProducts.get(i).getId() - 1));
        }

        // Call controller
        ResponseEntity<ProductsSorted200Response> response = productController.productsSorted(0, 1);

        List<Article> sortedArticles = response.getBody().getData();

        // Expected order based on stock ratio (sizes with stock, highest to lowest):
        // 2 (3 sizes), 3 (3 sizes), 4 (3 sizes), 6 (3 sizes), 1 (2 sizes), 5 (1 size)
        assertEquals(6, sortedArticles.size());

        // Check that products with 3 sizes with stock come first
        assertEquals(3, countSizesWithStock(sortedArticles.get(0)));
        assertEquals(3, countSizesWithStock(sortedArticles.get(1)));
        assertEquals(3, countSizesWithStock(sortedArticles.get(2)));
        assertEquals(3, countSizesWithStock(sortedArticles.get(3)));

        // Check that product with 2 sizes with stock comes next
        assertEquals(2, countSizesWithStock(sortedArticles.get(4)));

        // Check that product with 1 size with stock comes last
        assertEquals(1, countSizesWithStock(sortedArticles.get(5)));
    }

    @Test
    public void testRetrieveProductsSorted_EqualWeights() {
        // Setup sorted products for equal weights
        List<Product> sortedDomainProducts =
                Arrays.asList(
                        mockDomainProducts.get(4), // ID 5 (650 sales + 1 size = 651)
                        mockDomainProducts.get(0), // ID 1 (100 sales + 2 sizes = 102)
                        mockDomainProducts.get(2), // ID 3 (80 sales + 3 sizes = 83)
                        mockDomainProducts.get(1), // ID 2 (50 sales + 3 sizes = 53)
                        mockDomainProducts.get(5), // ID 6 (20 sales + 3 sizes = 23)
                        mockDomainProducts.get(3) // ID 4 (3 sales + 3 sizes = 6)
                );

        // Setup use case mock
        when(retrieveSortedProductsUseCase.retrieveSortedProducts(1, 1))
                .thenReturn(sortedDomainProducts);

        // Setup mapper mocks
        for (int i = 0; i < sortedDomainProducts.size(); i++) {
            when(productWebMapper.toDto(sortedDomainProducts.get(i)))
                    .thenReturn(mockArticles.get(sortedDomainProducts.get(i).getId() - 1));
        }

        // Call controller
        ResponseEntity<ProductsSorted200Response> response = productController.productsSorted(1, 1);

        List<Article> sortedArticles = response.getBody().getData();

        // Expected order based on combined score (sales + stock ratio):
        // 5 (650 + 1 = 651), 1 (100 + 2 = 102), 3 (80 + 3 = 83), 2 (50 + 3 = 53), 6 (20 + 3 = 23), 4 (3
        // + 3 = 6)
        assertEquals(6, sortedArticles.size());
        assertEquals(5, sortedArticles.get(0).getId());
        assertEquals(1, sortedArticles.get(1).getId());
        assertEquals(3, sortedArticles.get(2).getId());
        assertEquals(2, sortedArticles.get(3).getId());
        assertEquals(6, sortedArticles.get(4).getId());
        assertEquals(4, sortedArticles.get(5).getId());
    }

    // Helper method to create a domain Product
    private Product createDomainProduct(
            int id, String name, int sales, int sizeS, int sizeM, int sizeL) {
        Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

        return Product.builder().id(id).name(name).salesUnits(sales).stock(stock).build();
    }

    // Helper method to create an Article (DTO)
    private Article createArticle(int id, String name, int sales, int sizeS, int sizeM, int sizeL) {
        Article article = new Article();
        article.setId(id);
        article.setName(name);
        article.setSales(sales);

        com.pruebaItx.web.application.entities.ArticleStock stock =
                new com.pruebaItx.web.application.entities.ArticleStock();
        stock.setSizeS(sizeS);
        stock.setSizeM(sizeM);
        stock.setSizeL(sizeL);

        article.setStock(stock);
        return article;
    }

    // Helper method to count sizes with stock
    private int countSizesWithStock(Article article) {
        int count = 0;
        if (article.getStock().getSizeS() > 0) count++;
        if (article.getStock().getSizeM() > 0) count++;
        if (article.getStock().getSizeL() > 0) count++;
        return count;
    }
}
