package br.com.ifba.feiraplus.features.expositor.dto;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpositorGetDto {

    private Long id;
    private String nome;
    private String email;
    private String cpfCnpj;
    private String telefone;
    private StatusExpositor status;
}
