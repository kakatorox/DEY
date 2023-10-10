package cl.desafio.loginey.controller;

import cl.desafio.loginey.delegate.UsuarioDelegate;
import cl.desafio.loginey.request.UsuarioActualizarRequest;
import cl.desafio.loginey.response.ResponseServiceObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "swaggereyapi")
@RequiredArgsConstructor
public class UsuarioController {
  @Autowired
  private UsuarioDelegate usuarioDelegate;

  @GetMapping("/obtener")
  public ResponseEntity<String> obtener(){
    return ResponseEntity.ok("entre contoqken");
  }
  @DeleteMapping("/eliminar/{userUUID}")
  public ResponseEntity<ResponseServiceObject> eliminar(@PathVariable String userUUID){
    return new ResponseEntity<>(usuarioDelegate.eliminarUsuario(userUUID), HttpStatus.OK);
  }
  @PutMapping("/actualizar")
  public ResponseEntity<ResponseServiceObject> actualizar(@RequestBody UsuarioActualizarRequest usuarioActualizarRequest){
    return new ResponseEntity<>(usuarioDelegate.actualizarUsuario(usuarioActualizarRequest), HttpStatus.OK);
  }
}
