package com.pruebaItx.web.application.entities;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PriceResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class PriceResponse {

  private Long productId;

  private Long brandId;

  private Integer priceList;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime endDate;

  private Double price;

  private @Nullable String currency;

  public PriceResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate, Double price) {
    this.productId = productId;
    this.brandId = brandId;
    this.priceList = priceList;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
  }

  public PriceResponse productId(Long productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Product identifier
   * @return productId
   */
  @NotNull 
  @Schema(name = "productId", example = "35455", description = "Product identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productId")
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public PriceResponse brandId(Long brandId) {
    this.brandId = brandId;
    return this;
  }

  /**
   * Brand identifier
   * @return brandId
   */
  @NotNull 
  @Schema(name = "brandId", example = "1", description = "Brand identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("brandId")
  public Long getBrandId() {
    return brandId;
  }

  public void setBrandId(Long brandId) {
    this.brandId = brandId;
  }

  public PriceResponse priceList(Integer priceList) {
    this.priceList = priceList;
    return this;
  }

  /**
   * Price list identifier
   * @return priceList
   */
  @NotNull 
  @Schema(name = "priceList", example = "1", description = "Price list identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("priceList")
  public Integer getPriceList() {
    return priceList;
  }

  public void setPriceList(Integer priceList) {
    this.priceList = priceList;
  }

  public PriceResponse startDate(LocalDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Start date of price validity
   * @return startDate
   */
  @NotNull @Valid 
  @Schema(name = "startDate", description = "Start date of price validity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("startDate")
  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public PriceResponse endDate(LocalDateTime endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * End date of price validity
   * @return endDate
   */
  @NotNull @Valid 
  @Schema(name = "endDate", description = "End date of price validity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("endDate")
  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public PriceResponse price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Final price to apply
   * @return price
   */
  @NotNull 
  @Schema(name = "price", example = "35.5", description = "Final price to apply", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public PriceResponse currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Currency
   * @return currency
   */
  
  @Schema(name = "currency", example = "EUR", description = "Currency", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PriceResponse priceResponse = (PriceResponse) o;
    return Objects.equals(this.productId, priceResponse.productId) &&
        Objects.equals(this.brandId, priceResponse.brandId) &&
        Objects.equals(this.priceList, priceResponse.priceList) &&
        Objects.equals(this.startDate, priceResponse.startDate) &&
        Objects.equals(this.endDate, priceResponse.endDate) &&
        Objects.equals(this.price, priceResponse.price) &&
        Objects.equals(this.currency, priceResponse.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, brandId, priceList, startDate, endDate, price, currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PriceResponse {\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    brandId: ").append(toIndentedString(brandId)).append("\n");
    sb.append("    priceList: ").append(toIndentedString(priceList)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

