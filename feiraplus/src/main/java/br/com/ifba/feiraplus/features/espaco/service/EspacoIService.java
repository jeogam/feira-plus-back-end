package br.com.ifba.feiraplus.features.espaco.service;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import java.util.List;

public interface EspacoIService {
    List<Espaco> findAll();
    Espaco save(Espaco espaco);
    Espaco update(Espaco espaco);
    void delete(Long id);
    Espaco findById(Long id);
}