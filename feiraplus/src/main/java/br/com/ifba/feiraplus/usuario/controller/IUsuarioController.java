package br.com.ifba.feiraplus.usuario.controller;

import br.com.ifba.feiraplus.usuario.entity.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioController {
  ResponseEntity<Usuario> save(Usuario user);

  ResponseEntity<Usuario> update( Long id, Usuario user);

  ResponseEntity<Usuario> delete(Long id);

  ResponseEntity<Usuario> findById(Long id);

  ResponseEntity <List<Usuario>> findAll();
}
