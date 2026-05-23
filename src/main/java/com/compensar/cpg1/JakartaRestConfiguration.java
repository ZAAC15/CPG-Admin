package com.compensar.cpg1;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class JakartaRestConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Controllers
        classes.add(com.compensar.cpg1.controller.ClienteController.class);
        classes.add(com.compensar.cpg1.controller.ProductoController.class);
        classes.add(com.compensar.cpg1.controller.PedidoController.class);
        classes.add(com.compensar.cpg1.controller.DashboardController.class);
        classes.add(com.compensar.cpg1.controller.SuscripcionController.class);
        classes.add(com.compensar.cpg1.controller.PostController.class);

        // Jackson provider directo
        classes.add(JacksonContextResolver.class);
        classes.add(com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider.class);
        return classes;
    }
}