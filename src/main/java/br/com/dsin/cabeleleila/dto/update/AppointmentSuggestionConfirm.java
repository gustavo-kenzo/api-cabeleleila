package br.com.dsin.cabeleleila.dto.update;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AppointmentSuggestionConfirm(
        @NotNull(message = "NewDate is required")
        Instant newDate
) {
}
