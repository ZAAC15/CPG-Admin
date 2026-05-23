package com.compensar.cpg1.DAO;

import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class PostDAO {

    @SuppressWarnings("unchecked")
    public List<Object[]> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createNativeQuery(
                            "SELECT p.id, p.usuario_id, p.nombre, p.titulo, p.contenido, " +
                                    "       p.imagen_url, p.etiqueta, p.likes, p.activo, p.created_at " +
                                    "FROM \"Posts\" p ORDER BY p.created_at DESC")
                    .getResultList();
        } finally { em.close(); }
    }

    public boolean toggleActivo(String id, boolean activo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int rows = em.createNativeQuery(
                            "UPDATE \"Posts\" SET activo = ? WHERE id = CAST(? AS uuid)")
                    .setParameter(1, activo)
                    .setParameter(2, id)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally { em.close(); }
    }

    public boolean eliminar(String id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int rows = em.createNativeQuery(
                            "DELETE FROM \"Posts\" WHERE id = CAST(? AS uuid)")
                    .setParameter(1, id)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally { em.close(); }
    }
}