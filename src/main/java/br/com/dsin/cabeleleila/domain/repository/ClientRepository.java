package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
