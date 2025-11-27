package br.com.ifba.feiraplus.feira.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feira_evento")
public class FeiraEvento extends Feira {

  private LocalDate dataInicio;

  private LocalDate dataFim;
}
