package com.yoozlab.yshop.adapter.repository;

import com.yoozlab.yshop.adapter.repository.document.ProductDocument;
import com.yoozlab.yshop.adapter.repository.document.ProductMongoRepository;
import com.yoozlab.yshop.test.DocumentHelper;
import com.yoozlab.yshop.test.DomainHelper;
import com.yoozlab.yshop.test.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductMongoRepositoryTest {
    @InjectMocks
    private ProductMongoRepository underTest;
    @Mock
    private MongoTemplate mongoTemplate;

    private final String COLLECTION_NAME_PREFIX = "product_";
    private final String CATEGORY_ID_INDEX = "category_id_index";

    private String supplierId;

    @BeforeEach
    void setup() {
        supplierId = TestHelper.getRandomId("suppl");
    }

    @Test
    void testCreateCollection() {
        var collectionName = COLLECTION_NAME_PREFIX + supplierId;
        var indexOps = Mockito.mock(IndexOperations.class);

        when(mongoTemplate.indexOps(anyString())).thenReturn(indexOps);

        underTest.createCollection(supplierId);

        verify(mongoTemplate).createCollection(collectionName);

        var captor = ArgumentCaptor.forClass(Index.class);

        verify(indexOps).ensureIndex(captor.capture());

        var createdIndex = captor.getValue();

        assertThat(createdIndex)
                .usingRecursiveComparison()
                .isEqualTo(new Index().named(CATEGORY_ID_INDEX).on(CATEGORY_ID_INDEX, Sort.Direction.ASC));
    }

    @Test
    void testSave() {
        var product = DomainHelper.randomProduct();
        var oDocument = DocumentHelper.randomProductDocument();
        var collectionName = COLLECTION_NAME_PREFIX + product.supplierId();

        when(mongoTemplate.save(any(), any())).thenReturn(oDocument);

        var result = underTest.save(product);

        var captor = ArgumentCaptor.forClass(ProductDocument.class);
        verify(mongoTemplate).save(captor.capture(), eq(collectionName));
        var iDocument = captor.getValue();

        assertThat(iDocument)
                .usingRecursiveComparison()
                .isEqualTo(product);

        assertThat(result).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(oDocument);

    }

}
