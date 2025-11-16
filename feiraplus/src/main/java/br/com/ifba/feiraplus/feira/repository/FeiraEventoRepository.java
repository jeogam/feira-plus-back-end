package br.com.ifba.feiraplus.feira.repository;

import br.com.ifba.feiraplus.feira.entity.FeiraEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeiraEventoRepository extends JpaRepository<FeiraEvento, Long> {

}
