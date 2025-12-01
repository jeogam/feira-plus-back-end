package br.com.ifba.feiraplus.features.espaco.repository;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long> {
}