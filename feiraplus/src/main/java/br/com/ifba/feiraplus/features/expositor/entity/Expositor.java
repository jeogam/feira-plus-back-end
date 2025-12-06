package br.com.ifba.feiraplus.features.expositor.entity;

import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "expositor")
public class Expositor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "cpf_cnpj", nullable = false, unique = true)
    private String cpfCnpj;

    @Column(nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    private StatusExpositor status;
    @ManyToOne
    // Lê-se: "Muitos expositores pertencem a Uma categoria"
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
