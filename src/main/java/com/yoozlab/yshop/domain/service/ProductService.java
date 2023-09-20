package com.yoozlab.yshop.domain.service;

import com.yoozlab.yshop.adapter.repository.ProductMongoRepository;
import com.yoozlab.yshop.domain.model.Product;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMongoRepository productRepository;

    public Optional<Product> save(Product product) {
        return productRepository.save(product);
    }
}
