package com.compensar.cpg1.DAO;

import com.compensar.cpg1.model.entity.Suscripcion;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SuscripcionDAO extends GenericDAO<Suscripcion> {

    public SuscripcionDAO() {
        super(Suscripcion.class);
    }

    public List<Suscripcion> buscarPorEstado(String estado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM Suscripcion s WHERE s.estado = :estado", Suscripcion.class)
                    .setParameter("estado", estado)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Suscripcion> buscarPorCliente(Long idCliente) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM Suscripcion s WHERE s.cliente.idCliente = :idCliente",
                    Suscripcion.class)
                    .setParameter("idCliente", idCliente)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long contarActivas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(s) FROM Suscripcion s WHERE s.estado = :estado", Long.class)
                    .setParameter("estado", "ACTIVO")
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public String generarSiguienteCodigo() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(s) FROM Suscripcion s", Long.class)
                    .getSingleResult();
            return String.format("SUB-%03d", count.intValue() + 1);
        } finally {
            em.close();
        }
    }

}