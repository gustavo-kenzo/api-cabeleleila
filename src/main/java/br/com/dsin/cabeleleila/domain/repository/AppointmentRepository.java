package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(nativeQuery = true, value = """ 
            SELECT ap.schedule_at 
            FROM appointments ap 
            WHERE ap.client_id = :clientId 
            AND WEEK(ap.schedule_at,1) = WEEK(:scheduleAt, 1)
            LIMIT 1
            """)
    Optional<Instant> getScheduleInWeek(Long clientId, Instant scheduleAt);

    Page<Appointment> findByClientIdAndScheduleAtBetween(Pageable pageable, Long clientId, Instant initialDate, Instant endDate);

    @Query("""
            SELECT a FROM Appointment a
            WHERE a.client.id = :clientId 
            AND a.scheduleAt < CURRENT_TIMESTAMP 
            ORDER BY a.scheduleAt DESC
            """)
    Page<Appointment> findPastByClientId(Long clientId, Pageable pageable);
}
