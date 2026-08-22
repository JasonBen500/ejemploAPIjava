package com.punto.venta.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoDTO {
private Integer idProducto;
private Integer idCategoria;
private Boolean estado;
private String nombre;
private String descripcion;
private BigDecimal precio;
private Integer stock;
}
