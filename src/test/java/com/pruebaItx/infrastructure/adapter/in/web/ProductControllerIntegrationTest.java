package com.pruebaItx.infrastructure.adapter.in.web;

import com.pruebaItx.web.application.entities.Article;
import com.pruebaItx.web.application.entities.ProductsSorted200Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the ProductController. Tests the entire flow from the controller to the
 * repository.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        // Ensure a clean state before each test by making a request with neutral parameters
        HttpHeaders headers = new HttpHeaders();
        headers.add("saleFactor", "0");
        headers.add("stockFactor", "0");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        restTemplate.exchange(
                "http://localhost:" + port + "/product",
                HttpMethod.GET,
                entity,
                ProductsSorted200Response.class);
    }

    @AfterEach
    public void cleanup() {
        // Reset any shared state by making a request with neutral parameters
        HttpHeaders headers = new HttpHeaders();
        headers.add("saleFactor", "0");
        headers.add("stockFactor", "0");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        restTemplate.exchange(
                "http://localhost:" + port + "/product",
                HttpMethod.GET,
                entity,
                ProductsSorted200Response.class);
    }

    @Test
    public void testProductsSorted_SalesFactorOnly() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add("saleFactor", "1");
        headers.add("stockFactor", "0");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // When
        ResponseEntity<ProductsSorted200Response> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/product",
                        HttpMethod.GET,
                        entity,
                        ProductsSorted200Response.class);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<Article> articles = response.getBody().getData();
        assertFalse(articles.isEmpty());

        // Expected order based on sales units (highest to lowest):
        // 5 (650), 1 (100), 3 (80), 2 (50), 6 (20), 4 (3)
        assertEquals(5, articles.get(0).getId());
        assertEquals(1, articles.get(1).getId());
        assertEquals(3, articles.get(2).getId());
        assertEquals(2, articles.get(3).getId());
        assertEquals(6, articles.get(4).getId());
        assertEquals(4, articles.get(5).getId());
    }

    @Test
    public void testProductsSorted_StockFactorOnly() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add("saleFactor", "0");
        headers.add("stockFactor", "1");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // When
        ResponseEntity<ProductsSorted200Response> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/product",
                        HttpMethod.GET,
                        entity,
                        ProductsSorted200Response.class);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<Article> articles = response.getBody().getData();
        assertFalse(articles.isEmpty());

        // Check that products with 3 sizes with stock come first
        for (int i = 0; i < 4; i++) {
            int sizesWithStock = countSizesWithStock(articles.get(i));
            assertEquals(3, sizesWithStock);
        }

        // Check that product with 2 sizes with stock comes next
        assertEquals(2, countSizesWithStock(articles.get(4)));

        // Check that product with 1 size with stock comes last
        assertEquals(1, countSizesWithStock(articles.get(5)));
    }

    // Helper method to count sizes with stock
    private int countSizesWithStock(Article article) {
        int count = 0;
        assertNotNull(article.getStock());
        if (article.getStock().getSizeS() > 0) count++;
        if (article.getStock().getSizeM() > 0) count++;
        if (article.getStock().getSizeL() > 0) count++;
        return count;
    }
}
