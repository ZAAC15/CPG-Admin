package com.compensar.cpg1.service;

import com.compensar.cpg1.DAO.PedidoDAO;
import com.compensar.cpg1.DTO.PedidoDTO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public List<PedidoDTO> listarTodos() {
        return mapear(pedidoDAO.listarTodos());
    }

    public List<PedidoDTO> listarRecientes(int limite) {
        return mapear(pedidoDAO.listarRecientes(limite));
    }

    public List<PedidoDTO> listarPorEstado(String estado) {
        return listarTodos();
    }

    public PedidoDTO buscarPorId(String id) {
        return null;
    }

    public PedidoDTO actualizarEstado(String id, String nuevoEstado) {
        return null;
    }

    public boolean eliminar(String id) {
        return false;
    }

    private List<PedidoDTO> mapear(List<Object[]> rows) {
        List<PedidoDTO> resultado = new ArrayList<>();
        for (Object[] row : rows) {
            resultado.add(rowToDTO(row));
        }
        return resultado;
    }

    private PedidoDTO rowToDTO(Object[] row) {
        // row: [id, user_id, total, descuento, created_at, nombre_cliente, cantidad_items]
        PedidoDTO dto = new PedidoDTO();

        String id = row[0] != null ? row[0].toString() : null;
        dto.setIdPedido(id);
        dto.setCodigo(id != null ? "ORD-" + id.substring(0, 8).toUpperCase() : "—");

        String idCliente = row[1] != null ? row[1].toString() : null;
        dto.setIdCliente(idCliente);

        Object totalObj = row[2];
        if (totalObj != null) {
            dto.setTotal(new BigDecimal(totalObj.toString()));
        }

        // created_at
        Object fechaObj = row[4];
        if (fechaObj instanceof Timestamp) {
            dto.setFechaPedido(((Timestamp) fechaObj).toLocalDateTime());
        } else if (fechaObj instanceof LocalDateTime) {
            dto.setFechaPedido((LocalDateTime) fechaObj);
        }

        // nombre_cliente
        String nombreCliente = row[5] != null ? row[5].toString().trim() : "";
        dto.setNombreCliente(nombreCliente.isEmpty() ? "Cliente" : nombreCliente);

        // cantidad_items
        Object itemsObj = row[6];
        dto.setCantidadItems(itemsObj != null ? ((Number) itemsObj).intValue() : 0);

        dto.setEstado("COMPLETADO");
        return dto;
    }
}