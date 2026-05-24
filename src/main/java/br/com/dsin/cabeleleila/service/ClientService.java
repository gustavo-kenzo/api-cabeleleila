package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.domain.repository.ClientRepository;
import br.com.dsin.cabeleleila.domain.security.User;
import br.com.dsin.cabeleleila.dto.request.ClientCreateRequest;
import br.com.dsin.cabeleleila.dto.response.ClientDetailResponse;
import br.com.dsin.cabeleleila.dto.response.ClientResponse;
import br.com.dsin.cabeleleila.mapper.ClientMapper;
import br.com.dsin.cabeleleila.service.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserService userService;

    @Transactional
    public ClientResponse register(ClientCreateRequest clientDTO) {

        validateEmailAvailability(clientDTO.email());
        validatePhoneAvailability(clientDTO.phone());

        var user = userService.createUser(clientDTO.name(), clientDTO.email(), clientDTO.password());
        var newClient = new Client(null, clientDTO.name(), clientDTO.email(), clientDTO.phone(), true, List.of(), user);
        var client = clientRepository.save(newClient);

        return clientMapper.toResponse(client);
    }

    private void validateEmailAvailability(String email) {
        if (clientRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
    }

    private void validatePhoneAvailability(String phone) {
        if (clientRepository.existsByPhone(phone)) {
            throw new RuntimeException("Phone already in use");
        }
    }

    public ClientDetailResponse findById(Long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        return clientMapper.toDetailResponse(client);
    }

    public Page<ClientResponse> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toResponse);
    }

    public Client findClientById(Long id) {
        System.out.println("ID QUE CHEGOU NO SERVICE DE CLIENT: "+id);
        return clientRepository.findById(id).orElseThrow(()-> new RuntimeException("Client not found"));
    }

    public Optional<Client> findByUserId(Long id) {
        return clientRepository.findByUserId(id);
    }
}
