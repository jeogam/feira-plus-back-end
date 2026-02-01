package br.com.ifba.feiraplus.features.expositor.dto;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    private String foto;

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Float nota;
}
