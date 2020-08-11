package kr.co.parkseungsu.eatgo.utiles;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private final String secret;
    private final Key key;

    public JwtUtil(String secret) {
        this.key= Keys.hmacShaKeyFor(secret.getBytes());
        this.secret=secret;
    }

    public String createToken(long userId, String name) {


        return Jwts.builder()
                .claim("userId",userId)
                .claim("name",name)
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
