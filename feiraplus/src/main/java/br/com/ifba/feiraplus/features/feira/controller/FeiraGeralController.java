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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feiras")
public class FeiraGeralController {

    private final FeiraGeralService feiraGeralService;
    private final ObjectMapperUtil mapperUtil;

    public FeiraGeralController(FeiraGeralService feiraGeralService, ObjectMapperUtil mapperUtil) {
        this.feiraGeralService = feiraGeralService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<FeiraBuscaResponseDTO>> pesquisar(
            @RequestParam(required = false, defaultValue = "") String termo) {

        // 1. Busca as feiras no serviço
        List<Feira> feirasEncontradas = feiraGeralService.pesquisarPorArtesaoOuCategoria(termo);

        // 2. Mapeia para o DTO de resposta
        List<FeiraBuscaResponseDTO> response = feirasEncontradas.stream()
                .map(feira -> {
                    FeiraBuscaResponseDTO dto = mapperUtil.map(feira, FeiraBuscaResponseDTO.class);

                    // Define o tipo de feira para o front-end saber diferenciar
                    if (feira instanceof FeiraEvento) {
                        dto.setTipoFeira("EVENTO");
                    } else if (feira instanceof FeiraPermanente) {
                        dto.setTipoFeira("PERMANENTE");
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // 3. Retorna a lista (aqui a variável 'response' agora existe)
        return ResponseEntity.ok(response);
    }

    // --- NOVO ENDPOINT: MÉDIA GERAL DAS FEIRAS ---
    // URL Final: GET /feiras/media-avaliacoes
    @GetMapping("/media-avaliacoes")
    public ResponseEntity<Map<String, Double>> getMediaAvaliacoes() {
        Double media = feiraGeralService.getMediaGeralAvaliacoes();
        return ResponseEntity.ok(Map.of("media", media));
    }
}