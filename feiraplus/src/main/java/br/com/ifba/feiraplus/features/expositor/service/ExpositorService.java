package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.categoria.repository.CategoriaRepository;
import br.com.ifba.feiraplus.features.expositor.dto.ExpositorPostDto;
import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.exception.ExpositorNaoEncontrado;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.persistence.EntityNotFoundException;
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
public class ExpositorService implements ExpositorIService{

    private final ExpositorRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ObjectMapperUtil mapUtil;

    @Transactional
    public Expositor save(ExpositorPostDto expositorDto) {

        // 1. Converter os dados básicos (Nome, Documentação, Status)
        Expositor expositor = mapUtil.map(expositorDto, Expositor.class);

        // 2. BUSCAR A CATEGORIA PELO ID (A parte que estava faltando!)
        Categoria categoria = categoriaRepository.findById(expositorDto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + expositorDto.getCategoriaId()));

        // 3. VINCULAR A CATEGORIA AO EXPOSITOR
        expositor.setCategoria(categoria);

        // 4. Salvar no banco
        return repository.save(expositor);
    }


    @Transactional(readOnly = true)
    public List<Expositor> findAll() {

        try{
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException("Nao retornado Lista de expositor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Expositor  findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ExpositorNaoEncontrado(String.format("Expositor com id %s  não encontrado", id)));
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

    @Override
    @Transactional
    public Expositor update(Long id, ExpositorPostDto expositorDto) {

        // 1. Busca o expositor existente (ou lança erro se não existir)
        Expositor expositorExistente = repository.findById(id)
                .orElseThrow(() -> new ExpositorNaoEncontrado("Expositor não encontrado com o id: " + id));

        // 2. Busca a nova Categoria pelo ID que veio no DTO
        Categoria novaCategoria = categoriaRepository.findById(expositorDto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + expositorDto.getCategoriaId()));

        // 3. Atualiza os dados do objeto existente
        expositorExistente.setNome(expositorDto.getNome());
        expositorExistente.setDocumentacao(expositorDto.getDocumentacao());
        expositorExistente.setStatus(expositorDto.getStatus());

        // 4. Atualiza o relacionamento
        expositorExistente.setCategoria(novaCategoria);

        // 5. Salva (o JPA entende que é update porque o objeto já tem ID)
        return repository.save(expositorExistente);
    }
}
