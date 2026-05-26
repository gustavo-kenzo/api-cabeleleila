package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String client);
}
