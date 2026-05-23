package br.com.dsin.cabeleleila.mapper;

import br.com.dsin.cabeleleila.domain.Appointment;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment entity) {
        return new AppointmentResponse(
                entity.getId(),
                entity.getClient().getId(),
                entity.getService().getId(),
                entity.getCreatedAt(),
                entity.getScheduleAt(),
                entity.getDescription(),
                entity.getStatus(),
                null
        );
    }

    public AppointmentResponse toResponseWithSuggestion(Appointment entity, Instant suggestion) {
        return new AppointmentResponse(
                entity.getId(),
                entity.getClient().getId(),
                entity.getService().getId(),
                entity.getCreatedAt(),
                entity.getScheduleAt(),
                entity.getDescription(),
                entity.getStatus(),
                suggestion
        );
    }
}
