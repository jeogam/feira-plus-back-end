package br.com.ifba.feiraplus.features.produto.entity;


import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends PersistenceEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "expositor_id")
    private Expositor expositor;

    @Column(name = "nota")
    private Double nota;

}
