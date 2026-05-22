package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
}
