package com.yoozlab.yshop.port.repository;

import com.yoozlab.yshop.domain.model.Product;
import java.util.Optional;

public interface ProductRepository {

  Optional<Product> save(Product product);

}
