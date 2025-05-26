package com.pruebaItx.domain.exception;

import java.time.LocalDateTime;
import lombok.Getter;

/** Domain exception thrown when no price is found for the given criteria. */
@Getter
public class PriceNotFoundException extends RuntimeException {

  private final Long brandId;
  private final Long productId;
  private final LocalDateTime applicationDate;

  public PriceNotFoundException(Long brandId, Long productId, LocalDateTime applicationDate) {
    super(
        String.format(
            "No price found for brand %d, product %d at date %s",
            brandId, productId, applicationDate));
    this.brandId = brandId;
    this.productId = productId;
    this.applicationDate = applicationDate;
  }
}
