package br.com.ifba.feiraplus.features.evento.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoRequestDTO {

    @NotBlank(message = "O título do evento é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    @NotNull(message = "O ID da feira é obrigatório")
    private Long feiraId;
}