package com.yoozlab.yshop.adapter.repository.document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_category")
public record ProductCategoryDocument(
        String categoryId,
        String categoryName
) {
}
