package br.com.ifba.feiraplus.features.espaco.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EspacoResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String horarios;
    private Long feiraId; // Retorna o ID da feira para referência
}