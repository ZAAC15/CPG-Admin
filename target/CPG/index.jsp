<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Camine pal Gym — Panel de Administración</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
</head>
<body>

<nav class="sidebar" id="sidebar">
    <div class="sidebar-brand">
        <div class="brand-icon">
            <img src="img/logito.png" alt="Logo" class="logo">
        </div>
        <span class="brand-name"></span>
        <span class="brand-version">v2.40</span>
    </div>
    <ul class="nav-menu">
        <li class="nav-item active">
            <a href="index.jsp" class="nav-link">
                <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/>
                    <rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/>
                </svg>
                <span>Panel</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="users.jsp" class="nav-link">
                <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                    <circle cx="9" cy="7" r="4"/>
                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                    <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                </svg>
                <span>Usuarios</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="products.jsp" class="nav-link">
                <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 7H4a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
                    <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/>
                </svg>
                <span>Productos</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="subscriptions.jsp" class="nav-link">
                <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="1" y="4" width="22" height="16" rx="2" ry="2"/>
                    <line x1="1" y1="10" x2="23" y2="10"/>
                </svg>
                <span>Suscripciones</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="orders.jsp" class="nav-link">
                <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                    <polyline points="14 2 14 8 20 8"/>
                </svg>
                <span>Pedidos</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="forum.jsp" class="nav-link">
                <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                </svg>
                <span>Foro</span>
            </a>
        </li>
    </ul>
    <div class="sidebar-footer">
        <a href="LogoutServlet" class="back-link">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="15 18 9 12 15 6"/>
            </svg>
            <span>SALIR</span>
        </a>
    </div>
</nav>

<main class="main-content">

    <header class="topbar">
        <h1 class="page-title">Panel de control</h1>
        <div class="topbar-right">
            <button class="notif-btn">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/>
                    <path d="M13.73 21a2 2 0 0 1-3.46 0"/>
                </svg>
                <span class="notif-badge">!</span>
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

    <section class="stats-grid">
        <div class="stat-card fade-in" style="--delay:0.05s">
            <div class="stat-icon icon-blue">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                    <circle cx="9" cy="7" r="4"/>
                </svg>
            </div>
            <div class="stat-info">
                <span class="stat-label">Usuarios Totales</span>
                <span class="stat-value" id="stat-usuarios">—</span>
            </div>
        </div>
        <div class="stat-card fade-in" style="--delay:0.1s">
            <div class="stat-icon icon-purple">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="1" y="4" width="22" height="16" rx="2" ry="2"/>
                    <line x1="1" y1="10" x2="23" y2="10"/>
                </svg>
            </div>
            <div class="stat-info">
                <span class="stat-label">Suscripciones Activas</span>
                <span class="stat-value" id="stat-suscripciones">—</span>
            </div>
        </div>
        <div class="stat-card fade-in" style="--delay:0.15s">
            <div class="stat-icon icon-orange">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 7H4a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
                </svg>
            </div>
            <div class="stat-info">
                <span class="stat-label">Productos Totales</span>
                <span class="stat-value" id="stat-productos">—</span>
            </div>
        </div>
        <div class="stat-card fade-in" style="--delay:0.2s">
            <div class="stat-icon icon-green">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="12" y1="1" x2="12" y2="23"/>
                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                </svg>
            </div>
            <div class="stat-info">
                <span class="stat-label">Ganancia Mensual</span>
                <span class="stat-value" id="stat-ganancia">—</span>
            </div>
        </div>
    </section>

    <section class="tables-row">
        <div class="table-card-dash fade-in" style="--delay:0.25s">
            <div class="table-header">
                <h2 class="table-title">Usuarios recientes</h2>
                <a href="users.jsp" class="view-all">Ver todo</a>
            </div>
            <div class="user-list" id="lista-usuarios">
                <div class="loading-msg">Cargando usuarios...</div>
            </div>
        </div>
        <div class="table-card-dash fade-in" style="--delay:0.3s">
            <div class="table-header">
                <h2 class="table-title">Órdenes recientes</h2>
                <a href="orders.jsp" class="view-all">Ver todo</a>
            </div>
            <div class="orders-list" id="lista-pedidos">
                <div class="loading-msg">Cargando pedidos...</div>
            </div>
        </div>
    </section>

</main>

<script src="js/index.js"></script>
</body>
</html>
