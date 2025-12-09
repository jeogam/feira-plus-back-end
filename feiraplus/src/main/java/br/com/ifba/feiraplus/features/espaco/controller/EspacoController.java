package br.com.ifba.feiraplus.features.espaco.controller;

import br.com.ifba.feiraplus.features.espaco.dto.request.EspacoRequestDTO;
import br.com.ifba.feiraplus.features.espaco.dto.response.EspacoResponseDTO;
import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import br.com.ifba.feiraplus.features.espaco.exception.EspacoNotFoundException;
import br.com.ifba.feiraplus.features.espaco.service.IEspacoService;
import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento; // Usando FeiraEvento ou Feira genérica como stub
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/espacos")
public class EspacoController {

    private final IEspacoService service;

    private final ModelMapper mapper;

    public EspacoController(IEspacoService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EspacoResponseDTO> save(@RequestBody @Valid EspacoRequestDTO dto) {
        Espaco espaco = mapper.map(dto, Espaco.class);

        // Mapeamento manual do ID da Feira (pois o ModelMapper pode não fazer automático de Long -> Objeto)
        if (dto.getFeiraId() != null) {
            FeiraEvento feiraStub = new FeiraEvento(); // Stub apenas para carregar o ID
            feiraStub.setId(dto.getFeiraId());
            espaco.setFeira(feiraStub);
        }

        Espaco saved = service.save(espaco);
        EspacoResponseDTO response = mapper.map(saved, EspacoResponseDTO.class);

        // Garante que o ID da feira volte no response
        if(saved.getFeira() != null) {
            response.setFeiraId(saved.getFeira().getId());
        }

        return ResponseEntity
                .created(URI.create("/espacos/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspacoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid EspacoRequestDTO dto) {
        Espaco espaco = mapper.map(dto, Espaco.class);
        Espaco atualizado = service.update(id, espaco);

        EspacoResponseDTO response = mapper.map(atualizado, EspacoResponseDTO.class);
        if(atualizado.getFeira() != null) {
            response.setFeiraId(atualizado.getFeira().getId());
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacoResponseDTO> findById(@PathVariable Long id) {
        Espaco espaco = service.findById(id);
        EspacoResponseDTO response = mapper.map(espaco, EspacoResponseDTO.class);

        if(espaco.getFeira() != null) {
            response.setFeiraId(espaco.getFeira().getId());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EspacoResponseDTO>> findAll() {
        List<Espaco> lista = service.findAll();
        List<EspacoResponseDTO> response = lista.stream()
                .map(e -> {
                    EspacoResponseDTO dto = mapper.map(e, EspacoResponseDTO.class);
                    if(e.getFeira() != null) dto.setFeiraId(e.getFeira().getId());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(EspacoNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EspacoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}