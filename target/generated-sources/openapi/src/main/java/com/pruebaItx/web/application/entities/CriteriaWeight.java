package com.pruebaItx.web.application.entities;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Sorting Criterion with Weight
 */

@Schema(name = "CriteriaWeight", description = "Sorting Criterion with Weight")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class CriteriaWeight {

  private String id;

  private Integer weight;

  public CriteriaWeight() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CriteriaWeight(String id, Integer weight) {
    this.id = id;
    this.weight = weight;
  }

  public CriteriaWeight id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Criterion identifier
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "salesUnits", description = "Criterion identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CriteriaWeight weight(Integer weight) {
    this.weight = weight;
    return this;
  }

  /**
   * Weight for this criterion
   * @return weight
   */
  @NotNull 
  @Schema(name = "weight", example = "12345", description = "Weight for this criterion", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("weight")
  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CriteriaWeight criteriaWeight = (CriteriaWeight) o;
    return Objects.equals(this.id, criteriaWeight.id) &&
        Objects.equals(this.weight, criteriaWeight.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CriteriaWeight {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
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

