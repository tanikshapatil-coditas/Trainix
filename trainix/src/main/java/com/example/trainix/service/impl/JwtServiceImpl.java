package com.example.trainix.service.impl;


import com.example.trainix.repository.UserRepository;
import com.example.trainix.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;


@EnableJpaRepositories
@Service
public class JwtServiceImpl implements JwtService {

    private final UserRepository userRepository;


    private  String secretkey = "f9dbbb2f2ceb6b8fdead22fab46f8aab48fb64c8d9e43d8fdcbac08c5b4476d18bb1c8b29c21f093aa8ae21fea070b59010e59d9d3baaf07f0289bc80a0f47f4425d5952adb580747f4bc7f6109a2a3f96ecbdb5ff52ea3a34fe569119861aa591769e4a91910be3ef5d89ac2b954d2007bac5eed5b65ae763499f5caf8fc406d30bfb8e13a5ddb59a122a0adaee5eb54be88fa61906f0778e06eade60593890df4333c1a332f3020c5a1fa7dca7739d8f169f866c59c3e2b720682823e05820d5112df50df2e983f08b997ddbe4e974e58a34f13f27d0196ca5397d31c8d9110cf1d94ebc1b6e87a5c60bec08a9ec92abc862bbea91c9e4756d0985a12f0986";

     private final long accessTokenExpiration = 3153600000000L;



    public JwtServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getKey())
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = this.extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
