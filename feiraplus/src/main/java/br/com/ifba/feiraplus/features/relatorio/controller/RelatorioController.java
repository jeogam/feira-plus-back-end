package br.com.ifba.feiraplus.features.relatorio.controller;

import br.com.ifba.feiraplus.features.relatorio.dto.response.RelatorioOcupacaoResponseDTO;
import br.com.ifba.feiraplus.features.relatorio.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    // UC05 - Gerar Relatórios Administrativos
    @GetMapping("/ocupacao")
    public ResponseEntity<List<RelatorioOcupacaoResponseDTO>> getRelatorioOcupacao() {
        return ResponseEntity.ok(relatorioService.gerarRelatorioOcupacao());
    }
}