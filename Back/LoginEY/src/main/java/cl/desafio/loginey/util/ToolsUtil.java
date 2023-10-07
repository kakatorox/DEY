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
  private final static String secretKey = getUUID();
  private final static long expirationTimeInMillis = 600_000; // 10 minutes

  public static String generateOtp(String subject) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + expirationTimeInMillis);
    return Jwts.builder().setSubject(subject).setIssuedAt(now).setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, secretKey).compact();

  }

  public static boolean validarString(String input) {
    String patron = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).+$";

    Pattern pattern = Pattern.compile(patron);
    Matcher matcher = pattern.matcher(input);

    return matcher.matches();
  }

  public static String getUUID(){
    return UUID.randomUUID().toString();
  }
}
