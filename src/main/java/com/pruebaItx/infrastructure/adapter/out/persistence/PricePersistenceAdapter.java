package com.pruebaItx.infrastructure.adapter.out.persistence;

import com.pruebaItx.application.port.out.PriceRepositoryPort;
import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.out.persistence.mapper.PricePersistenceMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Adapter for loading prices from the database. Implements the PriceRepositoryPort interface. */
@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepositoryPort {

  private final PriceJpaRepository priceJpaRepository;
  private final PricePersistenceMapper pricePersistenceMapper;

  /** {@inheritDoc} */
  @Override
  public List<Price> findApplicablePrices(
      Long brandId, Long productId, LocalDateTime applicationDate) {
    return priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate).stream()
        .map(pricePersistenceMapper::toDomainEntity)
        .collect(Collectors.toList());
  }
}
