package br.com.ifba.feiraplus.features.contato.dto;

import br.com.ifba.feiraplus.features.contato.entity.Contato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoGetDto {

    private Long id;
    private String nome;
    private String email;
    private Contato.TipoAssunto assunto;
    private String mensagem;
    private Boolean lida;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataLeitura;

    public ContatoGetDto(Contato contato) {
        this.id = contato.getId();
        this.nome = contato.getNome();
        this.email = contato.getEmail();
        this.assunto = contato.getAssunto();
        this.mensagem = contato.getMensagem();
        this.lida = contato.getLida();
        this.dataEnvio = contato.getDataEnvio();
        this.dataLeitura = contato.getDataLeitura();
    }
}
