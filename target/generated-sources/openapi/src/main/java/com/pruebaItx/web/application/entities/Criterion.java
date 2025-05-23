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
 * Sorting Criterion
 */

@Schema(name = "Criterion", description = "Sorting Criterion")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class Criterion {

  private @Nullable String id;

  private @Nullable String name;

  private @Nullable String description;

  public Criterion id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Criterion identifier
   * @return id
   */
  
  @Schema(name = "id", example = "salesUnits", description = "Criterion identifier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Criterion name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Criterion name
   * @return name
   */
  
  @Schema(name = "name", example = "Sales Units", description = "Criterion name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Criterion description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Criterion description
   * @return description
   */
  
  @Schema(name = "description", example = "Scores products based on number of units sold", description = "Criterion description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Criterion criterion = (Criterion) o;
    return Objects.equals(this.id, criterion.id) &&
        Objects.equals(this.name, criterion.name) &&
        Objects.equals(this.description, criterion.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Criterion {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

