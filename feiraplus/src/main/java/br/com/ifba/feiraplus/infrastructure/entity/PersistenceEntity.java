package br.com.ifba.feiraplus.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.io.Serializable;


@Getter
@MappedSuperclass
public class PersistenceEntity implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

}