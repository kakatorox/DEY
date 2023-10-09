package cl.desafio.loginey.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "swaggereyapi")
@RequiredArgsConstructor
public class UsuarioController {

  @GetMapping("/obtener")
  public ResponseEntity<String> hola(){
    return ResponseEntity.ok("entre contoqken");
  }

  @DeleteMapping("/eliminar")
  public ResponseEntity<String> eliminar
}
