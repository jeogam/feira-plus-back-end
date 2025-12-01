package br.com.ifba.feiraplus.features.espaco.entity;

import java.time.LocalDate;

import br.com.ifba.feiraplus.features.espaco.enums.StatusEspaco;
import br.com.ifba.feiraplus.features.feira.entity.Feira;
import br.com.ifba.feiraplus.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "espacos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Espaco extends PersistenceEntity {

    @Column(name = "nome", nullable = false, length = 150)
    @NotBlank(message = "O nome do espaço é obrigatório")
    private String nome;

    @Column(name = "local", nullable = false, length = 200)
    @NotBlank(message = "O local do espaço é obrigatório")
    private String local;

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    @NotNull(message = "A data de fim é obrigatória")
    private LocalDate dataFim;

    @Column(name = "horarios", nullable = false)
    @NotBlank(message = "A descrição dos horários é obrigatória")
    private String horarios;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "O status do espaço é obrigatório")
    private StatusEspaco status;

    // Relacionamento: Uma Feira possui muitos Espaços
    @ManyToOne
    @JoinColumn(name = "feira_id", nullable = false)
    @NotNull(message = "A feira vinculada é obrigatória")
    private Feira feira;

}