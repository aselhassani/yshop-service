package com.yoozlab.yshop.adapter.ws.v1.dto;

import lombok.Builder;

@Builder
public record ErrorV1DTO(
    String code,
    String message
) {

}
