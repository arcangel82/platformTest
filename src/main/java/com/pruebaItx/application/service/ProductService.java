package com.pruebaItx.application.service;

import com.pruebaItx.application.port.in.RetrieveSortedProductsUseCase;
import com.pruebaItx.application.port.out.LoadProductsPort;
import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.SortingCriteriaType;
import com.pruebaItx.domain.service.ProductSortingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Application service that implements the RetrieveSortedProductsUseCase. Orchestrates the flow of
 * data between the domain and the infrastructure layers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements RetrieveSortedProductsUseCase {

    private final LoadProductsPort loadProductsPort;
    private final ProductSortingService productSortingService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> retrieveSortedProducts(Integer saleFactor, Integer stockFactor) {
        log.info(
                "Requested the products, factors: saleFactor: {} stockFactor: {}", saleFactor, stockFactor);

        // Load all products from the repository
        List<Product> products = loadProductsPort.loadAllProducts();

        // Sort products using the domain service
        return productSortingService.sortProducts(products, saleFactor, stockFactor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> retrieveSortedProducts(Map<SortingCriteriaType, Integer> criteriaWeights) {
        log.info("Requested the products with criteria weights: {}", criteriaWeights);

        // Load all products from the repository
        List<Product> products = loadProductsPort.loadAllProducts();

        // Sort products using the domain service
        return productSortingService.sortProducts(products, criteriaWeights);
    }
}
