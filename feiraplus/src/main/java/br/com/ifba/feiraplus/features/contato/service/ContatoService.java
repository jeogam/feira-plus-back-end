package br.com.ifba.feiraplus.features.contato.service;

import br.com.ifba.feiraplus.features.contato.dto.ContatoGetDto;
import br.com.ifba.feiraplus.features.contato.entity.Contato;
import br.com.ifba.feiraplus.features.contato.entity.Contato.TipoAssunto;
import br.com.ifba.feiraplus.features.contato.exception.ContatoNotFoundException;
import br.com.ifba.feiraplus.features.contato.repository.ContatoRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService implements IContatoService {

    private final ContatoRepository repository;

    /**
     * Cria uma nova mensagem de contato
     * Endpoint público - não requer autenticação
     */
    @Transactional
    @Override
    public Contato save(Contato contato) {
        try {
            return repository.save(contato);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível salvar o contato");
        }
    }

    /**
     * Lista todas as mensagens
     * Requer autenticação ADMIN
     */
    @Transactional(readOnly = true)
    @Override
    public List<ContatoGetDto> findAll() {
        return repository.findAll()
            .stream()
            .map(ContatoGetDto::new)
            .collect(Collectors.toList());
    }

    /**
     * Lista mensagens não lidas
     * Requer autenticação ADMIN
     */
    @Transactional(readOnly = true)
    @Override
    public List<ContatoGetDto> findByLidaFalse() {
        return repository.findByLidaFalseOrderByDataEnvioDesc()
            .stream()
            .map(ContatoGetDto::new)
            .collect(Collectors.toList());
    }

    /**
     * Lista mensagens por tipo de assunto
     * Requer autenticação ADMIN
     */
    @Transactional(readOnly = true)
    @Override
    public List<ContatoGetDto> findByAssunto(TipoAssunto assunto) {
        return repository.findByAssuntoOrderByDataEnvioDesc(assunto)
            .stream()
            .map(ContatoGetDto::new)
            .collect(Collectors.toList());
    }

    /**
     * Marca uma mensagem como lida
     * Requer autenticação ADMIN
     */
    @Transactional
    @Override
    public ContatoGetDto marcarComoLida(Long id) {
        Contato contato = repository.findById(id)
            .orElseThrow(() -> new ContatoNotFoundException("Contato não encontrado"));
        
        contato.setLida(true);
        contato.setDataLeitura(LocalDateTime.now());
        
        Contato atualizado = repository.save(contato);
        return new ContatoGetDto(atualizado);
    }

    /**
     * Marca uma mensagem como não lida
     * Requer autenticação ADMIN
     */
    @Transactional
    @Override
    public ContatoGetDto marcarComoNaoLida(Long id) {
        Contato contato = repository.findById(id)
            .orElseThrow(() -> new ContatoNotFoundException("Contato não encontrado"));
        
        contato.setLida(false);
        contato.setDataLeitura(null);
        
        Contato atualizado = repository.save(contato);
        return new ContatoGetDto(atualizado);
    }

    /**
     * Busca uma mensagem específica por ID
     * Requer autenticação ADMIN
     */
    @Transactional(readOnly = true)
    @Override
    public ContatoGetDto findById(Long id) {
        Contato contato = repository.findById(id)
            .orElseThrow(() -> new ContatoNotFoundException("Contato não encontrado"));
        return new ContatoGetDto(contato);
    }

    /**
     * Exclui uma mensagem
     * Requer autenticação ADMIN
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ContatoNotFoundException("Contato não encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Conta mensagens não lidas
     * Requer autenticação ADMIN
     */
    @Transactional(readOnly = true)
    @Override
    public Long countByLidaFalse() {
        return repository.countByLidaFalse();
    }

    /**
     * Lista mensagens recentes (últimos 7 dias)
     * Requer autenticação ADMIN
     */
    @Transactional(readOnly = true)
    @Override
    public List<ContatoGetDto> findRecentes() {
        LocalDateTime seteDiasAtras = LocalDateTime.now().minusDays(7);
        return repository.findRecentes(seteDiasAtras)
            .stream()
            .map(ContatoGetDto::new)
            .collect(Collectors.toList());
    }
}
