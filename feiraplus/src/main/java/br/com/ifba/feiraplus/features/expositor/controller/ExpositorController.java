package br.com.ifba.feiraplus.features.expositor.controller;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.exception.ExpositorNaoEncontrado;
import br.com.ifba.feiraplus.features.expositor.mapper.ExpositorMapper;
// Alterado para a classe concreta ou adicione o método na interface
import br.com.ifba.feiraplus.features.expositor.service.ExpositorService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expositores")
public class ExpositorController {

    private final ObjectMapperUtil objectMapperUtil;
    private final ExpositorService expositorService; // Usando a classe concreta para acessar getMediaGeralAvaliacoes
    private final ExpositorMapper expositorMapper;

    // --- MÉTODO AUXILIAR ---
    private ExpositorGetDto converterParaDto(Expositor expositor) {
        // Usa o Mapper manual que criamos para garantir que tudo (incluindo nota) seja mapeado corretamente
        // Se usar apenas o objectMapperUtil, verifique se os campos tem o mesmo nome
        return expositorMapper.toDto(expositor);
    }

    @PostMapping("/register")
    public ResponseEntity<ExpositorGetDto> save(@RequestBody @Valid ExpositorPostDto expositorDto) {
        Expositor expositorSalvo = expositorService.save(expositorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDto(expositorSalvo));
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<ExpositorGetDto>> findAll() {
        List<Expositor> expositores = expositorService.findAll();
        List<ExpositorGetDto> dtos = expositores.stream()
                .map(expositorMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpositorGetDto> buscarPorId(@PathVariable Long id) {
        Expositor expositor = expositorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(converterParaDto(expositor));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ExpositorGetDto> update(@PathVariable Long id,
                                                  @RequestBody @Valid ExpositorPostDto expositorDto) {
        Expositor expositorAtualizado = expositorService.update(id, expositorDto);
        return ResponseEntity.ok(converterParaDto(expositorAtualizado));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expositorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-por-categoria")
    public ResponseEntity<Page<ExpositorGetDto>> buscarPorNomeCategoria(
            @RequestParam String nomeCategoria,
            Pageable pageable) {
        Page<Expositor> expositoresPage = expositorService.buscarPorCategoria(nomeCategoria, pageable);
        Page<ExpositorGetDto> dtoPage = expositoresPage.map(this::converterParaDto);
        return ResponseEntity.ok(dtoPage);
    }

    // --- NOVO ENDPOINT: MÉDIA ---
    @GetMapping("/media-avaliacoes")
    public ResponseEntity<Map<String, Double>> getMediaAvaliacoes() {
        Double media = expositorService.getMediaGeralAvaliacoes();
        return ResponseEntity.ok(Map.of("media", media));
    }

    @ExceptionHandler(ExpositorNaoEncontrado.class)
    public ResponseEntity<String> handleNotFound(ExpositorNaoEncontrado e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}