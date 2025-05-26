package com.pruebaItx.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for price query requests. Contains the parameters needed to search for a
 * product price.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request parameters for price query")
public class PriceRequestDto {

  /**
   * Date and time when the price should be applied. Must be in ISO 8601 format
   * (yyyy-MM-ddTHH:mm:ss).
   */
  @NotNull(message = "Application date is required")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Schema(
      description = "Date and time when the price should be applied",
      example = "2020-06-14T10:00:00",
      required = true,
      format = "date-time")
  private LocalDateTime applicationDate;

  /** Product identifier. Must be a positive integer. */
  @NotNull(message = "Product ID is required")
  @Positive(message = "Product ID must be positive")
  @Schema(description = "Product identifier", example = "35455", required = true, minimum = "1")
  private Long productId;

  /** Brand identifier. Must be a positive integer (1 = ZARA). */
  @NotNull(message = "Brand ID is required")
  @Positive(message = "Brand ID must be positive")
  @Schema(
      description = "Brand identifier (1 = ZARA)",
      example = "1",
      required = true,
      minimum = "1")
  private Long brandId;

  /**
   * Creates a PriceRequestDto with the specified parameters.
   *
   * @param applicationDate the date and time when the price should be applied
   * @param productId the product identifier
   * @param brandId the brand identifier
   * @return a new PriceRequestDto instance
   */
  public static PriceRequestDto of(LocalDateTime applicationDate, Long productId, Long brandId) {
    return PriceRequestDto.builder()
        .applicationDate(applicationDate)
        .productId(productId)
        .brandId(brandId)
        .build();
  }

  /**
   * Validates that all required fields are present and valid.
   *
   * @return true if the request is valid, false otherwise
   */
  public boolean isValid() {
    return applicationDate != null
        && productId != null
        && productId > 0
        && brandId != null
        && brandId > 0;
  }

  @Override
  public String toString() {
    return String.format(
        "PriceRequestDto{applicationDate=%s, productId=%d, brandId=%d}",
        applicationDate, productId, brandId);
  }
}
