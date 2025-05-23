package com.pruebaItx.web.application.entities;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.pruebaItx.web.application.entities.CriteriaWeight;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Request with sorting criteria
 */

@Schema(name = "SortingCriteriaRequest", description = "Request with sorting criteria")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class SortingCriteriaRequest {

  @Valid
  private List<@Valid CriteriaWeight> criteria = new ArrayList<>();

  public SortingCriteriaRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SortingCriteriaRequest(List<@Valid CriteriaWeight> criteria) {
    this.criteria = criteria;
  }

  public SortingCriteriaRequest criteria(List<@Valid CriteriaWeight> criteria) {
    this.criteria = criteria;
    return this;
  }

  public SortingCriteriaRequest addCriteriaItem(CriteriaWeight criteriaItem) {
    if (this.criteria == null) {
      this.criteria = new ArrayList<>();
    }
    this.criteria.add(criteriaItem);
    return this;
  }

  /**
   * Get criteria
   * @return criteria
   */
  @NotNull @Valid 
  @Schema(name = "criteria", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("criteria")
  public List<@Valid CriteriaWeight> getCriteria() {
    return criteria;
  }

  public void setCriteria(List<@Valid CriteriaWeight> criteria) {
    this.criteria = criteria;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SortingCriteriaRequest sortingCriteriaRequest = (SortingCriteriaRequest) o;
    return Objects.equals(this.criteria, sortingCriteriaRequest.criteria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(criteria);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SortingCriteriaRequest {\n");
    sb.append("    criteria: ").append(toIndentedString(criteria)).append("\n");
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

