package com.yoozlab.yshop.adapter.repository.document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supplier")
public record SupplierDocument(
        String supplierId,
        String supplierName
) {
}
