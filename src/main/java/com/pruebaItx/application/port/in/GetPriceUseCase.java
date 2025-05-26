package com.pruebaItx.application.port.in;

import com.pruebaItx.domain.model.Price;
import java.time.LocalDateTime;

/**
 * Use case for retrieving the applicable price for a product. This interface defines the business
 * operation from the application's perspective.
 */
public interface GetPriceUseCase {

  /**
   * Retrieves the applicable price for the given parameters.
   *
   * @param brandId Brand identifier
   * @param productId Product identifier
   * @param applicationDate Date when the price should be applied
   * @return The applicable price with the highest priority
   * @throws com.inditex.platformtest.domain.exception.PriceNotFoundException if no price is found
   */
  Price getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
