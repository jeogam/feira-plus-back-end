package br.com.ifba.feiraplus.features.usuario.service;

import br.com.ifba.feiraplus.features.usuario.dto.UpdateSenhaDto;
import br.com.ifba.feiraplus.features.usuario.dto.UsuarioUpdateDto;
import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import br.com.ifba.feiraplus.features.usuario.exception.UsuarioNotFoundException;
import br.com.ifba.feiraplus.features.usuario.repository.UsuarioRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.ifba.feiraplus.features.usuario.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService, UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Usuario save(Usuario user) {

        try{
            Usuario saved = repository.save(user);
        }catch (Exception e){
            throw new BusinessException("Nao foi possivel registrar Usuario");
        }
        return repository.save(user);
    }


    @Override
    @Transactional
    public Usuario update(Long id, UsuarioUpdateDto dadosAtualizados) {
        // 1. Busca o utilizador persistido (com todos os dados: senha, email, etc)
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Nao foi encontrado um Usuario"));
        // 2. Atualiza APENAS os campos permitidos pelo DTO
        // Isso evita que senha ou email sejam apagados acidentalmente
        usuarioExistente.setNome(dadosAtualizados.getNome());
        usuarioExistente.setPerfilUsuario(dadosAtualizados.getPerfilUsuario());


        return repository.save(usuarioExistente);
    }
    @Transactional
    @Override
    public void updatePassword(Long id, UpdateSenhaDto dto) {
        Usuario user = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"+id));

        // 1. Valida se a senha atual está correta
        if (!passwordEncoder.matches(dto.getSenhaAtual(), user.getSenha())) {
            throw new RuntimeException("A senha atual está incorreta.");
        }

        // 2. Valida se a nova senha e a confirmação batem
        if (!dto.getNovaSenha().equals(dto.getSenhaConfirmada())) {
            throw new RuntimeException("A nova senha e a confirmação não coincidem.");
        }

        // 3. Encripta e salva a nova senha
        user.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id)){
            throw new UsuarioNotFoundException("Nao foi encontrado este usuario");
        }
        repository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNotFoundException("Nao foi encontrado este usuario"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {

        return repository.findAll(pageable);
    }




    ///  metodo que retorna os dados de usuario para login
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        // 1. Busca o usuário (ajuste se o método no repo for findByEmail)
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com esse email"));

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
                new UsernameNotFoundException("usuario com este email nao encontrado"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }
}