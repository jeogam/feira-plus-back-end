package br.com.ifba.feiraplus.features.evento.service;

import br.com.ifba.feiraplus.features.evento.dto.request.EventoRequestDTO;
import br.com.ifba.feiraplus.features.evento.entity.Evento;

import java.util.List;

public interface EventoIService {
    Evento save(EventoRequestDTO dto);
    Evento update(Long id, EventoRequestDTO dto);
    void delete(Long id);
    Evento findById(Long id);
    List<Evento> findAll();
    List<Evento> findByFeiraId(Long feiraId);
}
