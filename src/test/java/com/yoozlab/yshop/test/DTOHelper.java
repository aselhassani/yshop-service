package com.yoozlab.yshop.test;

import static com.yoozlab.yshop.test.TestHelper.getRandomId;
import static com.yoozlab.yshop.test.TestHelper.getRandomText;

import com.yoozlab.yshop.adapter.ws.v1.dto.ProductV1DTO;
import java.util.Random;

public class DTOHelper {

    private static final Random random = new Random();

    public static final ProductV1DTO randomProductV1DTO() {
        return ProductV1DTO.builder()
            .supplierId(getRandomId("suppl"))
            .categoryId(getRandomId("categ"))
            .productId(getRandomId("prod"))
            .productName(getRandomText(10))
            .productDescription(getRandomText(20))
            .price(random.nextDouble(100))
            .onSale(random.nextBoolean())
            .build();
    }
}
