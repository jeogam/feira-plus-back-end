package br.com.ifba.feiraplus.features.espaco.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EspacoRequestDTO {

    @NotBlank(message = "O nome do espaço é obrigatório")
    private String nome;

    @NotBlank(message = "O local do espaço é obrigatório")
    private String local;

    @NotNull(message = "Data de início é obrigatória")
    @FutureOrPresent(message = "A data deve ser futura ou presente")
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @FutureOrPresent(message = "A data deve ser futura ou presente")
    private LocalDate dataFim;

    @NotBlank(message = "Os horários são obrigatórios")
    private String horarios;

    @NotNull(message = "O ID da feira é obrigatório")
    private Long feiraId;
}