package br.com.ifba.feiraplus.features.feira.repository;

import br.com.ifba.feiraplus.features.feira.entity.Feira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeiraRepository extends JpaRepository<Feira, Long> {

    List<Feira> findByNomeContainingIgnoreCase(String nome);
    List<Feira> findByUsuarioId(Long usuarioId);

    @Query("SELECT DISTINCT f FROM Feira f " +
            "JOIN f.expositores e " +
            "JOIN e.categoria c " +
            "WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Feira> buscarPorArtesaoOuCategoria(@Param("termo") String termo);

    // --- NOVO MÉTODO: MÉDIA GERAL ---
    @Query("SELECT COALESCE(AVG(f.nota), 0.0) FROM Feira f WHERE f.nota IS NOT NULL")
    Double calcularMediaGlobal();
}