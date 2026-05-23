package com.compensar.cpg1.service;

import com.compensar.cpg1.DAO.PostDAO;
import com.compensar.cpg1.DTO.PostDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {

    private final PostDAO postDAO = new PostDAO();

    public List<PostDTO> listarTodos() {
        List<Object[]> rows = postDAO.listarTodos();
        List<PostDTO> resultado = new ArrayList<>();
        for (Object[] row : rows) resultado.add(rowToDTO(row));
        return resultado;
    }

    public boolean ocultar(String id) {
        return postDAO.toggleActivo(id, false);
    }

    public boolean mostrar(String id) {
        return postDAO.toggleActivo(id, true);
    }

    public boolean eliminar(String id) {
        return postDAO.eliminar(id);
    }

    private PostDTO rowToDTO(Object[] row) {
        // [id, usuario_id, nombre, titulo, contenido, imagen_url, etiqueta, likes, activo, created_at]
        PostDTO dto = new PostDTO();
        dto.setId(row[0] != null ? row[0].toString() : null);
        dto.setUsuarioId(row[1] != null ? row[1].toString() : null);
        dto.setNombre(row[2] != null ? row[2].toString() : "");
        dto.setTitulo(row[3] != null ? row[3].toString() : "");
        dto.setContenido(row[4] != null ? row[4].toString() : "");
        dto.setImagenUrl(row[5] != null ? row[5].toString() : null);
        dto.setEtiqueta(row[6] != null ? row[6].toString() : null);
        dto.setLikes(row[7] != null ? ((Number) row[7]).intValue() : 0);
        dto.setActivo(row[8] == null || (Boolean) row[8]);
        Object fecha = row[9];
        if (fecha instanceof Timestamp) dto.setCreatedAt(((Timestamp) fecha).toLocalDateTime());
        else if (fecha instanceof LocalDateTime) dto.setCreatedAt((LocalDateTime) fecha);
        return dto;
    }
}