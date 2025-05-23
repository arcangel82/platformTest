package com.pruebaItx.infrastructure.adapter.out.persistence.mapper;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.infrastructure.adapter.out.persistence.ProductJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between ProductJpaEntity and Product domain entity.
 */
@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

    /**
     * Maps a JPA entity to a domain entity.
     *
     * @param jpaEntity the JPA entity
     * @return the domain entity
     */
    @Mapping(target = "id", source = "product_id")
    @Mapping(target = "name", source = "product_name")
    @Mapping(target = "salesUnits", source = "sales_unit")
    @Mapping(target = "stock.sizeS", source = "size_S_stock")
    @Mapping(target = "stock.sizeM", source = "size_M_stock")
    @Mapping(target = "stock.sizeL", source = "size_L_stock")
    Product toDomainEntity(ProductJpaEntity jpaEntity);
}
