package br.com.dsin.cabeleleila.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AppointmentUpdateRequest(
        Long serviceId,

        @Future(message = " Schedule date must be future")
        Instant scheduleAt,

        @Size(max = 500, message = "Appointment description cannot exceed 500 characters")
        String description
) {
}
