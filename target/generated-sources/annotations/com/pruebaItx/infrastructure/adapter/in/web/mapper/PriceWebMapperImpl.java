package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.web.application.entities.PriceResponse;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T17:59:38+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
)
@Component
public class PriceWebMapperImpl implements PriceWebMapper {

    @Override
    public PriceResponse toPriceResponse(Price price) {
        if ( price == null ) {
            return null;
        }

        PriceResponse priceResponse = new PriceResponse();

        if ( price.getStartDate() != null ) {
            priceResponse.setStartDate( LocalDateTime.parse( localDateTimeToString( price.getStartDate() ) ) );
        }
        if ( price.getEndDate() != null ) {
            priceResponse.setEndDate( LocalDateTime.parse( localDateTimeToString( price.getEndDate() ) ) );
        }
        priceResponse.setProductId( price.getProductId() );
        priceResponse.setBrandId( price.getBrandId() );
        priceResponse.setPriceList( price.getPriceList() );
        if ( price.getPrice() != null ) {
            priceResponse.setPrice( price.getPrice().doubleValue() );
        }
        priceResponse.setCurrency( price.getCurrency() );

        return priceResponse;
    }
}
