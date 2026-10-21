package br.com.rafaelpvs.device_service.controllers.errors;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import br.com.rafaelpvs.device_service.services.DeviceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
      MethodArgumentNotValidException exception,
      HttpServletRequest request) {

    List<FieldViolation> violations = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
        .toList();

    return badRequest("Request validation failed", request, violations);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorResponse> handleUnreadableMessage(
      HttpMessageNotReadableException exception,
      HttpServletRequest request) {

    return badRequest("Request body is missing or malformed", request, List.of());
  }

  @ExceptionHandler(DeviceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleDeviceNotFound(
      DeviceNotFoundException exception,
      HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiErrorResponse response = new ApiErrorResponse(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        exception.getMessage(),
        request.getRequestURI(),
        List.of());

    return ResponseEntity.status(status).body(response);
  }

  private ResponseEntity<ApiErrorResponse> badRequest(
      String message,
      HttpServletRequest request,
      List<FieldViolation> violations) {

    HttpStatus status = HttpStatus.BAD_REQUEST;
    ApiErrorResponse response = new ApiErrorResponse(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI(),
        violations);

    return ResponseEntity.status(status).body(response);
  }
}
