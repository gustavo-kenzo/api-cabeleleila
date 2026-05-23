package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.repository.ClientRepository;
import br.com.dsin.cabeleleila.dto.register.ClientRegister;
import br.com.dsin.cabeleleila.dto.response.ClientDetailResponse;
import br.com.dsin.cabeleleila.dto.response.ClientResponse;
import br.com.dsin.cabeleleila.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponse registerClient(ClientRegister clientDTO) {

        if (clientRepository.existsByEmail(clientDTO.email())) {
            throw new RuntimeException("Email already in use");
        }

        if (clientRepository.existsByPhone(clientDTO.phone())) {
            throw new RuntimeException("Phone already in use");
        }

        var newClient = clientMapper.toEntity(clientDTO);
        var client = clientRepository.save(newClient);
        return clientMapper.toResponse(client);
    }

    public ClientDetailResponse findById(Long id) {
        var client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        return clientMapper.toDetailResponse(client);
    }

    public Page<ClientResponse> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toResponse);
    }
}
