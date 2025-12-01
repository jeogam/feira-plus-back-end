package br.com.ifba.feiraplus.features.espaco.service;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import br.com.ifba.feiraplus.features.espaco.repository.EspacoRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspacoService implements EspacoIService {

    private final EspacoRepository espacoRepository;

    @Override
    public List<Espaco> findAll() {
        return espacoRepository.findAll();
    }

    @Override
    public Espaco save(Espaco espaco) {
        return espacoRepository.save(espaco);
    }

    @Override
    public Espaco update(Espaco espaco) {
        // Verifica se existe antes de atualizar
        findById(espaco.getId());
        return espacoRepository.save(espaco);
    }

    @Override
    public void delete(Long id) {
        Espaco espaco = findById(id);
        espacoRepository.delete(espaco);
    }

    @Override
    public Espaco findById(Long id) {
        return espacoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Espaço não encontrado!"));
    }
}