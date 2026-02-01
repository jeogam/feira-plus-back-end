package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento;
import br.com.ifba.feiraplus.features.feira.exception.FeiraEventoNotFoundException;
import br.com.ifba.feiraplus.features.feira.repository.FeiraEventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeiraEventoService implements IFeiraEventoService {

    private final FeiraEventoRepository repository;
    private final ExpositorRepository expositorRepository;

    // Método auxiliar para vincular expositores via IDs
    private void sincronizarExpositores(FeiraEvento feira, List<Long> expositorIds) {
        if (expositorIds == null || expositorIds.isEmpty()) {
            // Se a lista for nula ou vazia, limpa os expositores (opcional, depende da regra de negócio)
            // feira.setExpositores(new ArrayList<>());
            return;
        }

        List<Expositor> expositores = expositorRepository.findAllById(expositorIds);
        feira.setExpositores(expositores);
    }

    @Override
    @Transactional
    public FeiraEvento save(FeiraEvento feira) {
        validar(feira);

        // Vincula os expositores antes de salvar
        sincronizarExpositores(feira, feira.getExpositorIds());

        FeiraEvento saved = repository.save(feira);
        auditar("CRIAR", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public FeiraEvento update(Long id, FeiraEvento feira) {
        validar(feira);

        FeiraEvento existente = repository.findById(id)
                .orElseThrow(() -> new FeiraEventoNotFoundException(id));

        // Atualiza campos
        existente.setNome(feira.getNome());
        existente.setLocal(feira.getLocal());
        existente.setEspacos(feira.getEspacos());
        existente.setHoraAbertura(feira.getHoraAbertura());
        existente.setHoraFechamento(feira.getHoraFechamento());
        existente.setDataInicio(feira.getDataInicio());
        existente.setDataFim(feira.getDataFim());
        existente.setFoto(feira.getFoto());

        // Atualiza a Nota
        existente.setNota(feira.getNota());

        // Atualiza Expositores
        sincronizarExpositores(existente, feira.getExpositorIds());

        FeiraEvento atualizado = repository.save(existente);
        auditar("ATUALIZAR", id);
        return atualizado;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FeiraEvento feira = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feira não encontrada"));

        // Remove associações ManyToMany para evitar erro de FK
        for (Expositor expositor : feira.getExpositores()) {
            expositor.getFeiras().remove(feira);
        }
        feira.getExpositores().clear();

        repository.delete(feira);
    }

    @Override
    @Transactional(readOnly = true)
    public FeiraEvento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FeiraEventoNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeiraEvento> findAll() {
        return repository.findAll();
    }

    private void validar(FeiraEvento feira) {
        if (feira.getNome() == null || feira.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (feira.getLocal() == null || feira.getLocal().isBlank()) {
            throw new IllegalArgumentException("Local é obrigatório");
        }
        if (feira.getEspacos() < 0) {
            throw new IllegalArgumentException("O total de espaços não pode ser negativo");
        }
        if (feira.getDataInicio() != null && feira.getDataFim() != null &&
                feira.getDataFim().isBefore(feira.getDataInicio())) {
            throw new IllegalArgumentException("Data fim não pode ser antes da data início");
        }

        // Validação da Nota
        if (feira.getNota() != null && (feira.getNota() < 1 || feira.getNota() > 5)) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
    }

    private void auditar(String acao, Long id) {
        // Log simples (pode ser substituído por SLF4J)
        System.out.println("AÇÃO: " + acao + " FEIRA EVENTO: " + id);
    }
}