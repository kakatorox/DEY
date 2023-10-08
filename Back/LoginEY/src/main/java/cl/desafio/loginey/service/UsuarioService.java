package cl.desafio.loginey.service;

import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceObject;

public interface UsuarioService {
  ResponseServiceObject getAuth(CredencialesRequest credencialesRequest);

  ResponseServiceObject registrarUsuario(UsuarioRequest usuarioRequest);
}
