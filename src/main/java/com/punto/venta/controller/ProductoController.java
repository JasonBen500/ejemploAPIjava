package com.punto.venta.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.service.ProductoService;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoDTO> listarTodos() {
        return productoService.listarProductos();
    }

    @GetMapping("/activos")
    public List<ProductoDTO> mostrarActivos() {
        return productoService.mostrarActivos();
    }

    @GetMapping("/activosFiltro")
    public List<ProductoDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/activosFiltroTop2")
    public List<ProductoDTO> mostrarActivosFiltroTop2(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltroTop2(nombre);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        try {
            productoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("Producto creado con exito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<MessageResponse> actualizarProducto(@PathVariable Integer idProducto,
            @Valid @RequestBody ProductoDTO dto) {
        try {
            productoService.actualizar(idProducto, dto);
            return ResponseEntity.ok(new MessageResponse("Producto actualizado con exito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/anular/{idProducto}")
    public ResponseEntity<MessageResponse> anularProducto(@PathVariable Integer idProducto) {
        try {
            productoService.anular(idProducto);
            return ResponseEntity.ok(new MessageResponse("Producto anulado con exito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}
