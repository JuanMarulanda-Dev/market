package com.platzi.platzimarker.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlatziUserDetailsService implements UserDetailsService {

    // {noop} indica que la contraseña no va a ser codeada o hasheada por algo
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("alejandro", "{noop}platzi", new ArrayList<>()); // el ArrayList almacenara los tipos de roles que puede ser el usuario
    }


}
