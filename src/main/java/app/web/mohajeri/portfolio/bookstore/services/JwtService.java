package app.web.mohajeri.portfolio.bookstore.services;

import app.web.mohajeri.portfolio.bookstore.DTO.TokenDto;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    //    @Value("{bookstore.app.jwtSecret}")
    private final String jwtSecret = "AVeryGoodSecretToProtectOurAppFromUnauthorizedUsers";

    //    @Value("${bookstore.app.jwtExpirationMs}")
    private int jwtExpirationMs = 10000;

    public TokenDto generateTokenFromUserId(String userId) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            var accessExpirationDate = new Date(ts.getTime() + jwtExpirationMs);
            var refreshExpirationDate = new Date(ts.getTime() + 10 * jwtExpirationMs);

            var accessToken = Jwts.builder()
                    .claim("userId", userId)
                    .claim("type", "accessToken")
                    .setIssuedAt(ts)
                    .setExpiration(accessExpirationDate)
                    .signWith(key)
                    .setIssuedAt(date)
                    .compact();

            var refreshToken = Jwts.builder()
                    .claim("userId", userId)
                    .claim("type", "refreshToken")
                    .setIssuedAt(ts)
                    .setExpiration(refreshExpirationDate)
                    .signWith(key)
                    .setIssuedAt(date)
                    .compact();

            return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        } catch (Exception e) {
            throw new Error("JWT error : " + e);
        }
    }


    public boolean validateJwt(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractToken(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt)
//            var claims = Jwts.claims().get("userId");
            var claims = Jwts.parserBuilder()  // (1)
                    .setSigningKey(key)         // (2)
                    .build()                    // (3)
                    .parseClaimsJws(jwt);
            log.info("=== token claims " + claims);
            return claims.toString();
        } catch (Exception e) {
            throw new Error(e);
        }
    }



}
