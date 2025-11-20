package com.jwtprueba.pruebajwt.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carrito_producto")
public class carritoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private CarritoDeCompras carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @Column(nullable = false)
    private int cantidad;

    
    public carritoProducto() {
    }


    public carritoProducto(Long id, CarritoDeCompras carrito, Producto producto, int cantidad) {
        this.id = id;
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public CarritoDeCompras getCarrito() {
        return carrito;
    }


    public void setCarrito(CarritoDeCompras carrito) {
        this.carrito = carrito;
    }


    public Producto getProducto() {
        return producto;
    }


    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    public int getCantidad() {
        return cantidad;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
}
