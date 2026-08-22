package com.punto.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto.venta.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);
}
}
