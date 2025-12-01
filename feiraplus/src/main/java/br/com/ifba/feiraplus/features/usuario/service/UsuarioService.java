package br.com.ifba.feiraplus.features.usuario.service;

import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import br.com.ifba.feiraplus.features.usuario.exception.UsuarioNotFoundException;
import br.com.ifba.feiraplus.features.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.ifba.feiraplus.features.usuario.exception.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService, UserDetailsService {

    private final UsuarioRepository repository;

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



    ///  metodo que retorna os dados de usuario para login
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        // 1. Busca o usuário (ajuste se o método no repo for findByEmail)
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        // 2.  Cria a permissão baseada no perfil (ex: "ADMIN" vira "ROLE_ADMIN")
        // Se o perfil for nulo, usamos uma role padrão "USER" para não quebrar
        String perfil = usuario.getPerfilUsuario() == null ? "USER" : usuario.getPerfilUsuario().toUpperCase();

        // Cria a lista de autoridades que o Spring Security entende
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + perfil));

        // 2. Retorna o objeto User do Spring Security
        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                authorities
        );
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return repository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("usuario com este email nao encontrado"+username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }
}