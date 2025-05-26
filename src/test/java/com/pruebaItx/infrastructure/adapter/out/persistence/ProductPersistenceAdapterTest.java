package com.pruebaItx.infrastructure.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.infrastructure.adapter.out.persistence.mapper.ProductPersistenceMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for the ProductPersistenceAdapter. */
@ExtendWith(MockitoExtension.class)
public class ProductPersistenceAdapterTest {

  @Mock private ProductJpaRepository productRepository;

  @Mock private ProductPersistenceMapper productMapper;

  @InjectMocks private ProductPersistenceAdapter productPersistenceAdapter;

  private List<ProductJpaEntity> jpaEntities;
  private List<Product> domainProducts;

  @BeforeEach
  public void setup() {
    // Create JPA entities
    jpaEntities =
        Arrays.asList(
            createJpaEntity(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
            createJpaEntity(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9));

    // Create domain products
    domainProducts =
        Arrays.asList(
            createDomainProduct(1, "V-NECH BASIC SHIRT", 100, 4, 9, 0),
            createDomainProduct(2, "CONTRASTING FABRIC T-SHIRT", 50, 35, 9, 9));
  }

  @Test
  public void testLoadAllProducts() {
    // Given
    when(productRepository.findAll()).thenReturn(jpaEntities);
    when(productMapper.toDomainEntity(jpaEntities.get(0))).thenReturn(domainProducts.get(0));
    when(productMapper.toDomainEntity(jpaEntities.get(1))).thenReturn(domainProducts.get(1));

    // When
    List<Product> result = productPersistenceAdapter.loadAllProducts();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(domainProducts, result);
    verify(productRepository, times(1)).findAll();
    verify(productMapper, times(1)).toDomainEntity(jpaEntities.get(0));
    verify(productMapper, times(1)).toDomainEntity(jpaEntities.get(1));
  }

  @Test
  public void testLoadAllProducts_EmptyList() {
    // Given
    when(productRepository.findAll()).thenReturn(Collections.emptyList());

    // When
    List<Product> result = productPersistenceAdapter.loadAllProducts();

    // Then
    assertNotNull(result);
    assertEquals(0, result.size());
    verify(productRepository, times(1)).findAll();
    verify(productMapper, never()).toDomainEntity(any());
  }

  // Helper method to create a JPA entity
  private ProductJpaEntity createJpaEntity(
      int id, String name, int salesUnit, int sizeS, int sizeM, int sizeL) {
    ProductJpaEntity entity = new ProductJpaEntity();
    entity.setProduct_id(id);
    entity.setProduct_name(name);
    entity.setSales_unit(salesUnit);
    entity.setSize_S_stock(sizeS);
    entity.setSize_M_stock(sizeM);
    entity.setSize_L_stock(sizeL);
    return entity;
  }

  // Helper method to create a domain Product
  private Product createDomainProduct(
      int id, String name, int salesUnits, int sizeS, int sizeM, int sizeL) {
    Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

    return Product.builder().id(id).name(name).salesUnits(salesUnits).stock(stock).build();
  }
}
