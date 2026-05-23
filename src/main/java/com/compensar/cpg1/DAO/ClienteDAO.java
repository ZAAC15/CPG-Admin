package com.compensar.cpg1.DAO;

import com.compensar.cpg1.model.entity.Cliente;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class ClienteDAO extends GenericDAO<Cliente> {

    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Cliente> buscarPorRol(String rol) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.rol = :rol", Cliente.class)
                    .setParameter("rol", rol)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long contarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Cliente> listarRecientes(int limite) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cliente c ORDER BY c.fechaIngreso DESC", Cliente.class)
                    .setMaxResults(limite)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}