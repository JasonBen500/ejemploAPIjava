package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.PedidoRepository;

@Service
public class PedidoService {
private final PedidoRepository pedidoRepository;

     private final PedidoRepository pedidoRepository1;

    public PedidoService(PedidoRepository pedidoRepository, PedidoRepository pedidoRepository1) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoRepository1 = pedidoRepository1;
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosOrdenados() {
    return pedidoRepository.findByEstadoTrueOrderByIdPedidoDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    public List<PedidoDTO> mostrarActivos() {
    return pedidoRepository.findByEstadoTrue()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    public List<PedidoDTO> mostrarPedidosActivos() {
    return pedidoRepository.findByEstadoPedidoTrue()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    public PedidoDTO crear(PedidoDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());

        boolean tienePedidoAbierto = pedidoRepository.existsByIdClienteAndEstadoPedidoFalse(cliente);
        if (tienePedidoAbierto) {
            throw new RuntimeException("El cliente ya tiene un pedido abierto");
        }

        return convertToDTO(pedidoRepository.save(convertToEntity(dto)));
    }

    private PedidoDTO convertToDTO(Pedido c) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(c.getIdPedido());
        dto.setEstado(c.getEstado());
        dto.setEstadoPedido(c.getEstadoPedido());
        dto.setIdCliente(c.getIdCliente().getIdCliente());
        dto.setFechaPedido(c.getFechaPedido());
        dto.setEstadoPedido(c.getEstadoPedido());
        dto.setTotal(c.getTotal());
        return dto;
    }

    private Pedido convertToEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        pedido.setEstado(dto.getEstado());
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        pedido.setIdCliente(cliente);
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setTotal(dto.getTotal());
        pedido.setEstadoPedido(dto.getEstadoPedido());
        return pedido;
    }
}
