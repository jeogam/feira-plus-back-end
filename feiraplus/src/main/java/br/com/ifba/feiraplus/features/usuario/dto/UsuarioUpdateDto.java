package br.com.ifba.feiraplus.features.usuario.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDto {

    @NotEmpty
    private String nome;
    @NotEmpty
    private String perfilUsuario;
}
