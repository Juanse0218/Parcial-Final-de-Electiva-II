package com.jwtprueba.pruebajwt.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtprueba.pruebajwt.Model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario,Long>{

     List<Comentario> findByFechaGreaterThanEqual(String fecha);
}
