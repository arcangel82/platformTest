package com.pruebaItx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product domain entity. Represents a product in the system with its sales and stock information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private Integer salesUnits;
    private Stock stock;

    /**
     * Calculates the number of sizes that have stock.
     *
     * @return The number of sizes with stock (0-3)
     */
    public int countSizesWithStock() {
        int count = 0;
        if (stock != null) {
            if (stock.getSizeS() != null && stock.getSizeS() > 0) count++;
            if (stock.getSizeM() != null && stock.getSizeM() > 0) count++;
            if (stock.getSizeL() != null && stock.getSizeL() > 0) count++;
        }
        return count;
    }
}
