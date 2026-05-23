package br.com.dsin.cabeleleila.dto.response;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String phone,
        boolean active
) {
}
