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

import com.punto.venta.dto.DetallePedidoDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.repository.DetallePedidoRepository;
import com.punto.venta.service.DetallePedidoService;

@RestController
@RequestMapping("/detallespedidos")
@CrossOrigin(origins = "*")
public class DetallePedidoController {
    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoRepository detallePedidoRepository,
            DetallePedidoService detallePedidoService) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedidoDTO> listarTodos() {
        return detallePedidoService.listarTodos();
    }

    @GetMapping("/DetallePedidoactivoOrden")
    public List<DetallePedidoDTO> mostrarActivosOrdenados() {
    return detallePedidoService.mostrarActivosOrden();
}

    @GetMapping("/DetallePedidosactivos")
    public List<DetallePedidoDTO> mostrarActivos() {
    return detallePedidoService.mostrarActivos();
}

    @PostMapping
    public ResponseEntity<MessageResponse> crearDetalle(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        try {
            detallePedidoService.crear(detallePedidoDTO);
            return ResponseEntity.ok(new MessageResponse("Detalle de pedido creado con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el detalle de pedido " + e.getMessage()));
        }
    }
}
