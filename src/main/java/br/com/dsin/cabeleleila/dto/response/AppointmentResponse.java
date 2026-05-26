package br.com.dsin.cabeleleila.dto.response;

import br.com.dsin.cabeleleila.domain.ScheduleStatus;

import java.time.Instant;

public record AppointmentResponse(
        Long id,
        Long clientId,
        Long serviceId,
        Instant createdAt,
        Instant scheduleAt,
        String description,
        ScheduleStatus status,
        Instant suggestion
) {
}
