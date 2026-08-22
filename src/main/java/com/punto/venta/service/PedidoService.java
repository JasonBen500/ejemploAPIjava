package com.punto.venta.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    private Pedido convertToEntity(PedidoDTO dto) {
    Pedido pedido = new Pedido();

    Cliente cliente = new Cliente();
    cliente.setIdCliente(dto.getIdCliente());
    pedido.setIdCliente(cliente);

    pedido.setEstado(dto.getEstado());

    if (dto.getFechaPedido() != null) {
        pedido.setFechaPedido(Date.from(dto.getFechaPedido().atZone(ZoneId.systemDefault()).toInstant()));
    }

    pedido.setEstadoPedido(dto.getEstadoPedido());
    pedido.setTotal(dto.getTotal());
    return pedido;
}

private PedidoDTO convertToDTO(Pedido p) {
    PedidoDTO dto = new PedidoDTO();
    dto.setIdPedido(p.getIdPedido());
    dto.setIdCliente(p.getIdCliente().getIdCliente());
    dto.setEstado(p.getEstado());

    if (p.getFechaPedido() != null) {
        dto.setFechaPedido(LocalDateTime.ofInstant(p.getFechaPedido().toInstant(), ZoneId.systemDefault()));
    }

    dto.setEstadoPedido(p.getEstadoPedido());
    dto.setTotal(p.getTotal());
    return dto;
}

    public List<PedidoDTO> listarTodos(){
        return pedidoRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public PedidoDTO save(PedidoDTO dto){
        Pedido pedido = convertToEntity(dto);
        Pedido guardada = pedidoRepository.save(pedido);
        return convertToDTO(guardada);
    }
}
