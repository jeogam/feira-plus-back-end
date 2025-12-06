package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;

import java.util.List;

public interface ExpositorIService {

    Expositor save(Expositor expositor);      // Create
    Expositor update(Long id, Expositor expositor); // Update
    List<Expositor> findAll();
    Expositor findById(Long id);
    void delete(Long id);
}
