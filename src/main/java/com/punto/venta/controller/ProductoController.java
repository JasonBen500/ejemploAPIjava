package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.repository.ProductoRepository;
import com.punto.venta.service.ProductoService;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<ProductoDTO> listarTodos() {
        return productoService.listarProductos();
    }

        @PostMapping
    public ResponseEntity<MessageResponse> crearProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            productoService.crear(productoDTO);
            return ResponseEntity.ok(new MessageResponse("Producto Creado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error el producto ya existe"));
        }
    }

}
