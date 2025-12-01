package br.com.ifba.feiraplus.features.atribuicao.service;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import br.com.ifba.feiraplus.features.espaco.repository.EspacoRepository;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.atribuicao.dto.request.AtribuicaoPostRequestDTO;
import br.com.ifba.feiraplus.features.atribuicao.entity.Atribuicao;
import br.com.ifba.feiraplus.features.atribuicao.repository.AtribuicaoRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtribuicaoService implements AtribuicaoIService {

    private final AtribuicaoRepository atribuicaoRepository;
    private final EspacoRepository espacoRepository;
    private final ExpositorRepository expositorRepository;

    @Override
    public List<Atribuicao> findAll() {
        return atribuicaoRepository.findAll();
    }

    @Override
    public Atribuicao findById(Long id) {
        return atribuicaoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Atribuição não encontrada!"));
    }

    @Override
    public Atribuicao save(AtribuicaoPostRequestDTO dto) {
        // Buscamos as entidades reais baseadas nos IDs enviados no DTO
        Espaco espaco = espacoRepository.findById(dto.getEspacoId())
                .orElseThrow(() -> new BusinessException("Espaço não encontrado!"));

        Expositor expositor = expositorRepository.findById(dto.getExpositorId())
                .orElseThrow(() -> new BusinessException("Expositor não encontrado!"));

        // Verifica se o espaço já está ocupado (regra de negócio para OneToOne)
        // Isso previne erro de duplicidade no banco
        // if (espaco.getAtribuicao() != null) throw new BusinessException("Espaço já ocupado!");

        Atribuicao atribuicao = new Atribuicao();
        atribuicao.setDataAtribuicao(dto.getDataAtribuicao());
        atribuicao.setEspaco(espaco);
        atribuicao.setExpositor(expositor);

        return atribuicaoRepository.save(atribuicao);
    }

    @Override
    public Atribuicao update(Long id, AtribuicaoPostRequestDTO dto) {
        Atribuicao existente = findById(id);

        // Atualiza Data
        existente.setDataAtribuicao(dto.getDataAtribuicao());

        // Atualiza Espaço se mudou
        if (!existente.getEspaco().getId().equals(dto.getEspacoId())) {
            Espaco novoEspaco = espacoRepository.findById(dto.getEspacoId())
                    .orElseThrow(() -> new BusinessException("Espaço não encontrado!"));
            existente.setEspaco(novoEspaco);
        }

        // Atualiza Expositor se mudou
        if (!existente.getExpositor().getId().equals(dto.getExpositorId())) {
            Expositor novoExpositor = expositorRepository.findById(dto.getExpositorId())
                    .orElseThrow(() -> new BusinessException("Expositor não encontrado!"));
            existente.setExpositor(novoExpositor);
        }

        return atribuicaoRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        Atribuicao atribuicao = findById(id);
        atribuicaoRepository.delete(atribuicao);
    }
}