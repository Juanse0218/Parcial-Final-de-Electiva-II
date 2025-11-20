package com.jwtprueba.pruebajwt.Service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtprueba.pruebajwt.Model.Usuario;
import com.jwtprueba.pruebajwt.Repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl {

     private UsuarioRepository usuarioRepository = null;
     private PasswordEncoder passwordEncoder = null;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void guardarUsuariosIniciales() {
        Usuario u1 = new Usuario();
        u1.setUsername("Juan Pérez");
        u1.setEmail("juan.perez@email.com");
        u1.setPassword(passwordEncoder.encode("Qwerty123"));
        u1.setDireccion("Carrera 45 #10-20");
        u1.setMetododePago("Tarjeta de crédito");
        u1.setRoles(Set.of("USER"));

        Usuario u2 = new Usuario();
        u2.setUsername("Ana Gómez");
        u2.setEmail("ana.gomez@email.com");
        u2.setPassword(passwordEncoder.encode("Pass456"));
        u2.setDireccion("Calle 21 #35-50");
        u2.setMetododePago("PayPal");
        u2.setRoles(Set.of("USER"));

        Usuario u3 = new Usuario();
        u3.setUsername("Carlos Ruiz");
        u3.setEmail("carlos.ruiz@email.com");
        u3.setPassword(passwordEncoder.encode("Segura789"));
        u3.setDireccion("Avenida Principal #100");
        u3.setMetododePago("Transferencia bancaria");
        u3.setRoles(Set.of("USER"));

        Usuario u4 = new Usuario();
        u4.setUsername("Sofía Martínez");
        u4.setEmail("sofia.martinez@email.com");
        u4.setPassword(passwordEncoder.encode("Clave987"));
        u4.setDireccion("Calle 8 #20-30");
        u4.setMetododePago("Efectivo");
        u4.setRoles(Set.of("USER"));

        Usuario u5 = new Usuario();
        u5.setUsername("Diego Fernández");
        u5.setEmail("diego.fernandez@email.com");
        u5.setPassword(passwordEncoder.encode("Contra654"));
        u5.setDireccion("Carrera 77 #40-60");
        u5.setMetododePago("Tarjeta débito");
        u5.setRoles(Set.of("USER"));

        usuarioRepository.saveAll(List.of(u1, u2, u3, u4, u5));
    }

}
