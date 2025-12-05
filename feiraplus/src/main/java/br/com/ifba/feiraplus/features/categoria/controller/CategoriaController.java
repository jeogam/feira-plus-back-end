package br.com.ifba.feiraplus.features.categoria.controller;

import br.com.ifba.feiraplus.features.categoria.dto.request.CategoriaRequestDTO;
import br.com.ifba.feiraplus.features.categoria.dto.response.CategoriaResponseDTO;
import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.categoria.service.CategoriaIService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    // --- CADASTRAR ---
    // URL Final: POST /categorias/cadastrar
    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> save(@RequestBody @Valid CategoriaRequestDTO dto) {
        Categoria categoriaSalva = categoriaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(categoriaSalva, CategoriaResponseDTO.class));
    }

    // --- ATUALIZAR ---
    // URL Final: PUT /categorias/update/1
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dto) {
        Categoria categoriaAtualizada = categoriaService.update(id, dto);
        return ResponseEntity.ok(objectMapperUtil.map(categoriaAtualizada, CategoriaResponseDTO.class));
    }

    // --- DELETAR ---
    // URL Final: DELETE /categorias/delete/1
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- BUSCAR POR ID ---
    // URL Final: GET /categorias/buscar/1
    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(categoria, CategoriaResponseDTO.class));
    }

    // --- LISTAR TODOS ---
    // URL Final: GET /categorias/buscar
    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(objectMapperUtil.mapAll(
                this.categoriaService.findAll(),
                CategoriaResponseDTO.class));
    }

    // Tratamento de erro simples para o controller
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}