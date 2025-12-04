package br.com.ifba.feiraplus.features.usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioGetDto {

    private Long id;
    private String nome;
    private String email;
    private String perfilUsuario;
}
