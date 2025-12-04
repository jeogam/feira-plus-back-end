package br.com.ifba.feiraplus.features.categoria.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class    CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria não pode estar em branco")
    private String nome;
}