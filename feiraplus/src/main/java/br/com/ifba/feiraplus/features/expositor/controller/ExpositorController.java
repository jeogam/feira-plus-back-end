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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expositores")

public class ExpositorController {

    private final ObjectMapperUtil objectMapperUtil;
    private final ExpositorIService expositorIService;

    // --- 1. POST (CRIAR) ---
    // Rota: POST /expositores
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ExpositorGetDto> save(@RequestBody ExpositorPostDto expositorDto) {

        Expositor expositor = objectMapperUtil.map(expositorDto, Expositor.class);
        Expositor expositorSalvo = expositorIService.save(expositor);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapperUtil.map(expositorSalvo, ExpositorGetDto.class));
    }

    // --- 2. GET (BUSCAR TODOS) ---
    // Rota: GET /expositores
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity< List <ExpositorGetDto>> findAll() {

        List<Expositor> expositores = expositorIService.findAll();

        return ResponseEntity.ok()
                .body(objectMapperUtil.mapAll(expositores, ExpositorGetDto.class));
    }

    // --- 3. GET (BUSCAR POR ID) ---
    // Rota: GET /expositores/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ExpositorGetDto> buscarPorId( @PathVariable Long id){

        Expositor expositor = expositorIService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.map(expositor, ExpositorGetDto.class));
    }

    // --- 4. PUT (ATUALIZAR) ---
    // Rota: PUT /expositores/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ExpositorGetDto> update(@PathVariable Long id, @RequestBody ExpositorPostDto expositorDto) {

        Expositor expositor = objectMapperUtil.map(expositorDto, Expositor.class);
        expositor.setId(id); // Define o ID para o Service saber qual atualizar

        Expositor expositorAtualizado = expositorIService.save(expositor);

        return ResponseEntity.ok(objectMapperUtil.map(expositorAtualizado, ExpositorGetDto.class));
    }

    // --- 5. DELETE (DELETAR) ---
    // Rota: DELETE /expositores/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete( @PathVariable Long id ) {
        expositorIService.delete(id);

        // Retorno 204 No Content
        return ResponseEntity.noContent().build();
    }

    // --- 6. EXCEPTION HANDLER ---
    // Mapeia a exceção de "Não Encontrado" para o status 404
    @ExceptionHandler(ExpositorNaoEncontrado.class)
    public ResponseEntity<String> handleNotFound(ExpositorNaoEncontrado e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}