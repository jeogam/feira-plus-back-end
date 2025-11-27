package br.com.ifba.feiraplus.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@MappedSuperclass
public class PersistenceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

}