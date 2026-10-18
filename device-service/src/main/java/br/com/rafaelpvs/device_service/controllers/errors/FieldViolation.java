package br.com.rafaelpvs.device_service.controllers.errors;

public record FieldViolation(
    String field,
    String message) {
}
