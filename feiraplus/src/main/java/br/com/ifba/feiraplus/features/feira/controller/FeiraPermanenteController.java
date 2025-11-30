package br.com.ifba.feiraplus.features.feira.controller;

// IMPORTS ATUALIZADOS
import br.com.ifba.feiraplus.features.feira.dto.request.FeiraPermanenteRequestDTO;
import br.com.ifba.feiraplus.features.feira.dto.response.FeiraPermanenteResponseDTO;
import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;
import br.com.ifba.feiraplus.features.feira.exception.FeiraPermanenteNotFoundException;
import br.com.ifba.feiraplus.features.feira.service.IFeiraPermanenteService;
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
@RequestMapping("/feiras/permanentes")
public class FeiraPermanenteController {

    @Autowired
    private IFeiraPermanenteService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<FeiraPermanenteResponseDTO> save(@RequestBody @Valid FeiraPermanenteRequestDTO dto) {
        FeiraPermanente feira = mapper.map(dto, FeiraPermanente.class);
        FeiraPermanente saved = service.save(feira);

        FeiraPermanenteResponseDTO response = mapper.map(saved, FeiraPermanenteResponseDTO.class);

        return ResponseEntity
                .created(URI.create("/feiras/permanentes/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeiraPermanenteResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid FeiraPermanenteRequestDTO dto) {

        FeiraPermanente feiraDados = mapper.map(dto, FeiraPermanente.class);
        FeiraPermanente atualizada = service.update(id, feiraDados);

        return ResponseEntity.ok(mapper.map(atualizada, FeiraPermanenteResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeiraPermanenteResponseDTO> findById(@PathVariable Long id) {
        FeiraPermanente feira = service.findById(id);
        return ResponseEntity.ok(mapper.map(feira, FeiraPermanenteResponseDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<FeiraPermanenteResponseDTO>> findAll() {
        List<FeiraPermanente> lista = service.findAll();

        List<FeiraPermanenteResponseDTO> response = lista.stream()
                .map(feira -> mapper.map(feira, FeiraPermanenteResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(FeiraPermanenteNotFoundException.class)
    public ResponseEntity<String> handleNotFound(FeiraPermanenteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}