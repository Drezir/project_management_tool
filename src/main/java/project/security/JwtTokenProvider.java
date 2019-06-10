package project.security;

import static project.security.SecurityConstants.SECRET;
import static project.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import project.domain.User;

@Component
@Slf4j
public class JwtTokenProvider {

  public String generateToken(Authentication authentication) {
    User user = (User)authentication.getPrincipal();
    Date now = new Date(System.currentTimeMillis());
    Date expire = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

    String userId = user.getId().toString();

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userId);
    claims.put("username", user.getUsername());
    claims.put("fullName", user.getFullName());

    return Jwts.builder()
        .setSubject(userId)
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expire)
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature", ex);
    } catch (MalformedJwtException ex) {
      log.error("Malformed JWT token", ex);
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token", ex);
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token", ex);
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty", ex);
    }
    return false;
  }

  public Long extractUserId(String token) {
    Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    String id = (String)claims.get("id");
    return Long.parseLong(id);
  }
}
