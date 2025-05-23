package com.pruebaItx.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the Product domain entity.
 */
public class ProductTest {

    @Test
    public void testCountSizesWithStock_AllSizesHaveStock() {
        // Given
        Product product = createProduct(1, "Test Product", 100, 10, 20, 30);

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(3, count);
    }

    @Test
    public void testCountSizesWithStock_TwoSizesHaveStock() {
        // Given
        Product product = createProduct(1, "Test Product", 100, 10, 20, 0);

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(2, count);
    }

    @Test
    public void testCountSizesWithStock_OneSizeHasStock() {
        // Given
        Product product = createProduct(1, "Test Product", 100, 10, 0, 0);

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(1, count);
    }

    @Test
    public void testCountSizesWithStock_NoSizesHaveStock() {
        // Given
        Product product = createProduct(1, "Test Product", 100, 0, 0, 0);

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(0, count);
    }

    @Test
    public void testCountSizesWithStock_NegativeStockValues() {
        // Given
        Product product = createProduct(1, "Test Product", 100, -10, -20, -30);

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(0, count);
    }

    @Test
    public void testCountSizesWithStock_MixedStockValues() {
        // Given
        Product product = createProduct(1, "Test Product", 100, 10, 0, -30);

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(1, count);
    }

    @Test
    public void testCountSizesWithStock_NullStockValues() {
        // Given
        Stock stock = new Stock();
        Product product =
                Product.builder().id(1).name("Test Product").salesUnits(100).stock(stock).build();

        // When
        int count = product.countSizesWithStock();

        // Then
        assertEquals(0, count);
    }

    @Test
    public void testBuilder() {
        // Given
        Stock stock = Stock.builder().sizeS(10).sizeM(20).sizeL(30).build();

        // When
        Product product =
                Product.builder().id(1).name("Test Product").salesUnits(100).stock(stock).build();

        // Then
        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(100, product.getSalesUnits());
        assertNotNull(product.getStock());
        assertEquals(10, product.getStock().getSizeS());
        assertEquals(20, product.getStock().getSizeM());
        assertEquals(30, product.getStock().getSizeL());
    }

    // Helper method to create a Product
    private Product createProduct(
            int id, String name, int salesUnits, int sizeS, int sizeM, int sizeL) {
        Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

        return Product.builder().id(id).name(name).salesUnits(salesUnits).stock(stock).build();
    }
}
