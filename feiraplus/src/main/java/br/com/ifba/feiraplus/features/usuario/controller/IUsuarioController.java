package br.com.ifba.feiraplus.features.usuario.controller;

import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUsuarioController {
  ResponseEntity<Usuario> save(Usuario user);

  ResponseEntity<Usuario> update( Long id, Usuario user);

  ResponseEntity<Usuario> delete(Long id);

  ResponseEntity<Usuario> findById(Long id);

  ResponseEntity<Usuario> getUsuarioLogado( UserDetails userDetails);

  ResponseEntity <List<Usuario>> findAll();
}
