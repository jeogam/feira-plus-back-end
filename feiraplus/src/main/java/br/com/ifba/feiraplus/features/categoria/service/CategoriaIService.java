package br.com.ifba.feiraplus.features.categoria.service;

import br.com.ifba.feiraplus.features.categoria.dto.request.CategoriaRequestDTO;
import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import java.util.List;

public interface CategoriaIService {
    List<Categoria> findAll();
    Categoria save(CategoriaRequestDTO categoriaDto);
    Categoria update(Long id, CategoriaRequestDTO categoriaDto);
    void delete(Long id);
    Categoria findById(Long id);
}