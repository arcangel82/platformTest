package com.pruebaItx.domain.model;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain model representing a price with its validity period and priority.
 * This is the core business entity that encapsulates all price-related business rules.
 */
@Getter
public final class Price {

  private final Long brandId;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final Integer priceList;
  private final Long productId;
  private final Integer priority;
  private final BigDecimal price;
  private final String currency;

  private Price(Builder builder) {
    this.brandId = Objects.requireNonNull(builder.brandId, "Brand ID cannot be null");
    this.startDate = Objects.requireNonNull(builder.startDate, "Start date cannot be null");
    this.endDate = Objects.requireNonNull(builder.endDate, "End date cannot be null");
    this.priceList = Objects.requireNonNull(builder.priceList, "Price list cannot be null");
    this.productId = Objects.requireNonNull(builder.productId, "Product ID cannot be null");
    this.priority = Objects.requireNonNull(builder.priority, "Priority cannot be null");
    this.price = Objects.requireNonNull(builder.price, "Price cannot be null");
    this.currency = Objects.requireNonNull(builder.currency, "Currency cannot be null");

    validateBusinessRules();
  }

  private void validateBusinessRules() {
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date must be before or equal to end date");
    }
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    }
    if (priority < 0) {
      throw new IllegalArgumentException("Priority cannot be negative");
    }
  }

  /**
   * Checks if this price is applicable for the given date.
   */
  public boolean isApplicableAt(LocalDateTime applicationDate) {
    return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
  }

  /**
   * Checks if this price has higher priority than another price.
   */
  public boolean hasHigherPriorityThan(Price other) {
    return this.priority > other.priority;
  }

    @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Price price1 = (Price) o;
    return Objects.equals(brandId, price1.brandId) &&
            Objects.equals(startDate, price1.startDate) &&
            Objects.equals(endDate, price1.endDate) &&
            Objects.equals(priceList, price1.priceList) &&
            Objects.equals(productId, price1.productId) &&
            Objects.equals(priority, price1.priority) &&
            Objects.equals(price, price1.price) &&
            Objects.equals(currency, price1.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brandId, startDate, endDate, priceList, productId, priority, price, currency);
  }

  @Override
  public String toString() {
    return "Price{" +
            "brandId=" + brandId +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", priceList=" + priceList +
            ", productId=" + productId +
            ", priority=" + priority +
            ", price=" + price +
            ", currency='" + currency + '\'' +
            '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;

    private Builder() {}

    public Builder brandId(Long brandId) {
      this.brandId = brandId;
      return this;
    }

    public Builder startDate(LocalDateTime startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder endDate(LocalDateTime endDate) {
      this.endDate = endDate;
      return this;
    }

    public Builder priceList(Integer priceList) {
      this.priceList = priceList;
      return this;
    }

    public Builder productId(Long productId) {
      this.productId = productId;
      return this;
    }

    public Builder priority(Integer priority) {
      this.priority = priority;
      return this;
    }

    public Builder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Price build() {
      return new Price(this);
    }
  }
}