package com.pruebaItx.web.application.entities;

import lombok.Data;

/**
 * Sorting Criterion with Weight
 */
@Data
public class CriterionWeight {
    /**
     * Criterion identifier
     */
    private String id;

    /**
     * Weight for this criterion
     */
    private Integer weight;
}
