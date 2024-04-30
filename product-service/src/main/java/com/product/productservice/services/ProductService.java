package com.product.productservice.services;

import com.product.productservice.dto.ProductRequest;
import com.product.productservice.dto.ProductResponse;
import com.product.productservice.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProductDetails();

}
