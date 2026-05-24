package br.com.dsin.cabeleleila.domain.repository;

import br.com.dsin.cabeleleila.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String client);
}
