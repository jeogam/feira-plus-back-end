package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;

import java.util.List;

public interface ExpositorIService {

    public List<Expositor> findAll();

    public Expositor save(Expositor expositor);
}
