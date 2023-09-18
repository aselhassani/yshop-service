package com.yoozlab.yshop.adapter.ws.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoozlab.yshop.domain.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
@Schema(name = "ProductV1DTO", description = "Product")
public record ProductV1DTO(
    @JsonProperty("supplier_id")
    @NotBlank
    String supplierId,
    @JsonProperty("category_id")
    @NotBlank
    String categoryId,
    @JsonProperty("product_id")
    @NotBlank
    String productId,
    @JsonProperty("product_name")
    @NotBlank
    String productName,
    @JsonProperty("product_description")
    @NotBlank
    String productDescription,
    @PositiveOrZero
    @NotNull
    Double price,
    @JsonProperty("on_sale")
    @NotNull
    Boolean onSale
) {

  public static ProductV1DTO fromDomain(Product domain) {
    return ProductV1DTO.builder()
        .supplierId(domain.supplierId())
        .categoryId(domain.categoryId())
        .productId(domain.productId())
        .productName(domain.productName())
        .productDescription(domain.productDescription())
        .price(domain.price())
        .onSale(domain.onSale())
        .build();
  }

  public Product toDomain() {
    return Product.builder()
        .supplierId(supplierId)
        .categoryId(categoryId)
        .productId(productId)
        .productName(productName)
        .productDescription(productDescription)
        .price(price)
        .onSale(onSale)
        .build();
  }
}
