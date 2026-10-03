package com.punto.venta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.punto.venta.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);

    List<Cliente> findByEstadoTrueOrderByIdClienteDesc();

    List<Cliente> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByEstadoTrueAndApellidoContainingIgnoreCase(String apellido);

    List<Cliente> findTop2ByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Cliente> findTop2ByEstadoTrueAndApellidoContainingIgnoreCase(String apellido);
}
