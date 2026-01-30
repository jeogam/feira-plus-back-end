package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.features.feira.entity.FeiraEvento;
import br.com.ifba.feiraplus.features.feira.entity.FeiraPermanente;
import br.com.ifba.feiraplus.features.feira.exception.FeiraPermanenteNotFoundException;
import br.com.ifba.feiraplus.features.feira.repository.FeiraPermanenteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeiraPermanenteService implements IFeiraPermanenteService {

    // Injeção de repositórios
    private final FeiraPermanenteRepository repository;
    private final ExpositorRepository expositorRepository;

    // Método auxiliar para sincronizar a lista de expositores
    private void sincronizarExpositores(FeiraPermanente feira, List<Long> expositorIds) {
        if (expositorIds == null || expositorIds.isEmpty()) {
            feira.setExpositores(new ArrayList<>());
            return;
        }

        List<Expositor> expositores = expositorRepository.findAllById(expositorIds);
        feira.setExpositores(expositores);
    }


    @Override
    @Transactional
    public FeiraPermanente save(FeiraPermanente feira) {
        validar(feira);

        // Assume que o getter do campo @Transient Feira.expositorIds existe
        sincronizarExpositores(feira, feira.getExpositorIds());

        FeiraPermanente saved = repository.save(feira);
        auditar("CRIAR", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public FeiraPermanente update(Long id, FeiraPermanente feira) {
        validar(feira);

        FeiraPermanente existente = repository.findById(id)
                .orElseThrow(() -> new FeiraPermanenteNotFoundException(id));

        existente.setNome(feira.getNome());
        existente.setLocal(feira.getLocal());
        existente.setEspacos(feira.getEspacos());
        existente.setHoraAbertura(feira.getHoraAbertura());
        existente.setHoraFechamento(feira.getHoraFechamento());
        existente.setFrequencia(feira.getFrequencia());
        existente.setFoto(feira.getFoto());

        // Assume que o getter do campo @Transient Feira.expositorIds existe
        sincronizarExpositores(existente, feira.getExpositorIds());

        FeiraPermanente atualizado = repository.save(existente);
        auditar("ATUALIZAR", id);
        return atualizado;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 1. Busca a feira que será excluída
        FeiraPermanente feira = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feira não encontrada"));

        // 2. DESVINCULAR OS EXPOSITORES (Limpar a tabela de junção)
        // Como é ManyToMany, precisamos ir em cada expositor e remover esta feira da lista dele
        // para manter o Java consistente, e depois limpar a lista da feira.

        for (Expositor expositor : feira.getExpositores()) {
            // Remove a feira da lista de feiras do expositor
            expositor.getFeiras().remove(feira);

            // Opcional: Se necessário, salve o expositor (geralmente o cascade cuida ou o passo abaixo resolve)
            // expositorRepository.save(expositor);
        }

        // 3. Limpa a lista de expositores da própria feira
        // Isso é crucial no ManyToMany para o Hibernate entender que a relação acabou
        feira.getExpositores().clear();

        // 4. Agora pode apagar a feira.
        // Como a lista de relações está vazia, não haverá erro de Foreign Key.
        repository.delete(feira);
    }
    // CORREÇÃO: Implementação do método findById que estava faltando
    @Override
    @Transactional(readOnly = true)
    public FeiraPermanente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FeiraPermanenteNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeiraPermanente> findAll() {
        return repository.findAll();
    }

    private void validar(FeiraPermanente feira) {
        if (feira.getNome() == null || feira.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (feira.getLocal() == null || feira.getLocal().isBlank()) {
            throw new IllegalArgumentException("Local é obrigatório");
        }

        if (feira.getEspacos() < 0) {
            throw new IllegalArgumentException("O total de espaços não pode ser negativo");
        }

        if (feira.getFrequencia() == null) {
            throw new IllegalArgumentException("Frequência é obrigatória");
        }
    }

    private void auditar(String acao, Long id) {
        System.out.println("AÇÃO: " + acao + " FEIRA PERMANENTE: " + id);
    }
}