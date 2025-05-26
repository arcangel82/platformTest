package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.web.application.entities.PriceResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct mapper for converting between Price domain model and PriceResponse DTO. This mapper is
 * configured as a Spring component, allowing it to be injected into other Spring beans (e.g.,
 * controllers).
 */
@Mapper(componentModel = "spring")
public interface PriceWebMapper {

  DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateTimeToString")
  @Mapping(target = "endDate", source = "endDate", qualifiedByName = "localDateTimeToString")
  PriceResponse toPriceResponse(Price price);

  @Named("localDateTimeToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    return dateTime != null ? dateTime.format(FORMATTER) : null;
  }

  @Named("stringToLocalDateTime")
  default LocalDateTime stringToLocalDateTime(String dateTimeString) {
    return dateTimeString != null ? LocalDateTime.parse(dateTimeString, FORMATTER) : null;
  }
}
