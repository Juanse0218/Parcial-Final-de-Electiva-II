package com.jwtprueba.pruebajwt.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtprueba.pruebajwt.Model.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>{

    List<Producto> findByStockLessThan(Integer x);

}
