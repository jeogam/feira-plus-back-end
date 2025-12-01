package br.com.ifba.feiraplus.features.expositor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpositorNaoEncontrado extends RuntimeException {
    // Construtor que aceita o nome do recurso e o ID para uma mensagem detalhada
    public ExpositorNaoEncontrado(String recurso, Object id) {
        super(String.format("%s com ID %s não encontrado.", recurso, id));
    }

    // Construtor padrão
    public ExpositorNaoEncontrado(String message) {
        super(message);
    }
}
