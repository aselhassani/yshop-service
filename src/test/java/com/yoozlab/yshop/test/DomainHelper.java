package com.yoozlab.yshop.test;

import com.yoozlab.yshop.domain.model.Product;

import java.util.Random;

import static com.yoozlab.yshop.test.TestHelper.getRandomId;

public class DomainHelper {

    private static final Random random = new Random();

    public static Product randomProduct() {
        return Product.builder()
                .supplierId(getRandomId("suppl"))
                .categoryId(getRandomId("categ"))
                .productId(getRandomId("prod"))
                .productName(TestHelper.getRandomText(20))
                .productDescription(TestHelper.getRandomText(30))
                .price(random.nextDouble(500))
                .onSale(random.nextBoolean())
                .build();
    }
}
