package br.com.ifba.feiraplus.features.contato.exception;

import br.com.ifba.feiraplus.infrastructure.exception.BusinessException;

public class ContatoNotFoundException extends BusinessException {
    public ContatoNotFoundException(String message) {
        super(message);
    }
}
