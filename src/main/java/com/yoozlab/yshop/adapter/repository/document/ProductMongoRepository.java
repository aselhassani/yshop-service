package com.yoozlab.yshop.adapter.repository.document;

import com.yoozlab.yshop.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProductMongoRepository {
    private static final String COLLECTION_NAME_PREFIX = "product_";
    private static final String CATEGORY_ID_INDEX = "category_id_index";
    private final MongoTemplate mongoTemplate;

    public void createCollection(String supplierId) {
        var collectionName = getCollectionName(supplierId);
        mongoTemplate.createCollection(collectionName);
        mongoTemplate.indexOps(collectionName).ensureIndex(new Index().named(CATEGORY_ID_INDEX).on(CATEGORY_ID_INDEX, Sort.Direction.ASC));
        log.info("New collection created: {}", collectionName);
    }

    public Optional<Product> save(Product product) {
        return Optional.of(product)
                .map(ProductDocument::fromDomain)
                .map(doc -> mongoTemplate.save(doc, getCollectionName(doc.supplierId())))
                .map(ProductDocument::toDomain);
    }

    private static final String getCollectionName(String supplierId) {
        return COLLECTION_NAME_PREFIX + supplierId;
    }
}
