package br.com.dsin.cabeleleila.mapper;

import br.com.dsin.cabeleleila.domain.ServiceProvided;
import br.com.dsin.cabeleleila.dto.response.ServiceProvidedResponse;
import org.springframework.stereotype.Component;

@Component
public class ServiceProvidedMapper {

    public ServiceProvidedResponse toResponse(ServiceProvided entity) {
        return new ServiceProvidedResponse(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.isActive());
    }
}

