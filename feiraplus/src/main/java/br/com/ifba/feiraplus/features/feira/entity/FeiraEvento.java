package br.com.ifba.feiraplus.features.feira.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "feira_evento")
@PrimaryKeyJoinColumn(name = "feira_id")
public class FeiraEvento extends Feira {

    private LocalDate dataInicio;

    private LocalDate dataFim;
}