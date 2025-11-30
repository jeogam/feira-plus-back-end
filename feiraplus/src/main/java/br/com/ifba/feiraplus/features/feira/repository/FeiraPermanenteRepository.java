package br.com.ifba.feiraplus.features.feira.repository;

import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeiraPermanenteRepository extends JpaRepository<FeiraPermanente, Long> {

}
