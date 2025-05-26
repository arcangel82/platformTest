package com.pruebaItx.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for price query responses. Contains the price information returned by the
 * price query service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Price information response")
public class PriceResponseDto {

  /** Product identifier. */
  @Schema(description = "Product identifier", example = "35455", required = true)
  private Long productId;

  /** Brand identifier (1 = ZARA). */
  @Schema(description = "Brand identifier (1 = ZARA)", example = "1", required = true)
  private Long brandId;

  /** Price list identifier. */
  @Schema(description = "Price list identifier", example = "1", required = true)
  private Integer priceList;

  /** Start date when the price becomes effective. */
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Schema(
      description = "Start date when the price becomes effective",
      example = "2020-06-14T00:00:00",
      required = true,
      format = "date-time")
  private LocalDateTime startDate;

  /** End date when the price expires. */
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Schema(
      description = "End date when the price expires",
      example = "2020-12-31T23:59:59",
      required = true,
      format = "date-time")
  private LocalDateTime endDate;

  /** Final price amount. */
  @Schema(description = "Final price amount", example = "35.50", required = true, minimum = "0")
  private BigDecimal price;

  /** Currency code in ISO 4217 format. */
  @Schema(
      description = "Currency code in ISO 4217 format",
      example = "EUR",
      required = true,
      pattern = "^[A-Z]{3}$")
  private String currency;

  /**
   * Creates a PriceResponseDto with the specified parameters.
   *
   * @param productId the product identifier
   * @param brandId the brand identifier
   * @param priceList the price list identifier
   * @param startDate the start date when the price becomes effective
   * @param endDate the end date when the price expires
   * @param price the final price amount
   * @param currency the currency code
   * @return a new PriceResponseDto instance
   */
  public static PriceResponseDto of(
      Long productId,
      Long brandId,
      Integer priceList,
      LocalDateTime startDate,
      LocalDateTime endDate,
      BigDecimal price,
      String currency) {
    return PriceResponseDto.builder()
        .productId(productId)
        .brandId(brandId)
        .priceList(priceList)
        .startDate(startDate)
        .endDate(endDate)
        .price(price)
        .currency(currency)
        .build();
  }

  /**
   * Creates a copy of this PriceResponseDto with a new price.
   *
   * @param newPrice the new price amount
   * @return a new PriceResponseDto instance with the updated price
   */
  public PriceResponseDto withPrice(BigDecimal newPrice) {
    return PriceResponseDto.builder()
        .productId(this.productId)
        .brandId(this.brandId)
        .priceList(this.priceList)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .price(newPrice)
        .currency(this.currency)
        .build();
  }

  /**
   * Checks if this price response represents a valid price.
   *
   * @return true if all required fields are present and valid
   */
  public boolean isValid() {
    return productId != null
        && productId > 0
        && brandId != null
        && brandId > 0
        && priceList != null
        && priceList > 0
        && startDate != null
        && endDate != null
        && startDate.isBefore(endDate)
        && price != null
        && price.compareTo(BigDecimal.ZERO) >= 0
        && currency != null
        && currency.length() == 3;
  }

  /**
   * Gets a formatted price string with currency.
   *
   * @return formatted price string (e.g., "35.50 EUR")
   */
  public String getFormattedPrice() {
    return String.format("%.2f %s", price, currency);
  }

  @Override
  public String toString() {
    return String.format(
        "PriceResponseDto{productId=%d, brandId=%d, priceList=%d, "
            + "startDate=%s, endDate=%s, price=%s, currency='%s'}",
        productId, brandId, priceList, startDate, endDate, price, currency);
  }
}
