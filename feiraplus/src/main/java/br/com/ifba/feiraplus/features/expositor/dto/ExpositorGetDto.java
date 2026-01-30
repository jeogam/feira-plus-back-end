package br.com.ifba.feiraplus.features.expositor.dto;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import br.com.ifba.feiraplus.features.produto.dto.response.ProdutoResponseDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExpositorGetDto {

    private Long id; // Importante para o React saber quem é quem (key prop)
    private String nome;
    private String documentacao;
    private StatusExpositor status;

    // Informações da Categoria para exibição
    private Long categoriaId;
    private String categoriaNome;

    private String descricao;
    private List<ProdutoResponseDTO> produtos;
    private String foto;

}
