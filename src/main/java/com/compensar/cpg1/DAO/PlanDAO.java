package com.compensar.cpg1.DAO;

import com.compensar.cpg1.model.entity.Plan;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class PlanDAO extends GenericDAO<Plan> {

    public PlanDAO() {
        super(Plan.class);
    }

    public Plan buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Plan p WHERE p.nombre = :nombre", Plan.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}