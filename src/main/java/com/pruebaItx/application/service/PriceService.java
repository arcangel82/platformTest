package com.pruebaItx.application.service;

import com.pruebaItx.application.port.in.GetPriceUseCase;
import com.pruebaItx.application.port.out.PriceRepositoryPort;
import com.pruebaItx.domain.exception.PriceNotFoundException;
import com.pruebaItx.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service implementing the price retrieval use case. Contains the business logic for selecting the
 * appropriate price.
 */
@Service
public class PriceService implements GetPriceUseCase {

  private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

  private final PriceRepositoryPort priceRepositoryPort;

  public PriceService(PriceRepositoryPort priceRepositoryPort) {
    this.priceRepositoryPort = priceRepositoryPort;
  }

  @Override
  public Price getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
    logger.debug(
        "Getting applicable price for brand: {}, product: {}, date: {}",
        brandId,
        productId,
        applicationDate);

    List<Price> applicablePrices =
        priceRepositoryPort.findApplicablePrices(brandId, productId, applicationDate);

    if (applicablePrices.isEmpty()) {
      logger.warn(
          "No price found for brand: {}, product: {}, date: {}",
          brandId,
          productId,
          applicationDate);
      throw new PriceNotFoundException(brandId, productId, applicationDate);
    }

    // Select price with highest priority using domain logic
    Price selectedPrice =
        applicablePrices.stream()
            .max(Comparator.comparing(Price::getPriority))
            .orElseThrow(() -> new PriceNotFoundException(brandId, productId, applicationDate));

    logger.debug(
        "Selected price with priority {} and value {}",
        selectedPrice.getPriority(),
        selectedPrice.getPrice());

    return selectedPrice;
  }
}
