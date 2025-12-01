package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.features.feira.enums.Frequencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feira_permanente")
public class FeiraPermanente extends Feira {

  @Column(name = "frequencia", nullable = false)
  @NotNull(message = "A frequência é obrigatória")
  @Enumerated(EnumType.STRING)
  private Frequencia frequencia;
}
