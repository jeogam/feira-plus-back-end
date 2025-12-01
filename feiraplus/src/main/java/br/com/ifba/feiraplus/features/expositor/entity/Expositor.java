package br.com.ifba.feiraplus.features.expositor.entity;

import br.com.ifba.feiraplus.features.expositor.enums.StatusExpositor;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table(name = "expositores")
public class Expositor extends PersistenceEntity {


    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documentacao; // CPF ou CNPJ

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExpositor status;
}
