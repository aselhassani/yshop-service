package com.yoozlab.yshop.adapter.repository.document;


import com.yoozlab.yshop.domain.Product;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
public record ProductDocument(
        String supplierId,
        String categoryId,
        @Id
        String productId,
        String productName,
        String productDescription,
        Double price,
        Boolean onSale
) {

        public static ProductDocument fromDomain(Product domain) {
                return ProductDocument.builder()
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
