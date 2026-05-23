<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="true"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Camine pal Gym — Productos</title>
  <link rel="stylesheet" href="css/styles.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
  <style>
    /* estilos específicos dpara prductos estos no estan en css */
    .prod-cell  { display:flex; align-items:center; gap:14px; }
    .prod-thumb { width:48px; height:48px; border-radius:8px; overflow:hidden; flex-shrink:0;
      background:var(--bg-hover); border:1px solid var(--border); }
    .prod-img   { width:100%; height:100%; object-fit:cover; display:block; }
    .prod-img-fallback { width:100%; height:100%; display:flex; align-items:center;
      justify-content:center; color:var(--text-muted); }
    .prod-name  { font-weight:600; font-size:14px; margin-bottom:4px; }
  </style>
</head>
<body>

<nav class="sidebar">
  <div class="sidebar-brand">
    <div class="brand-icon"><img src="img/logito.png" alt="Logo" class="logo"></div>
    <span class="brand-version">v2.40</span>
  </div>
  <ul class="nav-menu">
    <li class="nav-item"><a href="index.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>
      <span>Panel</span></a></li>
    <li class="nav-item"><a href="users.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
      <span>Usuarios</span></a></li>
    <li class="nav-item active"><a href="products.jsp" class="nav-link">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 7H4a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
      <span>Productos</span></a></li>
    <li class="nav-item"><a href="subscriptions.jsp" class="nav-link">
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

<main class="main-content">

  <header class="topbar">
    <h1 class="page-title">Productos</h1>
    <div class="topbar-right">
      <div class="search-box">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
        <input type="text" id="search-input" placeholder="Buscar..." class="search-input" oninput="filtrarTabla()"/>
      </div>
      <div class="user-profile">
        <div class="user-info"><span class="user-name">Administrador</span><span class="user-role">Superadmin</span></div>
        <div class="avatar">AU</div>
      </div>
    </div>
  </header>

  <div class="filters-row" id="filtros-categorias">
    <button class="filter-btn active" onclick="setFiltro('todos', this)">Todos</button>
    <!-- las categorías reales se inyectan desde products.js una vez cargan los datos -->
  </div>
  <div style="display:flex; justify-content:flex-end; margin-top:-10px; margin-bottom:4px;">
    <button class="btn-add" onclick="abrirModalNuevo()">
      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
      Agregar producto
    </button>
  </div>

  <div class="users-table-wrap">
    <table class="users-table">
      <thead>
      <tr>
        <th>Producto</th>
        <th>Categoría</th>
        <th>Precio</th>
        <th>Stock</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody id="tabla-body">
      <tr><td colspan="5" style="text-align:center;padding:32px;color:var(--text-muted)">Cargando productos...</td></tr>
      </tbody>
    </table>
    <div class="pag-row">
      <span id="pag-info">—</span>
      <div class="pag-btns">
        <button class="pag-btn" onclick="cambiarPag(-1)">&#8592;</button>
        <button class="pag-btn" onclick="cambiarPag(1)">&#8594;</button>
      </div>
    </div>
  </div>

</main>

/* modal agregar y editar producto */
<div class="modal-overlay" id="modal-overlay">
  <div class="modal">
    <button class="close-modal" onclick="cerrarModal()">&#x2715;</button>
    <h2 id="modal-titulo">Agregar Producto</h2>
    <input type="hidden" id="p-id"/>
    <div class="form-grid">
      <div class="field full"><label>Nombre</label><input id="p-nombre" placeholder="Nombre del producto"/></div>
      <div class="field full"><label>Descripción</label><input id="p-descripcion" placeholder="Descripción breve"/></div>
      <div class="field"><label>Precio (COP)</label><input id="p-precio" type="number" placeholder="0"/></div>
      <div class="field"><label>Stock</label><input id="p-stock" type="number" placeholder="0"/></div>
      <div class="field"><label>Categoría</label><input id="p-categoria" placeholder="Ej: Suplementos"/></div>
      <div class="field"><label>Insignia</label><input id="p-insignia" placeholder="Ej: Nuevo, Más vendida"/></div>
      <div class="field full"><label>URL imagen</label><input id="p-imagen" placeholder="https://..."/></div>
      <div class="field full" style="display:flex;align-items:center;gap:10px;padding-top:6px;">
        <input id="p-activo" type="checkbox" checked style="width:auto;accent-color:var(--yellow)"/>
        <label for="p-activo" style="text-transform:none;font-size:13px">Producto activo</label>
      </div>
    </div>
    <div class="modal-footer">
      <button class="btn-cancel" onclick="cerrarModal()">Cancelar</button>
      <button class="btn-save" onclick="guardarProducto()">Crear producto</button>
    </div>
  </div>
</div>

<div class="toast" id="toast"></div>

<script src="js/products.js"></script>
</body>
</html>
