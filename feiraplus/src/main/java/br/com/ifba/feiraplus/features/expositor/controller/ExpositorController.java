package br.com.ifba.feiraplus.features.expositor.controller;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.exception.ExpositorNaoEncontrado;
import br.com.ifba.feiraplus.features.expositor.mapper.ExpositorMapper;
import br.com.ifba.feiraplus.features.expositor.service.ExpositorIService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expositores")
public class ExpositorController {

    private final ObjectMapperUtil objectMapperUtil;
    private final ExpositorIService expositorIService;
    private final ExpositorMapper expositorMapper;



    // --- MÉTODO AUXILIAR---
    // Centraliza a conversão para não esquecer a categoria em nenhum lugar
    private ExpositorGetDto converterParaDto(Expositor expositor) {
        // 1. Converte os dados básicos (nome, status, doc)
        ExpositorGetDto dto = objectMapperUtil.map(expositor, ExpositorGetDto.class);

        // 2. Preenche manualmente os dados da Categoria
        if (expositor.getCategoria() != null) {
            dto.setCategoriaId(expositor.getCategoria().getId());
            dto.setCategoriaNome(expositor.getCategoria().getNome());
        }

        return dto;
    }

    // --- 1. POST (CRIAR) ---

    @PostMapping("/register")
    public ResponseEntity<ExpositorGetDto> save(@RequestBody @Valid ExpositorPostDto expositorDto) {
        Expositor expositorSalvo = expositorIService.save(expositorDto);
        // Usa o método auxiliar
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDto(expositorSalvo));
    }

    // --- 2. GET (BUSCAR TODOS) ---

        @GetMapping("/buscar-todos")
    public ResponseEntity<List<ExpositorGetDto>> findAll() {
        List<Expositor> expositores = expositorIService.findAll();

        // Converte a lista usando Stream e o nosso método auxiliar
            List<ExpositorGetDto> dtos = expositores.stream()
                    .map(expositorMapper::toDto)
                    .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // --- 3. GET (BUSCAR POR ID) ---

    @GetMapping("/{id}")
    public ResponseEntity<ExpositorGetDto> buscarPorId(@PathVariable Long id) {
        Expositor expositor = expositorIService.findById(id);
        // Usa o método auxiliar
        return ResponseEntity.status(HttpStatus.OK).body(converterParaDto(expositor));
    }

    // --- 4. PUT (ATUALIZAR) ---

    @PutMapping("update/{id}")
    public ResponseEntity<ExpositorGetDto> update(@PathVariable Long id,
                                                  @RequestBody @Valid ExpositorPostDto expositorDto) {
        Expositor expositorAtualizado = expositorIService.update(id, expositorDto);
        // Agora ficou limpo, pois a lógica foi para o método converterParaDto
        return ResponseEntity.ok(converterParaDto(expositorAtualizado));
    }

    // --- 5. DELETE (DELETAR) ---

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expositorIService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- BUSCAR POR CATEGORIA PAGINADO ---

    @GetMapping("/buscar-por-categoria")
    public ResponseEntity<Page<ExpositorGetDto>> buscarPorNomeCategoria(
            @RequestParam String nomeCategoria,
            Pageable pageable) {

        Page<Expositor> expositoresPage = expositorIService.buscarPorCategoria(nomeCategoria, pageable);

        // O map do Page aceita nosso método auxiliar também!
        Page<ExpositorGetDto> dtoPage = expositoresPage.map(this::converterParaDto);

        return ResponseEntity.ok(dtoPage);
    }

    // --- EXCEPTION HANDLER ---
    @ExceptionHandler(ExpositorNaoEncontrado.class)
    public ResponseEntity<String> handleNotFound(ExpositorNaoEncontrado e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}