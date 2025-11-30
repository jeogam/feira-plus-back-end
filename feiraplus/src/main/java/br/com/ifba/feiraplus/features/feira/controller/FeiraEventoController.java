package br.com.ifba.feiraplus.features.feira.controller;

// IMPORTS ATUALIZADOS
import br.com.ifba.feiraplus.features.feira.dto.request.FeiraEventoRequestDTO;
import br.com.ifba.feiraplus.features.feira.dto.response.FeiraEventoResponseDTO;
import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento;
import br.com.ifba.feiraplus.features.feira.exception.FeiraEventoNotFoundException;
import br.com.ifba.feiraplus.features.feira.service.IFeiraEventoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feiras/eventos")
public class FeiraEventoController {

    @Autowired
    private IFeiraEventoService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<FeiraEventoResponseDTO> save(@RequestBody @Valid FeiraEventoRequestDTO dto) {
        FeiraEvento feira = mapper.map(dto, FeiraEvento.class);
        FeiraEvento saved = service.save(feira);

        FeiraEventoResponseDTO response = mapper.map(saved, FeiraEventoResponseDTO.class);

        return ResponseEntity
                .created(URI.create("/feiras/eventos/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeiraEventoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid FeiraEventoRequestDTO dto) {

        FeiraEvento feiraDados = mapper.map(dto, FeiraEvento.class);
        FeiraEvento atualizada = service.update(id, feiraDados);

        return ResponseEntity.ok(mapper.map(atualizada, FeiraEventoResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeiraEventoResponseDTO> findById(@PathVariable Long id) {
        FeiraEvento feira = service.findById(id);
        return ResponseEntity.ok(mapper.map(feira, FeiraEventoResponseDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<FeiraEventoResponseDTO>> findAll() {
        List<FeiraEvento> lista = service.findAll();

        List<FeiraEventoResponseDTO> response = lista.stream()
                .map(feira -> mapper.map(feira, FeiraEventoResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(FeiraEventoNotFoundException.class)
    public ResponseEntity<String> handleNotFound(FeiraEventoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}