package br.com.ifba.feiraplus.features.usuario.service;

import br.com.ifba.feiraplus.features.usuario.dto.UpdateSenhaDto;
import br.com.ifba.feiraplus.features.usuario.dto.UsuarioUpdateDto;
import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUsuarioService {

    Usuario save(Usuario usuario);

    void updatePassword(Long id, UpdateSenhaDto dto);

    void delete(Long id);

    Usuario findById(Long id);

     Usuario update(Long id, UsuarioUpdateDto dadosAtualizados);

    Page<Usuario> findAll(Pageable pageable);

    UserDetails loadUserByEmail(String email);

    Usuario buscarPorUsername(String username);
}