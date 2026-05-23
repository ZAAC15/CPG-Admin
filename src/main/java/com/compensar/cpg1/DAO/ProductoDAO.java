package com.compensar.cpg1.DAO;

import com.compensar.cpg1.model.entity.Producto;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Producto p ORDER BY p.nombre", Producto.class)
                    .getResultList();
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
}