package com.pruebaItx.infrastructure.adapter.out.persistence;

import com.pruebaItx.application.port.out.PriceRepositoryPort;
import com.pruebaItx.domain.exception.PriceNotFoundException;
import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.out.persistence.mapper.PricePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** Adapter for loading prices from the database. Implements the PriceRepositoryPort interface. */
@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter  implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;
    private final PricePersistenceMapper pricePersistenceMapper;

    /** {@inheritDoc} */
    @Override
    public Price getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceJpaRepository.findApplicablePrices(
                brandId, productId, applicationDate).stream()
                .findFirst()
                .map(pricePersistenceMapper::toDomainEntity)
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId, applicationDate));

    }

}
