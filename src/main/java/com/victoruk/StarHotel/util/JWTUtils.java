package com.victoruk.StarHotel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {

    private static final long EXPIRATION_TIME = 1000 * 60* 60 * 24 * 7; // alternatively use duration or //timeunit

//    private static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(24);

    private final SecretKey key;

    public JWTUtils() {
      String secreteString = "3476671672387536438742698r7r4389895735987783578f54898990d949094985fo3o3oii9o3m6989490209m95905598y90690548934i509606k7r5r8rr0wr7ss43903iw8if0kd9h7642k2ud94";
      byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
      this.key = new SecretKeySpec(keyBytes, "HmacSHA256");

    }

    public String generateToken(UserDetails userDetails){

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();

    }

    public String extractUserName(String token){

        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isAvailable(String token, UserDetails userDetails){

        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, userDetails));
    }

    public  boolean isTokenExpired(String token, UserDetails userDetails){

        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
























