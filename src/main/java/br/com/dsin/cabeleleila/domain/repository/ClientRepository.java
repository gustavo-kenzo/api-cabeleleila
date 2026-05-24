package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Client> findByUserId(Long id);
}
