package br.com.ifba.feiraplus.features.evento.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventoRequestDTO {
    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    private String descricao;
    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    @NotNull(message = "O ID da feira é obrigatório")
    private Long feiraId;

    // Getters e Setters Manuais
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    public Long getFeiraId() { return feiraId; }
    public void setFeiraId(Long feiraId) { this.feiraId = feiraId; }
}