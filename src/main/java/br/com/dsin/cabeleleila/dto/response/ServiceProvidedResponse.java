package br.com.dsin.cabeleleila.dto.response;

import java.math.BigDecimal;

public record ServiceProvidedResponse(
        Long id,
        String name,
        BigDecimal price,
        String description,
        boolean active
) {
}
