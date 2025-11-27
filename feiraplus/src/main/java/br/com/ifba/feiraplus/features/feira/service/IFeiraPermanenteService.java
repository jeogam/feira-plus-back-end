package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;

import java.util.List;

public interface IFeiraPermanenteService {

  FeiraPermanente save(FeiraPermanente feira);

  FeiraPermanente update(Long id, FeiraPermanente feira);

  void delete(Long id);

  FeiraPermanente findById(Long id);

  List<FeiraPermanente> findAll();
}
