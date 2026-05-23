package br.com.dsin.cabeleleila.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AppointmentSuggestionConfirmRequest(
        @NotNull(message = "NewDate is required")
        Instant newDate
) {
}
