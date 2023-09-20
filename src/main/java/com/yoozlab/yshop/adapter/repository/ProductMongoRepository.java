package com.yoozlab.yshop.adapter.repository;

import com.yoozlab.yshop.adapter.repository.document.ProductDocument;
import com.yoozlab.yshop.domain.model.Product;
import com.yoozlab.yshop.port.repository.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProductMongoRepository implements ProductRepository {

    private final MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME_PREFIX = "product_";
    private static final String CATEGORY_ID_INDEX = "category_id_index";

    private void createCollectionIfNotExist(String collectionName) {
        if (!mongoTemplate.collectionExists(collectionName)) {
            createCollection(collectionName);
        }
    }

    private void createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
        mongoTemplate.indexOps(collectionName).ensureIndex(
            new Index().named(CATEGORY_ID_INDEX).on(CATEGORY_ID_INDEX, Sort.Direction.ASC));
        log.info("New collection created: {}", collectionName);
    }


    private static final String getCollectionName(String supplierId) {
        return COLLECTION_NAME_PREFIX + supplierId;
    }

    public Optional<Product> save(Product product) {

        String collectionName = getCollectionName(product.supplierId());

        createCollectionIfNotExist(collectionName);

        return Optional.of(product)
            .map(ProductDocument::fromDomain)
            .map(doc -> mongoTemplate.save(doc, collectionName))
            .map(ProductDocument::toDomain);
    }


}
