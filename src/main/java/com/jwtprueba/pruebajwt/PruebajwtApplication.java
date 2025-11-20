package com.jwtprueba.pruebajwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jwtprueba.pruebajwt.Service.UsuarioServiceImpl;

@SpringBootApplication
public class PruebajwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebajwtApplication.class, args);
	}
	/*@Bean
    CommandLineRunner run(UsuarioServiceImpl usuarioService) {
        return args -> {
            usuarioService.guardarUsuariosIniciales();
        };
    }*/

}
