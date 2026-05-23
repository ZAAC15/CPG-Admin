package com.compensar.cpg1.DAO;

import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PedidoDAO {

    @SuppressWarnings("unchecked")
    public List<Object[]> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JOIN ordenes con orden_items para contar items reales
            // y con "Usuarios" para traer el nombre del cliente
            return em.createNativeQuery(
                            "SELECT o.id, o.user_id, o.total, o.descuento, o.created_at, " +
                                    "       COALESCE(u.nombre, '') || ' ' || COALESCE(u.apellidos, '') AS nombre_cliente, " +
                                    "       COUNT(oi.id) AS cantidad_items " +
                                    "FROM ordenes o " +
                                    "LEFT JOIN \"Usuarios\" u ON u.id = o.user_id " +
                                    "LEFT JOIN orden_items oi ON oi.orden_id = o.id " +
                                    "GROUP BY o.id, o.user_id, o.total, o.descuento, o.created_at, u.nombre, u.apellidos " +
                                    "ORDER BY o.created_at DESC")
                    .getResultList();
        } finally { em.close(); }
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> listarRecientes(int limite) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createNativeQuery(
                            "SELECT o.id, o.user_id, o.total, o.descuento, o.created_at, " +
                                    "       COALESCE(u.nombre, '') || ' ' || COALESCE(u.apellidos, '') AS nombre_cliente, " +
                                    "       COUNT(oi.id) AS cantidad_items " +
                                    "FROM ordenes o " +
                                    "LEFT JOIN \"Usuarios\" u ON u.id = o.user_id " +
                                    "LEFT JOIN orden_items oi ON oi.orden_id = o.id " +
                                    "GROUP BY o.id, o.user_id, o.total, o.descuento, o.created_at, u.nombre, u.apellidos " +
                                    "ORDER BY o.created_at DESC " +
                                    "LIMIT " + limite)
                    .getResultList();
        } finally { em.close(); }
    }

    public Double sumarGananciaMes(int anio, int mes) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Object res = em.createNativeQuery(
                            "SELECT COALESCE(SUM(total), 0) FROM ordenes")
                    .getSingleResult();
            return res != null ? ((Number) res).doubleValue() : 0.0;
        } finally { em.close(); }
    }
}