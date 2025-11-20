package com.jwtprueba.pruebajwt.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwtprueba.pruebajwt.Model.CarritoDeCompras;
import com.jwtprueba.pruebajwt.Model.Comentario;
import com.jwtprueba.pruebajwt.Model.LoginRequest;
import com.jwtprueba.pruebajwt.Model.Producto;
import com.jwtprueba.pruebajwt.Model.Usuario;
import com.jwtprueba.pruebajwt.Repository.CarritoRepository;
import com.jwtprueba.pruebajwt.Repository.ComentarioRepository;
import com.jwtprueba.pruebajwt.Repository.ProductoRepository;
import com.jwtprueba.pruebajwt.Repository.UsuarioRepository;
import com.jwtprueba.pruebajwt.Service.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = repo.findByUsername(request.getUsername()).get();

        String token = jwtUtil.generarToken(usuario);

        return Map.of("token", token);
    }

    @GetMapping("/hola")
    public String hola() {
        return "Hola, estás autenticado!";
    }

    @GetMapping("/productos")
    public ResponseEntity<Object> listarProductos(@RequestParam(required = false) Integer x) {

        List<Producto> productos;

        if (x != null) {
            productos = productoRepository.findByStockLessThan(x);
        } else {
            productos = productoRepository.findAll();
        }

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/comentarios")
    public ResponseEntity<Object> listarcomentarios(@RequestParam(required = false) String fecha) {

        List<Comentario> comentarios;

        if (fecha != null) {
            comentarios = comentarioRepository.findByFechaGreaterThanEqual(fecha);
        } else {
            comentarios = comentarioRepository.findAll();
        }

        return ResponseEntity.ok(comentarios);
    }

    @PostMapping("/crear/{usuarioId}")
    public ResponseEntity<Object> crearCarrito(@PathVariable Long usuarioId) {

        Optional<Usuario> usuario = repo.findById(usuarioId);

        if (!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        CarritoDeCompras carrito = new CarritoDeCompras();
        carrito.setUsuario(usuario.get());
        carrito.setProductos(new ArrayList<>());
        carrito.setSubtotal(0.0);
        carrito.setImpuesto(0.0);

        carritoRepository.save(carrito);

        return ResponseEntity.ok("Carrito creado para el usuario: " + usuarioId);
    }

    @PostMapping("/agregar/{carritoId}/{productoId}")
    public ResponseEntity<Object> agregarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId,
            @RequestParam int cantidad) {

        Optional<CarritoDeCompras> carritoOpt = carritoRepository.findById(carritoId);
        Optional<Producto> productoOpt = productoRepository.findById(productoId);

        if (!carritoOpt.isPresent()) {
            return ResponseEntity.badRequest().body(" Carrito no encontrado");
        }
        if (!productoOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Producto no encontrado");
        }

        CarritoDeCompras carrito = carritoOpt.get();
        Producto producto = productoOpt.get();

        if (producto.getStock() < cantidad) {
            return ResponseEntity.badRequest().body("No hay stock suficiente. Stock actual: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        carrito.getProductos().add(producto);

        double subtotal = carrito.getSubtotal() + (producto.getPrecio() * cantidad);
        double impuesto = subtotal * 0.19;

        carrito.setSubtotal(subtotal);
        carrito.setImpuesto(impuesto);

        carritoRepository.save(carrito);

        return ResponseEntity.ok("Producto agregado al carrito y stock actualizado.");
    }

    @GetMapping("/productos/carrito")
    public ResponseEntity<Object> listarProductosDelCarrito(
            @RequestParam Long carritoId,
            @RequestParam Long usuarioId) {

        Optional<CarritoDeCompras> carritoOpt = carritoRepository.findById(carritoId);

        if (!carritoOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Carrito no encontrado");
        }

        CarritoDeCompras carrito = carritoOpt.get();
        if (!carrito.getUsuario().getId().equals(usuarioId)) {
            return ResponseEntity.status(403).body(
                    "No tienes permiso para ver este carrito (no pertenece al usuario)");
        }

        return ResponseEntity.ok(carrito.getProductos());
    }
}
