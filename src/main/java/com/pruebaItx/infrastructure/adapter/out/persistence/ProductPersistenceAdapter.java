package com.pruebaItx.infrastructure.adapter.out.persistence;

import com.pruebaItx.application.port.out.LoadProductsPort;
import com.pruebaItx.domain.model.Product;
import com.pruebaItx.infrastructure.adapter.out.persistence.mapper.ProductPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for loading products from the database. Implements the LoadProductsPort interface.
 */
@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements LoadProductsPort {

    private final ProductJpaRepository productRepository;
    private final ProductPersistenceMapper productMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> loadAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDomainEntity)
                .collect(Collectors.toList());
    }
}
