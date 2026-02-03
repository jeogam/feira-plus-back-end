package br.com.ifba.feiraplus.features.feira.dto.response;

import br.com.ifba.feiraplus.features.evento.dto.request.EventoRequestDTO;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.dto.response.ExpositorSimplesResponseDTO;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List; // Adicionado

@Data
public class FeiraEventoResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private int espacos;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    // Lista de expositores para a resposta
    private List<ExpositorGetDto> expositores;

    private List<EventoRequestDTO> eventos;

    private String foto;
    private Float nota;

}