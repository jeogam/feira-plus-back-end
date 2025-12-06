package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.exception.ExpositorNaoEncontrado;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.expositor.service.ExpositorIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpositorService implements ExpositorIService {

    private final ExpositorRepository repository;

    // CREATE
    @Override
    public Expositor save(Expositor expositor) {
        expositor.setId(null); // Garantir que é criação
        return repository.save(expositor);
    }

    // UPDATE
    @Override
    public Expositor update(Long id, Expositor novosDados) {
        Expositor existente = repository.findById(id)
                .orElseThrow(() -> new ExpositorNaoEncontrado("Expositor não encontrado!"));

        existente.setNome(novosDados.getNome());
        existente.setEmail(novosDados.getEmail());
        existente.setTelefone(novosDados.getTelefone());
        // Adicione outros campos caso existam

        return repository.save(existente);
    }

    @Override
    public List<Expositor> findAll() {
        return repository.findAll();
    }

    @Override
    public Expositor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ExpositorNaoEncontrado("Expositor não encontrado!"));
    }

    @Override
    public void delete(Long id) {
        Expositor existente = findById(id);
        repository.delete(existente);
    }
}
