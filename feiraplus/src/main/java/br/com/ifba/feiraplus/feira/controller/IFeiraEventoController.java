package br.com.ifba.feiraplus.feira.controller;

import br.com.ifba.feiraplus.feira.entity.FeiraEvento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFeiraEventoController {

    ResponseEntity<FeiraEvento> save(FeiraEvento feira);

    ResponseEntity<FeiraEvento> update(Long id, FeiraEvento feira);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<FeiraEvento> findById(Long id);

    ResponseEntity<List<FeiraEvento>> findAll();
}
