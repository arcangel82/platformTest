package com.pruebaItx.web.application.entities;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.pruebaItx.web.application.entities.ArticleStock;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Article
 */

@Schema(name = "Article", description = "Article")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class Article {

  private @Nullable Integer id;

  private @Nullable String name;

  private @Nullable Integer sales;

  private @Nullable ArticleStock stock;

  public Article id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Article identifier
   * maximum: 99999
   * @return id
   */
  @Max(99999) 
  @Schema(name = "id", example = "12345", description = "Article identifier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Article name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Article name
   * @return name
   */
  @Pattern(regexp = "^[0-9a-zA-Z]+$") @Size(max = 30) 
  @Schema(name = "name", example = "Long slouchy pants", description = "Article name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Article sales(Integer sales) {
    this.sales = sales;
    return this;
  }

  /**
   * Get sales
   * maximum: 999
   * @return sales
   */
  @Max(999) 
  @Schema(name = "sales", example = "888", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sales")
  public Integer getSales() {
    return sales;
  }

  public void setSales(Integer sales) {
    this.sales = sales;
  }

  public Article stock(ArticleStock stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Get stock
   * @return stock
   */
  @Valid 
  @Schema(name = "stock", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stock")
  public ArticleStock getStock() {
    return stock;
  }

  public void setStock(ArticleStock stock) {
    this.stock = stock;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return Objects.equals(this.id, article.id) &&
        Objects.equals(this.name, article.name) &&
        Objects.equals(this.sales, article.sales) &&
        Objects.equals(this.stock, article.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, sales, stock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Article {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    sales: ").append(toIndentedString(sales)).append("\n");
    sb.append("    stock: ").append(toIndentedString(stock)).append("\n");
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

