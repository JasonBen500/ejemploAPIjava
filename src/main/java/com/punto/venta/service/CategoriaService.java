package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CategoriaDTO> mostrarActivos() {
        return categoriaRepository.findByEstadoTrueOrderByIdCategoriaDesc()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CategoriaDTO> mostrarActivosFiltro(String nombre) {
        return categoriaRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CategoriaDTO> mostrarActivosFiltroTop2(String nombre) {
        return categoriaRepository.findTop2ByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("La categoria ya existe");
        }
        return convertToDTO(categoriaRepository.save(convertToEntity(dto)));
    }

    public CategoriaDTO modificarCategoria(Integer idCategoria, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return convertToDTO(categoriaRepository.save(categoria));
    }

    public CategoriaDTO anularCategoria(Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));
        categoria.setEstado(false);
        return convertToDTO(categoriaRepository.save(categoria));
    }

    private CategoriaDTO convertToDTO(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(c.getIdCategoria());
        dto.setEstado(c.getEstado());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        return dto;
    }

    private Categoria convertToEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(true);
        return categoria;
    }
}
