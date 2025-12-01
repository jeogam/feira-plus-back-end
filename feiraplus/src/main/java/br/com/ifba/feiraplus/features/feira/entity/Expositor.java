package br.com.ifba.feiraplus.features.feira.entity;

import br.com.ifba.feiraplus.features.feira.enums.StatusExpositor; // Ajustado
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "expositores")
public class Expositor extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documentacao; // CPF ou CNPJ

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExpositor status;

    // --- Construtores ---
    public Expositor() {}

    public Expositor(String nome, String documentacao, StatusExpositor status) {
        this.nome = nome;
        this.documentacao = documentacao;
        this.status = status;
    }

    // --- Getters e Setters ---
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDocumentacao() {
        return documentacao;
    }
    public void setDocumentacao(String documentacao) {
        this.documentacao = documentacao;
    }
    public StatusExpositor getStatus() {
        return status;
    }
    public void setStatus(StatusExpositor status) {
        this.status = status;
    }
}