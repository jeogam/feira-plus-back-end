package br.com.ifba.feiraplus.features.feira.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feira_permanente")
public class FeiraPermanente extends Feira {

  @Enumerated(EnumType.STRING)
  private Frequencia frequencia;
}
