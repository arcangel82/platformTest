package com.pruebaItx.domain.model;

/**
 * Enum defining the types of sorting criteria available for product sorting. This enum allows for
 * easy extension with additional criteria types in the future.
 */
public enum SortingCriteriaType {
    SALE_FACTOR("salesUnits", "Sales Units", "Scores products based on number of units sold"),
    STOCK_FACTOR("stockRatio", "Stock Ratio", "Scores products based on available sizes with stock.");

    private final String id;
    private final String name;
    private final String description;

    SortingCriteriaType(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
