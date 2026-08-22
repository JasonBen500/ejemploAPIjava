package com.punto.venta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.repository.PedidoRepository;
import com.punto.venta.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService pedidoService, PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoDTO>listarTodos(){
        return pedidoService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
    return pedidoService.save(pedidoDTO);
}
}
