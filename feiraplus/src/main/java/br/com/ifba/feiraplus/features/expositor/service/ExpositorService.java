package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.categoria.repository.CategoriaRepository;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.exception.ExpositorNaoEncontrado;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpositorService implements ExpositorIService {

    private final ExpositorRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ObjectMapperUtil mapUtil;

    // Novo método para média
    public Double getMediaGeralAvaliacoes() {
        return repository.calcularMediaGlobal();
    }

    @Override
    @Transactional
    public Expositor save(ExpositorPostDto expositorDto) {
        validarNota(expositorDto.getNota()); // Validação

        Expositor expositor = mapUtil.map(expositorDto, Expositor.class);

        Categoria categoria = categoriaRepository.findById(expositorDto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + expositorDto.getCategoriaId()));

        expositor.setCategoria(categoria);
        // O mapUtil já deve copiar a nota se os nomes baterem, mas garantindo:
        expositor.setNota(expositorDto.getNota());

        return repository.save(expositor);
    }

    @Override
    @Transactional
    public Expositor update(Long id, ExpositorPostDto expositorDto) {
        validarNota(expositorDto.getNota()); // Validação

        Expositor expositorExistente = repository.findById(id)
                .orElseThrow(() -> new ExpositorNaoEncontrado("Expositor não encontrado com o id: " + id));

        Categoria novaCategoria = categoriaRepository.findById(expositorDto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + expositorDto.getCategoriaId()));

        expositorExistente.setNome(expositorDto.getNome());
        expositorExistente.setStatus(expositorDto.getStatus());
        expositorExistente.setCategoria(novaCategoria);
        expositorExistente.setDescricao(expositorDto.getDescricao());
        expositorExistente.setFoto(expositorDto.getFoto());
        expositorExistente.setNota(expositorDto.getNota()); // Atualiza nota

        return repository.save(expositorExistente);
    }

    // ... Outros métodos (findAll, findById, delete, buscarPorCategoria) mantêm-se iguais ...

    @Transactional(readOnly = true)
    public List<Expositor> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Expositor findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ExpositorNaoEncontrado(String.format("Expositor com id %s não encontrado", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Expositor expositor = this.findById(id);
        repository.delete(expositor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Expositor> buscarPorCategoria(String nomeCategoria, Pageable pageable) {
        Page<Expositor> paginaExpositores = repository.findByCategoriaNome(nomeCategoria, pageable);
        if (paginaExpositores.isEmpty()) {
            throw new ExpositorNaoEncontrado(
                    String.format("Nenhum expositor encontrado para a categoria '%s'", nomeCategoria)
            );
        }
        return paginaExpositores;
    }

    // Método auxiliar de validação
    private void validarNota(Float nota) {
        if (nota != null && (nota < 1 || nota > 5)) {
            throw new BusinessException("A nota deve estar entre 1 e 5.");
        }
    }
}