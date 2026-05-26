package br.com.dsin.cabeleleila.service;

import br.com.dsin.cabeleleila.domain.ServiceProvided;
import br.com.dsin.cabeleleila.domain.repository.ServiceProvidedRepository;
import br.com.dsin.cabeleleila.dto.request.ServiceProvidedCreateRequest;
import br.com.dsin.cabeleleila.dto.response.ServiceProvidedResponse;
import br.com.dsin.cabeleleila.exceptions.ResourceNotFoundException;
import br.com.dsin.cabeleleila.mapper.ServiceProvidedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceProvidedService {

    private final ServiceProvidedRepository serviceRepository;
    private final ServiceProvidedMapper mapper;

    public ServiceProvidedResponse register(ServiceProvidedCreateRequest serviceDTO) {
        var newService = new ServiceProvided(null, serviceDTO.name(), serviceDTO.price(), serviceDTO.description(), true);
        var service = serviceRepository.save(newService);
        return mapper.toResponse(service);
    }


    public ServiceProvided findServiceById(Long id) {
        return serviceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service", id.toString()));
    }
}
