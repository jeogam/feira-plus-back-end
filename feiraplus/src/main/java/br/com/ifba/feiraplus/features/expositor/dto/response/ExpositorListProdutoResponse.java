package br.com.ifba.feiraplus.features.expositor.dto.response;


import br.com.ifba.feiraplus.features.produto.entity.Produto;
import lombok.Data;

import java.util.List;

@Data
public class ExpositorListProdutoResponse {

    private String nomeExpositor;

    private List<Produto> produtos;
}
