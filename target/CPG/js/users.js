const BASE = window.location.pathname.split('/').slice(0, 2).join('/') + '/api';
const COLORS = ['#3b82f6', '#8b5cf6', '#06b6d4', '#f59e0b', '#10b981', '#ef4444', '#f97316'];

function colorFor(s) {
    let h = 0;
    for (let i = 0; i < s.length; i++) h = s.charCodeAt(i) + ((h << 5) - h);
    return COLORS[Math.abs(h) % COLORS.length];
}

function initials(n, a) {
    return ((n || '?')[0] + (a || '?')[0]).toUpperCase();
}

function fmtFecha(iso) {
    if (!iso) return '—';
    const d = new Date(iso);
    return d.toLocaleDateString('es-CO', { month: 'short', day: '2-digit', year: 'numeric' });
}

function rolBadge(r) {
    const rl = (r || 'cliente').toLowerCase();
    if (rl === 'administrador') return '<span class="badge-rol badge-administrador">ADMIN</span>';
    if (rl === 'pro') return '<span class="badge-rol badge-pro">PRO</span>';
    return '<span class="badge-rol badge-cliente">BÁSICO</span>';
}

let todosUsuarios = [], filtroActual = 'todos', paginaActual = 1;
const POR_PAGINA = 25;

function cargarUsuarios() {
    fetch(BASE + '/clientes')
        .then(r => r.json())
        .then(data => { todosUsuarios = data; renderTabla(); })
        .catch(() => {
            document.getElementById('tabla-body').innerHTML =
                '<tr><td colspan="5" style="text-align:center;color:var(--red);padding:32px">Error al cargar usuarios</td></tr>';
        });
}

function filtrados() {
    const q = (document.getElementById('search-input').value || '').toLowerCase();
    return todosUsuarios.filter(u => {
        const mr = filtroActual === 'todos' || (u.rol || 'cliente').toLowerCase() === filtroActual;
        const mq = !q
            || (u.nombre || '').toLowerCase().includes(q)
            || (u.apellido || '').toLowerCase().includes(q)
            || (u.email || '').toLowerCase().includes(q);
        return mr && mq;
    });
}

function renderTabla() {
    const lista = filtrados(), total = lista.length;
    const inicio = (paginaActual - 1) * POR_PAGINA;
    const pagina = lista.slice(inicio, inicio + POR_PAGINA);
    document.getElementById('pag-info').textContent =
        `Mostrando ${total === 0 ? 0 : inicio + 1}–${Math.min(inicio + POR_PAGINA, total)} de ${total} usuarios`;
    if (!pagina.length) {
        document.getElementById('tabla-body').innerHTML =
            '<tr><td colspan="5" style="text-align:center;padding:32px;color:var(--text-muted)">Sin usuarios</td></tr>';
        return;
    }
    document.getElementById('tabla-body').innerHTML = pagina.map(u => {
        const color = colorFor(u.email || u.nombre || '?');
        const ini = initials(u.nombre, u.apellido);
        const dotClass = (u.estado || 'Activo') === 'Inactivo' ? 'dot-inactivo' : 'dot-activo';
        const estado = u.estado || 'Activo';
        const nc = (u.nombre || '') + ' ' + (u.apellido || '');
        return `<tr>
            <td><div class="user-cell">
                <div class="u-avatar" style="background:${color}">${ini}</div>
                <div><div class="u-name">${nc.trim() || u.email}</div><div class="u-email">${u.email || ''}</div></div>
            </div></td>
            <td>${rolBadge(u.rol)}</td>
            <td><span class="status-dot"><span class="dot ${dotClass}"></span>${estado}</span></td>
            <td class="text-muted-sm">${fmtFecha(u.fechaIngreso)}</td>
            <td>
                <button class="action-btn" title="Editar" onclick='abrirModalEditar(${JSON.stringify(u)})'>
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
                </button>
                <button class="action-btn del" title="Eliminar" onclick="eliminarUsuario('${u.idCliente}', '${nc.trim()}')">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/><path d="M10 11v6"/><path d="M14 11v6"/><path d="M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"/></svg>
                </button>
            </td>
        </tr>`;
    }).join('');
}

function setFiltro(f, btn) {
    filtroActual = f;
    paginaActual = 1;
    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    renderTabla();
}

function filtrarTabla() {
    paginaActual = 1;
    renderTabla();
}

function cambiarPag(d) {
    const max = Math.ceil(filtrados().length / POR_PAGINA);
    paginaActual = Math.max(1, Math.min(paginaActual + d, max));
    renderTabla();
}

function abrirModalNuevo() {
    document.getElementById('modal-titulo').textContent = 'Agregar Usuario';
    ['u-id', 'u-nombre', 'u-apellidos', 'u-usuario', 'u-correo', 'u-celular', 'u-password']
        .forEach(id => document.getElementById(id).value = '');
    document.getElementById('campo-password').style.display = '';
    document.getElementById('u-rol').value = 'cliente';
    document.querySelectorAll('.role-chip').forEach(c => c.classList.remove('selected'));
    document.querySelectorAll('.role-chip')[0].classList.add('selected');
    document.querySelector('.btn-save').textContent = 'Crear usuario';
    document.getElementById('modal-overlay').classList.add('open');
}

function abrirModalEditar(u) {
    document.getElementById('modal-titulo').textContent = 'Editar Usuario';
    document.getElementById('u-id').value = u.idCliente || '';
    document.getElementById('u-nombre').value = u.nombre || '';
    document.getElementById('u-apellidos').value = u.apellido || '';
    document.getElementById('u-usuario').value = u.nombre || '';
    document.getElementById('u-correo').value = u.email || '';
    document.getElementById('u-celular').value = u.telefono || '';
    document.getElementById('campo-password').style.display = 'none';
    const rol = (u.rol || 'cliente').toLowerCase();
    document.getElementById('u-rol').value = rol;
    document.querySelectorAll('.role-chip').forEach(c => {
        const mapa = { básico: 'cliente', pro: 'pro', admin: 'administrador' };
        c.classList.toggle('selected', mapa[c.textContent.trim().toLowerCase()] === rol);
    });
    document.querySelector('.btn-save').textContent = 'Guardar cambios';
    document.getElementById('modal-overlay').classList.add('open');
}

function cerrarModal() {
    document.getElementById('modal-overlay').classList.remove('open');
}

function seleccionarRol(rol, btn) {
    document.getElementById('u-rol').value = rol;
    document.querySelectorAll('.role-chip').forEach(c => c.classList.remove('selected'));
    btn.classList.add('selected');
}

function guardarUsuario() {
    const id = document.getElementById('u-id').value;
    const dto = {
        nombre: document.getElementById('u-nombre').value,
        apellido: document.getElementById('u-apellidos').value,
        email: document.getElementById('u-correo').value,
        telefono: document.getElementById('u-celular').value,
        rol: document.getElementById('u-rol').value,
        password: document.getElementById('u-password').value
    };
    if (!dto.email) { mostrarToast('El correo es requerido', 'error'); return; }
    const url = id ? `${BASE}/clientes/${id}` : `${BASE}/clientes`;
    const method = id ? 'PUT' : 'POST';
    fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(dto) })
        .then(r => { if (!r.ok) throw new Error(); return r.json(); })
        .then(nuevo => {
            if (id) {
                const i = todosUsuarios.findIndex(u => u.idCliente === id);
                if (i >= 0) todosUsuarios[i] = nuevo;
            } else {
                todosUsuarios.unshift(nuevo);
            }
            renderTabla();
            cerrarModal();
            mostrarToast(id ? 'Usuario actualizado' : 'Usuario creado', 'success');
        })
        .catch(() => mostrarToast('Error al guardar usuario', 'error'));
}

function eliminarUsuario(id, nombre) {
    if (!confirm(`¿Eliminar a "${nombre}"? Esta acción no se puede deshacer.`)) return;
    fetch(`${BASE}/clientes/${id}`, { method: 'DELETE' })
        .then(r => {
            if (r.ok || r.status === 204) {
                todosUsuarios = todosUsuarios.filter(u => u.idCliente !== id);
                renderTabla();
                mostrarToast('Usuario eliminado', 'success');
            } else throw new Error();
        })
        .catch(() => mostrarToast('Error al eliminar usuario', 'error'));
}

function mostrarToast(msg, tipo) {
    const t = document.getElementById('toast');
    t.textContent = msg;
    t.className = `toast ${tipo} show`;
    setTimeout(() => t.classList.remove('show'), 3000);
}

document.addEventListener('DOMContentLoaded', () => {
    cargarUsuarios();
    document.getElementById('modal-overlay').addEventListener('click', function (e) {
        if (e.target === this) cerrarModal();
    });
});