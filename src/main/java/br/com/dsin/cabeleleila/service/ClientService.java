package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.domain.repository.ClientRepository;
import br.com.dsin.cabeleleila.dto.request.ClientCreateRequest;
import br.com.dsin.cabeleleila.dto.response.ClientDetailResponse;
import br.com.dsin.cabeleleila.dto.response.ClientResponse;
import br.com.dsin.cabeleleila.exceptions.ConflictException;
import br.com.dsin.cabeleleila.exceptions.ResourceNotFoundException;
import br.com.dsin.cabeleleila.mapper.ClientMapper;
import br.com.dsin.cabeleleila.service.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new ConflictException("Email already in use");
        }
    }

    private void validatePhoneAvailability(String phone) {
        if (clientRepository.existsByPhone(phone)) {
            throw new ConflictException("Phone already in use");
        }
    }

    public ClientDetailResponse findById(Long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id.toString()));
        return clientMapper.toDetailResponse(client);
    }

    public Page<ClientResponse> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toResponse);
    }

    public Client findByUserId(Long id) {
        return clientRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("Client", id.toString()));
    }
}
