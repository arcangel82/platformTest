package com.pruebaItx.application.service;

import com.pruebaItx.application.port.out.LoadProductsPort;
import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.Stock;
import com.pruebaItx.domain.service.ProductSortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProductService application service.
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private LoadProductsPort loadProductsPort;

    @Mock
    private ProductSortingService productSortingService;

    @InjectMocks
    private ProductService productService;

    private List<Product> testProducts;
    private List<Product> sortedProducts;

    @BeforeEach
    public void setup() {
        // Create test products
        testProducts =
                Arrays.asList(
                        createProduct(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
                        createProduct(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9));

        // Create sorted products (different order)
        sortedProducts =
                Arrays.asList(
                        createProduct(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9),
                        createProduct(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0));
    }

    @Test
    public void testRetrieveSortedProducts() {
        // Given
        Integer saleFactor = 1;
        Integer stockFactor = 2;

        when(loadProductsPort.loadAllProducts()).thenReturn(testProducts);
        when(productSortingService.sortProducts(testProducts, saleFactor, stockFactor))
                .thenReturn(sortedProducts);

        // When
        List<Product> result = productService.retrieveSortedProducts(saleFactor, stockFactor);

        // Then
        assertEquals(sortedProducts, result);
        verify(loadProductsPort, times(1)).loadAllProducts();
        verify(productSortingService, times(1)).sortProducts(testProducts, saleFactor, stockFactor);
    }

    // Helper method to create a Product
    private Product createProduct(
            int id, String name, int salesUnits, int sizeS, int sizeM, int sizeL) {
        Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

        return Product.builder().id(id).name(name).salesUnits(salesUnits).stock(stock).build();
    }
}
