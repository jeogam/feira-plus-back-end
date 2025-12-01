package br.com.ifba.feiraplus.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import java.io.Serializable;


@Data
@MappedSuperclass
public class PersistenceEntity implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

}