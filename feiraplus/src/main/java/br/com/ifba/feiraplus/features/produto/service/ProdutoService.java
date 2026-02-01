package br.com.ifba.feiraplus.features.produto.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.produto.dto.request.ProdutoRequestDTO;
import br.com.ifba.feiraplus.features.produto.entity.Produto;
import br.com.ifba.feiraplus.features.produto.repository.ProdutoRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoIService {

    private final ProdutoRepository produtoRepository;
    private final ExpositorRepository expositorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado com ID: " + id));
    }

    // Método alterado para retornar List<Produto>
    public List<Produto> findByExpositorId(Long expositorId) {
        return produtoRepository.findByExpositorId(expositorId);
    }

    // Novo método para obter a média
    public Double getMediaGeralAvaliacoes() {
        return produtoRepository.calcularMediaGlobal();
    }

    @Override
    @Transactional
    public Produto save(ProdutoRequestDTO dto) {
        validarNota(dto.getNota()); // Validação de regra de negócio

        Expositor expositor = expositorRepository.findById(dto.getExpositorId())
                .orElseThrow(()-> new EntityNotFoundException("Expositor não encontrado"));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setFoto(dto.getFoto());
        produto.setDescricao(dto.getDescricao());
        produto.setNota(dto.getNota()); // Setando a nota
        produto.setExpositor(expositor);

        return produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public Produto update(Long id, ProdutoRequestDTO dto) {
        validarNota(dto.getNota());

        Produto produtoExistente = findById(id);

        produtoExistente.setNome(dto.getNome());
        produtoExistente.setPreco(dto.getPreco());
        produtoExistente.setFoto(dto.getFoto());
        produtoExistente.setDescricao(dto.getDescricao());
        produtoExistente.setNota(dto.getNota()); // Atualizando a nota

        return produtoRepository.save(produtoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Produto produto = findById(id);
        produtoRepository.delete(produto);
    }

    // Validação auxiliar
    private void validarNota(Float nota) {
        if (nota != null && (nota < 1 || nota > 5)) {
            throw new BusinessException("A nota deve estar entre 1 e 5.");
        }
    }
}