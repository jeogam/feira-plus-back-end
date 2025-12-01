
package br.com.ifba.feiraplus.features.feira.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "feira_evento")
@PrimaryKeyJoinColumn(name = "feira_id") // Une com a tabela pai pelo ID
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiraEvento extends Feira {

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
}
