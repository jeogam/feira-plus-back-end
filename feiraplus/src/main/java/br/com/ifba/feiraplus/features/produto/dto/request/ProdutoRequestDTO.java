package br.com.ifba.feiraplus.features.produto.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String nome;

    @NotNull(message = "O preço do produto não pode ser nulo")
    @Positive(message = "O preço deve ser um valor positivo")
    private BigDecimal preco;

    private String foto;

    private String descricao;

    private Long expositorId;

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Float nota;
}
