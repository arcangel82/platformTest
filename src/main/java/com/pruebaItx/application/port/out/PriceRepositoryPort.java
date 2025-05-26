package com.pruebaItx.application.port.out;

import com.pruebaItx.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Port for price repository operations. This interface abstracts the persistence layer from the
 * application layer.
 */
public interface PriceRepositoryPort {

  /**
   * Finds all prices applicable for the given criteria.
   *
   * @param brandId Brand identifier
   * @param productId Product identifier
   * @param applicationDate Date when the price should be applied
   * @return List of applicable prices, empty if none found
   */
  List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate);
}
