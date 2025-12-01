package br.com.ifba.feiraplus.features.relatorio.service;

import br.com.ifba.feiraplus.features.atribuicao.repository.AtribuicaoRepository;
import br.com.ifba.feiraplus.features.espaco.repository.EspacoRepository;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.repository.FeiraRepository;
import br.com.ifba.feiraplus.features.relatorio.dto.response.RelatorioOcupacaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    // Injetamos os repositórios de quem tem os dados
    private final FeiraRepository feiraRepository;
    private final EspacoRepository espacoRepository;
    private final AtribuicaoRepository atribuicaoRepository;

    public List<RelatorioOcupacaoResponseDTO> gerarRelatorioOcupacao() {
        List<Feira> todasFeiras = feiraRepository.findAll();
        List<RelatorioOcupacaoResponseDTO> relatorio = new ArrayList<>();

        for (Feira feira : todasFeiras) {
            // 1. Buscamos os números no banco
            long totalEspacos = espacoRepository.countByFeiraId(feira.getId());
            long ocupados = atribuicaoRepository.countByEspacoFeiraId(feira.getId());
            long vagos = totalEspacos - ocupados;

            // 2. Calculamos a porcentagem (Cuidado com divisão por zero!)
            double taxa = 0.0;
            if (totalEspacos > 0) {
                taxa = ((double) ocupados / totalEspacos) * 100;
            }

            // 3. Montamos o DTO
            RelatorioOcupacaoResponseDTO linha = RelatorioOcupacaoResponseDTO.builder()
                    .nomeFeira(feira.getNome())
                    .totalEspacos(totalEspacos)
                    .espacosOcupados(ocupados)
                    .espacosVagos(vagos)
                    .taxaOcupacao(Math.round(taxa * 100.0) / 100.0) // Arredonda 2 casas
                    .build();

            relatorio.add(linha);
        }

        return relatorio;
    }
}