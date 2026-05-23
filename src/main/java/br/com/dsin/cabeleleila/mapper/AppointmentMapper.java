package br.com.dsin.cabeleleila.mapper;

import br.com.dsin.cabeleleila.domain.Appointment;
import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.domain.ScheduleStatus;
import br.com.dsin.cabeleleila.domain.ServiceProvided;
import br.com.dsin.cabeleleila.dto.register.AppointmentRegister;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRegister dto, Client client, ServiceProvided service) {
        return new Appointment(
                null,
                client,
                service,
                Instant.now(),
                dto.scheduleAt(),
                dto.description(),
                ScheduleStatus.PENDING
        );
    }

    public AppointmentResponse toResponse(Appointment entity) {
        return new AppointmentResponse(entity.getId(), entity.getClient().getId(), entity.getService().getId(), entity.getCreatedAt(), entity.getScheduleAt(), entity.getDescription(), entity.getStatus(), null);
    }

    public AppointmentResponse toResponseWithSuggestion(Appointment entity, String suggestion) {
        return new AppointmentResponse(entity.getId(), entity.getClient().getId(), entity.getService().getId(), entity.getCreatedAt(), entity.getScheduleAt(), entity.getDescription(), entity.getStatus(), suggestion);
    }
}
