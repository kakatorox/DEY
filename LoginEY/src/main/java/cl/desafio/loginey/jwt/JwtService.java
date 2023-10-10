package cl.desafio.loginey.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  private static final String SECRET_KEY="yXgWwQgkIy7BIeN2DY5rdefOPFM5KUeI8rk3fl6L57NiD2OSvZS71WAktvoGcAN8";
  public String getToken(UserDetails us) {
    return getToken(new HashMap<>(),us);
  }

  private String getToken(Map<String,Object> extraClaims, UserDetails us) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(us.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getKey() {
    byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String getUserFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

  public boolean isTokenValid(String token, UserDetails uDet) {
    final String username = getUserFromToken(token);
    return (username.equals(uDet.getUsername()) && !isTokenExpired(token));
  }

  private Claims getAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public <T> T getClaim (String token, Function<Claims,T> claimsResolver) {
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  private boolean isTokenExpired(String token) {
    return getExpiration(token).before(new Date());
  }
}
