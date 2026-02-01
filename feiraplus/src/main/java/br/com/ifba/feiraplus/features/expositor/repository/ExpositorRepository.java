package br.com.ifba.feiraplus.features.expositor.repository;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpositorRepository extends JpaRepository<Expositor, Long> {

    Page<Expositor> findByCategoriaNome(String nomeCategoria, Pageable pageable);

    // Query para média global de expositores
    @Query("SELECT COALESCE(AVG(e.nota), 0.0) FROM Expositor e WHERE e.nota IS NOT NULL")
    Double calcularMediaGlobal();
}