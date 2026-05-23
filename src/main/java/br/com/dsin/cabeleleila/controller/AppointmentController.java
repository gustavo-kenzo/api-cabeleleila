package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.dto.request.AppointmentCreateRequest;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import br.com.dsin.cabeleleila.dto.request.AppointmentSuggestionConfirmRequest;
import br.com.dsin.cabeleleila.dto.request.AppointmentUpdateRequest;
import br.com.dsin.cabeleleila.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@RequestBody @Valid AppointmentCreateRequest dto) {
        return ResponseEntity.ok(appointmentService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @RequestBody @Valid AppointmentUpdateRequest dto) {
        return ResponseEntity.ok(appointmentService.update(id, dto));
    }

    @PatchMapping("/{id}/confirm-suggestion")
    public ResponseEntity<AppointmentResponse> confirmSuggestion(@PathVariable Long id, @RequestBody @Valid AppointmentSuggestionConfirmRequest dto) {
        return ResponseEntity.ok(appointmentService.confirmSuggestion(id, dto));
    }

    @GetMapping("/{clientId}/history")
    public ResponseEntity<Page<AppointmentResponse>> findByPeriod(@PathVariable Long clientId,
                                                                  @RequestParam(required = false) Instant initialDate,
                                                                  @RequestParam(required = false) Instant endDate,
                                                                  @PageableDefault(size = 5, direction = Sort.Direction.DESC)
                                                                  Pageable pageable) {
        return ResponseEntity.ok(appointmentService.findByPeriod(clientId, pageable, initialDate, endDate));
    }
}
