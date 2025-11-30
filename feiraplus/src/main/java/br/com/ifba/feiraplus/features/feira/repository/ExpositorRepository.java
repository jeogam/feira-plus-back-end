package br.com.ifba.feiraplus.features.feira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifba.feiraplus.features.feira.entity.Expositor;
import br.com.ifba.feiraplus.features.feira.enums.StatusExpositor;

@Repository
public interface ExpositorRepository extends JpaRepository<Expositor, Long> {
    // Útil para listar apenas quem pode receber um espaço (Aprovados)
    List<Expositor> findByStatus(StatusExpositor status);
}