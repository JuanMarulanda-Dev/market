package com.platzi.platzimarker.web.security.filter;

import com.platzi.platzimarker.domain.service.PlatziUserDetailsService;
import com.platzi.platzimarker.web.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Esto hace de middleware
// OncePerRequestFilter hara que el filtro se ejecute cada vez que exista una peticion
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            // "SecurityContextHolder.getContext().getAuthentication() == null" esto se utiliza para verificar que todavia no exista una autenticacion para este usuario
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = platziUserDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwt, userDetails)){ // el jwt es correcto?
                    // si es correcto se va a crear una session para el usuario
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // acá se añaden los detalles de la conexion/peticion que se realizo, como por ejemplo: desde que navegador, que maquina, que sistema operativo, etc
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // esto se hace para crear una session del usuario y el usuario no tenga que pasar por estas validaciones siempre
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
