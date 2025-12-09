package br.com.ifba.feiraplus.features.relatorio.dto.response;

import br.com.ifba.feiraplus.features.expositor.dto.response.ExpositorSimplesResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RelatorioOcupacaoResponseDTO {
    private String nomeFeira;
    private Long idFeira;
    private Long totalEspacos;
    private Long espacosOcupados;
    private Long espacosVagos;
    private Double taxaOcupacao; // Ex: 85.5%

    // Lista de expositores para mostrar no modal
    private List<ExpositorSimplesResponseDTO> expositores;
}