package br.com.ifba.feiraplus.features.feira.dto.response;

import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import lombok.Data;
import java.time.LocalTime;

@Data
public class FeiraPermanenteResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private Frequencia frequencia;
}