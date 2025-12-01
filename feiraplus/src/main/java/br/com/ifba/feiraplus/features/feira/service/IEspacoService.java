package br.com.ifba.feiraplus.features.feira.service;

import java.util.List;

import br.com.ifba.feiraplus.features.feira.entity.Espaco;

public interface IEspacoService {
    Espaco save(Long feiraId, Espaco espaco);
    Espaco update(Long id, Espaco espaco);
    void delete(Long id);
    Espaco findById(Long id);
    List<Espaco> findAllByFeiraId(Long feiraId);
}