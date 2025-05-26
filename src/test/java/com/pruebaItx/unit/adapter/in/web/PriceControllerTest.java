package com.pruebaItx.unit.adapter.in.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pruebaItx.application.port.in.GetPriceUseCase;
import com.pruebaItx.domain.exception.PriceNotFoundException;
import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.in.web.PriceController;
import com.pruebaItx.infrastructure.adapter.in.web.mapper.PriceWebMapper;
import com.pruebaItx.web.application.entities.PriceResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private GetPriceUseCase getPriceUseCase;

  @MockitoBean private PriceWebMapper priceWebMapper;

  @Test
  @DisplayName("Should return 200 OK and correct price for valid request")
  void shouldReturnCorrectPriceForValidRequest() throws Exception {
    Long productId = 35455L;
    Long brandId = 1L;
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

    Price mockPrice =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(1)
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusDays(1))
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .priority(0)
            .build();

    PriceResponse mockResponse = new PriceResponse();
    mockResponse.setProductId(productId);
    mockResponse.setBrandId(brandId);
    mockResponse.setPrice(35.50);
    mockResponse.setStartDate(applicationDate.minusDays(1));
    mockResponse.setEndDate(applicationDate.plusDays(1));

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(mockPrice);
    when(priceWebMapper.toPriceResponse(mockPrice)).thenReturn(mockResponse);

    mockMvc
        .perform(
            get("/api/v1/prices")
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())
                .param("applicationDate", applicationDate.toString())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(productId))
        .andExpect(jsonPath("$.brandId").value(brandId))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Should return 404 Not Found when no price is available")
  void shouldReturn404WhenPriceNotFound() throws Exception {
    Long productId = 99999L;
    Long brandId = 1L;
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

    when(getPriceUseCase.getApplicablePrice(brandId, productId, applicationDate))
        .thenThrow(new PriceNotFoundException(brandId, productId, applicationDate));

    mockMvc
        .perform(
            get("/api/prices")
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())
                .param("applicationDate", applicationDate.toString())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should return 404 Not Found when missing parameters")
  void shouldReturn404WhenMissingParameters() throws Exception {

    mockMvc
        .perform(get("/api/prices").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
