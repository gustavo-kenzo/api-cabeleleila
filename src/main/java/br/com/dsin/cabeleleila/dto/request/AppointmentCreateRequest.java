package br.com.dsin.cabeleleila.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AppointmentCreateRequest(
        @NotNull(message = "Client id for appointment is required")
        Long clientId,

        @NotNull(message = "Service id for appointment is required")
        Long serviceId,

        @NotNull(message = "Schedule field is required")
        @Future(message = " Schedule date must be future")
        Instant scheduleAt,

        @Size(max = 500, message = "Appointment description cannot exceed 500 characters")
        String description
) {
}
