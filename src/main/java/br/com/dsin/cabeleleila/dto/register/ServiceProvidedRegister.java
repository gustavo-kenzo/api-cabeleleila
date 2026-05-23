package br.com.dsin.cabeleleila.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServiceProvidedRegister(
        @NotBlank(message = "Service provided is required")
        @Size(max = 150, message = "Service name cannot exceed characters")
        String name,

        @NotNull(message = "Service price is required")
        @PositiveOrZero(message = "Service price must be positive or zero")
        BigDecimal price,

        @Size(max = 500, message = "Service description cannot exceed 500 characters")
        String description
) {
}
