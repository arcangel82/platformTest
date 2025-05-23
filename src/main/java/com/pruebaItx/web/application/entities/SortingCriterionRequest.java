package com.pruebaItx.web.application.entities;

import lombok.Data;

import java.util.List;

/**
 * Request with sorting criteria
 */
@Data
public class SortingCriterionRequest {
    /**
     * List of criteria with weights
     */
    private List<CriterionWeight> criteria;
}
