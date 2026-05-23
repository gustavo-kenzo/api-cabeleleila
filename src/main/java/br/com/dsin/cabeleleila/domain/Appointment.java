package br.com.dsin.cabeleleila.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void changeService(ServiceProvided newService) {
        this.service = newService;
    }

    public void changeSchedule(Instant newDate) {
        this.scheduleAt = newDate;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void changeStatus(ScheduleStatus newStatus) {
        this.status = newStatus;
    }
}

