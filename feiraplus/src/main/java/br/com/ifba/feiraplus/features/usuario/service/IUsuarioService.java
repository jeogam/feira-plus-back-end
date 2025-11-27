package br.com.ifba.feiraplus.features.usuario.service;

import br.com.ifba.feiraplus.features.usuario.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

  Usuario save(Usuario usuario);

  Usuario update(Long id, Usuario usuario);

  void delete(Long id);

  Usuario findById(Long id);

  List<Usuario> findAll();
}
