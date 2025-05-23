package com.pruebaItx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stock information for a product. Contains stock quantities for different sizes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Integer sizeS;
    private Integer sizeM;
    private Integer sizeL;

    public Integer getSizeS() {
        return sizeS;
    }

    public Integer getSizeM() {
        return sizeM;
    }

    public Integer getSizeL() {
        return sizeL;
    }
}
