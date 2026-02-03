package br.com.ifba.feiraplus.features.produto.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;
import br.com.ifba.feiraplus.features.produto.repository.ProdutoRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoIService {

    private final ProdutoRepository produtoRepository;
    private final ExpositorRepository expositorRepository;
    private final ObjectMapperUtil objectMapperUtil;

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
    public List<Produto> findByExpositorId(Long expositorId) {
        return produtoRepository.findByExpositorId(expositorId);
    }

// Importe o Repository do Expositor
// private final ExpositorRepository expositorRepository;

    @Override
    public Produto save(ProdutoRequestDTO dto) {
        // 1. Converte o DTO para Entidade (Produto)
        Produto produto = objectMapperUtil.map(dto, Produto.class);

        // 2. Busca o Expositor pelo ID que veio no DTO
        Expositor expositor = expositorRepository.findById(dto.getExpositorId())
                .orElseThrow(() -> new RuntimeException("Expositor não encontrado com ID: " + dto.getExpositorId()));

        // 3. VINCULA O PRODUTO AO EXPOSITOR (O Passo Mágico)
        produto.setExpositor(expositor);

        // 4. Salva no banco
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
