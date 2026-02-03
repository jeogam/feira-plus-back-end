package br.com.ifba.feiraplus.features.evento.entity;

import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventos")
public class Evento extends PersistenceEntity {

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feira_id", nullable = false)
    @JsonBackReference // Adicione esta anotação
    private Feira feira;
}

