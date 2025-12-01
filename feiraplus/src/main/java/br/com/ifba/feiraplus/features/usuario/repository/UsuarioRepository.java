package br.com.ifba.feiraplus.features.usuario.repository;

import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar usuário por email, retornando um Optional para evitar NullPointerException
    Optional<Usuario> findByEmail(String email);
}