package com.pruebaItx.domain.service;

import com.pruebaItx.domain.model.Product;

/**
 * Stock ratio criterion - scores products based on available sizes with stock.
 */
public class StockRatioCriterion implements SortingCriterion {
    @Override
    public double calculateScore(Product product) {
        return product.countSizesWithStock();
    }
}
