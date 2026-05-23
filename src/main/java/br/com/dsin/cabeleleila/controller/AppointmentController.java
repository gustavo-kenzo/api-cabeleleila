package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.dto.register.AppointmentRegister;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import br.com.dsin.cabeleleila.dto.update.AppointmentSuggestionConfirm;
import br.com.dsin.cabeleleila.dto.update.AppointmentUpdate;
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
    public ResponseEntity<AppointmentResponse> schedule(@RequestBody @Valid AppointmentRegister dto) {
        return ResponseEntity.ok(appointmentService.schedule(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<AppointmentResponse> updateSchedule(@PathVariable Long id, @RequestBody @Valid AppointmentUpdate dto) {
        return ResponseEntity.ok(appointmentService.updateSchedule(id, dto));
    }

    @PatchMapping("/{id}/confirm-suggestion")
    public ResponseEntity<AppointmentResponse> confirmSuggestion(@PathVariable Long id, @RequestBody @Valid AppointmentSuggestionConfirm appointmentSuggestion) {
        return ResponseEntity.ok(appointmentService.confirmSuggestion(id, appointmentSuggestion));
    }

    @GetMapping("{clientId}")
    public ResponseEntity<Page<AppointmentResponse>> findByDate(@PathVariable Long clientId,
                                                                @RequestParam(required = false) Instant initialDate,
                                                                @RequestParam(required = false) Instant endDate,
                                                                @PageableDefault(size = 5, direction = Sort.Direction.DESC)
                                                                Pageable pageable) {
        return ResponseEntity.ok(appointmentService.findByDate(clientId, pageable, initialDate, endDate));
    }
}
