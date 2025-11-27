package br.com.ifba.feiraplus.usuario.service;

import br.com.ifba.feiraplus.usuario.entity.Usuario;
import br.com.ifba.feiraplus.usuario.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.ifba.feiraplus.usuario.exception.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService{

  @Autowired
  private UsuarioRepository repository;

  @Override
  public Usuario save(Usuario user) {
    validar(user);
    Usuario saved = repository.save(user);
    auditar("CRIAR", saved.getId());
    return saved;
  }

  @Override
  public Usuario update(Long id, Usuario user) {
    validar(user);
    Usuario oldUser = repository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
    BeanUtils.copyProperties(user, oldUser,"id");
    Usuario updated = repository.save(oldUser);
    auditar("ATUALIZAR", id);
    return updated;
  }

  @Override
  public void delete(Long id) {
    if (!repository.existsById(id)){
      throw new UsuarioNotFoundException(id);
    }
    repository.deleteById(id);
    auditar("DELETAR", id);
  }

  @Override
  public Usuario findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
  }

  @Override
  public List<Usuario> findAll() {
    return repository.findAll();
  }

  private void validar(Usuario user) {
    if (user.getNome() == null || user.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }
    if (user.getEmail() == null || user.getEmail().isBlank()) {
      throw new IllegalArgumentException("Email é obrigatório");
    }
    if (user.getSenha() == null || user.getSenha().isBlank()) {
      throw new IllegalArgumentException("Senha é obrigatório");
    }
    if (user.getPerfilUsuario() == null || user.getPerfilUsuario().isBlank()) {
      throw new IllegalArgumentException("perfil de usuario é obrigatório");
    }
  }

  private void auditar(String acao, Long id) {
    System.out.println("AÇÃO: " + acao + " FEIRA: " + id);
  }
}
