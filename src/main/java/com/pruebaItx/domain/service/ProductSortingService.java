package com.pruebaItx.domain.service;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.SortingCriteriaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Domain service for sorting products based on various criteria.
 */
@Service
public class ProductSortingService {

    /**
     * Sorts products based on a weighted combination of criteria.
     *
     * @param products        the list of products to sort
     * @param criteriaWeights map of criteria types and their weights
     * @return the sorted list of products
     */
    public List<Product> sortProducts(
            List<Product> products, Map<SortingCriteriaType, Integer> criteriaWeights) {
        // Create a map of criteria and their weights
        Map<SortingCriterion, Integer> criteriaWithWeights = new HashMap<>();

        // Convert criteria types to actual criterion implementations
        for (Map.Entry<SortingCriteriaType, Integer> entry : criteriaWeights.entrySet()) {
            SortingCriteriaType criteriaType = entry.getKey();
            Integer weight = entry.getValue();

            SortingCriterion criterion = getCriterionForType(criteriaType);
            criteriaWithWeights.put(criterion, weight);
        }

        // Calculate weighted scores and sort products
        return products.stream()
                .sorted(
                        (p1, p2) -> {
                            // Calculate weighted score for each product
                            double score1 = calculateWeightedScore(p1, criteriaWithWeights);
                            double score2 = calculateWeightedScore(p2, criteriaWithWeights);

                            // Sort in descending order (higher score first)
                            return Double.compare(score2, score1);
                        })
                .collect(Collectors.toList());
    }

    /**
     * Calculate weighted score for a product based on multiple criteria.
     *
     * @param product             the product to score
     * @param criteriaWithWeights map of sorting criteria with their corresponding weights
     * @return the weighted score
     */
    private double calculateWeightedScore(
            Product product, Map<SortingCriterion, Integer> criteriaWithWeights) {

        double weightedScore = 0.0;

        // Calculate weighted sum by iterating through all criteria
        for (Map.Entry<SortingCriterion, Integer> entry : criteriaWithWeights.entrySet()) {
            SortingCriterion criterion = entry.getKey();
            Integer weight = entry.getValue();

            double score = criterion.calculateScore(product);
            weightedScore += score * weight;
        }

        return weightedScore;
    }

    /**
     * Returns the appropriate SortingCriterion implementation for the given criteria type.
     *
     * @param criteriaType the type of sorting criteria
     * @return the corresponding SortingCriterion implementation
     * @throws IllegalArgumentException if the criteria type is not supported
     */
    private SortingCriterion getCriterionForType(SortingCriteriaType criteriaType) {
        switch (criteriaType) {
            case SALE_FACTOR:
                return new SalesUnitsCriterion();
            case STOCK_FACTOR:
                return new StockRatioCriterion();
            default:
                throw new IllegalArgumentException("Unsupported criteria type: " + criteriaType);
        }
    }

    /**
     * Sorts products based on a weighted combination of sales and stock criteria. This method is kept
     * for backward compatibility.
     *
     * @param products    the list of products to sort
     * @param saleFactor  weight for sales criterion
     * @param stockFactor weight for stock criterion
     * @return the sorted list of products
     */
    public List<Product> sortProducts(
            List<Product> products, Integer saleFactor, Integer stockFactor) {
        Map<SortingCriteriaType, Integer> criteriaWeights = new HashMap<>();
        criteriaWeights.put(SortingCriteriaType.SALE_FACTOR, saleFactor);
        criteriaWeights.put(SortingCriteriaType.STOCK_FACTOR, stockFactor);

        return sortProducts(products, criteriaWeights);
    }
}
