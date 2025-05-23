package com.pruebaItx.web.application.entities;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ArticleStock
 */

@JsonTypeName("Article_stock")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class ArticleStock {

  private @Nullable Integer sizeS;

  private @Nullable Integer sizeM;

  private @Nullable Integer sizeL;

  public ArticleStock sizeS(Integer sizeS) {
    this.sizeS = sizeS;
    return this;
  }

  /**
   * Get sizeS
   * maximum: 999
   * @return sizeS
   */
  @Max(999) 
  @Schema(name = "sizeS", example = "888", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sizeS")
  public Integer getSizeS() {
    return sizeS;
  }

  public void setSizeS(Integer sizeS) {
    this.sizeS = sizeS;
  }

  public ArticleStock sizeM(Integer sizeM) {
    this.sizeM = sizeM;
    return this;
  }

  /**
   * Get sizeM
   * maximum: 999
   * @return sizeM
   */
  @Max(999) 
  @Schema(name = "sizeM", example = "888", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sizeM")
  public Integer getSizeM() {
    return sizeM;
  }

  public void setSizeM(Integer sizeM) {
    this.sizeM = sizeM;
  }

  public ArticleStock sizeL(Integer sizeL) {
    this.sizeL = sizeL;
    return this;
  }

  /**
   * Get sizeL
   * maximum: 999
   * @return sizeL
   */
  @Max(999) 
  @Schema(name = "sizeL", example = "888", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sizeL")
  public Integer getSizeL() {
    return sizeL;
  }

  public void setSizeL(Integer sizeL) {
    this.sizeL = sizeL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleStock articleStock = (ArticleStock) o;
    return Objects.equals(this.sizeS, articleStock.sizeS) &&
        Objects.equals(this.sizeM, articleStock.sizeM) &&
        Objects.equals(this.sizeL, articleStock.sizeL);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sizeS, sizeM, sizeL);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArticleStock {\n");
    sb.append("    sizeS: ").append(toIndentedString(sizeS)).append("\n");
    sb.append("    sizeM: ").append(toIndentedString(sizeM)).append("\n");
    sb.append("    sizeL: ").append(toIndentedString(sizeL)).append("\n");
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

