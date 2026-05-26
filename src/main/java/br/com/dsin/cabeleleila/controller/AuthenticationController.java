package br.com.dsin.cabeleleila.controller;

import br.com.dsin.cabeleleila.domain.security.User;
import br.com.dsin.cabeleleila.dto.request.authentication.AuthenticationRequest;
import br.com.dsin.cabeleleila.dto.response.TokenResponse;
import br.com.dsin.cabeleleila.service.security.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationManager manager;
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthenticationRequest dados) {
        var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dados.username(), dados.password()));
        var token = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponse(token));
    }

}
