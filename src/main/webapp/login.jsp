<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="true"%>
<%
    if (session.getAttribute("adminLoggedIn") != null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String error = (String) request.getAttribute("loginError");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Camine pal Gym — Acceso Admin</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="login-box">
    <div class="login-logo">
        <img src="img/logito.png" alt="Logo">
        <h1>Panel Administrativo</h1>
    </div>

    <% if (error != null) { %>
    <div class="error-msg">Usuario o contraseña incorrectos</div>
    <% } %>

    <form method="post" action="LoginServlet">
        <div class="field">
            <label>Usuario</label>
            <input type="text" name="usuario" placeholder="admin" autocomplete="username" required/>
        </div>
        <div class="field">
            <label>Contraseña</label>
            <input type="password" name="password" placeholder="••••••" autocomplete="current-password" required/>
        </div>
        <div class="hint-box">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
            <span>Usuario: <strong>admin</strong> &nbsp;|&nbsp; Contraseña: <strong>admin</strong></span>
        </div>
        <button type="submit" class="btn-login">Ingresar</button>
    </form>
</div>
</body>
</html>
