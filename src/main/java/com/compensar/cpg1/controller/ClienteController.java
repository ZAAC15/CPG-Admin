package com.compensar.cpg1.controller;

import com.compensar.cpg1.DTO.ClienteDTO;
import com.compensar.cpg1.service.ClienteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    private final ClienteService service = new ClienteService();

    @GET
    public Response listarTodos(@QueryParam("rol") String rol) {
        try {
            List<ClienteDTO> lista = (rol == null || rol.isEmpty())
                    ? service.listarTodos() : service.listarPorRol(rol);
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(500).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET @Path("/{id}")
    public Response buscarPorId(@PathParam("id") String id) {
        ClienteDTO dto = service.buscarPorId(id);
        if (dto == null) return Response.status(404).entity(Map.of("error","No encontrado")).build();
        return Response.ok(dto).build();
    }

    @POST
    public Response crear(ClienteDTO dto) {
        try {
            return Response.status(201).entity(service.crear(dto)).build();
        } catch (Exception e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, ClienteDTO dto) {
        ClienteDTO actualizado = service.actualizar(id, dto);
        if (actualizado == null) return Response.status(404).entity(Map.of("error","No encontrado")).build();
        return Response.ok(actualizado).build();
    }

    @DELETE @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        if (!service.eliminar(id)) return Response.status(404).entity(Map.of("error","No encontrado")).build();
        return Response.noContent().build();
    }
}