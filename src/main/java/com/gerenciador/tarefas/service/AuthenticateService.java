package com.gerenciador.tarefas.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class AuthenticateService {

    private final static String BEARER = "Bearer";
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String JWT_KEY = "signinKey";
    private final static String AUTHORITIES = "authorities";
    private final static int EXPIRATION_TOKEN_ONE_HOUR = 3600000;

    static public void addJWTToken(HttpServletResponse response, Authentication authentication) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES, authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));


        String jwtToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_ONE_HOUR))
                .signWith(SignatureAlgorithm.ES512, JWT_KEY)
                .addClaims(claims)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, BEARER + " " + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", HEADER_AUTHORIZATION);
    }

    static public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);

        if (token != null && token.startsWith(BEARER)) {
            Claims user = Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(token.replace(BEARER + " ", ""))
                    .getBody();

            if (user != null) {

                List<SimpleGrantedAuthority> permissions = ((ArrayList<String>)user.get(AUTHORITIES))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();


                return new UsernamePasswordAuthenticationToken(user, null, permissions);
            } else {
                throw new RuntimeException("Autenticação falhou");
            }
        }
        return null;
    }
}
