package br.com.ifba.feiraplus.features.expositor.dto;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpositorPostDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A documentação (CPF/CNPJ) é obrigatória")
    private String documentacao;

    @NotNull(message = "O status é obrigatório")
    private StatusExpositor status;

    // O Frontend envia apenas o ID da categoria selecionada
    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;

    @NotBlank(message = "A descrição é obrigat´roia")
    private String descricao;

    @NotBlank(message = "O tipo de produto é obrigatório")
    private String tipoProduto;

    private String foto;

}
