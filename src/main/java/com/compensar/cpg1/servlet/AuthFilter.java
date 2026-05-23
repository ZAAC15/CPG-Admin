package com.compensar.cpg1.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*.jsp")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Permitir acceso al login sin autenticación
        if (uri.endsWith("login.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // Verificar sesión
        Object loggedIn = req.getSession(false) != null
                ? req.getSession(false).getAttribute("adminLoggedIn")
                : null;

        if (loggedIn == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }
}