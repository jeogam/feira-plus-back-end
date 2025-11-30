package br.com.ifba.feiraplus.features.espaco.service;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import java.util.List;

public interface IEspacoService {
    Espaco save(Espaco espaco);
    Espaco update(Long id, Espaco espaco);
    void delete(Long id);
    Espaco findById(Long id);
    List<Espaco> findAll();
}