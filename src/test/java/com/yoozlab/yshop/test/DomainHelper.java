package com.yoozlab.yshop.test;

import static com.yoozlab.yshop.test.TestHelper.getRandomId;

import com.yoozlab.yshop.domain.model.Product;
import java.util.Random;

public class DomainHelper {

    private static final Random random = new Random();

    public static Product randomProduct(String supplierId) {
        return Product.builder()
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
