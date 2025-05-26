package com.pruebaItx.infrastructure.adapter.in.web;

import com.pruebaItx.application.port.in.GetPriceUseCase;
import com.pruebaItx.domain.exception.PriceNotFoundException;
import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.in.web.mapper.PriceWebMapper;
import com.pruebaItx.web.application.entities.PriceResponse;
import com.pruebaItx.web.application.interfaces.PriceApi;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web adapter for the price API. Implements the PriceApi interface generated from the OpenAPI
 * specification.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class PriceController implements PriceApi {

  private final GetPriceUseCase getPriceUseCase; // Renamed to match the provided interface
  private final PriceWebMapper priceWebMapper;

  /**
   * Endpoint to query product prices based on application date, product ID, and brand ID.
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<PriceResponse> getPrice(
      LocalDateTime applicationDate, Long productId, Long brandId) {
    log.debug(
        "Request to getProductPrice: applicationDate={}, productId={}, brandId={}",
        applicationDate,
        productId,
        brandId);

    try {
      Price price = getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate);

      return ResponseEntity.ok(priceWebMapper.toDto(price));
    } catch (PriceNotFoundException e) {
      log.info(
          "No price found for applicationDate={}, productId={}, brandId={}. Reason: {}",
          applicationDate,
          productId,
          brandId,
          e.getMessage());

      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      log.error("An unexpected error occurred while fetching price: {}", e.getMessage(), e);

      return ResponseEntity.internalServerError().build();
    }
  }
}
