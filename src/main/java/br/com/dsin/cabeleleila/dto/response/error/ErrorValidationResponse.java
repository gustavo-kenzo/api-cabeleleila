package br.com.dsin.cabeleleila.dto.response.error;

public record ErrorValidationResponse(String field, String message, int status) {
}
