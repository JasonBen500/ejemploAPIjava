package com.punto.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.service.CategoriaService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/categorias")
@CrossOrigin (origins = "http://localhost:5174") 
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.findAll();
    }

    @GetMapping("/mostrarActivos")
    public List<CategoriaDTO> mostrarActivos() {
        return categoriaService.mostrarActivos();
    }

    @GetMapping("/mostrarActivosFiltro")
    public List<CategoriaDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return categoriaService.mostrarActivosFIltro(nombre);
    }

    @GetMapping("/mostrarActivosFiltroTop")
    public List<CategoriaDTO> mostrarActivosFiltroTop2(@RequestParam String nombre) {
        return categoriaService.mostrarActivosFIltroTop2(nombre);
    }


@PostMapping
@ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<MessageResponse> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            categoriaService.crearCategoria(categoriaDTO);
            return ResponseEntity.ok(new MessageResponse("Categoría creada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: La categoría ya existe"));
        }
    }
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteCategoria(@PathVariable Integer id){
    categoriaService.eliminarCategoria(id);
}
@PutMapping("anular/{id}")
public CategoriaDTO anulCategoria(@PathVariable Integer id){
    return categoriaService.anularCategoria(id);
}

}
