package br.com.ifba.feiraplus.features.feira.controller;

import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFeiraPermanenteController {

  ResponseEntity<FeiraPermanente> save(FeiraPermanente feira);

  ResponseEntity<FeiraPermanente> update(Long id, FeiraPermanente feira);

  ResponseEntity<Void> delete(Long id);

  ResponseEntity<FeiraPermanente> findById(Long id);

  ResponseEntity<List<FeiraPermanente>> findAll();
}
