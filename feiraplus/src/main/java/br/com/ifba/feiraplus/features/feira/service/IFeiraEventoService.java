package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento;

import java.util.List;

public interface IFeiraEventoService {

  FeiraEvento save(FeiraEvento feira);

  FeiraEvento update(Long id, FeiraEvento feira);

  void delete(Long id);

  FeiraEvento findById(Long id);

  List<FeiraEvento> findAll();
}
