package com.pruebaItx.unit.adapter.in.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pruebaItx.application.port.in.GetPriceUseCase;
import com.pruebaItx.domain.exception.PriceNotFoundException;
import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.in.web.PriceController;
import com.pruebaItx.infrastructure.adapter.in.web.mapper.PriceWebMapper;
import com.pruebaItx.web.application.entities.PriceResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Unit tests for PriceController. Tests the web layer functionality including request mapping,
 * response handling, and error scenarios.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PriceController Tests")
class PriceControllerTest {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  @Mock private GetPriceUseCase getPriceUseCase;

  @Mock private PriceWebMapper priceWebMapper;

  @InjectMocks private PriceController priceController;

  private MockMvc mockMvc;

  private Price testPrice;
  private PriceResponse testPriceResponse;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();

    // Create test data
    testPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    testPriceResponse =
        new PriceResponse()
            .productId(35455L)
            .brandId(1L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .price(Double.valueOf("35.50"));
  }

  @Test
  @DisplayName("Should return price when valid parameters are provided")
  void shouldReturnPrice_WhenValidParametersProvided() throws Exception {
    // Given
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(testPrice);
    when(priceWebMapper.toDto(testPrice)).thenReturn(testPriceResponse);

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", applicationDate.format(FORMATTER))
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(35.50))
        .andExpect(jsonPath("$.currency").value("EUR"));
  }

  @Test
  @DisplayName("Should return 404 when price not found")
  void shouldReturn404_WhenPriceNotFound() throws Exception {
    // Given
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenThrow(new PriceNotFoundException(brandId, productId, applicationDate));

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", applicationDate.format(FORMATTER))
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should return 500 when unexpected error occurs")
  void shouldReturn500_WhenUnexpectedErrorOccurs() throws Exception {
    // Given
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenThrow(new RuntimeException("Database connection error"));

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", applicationDate.format(FORMATTER))
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @Test
  @DisplayName("Should handle multiple price scenarios - Test 1: 2020-06-14 10:00:00")
  void shouldHandlePriceScenario1() throws Exception {
    // Given - Test case 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1
    // (ZARA)
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price expectedPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse()
            .productId(35455L)
            .brandId(1L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .price(Double.valueOf("35.50"));

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(expectedPrice);
    when(priceWebMapper.toDto(expectedPrice)).thenReturn(expectedResponse);

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Should handle multiple price scenarios - Test 2: 2020-06-14 16:00:00")
  void shouldHandlePriceScenario2() throws Exception {
    // Given - Test case 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1
    // (ZARA)
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price expectedPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(2)
            .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
            .priority(1)
            .price(new BigDecimal("25.45"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse()
            .productId(35455L)
            .brandId(1L)
            .priceList(2)
            .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
            .price(Double.valueOf("25.45"));

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(expectedPrice);
    when(priceWebMapper.toDto(expectedPrice)).thenReturn(expectedResponse);

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Should handle multiple price scenarios - Test 3: 2020-06-14 21:00:00")
  void shouldHandlePriceScenario3() throws Exception {
    // Given - Test case 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1
    // (ZARA)
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price expectedPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse()
            .productId(35455L)
            .brandId(1L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .price(Double.valueOf("35.50"));

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(expectedPrice);
    when(priceWebMapper.toDto(expectedPrice)).thenReturn(expectedResponse);

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Should handle multiple price scenarios - Test 4: 2020-06-15 10:00:00")
  void shouldHandlePriceScenario4() throws Exception {
    // Given - Test case 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1
    // (ZARA)
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price expectedPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(3)
            .startDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0))
            .priority(1)
            .price(new BigDecimal("30.50"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse()
            .productId(35455L)
            .brandId(1L)
            .priceList(3)
            .startDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0))
            .price(Double.valueOf("35.50"));

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(expectedPrice);
    when(priceWebMapper.toDto(expectedPrice)).thenReturn(expectedResponse);

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(30.50));
  }

  @Test
  @DisplayName("Should handle multiple price scenarios - Test 5: 2020-06-16 21:00:00")
  void shouldHandlePriceScenario5() throws Exception {
    // Given - Test case 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1
    // (ZARA)
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price expectedPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(4)
            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priority(1)
            .price(new BigDecimal("38.95"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse()
            .productId(35455L)
            .brandId(1L)
            .priceList(4)
            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .price(Double.valueOf("38.95"));

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(expectedPrice);
    when(priceWebMapper.toDto(expectedPrice)).thenReturn(expectedResponse);

    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-16T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(38.95));
  }

  @Test
  @DisplayName("Should handle invalid date format")
  void shouldHandleInvalidDateFormat() throws Exception {
    // When & Then
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "invalid-date")
                .param("productId", "35455")
                .param("brandId", "1"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should handle missing required parameters")
  void shouldHandleMissingRequiredParameters() throws Exception {
    // When & Then - Missing applicationDate
    mockMvc
        .perform(get("/api/prices").param("productId", "35455").param("brandId", "1"))
        .andExpect(status().isBadRequest());

    // When & Then - Missing productId
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("brandId", "1"))
        .andExpect(status().isBadRequest());

    // When & Then - Missing brandId
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should handle invalid parameter types")
  void shouldHandleInvalidParameterTypes() throws Exception {
    // When & Then - Invalid productId
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "invalid")
                .param("brandId", "1"))
        .andExpect(status().isBadRequest());

    // When & Then - Invalid brandId
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "invalid"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should handle negative parameter values")
  void shouldHandleNegativeParameterValues() throws Exception {
    // When & Then - Negative productId
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "-1")
                .param("brandId", "1"))
        .andExpect(status().isBadRequest());

    // When & Then - Negative brandId
    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "-1"))
        .andExpect(status().isBadRequest());
  }
}
