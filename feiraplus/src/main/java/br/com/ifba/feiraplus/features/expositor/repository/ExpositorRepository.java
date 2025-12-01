package br.com.ifba.feiraplus.features.expositor.repository;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpositorRepository extends JpaRepository<Expositor,Long> {
}
