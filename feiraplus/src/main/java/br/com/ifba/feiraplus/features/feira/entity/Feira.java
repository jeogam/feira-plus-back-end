package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "feira")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Feira extends PersistenceEntity {

    private String nome;

    private String local;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

}