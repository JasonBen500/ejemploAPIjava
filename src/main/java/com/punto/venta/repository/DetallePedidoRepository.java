package com.punto.venta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto.venta.entity.DetallePedido;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.Producto;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
        boolean existsByIdPedidoAndIdProducto(Pedido idPedido, Producto idProducto);

        List<DetallePedido> findByEstadoTrueOrderByIdPedidoDetalleDesc();
        
        List<DetallePedido> findByEstadoTrue(); 
}
