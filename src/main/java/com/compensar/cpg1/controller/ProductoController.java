package com.compensar.cpg1.controller;

import com.compensar.cpg1.DTO.ProductoDTO;
import com.compensar.cpg1.service.ProductoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoController {

    private final ProductoService service = new ProductoService();

    @GET
    public List<ProductoDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") String id) {
        ProductoDTO dto = service.buscarPorId(id);
        return dto != null ? Response.ok(dto).build() : Response.status(404).build();
    }

    @POST
    public Response crear(ProductoDTO dto) {
        try {
            return Response.status(201).entity(service.crear(dto)).build();
        } catch (Exception e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, ProductoDTO dto) {
        try {
            ProductoDTO actualizado = service.actualizar(id, dto);
            return actualizado != null ? Response.ok(actualizado).build() : Response.status(404).build();
        } catch (Exception e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        return service.eliminar(id)
                ? Response.noContent().build()
                : Response.status(404).build();
    }
}