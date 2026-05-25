package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.Appointment;
import br.com.dsin.cabeleleila.domain.ScheduleStatus;
import br.com.dsin.cabeleleila.domain.repository.AppointmentRepository;
import br.com.dsin.cabeleleila.domain.security.User;
import br.com.dsin.cabeleleila.dto.request.AppointmentCreateRequest;
import br.com.dsin.cabeleleila.dto.request.AppointmentStatusUpdateRequest;
import br.com.dsin.cabeleleila.dto.request.AppointmentSuggestionConfirmRequest;
import br.com.dsin.cabeleleila.dto.request.AppointmentUpdateRequest;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import br.com.dsin.cabeleleila.mapper.AppointmentMapper;
import jakarta.validation.Valid;
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

    private static final long RESCHEDULE_DEADLINE_HOURS = 48;
    private final AppointmentRepository appointmentRepository;
    private final ServiceProvidedService serviceProvidedService;
    private final ClientService clientService;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentResponse create(User user, AppointmentCreateRequest appointmentDTO) {
        var userId = user.getId();
        var client = clientService.findByUserId(userId).orElseThrow(() -> new RuntimeException("Client not found"));
        var service = serviceProvidedService.findServiceById(appointmentDTO.serviceId());
        var newAppointment = new Appointment(null, client, service, Instant.now(), appointmentDTO.scheduleAt(), appointmentDTO.description(), ScheduleStatus.PENDING);


        var alreadySchedule = appointmentRepository.findScheduleInWeek(client.getId(), appointmentDTO.scheduleAt());
        System.out.println(alreadySchedule);
        var appointment = appointmentRepository.save(newAppointment);
        if (alreadySchedule.isPresent()) {
            return appointmentMapper.toResponseWithSuggestion(appointment, alreadySchedule.get());
        }

        return appointmentMapper.toResponse(appointment);
    }

    @Transactional
    public AppointmentResponse update(Long id, AppointmentUpdateRequest dto) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        var date = appointment.getScheduleAt();
        if (dto.scheduleAt() != null) {
            validateReschedule(appointment);
            appointment.changeSchedule(dto.scheduleAt());
        }
        if (dto.serviceId() != null) {
            var service = serviceProvidedService.findServiceById(dto.serviceId());
            appointment.changeService(service);
        }
        if (dto.description() != null) {
            appointment.changeDescription(dto.description());
        }
        return appointmentMapper.toResponse(appointment);
    }

    private void validateReschedule(Appointment appointment) {
        long hoursRemaining = Duration.between(Instant.now(), appointment.getScheduleAt()).toHours();
        if (hoursRemaining < RESCHEDULE_DEADLINE_HOURS) {
            throw new RuntimeException("Appointment can only be rescheduled by phone when less than 2 days away");
        }
    }

    @Transactional
    public AppointmentResponse confirmSuggestion(Long id, AppointmentSuggestionConfirmRequest dto) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        var suggestedDate = appointmentRepository.findScheduleInWeek(appointment.getClient().getId(), appointment.getScheduleAt())
                .orElseThrow(() -> new RuntimeException("No suggestion available for this appointment"));
        if (!suggestedDate.equals(dto.newDate())) {
            throw new RuntimeException("The confirmed date must match the suggested date: " + suggestedDate);
        }
        appointment.changeSchedule(dto.newDate());
        return appointmentMapper.toResponse(appointment);
    }

    public Page<AppointmentResponse> findByPeriod(Long userId, Pageable pageable, Instant initialDate, Instant endDate) {
        Page<Appointment> appointments;
        var client = clientService.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (initialDate != null && endDate != null) {
            appointments = appointmentRepository.findByClientIdAndScheduleAtBetween(pageable, client.getId(), initialDate, endDate);
        } else {
            appointments = appointmentRepository.findPastByClientId(client.getId(), pageable);
        }
        return appointments.map(appointmentMapper::toResponse);
    }

    public Page<AppointmentResponse> listAll(ScheduleStatus status, Pageable pageable) {
        if (status != null) {
            return appointmentRepository.findByStatus(status, pageable).map(appointmentMapper::toResponse);
        }
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toResponse);
    }

    @Transactional
    public AppointmentResponse adminUpdate(Long id, @Valid AppointmentUpdateRequest dto) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (dto.scheduleAt() != null) {
            appointment.changeSchedule(dto.scheduleAt());
        }
        if (dto.serviceId() != null) {
            var service = serviceProvidedService.findServiceById(dto.serviceId());
            appointment.changeService(service);
        }
        if (dto.description() != null) {
            appointment.changeDescription(dto.description());
        }
        return appointmentMapper.toResponse(appointment);
    }

    @Transactional
    public AppointmentResponse updateStatus(Long id, AppointmentStatusUpdateRequest dto) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.changeStatus(dto.status());
        return appointmentMapper.toResponse(appointment);
    }
}