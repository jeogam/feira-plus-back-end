package br.com.ifba.feiraplus.features.relatorio.service;

import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.repository.FeiraRepository;
import br.com.ifba.feiraplus.features.relatorio.dto.response.RelatorioOcupacaoResponseDTO;
import br.com.ifba.feiraplus.features.expositor.dto.response.ExpositorSimplesResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    // Apenas FeiraRepository é necessário para buscar todas as Feiras.
    private final FeiraRepository feiraRepository;

    @Transactional(readOnly = true)
    public List<RelatorioOcupacaoResponseDTO> gerarRelatorioOcupacao() {
        // Usamos @Transactional(readOnly = true) para garantir que a coleção 'expositores'
        // possa ser acessada mesmo que esteja com FetchType.LAZY.
        List<Feira> todasFeiras = feiraRepository.findAll();
        List<RelatorioOcupacaoResponseDTO> relatorio = new ArrayList<>();

        for (Feira feira : todasFeiras) {
            // 1. Buscamos os números conforme a nova lógica
            long totalEspacos = feira.getEspacos(); // Total de espaços definido na Feira
            // Ocupados = número de expositores na relação N:N
            long ocupados = feira.getExpositores() != null ? feira.getExpositores().size() : 0;
            long vagos = totalEspacos - ocupados;

            if (vagos < 0) vagos = 0; // Garante que o número de vagos não é negativo

            // 2. Calculamos a porcentagem
            double taxa = 0.0;
            if (totalEspacos > 0) {
                taxa = ((double) ocupados / totalEspacos) * 100;
            }

            // 3. Mapeamos os Expositores para DTO Simples para a lista
            List<ExpositorSimplesResponseDTO> expositoresSimples = feira.getExpositores().stream()
                    .map(exp -> ExpositorSimplesResponseDTO.builder() // Assumimos que ExpositorSimplesResponseDTO tem @Builder
                            .id(exp.getId())
                            .nome(exp.getNome())
                            .build())
                    .collect(Collectors.toList());

            // 4. Montamos o DTO principal
            RelatorioOcupacaoResponseDTO linha = RelatorioOcupacaoResponseDTO.builder()
                    .nomeFeira(feira.getNome())
                    .idFeira(feira.getId())
                    .totalEspacos(totalEspacos)
                    .espacosOcupados(ocupados)
                    .espacosVagos(vagos)
                    .taxaOcupacao(Math.round(taxa * 100.0) / 100.0)
                    .expositores(expositoresSimples)
                    .build();

            relatorio.add(linha);
        }

        return relatorio;
    }
}