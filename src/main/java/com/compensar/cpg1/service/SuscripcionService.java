package com.compensar.cpg1.service;

import com.compensar.cpg1.DTO.SuscripcionDTO;
import java.util.ArrayList;
import java.util.List;

public class SuscripcionService {

    public List<SuscripcionDTO> listarTodos() {
        return new ArrayList<>();
    }

    public SuscripcionDTO buscarPorId(String id) {
        return null;
    }

    public List<SuscripcionDTO> listarPorEstado(String estado) {
        return new ArrayList<>();
    }

    public List<SuscripcionDTO> listarPorCliente(String idCliente) {
        return new ArrayList<>();
    }

    public SuscripcionDTO crear(SuscripcionDTO dto) {
        throw new UnsupportedOperationException("Tabla suscripcion no disponible aun");
    }

    public SuscripcionDTO actualizar(String id, SuscripcionDTO dto) {
        return null;
    }

    public boolean eliminar(String id) {
        return false;
    }
}