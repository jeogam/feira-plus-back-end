package br.com.ifba.feiraplus.feira.controller;

import br.com.ifba.feiraplus.feira.entity.FeiraPermanente;
import br.com.ifba.feiraplus.feira.service.IFeiraPermanenteService;
import br.com.ifba.feiraplus.feira.exception.FeiraPermanenteNotFoundException;

import java.net.URI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feiras/permanentes")
public class FeiraPermanenteController implements IFeiraPermanenteController {

  @Autowired
  private IFeiraPermanenteService service;

  @Override
  @PostMapping
  public ResponseEntity<FeiraPermanente> save(@RequestBody FeiraPermanente feira) {
    FeiraPermanente novaFeira = service.save(feira);
    return ResponseEntity
            .created(URI.create("/feiras/permanentes/" + novaFeira.getId()))
            .body(novaFeira);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<FeiraPermanente> update(
          @PathVariable Long id,
          @RequestBody FeiraPermanente feira) {

    FeiraPermanente atualizada = service.update(id, feira);
    return ResponseEntity.ok(atualizada);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<FeiraPermanente> findById(@PathVariable Long id) {
    FeiraPermanente feira = service.findById(id);
    return ResponseEntity.ok(feira);
  }

  @Override
  @GetMapping
  public ResponseEntity<List<FeiraPermanente>> findAll() {
    List<FeiraPermanente> feiras = service.findAll();
    return ResponseEntity.ok(feiras);
  }
}
