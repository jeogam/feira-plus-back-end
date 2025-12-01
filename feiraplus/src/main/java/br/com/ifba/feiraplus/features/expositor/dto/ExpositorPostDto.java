package br.com.ifba.feiraplus.features.expositor.dto;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpositorPostDto {

    private String nome;
    private String documentacao;
    private StatusExpositor status;
}
