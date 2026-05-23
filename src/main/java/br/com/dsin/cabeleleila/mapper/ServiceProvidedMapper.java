package br.com.dsin.cabeleleila.mapper;

import br.com.dsin.cabeleleila.domain.ServiceProvided;
import br.com.dsin.cabeleleila.dto.register.ServiceProvidedRegister;
import br.com.dsin.cabeleleila.dto.response.ServiceProvidedResponse;
import org.springframework.stereotype.Service;

@Service
public class ServiceProvidedMapper {

    public ServiceProvided toEntity(ServiceProvidedRegister dto) {
        return new ServiceProvided(null, dto.name(), dto.price(), dto.description(), true);
    }

    public ServiceProvidedResponse toResponse(ServiceProvided entity) {
        return new ServiceProvidedResponse(entity.getId(), entity.getName(), entity.getPrice(), entity.getDescription(), entity.isActive());
    }
}

