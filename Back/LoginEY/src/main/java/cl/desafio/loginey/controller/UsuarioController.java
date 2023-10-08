package cl.desafio.loginey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @GetMapping("/obtener")
  public ResponseEntity<String> hola(){
    return ResponseEntity.ok("entre contoqken");
  }
}
