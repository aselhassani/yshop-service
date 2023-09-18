package com.yoozlab.yshop.domain;

import lombok.Builder;

@Builder
public record Product(
        String supplierId,
        String categoryId,
        String productId,
        String productName,
        String productDescription,
        Double price,
        Boolean onSale
) {

}
