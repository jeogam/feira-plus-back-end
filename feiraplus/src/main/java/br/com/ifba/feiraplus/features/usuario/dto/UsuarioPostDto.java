package br.com.ifba.feiraplus.features.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPostDto {


    @NotEmpty
    private String nome;

    @Email
    private String email;

    @NotEmpty
    private String senha;

    @NotEmpty
    private String perfilUsuario;
}
