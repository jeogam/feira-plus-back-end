package br.com.ifba.feiraplus.features.evento.repository;

import br.com.ifba.feiraplus.features.evento.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Busca todos os eventos de uma feira específica
    List<Evento> findByFeiraId(Long feiraId);
}