<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Camine pal Gym — Suscripciones</title>
  <link rel="stylesheet" href="css/styles.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
</head>
<body>

<!-- PANEL LATERAL -->
<nav class="sidebar">
  <div class="sidebar-brand">
        <div class="brand-icon">
            <img src="img/logito.png" alt="Logo" class="logo">
        </div>
        <span class="brand-name"></span>
        <span class="brand-version">v2.40</span>
    </div>
  <ul class="nav-menu">
    <li class="nav-item"><a href="index.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>
      <span>Panel</span></a></li>
    <li class="nav-item"><a href="users.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
      <span>Usuarios</span></a></li>
    <li class="nav-item"><a href="products.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 7H4a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
      <span>Productos</span></a></li>
    <li class="nav-item active"><a href="subscriptions.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"/><line x1="1" y1="10" x2="23" y2="10"/></svg>
      <span>Suscripciones</span></a></li>
    <li class="nav-item"><a href="orders.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
      <span>Pedidos</span></a></li>
    <li class="nav-item"><a href="forum.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
      <span>Foro</span></a></li>
  </ul>
  <div class="sidebar-footer">
    <a href="LogoutServlet" class="back-link">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="15 18 9 12 15 6"/></svg>
      <span>SALIR</span>
    </a>
  </div>
</nav>

<!-- MAIN -->
<main class="main-content">

  <header class="topbar">
    <h1 class="page-title">Suscripciones</h1>
    <div class="topbar-right">
      <div class="search-box">
        <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
        <input type="text" placeholder="Buscar..." class="search-input" />
      </div>
      <button class="notif-btn">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>
        <span class="notif-badge">9</span>
      </button>
      <div class="user-profile">
        <div class="user-info">
          <span class="user-name">Administrador</span>
          <span class="user-role">Superadmin</span>
        </div>
        <div class="avatar">AU</div>
      </div>
    </div>
  </header>

  <div class="table-card fade-in" style="--delay:.08s">
    <table class="data-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Usuario</th>
          <th>Plan</th>
          <th>Estatus</th>
          <th>Fechas</th>
          <th style="text-align:right">Monto</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td><span class="sub-id-tag">SUB-001</span></td>
          <td>
            <div class="row-avatar">
              <div class="av-circle" style="background:#8b5cf6">SC</div>
              <span class="av-name">Sarah Chen</span>
            </div>
          </td>
          <td><span class="badge badge-pro">PRO</span></td>
          <td><span class="status-pill pill-active">Activo</span></td>
          <td class="dates-cell">
            <div><strong>Inicio:</strong> Feb 05, 2024</div>
            <div><strong>Renueva:</strong> Mar 05, 2024</div>
          </td>
          <td class="amount-cell">
            <span class="amount-main">$9.990 COP</span>
            <span class="amount-sub">/mo</span>
          </td>
        </tr>
        <tr>
          <td><span class="sub-id-tag">SUB-002</span></td>
          <td>
            <div class="row-avatar">
              <div class="av-circle" style="background:#06b6d4">MW</div>
              <span class="av-name">Marcus Webb</span>
            </div>
          </td>
          <td><span class="badge badge-pro">PRO</span></td>
          <td><span class="status-pill pill-expired">Expiro</span></td>
          <td class="dates-cell">
            <div><strong>Inicio:</strong> Feb 18, 2024</div>
            <div><strong>Renueva:</strong> Mar 18, 2024</div>
          </td>
          <td class="amount-cell">
            <span class="amount-main">$9.990 COP</span>
            <span class="amount-sub">/mo</span>
          </td>
        </tr>
        <tr>
          <td><span class="sub-id-tag">SUB-003</span></td>
          <td>
            <div class="row-avatar">
              <div class="av-circle" style="background:#f59e0b">PP</div>
              <span class="av-name">Priya Patel</span>
            </div>
          </td>
          <td><span class="badge badge-basic">BASICO</span></td>
          <td><span class="status-pill pill-active">Activo</span></td>
          <td class="dates-cell">
            <div><strong>Inicio:</strong> Mar 02, 2024</div>
            <div><strong>Renueva:</strong> —</div>
          </td>
          <td class="amount-cell">
            <span class="amount-main">$0 COP</span>
            <span class="amount-sub">/mo</span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

</main>
<script src="js/index.js"></script>
</body>
</html>
