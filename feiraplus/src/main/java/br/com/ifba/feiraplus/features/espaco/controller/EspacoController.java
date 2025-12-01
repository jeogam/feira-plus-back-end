package br.com.ifba.feiraplus.features.espaco.controller;

import br.com.ifba.feiraplus.features.espaco.dto.response.EspacoGetResponseDTO;
import br.com.ifba.feiraplus.features.espaco.dto.request.EspacoPostRequestDTO;
import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import br.com.ifba.feiraplus.features.espaco.service.EspacoIService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/espacos")
@RequiredArgsConstructor
public class EspacoController {

    private final EspacoIService espacoService;
    private final ObjectMapperUtil objectMapperUtil;

    // --- LISTAR (GET) ---
    // Fonte: AULA 20, Slide 7 [cite: 857]
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EspacoGetResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.espacoService.findAll(),
                        EspacoGetResponseDTO.class));
    }

    // --- SALVAR (POST) ---
    // Fonte: AULA 20, Slide 12 [cite: 1048]
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EspacoGetResponseDTO> save(@RequestBody EspacoPostRequestDTO espacoPostRequestDto) {
        Espaco espaco = objectMapperUtil.map(espacoPostRequestDto, Espaco.class);
        Espaco espacoSalvo = espacoService.save(espaco);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(espacoSalvo, EspacoGetResponseDTO.class));
    }

    // --- ATUALIZAR (PUT) ---
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody EspacoPostRequestDTO espacoDto) {
        // Nota: O DTO de update idealmente teria o ID, ou passaria na URL.
        // Seguindo o exemplo simples do slide, mapeamos direto.
        Espaco espaco = objectMapperUtil.map(espacoDto, Espaco.class);
        espacoService.update(espaco);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --- DELETAR (DELETE) ---
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        espacoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}