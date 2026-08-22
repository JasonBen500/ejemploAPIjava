package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.DetallePedidoDTO;
import com.punto.venta.entity.DetallePedido;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {
 private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository){
        this.detallePedidoRepository = detallePedidoRepository;
    }

    private DetallePedido convertToEntity(DetallePedidoDTO dto) {
    DetallePedido detallePedido = new DetallePedido();

    Pedido pedido = new Pedido();
    pedido.setIdPedido(dto.getIdPedido());
    detallePedido.setIdPedido(pedido);

    Producto producto = new Producto();
    producto.setIdProducto(dto.getIdProducto());
    detallePedido.setIdProducto(producto);

    detallePedido.setEstado(dto.getEstado());
    detallePedido.setCantidad(dto.getCantidad());
    detallePedido.setPrecioUnitario(dto.getPrecioUnitario());
    detallePedido.setSubtotal(dto.getSubtotal());
    return detallePedido;
}

private DetallePedidoDTO convertToDTO(DetallePedido d) {
    DetallePedidoDTO dto = new DetallePedidoDTO();
    dto.setIdPedidoDetalle(d.getIdPedidoDetalle());
    dto.setIdPedido(d.getIdPedido().getIdPedido());
    dto.setIdProducto(d.getIdProducto().getIdProducto());
    dto.setEstado(d.getEstado());
    dto.setCantidad(d.getCantidad());
    dto.setPrecioUnitario(d.getPrecioUnitario());
    dto.setSubtotal(d.getSubtotal());
    return dto;
}

    public List<DetallePedidoDTO> listarTodos(){
        return detallePedidoRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public DetallePedidoDTO save(DetallePedidoDTO dto){
        DetallePedido detallePedido = convertToEntity(dto);
        DetallePedido guardada = detallePedidoRepository.save(detallePedido);
        return convertToDTO(guardada);
    }
}
