package com.platzi.platzimarker.web.security;

import com.platzi.platzimarker.domain.service.PlatziUserDetailsService;
import com.platzi.platzimarker.web.security.filter.JwtFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;


    @Override // De esta forma le decimos a spring security que use nuestro usuario para el loquin y no el por defecto
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(platziUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Habilitamos el acceso a las rutas de swagger
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Estamos indicando que desactive el CSRF (peticiones cruzadas) y que autorice unicamente a la ruta "authenticate" tenga permisos de ingresas
        http.csrf().disable().authorizeHttpRequests().antMatchers("/**/authenticate").permitAll()
                .anyRequest().authenticated() // Las peticiones que no cumplan esa condiciones si necesitan estar authenticadas
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // le estamos indicando que la aplicacion se va a manejar sin variables de session porque los jwt son los que van a controlar las peticiones

        //le decimos que va a filtrar todas las peticiones antes con el friltro que se creo para el manejo de jwt
        // y adicional le decimos que tipo de filtro el (es un filtro de usuario y contraseña)
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);

        // super.configure(http);
        // O esta otra forma tambien se puede utilziar para hacer lo mismo que el metodo anterior
        //http.authorizeRequests().antMatchers("/swagger*").permitAll();
    }

    @Override
    @Bean // esto se hace para decirle a spring explicitamente que este es el gestor de autenticacion que estamos utilizando
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
