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
 * Used when an API throws an Error, typically with a HTTP error response-code (3xx, 4xx, 5xx)
 */

@Schema(name = "ErrorResource", description = "Used when an API throws an Error, typically with a HTTP error response-code (3xx, 4xx, 5xx)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.13.0")
public class ErrorResource {

  private @Nullable String errorType;

  private @Nullable Integer errorCode;

  private @Nullable String errorCodeMsg;

  private @Nullable String errorMessage;

  public ErrorResource errorType(String errorType) {
    this.errorType = errorType;
    return this;
  }

  /**
   * Describes different error severity levels, INFO, WARNING, ERROR, FATAL
   * @return errorType
   */
  @Pattern(regexp = "^[0-9a-zA-Z]+$") @Size(max = 32) 
  @Schema(name = "errorType", example = "ERROR", description = "Describes different error severity levels, INFO, WARNING, ERROR, FATAL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorType")
  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  public ErrorResource errorCode(Integer errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * donde nnnnn es un numero de error. >= 1000 y < 90000
   * minimum: 1
   * maximum: 99999
   * @return errorCode
   */
  @Min(1) @Max(99999) 
  @Schema(name = "errorCode", description = "donde nnnnn es un numero de error. >= 1000 y < 90000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorCode")
  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorResource errorCodeMsg(String errorCodeMsg) {
    this.errorCodeMsg = errorCodeMsg;
    return this;
  }

  /**
   * Internal error description
   * @return errorCodeMsg
   */
  @Pattern(regexp = "^[0-9a-zA-Z]+$") @Size(max = 500) 
  @Schema(name = "errorCodeMsg", description = "Internal error description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorCodeMsg")
  public String getErrorCodeMsg() {
    return errorCodeMsg;
  }

  public void setErrorCodeMsg(String errorCodeMsg) {
    this.errorCodeMsg = errorCodeMsg;
  }

  public ErrorResource errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * More details and corrective actions related to the error which can be shown to a client user.
   * @return errorMessage
   */
  @Pattern(regexp = "^[0-9a-zA-Z]+$") @Size(max = 500) 
  @Schema(name = "errorMessage", example = "The parameters entered are not valid", description = "More details and corrective actions related to the error which can be shown to a client user.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResource errorResource = (ErrorResource) o;
    return Objects.equals(this.errorType, errorResource.errorType) &&
        Objects.equals(this.errorCode, errorResource.errorCode) &&
        Objects.equals(this.errorCodeMsg, errorResource.errorCodeMsg) &&
        Objects.equals(this.errorMessage, errorResource.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorType, errorCode, errorCodeMsg, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResource {\n");
    sb.append("    errorType: ").append(toIndentedString(errorType)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorCodeMsg: ").append(toIndentedString(errorCodeMsg)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
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

