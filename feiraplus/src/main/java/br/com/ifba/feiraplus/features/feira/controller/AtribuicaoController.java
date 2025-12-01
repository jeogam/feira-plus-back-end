package br.com.ifba.feiraplus.features.feira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.feiraplus.features.feira.entity.Atribuicao;
import br.com.ifba.feiraplus.features.feira.service.IAtribuicaoService;

@RestController
@RequestMapping("/atribuicoes")
public class AtribuicaoController {

    @Autowired
    private IAtribuicaoService service;

    // POST /atribuicoes/atribuir?espacoId=1&expositorId=5
    @PostMapping("/atribuir")
    public ResponseEntity<Atribuicao> criarAtribuicao(
            @RequestParam Long espacoId,
            @RequestParam Long expositorId) {
        
        Atribuicao atribuicao = service.atribuir(espacoId, expositorId);
        return ResponseEntity.ok(atribuicao);
    }

    // DELETE /atribuicoes/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAtribuicao(@PathVariable Long id) {
        service.removerAtribuicao(id);
        return ResponseEntity.noContent().build();
    }
}