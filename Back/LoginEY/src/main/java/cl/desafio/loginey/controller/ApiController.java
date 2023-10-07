package cl.desafio.loginey.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
  //aca declarar servicios sise requieren

  @GetMapping("/hola")
  public String hola(){
    return "holaEWEWE";
  }

//  @PostMapping("/UsuarioRegistrar")
//  @ResponseStatus(code = HttpStatus.ACCEPTED)
//  public ResponseUsuario guardar(@RequestBody Usuario request) {
//    return service.guardarUsuario(request);
//
//  }


}
