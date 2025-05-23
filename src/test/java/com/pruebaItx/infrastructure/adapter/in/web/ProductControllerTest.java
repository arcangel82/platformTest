package com.pruebaItx.infrastructure.adapter.in.web;

import com.pruebaItx.application.port.in.RetrieveSortedProductsUseCase;
import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.SortingCriteriaType;
import com.pruebaItx.domain.model.Stock;
import com.pruebaItx.infrastructure.adapter.in.web.mapper.ProductWebMapper;
import com.pruebaItx.web.application.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProductController web adapter.
 */
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private RetrieveSortedProductsUseCase retrieveSortedProductsUseCase;

    @Mock
    private ProductWebMapper productWebMapper;

    @InjectMocks
    private ProductController productController;

    private List<Product> domainProducts;
    private List<Article> dtoArticles;

    @BeforeEach
    public void setup() {
        // Create domain products
        domainProducts =
                Arrays.asList(
                        createDomainProduct(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
                        createDomainProduct(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9));

        // Create DTO articles
        dtoArticles =
                Arrays.asList(
                        createDtoArticle(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
                        createDtoArticle(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9));
    }

    @Test
    public void testProductsSorted() {
        // Given
        Integer saleFactor = 1;
        Integer stockFactor = 2;

        when(retrieveSortedProductsUseCase.retrieveSortedProducts(saleFactor, stockFactor))
                .thenReturn(domainProducts);
        when(productWebMapper.toDto(domainProducts.get(0))).thenReturn(dtoArticles.get(0));
        when(productWebMapper.toDto(domainProducts.get(1))).thenReturn(dtoArticles.get(1));

        // When
        ResponseEntity<ProductsSorted200Response> response =
                productController.productsSorted(saleFactor, stockFactor);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());
        assertEquals(dtoArticles, response.getBody().getData());

        verify(retrieveSortedProductsUseCase, times(1)).retrieveSortedProducts(saleFactor, stockFactor);
        verify(productWebMapper, times(1)).toDto(domainProducts.get(0));
        verify(productWebMapper, times(1)).toDto(domainProducts.get(1));
    }

    @Test
    public void testProductsSorted_ZeroFactors() {
        // Given
        Integer saleFactor = 0;
        Integer stockFactor = 0;

        when(retrieveSortedProductsUseCase.retrieveSortedProducts(saleFactor, stockFactor))
                .thenReturn(domainProducts);
        when(productWebMapper.toDto(domainProducts.get(0))).thenReturn(dtoArticles.get(0));
        when(productWebMapper.toDto(domainProducts.get(1))).thenReturn(dtoArticles.get(1));

        // When
        ResponseEntity<ProductsSorted200Response> response =
                productController.productsSorted(saleFactor, stockFactor);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());

        verify(retrieveSortedProductsUseCase, times(1)).retrieveSortedProducts(saleFactor, stockFactor);
    }

    @Test
    public void testProductsSorted_NegativeFactors() {
        // Given
        Integer saleFactor = -1;
        Integer stockFactor = -2;

        when(retrieveSortedProductsUseCase.retrieveSortedProducts(saleFactor, stockFactor))
                .thenReturn(domainProducts);
        when(productWebMapper.toDto(domainProducts.get(0))).thenReturn(dtoArticles.get(0));
        when(productWebMapper.toDto(domainProducts.get(1))).thenReturn(dtoArticles.get(1));

        // When
        ResponseEntity<ProductsSorted200Response> response =
                productController.productsSorted(saleFactor, stockFactor);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());

        verify(retrieveSortedProductsUseCase, times(1)).retrieveSortedProducts(saleFactor, stockFactor);
    }

    @Test
    public void testProductsSorted_EmptyList() {
        // Given
        Integer saleFactor = 1;
        Integer stockFactor = 1;

        when(retrieveSortedProductsUseCase.retrieveSortedProducts(saleFactor, stockFactor))
                .thenReturn(Collections.emptyList());

        // When
        ResponseEntity<ProductsSorted200Response> response =
                productController.productsSorted(saleFactor, stockFactor);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getData().size());

        verify(retrieveSortedProductsUseCase, times(1)).retrieveSortedProducts(saleFactor, stockFactor);
        verify(productWebMapper, never()).toDto(any());
    }

    @Test
    public void testProductsSortedWithBody() {
        // Given
        SortingCriteriaRequest request = new SortingCriteriaRequest();
        List<CriteriaWeight> criteria = Arrays.asList(new CriteriaWeight("salesUnits", 1));
        request.setCriteria(criteria);

        Map<SortingCriteriaType, Integer> expectedCriteriaWeights = new HashMap<>();
        expectedCriteriaWeights.put(SortingCriteriaType.SALE_FACTOR, 1);

        when(retrieveSortedProductsUseCase.retrieveSortedProducts(expectedCriteriaWeights))
                .thenReturn(domainProducts);
        when(productWebMapper.toDto(domainProducts.get(0))).thenReturn(dtoArticles.get(0));
        when(productWebMapper.toDto(domainProducts.get(1))).thenReturn(dtoArticles.get(1));

        // When
        ResponseEntity<ProductsSorted200Response> response =
                productController.productsSortedWithBody(request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());
        assertEquals(dtoArticles, response.getBody().getData());

        verify(retrieveSortedProductsUseCase, times(1)).retrieveSortedProducts(expectedCriteriaWeights);
        verify(productWebMapper, times(1)).toDto(domainProducts.get(0));
        verify(productWebMapper, times(1)).toDto(domainProducts.get(1));
    }

    // Helper method to create a domain Product
    private Product createDomainProduct(
            int id, String name, int salesUnits, int sizeS, int sizeM, int sizeL) {
        Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

        return Product.builder().id(id).name(name).salesUnits(salesUnits).stock(stock).build();
    }

    // Helper method to create a DTO Article
    private Article createDtoArticle(
            int id, String name, int sales, int sizeS, int sizeM, int sizeL) {
        Article article = new Article();
        article.setId(id);
        article.setName(name);
        article.setSales(sales);

        ArticleStock stock = new ArticleStock();
        stock.setSizeS(sizeS);
        stock.setSizeM(sizeM);
        stock.setSizeL(sizeL);

        article.setStock(stock);
        return article;
    }
}
