package com.compensar.cpg1.service;

import com.compensar.cpg1.DAO.ClienteDAO;
import com.compensar.cpg1.DAO.PedidoDAO;
import com.compensar.cpg1.DAO.ProductoDAO;
import com.compensar.cpg1.DTO.DashboardDTO;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DashboardService {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public DashboardDTO obtenerMetricas() {
        long usuarios = clienteDAO.contarTodos();
        long suscripciones = 0; // tabla no disponible aún
        long productos = productoDAO.contarTodos();

        LocalDate hoy = LocalDate.now();
        BigDecimal ganancia = BigDecimal.valueOf(pedidoDAO.sumarGananciaMes(hoy.getYear(), hoy.getMonthValue()));

        return new DashboardDTO(usuarios, suscripciones, productos, ganancia);
    }
}