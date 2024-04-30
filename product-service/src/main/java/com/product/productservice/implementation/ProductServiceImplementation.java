package com.product.productservice.implementation;

import com.product.productservice.dto.ProductRequest;
import com.product.productservice.dto.ProductResponse;
import com.product.productservice.model.Product;
import com.product.productservice.repository.ProductRepository;
import com.product.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j //helps to add logs
public class ProductServiceImplementation implements ProductService {


    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();

        product.setProductName(productRequest.getProductName());
        product.setProductDesc(productRequest.getProductDesc());
        product.setPrice(productRequest.getPrice());
        log.info("Product added successfully");

        return productRepository.save(product);

    }

    @Override
    public List<ProductResponse> getAllProductDetails() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();

        productResponse.setProductId(product.getId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductDesc(product.getProductDesc());
        productResponse.setPrice(product.getPrice());

        return productResponse;
    }
}
