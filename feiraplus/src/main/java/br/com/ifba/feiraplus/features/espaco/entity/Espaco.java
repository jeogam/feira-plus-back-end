package br.com.ifba.feiraplus.features.espaco.entity;

import br.com.ifba.feiraplus.features.espaco.enums.StatusEspaco;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "espaco")
@Data
@EqualsAndHashCode(callSuper = true)
public class Espaco extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String local;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private String horarios; // Ex: "08:00 - 12:00"

    @Column(length = 20)
    private String dimensao;

    @Enumerated(EnumType.STRING)
    private StatusEspaco status;

    // RELACIONAMENTO: 1 Feira tem N Espaços
    @ManyToOne
    @JoinColumn(name = "feira_id", nullable = false)
    private Feira feira;
}