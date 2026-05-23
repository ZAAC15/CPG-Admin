package com.compensar.cpg1.controller;

import com.compensar.cpg1.DTO.PostDTO;
import com.compensar.cpg1.service.PostService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    private final PostService service = new PostService();

    @GET
    public List<PostDTO> listar() {
        return service.listarTodos();
    }

    @PUT
    @Path("/{id}/ocultar")
    public Response ocultar(@PathParam("id") String id) {
        return service.ocultar(id)
                ? Response.ok(Map.of("activo", false)).build()
                : Response.status(404).build();
    }

    @PUT
    @Path("/{id}/mostrar")
    public Response mostrar(@PathParam("id") String id) {
        return service.mostrar(id)
                ? Response.ok(Map.of("activo", true)).build()
                : Response.status(404).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        return service.eliminar(id)
                ? Response.noContent().build()
                : Response.status(404).build();
    }
}