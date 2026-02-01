package br.com.ifba.feiraplus.features.feira.dto.request;

import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.List; // Adicionado

@Data
public class FeiraPermanenteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "O total de espaços é obrigatório")
    @Min(value = 0, message = "O total de espaços não pode ser negativo")
    private int espacos;

    @NotNull(message = "Hora de abertura é obrigatória")
    private LocalTime horaAbertura;

    @NotNull(message = "Hora de fechamento é obrigatória")
    private LocalTime horaFechamento;

    @NotNull(message = "A frequência é obrigatória")
    private Frequencia frequencia;

    private Long usuarioId;

    // IDs dos expositores a serem associados à feira
    private List<Long> expositorIds;

    private String foto;

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Float nota;
}