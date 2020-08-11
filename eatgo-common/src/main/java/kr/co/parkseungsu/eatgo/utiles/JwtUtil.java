package kr.co.parkseungsu.eatgo.utiles;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private String secret;
    private Key key;

    public JwtUtil(String secret) {
        this.key= Keys.hmacShaKeyFor(secret.getBytes());
        this.secret=secret;
    }

    public String createToken(long userId, String name, Long restaurantId) {
        JwtBuilder builder=Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);
        if(restaurantId!=null){
            builder=builder.claim("restaurantId",restaurantId);
        }
        return builder
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

    }
}
