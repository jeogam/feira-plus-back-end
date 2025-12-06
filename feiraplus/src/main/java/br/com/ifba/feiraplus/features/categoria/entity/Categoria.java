package br.com.ifba.feiraplus.features.categoria.entity;

import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria extends PersistenceEntity {

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

}