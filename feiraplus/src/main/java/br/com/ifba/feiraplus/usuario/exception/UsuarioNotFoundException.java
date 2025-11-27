package br.com.ifba.feiraplus.usuario.exception;

public class UsuarioNotFoundException extends RuntimeException{
  public UsuarioNotFoundException(long id){super("usuario não encontrado:" + id);}
}