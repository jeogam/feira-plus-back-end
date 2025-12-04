package br.com.ifba.feiraplus.features.expositor.repository;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpositorRepository extends JpaRepository<Expositor,Long> {

    Page<Expositor> findByCategoriaNome(String nomeCategoria, Pageable pageable);
}
