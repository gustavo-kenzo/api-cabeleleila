package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Appointment;
import br.com.dsin.cabeleleila.domain.ScheduleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(""" 
            SELECT ap.scheduleAt
            FROM Appointment ap 
            WHERE ap.client.id = :clientId 
            AND WEEK(ap.scheduleAt) = WEEK(:scheduleAt)
            AND ap.scheduleAt <> :scheduleAt
            LIMIT 1
            """)
    Optional<Instant> findScheduleInWeek(Long clientId, Instant scheduleAt);

    Page<Appointment> findByClientIdAndScheduleAtBetween(Pageable pageable, Long clientId, Instant initialDate, Instant endDate);

    @Query("""
            SELECT a FROM Appointment a
            WHERE a.client.id = :clientId
            AND a.scheduleAt < CURRENT_TIMESTAMP 
            ORDER BY a.scheduleAt DESC
            """)
    Page<Appointment> findPastByClientId(Long clientId, Pageable pageable);

    Page<Appointment> findByStatus(ScheduleStatus status, Pageable pageable);
}
