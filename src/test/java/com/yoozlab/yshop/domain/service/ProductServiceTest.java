package com.yoozlab.yshop.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yoozlab.yshop.adapter.repository.ProductMongoRepository;
import com.yoozlab.yshop.domain.model.Product;
import com.yoozlab.yshop.test.DomainHelper;
import com.yoozlab.yshop.test.TestHelper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @InjectMocks
  private ProductService underTest;

  @Mock
  private ProductMongoRepository productRepository;

  private Product product;

  @BeforeEach
  void setup() {
    product = DomainHelper.randomProduct(TestHelper.getRandomId("suppl"));
  }

  @Test
  void testSave() {

    when(productRepository.save(any())).thenReturn(Optional.of(product));

    var result = underTest.save(product);

    verify(productRepository).save(product);

    assertThat(result).contains(product);
  }



}