package com.compensar.cpg1.service;

import com.compensar.cpg1.DAO.ClienteDAO;
import com.compensar.cpg1.DTO.ClienteDTO;
import com.compensar.cpg1.model.entity.Cliente;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public List<ClienteDTO> listarTodos() {
        List<Cliente> lista = clienteDAO.listarTodos();
        List<ClienteDTO> resultado = new ArrayList<>();
        for (Cliente c : lista) resultado.add(toDTO(c));
        return resultado;
    }

    public ClienteDTO buscarPorId(String id) {
        Cliente c = clienteDAO.buscarPorId(id);
        return c == null ? null : toDTO(c);
    }

    public ClienteDTO buscarPorEmail(String email) {
        Cliente c = clienteDAO.buscarPorEmail(email);
        return c == null ? null : toDTO(c);
    }

    public List<ClienteDTO> listarPorRol(String rol) {
        List<Cliente> lista = clienteDAO.buscarPorRol(rol.toUpperCase());
        List<ClienteDTO> resultado = new ArrayList<>();
        for (Cliente c : lista) resultado.add(toDTO(c));
        return resultado;
    }

    public ClienteDTO crear(ClienteDTO dto) {
        try {
            // Llamar a Supabase Admin API para crear usuario en auth
            java.net.URL url = new java.net.URL("https://vpxrzsdayfslvhtllqry.supabase.co/auth/v1/admin/users");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZweHJ6c2RheWZzbHZodGxscXJ5Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc3ODcxMjIwNCwiZXhwIjoyMDk0Mjg4MjA0fQ.qZuVTamPKZH9_DB3XQkhEq3R5T_HzA-BzUg3hMMm8lc");
            conn.setRequestProperty("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZweHJ6c2RheWZzbHZodGxscXJ5Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc3ODcxMjIwNCwiZXhwIjoyMDk0Mjg4MjA0fQ.qZuVTamPKZH9_DB3XQkhEq3R5T_HzA-BzUg3hMMm8lc");
            conn.setDoOutput(true);

            String body = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"email_confirm\":true," +
                            "\"user_metadata\":{\"nombre\":\"%s\",\"apellidos\":\"%s\"," +
                            "\"usuario\":\"%s\",\"celular\":\"%s\",\"rol\":\"%s\"}}",
                    dto.getEmail(), dto.getPassword(),
                    dto.getNombre(), dto.getApellido(),
                    dto.getNombre(), dto.getTelefono(),
                    dto.getRol() != null ? dto.getRol() : "cliente"
            );

            conn.getOutputStream().write(body.getBytes("UTF-8"));
            int status = conn.getResponseCode();
            // Leer respuesta para debug
            java.io.InputStream is = status >= 400 ? conn.getErrorStream() : conn.getInputStream();
            if (is != null) {
                String resp = new String(is.readAllBytes(), "UTF-8");
                System.out.println("Supabase response body: " + resp);
            }
            if (status == 200 || status == 201) {
                dto.setIdCliente("created");
                return dto;
            } else {
                throw new RuntimeException("Supabase error: " + status);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creando usuario: " + e.getMessage(), e);
        }
    }

    public ClienteDTO actualizar(String id, ClienteDTO dto) {
        EntityManager em = com.compensar.cpg1.util.JPAUtil.getEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery(
                            "UPDATE \"Usuarios\" SET nombre = ?, apellidos = ?, usuario = ?, " +
                                    "correo = ?, celular = ?, rol = ? WHERE id = CAST(? AS uuid)")
                    .setParameter(1, dto.getNombre())
                    .setParameter(2, dto.getApellido())
                    .setParameter(3, dto.getNombre())
                    .setParameter(4, dto.getEmail())
                    .setParameter(5, dto.getTelefono())
                    .setParameter(6, dto.getRol() != null ? dto.getRol() : "cliente")
                    .setParameter(7, id)
                    .executeUpdate();
            tx.commit();
            dto.setIdCliente(id);
            return dto;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error actualizando: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public boolean eliminar(String id) {
        EntityManager em = com.compensar.cpg1.util.JPAUtil.getEntityManager();
        jakarta.persistence.EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int rows = em.createNativeQuery(
                            "DELETE FROM \"Usuarios\" WHERE id = CAST(? AS uuid)")
                    .setParameter(1, id)
                    .executeUpdate();
            tx.commit();
            return rows > 0;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public ClienteDTO toDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(c.getIdCliente());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setEmail(c.getEmail());
        dto.setTelefono(c.getTelefono());
        dto.setRol(c.getRol());
        dto.setEstado(c.getEstado());
        dto.setFechaIngreso(c.getFechaIngreso());
        return dto;
    }
}