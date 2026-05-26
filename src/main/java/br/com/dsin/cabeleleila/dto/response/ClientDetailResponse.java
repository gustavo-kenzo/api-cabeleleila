package br.com.dsin.cabeleleila.dto.response;

import java.util.List;

public record ClientDetailResponse(
        Long id,
        String name,
        String email,
        String phone,
        boolean active,
        List<AppointmentResponse> appointments
) {
}
