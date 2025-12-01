package br.com.ifba.feiraplus.features.atribuicao.service;

import br.com.ifba.feiraplus.features.atribuicao.dto.request.AtribuicaoPostRequestDTO;
import br.com.ifba.feiraplus.features.atribuicao.entity.Atribuicao;
import java.util.List;

public interface AtribuicaoIService {
    List<Atribuicao> findAll();
    Atribuicao save(AtribuicaoPostRequestDTO dto);
    Atribuicao update(Long id, AtribuicaoPostRequestDTO dto);
    void delete(Long id);
    Atribuicao findById(Long id);
}