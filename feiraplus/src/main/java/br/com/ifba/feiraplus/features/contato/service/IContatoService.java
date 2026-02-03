package br.com.ifba.feiraplus.features.contato.service;

import br.com.ifba.feiraplus.features.contato.dto.ContatoGetDto;
import br.com.ifba.feiraplus.features.contato.dto.ContatoPostDto;
import br.com.ifba.feiraplus.features.contato.entity.Contato;
import br.com.ifba.feiraplus.features.contato.entity.Contato.TipoAssunto;
import java.util.List;

public interface IContatoService {
    
    Contato save(Contato contato);
    
    List<ContatoGetDto> findAll();
    
    List<ContatoGetDto> findByLidaFalse();
    
    List<ContatoGetDto> findByAssunto(TipoAssunto assunto);
    
    ContatoGetDto marcarComoLida(Long id);
    
    ContatoGetDto marcarComoNaoLida(Long id);
    
    ContatoGetDto findById(Long id);
    
    void delete(Long id);
    
    Long countByLidaFalse();
    
    List<ContatoGetDto> findRecentes();
}
