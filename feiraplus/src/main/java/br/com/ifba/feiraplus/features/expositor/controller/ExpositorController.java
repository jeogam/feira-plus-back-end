package br.com.ifba.feiraplus.features.expositor.controller;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.exception.ExpositorNaoEncontrado;
import br.com.ifba.feiraplus.features.expositor.service.ExpositorIService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expositores")
public class ExpositorController {

    private final ObjectMapperUtil mapper;
    private final ExpositorIService service;

    @PostMapping
    public ResponseEntity<ExpositorGetDto> save(@RequestBody ExpositorPostDto dto) {
        Expositor expositor = mapper.map(dto, Expositor.class);
        Expositor saved = service.save(expositor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.map(saved, ExpositorGetDto.class));
    }

    @GetMapping
    public ResponseEntity<List<ExpositorGetDto>> findAll() {
        List<Expositor> list = service.findAll();
        return ResponseEntity.ok(mapper.mapAll(list, ExpositorGetDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpositorGetDto> findById(@PathVariable Long id) {
        Expositor expositor = service.findById(id);
        return ResponseEntity.ok(mapper.map(expositor, ExpositorGetDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpositorGetDto> update(
            @PathVariable Long id,
            @RequestBody ExpositorPostDto dto
    ) {
        Expositor novosDados = mapper.map(dto, Expositor.class);
        Expositor updated = service.update(id, novosDados);

        return ResponseEntity.ok(mapper.map(updated, ExpositorGetDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ExpositorNaoEncontrado.class)
    public ResponseEntity<String> handleNotFound(ExpositorNaoEncontrado e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
