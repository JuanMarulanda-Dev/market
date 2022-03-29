package com.platzi.platzimarker.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.signing.key}")
    private String KEY;

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // se añade el usuario al token
                .setIssuedAt(new Date()) // Fecha de cuando fue creado el token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // cuando expira el token
                .signWith(SignatureAlgorithm.HS256, KEY) // Algoritmo como va a encriptar esto (se necesita una llave)
                .compact(); //
    }

    public boolean validateToken(String token, UserDetails userDetails){
        //validamos que el usuario que llega en la peticion es igual al que esta en el token
        //validamos que no haya expirado el token
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token){
        // en el subject es donde esta el usuario de la peticion
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token){
        // verifica que la fecha de expiracion del token este antes que la fecha actual
        return getClaims(token).getExpiration().before(new Date());
    }

    // Los claims son como los objetos dentro de json web toke JWT
    private Claims getClaims(String token){
        // se encarga de obtener los objetos del token separados por claims (objetos de jwt)
        // si no encuentra la firma valida respondera un 403 forbbiden
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }

}
