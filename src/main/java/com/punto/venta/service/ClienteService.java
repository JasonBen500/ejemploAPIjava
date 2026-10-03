package com.punto.venta.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ClienteDTO> mostrarActivos() {
        return clienteRepository.findByEstadoTrueOrderByIdClienteDesc()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ClienteDTO> mostrarActivosFiltroNombre(String nombre) {
        return clienteRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ClienteDTO> mostrarActivosFiltroApellido(String apellido) {
        return clienteRepository.findByEstadoTrueAndApellidoContainingIgnoreCase(apellido)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ClienteDTO crear(ClienteDTO dto) {
        boolean duplicado = clienteRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(
                dto.getNombre(), dto.getApellido());
        if (duplicado) {
            throw new RuntimeException("El cliente ya existe");
        }
        return convertToDTO(clienteRepository.save(convertToEntity(dto)));
    }

    public ClienteDTO actualizar(Integer idCliente, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("El cliente no existe con id " + idCliente));
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return convertToDTO(clienteRepository.save(cliente));
    }

    public ClienteDTO anular(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("El cliente no existe con id " + idCliente));
        cliente.setEstado(false);
        return convertToDTO(clienteRepository.save(cliente));
    }

    private ClienteDTO convertToDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(c.getIdCliente());
        dto.setEstado(c.getEstado());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setEmail(c.getEmail());
        dto.setTelefono(c.getTelefono());
        dto.setFecharegistro(c.getFechaRegistro());
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setFechaRegistro(new Date());
        cliente.setEstado(true);
        return cliente;
    }
}