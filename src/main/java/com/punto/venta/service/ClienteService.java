package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService (ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
    Cliente cliente = new Cliente();
    cliente.setNombre(dto.getNombre());
    cliente.setApellido(dto.getApellido());
    cliente.setEstado(dto.getEstado());
    cliente.setEmail(dto.getEmail());
    cliente.setTelefono(dto.getTelefono());
    cliente.setFechaRegistro(dto.getFecharegistro());
    return cliente;
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

    public List<ClienteDTO>listarTodos(){
        return clienteRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public ClienteDTO save(ClienteDTO dto){
        Cliente cliente = convertToEntity(dto);
        Cliente guardada = clienteRepository.save(cliente);
        return convertToDTO(guardada);
    }

    public ClienteDTO crear(ClienteDTO dto) {
        boolean duplicado = clienteRepository
                .existsByNombreIgnoreCaseAndApellidoIgnoreCase(dto.getNombre(),
                        dto.getApellido());
        if (duplicado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya existe");
        }
        return convertToDTO(clienteRepository.save(convertToEntity(dto)));
    }
}
