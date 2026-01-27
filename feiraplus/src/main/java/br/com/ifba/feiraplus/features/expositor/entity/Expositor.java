package br.com.ifba.feiraplus.features.expositor.entity;

import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import br.com.ifba.feiraplus.features.feira.entity.Feira; // Importa a Feira
import br.com.ifba.feiraplus.features.produto.entity.Produto;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // Relação N:1 com Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "expositor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    // Relação N:N com Feira: Um Expositor pode participar de várias Feiras.
    // 'mappedBy' aponta para a propriedade na classe Feira que define a associação.
    @ManyToMany(mappedBy = "expositores")
    private List<Feira> feiras;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, length = 80)
    private String tipoProduto;

}