package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.features.expositor.entity.Expositor;
import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "feira")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Feira extends PersistenceEntity {

    // Nome da Feira (Obrigatório)
    @Column(nullable = false)
    private String nome;

    // Quantidade total de espaços disponíveis
    @Column(nullable = false)
    private int espacos;

    // Localização da Feira (Obrigatório)
    @Column(nullable = false)
    private String local;

    // Hora de Abertura
    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    // Hora de Fechamento
    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    // Relação N:1 com Usuário (Coordenador)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relação N:N com Expositor: Uma Feira pode ter vários Expositores e vice-versa.
    // Cria uma tabela de associação FEIRA_EXPOSITOR (o JPA cuida da criação).
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "feira_expositor", // Nome da tabela de associação
            joinColumns = @JoinColumn(name = "feira_id"), // Coluna que referencia Feira
            inverseJoinColumns = @JoinColumn(name = "expositor_id") // Coluna que referencia Expositor
    )
    private List<Expositor> expositores;

    @Transient
    private List<Long> expositorIds;

    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto;

    @Column(name = "nota")
    private Float nota;
}