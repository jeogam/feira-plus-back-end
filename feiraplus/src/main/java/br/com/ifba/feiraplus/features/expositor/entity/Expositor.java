package br.com.ifba.feiraplus.features.expositor.entity;

import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "expositor")
public class Expositor extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Column(name = "documentacao", nullable = false, unique = true)
    private String documentacao;

    @Enumerated(EnumType.STRING)
    private StatusExpositor status;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
