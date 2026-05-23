package com.compensar.cpg1.controller;

import com.compensar.cpg1.DTO.PedidoDTO;
import com.compensar.cpg1.service.PedidoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

    private final PedidoService service = new PedidoService();

    @GET
    public List<PedidoDTO> listar(@QueryParam("recientes") Integer recientes) {
        if (recientes != null && recientes > 0) {
            return service.listarRecientes(recientes);
        }
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") String id) {
        PedidoDTO dto = service.buscarPorId(id);
        return dto != null ? Response.ok(dto).build() : Response.status(404).build();
    }

    @PUT
    @Path("/{id}/estado")
    public Response actualizarEstado(@PathParam("id") String id, @QueryParam("estado") String estado) {
        PedidoDTO dto = service.actualizarEstado(id, estado);
        return dto != null ? Response.ok(dto).build() : Response.status(404).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        return service.eliminar(id)
                ? Response.noContent().build()
                : Response.status(404).build();
    }
}