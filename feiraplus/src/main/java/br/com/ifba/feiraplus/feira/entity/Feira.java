package br.com.ifba.feiraplus.feira.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Feira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String local;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

}
