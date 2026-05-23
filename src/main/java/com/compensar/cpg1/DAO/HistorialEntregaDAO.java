package com.compensar.cpg1.DAO;

import com.compensar.cpg1.model.entity.HistorialEntrega;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class HistorialEntregaDAO extends GenericDAO<HistorialEntrega> {

    public HistorialEntregaDAO() {
        super(HistorialEntrega.class);
    }

    public List<HistorialEntrega> buscarPorPedido(Long idPedido) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT h FROM HistorialEntrega h WHERE h.pedido.idPedido = :idPedido "
                  + "ORDER BY h.fechaEvento ASC",
                    HistorialEntrega.class)
                    .setParameter("idPedido", idPedido)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}