package br.com.ifba.feiraplus.features.usuario.controller;

import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import br.com.ifba.feiraplus.features.usuario.exception.UsuarioNotFoundException;
import br.com.ifba.feiraplus.features.usuario.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements IUsuarioController{

  @Autowired
  private IUsuarioService service;

  @Override
  @PostMapping
  public ResponseEntity<Usuario> save(@RequestBody Usuario user) {
    Usuario newUser = service.save(user);
    return ResponseEntity
            .created(URI.create("/usuarios/" + newUser.getId()))
            .body(newUser);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<Usuario> update(
          @PathVariable Long id,
          @RequestBody Usuario user) {

    Usuario updated = service.update(id, user);
    return ResponseEntity.ok(updated);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Usuario> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<Usuario> findById(@PathVariable Long id) {
    Usuario user = service.findById(id);
    return ResponseEntity.ok(user);
  }

  @Override
  @GetMapping
  public ResponseEntity<List<Usuario>> findAll() {
    List<Usuario> users = service.findAll();
    return ResponseEntity.ok(users);
  }

  @RestControllerAdvice
  public class ExceptionHandlerConfig{

    @ExceptionHandler (UsuarioNotFoundException.class)
    public ResponseEntity<String> notFound(Exception e) {return ResponseEntity.status(404).body(e.getMessage());}

    @ExceptionHandler (IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(Exception e) {return ResponseEntity.status(400).body(e.getMessage());}
  }
}
