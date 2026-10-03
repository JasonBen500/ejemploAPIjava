package com.punto.venta.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteDTO> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/mostrarActivos")
    public List<ClienteDTO> mostrarActivos() {
        return clienteService.mostrarActivos();
    }

    @GetMapping("/mostrarNombresActivosFiltro")
    public List<ClienteDTO> mostrarNombreActivo(@RequestParam String nombre) {
        return clienteService.mostrarActivosFiltroNombre(nombre);
    }

    @GetMapping("/mostrarApellidoActivoFiltro")
    public List<ClienteDTO> mostrarApellidoActivo(@RequestParam String apellido) {
        return clienteService.mostrarActivosFiltroApellido(apellido);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> crearCliente(@Valid @RequestBody ClienteDTO dto) {
        try {
            clienteService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("Cliente creado con exito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<MessageResponse> actualizarCliente(@PathVariable Integer idCliente,
            @Valid @RequestBody ClienteDTO dto) {
        try {
            clienteService.actualizar(idCliente, dto);
            return ResponseEntity.ok(new MessageResponse("Cliente actualizado con exito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/anular/{idCliente}")
    public ResponseEntity<MessageResponse> anularCliente(@PathVariable Integer idCliente) {
        try {
            clienteService.anular(idCliente);
            return ResponseEntity.ok(new MessageResponse("Cliente anulado con exito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}
