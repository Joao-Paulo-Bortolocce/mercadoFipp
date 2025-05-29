package unoeste.fipp.mercadofipp.security;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

public class JWTTokenProvider {

    private static final String SECRET = "MINHACHAVESECRETA_MINHACHAVESECRETA_123456";
    private static final SecretKey CHAVE = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String getToken(String usuario, String level, Long id) {
        return Jwts.builder()
                .setSubject(usuario)
                .setIssuer("localhost:8080")
                .claim("level", level)
                .claim("idUser", id)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(CHAVE, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean verifyToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(CHAVE)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // Inclui SignatureException, ExpiredJwtException, etc.
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    public static Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(CHAVE)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("Erro ao recuperar claims: " + e.getMessage());
            return null;
        }
    }
}
