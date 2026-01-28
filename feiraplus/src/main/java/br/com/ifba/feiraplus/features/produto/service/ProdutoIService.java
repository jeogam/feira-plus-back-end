package br.com.ifba.feiraplus.features.produto.service;

import br.com.ifba.feiraplus.features.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;

import java.util.List;

public interface ProdutoIService {
    List<Produto> findAll();
    Produto save(ProdutoRequestDTO produtoDto);
    Produto update(Long id, ProdutoRequestDTO produtoDto);
    void delete(Long id);
    Produto findById(Long id);

    List<Object> findByExpositorId(Long expositorId);
}
