package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);
}
