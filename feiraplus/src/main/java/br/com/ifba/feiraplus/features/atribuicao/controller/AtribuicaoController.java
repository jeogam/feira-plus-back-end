package br.com.ifba.feiraplus.features.atribuicao.controller;

import br.com.ifba.feiraplus.features.atribuicao.dto.request.AtribuicaoPostRequestDTO;
import br.com.ifba.feiraplus.features.atribuicao.dto.response.AtribuicaoGetResponseDTO;
import br.com.ifba.feiraplus.features.atribuicao.service.AtribuicaoIService;
import br.com.ifba.feiraplus.features.atribuicao.entity.Atribuicao;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/atribuicoes")
@RequiredArgsConstructor
public class AtribuicaoController {

    private final AtribuicaoIService atribuicaoService;
    private final ObjectMapperUtil objectMapperUtil;

    // GET: Listar todos
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AtribuicaoGetResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.atribuicaoService.findAll(),
                        AtribuicaoGetResponseDTO.class));
    }

    // POST: Salvar
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AtribuicaoGetResponseDTO> save(@RequestBody @Valid AtribuicaoPostRequestDTO dto) {
        // Service faz a lógica de busca e salvamento
        Atribuicao atribuicaoSalva = atribuicaoService.save(dto);

        // Mapeia entidade salva para DTO de resposta
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(atribuicaoSalva, AtribuicaoGetResponseDTO.class));
    }

    // PUT: Atualizar
    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid AtribuicaoPostRequestDTO dto) {
        atribuicaoService.update(id, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // DELETE: Remover
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        atribuicaoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}