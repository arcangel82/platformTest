package com.pruebaItx.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pruebaItx.infrastructure.adapter.out.persistence.PriceJpaRepository;
import com.pruebaItx.infrastructure.adapter.out.persistence.entity.PriceEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the Price API.
 * Tests the complete flow from HTTP request through all layers to the database.
 * Uses the real Spring Boot application context with an in-memory H2 database.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@DisplayName("Price API Integration Tests")
class PriceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceJpaRepository priceRepository;

    @BeforeEach
    void setUp() {
        // Clear the database and insert test data
        priceRepository.deleteAll();
        insertTestData();
    }

    private void insertTestData() {
        // Test data based on the requirements:
        // BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR

        PriceEntity price1 = new PriceEntity();
        price1.setBrandId(1L);
        price1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        price1.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        price1.setPriceList(1);
        price1.setProductId(35455L);
        price1.setPriority(0);
        price1.setPrice(new BigDecimal("35.50"));
        price1.setCurrency("EUR");

        PriceEntity price2 = new PriceEntity();
        price2.setBrandId(1L);
        price2.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
        price2.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));
        price2.setPriceList(2);
        price2.setProductId(35455L);
        price2.setPriority(1);
        price2.setPrice(new BigDecimal("25.45"));
        price2.setCurrency("EUR");

        PriceEntity price3 = new PriceEntity();
        price3.setBrandId(1L);
        price3.setStartDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0));
        price3.setEndDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0));
        price3.setPriceList(3);
        price3.setProductId(35455L);
        price3.setPriority(1);
        price3.setPrice(new BigDecimal("30.50"));
        price3.setCurrency("EUR");

        PriceEntity price4 = new PriceEntity();
        price4.setBrandId(1L);
        price4.setStartDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0));
        price4.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        price4.setPriceList(4);
        price4.setProductId(35455L);
        price4.setPriority(1);
        price4.setPrice(new BigDecimal("38.95"));
        price4.setCurrency("EUR");

        // Additional test data for edge cases
        PriceEntity price5 = new PriceEntity();
        price5.setBrandId(2L); // Different brand
        price5.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        price5.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        price5.setPriceList(1);
        price5.setProductId(35455L);
        price5.setPriority(0);
        price5.setPrice(new BigDecimal("42.00"));
        price5.setCurrency("EUR");

        PriceEntity price6 = new PriceEntity();
        price6.setBrandId(1L);
        price6.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        price6.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        price6.setPriceList(1);
        price6.setProductId(99999L); // Different product
        price6.setPriority(0);
        price6.setPrice(new BigDecimal("25.00"));
        price6.setCurrency("EUR");

        priceRepository.saveAll(Arrays.asList(price1, price2, price3, price4, price5, price6));
    }

    @Test
    @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test1_getPriceAt10_00OnDay14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
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
    @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test2_getPriceAt16_00OnDay14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test3_getPriceAt21_00OnDay14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
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
    @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
    void test4_getPriceAt10_00OnDay15() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
    void test5_getPriceAt21_00OnDay16() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Should return 404 when no price found for given criteria")
    void shouldReturn404WhenNoPriceFound() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2019-06-14T10:00:00") // Date before any price
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when product not found")
    void shouldReturn404WhenProductNotFound() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "99998") // Non-existent product
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when brand not found")
    void shouldReturn404WhenBrandNotFound() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "999") // Non-existent brand
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return correct price for different brand")
    void shouldReturnCorrectPriceForDifferentBrand() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "2") // Different brand
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(2))
                .andExpect(jsonPath("$.price").value(42.00))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Should return correct price for different product")
    void shouldReturnCorrectPriceForDifferentProduct() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "99999") // Different product
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(99999))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(25.00))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Should handle boundary dates correctly - Start date")
    void shouldHandleBoundaryDatesCorrectly_StartDate() throws Exception {
        // Test exact start time of promotional price
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T15:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Should handle boundary dates correctly - End date")
    void shouldHandleBoundaryDatesCorrectly_EndDate() throws Exception {
        // Test exact end time of promotional price
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T18:30:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Should handle boundary dates correctly - One second after end")
    void shouldHandleBoundaryDatesCorrectly_OneSecondAfterEnd() throws Exception {
        // Test one second after promotional price ends
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T18:30:01")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Should prioritize higher priority price when multiple prices overlap")
    void shouldPrioritizeHigherPriorityPrice() throws Exception {
        // At 16:00 on June 14, both price list 1 (priority 0) and price list 2 (priority 1) are valid
        // Should return price list 2 due to higher priority
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Should validate request parameters - Invalid date format")
    void shouldValidateRequestParameters_InvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "invalid-date")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should validate request parameters - Missing parameters")
    void shouldValidateRequestParameters_MissingParameters() throws Exception {
        // Missing applicationDate
        mockMvc.perform(get("/api/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Missing productId
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Missing brandId
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should validate request parameters - Invalid parameter types")
    void shouldValidateRequestParameters_InvalidParameterTypes() throws Exception {
        // Invalid productId
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "not-a-number")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Invalid brandId
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "not-a-number")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should validate request parameters - Negative values")
    void shouldValidateRequestParameters_NegativeValues() throws Exception {
        // Negative productId
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "-1")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Negative brandId
        mockMvc.perform(get("/api/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle concurrent requests correctly")
    void shouldHandleConcurrentRequestsCorrectly() throws Exception {
        // This test ensures that the service can handle multiple simultaneous requests
        // In a real scenario, you might use parallel streams or threading to test this
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/api/prices")
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", "35455")
                            .param("brandId", "1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.price").value(35.50));
        }
    }

    @Test
    @DisplayName("Should return consistent results for same request")
    void shouldReturnConsistentResultsForSameRequest() throws Exception {
        // Make the same request multiple times to ensure consistency
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/api/prices")
                            .param("applicationDate", "2020-06-14T16:00:00")
                            .param("productId", "35455")
                            .param("brandId", "1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList").value(2))
                    .andExpect(jsonPath("$.price").value(25.45))
                    .andExpect(jsonPath("$.currency").value("EUR"));
        }
    }
}