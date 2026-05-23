package br.com.dsin.cabeleleila.mapper;

import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.domain.ServiceProvided;
import br.com.dsin.cabeleleila.dto.register.ClientRegister;
import br.com.dsin.cabeleleila.dto.response.ClientDetailResponse;
import br.com.dsin.cabeleleila.dto.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMapper {
    private final AppointmentMapper appointmentMapper;

    public Client toEntity(ClientRegister dto) {
        return new Client(null,dto.name(),dto.email(),dto.phone(),true, List.of());
    }

    public ClientResponse toResponse(Client entity) {
        return new ClientResponse(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.isActive());
    }

    // Não é bom que tudo seja trazido do banco e só então limitado.
    // Sugestão: fazer consultas separadas -> client sem appointments + appointments de client
    public ClientDetailResponse toDetailResponse(Client entity) {
        return new ClientDetailResponse(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.isActive(), entity.getAppointments().stream().map(appointmentMapper::toResponse).toList());

    }
}
