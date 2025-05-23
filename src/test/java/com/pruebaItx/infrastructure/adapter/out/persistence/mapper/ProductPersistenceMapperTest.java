package com.pruebaItx.infrastructure.adapter.out.persistence.mapper;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.infrastructure.adapter.out.persistence.ProductJpaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the ProductPersistenceMapper.
 */
public class ProductPersistenceMapperTest {

    private final ProductPersistenceMapper mapper = Mappers.getMapper(ProductPersistenceMapper.class);

    @Test
    public void testToDomainEntity() {
        // Given
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        jpaEntity.setProduct_id(1);
        jpaEntity.setProduct_name("Test Product");
        jpaEntity.setSales_unit(100);
        jpaEntity.setSize_S_stock(10);
        jpaEntity.setSize_M_stock(20);
        jpaEntity.setSize_L_stock(30);

        // When
        Product domainEntity = mapper.toDomainEntity(jpaEntity);

        // Then
        assertNotNull(domainEntity);
        assertEquals(1, domainEntity.getId());
        assertEquals("Test Product", domainEntity.getName());
        assertEquals(100, domainEntity.getSalesUnits());
        assertNotNull(domainEntity.getStock());
        assertEquals(10, domainEntity.getStock().getSizeS());
        assertEquals(20, domainEntity.getStock().getSizeM());
        assertEquals(30, domainEntity.getStock().getSizeL());
    }

    @Test
    public void testToDomainEntity_NullValues() {
        // Given
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        // Leave all fields as null

        // When
        Product domainEntity = mapper.toDomainEntity(jpaEntity);

        // Then
        assertNotNull(domainEntity);
        assertEquals(null, domainEntity.getId());
        assertEquals(null, domainEntity.getName());
        assertEquals(null, domainEntity.getSalesUnits());
        assertNotNull(domainEntity.getStock());
        assertEquals(null, domainEntity.getStock().getSizeS());
        assertEquals(null, domainEntity.getStock().getSizeM());
        assertEquals(null, domainEntity.getStock().getSizeL());
    }

    @Test
    public void testToDomainEntity_ZeroValues() {
        // Given
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        jpaEntity.setProduct_id(0);
        jpaEntity.setProduct_name("");
        jpaEntity.setSales_unit(0);
        jpaEntity.setSize_S_stock(0);
        jpaEntity.setSize_M_stock(0);
        jpaEntity.setSize_L_stock(0);

        // When
        Product domainEntity = mapper.toDomainEntity(jpaEntity);

        // Then
        assertNotNull(domainEntity);
        assertEquals(0, domainEntity.getId());
        assertEquals("", domainEntity.getName());
        assertEquals(0, domainEntity.getSalesUnits());
        assertNotNull(domainEntity.getStock());
        assertEquals(0, domainEntity.getStock().getSizeS());
        assertEquals(0, domainEntity.getStock().getSizeM());
        assertEquals(0, domainEntity.getStock().getSizeL());
    }

    @Test
    public void testToDomainEntity_NegativeValues() {
        // Given
        ProductJpaEntity jpaEntity = new ProductJpaEntity();
        jpaEntity.setProduct_id(-1);
        jpaEntity.setProduct_name("Negative Test");
        jpaEntity.setSales_unit(-100);
        jpaEntity.setSize_S_stock(-10);
        jpaEntity.setSize_M_stock(-20);
        jpaEntity.setSize_L_stock(-30);

        // When
        Product domainEntity = mapper.toDomainEntity(jpaEntity);

        // Then
        assertNotNull(domainEntity);
        assertEquals(-1, domainEntity.getId());
        assertEquals("Negative Test", domainEntity.getName());
        assertEquals(-100, domainEntity.getSalesUnits());
        assertNotNull(domainEntity.getStock());
        assertEquals(-10, domainEntity.getStock().getSizeS());
        assertEquals(-20, domainEntity.getStock().getSizeM());
        assertEquals(-30, domainEntity.getStock().getSizeL());
    }
}
