package br.com.ifba.feiraplus.features.atribuicao.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AtribuicaoPostRequestDTO {

    @NotNull(message = "A data é obrigatória")
    private LocalDate dataAtribuicao;

    @NotNull(message = "O ID do espaço é obrigatório")
    private Long espacoId;

    @NotNull(message = "O ID do expositor é obrigatório")
    private Long expositorId;
}