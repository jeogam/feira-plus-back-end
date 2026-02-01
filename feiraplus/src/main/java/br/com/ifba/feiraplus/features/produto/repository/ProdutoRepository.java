package br.com.ifba.feiraplus.features.produto.repository;

import br.com.ifba.feiraplus.features.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Corrigido de List<Object> para List<Produto> para facilitar o mapeamento
    List<Produto> findByExpositorId(Long expositorId);

    // Query para calcular a média de todas as notas cadastradas
    // COALESCE retorna 0.0 se não houver nenhuma nota (evita null)
    @Query("SELECT COALESCE(AVG(p.nota), 0.0) FROM Produto p WHERE p.nota IS NOT NULL")
    Double calcularMediaGlobal();
}