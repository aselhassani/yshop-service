package com.yoozlab.yshop.adapter.ws.v1.exception;

import com.yoozlab.yshop.adapter.ws.v1.dto.ErrorCode;
import com.yoozlab.yshop.adapter.ws.v1.dto.ErrorV1DTO;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.yoozlab.yshop.adapter.ws.v1"})
public class ApiV1ExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    String details = Optional.ofNullable(ex.getBindingResult().getFieldErrors())
        .orElse(Collections.emptyList()).stream()
        .map(e -> camelToSnake(e.getField()) + " : " + e.getDefaultMessage())
        .collect(Collectors.joining(","));

    return ResponseEntity.badRequest()
        .body(buildErrorResponse(ErrorCode.VALIDATION_ERROR, details));
  }

  private ErrorV1DTO buildErrorResponse(ErrorCode errorCode, String details) {
    return ErrorV1DTO.builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage() + ". " + details)
        .build();
  }

  public static String camelToSnake(String camelStr) {
    return IntStream.range(0, camelStr.length())
        .mapToObj(i -> {
          char c = camelStr.charAt(i);
          return Character.isUpperCase(c) ? ("_" + Character.toLowerCase(c)) : String.valueOf(c);
        })
        .collect(Collectors.joining(""));

  }
}
