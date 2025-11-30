package br.com.ifba.feiraplus.features.feira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifba.feiraplus.features.feira.entity.Espaco;
import br.com.ifba.feiraplus.features.feira.enums.StatusEspaco;

@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long> {
    // Busca espaços por Feira (útil para listagem)
    List<Espaco> findByFeiraId(Long feiraId);
    
    // Busca apenas espaços vagos de uma feira (útil para o UC03 depois)
    List<Espaco> findByFeiraIdAndStatus(Long feiraId, StatusEspaco status);
}