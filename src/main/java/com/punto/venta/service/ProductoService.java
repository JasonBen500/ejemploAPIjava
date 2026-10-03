package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ProductoDTO> mostrarActivos() {
        return productoRepository.findByEstadoTrueOrderByIdProductoDesc()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ProductoDTO> mostrarActivosFiltro(String nombre) {
        return productoRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ProductoDTO> mostrarActivosFiltroTop2(String nombre) {
        return productoRepository.findTop2ByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProductoDTO crear(ProductoDTO dto) {
        if (productoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("El producto ya existe");
        }
        return convertToDTO(productoRepository.save(convertToEntity(dto)));
    }

    public ProductoDTO actualizar(Integer idProducto, ProductoDTO dto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("El producto no existe con id " + idProducto));
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setIdCategoria(new Categoria(dto.getIdCategoria()));
        return convertToDTO(productoRepository.save(producto));
    }

    public ProductoDTO anular(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("El producto no existe con id " + idProducto));
        producto.setEstado(false);
        return convertToDTO(productoRepository.save(producto));
    }

    private ProductoDTO convertToDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setEstado(p.getEstado());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setIdCategoria(p.getIdCategoria() != null ? p.getIdCategoria().getIdCategoria() : null);
        return dto;
    }

    private Producto convertToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setEstado(true);
        producto.setIdCategoria(new Categoria(dto.getIdCategoria()));
        return producto;
    }
}
