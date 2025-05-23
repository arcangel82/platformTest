package com.pruebaItx.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for the Product. Maps to the PRODUCT table in the database.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "PRODUCT")
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "sales_unit")
    private Integer sales_unit;

    @Column(name = "size_S_stock")
    private Integer size_S_stock;

    @Column(name = "size_M_stock")
    private Integer size_M_stock;

    @Column(name = "size_L_stock")
    private Integer size_L_stock;
}
