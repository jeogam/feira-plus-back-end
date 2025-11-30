package br.com.ifba.feiraplus.features.espaco.exception;

public class EspacoNotFoundException extends RuntimeException {
    public EspacoNotFoundException(Long id) {
        super("Espaço não encontrado com ID: " + id);
    }
}