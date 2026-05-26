package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.dto.request.ServiceProvidedCreateRequest;
import br.com.dsin.cabeleleila.dto.response.ServiceProvidedResponse;
import br.com.dsin.cabeleleila.service.ServiceProvidedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("service")
@PreAuthorize("hasRole('ADMIN')")
public class ServiceController {

    private final ServiceProvidedService service;

    @PostMapping
    public ResponseEntity<ServiceProvidedResponse> registerService(@RequestBody @Valid ServiceProvidedCreateRequest dto,
                                                                   UriComponentsBuilder uriBuilder) {
        var serviceResponse = service.register(dto);
        var uri = uriBuilder.path("/service/{id}").buildAndExpand(serviceResponse.id()).toUri();
        return ResponseEntity.created(uri).body(serviceResponse);
    }
}
