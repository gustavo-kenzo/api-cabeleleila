package br.com.dsin.cabeleleila.dto.update;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AppointmentUpdate(
        Long serviceId,

        @Future(message = " Schedule date must be future")
        Instant scheduleAt,

        @Size(max = 500, message = "Appointment description cannot exceed 500 characters")
        String description
) {
}
