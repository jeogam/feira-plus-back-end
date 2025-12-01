package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;
import br.com.ifba.feiraplus.features.feira.exception.FeiraPermanenteNotFoundException;
import br.com.ifba.feiraplus.features.feira.repository.FeiraPermanenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeiraPermanenteService implements IFeiraPermanenteService {

    @Autowired
    private FeiraPermanenteRepository repository;

    @Override
    public FeiraPermanente save(FeiraPermanente feira) {
        validar(feira);
        FeiraPermanente saved = repository.save(feira);
        auditar("CRIAR", saved.getId());
        return saved;
    }

    @Override
    public FeiraPermanente update(Long id, FeiraPermanente feira) {
        validar(feira);

        FeiraPermanente existente = repository.findById(id)
                .orElseThrow(() -> new FeiraPermanenteNotFoundException(id));

        existente.setNome(feira.getNome());
        existente.setLocal(feira.getLocal());
        existente.setHoraAbertura(feira.getHoraAbertura());
        existente.setHoraFechamento(feira.getHoraFechamento());
        existente.setFrequencia(feira.getFrequencia());

        FeiraPermanente atualizado = repository.save(existente);
        auditar("ATUALIZAR", id);
        return atualizado;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new FeiraPermanenteNotFoundException(id);
        }
        repository.deleteById(id);
        auditar("DELETAR", id);
    }

    @Override
    public FeiraPermanente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FeiraPermanenteNotFoundException(id));
    }

    @Override
    public List<FeiraPermanente> findAll() {
        return repository.findAll();
    }

    private void validar(FeiraPermanente feira) {
        if (feira.getNome() == null || feira.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (feira.getLocal() == null || feira.getLocal().isBlank()) {
            throw new IllegalArgumentException("Local é obrigatório");
        }

        if (feira.getFrequencia() == null) {
            throw new IllegalArgumentException("Frequência é obrigatória");
        }
    }

    private void auditar(String acao, Long id) {
        System.out.println("AÇÃO: " + acao + " FEIRA PERMANENTE: " + id);
    }
}