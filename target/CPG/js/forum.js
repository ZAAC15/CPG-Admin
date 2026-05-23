const BASE = window.location.pathname.split('/').slice(0, 2).join('/') + '/api';

const COLORS = ['#3b82f6','#8b5cf6','#06b6d4','#f59e0b','#10b981','#ef4444','#f97316'];
function colorFor(s) {
    let h = 0;
    for (let i = 0; i < (s||'').length; i++) h = s.charCodeAt(i) + ((h << 5) - h);
    return COLORS[Math.abs(h) % COLORS.length];
}
function initials(nombre) {
    if (!nombre) return '?';
    const p = nombre.trim().split(' ');
    return p.length >= 2 ? (p[0][0] + p[p.length-1][0]).toUpperCase() : nombre.substring(0,2).toUpperCase();
}
function fmtFecha(iso) {
    if (!iso) return '—';
    return new Date(iso).toLocaleDateString('es-CO', { day:'2-digit', month:'short', year:'numeric' });
}
function truncar(txt, n) {
    if (!txt) return '';
    return txt.length > n ? txt.substring(0, n) + '…' : txt;
}

let todosPosts = [], filtroActual = 'todos', paginaActual = 1;
const POR_PAGINA = 15;

function cargarPosts() {
    fetch(BASE + '/posts')
        .then(r => r.json())
        .then(data => { todosPosts = data; renderTabla(); })
        .catch(() => {
            document.getElementById('tabla-body').innerHTML =
                '<tr><td colspan="6" style="text-align:center;color:var(--red);padding:32px">Error al cargar posts</td></tr>';
        });
}

function filtrados() {
    const q = (document.getElementById('search-input').value || '').toLowerCase();
    return todosPosts.filter(p => {
        const mf = filtroActual === 'todos'
            || (filtroActual === 'activo'  &&  p.activo)
            || (filtroActual === 'oculto'  && !p.activo);
        const mq = !q
            || (p.titulo   || '').toLowerCase().includes(q)
            || (p.nombre   || '').toLowerCase().includes(q)
            || (p.etiqueta || '').toLowerCase().includes(q);
        return mf && mq;
    });
}

function renderTabla() {
    const lista = filtrados(), total = lista.length;
    const inicio = (paginaActual - 1) * POR_PAGINA;
    const pagina = lista.slice(inicio, inicio + POR_PAGINA);
    document.getElementById('pag-info').textContent =
        `Mostrando ${total === 0 ? 0 : inicio + 1}–${Math.min(inicio + POR_PAGINA, total)} de ${total} posts`;

    // Actualizar contadores de filtro
    document.getElementById('count-todos').textContent   = todosPosts.length;
    document.getElementById('count-activos').textContent = todosPosts.filter(p => p.activo).length;
    document.getElementById('count-ocultos').textContent = todosPosts.filter(p => !p.activo).length;

    if (!pagina.length) {
        document.getElementById('tabla-body').innerHTML =
            '<tr><td colspan="6" style="text-align:center;padding:32px;color:var(--text-muted)">Sin posts</td></tr>';
        return;
    }
    document.getElementById('tabla-body').innerHTML = pagina.map(p => {
        const color   = colorFor(p.nombre || p.usuarioId || '?');
        const ini     = initials(p.nombre);
        const activo  = p.activo !== false;
        const estadoBadge = activo
            ? '<span class="status-pill pill-active">Activo</span>'
            : '<span class="status-pill pill-expired">Oculto</span>';
        const etiquetaHtml = p.etiqueta
            ? `<span class="badge badge-basic" style="margin-left:6px">${p.etiqueta}</span>`
            : '';
        const toggleBtn = activo
            ? `<button class="action-btn" title="Ocultar" onclick="togglePost('${p.id}', false)">
                <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/><path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
               </button>`
            : `<button class="action-btn" title="Mostrar" onclick="togglePost('${p.id}', true)">
                <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
               </button>`;
        return `<tr style="${activo ? '' : 'opacity:0.55'}">
            <td><div class="row-avatar">
                <div class="av-circle" style="background:${color}">${ini}</div>
                <div><span class="av-name">${p.nombre || 'Usuario'}</span>
                     <span class="av-email">${fmtFecha(p.createdAt)}</span></div>
            </div></td>
            <td>
                <div style="font-weight:600;font-size:13.5px;margin-bottom:3px">${truncar(p.titulo, 60)}</div>
                <div class="text-muted-sm">${truncar(p.contenido, 80)}</div>
            </td>
            <td>${etiquetaHtml}</td>
            <td><span class="mono text-muted-sm">♥ ${p.likes || 0}</span></td>
            <td>${estadoBadge}</td>
            <td>
                ${toggleBtn}
                <button class="action-btn del" title="Eliminar" onclick="eliminarPost('${p.id}','${(p.titulo||'').replace(/'/g,"\\'")}')">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/><path d="M10 11v6"/><path d="M14 11v6"/><path d="M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"/></svg>
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
function filtrarTabla() { paginaActual = 1; renderTabla(); }
function cambiarPag(d) {
    const max = Math.ceil(filtrados().length / POR_PAGINA);
    paginaActual = Math.max(1, Math.min(paginaActual + d, max));
    renderTabla();
}

function togglePost(id, activo) {
    const endpoint = activo ? 'mostrar' : 'ocultar';
    fetch(`${BASE}/posts/${id}/${endpoint}`, { method: 'PUT' })
        .then(r => { if (!r.ok) throw new Error(); return r.json(); })
        .then(() => {
            const p = todosPosts.find(p => p.id === id);
            if (p) p.activo = activo;
            renderTabla();
            mostrarToast(activo ? 'Post visible' : 'Post ocultado', 'success');
        })
        .catch(() => mostrarToast('Error al actualizar post', 'error'));
}

function eliminarPost(id, titulo) {
    if (!confirm(`¿Eliminar el post "${titulo}"? Esta acción no se puede deshacer.`)) return;
    fetch(`${BASE}/posts/${id}`, { method: 'DELETE' })
        .then(r => {
            if (r.ok || r.status === 204) {
                todosPosts = todosPosts.filter(p => p.id !== id);
                renderTabla();
                mostrarToast('Post eliminado', 'success');
            } else throw new Error();
        })
        .catch(() => mostrarToast('Error al eliminar post', 'error'));
}

function mostrarToast(msg, tipo) {
    const t = document.getElementById('toast');
    t.textContent = msg;
    t.className = `toast ${tipo} show`;
    setTimeout(() => t.classList.remove('show'), 3000);
}

document.addEventListener('DOMContentLoaded', cargarPosts);