package com.cat.shopapp.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private Long expiration;//lưu vào biến môi trường
    @Value("${jwt.secretKey}")
    private String secretKey;
    public String generateToken(com.cat.shopapp.models.User user) throws Exception{
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)//làm thế nào để trích xuất các claim
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration * 1000L))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }
        catch (Exception e){
            throw new InvalidParameterException("Cannot create jwt token, error: " + e.getMessage());
        }
    }
    private Key getSignKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Decoders.BASE64.decode("+anJ7aMCub30UJkrS9Ms6x4mBfeZJWuLnu8omwaeMws=")
        return Keys.hmacShaKeyFor(bytes);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }
    private String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    //Kiểm tra token đó hết hạn chưa
    public boolean isTokenExprired(String toke){
        Date expirationDate = this.extractClaim(toke, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}
