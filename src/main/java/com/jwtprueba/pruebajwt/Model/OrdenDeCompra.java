package com.jwtprueba.pruebajwt.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Orden_de_compra")
public class OrdenDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(name = "Orden_productos", joinColumns = @JoinColumn(name = "Orden_id"), inverseJoinColumns = @JoinColumn(name = "producto_id") )
    private List<Producto> productos;
    @Column(nullable = false)
    private Double Subtotal;
    @Column(nullable = false)
    private Double Impuestos;
    @Column(nullable = false)
    private Double Envio;
    @Column(nullable = false)
    private Double total;
    
    public OrdenDeCompra() {
    }

    public OrdenDeCompra(Long id, List<Producto> productos, Double subtotal, Double impuestos, Double envio,
            Double total) {
        this.id = id;
        this.productos = productos;
        Subtotal = subtotal;
        Impuestos = impuestos;
        Envio = envio;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(Double subtotal) {
        Subtotal = subtotal;
    }

    public Double getImpuestos() {
        return Impuestos;
    }

    public void setImpuestos(Double impuestos) {
        Impuestos = impuestos;
    }

    public Double getEnvio() {
        return Envio;
    }

    public void setEnvio(Double envio) {
        Envio = envio;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    
}

