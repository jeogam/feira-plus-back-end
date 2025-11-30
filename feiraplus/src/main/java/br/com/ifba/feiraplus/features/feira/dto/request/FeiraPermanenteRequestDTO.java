package br.com.ifba.feiraplus.features.feira.dto.request;

import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class FeiraPermanenteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "Hora de abertura é obrigatória")
    private LocalTime horaAbertura;

    @NotNull(message = "Hora de fechamento é obrigatória")
    private LocalTime horaFechamento;

    @NotNull(message = "A frequência é obrigatória")
    private Frequencia frequencia;

    // TODO: Adicionar usuarioId
    // private Long usuarioId;
}