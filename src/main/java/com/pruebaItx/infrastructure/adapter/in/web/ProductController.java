package com.pruebaItx.infrastructure.adapter.in.web;

import com.pruebaItx.application.port.in.RetrieveSortedProductsUseCase;
import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.SortingCriteriaType;
import com.pruebaItx.infrastructure.adapter.in.web.mapper.ProductWebMapper;
import com.pruebaItx.web.application.entities.*;
import com.pruebaItx.web.application.interfaces.ProductApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

/**
 * Web adapter for the product API. Implements the ProductApi interface generated from the OpenAPI
 * specification.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final RetrieveSortedProductsUseCase retrieveSortedProductsUseCase;
    private final ProductWebMapper productWebMapper;

    /**
     * New endpoint that accepts criteria in the request body. This is the implementation of the new
     * API specification.
     */
    @org.springframework.web.bind.annotation.PostMapping("/product")
    public ResponseEntity<ProductsSorted200Response> productsSortedWithBody(
            @RequestBody SortingCriteriaRequest request) {
        log.debug("Request to Products Sorted with criteria in body: {}", request);

        // Process criteria from the request body
        Map<SortingCriteriaType, Integer> criteriaWeights = processCriteriaFromRequest(request);

        // Retrieve sorted products from the use case
        List<Product> sortedProducts =
                retrieveSortedProductsUseCase.retrieveSortedProducts(criteriaWeights);

        // Map domain entities to DTOs and return the response
        return createProductsResponse(sortedProducts);
    }

    /**
     * Processes criteria from the request and converts them to a map of SortingCriteriaType to
     * weight.
     *
     * @param request The sorting criteria request
     * @return A map of SortingCriteriaType to weight
     */
    private Map<SortingCriteriaType, Integer> processCriteriaFromRequest(
            SortingCriteriaRequest request) {
        if (request == null || request.getCriteria() == null) {
            return new HashMap<>();
        }

        return request.getCriteria().stream()
                .filter(this::isValidCriteriaWeight)
                .flatMap(this::mapToSortingCriteriaEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Checks if a criteria weight is valid.
     *
     * @param criteriaWeight The criteria weight to check
     * @return True if the criteria weight is valid, false otherwise
     */
    private boolean isValidCriteriaWeight(CriteriaWeight criteriaWeight) {
        return criteriaWeight.getId() != null
                && criteriaWeight.getWeight() != null
                && criteriaWeight.getWeight() > 0;
    }

    /**
     * Maps a criteria weight to a stream of Map.Entry with SortingCriteriaType as key and weight as
     * value.
     *
     * @param criteriaWeight The criteria weight to map
     * @return A stream of Map.Entry
     */
    private Stream<Map.Entry<SortingCriteriaType, Integer>> mapToSortingCriteriaEntry(
            CriteriaWeight criteriaWeight) {
        return stream(SortingCriteriaType.values())
                .filter(criteriaType -> criteriaType.getId().equals(criteriaWeight.getId()))
                .map(
                        criteriaType ->
                                new java.util.AbstractMap.SimpleEntry<>(criteriaType, criteriaWeight.getWeight()));
    }

    /**
     * Creates a response with the sorted products.
     *
     * @param sortedProducts The sorted products
     * @return A ResponseEntity with the sorted products
     */
    private ResponseEntity<ProductsSorted200Response> createProductsResponse(
            List<Product> sortedProducts) {
        List<Article> articleDtos =
                sortedProducts.stream().map(productWebMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ProductsSorted200Response().data(articleDtos));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ProductsSorted200Response> productsSorted(
            Integer saleFactor, Integer stockFactor) {
        log.debug(
                "Request to Products Sorted with headers: saleFactor={}, stockFactor={}",
                saleFactor,
                stockFactor);

        // Use the backward compatibility method directly with saleFactor and stockFactor
        List<Product> sortedProducts =
                retrieveSortedProductsUseCase.retrieveSortedProducts(saleFactor, stockFactor);

        // Map domain entities to DTOs
        List<Article> articleDtos =
                sortedProducts.stream().map(productWebMapper::toDto).collect(Collectors.toList());

        // Create and return the response
        return ResponseEntity.ok(new ProductsSorted200Response().data(articleDtos));
    }

    /**
     * GET /product/criteria : Available Sorting Criteria.
     *
     * @return Success (status code 200)
     */
    @GetMapping("/product/criteria")
    public ResponseEntity<GetAvailableCriteria200Response> getAvailableCriteria() {
        log.debug("Request to get available criteria");

        // Create the list of available criteria
        List<Criterion> criteria = new ArrayList<>();

        // Add criteria from the SortingCriteriaType enum
        for (SortingCriteriaType criteriaType : SortingCriteriaType.values()) {
            Criterion criterion =
                    new Criterion()
                            .id(criteriaType.getId())
                            .name(criteriaType.getName())
                            .description(criteriaType.getDescription());
            criteria.add(criterion);
        }

        // Create and return the response
        return ResponseEntity.ok(new GetAvailableCriteria200Response().data(criteria));
    }
}
