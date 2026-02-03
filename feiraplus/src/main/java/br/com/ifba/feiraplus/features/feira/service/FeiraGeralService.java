package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.repository.FeiraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeiraGeralService {

    private final FeiraRepository feiraRepository;

    public FeiraGeralService(FeiraRepository feiraRepository) {
        this.feiraRepository = feiraRepository;
    }

    @Transactional(readOnly = true)
    public List<Feira> pesquisarPorArtesaoOuCategoria(String termo) {
        if (termo == null || termo.isBlank()) {
            return feiraRepository.findAll();
        }
        return feiraRepository.buscarPorArtesaoOuCategoria(termo);
    }

    // --- NOVO MÉTODO ---
    public Double getMediaGeralAvaliacoes() {
        return feiraRepository.calcularMediaGlobal();
    }

    public List<Feira> findAll() {
        return feiraRepository.findAll();
    }
}