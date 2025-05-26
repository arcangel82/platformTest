package com.pruebaItx.web.application.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Unit tests for the ProductsSorted200Response entity. */
public class ProductsSorted200ResponseTest {

  @Test
  public void testProductsSorted200Response_GettersAndSetters() {
    // Given
    ProductsSorted200Response response = new ProductsSorted200Response();
    List<Article> articles = Arrays.asList(createArticle(1), createArticle(2));

    // When
    response.setData(articles);

    // Then
    assertNotNull(response.getData());
    assertEquals(2, response.getData().size());
    assertEquals(1, response.getData().get(0).getId());
    assertEquals(2, response.getData().get(1).getId());
  }

  @Test
  public void testProductsSorted200Response_DefaultConstructor() {
    // When
    ProductsSorted200Response response = new ProductsSorted200Response();

    // Then
    assertNotNull(response.getData());
    assertEquals(0, response.getData().size());
  }

  @Test
  public void testProductsSorted200Response_Equals() {
    // Given
    ProductsSorted200Response response1 = new ProductsSorted200Response();
    List<Article> articles1 = Arrays.asList(createArticle(1), createArticle(2));
    response1.setData(articles1);

    ProductsSorted200Response response2 = new ProductsSorted200Response();
    List<Article> articles2 = Arrays.asList(createArticle(1), createArticle(2));
    response2.setData(articles2);

    // Then
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
  }

  @Test
  public void testProductsSorted200Response_ToString() {
    // Given
    ProductsSorted200Response response = new ProductsSorted200Response();
    List<Article> articles = Arrays.asList(createArticle(1), createArticle(2));
    response.setData(articles);

    // When
    String toString = response.toString();

    // Then
    assertNotNull(toString);
    // Verify that the toString contains the field values
    assert (toString.contains("data: "));
  }

  @Test
  public void testProductsSorted200Response_FluentSetter() {
    // Given
    ProductsSorted200Response response = new ProductsSorted200Response();
    List<Article> articles = Arrays.asList(createArticle(1), createArticle(2));

    // When
    ProductsSorted200Response result = response.data(articles);

    // Then
    assertNotNull(result);
    assertEquals(response, result);
    assertNotNull(response.getData());
    assertEquals(2, response.getData().size());
  }

  @Test
  public void testProductsSorted200Response_EmptyList() {
    // Given
    ProductsSorted200Response response = new ProductsSorted200Response();

    // When
    response.setData(new ArrayList<>());

    // Then
    assertNotNull(response.getData());
    assertEquals(0, response.getData().size());
  }

  @Test
  public void testProductsSorted200Response_NullList() {
    // Given
    ProductsSorted200Response response = new ProductsSorted200Response();

    // When
    response.setData(null);

    // Then
    assertNull(response.getData());
  }

  // Helper method to create an Article
  private Article createArticle(int id) {
    Article article = new Article();
    article.setId(id);
    article.setName("Test Article " + id);
    article.setSales(100 * id);

    ArticleStock stock = new ArticleStock();
    stock.setSizeS(10 * id);
    stock.setSizeM(20 * id);
    stock.setSizeL(30 * id);
    article.setStock(stock);

    return article;
  }
}
