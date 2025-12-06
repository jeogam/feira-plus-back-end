package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpositorIService {

    public List<Expositor> findAll();

    public Expositor save(ExpositorPostDto expositorDto);

    public Expositor findById(Long id);

    public void delete(Long id);

    Page<Expositor> buscarPorCategoria(String nomeCategoria, Pageable pageable);

    Expositor update(Long id, ExpositorPostDto expositorDto);}
