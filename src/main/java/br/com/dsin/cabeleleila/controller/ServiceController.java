package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.dto.register.ServiceProvidedRegister;
import br.com.dsin.cabeleleila.dto.response.ServiceProvidedResponse;
import br.com.dsin.cabeleleila.service.ServiceProvidedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("service")
public class ServiceController {

    private final ServiceProvidedService service;

    @PostMapping
    public ResponseEntity<ServiceProvidedResponse> registerService(@RequestBody @Valid ServiceProvidedRegister serviceDTO) {
        return ResponseEntity.ok(service.registerService(serviceDTO));
    }
}
