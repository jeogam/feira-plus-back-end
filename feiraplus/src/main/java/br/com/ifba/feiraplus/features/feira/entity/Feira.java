package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.features.espaco.entity.Espaco; // Assumindo que você criará essa classe
import br.com.ifba.feiraplus.features.usuario.entity.Usuario; // Assumindo que você criará essa classe
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feira")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Feira extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String local;

    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    // --- RELAÇÕES DO DIAGRAMA ---

    // 1 Usuário Coordena N Feiras
    // A chave estrangeira fica aqui na tabela 'feira'
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // 1 Feira Possui N Espaços
    // mappedBy = "feira" indica que a classe Espaco é dona da relação (tem o FK)
    @OneToMany(mappedBy = "feira", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Espaco> espacos = new ArrayList<>();

}