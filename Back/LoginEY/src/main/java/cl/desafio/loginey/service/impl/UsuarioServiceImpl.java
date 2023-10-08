package cl.desafio.loginey.service.impl;

import cl.desafio.loginey.entity.Role;
import cl.desafio.loginey.entity.Telefono;
import cl.desafio.loginey.entity.Usuario;
import cl.desafio.loginey.repository.TelefonoRepository;
import cl.desafio.loginey.repository.UsuarioRepository;
import cl.desafio.loginey.request.CredencialesRequest;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceMessage;
import cl.desafio.loginey.response.ResponseServiceMessageType;
import cl.desafio.loginey.response.ResponseServiceObject;
import cl.desafio.loginey.jwt.JwtService;
import cl.desafio.loginey.service.UsuarioService;
import cl.desafio.loginey.util.ToolsUtil;
import cl.desafio.loginey.vo.CredencialVO;
import cl.desafio.loginey.vo.TelefonoVO;
import cl.desafio.loginey.vo.TokenVO;
import cl.desafio.loginey.vo.UsuarioVO;
import jdk.nashorn.internal.parser.Token;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("usuarioService")
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

  private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

  @Autowired
  private ResponseServiceObject responseServiceObject;

  @Autowired
  private ResponseServiceMessage responseServiceMessage;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TelefonoRepository telefonoRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  @Autowired
  JwtService jwtService;

  @Override
  public ResponseServiceObject getAuth(CredencialesRequest credencialesRequest) {

    logger.info(String.valueOf(credencialesRequest));
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            credencialesRequest.getEmail(),
            credencialesRequest.getPassword()
        )
    );

    var user = usuarioRepository.findByEmail(credencialesRequest.getEmail());

    var token = jwtService.getToken(user.get());

    responseServiceMessage.setTimestamp(new Date());
    responseServiceMessage.setCode("200");
    responseServiceMessage.setType(ResponseServiceMessageType.OK);
    responseServiceMessage.setMensaje("Servicio Finalizado Correctamente");

    responseServiceObject.setData(TokenVO.builder()
        .token(token)
        .build()
    );
    responseServiceObject.setMensaje(responseServiceMessage);

    return responseServiceObject;
  }

  @Override
  public ResponseServiceObject registrarUsuario(UsuarioRequest usuarioRequest) {


    String uuid = ToolsUtil.getUUID();

    Usuario usuario = new Usuario();
    List<Telefono> telefonos = new ArrayList<Telefono>();
    List<TelefonoVO> telefonosVO = usuarioRequest.getPhones();
    logger.info("telefonosVO"+telefonosVO);
    usuario.setUuid(uuid);
    try {


      Optional<Usuario> emailExiste = usuarioRepository.findByEmail(usuarioRequest.getEmail());
      logger.info("emailExiste"+emailExiste.isPresent());

      if(!emailExiste.isPresent()){
        usuario.setUuid(uuid);
        usuario.setName(usuarioRequest.getName());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        usuario.setCreated(new Date());
        usuario.setModified(new Date());
        usuario.setLastLogin(new Date());
        usuario.setIsActive(true);
        usuario.setRole(Role.USER);

        for (TelefonoVO tVO:telefonosVO) {
          Telefono telNuevo = new Telefono();
          telNuevo.setNumber(tVO.getNumber());
          telNuevo.setCitycode(tVO.getCitycode());
          telNuevo.setContrycode(tVO.getContrycode());
          telNuevo.setUsuario(usuario);
          telefonos.add(telNuevo);
        }
        usuario.setTelefonos(telefonos);


        usuarioRepository.save(usuario);

        usuarioRequest.getName();

        UsuarioVO usuarioVO= new UsuarioVO();

        usuarioVO.setToken(jwtService.getToken(usuario));
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

}
