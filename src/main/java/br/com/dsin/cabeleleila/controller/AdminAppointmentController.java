package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.domain.ScheduleStatus;
import br.com.dsin.cabeleleila.dto.request.AppointmentStatusUpdateRequest;
import br.com.dsin.cabeleleila.dto.request.AppointmentUpdateRequest;
import br.com.dsin.cabeleleila.dto.response.AppointmentResponse;
import br.com.dsin.cabeleleila.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/appointments")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAppointmentController {

    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> listAll(@RequestParam(required = false) ScheduleStatus status,
                                                             @PageableDefault(size = 5, sort = "scheduleAt") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.listAll(status, pageable));
    }

    @PutMapping("{id}")
    public ResponseEntity<AppointmentResponse> adminUpdate(@PathVariable Long id,
                                                           @RequestBody @Valid AppointmentUpdateRequest dto) {
        return ResponseEntity.ok(appointmentService.adminUpdate(id, dto));
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id,
                                                            @RequestBody AppointmentStatusUpdateRequest dto){
        return ResponseEntity.ok(appointmentService.updateStatus(id,dto));
    }
}
