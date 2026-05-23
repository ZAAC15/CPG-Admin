package com.compensar.cpg1.service;

import com.compensar.cpg1.DAO.ProductoDAO;
import com.compensar.cpg1.DTO.ProductoDTO;
import com.compensar.cpg1.model.entity.Producto;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductoService {

    private final ProductoDAO productoDAO = new ProductoDAO();

    public List<ProductoDTO> listarTodos() {
        List<Producto> lista = productoDAO.listarTodos();
        List<ProductoDTO> resultado = new ArrayList<>();
        for (Producto p : lista) resultado.add(toDTO(p));
        return resultado;
    }

    public ProductoDTO buscarPorId(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<?> res = em.createNativeQuery(
                            "SELECT id, nombre, descripcion, precio, categoria, insignia, imagen_url, rating, stock, activo " +
                                    "FROM \"Productos\" WHERE id = CAST(? AS uuid)", Producto.class)
                    .setParameter(1, id)
                    .getResultList();
            return res.isEmpty() ? null : toDTO((Producto) res.get(0));
        } finally { em.close(); }
    }

    public ProductoDTO crear(ProductoDTO dto) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String nuevoId = UUID.randomUUID().toString();
            em.createNativeQuery(
                            "INSERT INTO \"Productos\" (id, nombre, descripcion, precio, categoria, insignia, imagen_url, stock, activo) " +
                                    "VALUES (CAST(? AS uuid), ?, ?, ?, ?, ?, ?, ?, ?)")
                    .setParameter(1, nuevoId)
                    .setParameter(2, dto.getNombre())
                    .setParameter(3, dto.getDescripcion())
                    .setParameter(4, dto.getPrecio() != null ? dto.getPrecio() : BigDecimal.ZERO)
                    .setParameter(5, dto.getCategoria())
                    .setParameter(6, dto.getInsignia())
                    .setParameter(7, dto.getImagenUrl())
                    .setParameter(8, dto.getStock() != null ? dto.getStock() : 0)
                    .setParameter(9, dto.getActivo() != null ? dto.getActivo() : true)
                    .executeUpdate();
            tx.commit();
            dto.setId(nuevoId);
            return dto;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error creando producto: " + e.getMessage(), e);
        } finally { em.close(); }
    }

    public ProductoDTO actualizar(String id, ProductoDTO dto) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery(
                            "UPDATE \"Productos\" SET nombre=?, descripcion=?, precio=?, categoria=?, " +
                                    "insignia=?, imagen_url=?, stock=?, activo=? WHERE id=CAST(? AS uuid)")
                    .setParameter(1, dto.getNombre())
                    .setParameter(2, dto.getDescripcion())
                    .setParameter(3, dto.getPrecio())
                    .setParameter(4, dto.getCategoria())
                    .setParameter(5, dto.getInsignia())
                    .setParameter(6, dto.getImagenUrl())
                    .setParameter(7, dto.getStock())
                    .setParameter(8, dto.getActivo() != null ? dto.getActivo() : true)
                    .setParameter(9, id)
                    .executeUpdate();
            tx.commit();
            dto.setId(id);
            return dto;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error actualizando producto: " + e.getMessage(), e);
        } finally { em.close(); }
    }

    public boolean eliminar(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int rows = em.createNativeQuery(
                            "DELETE FROM \"Productos\" WHERE id = CAST(? AS uuid)")
                    .setParameter(1, id)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally { em.close(); }
    }

    public long contarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Object res = em.createNativeQuery("SELECT COUNT(id) FROM \"Productos\"")
                    .getSingleResult();
            return ((Number) res).longValue();
        } finally { em.close(); }
    }

    public ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setCategoria(p.getCategoria());
        dto.setInsignia(p.getInsignia());
        dto.setImagenUrl(p.getImagenUrl());
        dto.setRating(p.getRating());
        dto.setStock(p.getStock());
        dto.setActivo(p.getActivo());
        return dto;
    }
}