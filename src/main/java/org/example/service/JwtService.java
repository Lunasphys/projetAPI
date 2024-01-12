package org.example.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.service.UserService;

import java.util.Date;

import static java.rmi.server.LogStream.log;

@Service
public class JwtService {
    @Autowired
    private UserService userService;

    byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + 86400000); // 1 jour

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("isAdmin", user.isAdmin())
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public User validateToken(String token) {
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            String username = jwsClaims.getBody().getSubject();
            return userService.getUserByUsername(username);
        } catch (JwtException | IllegalArgumentException e) {
            log("JWT token invalid " + e);
            return null;
        }
    }
}
