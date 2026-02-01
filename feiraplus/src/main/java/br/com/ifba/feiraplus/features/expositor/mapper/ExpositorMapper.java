package br.com.ifba.feiraplus.features.expositor.mapper;

import br.com.ifba.feiraplus.features.expositor.dto.ExpositorGetDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.produto.dto.response.ProdutoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpositorMapper {

    public ExpositorGetDto toDto(Expositor entity) {
        if (entity == null) return null;

        ExpositorGetDto dto = new ExpositorGetDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setDocumentacao(entity.getDocumentacao());
        dto.setStatus(entity.getStatus());
        dto.setFoto(entity.getFoto());

        // Mapeando a Nota
        dto.setNota(entity.getNota());

        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getId());
            dto.setCategoriaNome(entity.getCategoria().getNome());
        }

        // --- LISTA DE PRODUTOS ---
        if (entity.getProdutos() != null && !entity.getProdutos().isEmpty()) {
            List<ProdutoResponseDTO> listaProdutos = entity.getProdutos().stream()
                    .map(prod -> {
                        ProdutoResponseDTO pDto = new ProdutoResponseDTO();
                        pDto.setId(prod.getId());
                        pDto.setNome(prod.getNome());
                        pDto.setDescricao(prod.getDescricao());
                        pDto.setPreco(prod.getPreco());
                        pDto.setFoto(prod.getFoto());
                        pDto.setNota(prod.getNota()); // Garante que a nota do produto também vá
                        return pDto;
                    })
                    .collect(Collectors.toList());
            dto.setProdutos(listaProdutos);
        } else {
            dto.setProdutos(Collections.emptyList());
        }

        return dto;
    }
}