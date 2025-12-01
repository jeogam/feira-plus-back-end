package br.com.ifba.feiraplus.features.autenticacao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @NotBlank(message = "O nome de Usuário (email) não pode ser vazio.")
    private String email;

    @NotBlank(message = " A senha não pode ser vazia.")
    private String senha;
 }

