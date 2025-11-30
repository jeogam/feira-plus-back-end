package br.com.ifba.feiraplus.features.feira.exception;

public class FeiraPermanenteNotFoundException extends RuntimeException {

  public FeiraPermanenteNotFoundException(Long id) {
    super("Feira permanente não encontrada: " + id);
  }
}
