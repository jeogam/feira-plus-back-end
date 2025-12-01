package br.com.ifba.feiraplus.features.atribuicao.repository;

import br.com.ifba.feiraplus.features.atribuicao.entity.Atribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtribuicaoRepository extends JpaRepository<Atribuicao, Long> {
}