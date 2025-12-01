package br.com.ifba.feiraplus.features.espaco.dto.response;

import br.com.ifba.feiraplus.features.espaco.enums.StatusEspaco;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EspacoGetResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String horarios;
    private StatusEspaco status;
}