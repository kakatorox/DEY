package cl.desafio.loginey.controller;

import cl.desafio.loginey.delegate.UsuarioDelegate;
import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceObject;
import cl.desafio.loginey.util.ToolsUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
  //aca declarar servicios sise requieren
  @Autowired
  private UsuarioDelegate usuarioDelegate;

  @GetMapping("/UUID")
  public String UUID(){
    return ToolsUtil.getUUID();
  }

  @PostMapping("/registrar")
  public ResponseEntity<ResponseServiceObject> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
    return new ResponseEntity<>(usuarioDelegate.registrarUsuario(usuarioRequest), HttpStatus.OK);
  }

  @PostMapping("/usuario")
  public ResponseEntity<ResponseServiceObject> loginUsuario(@RequestBody CredencialesRequest credencialesRequest){
    return new ResponseEntity<>(usuarioDelegate.getAuth(credencialesRequest), HttpStatus.OK);
  }
}
