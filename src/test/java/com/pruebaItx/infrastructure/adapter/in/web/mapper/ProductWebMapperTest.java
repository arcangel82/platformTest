package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.Stock;
import com.pruebaItx.web.application.entities.Article;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the ProductWebMapper.
 */
public class ProductWebMapperTest {

    private final ProductWebMapper mapper = Mappers.getMapper(ProductWebMapper.class);

    @Test
    public void testToDto() {
        // Given
        Product domainProduct = createDomainProduct(1, "Test Product", 100, 10, 20, 30);

        // When
        Article dto = mapper.toDto(domainProduct);

        // Then
        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Test Product", dto.getName());
        assertEquals(100, dto.getSales());
        assertNotNull(dto.getStock());
        assertEquals(10, dto.getStock().getSizeS());
        assertEquals(20, dto.getStock().getSizeM());
        assertEquals(30, dto.getStock().getSizeL());
    }

    @Test
    public void testToDto_NullValues() {
        // Given
        Product domainProduct = Product.builder().build();
        // Leave all fields as null

        // When
        Article dto = mapper.toDto(domainProduct);

        // Then
        assertNotNull(dto);
        assertEquals(null, dto.getId());
        assertEquals(null, dto.getName());
        assertEquals(null, dto.getSales());
        assertEquals(null, dto.getStock());
    }

    @Test
    public void testToDto_ZeroValues() {
        // Given
        Product domainProduct = createDomainProduct(0, "", 0, 0, 0, 0);

        // When
        Article dto = mapper.toDto(domainProduct);

        // Then
        assertNotNull(dto);
        assertEquals(0, dto.getId());
        assertEquals("", dto.getName());
        assertEquals(0, dto.getSales());
        assertNotNull(dto.getStock());
        assertEquals(0, dto.getStock().getSizeS());
        assertEquals(0, dto.getStock().getSizeM());
        assertEquals(0, dto.getStock().getSizeL());
    }

    @Test
    public void testToDto_NegativeValues() {
        // Given
        Product domainProduct = createDomainProduct(-1, "Negative Test", -100, -10, -20, -30);

        // When
        Article dto = mapper.toDto(domainProduct);

        // Then
        assertNotNull(dto);
        assertEquals(-1, dto.getId());
        assertEquals("Negative Test", dto.getName());
        assertEquals(-100, dto.getSales());
        assertNotNull(dto.getStock());
        assertEquals(-10, dto.getStock().getSizeS());
        assertEquals(-20, dto.getStock().getSizeM());
        assertEquals(-30, dto.getStock().getSizeL());
    }

    // Helper method to create a domain Product
    private Product createDomainProduct(
            int id, String name, int salesUnits, int sizeS, int sizeM, int sizeL) {
        Stock stock = Stock.builder().sizeS(sizeS).sizeM(sizeM).sizeL(sizeL).build();

        return Product.builder().id(id).name(name).salesUnits(salesUnits).stock(stock).build();
    }
}
