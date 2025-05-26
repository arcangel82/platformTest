package com.pruebaItx.acceptance;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Acceptance tests for Price API endpoints Tests the complete functionality from API layer to
 * database
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
class PriceAcceptanceTest {

  @Autowired private MockMvc mockMvc;

  private static final String PRICES_ENDPOINT = "/api/v1/prices";
  private static final Long PRODUCT_ID = 35455L;
  private static final Long BRAND_ID = 1L; // ZARA

  @Test
  @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
  void shouldReturnPriceAt10AMOnDay14() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(
                    MediaType
                        .APPLICATION_JSON)) // This content type applies to the request, not the
                                            // response
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
  void shouldReturnPriceAt4PMOnDay14() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T16:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
  void shouldReturnPriceAt9PMOnDay14() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T21:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
  void shouldReturnPriceAt10AMOnDay15() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-15T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(3))
        .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
        .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
        .andExpect(jsonPath("$.price").value(30.50));
  }

  @Test
  @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
  void shouldReturnPriceAt9PMOnDay16() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-16T21:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(4))
        .andExpect(
            jsonPath("$.startDate")
                .value("2020-06-15T16:00:00")) // Corrected startDate based on example data
        .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
        .andExpect(jsonPath("$.price").value(38.95));
  }

  @Test
  @DisplayName("Should return 400 when applicationDate parameter is missing")
  void shouldReturn400WhenApplicationDateIsMissing() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                // No contentType needed for incoming request with missing param, but good practice
                .accept(MediaType.APPLICATION_JSON)) // Expect JSON response
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .contentType(
                    MediaType.APPLICATION_JSON)) // This will now pass if Spring returns JSON
        .andExpect(jsonPath("$.status").value(400)) // Spring's default error response has 'status'
        .andExpect(
            jsonPath("$.error").value("Bad Request")) // Spring's default for missing parameters
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(
            jsonPath("$.message")
                .value(
                    containsString(
                        "Required parameter 'applicationDate' is not present"))); // Specific
                                                                                  // message
  }

  @Test
  @DisplayName("Should return 400 when productId parameter is missing")
  void shouldReturn400WhenProductIdIsMissing() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("brandId", BRAND_ID.toString())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(
            jsonPath("$.message")
                .value(containsString("Required parameter 'productId' is not present")));
  }

  @Test
  @DisplayName("Should return 400 when brandId parameter is missing")
  void shouldReturn400WhenBrandIdIsMissing() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(
            jsonPath("$.message")
                .value(containsString("Required parameter 'brandId' is not present")));
  }

  @Test
  @DisplayName("Should return 400 when applicationDate format is invalid")
  void shouldReturn400WhenApplicationDateFormatIsInvalid() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "invalid-date")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .accept(MediaType.APPLICATION_JSON)) // Expect JSON response
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // This should now pass
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(
            jsonPath("$.message")
                .value(
                    containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDateTime'"))); // More specific message
  }

  @Test
  @DisplayName("Should return 400 when productId is not a valid number")
  void shouldReturn400WhenProductIdIsNotValidNumber() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "not-a-number")
                .param("brandId", BRAND_ID.toString())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(
            jsonPath("$.message")
                .value(
                    containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'")));
  }

  @Test
  @DisplayName("Should return 400 when brandId is not a valid number")
  void shouldReturn400WhenBrandIdIsNotValidNumber() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", "not-a-number")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(
            jsonPath("$.message")
                .value(
                    containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'")));
  }

  @Test
  @DisplayName("Should return 404 when no price found for given parameters")
  void shouldReturn404WhenNoPriceFound() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2019-06-14T10:00:00")
                .param("productId", "99999")
                .param("brandId", "99")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(404)) // Spring's default for 404 error
        .andExpect(jsonPath("$.error").value("Not Found")) // Spring's default for 404 error
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(
            jsonPath("$.message")
                .value(
                    startsWith(
                        "No applicable price found for brandId:"))); // Match the specific message
                                                                     // from PriceNotFoundException
  }

  @Test
  @DisplayName("Should return 404 when product exists but no price for given date")
  void shouldReturn404WhenProductExistsButNoPriceForDate() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2019-01-01T10:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT))
        .andExpect(
            jsonPath("$.message").value(startsWith("No applicable price found for brandId:")));
  }

  @Test
  @DisplayName("Should handle edge case: request at exact start time")
  void shouldHandleRequestAtExactStartTime() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T15:00:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Should handle edge case: request at exact end time")
  void shouldHandleRequestAtExactEndTime() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("applicationDate", "2020-06-14T18:30:00")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  // Helper method to setup the initial data for the tests
  // This would typically be in a @BeforeEach or using Testcontainers for H2 initialization
  // For demonstration, let's assume an SQL script initializes it.
  /*
  @BeforeEach
  void setupDatabase() {
      // Example of how you might load data for tests if not using a data.sql file
      // This is conceptual, actual implementation depends on your data loading strategy
      jdbcTemplate.execute("DELETE FROM PRICES"); // Clean up before each test if not @Transactional rollback
      jdbcTemplate.execute("INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR');");
      jdbcTemplate.execute("INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR');");
      jdbcTemplate.execute("INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR');");
      jdbcTemplate.execute("INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');");
  }
  */
}
