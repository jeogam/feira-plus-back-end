package br.com.ifba.feiraplus.features.expositor.service;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.expositor.repository.ExpositorRepository;
import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpositorService implements ExpositorIService{

    private final ExpositorRepository repository;


    @Transactional
    public Expositor save(Expositor expositor) {

         try{
             return repository.save(expositor);
         }catch(Exception e){
             log.error("Erro ao tentar salvar o expositor: {}", expositor, e);
             throw new BusinessException("Nao foi possivel salvar expositor");
         }
    }


    @Transactional(readOnly = true)
    public List<Expositor> findAll() {

        try{
            return repository.findAll();
        }catch (Exception e){
            throw new BusinessException("Nao retornado Lista de expositor");
        }
    }
}
