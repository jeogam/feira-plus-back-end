package br.com.ifba.feiraplus.features.feira.dto.response;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.dto.response.ExpositorSimplesResponseDTO;
import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import lombok.Data;
import java.time.LocalTime;
import java.util.List; // Adicionado

@Data
public class FeiraPermanenteResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private int espacos;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private Frequencia frequencia;

    // Lista de expositores para a resposta
    private List<ExpositorGetDto> expositores;

    private String foto;
}