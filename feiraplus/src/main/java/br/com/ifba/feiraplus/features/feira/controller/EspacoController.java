package br.com.ifba.feiraplus.features.feira.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.feiraplus.features.feira.entity.Espaco;
import br.com.ifba.feiraplus.features.feira.service.IEspacoService;

@RestController
@RequestMapping("/espacos")
public class EspacoController {

    @Autowired
    private IEspacoService service;

    // Criar espaço vinculado a uma feira
    @PostMapping("/feira/{feiraId}")
    public ResponseEntity<Espaco> save(@PathVariable Long feiraId, @RequestBody Espaco espaco) {
        Espaco novoEspaco = service.save(feiraId, espaco);
        return ResponseEntity
                .created(URI.create("/espacos/" + novoEspaco.getId()))
                .body(novoEspaco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espaco> update(@PathVariable Long id, @RequestBody Espaco espaco) {
        return ResponseEntity.ok(service.update(id, espaco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espaco> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/feira/{feiraId}")
    public ResponseEntity<List<Espaco>> findAllByFeira(@PathVariable Long feiraId) {
        return ResponseEntity.ok(service.findAllByFeiraId(feiraId));
    }
}