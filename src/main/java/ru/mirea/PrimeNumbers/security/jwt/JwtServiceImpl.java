package ru.mirea.PrimeNumbers.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.mirea.PrimeNumbers.dto.UserModel;
import ru.mirea.PrimeNumbers.properties.TokenProperties;

import javax.crypto.SecretKeyFactory;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final TokenProperties tokenProperties;

    @Override
    public String generateToken(UserModel userModel) {
        return Jwts.builder()
                .setSubject(userModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((System.currentTimeMillis() * 100) + tokenProperties.getTtl()))
                .signWith(generateKey())
                .compact();
    }

    @Override
    public UserModel parseToken(String jwt) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build();

       if(!parser.isSigned(jwt)){
           return null;
       }

       Claims claims = parser.parseClaimsJws(jwt).getBody();

       String email = claims.getSubject();

       return UserModel.builder().email(email).build();
    }

    @SneakyThrows
    private Key generateKey(){
        byte[] keyBytes = tokenProperties.getKey().getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
