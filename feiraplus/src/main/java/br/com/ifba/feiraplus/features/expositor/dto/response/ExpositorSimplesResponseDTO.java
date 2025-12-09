package br.com.ifba.feiraplus.features.expositor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // <--- Necessário para o ModelMapper e Jackson (JSON)
@AllArgsConstructor // <--- Necessário para o @Builder funcionar junto com o @NoArgsConstructor
public class ExpositorSimplesResponseDTO {
    private Long id;
    private String nome;
}