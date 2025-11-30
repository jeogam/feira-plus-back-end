package br.com.ifba.feiraplus.features.espaco.service;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import br.com.ifba.feiraplus.features.espaco.exception.EspacoNotFoundException;
import br.com.ifba.feiraplus.features.espaco.repository.EspacoRepository;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
// Importante: Precisamos de um Repository de Feira (pode ser genérico) para validar a existência
import br.com.ifba.feiraplus.features.feira.repository.FeiraEventoRepository;
// OBS: Idealmente seria um IFeiraRepository genérico, mas usarei o pattern do projeto.
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacoService implements IEspacoService {

    @Autowired
    private EspacoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Espaco save(Espaco espaco) {
        validar(espaco);

        // Verifica se a Feira existe antes de salvar o Espaço
        if (espaco.getFeira() != null && espaco.getFeira().getId() != null) {
            Feira feiraExistente = entityManager.find(Feira.class, espaco.getFeira().getId());
            if (feiraExistente == null) {
                throw new IllegalArgumentException("A Feira informada não existe: " + espaco.getFeira().getId());
            }
            espaco.setFeira(feiraExistente);
        } else {
            throw new IllegalArgumentException("É necessário informar uma Feira para o Espaço.");
        }

        Espaco saved = repository.save(espaco);
        auditar("CRIAR", saved.getId());
        return saved;
    }

    @Override
    public Espaco update(Long id, Espaco espaco) {
        validar(espaco);

        Espaco existente = repository.findById(id)
                .orElseThrow(() -> new EspacoNotFoundException(id));

        existente.setNome(espaco.getNome());
        existente.setLocal(espaco.getLocal());
        existente.setDataInicio(espaco.getDataInicio());
        existente.setDataFim(espaco.getDataFim());
        existente.setHorarios(espaco.getHorarios());

        Espaco atualizado = repository.save(existente);
        auditar("ATUALIZAR", id);
        return atualizado;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EspacoNotFoundException(id);
        }
        repository.deleteById(id);
        auditar("DELETAR", id);
    }

    @Override
    public Espaco findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EspacoNotFoundException(id));
    }

    @Override
    public List<Espaco> findAll() {
        return repository.findAll();
    }

    private void validar(Espaco espaco) {
        if (espaco.getNome() == null || espaco.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do espaço é obrigatório");
        }

        if (espaco.getLocal() == null || espaco.getLocal().isBlank()) {
            throw new IllegalArgumentException("Local do espaço é obrigatório");
        }

        if (espaco.getHorarios() == null || espaco.getHorarios().isBlank()) {
            throw new IllegalArgumentException("Horários são obrigatórios");
        }

        if (espaco.getDataInicio() != null && espaco.getDataFim() != null &&
                espaco.getDataFim().isBefore(espaco.getDataInicio())) {
            throw new IllegalArgumentException("Data fim não pode ser antes da data início");
        }
    }

    private void auditar(String acao, Long id) {
        System.out.println("AÇÃO: " + acao + " ESPACO: " + id);
    }
}