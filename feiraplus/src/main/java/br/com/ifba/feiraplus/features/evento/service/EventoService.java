package br.com.ifba.feiraplus.features.evento.service;

import br.com.ifba.feiraplus.features.evento.entity.Evento;
import br.com.ifba.feiraplus.features.evento.repository.EventoRepository;
import br.com.ifba.feiraplus.features.evento.dto.request.EventoRequestDTO;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.repository.FeiraRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService implements  EventoIService{
    private final EventoRepository eventoRepository;
    private final FeiraRepository feiraRepository; // Precisamos disso para validar a Feira
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public Evento save(EventoRequestDTO dto) {
        // 1. Converte DTO para Entidade
        Evento evento = objectMapperUtil.map(dto, Evento.class);

        // 2. Busca a Feira pelo ID (Obrigatorio para o relacionamento)
        Feira feira = feiraRepository.findById(dto.getFeiraId())
                .orElseThrow(() -> new RuntimeException("Feira não encontrada com ID: " + dto.getFeiraId()));

        // 3. Vincula
        evento.setFeira(feira);

        // 4. Salva
        return eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public Evento update(Long id, EventoRequestDTO dto) {
        // 1. Busca o evento existente
        Evento eventoExistente = findById(id);

        // 2. Atualiza os dados simples
        eventoExistente.setTitulo(dto.getTitulo());
        eventoExistente.setDescricao(dto.getDescricao());
        eventoExistente.setDataHoraInicio(dto.getDataHoraInicio());
        eventoExistente.setDataHoraFim(dto.getDataHoraFim());

        // 3. Se a feira mudou, atualiza o relacionamento
        if (!eventoExistente.getFeira().getId().equals(dto.getFeiraId())) {
            Feira novaFeira = feiraRepository.findById(dto.getFeiraId())
                    .orElseThrow(() -> new RuntimeException("Nova Feira não encontrada"));
            eventoExistente.setFeira(novaFeira);
        }

        return eventoRepository.save(eventoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado para exclusão");
        }
        eventoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Evento findById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evento> findByFeiraId(Long feiraId) {
        // Certifique-se que seu Repository tem: List<Evento> findByFeiraId(Long feiraId);
        return eventoRepository.findByFeiraId(feiraId);
    }
    }
