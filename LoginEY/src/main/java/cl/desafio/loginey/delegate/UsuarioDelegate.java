package cl.desafio.loginey.delegate;

import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.request.UsuarioActualizarRequest;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceObject;

public interface UsuarioDelegate {

  ResponseServiceObject getAuth(CredencialesRequest credencialesRequest);

  ResponseServiceObject registrarUsuario(UsuarioRequest usuarioRequest);

  ResponseServiceObject eliminarUsuario(String userUUID);

  ResponseServiceObject actualizarUsuario(UsuarioActualizarRequest usuarioActualizarRequest);

  ResponseServiceObject obtener();
}
