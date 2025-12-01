package br.com.ifba.feiraplus.features.expositor.dto;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpositorGetDto {

    private String nome;
    private StatusExpositor status;
}
