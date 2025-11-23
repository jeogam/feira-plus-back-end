package br.com.ifba.feiraplus.feira.service;

import br.com.ifba.feiraplus.feira.entity.FeiraEvento;
import br.com.ifba.feiraplus.feira.exception.FeiraEventoNotFoundException;
import br.com.ifba.feiraplus.feira.repository.FeiraEventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeiraEventoService implements IFeiraEventoService {

    @Autowired
    private FeiraEventoRepository repository;

    @Override
    public FeiraEvento save(FeiraEvento feira) {
        validar(feira);
        FeiraEvento saved = repository.save(feira);
        auditar("CRIAR", saved.getId());
        return saved;
    }

    @Override
    public FeiraEvento update(Long id, FeiraEvento feira) {
        validar(feira);

        FeiraEvento existente = repository.findById(id)
                .orElseThrow(() -> new FeiraEventoNotFoundException(id));

        existente.setNome(feira.getNome());
        existente.setLocal(feira.getLocal());
        existente.setHoraAbertura(feira.getHoraAbertura());
        existente.setHoraFechamento(feira.getHoraFechamento());
        existente.setDataInicio(feira.getDataInicio());
        existente.setDataFim(feira.getDataFim());

        FeiraEvento atualizado = repository.save(existente);
        auditar("ATUALIZAR", id);
        return atualizado;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new FeiraEventoNotFoundException(id);
        }
        repository.deleteById(id);
        auditar("DELETAR", id);
    }

    @Override
    public FeiraEvento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FeiraEventoNotFoundException(id));
    }

    @Override
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

        if (feira.getDataInicio() != null && feira.getDataFim() != null &&
                feira.getDataFim().isBefore(feira.getDataInicio())) {
            throw new IllegalArgumentException("Data fim não pode ser antes da data início");
        }
    }

    private void auditar(String acao, Long id) {
        System.out.println("AÇÃO: " + acao + " FEIRA: " + id);
    }
}
