package com.pruebaItx.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pruebaItx.domain.model.Product;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for the ProductSortingService. */
public class ProductSortingServiceTest {

  private ProductSortingService productSortingService;
  private List<Product> testProducts;

  @BeforeEach
  public void setup() {
    productSortingService = new ProductSortingService();

    // Create test products
    testProducts =
        Arrays.asList(
            createProduct(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
            createProduct(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9),
            createProduct(3, "RAISED PRINT T-SHIRT", 80, 20, 2, 20),
            createProduct(4, "PLEATED T-SHIRT", 3, 25, 30, 10),
            createProduct(5, "CONTRASTING LACE T-SHIRT", 650, 0, 1, 0),
            createProduct(6, "SLOGAN T-SHIRT", 20, 9, 2, 5));
  }

  @Test
  public void testSortProducts_SalesFactorOnly() {
    // Test with only sales factor (weight 1 for sales, 0 for stock)
    List<Product> sortedProducts = productSortingService.sortProducts(testProducts, 1, 0);

    // Expected order based on sales units (highest to lowest):
    // 5 (650), 1 (100), 3 (80), 2 (50), 6 (20), 4 (3)
    assertEquals(6, sortedProducts.size());
    assertEquals(5, sortedProducts.get(0).getId());
    assertEquals(1, sortedProducts.get(1).getId());
    assertEquals(3, sortedProducts.get(2).getId());
    assertEquals(2, sortedProducts.get(3).getId());
    assertEquals(6, sortedProducts.get(4).getId());
    assertEquals(4, sortedProducts.get(5).getId());
  }

  @Test
  public void testSortProducts_StockFactorOnly() {
    // Test with only stock factor (weight 0 for sales, 1 for stock)
    List<Product> sortedProducts = productSortingService.sortProducts(testProducts, 0, 1);

    // Expected order based on stock ratio (sizes with stock, highest to lowest):
    // Products with 3 sizes with stock (2, 3, 4, 6), then 1 (2 sizes), then 5 (1 size)
    assertEquals(6, sortedProducts.size());

    // Check that products with 3 sizes with stock come first
    assertEquals(3, countSizesWithStock(sortedProducts.get(0)));
    assertEquals(3, countSizesWithStock(sortedProducts.get(1)));
    assertEquals(3, countSizesWithStock(sortedProducts.get(2)));
    assertEquals(3, countSizesWithStock(sortedProducts.get(3)));

    // Check that product with 2 sizes with stock comes next
    assertEquals(2, countSizesWithStock(sortedProducts.get(4)));

    // Check that product with 1 size with stock comes last
    assertEquals(1, countSizesWithStock(sortedProducts.get(5)));
  }

  @Test
  public void testSortProducts_EqualWeights() {
    // Test with equal weights for sales and stock factors
    List<Product> sortedProducts = productSortingService.sortProducts(testProducts, 1, 1);

    // Expected order based on combined score (sales + stock ratio)
    assertEquals(6, sortedProducts.size());
    assertEquals(5, sortedProducts.get(0).getId());
    assertEquals(1, sortedProducts.get(1).getId());
    assertEquals(3, sortedProducts.get(2).getId());
    assertEquals(2, sortedProducts.get(3).getId());
    assertEquals(6, sortedProducts.get(4).getId());
    assertEquals(4, sortedProducts.get(5).getId());
  }

  // Helper method to create a Product
  private Product createProduct(
      int id, String name, int salesUnits, int sizeS, int sizeM, int sizeL) {
    Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

    return Product.builder().id(id).name(name).salesUnits(salesUnits).stock(stock).build();
  }

  // Helper method to count sizes with stock
  private int countSizesWithStock(Product product) {
    return product.countSizesWithStock();
  }
}
