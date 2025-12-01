package br.com.ifba.feiraplus.features.feira.service;

import br.com.ifba.feiraplus.features.feira.entity.Espaco;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.features.feira.enums.StatusEspaco;
import br.com.ifba.feiraplus.features.feira.repository.EspacoRepository;
import br.com.ifba.feiraplus.features.feira.repository.FeiraPermanenteRepository; // Ou genérico se houver
import br.com.ifba.feiraplus.features.feira.repository.FeiraEventoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacoService implements IEspacoService {

    @Autowired
    private EspacoRepository espacoRepository;
    
    // Precisamos do repositório de Feira para vincular o espaço. 
    // Como Feira é abstrata e temos repositórios separados, 
    // vou assumir uma injeção genérica ou busca em ambos para simplificar,
    // mas o ideal seria ter um FeiraRepository genérico. 
    // Vou simular a busca simplificada aqui.
    @Autowired
    private FeiraPermanenteRepository feiraPermanenteRepository;
    @Autowired
    private FeiraEventoRepository feiraEventoRepository;

    @Override
    public Espaco save(Long feiraId, Espaco espaco) {
        // 1. Vincular a Feira (tenta achar em Permanente, senão tenta em Evento)
        Feira feira = feiraPermanenteRepository.findById(feiraId)
                .map(f -> (Feira) f)
                .orElseGet(() -> feiraEventoRepository.findById(feiraId)
                        .orElseThrow(() -> new EntityNotFoundException("Feira não encontrada com id: " + feiraId)));
        
        espaco.setFeira(feira);
        
        // Regra: Sistema salva como Vago 
        espaco.setStatus(StatusEspaco.VAGO);
        
        return espacoRepository.save(espaco);
    }

    @Override
    public Espaco update(Long id, Espaco espacoAtualizado) {
        Espaco existente = findById(id);
        
        existente.setNome(espacoAtualizado.getNome());
        existente.setLocal(espacoAtualizado.getLocal());
        existente.setHorarios(espacoAtualizado.getHorarios());
        existente.setDataInicio(espacoAtualizado.getDataInicio());
        existente.setDataFim(espacoAtualizado.getDataFim());
        
        return espacoRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        Espaco espaco = findById(id);
        
        // Regra: Só é possível excluir espaços Vagos 
        if (espaco.getStatus() == StatusEspaco.OCUPADO) {
            throw new IllegalStateException("Não é possível excluir um espaço que está Ocupado.");
        }
        
        espacoRepository.delete(espaco);
    }

    @Override
    public Espaco findById(Long id) {
        return espacoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Espaço não encontrado: " + id));
    }

    @Override
    public List<Espaco> findAllByFeiraId(Long feiraId) {
        return espacoRepository.findByFeiraId(feiraId);
    }
}