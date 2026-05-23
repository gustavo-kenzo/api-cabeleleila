package br.com.dsin.cabeleleila.mapper;

import br.com.dsin.cabeleleila.domain.Client;
import br.com.dsin.cabeleleila.dto.response.ClientDetailResponse;
import br.com.dsin.cabeleleila.dto.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {
    private final AppointmentMapper appointmentMapper;


    public ClientResponse toResponse(Client entity) {
        return new ClientResponse(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.isActive());
    }

    // Não é bom que tudo seja trazido do banco e só então limitado.
    // Sugestão: fazer consultas separadas -> client sem appointments + appointments de client
    public ClientDetailResponse toDetailResponse(Client entity) {
        return new ClientDetailResponse(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.isActive(), entity.getAppointments().stream().map(appointmentMapper::toResponse).toList());

    }
}
