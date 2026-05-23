package com.compensar.cpg1.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String USUARIO   = "admin";
    private static final String PASSWORD  = "admin";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String usuario  = req.getParameter("usuario");
        String password = req.getParameter("password");

        if (USUARIO.equals(usuario) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("adminLoggedIn", true);
            session.setAttribute("adminUser", usuario);
            session.setMaxInactiveInterval(60 * 60); // 1 hora
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("loginError", "invalid");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}