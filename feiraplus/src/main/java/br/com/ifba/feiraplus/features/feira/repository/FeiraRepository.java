package br.com.ifba.feiraplus.features.feira.repository;

import br.com.ifba.feiraplus.features.feira.entity.Feira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeiraRepository extends JpaRepository<Feira, Long> {

    // Busca feiras pelo nome (ignora maiúsculas/minúsculas)
    // Útil para filtros de pesquisa na tela da Gestora
    List<Feira> findByNomeContainingIgnoreCase(String nome);

    // Busca todas as feiras coordenadas por um usuário específico
    // Útil para a tela do Coordenador (ver apenas as feiras dele)
    List<Feira> findByUsuarioId(Long usuarioId);

    // Como a classe é abstrata, o findAll() retorna tanto FeiraPermanente quanto FeiraEvento.
}