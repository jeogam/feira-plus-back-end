package br.com.ifba.feiraplus.features.categoria.service;

import br.com.ifba.feiraplus.features.categoria.dto.request.CategoriaRequestDTO;
import br.com.ifba.feiraplus.features.categoria.entity.Categoria;
import br.com.ifba.feiraplus.features.categoria.repository.CategoriaRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaIService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada com ID: " + id));
    }

    @Override
    public Categoria save(CategoriaRequestDTO dto) {
        // Mapeamento manual simples ou via ModelMapper dentro do Service se preferir
        // Aqui instanciamos direto pois é simples
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Long id, CategoriaRequestDTO dto) {
        Categoria categoriaExistente = findById(id);

        // Atualiza os dados
        categoriaExistente.setNome(dto.getNome());

        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    public void delete(Long id) {
        Categoria categoria = findById(id);
        // Regra de negócio opcional: Validar se a categoria está em uso antes de deletar
        categoriaRepository.delete(categoria);
    }
}