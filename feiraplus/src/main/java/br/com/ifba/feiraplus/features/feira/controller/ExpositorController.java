package br.com.ifba.feiraplus.features.feira.controller;

import br.com.ifba.feiraplus.features.feira.entity.Expositor;
import br.com.ifba.feiraplus.features.feira.repository.ExpositorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expositores")
public class ExpositorController {

    @Autowired
    private ExpositorRepository repository;

    @PostMapping
    public Expositor save(@RequestBody Expositor expositor) {
        return repository.save(expositor);
    }

    @GetMapping
    public List<Expositor> findAll() {
        return repository.findAll();
    }
}