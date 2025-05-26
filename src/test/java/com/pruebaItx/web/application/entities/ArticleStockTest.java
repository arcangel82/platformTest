package com.pruebaItx.web.application.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Unit tests for the ArticleStock entity. */
public class ArticleStockTest {

  @Test
  public void testArticleStock_GettersAndSetters() {
    // Given
    ArticleStock stock = new ArticleStock();

    // When
    stock.setSizeS(10);
    stock.setSizeM(20);
    stock.setSizeL(30);

    // Then
    assertEquals(10, stock.getSizeS());
    assertEquals(20, stock.getSizeM());
    assertEquals(30, stock.getSizeL());
  }

  @Test
  public void testArticleStock_DefaultConstructor() {
    // When
    ArticleStock stock = new ArticleStock();

    // Then
    assertNull(stock.getSizeS());
    assertNull(stock.getSizeM());
    assertNull(stock.getSizeL());
  }

  @Test
  public void testArticleStock_Equals() {
    // Given
    ArticleStock stock1 = new ArticleStock();
    stock1.setSizeS(10);
    stock1.setSizeM(20);
    stock1.setSizeL(30);

    ArticleStock stock2 = new ArticleStock();
    stock2.setSizeS(10);
    stock2.setSizeM(20);
    stock2.setSizeL(30);

    // Then
    assertEquals(stock1, stock2);
    assertEquals(stock1.hashCode(), stock2.hashCode());
  }

  @Test
  public void testArticleStock_ToString() {
    // Given
    ArticleStock stock = new ArticleStock();
    stock.setSizeS(10);
    stock.setSizeM(20);
    stock.setSizeL(30);

    // When
    String toString = stock.toString();

    // Then
    assertNotNull(toString);
    // Verify that the toString contains the field values
    assert (toString.contains("sizeS: 10"));
    assert (toString.contains("sizeM: 20"));
    assert (toString.contains("sizeL: 30"));
  }

  @Test
  public void testArticleStock_NullValues() {
    // Given
    ArticleStock stock = new ArticleStock();

    // When
    stock.setSizeS(null);
    stock.setSizeM(null);
    stock.setSizeL(null);

    // Then
    assertNull(stock.getSizeS());
    assertNull(stock.getSizeM());
    assertNull(stock.getSizeL());
  }

  @Test
  public void testArticleStock_ZeroValues() {
    // Given
    ArticleStock stock = new ArticleStock();

    // When
    stock.setSizeS(0);
    stock.setSizeM(0);
    stock.setSizeL(0);

    // Then
    assertEquals(0, stock.getSizeS());
    assertEquals(0, stock.getSizeM());
    assertEquals(0, stock.getSizeL());
  }

  @Test
  public void testArticleStock_NegativeValues() {
    // Given
    ArticleStock stock = new ArticleStock();

    // When
    stock.setSizeS(-10);
    stock.setSizeM(-20);
    stock.setSizeL(-30);

    // Then
    assertEquals(-10, stock.getSizeS());
    assertEquals(-20, stock.getSizeM());
    assertEquals(-30, stock.getSizeL());
  }
}
