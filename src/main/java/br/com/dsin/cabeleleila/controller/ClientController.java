package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.dto.request.ClientCreateRequest;
import br.com.dsin.cabeleleila.dto.response.ClientDetailResponse;
import br.com.dsin.cabeleleila.dto.response.ClientResponse;
import br.com.dsin.cabeleleila.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> registerClient(@RequestBody @Valid ClientCreateRequest dto) {
        return ResponseEntity.ok(clientService.register(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<Page<ClientResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable));
    }
}
