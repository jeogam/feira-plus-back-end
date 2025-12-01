package br.com.ifba.feiraplus.features.usuario.service;

import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import br.com.ifba.feiraplus.features.usuario.exception.UsuarioNotFoundException;
import br.com.ifba.feiraplus.features.usuario.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; // Importação necessária
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importação necessária
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

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
        Usuario oldUser = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        BeanUtils.copyProperties(user, oldUser, "id");
        Usuario updated = repository.save(oldUser);
        auditar("ATUALIZAR", id);
        return updated;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNotFoundException(id);
        }
        repository.deleteById(id);
        auditar("DELETAR", id);
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    // --- NOVO MÉTODO ADICIONADO E AJUSTADO ---
    @Override
    public UserDetails loadUserByEmail(String email) {
        // Busca o usuário pelo email. Se não encontrar, lança exceção.
        // Importante: Sua classe Usuario deve implementar UserDetails ou você deve converter aqui.
        // Assumindo que Usuario implementa UserDetails:
        return (UserDetails) repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }
    // ------------------------------------------

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
        if (user.getPerfilUsuario() == null) {
            throw new IllegalArgumentException("Perfil de usuário é obrigatório");
        }
    }

    private void auditar(String acao, Long id) {
        System.out.println("AÇÃO: " + acao + " USUARIO: " + id);
    }
}