package com.aimnode.product_service.service;

import com.aimnode.product_service.dto.ProductRequest;
import com.aimnode.product_service.dto.ProductResponse;
import com.aimnode.product_service.model.Product;
import com.aimnode.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductResponse response = new ProductResponse();
                    response.setId(product.getId());
                    response.setName(product.getName());
                    response.setDescription(product.getDescription());
                    response.setPrice(product.getPrice());
                    return response;
                })
                .toList();
    }
}
