package com.yoozlab.yshop.test;

import static com.yoozlab.yshop.test.TestHelper.getRandomId;

import com.yoozlab.yshop.adapter.repository.document.ProductDocument;
import java.util.Random;

public class DocumentHelper {

    private static final Random random = new Random();

    public static ProductDocument randomProductDocument(String supplierId) {
        return ProductDocument.builder()
            .supplierId(supplierId)
            .categoryId(getRandomId("categ"))
            .productId(getRandomId("prod"))
            .productName(TestHelper.getRandomText(20))
            .productDescription(TestHelper.getRandomText(30))
            .price(random.nextDouble(500))
            .onSale(random.nextBoolean())
            .build();
    }
}
