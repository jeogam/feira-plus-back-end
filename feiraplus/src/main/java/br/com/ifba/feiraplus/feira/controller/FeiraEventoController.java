package br.com.ifba.feiraplus.feira.controller;

import br.com.ifba.feiraplus.feira.entity.FeiraEvento;
import br.com.ifba.feiraplus.feira.service.IFeiraEventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feiras/eventos")
public class FeiraEventoController implements IFeiraEventoController {

    @Autowired
    private IFeiraEventoService service;

    @Override
    @PostMapping
    public ResponseEntity<FeiraEvento> save(@RequestBody FeiraEvento feira) {
        FeiraEvento novaFeira = service.save(feira);
        return ResponseEntity.ok(novaFeira);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FeiraEvento> update(
            @PathVariable Long id,
            @RequestBody FeiraEvento feira) {

        FeiraEvento atualizada = service.update(id, feira);
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
    public ResponseEntity<FeiraEvento> findById(@PathVariable Long id) {
        FeiraEvento feira = service.findById(id);
        return ResponseEntity.ok(feira);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<FeiraEvento>> findAll() {
        List<FeiraEvento> feiras = service.findAll();
        return ResponseEntity.ok(feiras);
    }
}
