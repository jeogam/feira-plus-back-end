package br.com.ifba.feiraplus.features.feira.controller;

import br.com.ifba.feiraplus.features.feira.dto.response.FeiraBuscaResponseDTO;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento;
import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;
import br.com.ifba.feiraplus.features.feira.service.FeiraGeralService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feiras")
public class FeiraGeralController {

    private final FeiraGeralService feiraGeralService;
    private final ObjectMapperUtil mapperUtil;

    // Construtor manual para Injeção de Dependência
    public FeiraGeralController(FeiraGeralService feiraGeralService, ObjectMapperUtil mapperUtil) {
        this.feiraGeralService = feiraGeralService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<FeiraBuscaResponseDTO>> pesquisar(
            @RequestParam(required = false, defaultValue = "") String termo) {
        
        List<Feira> feirasEncontradas = feiraGeralService.pesquisarPorArtesaoOuCategoria(termo);

        List<FeiraBuscaResponseDTO> response = feirasEncontradas.stream()
                .map(feira -> {
                    FeiraBuscaResponseDTO dto = mapperUtil.map(feira, FeiraBuscaResponseDTO.class);
                    
                    if (feira instanceof FeiraEvento) {
                        dto.setTipoFeira("EVENTO");
                    } else if (feira instanceof FeiraPermanente) {
                        dto.setTipoFeira("PERMANENTE");
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}