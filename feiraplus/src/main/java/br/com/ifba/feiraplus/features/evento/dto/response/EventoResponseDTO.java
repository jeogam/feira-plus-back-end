package br.com.ifba.feiraplus.features.evento.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    private Long feiraId;
    private String feiraNome;
}