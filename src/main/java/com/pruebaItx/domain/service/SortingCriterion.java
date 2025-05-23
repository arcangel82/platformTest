package com.pruebaItx.domain.service;

import com.pruebaItx.domain.model.Product;

/**
 * Interface for sorting criteria.
 */
public interface SortingCriterion {
    /**
     * Calculate score for a product based on the criterion.
     *
     * @param product the product to score
     * @return the score
     */
    double calculateScore(Product product);
}
