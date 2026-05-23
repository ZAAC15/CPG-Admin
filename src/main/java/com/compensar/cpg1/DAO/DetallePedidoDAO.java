package com.compensar.cpg1.DAO;

import com.compensar.cpg1.model.entity.DetallePedido;
import com.compensar.cpg1.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class DetallePedidoDAO extends GenericDAO<DetallePedido> {

    public DetallePedidoDAO() {
        super(DetallePedido.class);
    }

    public List<DetallePedido> buscarPorPedido(Long idPedido) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT d FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido",
                    DetallePedido.class)
                    .setParameter("idPedido", idPedido)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}