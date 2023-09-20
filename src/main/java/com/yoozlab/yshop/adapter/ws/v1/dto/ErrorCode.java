package com.yoozlab.yshop.adapter.ws.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "400-01", "Validation error");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}