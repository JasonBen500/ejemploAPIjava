package com.punto.venta.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Integer idPedidoDetalle;
    private Integer idPedido;
    private Integer idProducto;
    private Boolean estado;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
