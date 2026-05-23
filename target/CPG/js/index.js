const BASE = window.location.pathname.split('/').slice(0, 2).join('/') + '/api';

/* UTILIDADES  */
const COLORS = ['#3b82f6', '#8b5cf6', '#06b6d4', '#f59e0b', '#10b981', '#ef4444', '#f97316'];

function colorFor(str) {
    let h = 0;
    for (let i = 0; i < str.length; i++) h = str.charCodeAt(i) + ((h << 5) - h);
    return COLORS[Math.abs(h) % COLORS.length];
}

function initials(nombre, apellido) {
    return ((nombre || '?')[0] + (apellido || '?')[0]).toUpperCase();
}

function badgeClass(rol) {
    if (!rol) return 'badge-basic';
    const r = rol.toLowerCase();
    if (r === 'administrador') return 'badge-admin';
    if (r === 'pro') return 'badge-pro';
    return 'badge-basic';
}

function badgeLabel(rol) {
    if (!rol) return 'BÁSICO';
    const r = rol.toLowerCase();
    if (r === 'administrador') return 'ADMIN';
    if (r === 'pro') return 'PRO';
    return 'BÁSICO';
}

function animateValue(el, target) {
    if (!el) return;
    const duration = 1200;
    const start = performance.now();
    (function step(now) {
        const p = Math.min((now - start) / duration, 1);
        const v = Math.floor((1 - Math.pow(1 - p, 3)) * target);
        el.textContent = v.toLocaleString('es-CO');
        if (p < 1) requestAnimationFrame(step);
    })(start);
}

/* CARGA DE DATOS */
document.addEventListener('DOMContentLoaded', function () {

    /* Métricas del dashboard */
    fetch(BASE + '/dashboard')
        .then(r => r.json())
        .then(d => {
            animateValue(document.getElementById('stat-usuarios'),      d.usuariosTotales      || 0);
            animateValue(document.getElementById('stat-suscripciones'), d.suscripcionesActivas || 0);
            animateValue(document.getElementById('stat-productos'),     d.productosTotales     || 0);
            const g = parseFloat(d.gananciaMensual || 0);
            const el = document.getElementById('stat-ganancia');
            if (el) el.textContent = '$' + g.toLocaleString('es-CO', { minimumFractionDigits: 0 });
        })
        .catch(() => {
            ['stat-usuarios', 'stat-suscripciones', 'stat-productos', 'stat-ganancia']
                .forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.textContent = 'Error';
                });
        });

    /* Últimos 5 usuarios */
    fetch(BASE + '/clientes')
        .then(r => r.json())
        .then(lista => {
            const cont = document.getElementById('lista-usuarios');
            if (!cont) return;
            if (!lista.length) {
                cont.innerHTML = '<div class="loading-msg">Sin usuarios registrados</div>';
                return;
            }
            cont.innerHTML = lista.slice(0, 5).map(c => {
                const ini   = initials(c.nombre, c.apellido);
                const color = colorFor(c.email || c.nombre || '?');
                const badge = badgeClass(c.rol);
                const label = badgeLabel(c.rol);
                return `<div class="user-row">
                    <div class="user-avatar-sm" style="background:${color}">${ini}</div>
                    <div class="user-details">
                        <span class="user-fullname">${(c.nombre || '') + ' ' + (c.apellido || '')}</span>
                        <span class="user-email">${c.email || ''}</span>
                    </div>
                    <span class="badge ${badge}">${label}</span>
                </div>`;
            }).join('');
        })
        .catch(() => {
            const cont = document.getElementById('lista-usuarios');
            if (cont) cont.innerHTML = '<div class="loading-msg">No se pudo conectar</div>';
        });

    /* Últimos 4 pedidos */
    fetch(BASE + '/pedidos?recientes=4')
        .then(r => r.json())
        .then(lista => {
            const cont = document.getElementById('lista-pedidos');
            if (!cont) return;
            if (!lista.length) {
                cont.innerHTML = '<div class="loading-msg">Sin pedidos registrados</div>';
                return;
            }
            cont.innerHTML = lista.map(p => {
                const monto = parseFloat(p.total || 0)
                    .toLocaleString('es-CO', { minimumFractionDigits: 0 });
                const items = p.cantidadItems || 0;
                return `<div class="order-row-dash">
                    <div class="order-info">
                        <span class="order-id-dash">${p.codigo || '—'}</span>
                        <span class="order-meta">${p.nombreCliente || 'Cliente'} &nbsp;•&nbsp; ${items} ${items === 1 ? 'producto' : 'productos'}</span>
                    </div>
                    <div class="order-right-dash">
                        <span class="order-amount-dash">$${monto} COP</span>
                    </div>
                </div>`;
            }).join('');
        })
        .catch(() => {
            const cont = document.getElementById('lista-pedidos');
            if (cont) cont.innerHTML = '<div class="loading-msg">No se pudo conectar</div>';
        });

    /* FILTROS */
    document.querySelectorAll('.tab-btn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const group = this.closest('.tab-group');
            if (group) group.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            const filter = this.getAttribute('data-filter');
            if (!filter) return;
            document.querySelectorAll('[data-category]').forEach(function (row) {
                row.style.display = (filter === 'all' || row.getAttribute('data-category') === filter) ? '' : 'none';
            });
        });
    });

    /*  BÚSQUEDA EN TABLA  */
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        searchInput.addEventListener('input', function () {
            const term = this.value.toLowerCase().trim();
            document.querySelectorAll('.data-table tbody tr').forEach(function (row) {
                row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
            });
        });
    }

    document.querySelectorAll('.data-table tbody tr').forEach(function (row, i) {
        row.style.animationDelay = (0.04 * i) + 's';
        row.classList.add('fade-in');
    });

});