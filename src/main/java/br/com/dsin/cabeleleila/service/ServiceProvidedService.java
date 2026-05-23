package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.repository.ServiceProvidedRepository;
import br.com.dsin.cabeleleila.dto.register.ServiceProvidedRegister;
import br.com.dsin.cabeleleila.dto.response.ServiceProvidedResponse;
import br.com.dsin.cabeleleila.mapper.ServiceProvidedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceProvidedService {

    private final ServiceProvidedRepository serviceRepository;
    private final ServiceProvidedMapper mapper;

    public ServiceProvidedResponse registerService(ServiceProvidedRegister serviceDTO) {
        var newService = mapper.toEntity(serviceDTO);
        var service = serviceRepository.save(newService);
        return mapper.toResponse(service);
    }
}
