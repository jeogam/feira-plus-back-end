package br.com.ifba.feiraplus.features.atribuicao.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AtribuicaoGetResponseDTO {
    private Long id;
    private LocalDate dataAtribuicao;

    // Devolvemos objetos simplificados ou IDs/Nomes para o front saber quem é
    private Long espacoId;
    private String espacoNome; // Útil para mostrar na tabela

    private Long expositorId;
    private String expositorNome; // Útil para mostrar na tabela
}