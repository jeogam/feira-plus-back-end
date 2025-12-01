package br.com.ifba.feiraplus.features.relatorio.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioOcupacaoResponseDTO {
    private String nomeFeira;
    private Long totalEspacos;
    private Long espacosOcupados;
    private Long espacosVagos;
    private Double taxaOcupacao; // Ex: 85.5%
}