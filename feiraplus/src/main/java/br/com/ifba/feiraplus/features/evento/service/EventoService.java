package br.com.ifba.feiraplus.features.evento.service;

import br.com.ifba.feiraplus.features.evento.entity.Evento;
import br.com.ifba.feiraplus.features.evento.repository.EventoRepository;
import br.com.ifba.feiraplus.features.evento.dto.request.EventoRequestDTO;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.repository.FeiraRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final FeiraRepository feiraRepository;

    public EventoService(EventoRepository eventoRepository, FeiraRepository feiraRepository) {
        this.eventoRepository = eventoRepository;
        this.feiraRepository = feiraRepository;
    }

    // MÉTODO QUE ESTAVA FALTANDO
    @Transactional(readOnly = true)
    public List<Evento> findByFeiraId(Long feiraId) {
        return eventoRepository.findByFeiraId(feiraId);
    }

    @Transactional
    public Evento save(EventoRequestDTO dto) {
        Feira feira = feiraRepository.findById(dto.getFeiraId())
                .orElseThrow(() -> new BusinessException("Feira não encontrada"));

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataHoraInicio(dto.getDataHoraInicio());
        evento.setDataHoraFim(dto.getDataHoraFim());
        evento.setFeira(feira);

        return eventoRepository.save(evento);
    }
}