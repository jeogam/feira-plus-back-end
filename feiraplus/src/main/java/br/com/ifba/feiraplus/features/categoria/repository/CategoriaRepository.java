package br.com.ifba.feiraplus.features.categoria.repository;

import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}