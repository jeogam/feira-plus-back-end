package br.com.ifba.feiraplus.features.espaco.dto.request;

import br.com.ifba.feiraplus.features.espaco.enums.StatusEspaco;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EspacoPostRequestDTO {
    private String nome;
    private String local;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String horarios;
    private StatusEspaco status;
    private Long feiraId; // Recebemos apenas o ID da feira
}