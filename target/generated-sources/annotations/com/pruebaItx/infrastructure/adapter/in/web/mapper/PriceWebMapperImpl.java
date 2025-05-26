package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.web.application.entities.PriceResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T16:11:27+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
)
@Component
public class PriceWebMapperImpl implements PriceWebMapper {

    @Override
    public PriceResponse toDto(Price price) {
        if ( price == null ) {
            return null;
        }

        PriceResponse priceResponse = new PriceResponse();

        priceResponse.setProductId( price.getProductId() );
        priceResponse.setBrandId( price.getBrandId() );
        priceResponse.setPriceList( price.getPriceList() );
        priceResponse.setStartDate( price.getStartDate() );
        priceResponse.setEndDate( price.getEndDate() );
        if ( price.getPrice() != null ) {
            priceResponse.setPrice( price.getPrice().doubleValue() );
        }

        return priceResponse;
    }
}
