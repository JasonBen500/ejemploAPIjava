package com.punto.venta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
        boolean existsByIdClienteAndEstadoPedidoFalse(Cliente idCliente);

        List<Pedido> findByEstadoTrueOrderByIdPedidoDesc();

        List<Pedido> findByEstadoTrue();
        
        List<Pedido> findByEstadoPedidoTrue();
}
