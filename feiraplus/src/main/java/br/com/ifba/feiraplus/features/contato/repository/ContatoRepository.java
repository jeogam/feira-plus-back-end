package br.com.ifba.feiraplus.features.contato.repository;

import br.com.ifba.feiraplus.features.contato.entity.Contato;
import br.com.ifba.feiraplus.features.contato.entity.Contato.TipoAssunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    // Buscar mensagens não lidas
    List<Contato> findByLidaFalseOrderByDataEnvioDesc();

    // Buscar por tipo de assunto
    List<Contato> findByAssuntoOrderByDataEnvioDesc(TipoAssunto assunto);

    // Buscar por período
    List<Contato> findByDataEnvioBetweenOrderByDataEnvioDesc(
        LocalDateTime inicio, 
        LocalDateTime fim
    );

    // Contar mensagens não lidas
    Long countByLidaFalse();

    // Buscar mensagens recentes (últimos 7 dias)
    @Query("SELECT c FROM Contato c WHERE c.dataEnvio >= :dataInicio ORDER BY c.dataEnvio DESC")
    List<Contato> findRecentes(LocalDateTime dataInicio);

    // Buscar por email do remetente
    List<Contato> findByEmailOrderByDataEnvioDesc(String email);
}
