package br.com.ifba.feiraplus.features.feira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifba.feiraplus.features.feira.entity.Atribuicao;

@Repository
public interface AtribuicaoRepository extends JpaRepository<Atribuicao, Long> {
    // Para ver o histórico de atribuições de um espaço específico
    List<Atribuicao> findByEspacoId(Long espacoId);
}