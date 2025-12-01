package br.com.ifba.feiraplus.features.expositor.controller;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.expositor.service.ExpositorIService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/expositores")
public class ExpositorController {

    private final ObjectMapperUtil objectMapperUtil;
    private final ExpositorIService expositorIService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ExpositorGetDto> save(@RequestBody ExpositorPostDto expositorDto) {

        //converte de dto para entidade
        Expositor expositor = objectMapperUtil.map(expositorDto, Expositor.class);
        //salva no banco e passa uma copia para usarmos como resposta
        Expositor expositorSalvo = expositorIService.save(expositor);

        //converte para dto e responde a requisicao
        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapperUtil.map(expositorSalvo, ExpositorGetDto.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar")
    public  ResponseEntity< List <ExpositorGetDto>> findAll() {

        List<Expositor> expositores = expositorIService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(expositores, ExpositorGetDto.class));
    }
}
