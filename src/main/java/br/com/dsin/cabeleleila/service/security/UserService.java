package br.com.dsin.cabeleleila.service.security;

import br.com.dsin.cabeleleila.domain.repository.UserRepository;
import br.com.dsin.cabeleleila.domain.security.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public User createUser(String name, String email, String password) {
        var passwordEncoded = passwordEncoder.encode(password);
        var role = roleService.findRole("ROLE_CLIENT");
        var user = new User(null, name, email, passwordEncoded, Set.of(role));
        return userRepository.save(user);
    }
}
