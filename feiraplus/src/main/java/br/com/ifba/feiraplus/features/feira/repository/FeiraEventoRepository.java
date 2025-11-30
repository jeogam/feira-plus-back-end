package br.com.ifba.feiraplus.features.feira.repository;

import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeiraEventoRepository extends JpaRepository<FeiraEvento, Long> {

}
