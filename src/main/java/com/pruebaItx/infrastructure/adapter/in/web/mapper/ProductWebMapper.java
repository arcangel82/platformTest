package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.web.application.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper for converting between Product domain entity and Article DTO. */
@Mapper(componentModel = "spring")
public interface ProductWebMapper {

  /**
   * Maps a domain entity to a DTO.
   *
   * @param product the domain entity
   * @return the DTO
   */
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "sales", source = "salesUnits")
  @Mapping(target = "stock.sizeS", source = "stock.sizeS")
  @Mapping(target = "stock.sizeM", source = "stock.sizeM")
  @Mapping(target = "stock.sizeL", source = "stock.sizeL")
  Article toDto(Product product);
}
