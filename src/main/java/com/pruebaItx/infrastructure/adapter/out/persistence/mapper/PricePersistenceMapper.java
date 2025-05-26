package com.pruebaItx.infrastructure.adapter.out.persistence.mapper;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;

/** Mapper for converting between PriceJpaEntity and Price domain entity. */
@Mapper(componentModel = "spring")
public interface PricePersistenceMapper {

  /**
   * Maps a JPA entity to a domain entity.
   *
   * @param jpaEntity the JPA entity
   * @return the domain entity
   */
  Price toDomainEntity(PriceEntity jpaEntity);
}
