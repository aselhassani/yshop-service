package com.yoozlab.yshop.adapter.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yoozlab.yshop.adapter.repository.document.ProductDocument;
import com.yoozlab.yshop.domain.model.Product;
import com.yoozlab.yshop.test.DocumentHelper;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

@ExtendWith(MockitoExtension.class)
public class ProductMongoRepositoryTest {

  @InjectMocks
  private ProductMongoRepository underTest;
  @Mock
  private MongoTemplate mongoTemplate;
  public static final String CATEGORY_ID_INDEX = "category_id_index";
  private final String COLLECTION_NAME_PREFIX = "products_";
  private String supplierId;
  private Product product;
  private ProductDocument productDocument;
  private String collectionName;

  @BeforeEach
  void setup() {
    supplierId = TestHelper.getRandomId("suppl");
    product = DomainHelper.randomProduct(supplierId);
    productDocument = DocumentHelper.randomProductDocument(supplierId);
    collectionName = COLLECTION_NAME_PREFIX + supplierId;
  }

  @Test
  void testSaveForExistingCollection() {

    when(mongoTemplate.collectionExists(anyString())).thenReturn(true);
    when(mongoTemplate.save(any(), any())).thenReturn(productDocument);

    var result = underTest.save(product);

    verify(mongoTemplate).collectionExists(collectionName);
    verify(mongoTemplate, never()).createCollection(anyString());
    verify(mongoTemplate, never()).indexOps(anyString());

    var captor = ArgumentCaptor.forClass(ProductDocument.class);
    verify(mongoTemplate).save(captor.capture(), eq(collectionName));
    var iDocument = captor.getValue();

    assertProductDomainToDtoMapping(product, iDocument);

    assertProductDocumentToDomainMapping(productDocument, result);
  }

  @Test
  void testSaveForNonExistingCollection() {

    var indexOps = mock(IndexOperations.class);

    when(mongoTemplate.collectionExists(anyString())).thenReturn(false);
    when(mongoTemplate.indexOps(anyString())).thenReturn(indexOps);
    when(mongoTemplate.save(any(), any())).thenReturn(productDocument);

    var result = underTest.save(product);

    verify(mongoTemplate).collectionExists(collectionName);

    var indexCaptor = ArgumentCaptor.forClass(Index.class);
    verify(indexOps).ensureIndex(indexCaptor.capture());
    var createdIndex = indexCaptor.getValue();

    assertThat(createdIndex)
        .usingRecursiveComparison()
        .isEqualTo(
            new Index().named(CATEGORY_ID_INDEX).on(CATEGORY_ID_INDEX, Sort.Direction.ASC));

    var productCaptor = ArgumentCaptor.forClass(ProductDocument.class);
    verify(mongoTemplate).save(productCaptor.capture(), eq(collectionName));
    var iDocument = productCaptor.getValue();

    assertProductDomainToDtoMapping(product, iDocument);

    assertProductDocumentToDomainMapping(productDocument, result);
  }

  private static void assertProductDocumentToDomainMapping(ProductDocument oDocument,
      Optional<Product> result) {
    assertThat(result).isPresent()
        .get()
        .usingRecursiveComparison()
        .ignoringFields("updatedAt")
        .isEqualTo(oDocument);
  }

  private static void assertProductDomainToDtoMapping(Product product, ProductDocument iDocument) {
    assertThat(iDocument)
        .usingRecursiveComparison()
        .ignoringFields("updatedAt")
        .isEqualTo(product);
  }

}
