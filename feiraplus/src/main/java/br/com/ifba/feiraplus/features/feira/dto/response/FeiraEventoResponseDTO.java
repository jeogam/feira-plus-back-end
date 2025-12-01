package br.com.ifba.feiraplus.features.feira.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FeiraEventoResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}