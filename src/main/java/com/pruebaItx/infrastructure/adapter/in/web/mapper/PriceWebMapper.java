package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.web.application.entities.PriceResponse;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between Price domain model and PriceResponse DTO. This mapper is
 * configured as a Spring component, allowing it to be injected into other Spring beans (e.g.,
 * controllers).
 */
@Mapper(componentModel = "spring")
public interface PriceWebMapper {

  /**
   * Maps a Price domain object to a PriceResponse DTO.
   *
   * @param price The Price domain object to map.
   * @return The mapped PriceResponse DTO.
   */
  PriceResponse toDto(Price price);
}
