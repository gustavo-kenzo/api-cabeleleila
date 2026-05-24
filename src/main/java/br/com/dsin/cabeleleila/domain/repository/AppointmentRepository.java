package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Appointment;
import br.com.dsin.cabeleleila.domain.ScheduleStatus;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //Poderia usar query JPQL para evitar acoplamento com banco MySQL
    //Nova query:
        /*SELECT a.scheduleAt FROM Appointment a
        WHERE a.client.id = :clientId
        AND a.scheduleAt >= :weekStart
        AND a.scheduleAt < :weekEnd */
    //A logica de weekStart e weekEnd ficaria no service
    @Query(nativeQuery = true, value = """ 
            SELECT ap.schedule_at 
            FROM appointments ap 
            WHERE ap.client_id = :clientId 
            AND WEEK(ap.schedule_at,1) = WEEK(:scheduleAt, 1)
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
