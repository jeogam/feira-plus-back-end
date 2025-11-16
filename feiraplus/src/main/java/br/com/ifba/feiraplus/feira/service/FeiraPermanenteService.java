package br.com.ifba.feiraplus.feira.service;

import br.com.ifba.feiraplus.feira.entity.FeiraPermanente;
import br.com.ifba.feiraplus.feira.repository.FeiraPermanenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeiraPermanenteService implements IFeiraPermanenteService {

    @Autowired
    private FeiraPermanenteRepository repository;

    @Override
    public FeiraPermanente save(FeiraPermanente feira) {
        return repository.save(feira);
    }

    @Override
    public FeiraPermanente update(Long id, FeiraPermanente feira) {
        FeiraPermanente existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira permanente não encontrada"));

        existente.setNome(feira.getNome());
        existente.setLocal(feira.getLocal());
        existente.setHoraAbertura(feira.getHoraAbertura());
        existente.setHoraFechamento(feira.getHoraFechamento());
        existente.setFrequencia(feira.getFrequencia());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Feira permanente não encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public FeiraPermanente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira permanente não encontrada"));
    }

    @Override
    public List<FeiraPermanente> findAll() {
        return repository.findAll();
    }
}
