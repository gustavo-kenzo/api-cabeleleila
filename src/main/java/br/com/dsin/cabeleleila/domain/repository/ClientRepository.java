package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
