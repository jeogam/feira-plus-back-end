package br.com.ifba.feiraplus.features.feira.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List; // Adicionado

@Data
public class FeiraEventoRequestDTO {

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

    @NotNull(message = "Data de início é obrigatória")
    @FutureOrPresent(message = "A data deve ser futura ou presente")
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @FutureOrPresent(message = "A data deve ser futura ou presente")
    private LocalDate dataFim;

    private Long usuarioId;

    // IDs dos expositores a serem associados à feira
    private List<Long> expositorIds;
}