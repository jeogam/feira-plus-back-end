package br.com.ifba.feiraplus.features.autenticacao.controller;


import br.com.ifba.feiraplus.features.autenticacao.dto.LoginRequestDto;
import br.com.ifba.feiraplus.features.autenticacao.dto.TokenResponseDto;
import br.com.ifba.feiraplus.infrastructure.jwt.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginDto) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getSenha()
        );

        Authentication authentication = authenticationManager.authenticate(authToken);

        // GERA O TOKEN REAL
        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new TokenResponseDto(token));
    }

}
