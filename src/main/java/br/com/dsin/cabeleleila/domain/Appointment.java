package br.com.dsin.cabeleleila.domain;

import br.com.dsin.cabeleleila.exceptions.BusinessRuleException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceProvided service;

    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant scheduleAt;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    public void changeSchedule(Instant newDate) {
        this.scheduleAt = newDate;
    }

    public void changeStatus(ScheduleStatus newStatus) {
        this.status = newStatus;
    }

    public void update(Instant newDate, ServiceProvided newService, String newDescription, boolean needValidadeReschedule) {
        if (newDate != null) {
            if (needValidadeReschedule) validateReschedule();
            this.scheduleAt = newDate;
        }
        if (newService != null) {
            this.service = newService;
        }
        if (newDescription != null) {
            this.description = newDescription;
        }
    }

    private void validateReschedule() {
        var rescheduleDeadlineHours = 48;
        long hoursRemaining = Duration.between(Instant.now(), this.getScheduleAt()).toHours();
        if (hoursRemaining < rescheduleDeadlineHours) {
            throw new BusinessRuleException("Appointment can only be rescheduled by phone when less than 2 days away");
        }
    }
}

