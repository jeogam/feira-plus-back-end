package br.com.ifba.feiraplus.features.evento.controller;

import br.com.ifba.feiraplus.features.evento.dto.request.EventoRequestDTO;
import br.com.ifba.feiraplus.features.evento.dto.response.EventoResponseDTO; // Crie este DTO
import br.com.ifba.feiraplus.features.evento.entity.Evento;
import br.com.ifba.feiraplus.features.evento.service.EventoService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil; // Utilitário de mapeamento
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor // 1. Injeção de dependência automática (Lombok)
public class EventoController {

    private final EventoService eventoService;
    private final ObjectMapperUtil objectMapperUtil; // Para converter Entity -> ResponseDTO



    // --- CADASTRAR (Também precisa devolver com o nome da feira) ---
    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventoResponseDTO> cadastrar(@RequestBody @Valid EventoRequestDTO dto) {
        Evento eventoSalvo = eventoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(eventoSalvo));
    }

    // --- ATUALIZAR ---
    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EventoRequestDTO dto) {
        Evento eventoAtualizado = eventoService.update(id, dto); // Você precisará criar o update no Service
        return ResponseEntity.ok(objectMapperUtil.map(eventoAtualizado, EventoResponseDTO.class));
    }

    // --- DELETAR ---
    @DeleteMapping("/remover/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        eventoService.delete(id); // Você precisará criar o delete no Service
        return ResponseEntity.noContent().build();
    }

    // --- LISTAR TODOS (Geral) ---
    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarTodos() {
        List<Evento> eventos = eventoService.findAll();

        // Usa o método auxiliar para converter lista
        List<EventoResponseDTO> dtos = eventos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // --- LISTAR POR FEIRA (Atualizado) ---
    @GetMapping("/feira/{feiraId}")
    public ResponseEntity<List<EventoResponseDTO>> listarPorFeira(@PathVariable Long feiraId) {
        List<Evento> eventos = eventoService.findByFeiraId(feiraId);

        List<EventoResponseDTO> dtos = eventos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // --- BUSCAR POR ID (Único) ---
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        Evento evento = eventoService.findById(id); // Crie findById no Service
        return ResponseEntity.ok(objectMapperUtil.map(evento, EventoResponseDTO.class));

    }

    // --- MÉTODO AUXILIAR PARA CONVERTER E PREENCHER DADOS DA FEIRA ---
    private EventoResponseDTO toResponseDTO(Evento evento) {
        // 1. Faz a cópia padrão dos dados simples (titulo, datas, etc)
        EventoResponseDTO dto = objectMapperUtil.map(evento, EventoResponseDTO.class);

        // 2. Preenche manualmente os dados da Feira (para garantir que não venham nulos)
        if (evento.getFeira() != null) {
            dto.setFeiraId(evento.getFeira().getId());
            dto.setFeiraNome(evento.getFeira().getNome());
        }

        return dto;
    }
}

