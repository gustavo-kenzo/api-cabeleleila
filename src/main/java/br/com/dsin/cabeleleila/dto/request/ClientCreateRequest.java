package br.com.dsin.cabeleleila.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ClientCreateRequest(
        @NotBlank(message = "Client name is required")
        @Size(max = 150, message = "Client name cannot exceed 150 characters")
        String name,

        @NotBlank(message = "Client email is required")
        @Email(message = "Mail address is invalid")
        String email,

        @NotBlank(message = "Client phone is required")
        @Size(min = 11, max = 15, message = "Invalid phone format (must be between 11 and 15 characters)")
        String phone,

        //tirei tambem lista de appointments porque nao faz sentiudo criar client com appointments ja pronto
        @NotBlank(message = "Client password is required")
        String password
) {
}
