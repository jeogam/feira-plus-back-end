package br.com.ifba.feiraplus.feira.service;

import br.com.ifba.feiraplus.feira.entity.FeiraEvento;
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
        return repository.save(feira);
    }

    @Override
    public FeiraEvento update(Long id, FeiraEvento feira) {
        FeiraEvento existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira evento não encontrada"));

        existente.setNome(feira.getNome());
        existente.setLocal(feira.getLocal());
        existente.setHoraAbertura(feira.getHoraAbertura());
        existente.setHoraFechamento(feira.getHoraFechamento());
        existente.setDataInicio(feira.getDataInicio());
        existente.setDataFim(feira.getDataFim());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Feira evento não encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public FeiraEvento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feira evento não encontrada"));
    }

    @Override
    public List<FeiraEvento> findAll() {
        return repository.findAll();
    }
}
