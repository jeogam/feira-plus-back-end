package br.com.ifba.feiraplus.features.categoria.controller;

import br.com.ifba.feiraplus.features.categoria.dto.request.CategoriaRequestDTO;
import br.com.ifba.feiraplus.features.categoria.dto.response.CategoriaResponseDTO;
import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.categoria.service.CategoriaIService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    // --- LISTAR ---
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.categoriaService.findAll(),
                        CategoriaResponseDTO.class));
    }

    // --- SALVAR ---
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaResponseDTO> save(@RequestBody @Valid CategoriaRequestDTO dto) {
        Categoria categoriaSalva = categoriaService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(categoriaSalva, CategoriaResponseDTO.class));
    }

    // --- ATUALIZAR ---
    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dto) {
        categoriaService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --- DELETAR ---
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoriaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}