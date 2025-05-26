package com.pruebaItx.web.application.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for the Article entity. */
public class ArticleTest {

  @Test
  public void testArticle_GettersAndSetters() {
    // Given
    Article article = new Article();

    // When
    article.setId(1);
    article.setName("Test Article");
    article.setSales(100);

    ArticleStock stock = new ArticleStock();
    stock.setSizeS(10);
    stock.setSizeM(20);
    stock.setSizeL(30);
    article.setStock(stock);

    // Then
    assertEquals(1, article.getId());
    assertEquals("Test Article", article.getName());
    assertEquals(100, article.getSales());
    assertNotNull(article.getStock());
    assertEquals(10, article.getStock().getSizeS());
    assertEquals(20, article.getStock().getSizeM());
    assertEquals(30, article.getStock().getSizeL());
  }

  @Test
  public void testArticle_DefaultConstructor() {
    // When
    Article article = new Article();

    // Then
    assertNull(article.getId());
    assertNull(article.getName());
    assertNull(article.getSales());
    assertNull(article.getStock());
  }

  @Test
  public void testArticle_Equals() {
    // Given
    Article article1 = new Article();
    article1.setId(1);
    article1.setName("Test Article");
    article1.setSales(100);

    ArticleStock stock1 = new ArticleStock();
    stock1.setSizeS(10);
    stock1.setSizeM(20);
    stock1.setSizeL(30);
    article1.setStock(stock1);

    Article article2 = new Article();
    article2.setId(1);
    article2.setName("Test Article");
    article2.setSales(100);

    ArticleStock stock2 = new ArticleStock();
    stock2.setSizeS(10);
    stock2.setSizeM(20);
    stock2.setSizeL(30);
    article2.setStock(stock2);

    // Then
    assertEquals(article1, article2);
    assertEquals(article1.hashCode(), article2.hashCode());
  }

  @Test
  public void testArticle_ToString() {
    // Given
    Article article = new Article();
    article.setId(1);
    article.setName("Test Article");
    article.setSales(100);

    ArticleStock stock = new ArticleStock();
    stock.setSizeS(10);
    stock.setSizeM(20);
    stock.setSizeL(30);
    article.setStock(stock);

    // When
    String toString = article.toString();

    // Then
    assertNotNull(toString);
    // Verify that the toString contains the field values
    assert (toString.contains("id: 1"));
    assert (toString.contains("name: Test Article"));
    assert (toString.contains("sales: 100"));
    assert (toString.contains("stock: "));
  }
}
