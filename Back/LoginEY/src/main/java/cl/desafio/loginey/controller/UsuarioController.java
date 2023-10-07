package cl.desafio.loginey.controller;

import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.response.ResponseServiceObject;
import cl.desafio.loginey.util.ToolsUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
  //aca declarar servicios sise requieren

  @GetMapping("/hola")
  public String hola(){
    return ToolsUtil.getUUID();
  }

//  @PostMapping("/UsuarioRegistrar")
//  @ResponseStatus(code = HttpStatus.ACCEPTED)
//  public ResponseUsuario guardar(@RequestBody Usuario request) {
//    return service.guardarUsuario(request);
//
//  }

  @PostMapping("/Login")
  public ResponseEntity<ResponseServiceObject> Login(@RequestBody CredencialesRequest credencialesRequest){
      ResponseServiceObject responseServiceObject = null;// esto no tiene que ir aca

      System.out.println(credencialesRequest);
      return new ResponseEntity<>(responseServiceObject, HttpStatus.OK);
  }
}
