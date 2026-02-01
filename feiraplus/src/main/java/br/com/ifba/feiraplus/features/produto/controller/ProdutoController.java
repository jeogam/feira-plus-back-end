package br.com.ifba.feiraplus.features.produto.controller;

import br.com.ifba.feiraplus.features.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.feiraplus.features.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;
// Importante importar a implementação concreta ou ajustar a interface para ter o método novo
import br.com.ifba.feiraplus.features.produto.service.ProdutoService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    // Alterado para a classe concreta para acessar o novo método getMediaGeralAvaliacoes
    // Ou você deve adicionar o método na interface ProdutoIService
    private final ProdutoService produtoService;
    private final ObjectMapperUtil objectMapperUtil;

    // --- CADASTRAR ---
    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPOSITOR')")
    public ResponseEntity<ProdutoResponseDTO> save(@RequestBody @Valid ProdutoRequestDTO dto) {
        Produto produtoSalvo = produtoService.save(dto);
        ProdutoResponseDTO response = objectMapperUtil.map(produtoSalvo, ProdutoResponseDTO.class);

        if (produtoSalvo.getExpositor() != null) {
            response.setNomeExpositor(produtoSalvo.getExpositor().getNome());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // --- ATUALIZAR ---
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPOSITOR')")
    public ResponseEntity<ProdutoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO dto) {
        Produto produtoAtualizado = produtoService.update(id, dto);
        return ResponseEntity.ok(objectMapperUtil.map(produtoAtualizado, ProdutoResponseDTO.class));
    }

    // --- DELETAR ---
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPOSITOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- BUSCAR POR ID ---
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(produto, ProdutoResponseDTO.class));
    }

    // --- LISTAR TODOS ---
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoResponseDTO>> findAll() {
        return ResponseEntity.ok(objectMapperUtil.mapAll(
                this.produtoService.findAll(),
                ProdutoResponseDTO.class));
    }

    @GetMapping("/listar-por-expositor/{expositorId}")
    public ResponseEntity<List<ProdutoResponseDTO>> findByExpositor(@PathVariable Long expositorId) {
        return ResponseEntity.ok(objectMapperUtil.mapAll(
                this.produtoService.findByExpositorId(expositorId),
                ProdutoResponseDTO.class));
    }

    // --- NOVO: MÉDIA GERAL DAS NOTAS ---
    // URL Final: GET /produtos/media-avaliacoes
    @GetMapping("/media-avaliacoes")
    public ResponseEntity<Map<String, Double>> getMediaAvaliacoes() {
        Double media = produtoService.getMediaGeralAvaliacoes();
        return ResponseEntity.ok(Map.of("media", media));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}