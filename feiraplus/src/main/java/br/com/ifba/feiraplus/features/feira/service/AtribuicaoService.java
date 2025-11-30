package br.com.ifba.feiraplus.features.feira.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.feiraplus.features.feira.entity.Atribuicao;
import br.com.ifba.feiraplus.features.feira.entity.Espaco;
import br.com.ifba.feiraplus.features.feira.entity.Expositor;
import br.com.ifba.feiraplus.features.feira.enums.StatusEspaco;
import br.com.ifba.feiraplus.features.feira.enums.StatusExpositor;
import br.com.ifba.feiraplus.features.feira.repository.AtribuicaoRepository;
import br.com.ifba.feiraplus.features.feira.repository.EspacoRepository;
import br.com.ifba.feiraplus.features.feira.repository.ExpositorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AtribuicaoService implements IAtribuicaoService {

    @Autowired
    private AtribuicaoRepository atribuicaoRepository;

    @Autowired
    private EspacoRepository espacoRepository;

    @Autowired
    private ExpositorRepository expositorRepository;

    @Override
    @Transactional // Garante que tudo acontece ou nada acontece (Atomicidade)
    public Atribuicao atribuir(Long idEspaco, Long idExpositor) {
        // 1. Buscar o Espaço
        Espaco espaco = espacoRepository.findById(idEspaco)
                .orElseThrow(() -> new EntityNotFoundException("Espaço não encontrado: " + idEspaco));

        // 2. Validar se o espaço está VAGO (RN01)
        if (espaco.getStatus() == StatusEspaco.OCUPADO) {
            throw new IllegalStateException("Este espaço já está ocupado por outro expositor.");
        }

        // 3. Buscar o Expositor
        Expositor expositor = expositorRepository.findById(idExpositor)
                .orElseThrow(() -> new EntityNotFoundException("Expositor não encontrado: " + idExpositor));

        // 4. Validar se o expositor está APROVADO (RN02)
        if (expositor.getStatus() != StatusExpositor.APROVADO) {
            throw new IllegalStateException("Apenas expositores APROVADOS podem receber espaços. Status atual: " + expositor.getStatus());
        }

        // 5. Criar a Atribuição
        Atribuicao novaAtribuicao = new Atribuicao();
        novaAtribuicao.setEspaco(espaco);
        novaAtribuicao.setExpositor(expositor);
        novaAtribuicao.setDataAtribuicao(LocalDate.now());

        // 6. Atualizar o status do espaço para OCUPADO
        espaco.setStatus(StatusEspaco.OCUPADO);
        espacoRepository.save(espaco); // Salva a mudança de status

        // 7. Salvar e retornar a atribuição
        return atribuicaoRepository.save(novaAtribuicao);
    }

    @Override
    @Transactional
    public void removerAtribuicao(Long idAtribuicao) {
        Atribuicao atribuicao = atribuicaoRepository.findById(idAtribuicao)
                .orElseThrow(() -> new EntityNotFoundException("Atribuição não encontrada."));

        // Libera o espaço (Volta a ser VAGO)
        Espaco espaco = atribuicao.getEspaco();
        espaco.setStatus(StatusEspaco.VAGO);
        espacoRepository.save(espaco);

        // Remove o registro da atribuição
        atribuicaoRepository.delete(atribuicao);
    }
}