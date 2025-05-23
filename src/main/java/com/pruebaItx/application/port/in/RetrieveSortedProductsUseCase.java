package com.pruebaItx.application.port.in;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.SortingCriteriaType;

import java.util.List;
import java.util.Map;

/**
 * Input port for retrieving sorted products. This is the primary use case of the application.
 */
public interface RetrieveSortedProductsUseCase {

    /**
     * Retrieves products sorted by a weighted combination of sales and stock criteria. This method is
     * kept for backward compatibility.
     *
     * @param saleFactor  weight for sales criterion
     * @param stockFactor weight for stock criterion
     * @return the sorted list of products
     */
    List<Product> retrieveSortedProducts(Integer saleFactor, Integer stockFactor);

    /**
     * Retrieves products sorted by a weighted combination of criteria.
     *
     * @param criteriaWeights map of criteria types and their weights
     * @return the sorted list of products
     */
    List<Product> retrieveSortedProducts(Map<SortingCriteriaType, Integer> criteriaWeights);
}
