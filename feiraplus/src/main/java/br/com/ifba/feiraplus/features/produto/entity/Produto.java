package br.com.ifba.feiraplus.features.produto.entity;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
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

    @ManyToOne
    @JoinColumn(name = "expositor_id") // Nome da coluna no banco
    private Expositor expositor;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

}
