package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.Appointment;
import br.com.dsin.cabeleleila.domain.repository.AppointmentRepository;
import br.com.dsin.cabeleleila.domain.repository.ClientRepository;
import br.com.dsin.cabeleleila.domain.repository.ServiceProvidedRepository;
import br.com.dsin.cabeleleila.dto.register.AppointmentRegister;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import br.com.dsin.cabeleleila.dto.update.AppointmentSuggestionConfirm;
import br.com.dsin.cabeleleila.dto.update.AppointmentUpdate;
import br.com.dsin.cabeleleila.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceProvidedRepository serviceProvidedRepository;
    private final ClientRepository clientRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentResponse schedule(AppointmentRegister appointmentDTO) {
        var client = clientRepository.getReferenceById(appointmentDTO.clientId());
        var service = serviceProvidedRepository.getReferenceById(appointmentDTO.serviceId());
        var newAppointment = appointmentMapper.toEntity(appointmentDTO, client, service);


        var alreadySchedule = appointmentRepository.getScheduleInWeek(appointmentDTO.clientId(), appointmentDTO.scheduleAt());
        var appointment = appointmentRepository.save(newAppointment);
        if (alreadySchedule.isPresent()) {
            return appointmentMapper.toResponseWithSuggestion(appointment, ("Do you want to schedule it for" + alreadySchedule + " ?"));
        }

        return appointmentMapper.toResponse(appointment);
    }

    @Transactional
    public AppointmentResponse updateSchedule(Long id, AppointmentUpdate dto) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        var date = appointment.getScheduleAt();
        if (dto.scheduleAt() != null) {
            if (Duration.between(Instant.now(), date).toHours() < 48) {
                throw new RuntimeException("Date can only be rescheduled by phone");
            }
            appointment.setScheduleAt(dto.scheduleAt());
        }
        if (dto.serviceId() != null) {
            var service = serviceProvidedRepository.findById(dto.serviceId()).orElseThrow(()-> new RuntimeException("Service not found"));
            appointment.setService(service);
        }
        if (dto.description() != null) {
            appointment.setDescription(dto.description());
        }
        return appointmentMapper.toResponse(appointment);
    }

    @Transactional
    public AppointmentResponse confirmSuggestion(Long id, AppointmentSuggestionConfirm dto) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setScheduleAt(dto.newDate());
        return appointmentMapper.toResponse(appointment);
    }

    public Page<AppointmentResponse> findByDate(Long clientId, Pageable pageable, Instant initialDate, Instant endDate) {
        Page<Appointment> appointments;
        if (initialDate != null && endDate != null) {
            appointments = appointmentRepository.findByClientIdAndScheduleAtBetween(pageable, clientId, initialDate, endDate);
        } else {
            appointments = appointmentRepository.findPastByClientId(clientId, pageable);
        }
        return appointments.map(appointmentMapper::toResponse);
    }
}