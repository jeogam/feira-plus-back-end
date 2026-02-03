package br.com.ifba.feiraplus.features.produto.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String foto;
    private String descricao;
    private Double nota;
}
