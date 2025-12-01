package br.com.ifba.feiraplus.features.feira.entity;

// Removemos o import antigo pois agora estão na mesma pasta
import java.time.LocalDate;

import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "atribuicoes")
public class Atribuicao extends PersistenceEntity {

    @Column(nullable = false)
    private LocalDate dataAtribuicao;

    @OneToOne
    @JoinColumn(name = "espaco_id", nullable = false, unique = true)
    private Espaco espaco;

    @ManyToOne
    @JoinColumn(name = "expositor_id", nullable = false)
    private Expositor expositor;

    // --- Construtores ---
    public Atribuicao() {}

    public Atribuicao(LocalDate dataAtribuicao, Espaco espaco, Expositor expositor) {
        this.dataAtribuicao = dataAtribuicao;
        this.espaco = espaco;
        this.expositor = expositor;
    }

    // --- Getters e Setters ---
    public LocalDate getDataAtribuicao() {
        return dataAtribuicao;
    }
    public void setDataAtribuicao(LocalDate dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }
    public Espaco getEspaco() {
        return espaco;
    }
    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }
    public Expositor getExpositor() {
        return expositor;
    }
    public void setExpositor(Expositor expositor) {
        this.expositor = expositor;
    }
}