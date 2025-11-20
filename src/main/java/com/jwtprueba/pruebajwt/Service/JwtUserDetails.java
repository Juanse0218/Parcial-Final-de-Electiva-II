package com.jwtprueba.pruebajwt.Service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwtprueba.pruebajwt.Model.Usuario;
import com.jwtprueba.pruebajwt.Repository.UsuarioRepository;

@Service
public class JwtUserDetails implements UserDetailsService {

     @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
                u.getUsername(),
                u.getPassword(),
                u.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

}
