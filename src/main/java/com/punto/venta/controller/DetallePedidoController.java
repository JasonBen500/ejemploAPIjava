package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.DetallePedidoDTO;
import com.punto.venta.repository.DetallePedidoRepository;
import com.punto.venta.service.DetallePedidoService;

@RestController
@RequestMapping("/detallespedidos")
@CrossOrigin(origins = "*")
public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;
    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoController(DetallePedidoService detallePedidoService, DetallePedidoRepository detallePedidoRepository){
        this.detallePedidoRepository = detallePedidoRepository;
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedidoDTO>listarTodos(){
        return detallePedidoService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetallePedidoDTO createDetallePedido(DetallePedidoDTO detallePedidoDTO) {
    return detallePedidoService.save(detallePedidoDTO);
}
}
