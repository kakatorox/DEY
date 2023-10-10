package cl.desafio.loginey.service.impl;

import cl.desafio.loginey.entity.Role;
import cl.desafio.loginey.entity.Telefono;
import cl.desafio.loginey.entity.Usuario;
import cl.desafio.loginey.repository.UsuarioRepository;
import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.request.UsuarioActualizarRequest;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceMessage;
import cl.desafio.loginey.response.ResponseServiceMessageType;
import cl.desafio.loginey.response.ResponseServiceObject;
import cl.desafio.loginey.jwt.JwtService;
import cl.desafio.loginey.service.UsuarioService;
import cl.desafio.loginey.util.ToolsUtil;
import cl.desafio.loginey.vo.TelefonoVO;
import cl.desafio.loginey.vo.TokenVO;
import cl.desafio.loginey.vo.UsuarioVO;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("usuarioService")

public class UsuarioServiceImpl implements UsuarioService{

  private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

  @Autowired
  private ResponseServiceObject responseServiceObject;

  @Autowired
  private ResponseServiceMessage responseServiceMessage;

  @Autowired
  private UsuarioRepository usuarioRepository;

  private PasswordEncoder passwordEncoder;

  private AuthenticationManager authenticationManager;

  @Autowired
  JwtService jwtService;

  @Override
  public ResponseServiceObject getAuth(CredencialesRequest credencialesRequest) {
    try {

      if(!isValid(credencialesRequest)){
        responseServiceObject.setData(null);
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("400");
        responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
        responseServiceMessage.setMensaje("No se permiten valores nulos o vacios ");
        responseServiceObject.setMensaje(responseServiceMessage);
        return responseServiceObject;
      }

      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              credencialesRequest.getEmail(),
              credencialesRequest.getPassword()
          )
      );

      var user = usuarioRepository.findByEmail(credencialesRequest.getEmail());

      responseServiceMessage.setTimestamp(new Date());
      responseServiceMessage.setCode("200");
      responseServiceMessage.setType(ResponseServiceMessageType.OK);
      responseServiceMessage.setMensaje("Servicio Finalizado Correctamente");
      responseServiceObject.setData(TokenVO.builder()
          .token(user.get().getToken())
          .build()
      );

    } catch (AuthenticationException e) {
      responseServiceObject.setData(null);
      responseServiceMessage.setTimestamp(new Date());
      responseServiceMessage.setCode("500");
      responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
      responseServiceMessage.setMensaje("Error de autenticacion: " + e.getMessage());
      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    }

    responseServiceObject.setMensaje(responseServiceMessage);
    return responseServiceObject;
  }

  @Override
  public ResponseServiceObject registrarUsuario(UsuarioRequest usuarioRequest) {


    String uuid = ToolsUtil.getUUID();

    Usuario usuario = new Usuario();
    List<Telefono> telefonos = new ArrayList<>();
    List<TelefonoVO> telefonosVO = usuarioRequest.getPhones();
    logger.info("telefonosVO"+telefonosVO);
    usuario.setUuid(uuid);
    try {

      if(!isValid(usuarioRequest)){
        responseServiceObject.setData(null);
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("400");
        responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
        responseServiceMessage.setMensaje("No se permiten valores nulos o vacios ");
        responseServiceObject.setMensaje(responseServiceMessage);
        return responseServiceObject;
      }

      Optional<Usuario> emailExiste = usuarioRepository.findByEmail(usuarioRequest.getEmail());
      logger.info("emailExiste"+emailExiste.isPresent());

      if(!emailExiste.isPresent()){

        if(!ToolsUtil.validarFormatoCorreo(usuarioRequest.getEmail())){
          responseServiceObject.setData(null);
          responseServiceMessage.setTimestamp(new Date());
          responseServiceMessage.setCode("400");
          responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
          responseServiceMessage.setMensaje("Fallo El formato de Correo ");
          responseServiceObject.setMensaje(responseServiceMessage);
          return responseServiceObject;
        }
        if(!ToolsUtil.validarPassword(usuarioRequest.getPassword())){
          responseServiceObject.setData(null);
          responseServiceMessage.setTimestamp(new Date());
          responseServiceMessage.setCode("400");
          responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
          responseServiceMessage.setMensaje("Fallo El formato de password ");
          responseServiceObject.setMensaje(responseServiceMessage);
          return responseServiceObject;
        }

          usuario.setUuid(uuid);
          usuario.setName(usuarioRequest.getName());
          usuario.setEmail(usuarioRequest.getEmail());
          usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
          usuario.setCreated(new Date());
          usuario.setModified(new Date());
          usuario.setLastLogin(new Date());
          usuario.setIsActive(true);
          usuario.setRole(Role.USER);

          for (TelefonoVO tVO : telefonosVO) {
            Telefono telNuevo = new Telefono();
            telNuevo.setNumber(tVO.getNumber());
            telNuevo.setCitycode(tVO.getCitycode());
            telNuevo.setContrycode(tVO.getContrycode());
            telNuevo.setUsuario(usuario);
            telefonos.add(telNuevo);
          }
          usuario.setTelefonos(telefonos);
          usuario.setToken(jwtService.getToken(usuario));

          usuarioRepository.save(usuario);

          UsuarioVO usuarioVO = new UsuarioVO();

          usuarioVO.setToken(usuario.getToken());
          usuarioVO.setUuid(usuario.getUuid());
          usuarioVO.setCreated(usuario.getCreated());
          usuarioVO.setModified(usuario.getModified());
          usuarioVO.setLastLogin(usuario.getLastLogin());
          usuarioVO.setIsActive(usuario.getIsActive());


          responseServiceObject.setData(usuarioVO);
          responseServiceMessage.setTimestamp(new Date());
          responseServiceMessage.setCode("200");
          responseServiceMessage.setType(ResponseServiceMessageType.OK);
          responseServiceMessage.setMensaje("Servicio Finalizado Correctamente");

      }else{

        responseServiceObject.setData(null);
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("409 ");
        responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
        responseServiceMessage.setMensaje("El correo ya registrado");
      }

      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    } catch (Exception e) {

      responseServiceMessage.setTimestamp(new Date());
      responseServiceMessage.setCode("500");
      responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
      responseServiceMessage.setMensaje("Fallo El Servicio de Modificacion: " + e.getMessage());
      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    }
  }

  @Override
  public ResponseServiceObject eliminarUsuario(String userUUID) {
    try {

      Optional<Usuario> usuario = usuarioRepository.findByUuid(userUUID);
      logger.info("usuario: "+usuario);
      if(usuario.isPresent()){

        if(userUUID == null){
          responseServiceObject.setData(null);
          responseServiceMessage.setTimestamp(new Date());
          responseServiceMessage.setCode("400");
          responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
          responseServiceMessage.setMensaje("Fallo El formato de Correo ");
          responseServiceObject.setMensaje(responseServiceMessage);
          return responseServiceObject;
        }


        usuarioRepository.delete(usuario.get());

        responseServiceObject.setData("El usuario fue eliminado Correctamente");
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("200");
        responseServiceMessage.setType(ResponseServiceMessageType.OK);
        responseServiceMessage.setMensaje("Servicio Finalizado Correctamente");

      }else{

        responseServiceObject.setData(null);
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("409 ");
        responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
        responseServiceMessage.setMensaje("El usuario no existe");
      }

      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    } catch (Exception e) {

      responseServiceMessage.setTimestamp(new Date());
      responseServiceMessage.setCode("500");
      responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
      responseServiceMessage.setMensaje("Fallo El Servicio de Modificacion: " + e.getMessage());
      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    }
  }

  @Override
  public ResponseServiceObject actualizarUsuario(UsuarioActualizarRequest usuarioActualizarRequest) {
    try {

      Optional<Usuario> usuario = usuarioRepository.findByUuid(usuarioActualizarRequest.getUuid());
      logger.info("usuario: "+usuario);
      if(usuario.isPresent()){

        if(!isValid(usuarioActualizarRequest)){
          responseServiceObject.setData(null);
          responseServiceMessage.setTimestamp(new Date());
          responseServiceMessage.setCode("400");
          responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
          responseServiceMessage.setMensaje("No se permiten valores nulos o vacios ");
          responseServiceObject.setMensaje(responseServiceMessage);
          return responseServiceObject;
        }
        Usuario usAnt = usuario.get();
        usAnt.setName(usuarioActualizarRequest.getName());
        usAnt.setEmail(usuarioActualizarRequest.getName());
        usAnt.setPassword(usuarioActualizarRequest.getName());
        List<Telefono> antiguosTelefonos = usAnt.getTelefonos();
        List<Telefono> nuevosTelefonos = usuarioActualizarRequest.getPhones().stream()
            .map(telVO -> ToolsUtil.convertirATelefono(telVO,usAnt))
            .collect(Collectors.toList());

        antiguosTelefonos.addAll(nuevosTelefonos);
        logger.info("antiguosTelefonos: "+antiguosTelefonos);

        usAnt.setTelefonos(antiguosTelefonos);
        logger.info(" usAnt.gtTelefonos: "+ usAnt.getTelefonos());

        usuarioRepository.save(usAnt);

        responseServiceObject.setData("El usuario fue actualizado Correctamente");
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("200");
        responseServiceMessage.setType(ResponseServiceMessageType.OK);
        responseServiceMessage.setMensaje("Servicio Finalizado Correctamente");

      }else{

        responseServiceObject.setData(null);
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("409 ");
        responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
        responseServiceMessage.setMensaje("El usuario no existe");
      }

      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    } catch (Exception e) {

      responseServiceMessage.setTimestamp(new Date());
      responseServiceMessage.setCode("500");
      responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
      responseServiceMessage.setMensaje("Fallo El Servicio de Modificacion: " + e.getMessage());
      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    }
  }

  @Override
  public ResponseServiceObject obtenerUsuarios() {
    try {

      List<Usuario> usuarios = usuarioRepository.findAll();
      if(!usuarios.isEmpty()){

        responseServiceObject.setData(usuarioRepository.findAll());
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("200");
        responseServiceMessage.setType(ResponseServiceMessageType.OK);
        responseServiceMessage.setMensaje("Servicio Finalizado Correctamente");

      }else{

        responseServiceObject.setData(null);
        responseServiceMessage.setTimestamp(new Date());
        responseServiceMessage.setCode("409 ");
        responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
        responseServiceMessage.setMensaje("No hay usuarios");
      }

      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    } catch (Exception e) {

      responseServiceMessage.setTimestamp(new Date());
      responseServiceMessage.setCode("500");
      responseServiceMessage.setType(ResponseServiceMessageType.ERROR);
      responseServiceMessage.setMensaje("Fallo El Servicio de obtencion: " + e.getMessage());
      responseServiceObject.setMensaje(responseServiceMessage);
      return responseServiceObject;
    }
  }

  private boolean isValid(UsuarioRequest usuarioRequest) {

    if (usuarioRequest.getName() == null || usuarioRequest.getName().trim().isEmpty()) {
      return false;
    }

    if (usuarioRequest.getEmail() == null || usuarioRequest.getEmail().trim().isEmpty()) {
      return false;
    }

    if (usuarioRequest.getPassword() == null || usuarioRequest.getPassword().trim().isEmpty()) {
      return false;
    }

    for (TelefonoVO phone : usuarioRequest.getPhones()) {
      if (phone == null) {
        return false;
      }
      if (phone.getNumber() == null || phone.getNumber().trim().isEmpty()) {
        return false;
      }

      if (phone.getCitycode() == null || phone.getCitycode().trim().isEmpty()) {
        return false;
      }

      if (phone.getContrycode() == null || phone.getContrycode().trim().isEmpty()) {
        return false;
      }
    }
    return true;
  }

  private boolean isValid(CredencialesRequest credencialesRequest) {

    if (credencialesRequest.getEmail() == null || credencialesRequest.getEmail().trim().isEmpty()) {
      return false;
    }

    if (credencialesRequest.getPassword() == null || credencialesRequest.getPassword().trim().isEmpty()) {
      return false;
    }

    return true;
  }

  private boolean isValid(UsuarioActualizarRequest usuarioActualizarRequest) {

    if (usuarioActualizarRequest.getUuid() == null || usuarioActualizarRequest.getUuid().trim().isEmpty()) {
      return false;
    }
    if (usuarioActualizarRequest.getName() == null || usuarioActualizarRequest.getName().trim().isEmpty()) {
      return false;
    }

    if (usuarioActualizarRequest.getEmail() == null || usuarioActualizarRequest.getEmail().trim().isEmpty()) {
      return false;
    }

    if (usuarioActualizarRequest.getPassword() == null || usuarioActualizarRequest.getPassword().trim().isEmpty()) {
      return false;
    }

    for (TelefonoVO phone : usuarioActualizarRequest.getPhones()) {
      if (phone == null) {
        return false;
      }
      if (phone.getNumber() == null || phone.getNumber().trim().isEmpty()) {
        return false;
      }

      if (phone.getCitycode() == null || phone.getCitycode().trim().isEmpty()) {
        return false;
      }

      if (phone.getContrycode() == null || phone.getContrycode().trim().isEmpty()) {
        return false;
      }
    }
    return true;
  }

}
