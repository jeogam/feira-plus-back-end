package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feira_permanente")
@PrimaryKeyJoinColumn(name = "feira_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiraPermanente extends Feira {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequencia frequencia;
}