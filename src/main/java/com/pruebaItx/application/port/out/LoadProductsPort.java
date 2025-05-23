package com.pruebaItx.application.port.out;

import com.pruebaItx.domain.model.Product;

import java.util.List;

/**
 * Output port for loading products from a repository.
 */
public interface LoadProductsPort {

    /**
     * Loads all products from the repository.
     *
     * @return the list of all products
     */
    List<Product> loadAllProducts();
}
