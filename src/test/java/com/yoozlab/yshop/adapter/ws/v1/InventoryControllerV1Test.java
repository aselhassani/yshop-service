package com.yoozlab.yshop.adapter.ws.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yoozlab.yshop.adapter.ws.v1.dto.ProductV1DTO;
import com.yoozlab.yshop.domain.model.Product;
import com.yoozlab.yshop.domain.service.ProductService;
import com.yoozlab.yshop.test.DTOHelper;
import com.yoozlab.yshop.test.DomainHelper;
import com.yoozlab.yshop.test.TestHelper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerV1Test {

    @InjectMocks
    private InventoryControllerV1 underTest;
    @Mock
    private ProductService productService;
    private ProductV1DTO productV1DTO;
    private Product product;
    private String supplierId;

    @BeforeEach
    void setup() {
        supplierId = TestHelper.getRandomId("suppl");
        productV1DTO = DTOHelper.randomProductV1DTO();
        product = DomainHelper.randomProduct(supplierId);
    }

    @Test
    void testCreateProduct() {

        when(productService.save(any())).thenReturn(Optional.of(product));

        var result = underTest.createProduct(productV1DTO);

        var captor = ArgumentCaptor.forClass(Product.class);

        verify(productService).save(captor.capture());

        var iProduct = captor.getValue();

        assertThat(iProduct).usingRecursiveComparison()
            .isEqualTo(productV1DTO);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveComparison()
            .isEqualTo(product);
    }
}
