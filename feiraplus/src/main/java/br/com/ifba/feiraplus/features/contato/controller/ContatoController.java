package br.com.ifba.feiraplus.features.contato.controller;

import br.com.ifba.feiraplus.features.contato.dto.ContatoGetDto;
import br.com.ifba.feiraplus.features.contato.dto.ContatoPostDto;
import br.com.ifba.feiraplus.features.contato.entity.Contato;
import br.com.ifba.feiraplus.features.contato.entity.Contato.TipoAssunto;
import br.com.ifba.feiraplus.features.contato.service.IContatoService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contato")
@RequiredArgsConstructor
public class ContatoController {

    private final IContatoService service;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * POST /contato
     * Endpoint público - Criar nova mensagem de contato
     * Não requer autenticação
     */
    @PostMapping
    public ResponseEntity<ContatoGetDto> criar(@Valid @RequestBody ContatoPostDto dto) {
        Contato contato = objectMapperUtil.map(dto, Contato.class);
        Contato saved = service.save(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ContatoGetDto(saved));
    }

    /**
     * GET /contato
     * Listar todas as mensagens
     * Requer ROLE_ADMIN
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContatoGetDto>> listarTodas() {
        List<ContatoGetDto> contatos = service.findAll();
        return ResponseEntity.ok(contatos);
    }

    /**
     * GET /contato/nao-lidas
     * Listar mensagens não lidas
     * Requer ROLE_ADMIN
     */
    @GetMapping("/nao-lidas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContatoGetDto>> listarNaoLidas() {
        List<ContatoGetDto> contatos = service.findByLidaFalse();
        return ResponseEntity.ok(contatos);
    }

    /**
     * GET /contato/recentes
     * Listar mensagens recentes (últimos 7 dias)
     * Requer ROLE_ADMIN
     */
    @GetMapping("/recentes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContatoGetDto>> listarRecentes() {
        List<ContatoGetDto> contatos = service.findRecentes();
        return ResponseEntity.ok(contatos);
    }

    /**
     * GET /contato/assunto/{assunto}
     * Listar por tipo de assunto
     * Requer ROLE_ADMIN
     */
    @GetMapping("/assunto/{assunto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContatoGetDto>> listarPorAssunto(@PathVariable TipoAssunto assunto) {
        List<ContatoGetDto> contatos = service.findByAssunto(assunto);
        return ResponseEntity.ok(contatos);
    }

    /**
     * GET /contato/{id}
     * Buscar mensagem específica por ID
     * Requer ROLE_ADMIN
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContatoGetDto> buscarPorId(@PathVariable Long id) {
        ContatoGetDto contato = service.findById(id);
        return ResponseEntity.ok(contato);
    }

    /**
     * PATCH /contato/{id}/lida
     * Marcar mensagem como lida
     * Requer ROLE_ADMIN
     */
    @PatchMapping("/{id}/lida")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContatoGetDto> marcarComoLida(@PathVariable Long id) {
        ContatoGetDto contato = service.marcarComoLida(id);
        return ResponseEntity.ok(contato);
    }

    /**
     * PATCH /contato/{id}/nao-lida
     * Marcar mensagem como não lida
     * Requer ROLE_ADMIN
     */
    @PatchMapping("/{id}/nao-lida")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContatoGetDto> marcarComoNaoLida(@PathVariable Long id) {
        ContatoGetDto contato = service.marcarComoNaoLida(id);
        return ResponseEntity.ok(contato);
    }

    /**
     * DELETE /contato/{id}
     * Excluir mensagem
     * Requer ROLE_ADMIN
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /contato/estatisticas/nao-lidas
     * Contar mensagens não lidas
     * Requer ROLE_ADMIN
     */
    @GetMapping("/estatisticas/nao-lidas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> contarNaoLidas() {
        Long count = service.countByLidaFalse();
        return ResponseEntity.ok(Map.of("total", count));
    }
}
