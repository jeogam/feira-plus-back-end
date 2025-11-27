package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "feira_permanente")
@PrimaryKeyJoinColumn(name = "feira_id")
public class FeiraPermanente extends Feira {

    @Enumerated(EnumType.STRING)
    private Frequencia frequencia;
}