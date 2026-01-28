package br.com.ifba.feiraplus.features.produto.controller;

import br.com.ifba.feiraplus.features.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.feiraplus.features.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;
import br.com.ifba.feiraplus.features.produto.service.ProdutoIService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoIService produtoService;
    private final ObjectMapperUtil objectMapperUtil;

    // --- CADASTRAR ---
    // URL Final: POST /produtos/cadastrar
    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPOSITOR')")
    public ResponseEntity<ProdutoResponseDTO> save(@RequestBody @Valid ProdutoRequestDTO dto) {

        Produto produtoSalvo = produtoService.save(dto);

        ProdutoResponseDTO response = objectMapperUtil.map(produtoSalvo, ProdutoResponseDTO.class);

        if (produtoSalvo.getExpositor() != null) {// Garante que o nome do expositor seja preenchido manualmente
            response.setNomeExpositor(produtoSalvo.getExpositor().getNome());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // --- ATUALIZAR ---
    // URL Final: PUT /produtos/update/1
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPOSITOR')")
    public ResponseEntity<ProdutoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO dto) {
        Produto produtoAtualizado = produtoService.update(id, dto);
        return ResponseEntity.ok(objectMapperUtil.map(produtoAtualizado, ProdutoResponseDTO.class));
    }

    // --- DELETAR ---
    // URL Final: DELETE /produtos/delete/1
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPOSITOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- BUSCAR POR ID ---
    // URL Final: GET /produtos/buscar/1
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(produto, ProdutoResponseDTO.class));
    }

    // --- LISTAR TODOS ---
    // URL Final: GET /produtos/buscar
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoResponseDTO>> findAll() {
        return ResponseEntity.ok(objectMapperUtil.mapAll(
                this.produtoService.findAll(),
                ProdutoResponseDTO.class));
    }
    @GetMapping("/listar-por-expositor/{expositorId}")
    public ResponseEntity<List<ProdutoResponseDTO>> findByExpositor(@PathVariable Long expositorId) {
        // Você precisaria criar esse método no service e repository
        return ResponseEntity.ok(objectMapperUtil.mapAll(
                this.produtoService.findByExpositorId(expositorId),
                ProdutoResponseDTO.class));
    }

    // Tratamento de erro simples para o controller
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
