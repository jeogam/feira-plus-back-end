package br.com.ifba.feiraplus.features.usuario.controller;

import br.com.ifba.feiraplus.features.usuario.dto.UpdateSenhaDto;
import br.com.ifba.feiraplus.features.usuario.dto.UsuarioGetDto;
import br.com.ifba.feiraplus.features.usuario.dto.UsuarioPostDto;
import br.com.ifba.feiraplus.features.usuario.dto.UsuarioUpdateDto;
import br.com.ifba.feiraplus.features.usuario.entity.Usuario;
import br.com.ifba.feiraplus.features.usuario.exception.UsuarioNotFoundException;
import br.com.ifba.feiraplus.features.usuario.service.IUsuarioService;
import br.com.ifba.feiraplus.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController{


  private final IUsuarioService service;
  private final ObjectMapperUtil objectMapperUtil;

  @PostMapping("/register")
  public ResponseEntity<UsuarioGetDto> save(@RequestBody @Valid UsuarioPostDto usuarioPostDto) {
    Usuario newUser = service.save(objectMapperUtil.map(usuarioPostDto, Usuario.class));

    return ResponseEntity.status(HttpStatus.CREATED).body(objectMapperUtil.map(newUser, UsuarioGetDto.class));
  }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioGetDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioUpdateDto dto // @Valid ativa as anotações do DTO
    ) {
        // Passamos o ID e o DTO diretamente para o serviço
        Usuario usuarioAtualizado = service.update(id, dto);

        UsuarioGetDto resposta = objectMapperUtil.map(usuarioAtualizado, UsuarioGetDto.class);

        return ResponseEntity.ok(resposta);
    }

  @PatchMapping("/update/senha/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> updatePassword(
          @PathVariable Long id,
          @RequestBody @Valid UpdateSenhaDto dto
  ) {
      service.updatePassword(id,dto);

      return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("buscar/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UsuarioGetDto> findById(@PathVariable Long id) {

    Usuario user = service.findById(id);
    return ResponseEntity.ok(objectMapperUtil.map(user, UsuarioGetDto.class));
  }


  @GetMapping("/buscar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<UsuarioGetDto>> findAll(Pageable pageable) {

    Page<Usuario> usersPage = service.findAll(pageable);

    Page<UsuarioGetDto> dtoPage = usersPage.map(
            user -> objectMapperUtil.map(user,UsuarioGetDto.class));

    return ResponseEntity.ok(dtoPage);
  }


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Usuario> getUsuarioLogado(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(401).build();

        Usuario usuario = getUsuarioEntity(userDetails);
        return ResponseEntity.ok(usuario);
    }

    // --- MÉTODOS AUXILIARES ---

    private Usuario getUsuarioEntity(UserDetails userDetails) {
        return service.buscarPorUsername(userDetails.getUsername());
    }



    @RestControllerAdvice
  public class ExceptionHandlerConfig{

    @ExceptionHandler (UsuarioNotFoundException.class)
    public ResponseEntity<String> notFound(Exception e) {return ResponseEntity.status(404).body(e.getMessage());}

    @ExceptionHandler (IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(Exception e) {return ResponseEntity.status(400).body(e.getMessage());}
  }
}
