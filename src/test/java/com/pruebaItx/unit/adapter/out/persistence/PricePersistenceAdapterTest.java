package com.pruebaItx.unit.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.out.persistence.PriceJpaRepository;
import com.pruebaItx.infrastructure.adapter.out.persistence.PricePersistenceAdapter;
import com.pruebaItx.infrastructure.adapter.out.persistence.entity.PriceEntity;
import com.pruebaItx.infrastructure.adapter.out.persistence.mapper.PricePersistenceMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PricePersistenceAdapter Tests")
class PricePersistenceAdapterTest {

  @Mock private PriceJpaRepository priceJpaRepository;

  @Mock private PricePersistenceMapper pricePersistenceMapper;

  @InjectMocks private PricePersistenceAdapter pricePersistenceAdapter;

  private Long brandId;
  private Long productId;
  private LocalDateTime applicationDate;
  private PriceEntity priceEntity1;
  private PriceEntity priceEntity2;
  private Price domainPrice1;
  private Price domainPrice2;

  @BeforeEach
  void setUp() {
    brandId = 1L;
    productId = 35455L;
    applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

    // Setup test entities
    priceEntity1 = createPriceEntity(1L, 0, new BigDecimal("35.50"));
    priceEntity2 = createPriceEntity(2L, 1, new BigDecimal("25.45"));

    // Setup test domain objects
    domainPrice1 = createDomainPrice(0, new BigDecimal("35.50"));
    domainPrice2 = createDomainPrice(1, new BigDecimal("25.45"));
  }

  @Test
  @DisplayName("Should find applicable prices successfully")
  void shouldFindApplicablePricesSuccessfully() {
    // Given
    List<PriceEntity> entities = Arrays.asList(priceEntity1, priceEntity2);
    when(priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate))
        .thenReturn(entities);
    when(pricePersistenceMapper.toDomainEntity(priceEntity1)).thenReturn(domainPrice1);
    when(pricePersistenceMapper.toDomainEntity(priceEntity2)).thenReturn(domainPrice2);

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, productId, applicationDate);

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(domainPrice1, domainPrice2);

    verify(priceJpaRepository).findApplicablePrices(brandId, productId, applicationDate);
    verify(pricePersistenceMapper).toDomainEntity(priceEntity1);
    verify(pricePersistenceMapper).toDomainEntity(priceEntity2);
  }

  @Test
  @DisplayName("Should return empty list when no prices found")
  void shouldReturnEmptyListWhenNoPricesFound() {
    // Given
    when(priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate))
        .thenReturn(Collections.emptyList());

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, productId, applicationDate);

    // Then
    assertThat(result).isEmpty();

    verify(priceJpaRepository).findApplicablePrices(brandId, productId, applicationDate);
    verifyNoInteractions(pricePersistenceMapper);
  }

  @Test
  @DisplayName("Should find single applicable price")
  void shouldFindSingleApplicablePrice() {
    // Given
    List<PriceEntity> entities = Collections.singletonList(priceEntity1);
    when(priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate))
        .thenReturn(entities);
    when(pricePersistenceMapper.toDomainEntity(priceEntity1)).thenReturn(domainPrice1);

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, productId, applicationDate);

    // Then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(domainPrice1);

    verify(priceJpaRepository).findApplicablePrices(brandId, productId, applicationDate);
    verify(pricePersistenceMapper).toDomainEntity(priceEntity1);
  }

  @Test
  @DisplayName("Should handle different brand IDs")
  void shouldHandleDifferentBrandIds() {
    // Given
    Long differentBrandId = 2L;
    List<PriceEntity> entities = Collections.singletonList(priceEntity1);
    when(priceJpaRepository.findApplicablePrices(differentBrandId, productId, applicationDate))
        .thenReturn(entities);
    when(pricePersistenceMapper.toDomainEntity(priceEntity1)).thenReturn(domainPrice1);

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(differentBrandId, productId, applicationDate);

    // Then
    assertThat(result).hasSize(1);
    verify(priceJpaRepository).findApplicablePrices(differentBrandId, productId, applicationDate);
  }

  @Test
  @DisplayName("Should handle different product IDs")
  void shouldHandleDifferentProductIds() {
    // Given
    Long differentProductId = 99999L;
    List<PriceEntity> entities = Collections.singletonList(priceEntity1);
    when(priceJpaRepository.findApplicablePrices(brandId, differentProductId, applicationDate))
        .thenReturn(entities);
    when(pricePersistenceMapper.toDomainEntity(priceEntity1)).thenReturn(domainPrice1);

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, differentProductId, applicationDate);

    // Then
    assertThat(result).hasSize(1);
    verify(priceJpaRepository).findApplicablePrices(brandId, differentProductId, applicationDate);
  }

  @Test
  @DisplayName("Should handle different application dates")
  void shouldHandleDifferentApplicationDates() {
    // Given
    LocalDateTime differentDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
    List<PriceEntity> entities = Collections.singletonList(priceEntity1);
    when(priceJpaRepository.findApplicablePrices(brandId, productId, differentDate))
        .thenReturn(entities);
    when(pricePersistenceMapper.toDomainEntity(priceEntity1)).thenReturn(domainPrice1);

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, productId, differentDate);

    // Then
    assertThat(result).hasSize(1);
    verify(priceJpaRepository).findApplicablePrices(brandId, productId, differentDate);
  }

  @Test
  @DisplayName("Should preserve order from repository")
  void shouldPreserveOrderFromRepository() {
    // Given - Repository returns prices in priority order (DESC)
    List<PriceEntity> entitiesInOrder =
        Arrays.asList(priceEntity2, priceEntity1); // Higher priority first
    when(priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate))
        .thenReturn(entitiesInOrder);
    when(pricePersistenceMapper.toDomainEntity(priceEntity2)).thenReturn(domainPrice2);
    when(pricePersistenceMapper.toDomainEntity(priceEntity1)).thenReturn(domainPrice1);

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, productId, applicationDate);

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(domainPrice2, domainPrice1); // Same order as repository
  }

  @Test
  @DisplayName("Should call mapper for each entity")
  void shouldCallMapperForEachEntity() {
    // Given
    List<PriceEntity> entities = Arrays.asList(priceEntity1, priceEntity2);
    when(priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate))
        .thenReturn(entities);
    when(pricePersistenceMapper.toDomainEntity(any(PriceEntity.class)))
        .thenReturn(domainPrice1, domainPrice2);

    // When
    pricePersistenceAdapter.findApplicablePrices(brandId, productId, applicationDate);

    // Then
    verify(pricePersistenceMapper, times(2)).toDomainEntity(any(PriceEntity.class));
    verify(pricePersistenceMapper).toDomainEntity(priceEntity1);
    verify(pricePersistenceMapper).toDomainEntity(priceEntity2);
  }

  @Test
  @DisplayName("Should handle null parameters gracefully")
  void shouldHandleNullParametersGracefully() {
    // Given
    when(priceJpaRepository.findApplicablePrices(null, null, null))
        .thenReturn(Collections.emptyList());

    // When
    List<Price> result = pricePersistenceAdapter.findApplicablePrices(null, null, null);

    // Then
    assertThat(result).isEmpty();
    verify(priceJpaRepository).findApplicablePrices(null, null, null);
  }

  @Test
  @DisplayName("Should handle large number of results")
  void shouldHandleLargeNumberOfResults() {
    // Given
    int numberOfEntities = 100;
    List<PriceEntity> manyEntities = createManyPriceEntities(numberOfEntities);
    List<Price> manyDomainPrices = createManyDomainPrices(numberOfEntities);

    when(priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate))
        .thenReturn(manyEntities);

    for (int i = 0; i < numberOfEntities; i++) {
      when(pricePersistenceMapper.toDomainEntity(manyEntities.get(i)))
          .thenReturn(manyDomainPrices.get(i));
    }

    // When
    List<Price> result =
        pricePersistenceAdapter.findApplicablePrices(brandId, productId, applicationDate);

    // Then
    assertThat(result).hasSize(numberOfEntities);
    verify(pricePersistenceMapper, times(numberOfEntities)).toDomainEntity(any(PriceEntity.class));
  }

  // Helper methods

  private PriceEntity createPriceEntity(Long id, Integer priority, BigDecimal price) {
    PriceEntity entity = new PriceEntity();
    entity.setId(id);
    entity.setBrandId(brandId);
    entity.setProductId(productId);
    entity.setPriceList(1);
    entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
    entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
    entity.setPriority(priority);
    entity.setPrice(price);
    entity.setCurrency("EUR");
    return entity;
  }

  private Price createDomainPrice(Integer priority, BigDecimal price) {
    return Price.builder()
        .brandId(brandId)
        .productId(productId)
        .priceList(1)
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(priority)
        .price(price)
        .currency("EUR")
        .build();
  }

  private List<PriceEntity> createManyPriceEntities(int count) {
    return java.util.stream.IntStream.range(0, count)
        .mapToObj(
            i -> createPriceEntity((long) i, i, new BigDecimal("10.00").add(new BigDecimal(i))))
        .collect(java.util.stream.Collectors.toList());
  }

  private List<Price> createManyDomainPrices(int count) {
    return java.util.stream.IntStream.range(0, count)
        .mapToObj(i -> createDomainPrice(i, new BigDecimal("10.00").add(new BigDecimal(i))))
        .collect(java.util.stream.Collectors.toList());
  }
}
