package br.com.ifba.feiraplus.usuario.entity;

import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario extends PersistenceEntity {

  @Column(name = "nome", nullable = false)
  private String nome;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "senha", nullable = false)
  private String senha;
  @Column(name = "perfil_usuario", nullable = false)
  private String perfilUsuario;
}
