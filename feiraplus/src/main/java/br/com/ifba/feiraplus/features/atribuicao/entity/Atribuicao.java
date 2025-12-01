package br.com.ifba.feiraplus.features.atribuicao.entity;

import java.time.LocalDate;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "atribuicoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atribuicao extends PersistenceEntity {

    @Column(nullable = false)
    @NotNull(message = "A data da atribuição é obrigatória")
    private LocalDate dataAtribuicao;

    // OneToOne: Um espaço só pode ter UMA atribuição ativa por vez
    @OneToOne
    @JoinColumn(name = "espaco_id", nullable = false, unique = true)
    @NotNull(message = "O espaço é obrigatório")
    private Espaco espaco;

    // ManyToOne: Um expositor pode ter várias atribuições (ex: em dias diferentes ou espaços diferentes)
    @ManyToOne
    @JoinColumn(name = "expositor_id", nullable = false)
    @NotNull(message = "O expositor é obrigatório")
    private Expositor expositor;

}