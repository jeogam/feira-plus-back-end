package br.com.ifba.feiraplus.features.usuario.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSenhaDto {

    @NotBlank(message = "A senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória")
    private String novaSenha;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String senhaConfirmada;
}
