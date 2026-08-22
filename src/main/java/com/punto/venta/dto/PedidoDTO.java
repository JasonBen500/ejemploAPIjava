package com.punto.venta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PedidoDTO {
private Integer idPedido;
private Integer idCliente;
private Boolean estado;
private LocalDateTime fechaPedido;
private Boolean estadoPedido;
private BigDecimal total;
}
