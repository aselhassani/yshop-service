package com.yoozlab.yshop.adapter.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supplier")
public record SupplierDocument(
        @Id
        String supplierId,
        String supplierName
) {
}
