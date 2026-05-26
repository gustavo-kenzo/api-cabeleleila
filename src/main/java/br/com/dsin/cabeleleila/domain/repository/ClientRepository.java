package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("""
            SELECT c
            FROM Client c
            WHERE c.user.id = :id
            """)
    Optional<Client> findByUserId(Long id);
}
