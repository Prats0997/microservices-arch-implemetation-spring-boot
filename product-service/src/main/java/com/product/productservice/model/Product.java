package com.product.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String productName;
    private String productDesc;
    private BigDecimal price;
}
