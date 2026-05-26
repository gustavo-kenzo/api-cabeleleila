package br.com.dsin.cabeleleila.service.security;

import br.com.dsin.cabeleleila.domain.repository.RoleRepository;
import br.com.dsin.cabeleleila.domain.security.Role;
import br.com.dsin.cabeleleila.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRole(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role", name));
    }
}
