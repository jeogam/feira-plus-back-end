package br.com.ifba.feiraplus.features.expositor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpositorSimplesResponseDTO {

    private Long id;
    private String nome;
}