package br.com.ifba.feiraplus.features.produto.service;

import br.com.ifba.feiraplus.features.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;
import br.com.ifba.feiraplus.features.produto.repository.ProdutoRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoIService {

    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id));
    }

    @Override
    public Produto save(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setFoto(dto.getFoto());
        produto.setDescricao(dto.getDescricao());

        return produtoRepository.save(produto);
    }

    @Override
    public Produto update(Long id, ProdutoRequestDTO dto) {
        Produto produtoExistente = findById(id);

        produtoExistente.setNome(dto.getNome());
        produtoExistente.setPreco(dto.getPreco());
        produtoExistente.setFoto(dto.getFoto());
        produtoExistente.setDescricao(dto.getDescricao());

        return produtoRepository.save(produtoExistente);
    }

    @Override
    public void delete(Long id) {
        Produto produto = findById(id);
        produtoRepository.delete(produto);
    }
}
