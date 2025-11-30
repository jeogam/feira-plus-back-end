package br.com.ifba.feiraplus.features.feira.exception;

public class FeiraEventoNotFoundException extends RuntimeException {

  public FeiraEventoNotFoundException(Long id) {
    super("Feira evento não encontrada: " + id);
  }
}
