package com.yoozlab.yshop.adapter.ws.v1;

import com.yoozlab.yshop.adapter.ws.v1.dto.ProductV1DTO;
import com.yoozlab.yshop.config.openapi.ApiVersion;
import com.yoozlab.yshop.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/inventory", headers = "accept-version=" + ApiVersion.V1)
@RequiredArgsConstructor
@Validated
public class InventoryControllerV1 {

    private final ProductService productService;

    @Operation(tags = "Product", summary = "Create a product", description = "Create a product")
    @Parameter(name = "accept-version", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = ApiVersion.V1), required = true)
    @PostMapping("/products")
    public ResponseEntity<ProductV1DTO> createProduct(
        @RequestBody @NotNull @Valid ProductV1DTO productV1DTO) {

        return productService.save(productV1DTO.toDomain())
            .map(ProductV1DTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(null);
    }
}
