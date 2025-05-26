package com.pruebaItx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

/** Unit tests for the SwaggerConfig class. */
public class SwaggerConfigTest {

  @Test
  public void testCustomOpenAPI() {
    // Given
    SwaggerConfig swaggerConfig = new SwaggerConfig();

    // When
    OpenAPI openAPI = swaggerConfig.customOpenAPI();

    // Then
    assertNotNull(openAPI);
    assertNotNull(openAPI.getInfo());
    assertEquals("Product API", openAPI.getInfo().getTitle());
    assertEquals("1.0", openAPI.getInfo().getVersion());
    assertEquals("API for managing products", openAPI.getInfo().getDescription());
    assertNotNull(openAPI.getInfo().getContact());
    assertEquals("Angel Perez", openAPI.getInfo().getContact().getName());
    assertEquals("angelperez@paradigmadigital.com", openAPI.getInfo().getContact().getEmail());
  }

  @Test
  public void testInfo() {
    // Given
    SwaggerConfig swaggerConfig = new SwaggerConfig();

    // When
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Info info = openAPI.getInfo();

    // Then
    assertNotNull(info);
    assertEquals("Product API", info.getTitle());
    assertEquals("1.0", info.getVersion());
    assertEquals("API for managing products", info.getDescription());
  }

  @Test
  public void testContact() {
    // Given
    SwaggerConfig swaggerConfig = new SwaggerConfig();

    // When
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Contact contact = openAPI.getInfo().getContact();

    // Then
    assertNotNull(contact);
    assertEquals("Angel Perez", contact.getName());
    assertEquals("angelperez@paradigmadigital.com", contact.getEmail());
  }
}
