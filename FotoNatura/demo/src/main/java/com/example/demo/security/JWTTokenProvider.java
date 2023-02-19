package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger; // Основная точка входа пользователя в SLF4J API
import org.slf4j.LoggerFactory; // служебный класс, создающий Loggers для различных API-интерфейсов ведения журналов, в
// первую очередь для log4j, logback и ведения журнала JDK 1.4
import org.springframework.security.core.Authentication; // Интерфейс основной стратегии для аутентификации
import org.springframework.stereotype.Component; // Аннотация, которая позволяет Spring автоматически определять ваши

import java.util.Date; // Класс предназначен для работы с текущими датой и временем и позволяет отталкиваться от них для
// решения своих задач
import java.util.HashMap;
import java.util.Map;



@Component// пользовательские компоненты.
public class JWTTokenProvider {
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);// getLogger() возвращает
// регистратор с именем, соответствующим классу, переданному в качестве параметра, используя статически связанный
// экземпляр ILoggerFactory

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());// System.currentTimeMillis() - текущее времяв миллисекундах
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);// now.getTime() - текущая дата и
        // время

        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getEmail());
        claimsMap.put("firstname", user.getName());
        claimsMap.put("lastname", user.getLastname());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException ex) {
            LOG.error(ex.getMessage());// запись ошибки в лог
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
