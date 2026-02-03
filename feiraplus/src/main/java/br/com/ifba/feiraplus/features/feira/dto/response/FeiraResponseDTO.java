package br.com.ifba.feiraplus.features.feira.dto.response;

import lombok.Data;
import java.time.LocalTime;
import java.time.LocalDate;

@Data
public class FeiraResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private int espacos;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private String foto;

    // Campos específicos de Feira Evento (podem vir nulos se for Permanente)
    private LocalDate dataInicio;
    private LocalDate dataFim;

    // Campos específicos de Feira Permanente
    private String frequencia;
}