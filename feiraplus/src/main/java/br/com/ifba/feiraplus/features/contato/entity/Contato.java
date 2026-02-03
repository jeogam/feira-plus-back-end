package br.com.ifba.feiraplus.features.contato.entity;

import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "contatos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contato extends PersistenceEntity {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoAssunto assunto;

    @Column(nullable = false, length = 1000)
    private String mensagem;

    @Column(nullable = false)
    private Boolean lida = false;

    @Column(name = "data_envio", nullable = false, updatable = false)
    private LocalDateTime dataEnvio;

    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura;

    @PrePersist
    protected void onCreate() {
        this.dataEnvio = LocalDateTime.now();
        if (this.lida == null) {
            this.lida = false;
        }
    }

    public enum TipoAssunto {
        SUGESTAO("Sugestão"),
        DUVIDA("Dúvida"),
        ELOGIO("Elogio"),
        QUERO_SER_EXPOSITOR("Quero ser Expositor"),
        RECLAMACAO("Reclamação"),
        OUTRO("Outro");

        private final String descricao;

        TipoAssunto(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
