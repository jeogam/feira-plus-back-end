package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.feira.entity.Atribuicao;

public interface IAtribuicaoService {
    Atribuicao atribuir(Long idEspaco, Long idExpositor);
    void removerAtribuicao(Long idAtribuicao);
}