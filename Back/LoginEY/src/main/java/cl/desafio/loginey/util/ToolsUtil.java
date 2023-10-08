package cl.desafio.loginey.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ToolsUtil {
  public static boolean validarString(String input) {
    String patron = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).+$";

    Pattern pattern = Pattern.compile(patron);
    Matcher matcher = pattern.matcher(input);

    return matcher.matches();
  }

//  public codificarPassword(String password){
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//  }


  public static String getUUID(){
    return UUID.randomUUID().toString();
  }


}
