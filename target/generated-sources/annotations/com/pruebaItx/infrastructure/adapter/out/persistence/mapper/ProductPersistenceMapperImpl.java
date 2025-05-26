package com.pruebaItx.infrastructure.adapter.out.persistence.mapper;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.infrastructure.adapter.out.persistence.ProductJpaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T09:58:23+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
)
@Component
public class ProductPersistenceMapperImpl implements ProductPersistenceMapper {

    @Override
    public Product toDomainEntity(ProductJpaEntity jpaEntity) {
        if ( jpaEntity == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.stock( productJpaEntityToStock( jpaEntity ) );
        product.id( jpaEntity.getProduct_id() );
        product.name( jpaEntity.getProduct_name() );
        product.salesUnits( jpaEntity.getSales_unit() );

        return product.build();
    }

    protected Stock productJpaEntityToStock(ProductJpaEntity productJpaEntity) {
        if ( productJpaEntity == null ) {
            return null;
        }

        Stock.StockBuilder stock = Stock.builder();

        stock.sizeS( productJpaEntity.getSize_S_stock() );
        stock.sizeM( productJpaEntity.getSize_M_stock() );
        stock.sizeL( productJpaEntity.getSize_L_stock() );

        return stock.build();
    }
}
