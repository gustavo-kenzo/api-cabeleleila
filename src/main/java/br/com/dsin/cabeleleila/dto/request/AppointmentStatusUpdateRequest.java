package br.com.dsin.cabeleleila.dto.request;

import br.com.dsin.cabeleleila.domain.ScheduleStatus;
import jakarta.validation.constraints.NotNull;

public record AppointmentStatusUpdateRequest(
        @NotNull(message = "Schedule Status is required")
        ScheduleStatus status
) {
}
