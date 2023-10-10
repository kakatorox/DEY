package cl.desafio.loginey.util;

import cl.desafio.loginey.entity.Telefono;
import cl.desafio.loginey.entity.Usuario;
import cl.desafio.loginey.vo.TelefonoVO;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ToolsUtil {
  public static boolean validarPassword(String input) {
    String patron = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d{2}).*$";

    Pattern pattern = Pattern.compile(patron);
    Matcher matcher = pattern.matcher(input);

    return matcher.matches();
  }

  public static boolean validarFormatoCorreo(String input) {
    String patron = "^[a-z]{7}@[a-z]+\\.[a-z]+$";

    Pattern pattern = Pattern.compile(patron);
    Matcher matcher = pattern.matcher(input);

    return matcher.matches();
  }


  public static String getUUID(){
    return UUID.randomUUID().toString();
  }

  public static Telefono convertirATelefono(TelefonoVO telVO, Usuario usuario) {
    Telefono telefono = new Telefono();
    telefono.setNumber(telVO.getNumber());
    telefono.setCitycode(telVO.getCitycode());
    telefono.setContrycode(telVO.getContrycode());
    telefono.setUsuario(usuario);
    return telefono;
  }

}
