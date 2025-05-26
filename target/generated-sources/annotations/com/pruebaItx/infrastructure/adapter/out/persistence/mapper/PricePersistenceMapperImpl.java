package com.pruebaItx.infrastructure.adapter.out.persistence.mapper;

import com.pruebaItx.domain.model.Price;
import com.pruebaItx.infrastructure.adapter.out.persistence.entity.PriceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T16:11:27+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
)
@Component
public class PricePersistenceMapperImpl implements PricePersistenceMapper {

    @Override
    public Price toDomainEntity(PriceEntity jpaEntity) {
        if ( jpaEntity == null ) {
            return null;
        }

        Price.Builder price = Price.builder();

        price.brandId( jpaEntity.getBrandId() );
        price.startDate( jpaEntity.getStartDate() );
        price.endDate( jpaEntity.getEndDate() );
        price.priceList( jpaEntity.getPriceList() );
        price.productId( jpaEntity.getProductId() );
        price.priority( jpaEntity.getPriority() );
        price.price( jpaEntity.getPrice() );
        price.currency( jpaEntity.getCurrency() );

        return price.build();
    }
}
