package cl.bci.techtest.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;


public class JwtUtil {

    private static final String SECRET_KEY = "j12wI0J#sU1@$3lPcO2&MbVfz*4eK!aN";

    public static String generateToken(String subject) {
        Date expirationDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
