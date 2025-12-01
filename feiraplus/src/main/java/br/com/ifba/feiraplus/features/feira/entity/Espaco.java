package br.com.ifba.feiraplus.features.feira.entity;

import java.time.LocalDate;

import br.com.ifba.feiraplus.features.feira.enums.StatusEspaco;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "espacos")
public class Espaco extends PersistenceEntity {

    @Column(nullable = false)
    private String nome; // Ex: "Barraca 01", "Setor A"

    @Column(nullable = false)
    private String local; // Localização específica dentro da feira

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String horarios;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEspaco status;

    // Relacionamento: Uma Feira possui muitos Espaços
    @ManyToOne
    @JoinColumn(name = "feira_id", nullable = false)
    private Feira feira;

    // --- Construtores ---
    public Espaco() {
    }

    public Espaco(String nome, String local, LocalDate dataInicio, LocalDate dataFim, String horarios, StatusEspaco status, Feira feira) {
        this.nome = nome;
        this.local = local;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horarios = horarios;
        this.status = status;
        this.feira = feira;
    }

    // --- Getters e Setters Manuais (Para corrigir o erro do Lombok) ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public StatusEspaco getStatus() {
        return status;
    }

    public void setStatus(StatusEspaco status) {
        this.status = status;
    }

    public Feira getFeira() {
        return feira;
    }

    public void setFeira(Feira feira) {
        this.feira = feira;
    }
}