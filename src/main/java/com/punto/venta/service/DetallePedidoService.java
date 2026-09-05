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

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
    this.detallePedidoRepository = detallePedidoRepository;
}

    public List<DetallePedidoDTO> listarTodos() {
        return detallePedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DetallePedidoDTO> mostrarActivosOrden() {
    return detallePedidoRepository.findByEstadoTrueOrderByIdPedidoDetalleDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    public List<DetallePedidoDTO> mostrarActivos() {
    return detallePedidoRepository.findByEstadoTrue()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    public DetallePedidoDTO crear(DetallePedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());
        boolean duplicado = detallePedidoRepository.existsByIdPedidoAndIdProducto(pedido, producto);
        if (duplicado) {
            throw new RuntimeException("El detalle de pedido ya existe");
        }
        return convertToDTO(detallePedidoRepository.save(convertToEntity(dto)));
    }

    private DetallePedidoDTO convertToDTO(DetallePedido c) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdPedidoDetalle(c.getIdPedidoDetalle());
        dto.setIdPedido(c.getIdPedido().getIdPedido());
        dto.setIdProducto(c.getIdProducto().getIdProducto());
        dto.setEstado(c.getEstado());
        dto.setCantidad(c.getCantidad());
        dto.setPrecioUnitario(c.getPrecioUnitario());
        dto.setSubtotal(c.getSubtotal());
        return dto;
    }

    private DetallePedido convertToEntity(DetallePedidoDTO c) {
        DetallePedido detalle = new DetallePedido();
        detalle.setIdPedidoDetalle(c.getIdPedidoDetalle());
        Pedido pedido = new Pedido();
        pedido.setIdPedido(c.getIdPedido());
        detalle.setIdPedido(pedido);
        Producto producto = new Producto();
        producto.setIdProducto(c.getIdProducto());
        detalle.setIdProducto(producto);
        detalle.setEstado(c.getEstado());
        detalle.setCantidad(c.getCantidad());
        detalle.setPrecioUnitario(c.getPrecioUnitario());
        detalle.setSubtotal(c.getSubtotal());
        return detalle;
    }
}
