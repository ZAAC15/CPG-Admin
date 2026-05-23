package com.compensar.cpg1.controller;

import com.compensar.cpg1.DTO.SuscripcionDTO;
import com.compensar.cpg1.service.SuscripcionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/suscripciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuscripcionController {

    private final SuscripcionService service = new SuscripcionService();

    @GET
    public Response listar(@QueryParam("estado") String estado,
                           @QueryParam("cliente") Long idCliente) {
        try {
            List<SuscripcionDTO> lista;
            if (idCliente != null) {
                lista = service.listarPorCliente(String.valueOf(idCliente));
            } else if (estado != null && !estado.isEmpty()) {
                lista = service.listarPorEstado(estado);
            } else {
                lista = service.listarTodos();
            }
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        SuscripcionDTO dto = service.buscarPorId(String.valueOf(id));
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Suscripcion no encontrada"))
                    .build();
        }
        return Response.ok(dto).build();
    }

    @POST
    public Response crear(SuscripcionDTO dto) {
        try {
            SuscripcionDTO creada = service.crear(dto);
            return Response.status(Response.Status.CREATED).entity(creada).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, SuscripcionDTO dto) {
        try {
            SuscripcionDTO actualizada = service.actualizar(String.valueOf(id), dto);
            if (actualizada == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Suscripcion no encontrada"))
                        .build();
            }
            return Response.ok(actualizada).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean ok = service.eliminar(String.valueOf(id));
        if (!ok) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Suscripcion no encontrada"))
                    .build();
        }
        return Response.noContent().build();
    }

}