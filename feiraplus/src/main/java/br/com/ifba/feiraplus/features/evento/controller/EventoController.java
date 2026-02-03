package br.com.ifba.feiraplus.features.evento.controller;

import java.util.List;

import br.com.ifba.feiraplus.features.evento.dto.request.EventoRequestDTO;
import br.com.ifba.feiraplus.features.evento.entity.Evento;
import br.com.ifba.feiraplus.features.evento.service.EventoService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    // Construtor Manual
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Evento> cadastrar(@RequestBody @Valid EventoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.save(dto));
    }

    @GetMapping("/feira/{feiraId}")
    public ResponseEntity<List<Evento>> listarPorFeira(@PathVariable Long feiraId) {
        // Agora o compilador encontrará este método no Service
        List<Evento> eventos = eventoService.findByFeiraId(feiraId);
        return ResponseEntity.ok(eventos);
    }
}