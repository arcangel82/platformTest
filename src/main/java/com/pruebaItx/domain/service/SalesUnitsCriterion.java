package com.pruebaItx.domain.service;

import com.pruebaItx.domain.model.Product;

/**
 * Sales units criterion - scores products based on number of units sold.
 */
public class SalesUnitsCriterion implements SortingCriterion {
    @Override
    public double calculateScore(Product product) {
        return product.getSalesUnits();
    }
}
