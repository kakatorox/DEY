package cl.desafio.loginey.delegate.impl;

import cl.desafio.loginey.delegate.UsuarioDelegate;
import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.request.UsuarioActualizarRequest;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceObject;
import cl.desafio.loginey.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("usuarioDelegate")
public class UsuarioDelegateImpl implements UsuarioDelegate {
  @Autowired
  private UsuarioService usuarioService;


  @Override
  public ResponseServiceObject getAuth(CredencialesRequest credencialesRequest) {
    return usuarioService.getAuth(credencialesRequest);
  }

  @Override
  public ResponseServiceObject registrarUsuario(UsuarioRequest usuarioRequest) {
    return usuarioService.registrarUsuario(usuarioRequest);
  }

  @Override
  public ResponseServiceObject eliminarUsuario(String userUUID) {
    return usuarioService.eliminarUsuario(userUUID);
  }

  @Override
  public ResponseServiceObject actualizarUsuario(UsuarioActualizarRequest usuarioActualizarRequest) {
    return usuarioService.actualizarUsuario(usuarioActualizarRequest);
  }

  @Override
  public ResponseServiceObject obtener() {
    return usuarioService.obtenerUsuarios();
  }
}
