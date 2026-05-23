const BASE = window.location.pathname.split('/').slice(0, 2).join('/') + '/api';

const COLORS = ['#3b82f6','#8b5cf6','#06b6d4','#f59e0b','#10b981','#ef4444','#f97316'];
function colorFor(s) {
    let h = 0;
    for (let i = 0; i < s.length; i++) h = s.charCodeAt(i) + ((h << 5) - h);
    return COLORS[Math.abs(h) % COLORS.length];
}
function initials(nombre) {
    if (!nombre) return '?';
    const parts = nombre.trim().split(' ');
    return parts.length >= 2
        ? (parts[0][0] + parts[parts.length - 1][0]).toUpperCase()
        : nombre.substring(0, 2).toUpperCase();
}
function fmtFecha(iso) {
    if (!iso) return '—';
    return new Date(iso).toLocaleDateString('es-CO', {
        day: '2-digit', month: 'short', year: 'numeric'
    });
}
function fmtMonto(v) {
    return '$' + parseFloat(v || 0).toLocaleString('es-CO', { minimumFractionDigits: 0 }) + ' COP';
}

let todasOrdenes = [], filtroActual = 'todos', paginaActual = 1;
const POR_PAGINA = 20;

function cargarOrdenes() {
    fetch(BASE + '/pedidos')
        .then(r => r.json())
        .then(data => { todasOrdenes = data; renderTabla(); })
        .catch(() => {
            document.getElementById('tabla-body').innerHTML =
                '<tr><td colspan="5" style="text-align:center;color:var(--red);padding:32px">Error al cargar órdenes</td></tr>';
        });
}

function filtrados() {
    const q = (document.getElementById('search-input').value || '').toLowerCase();
    return todasOrdenes.filter(o => {
        const mq = !q
            || (o.idPedido || '').toLowerCase().includes(q)
            || (o.nombreCliente || '').toLowerCase().includes(q);
        return mq;
    });
}

function renderTabla() {
    const lista = filtrados(), total = lista.length;
    const inicio = (paginaActual - 1) * POR_PAGINA;
    const pagina = lista.slice(inicio, inicio + POR_PAGINA);
    document.getElementById('pag-info').textContent =
        `Mostrando ${total === 0 ? 0 : inicio + 1}–${Math.min(inicio + POR_PAGINA, total)} de ${total} órdenes`;
    if (!pagina.length) {
        document.getElementById('tabla-body').innerHTML =
            '<tr><td colspan="5" style="text-align:center;padding:32px;color:var(--text-muted)">Sin órdenes</td></tr>';
        return;
    }
    document.getElementById('tabla-body').innerHTML = pagina.map(o => {
        const cliente = o.nombreCliente || 'Cliente';
        const color   = colorFor(o.idCliente || cliente);
        const ini     = initials(cliente);
        const idCorto = o.idPedido ? 'ORD-' + o.idPedido.substring(0, 8).toUpperCase() : '—';
        const items   = o.cantidadItems || 0;
        return `<tr>
            <td><span class="order-id-tag mono">${idCorto}</span></td>
            <td><div class="row-avatar">
                <div class="av-circle" style="background:${color}">${ini}</div>
                <div><span class="av-name">${cliente}</span>
                     <span class="av-email">${items} ${items === 1 ? 'producto' : 'productos'}</span></div>
            </div></td>
            <td class="text-muted-sm">${fmtFecha(o.fechaPedido)}</td>
            <td><span class="status-pill pill-completed">Completado</span></td>
            <td style="text-align:right">
                <span class="amount-main">${fmtMonto(o.total)}</span>
            </td>
        </tr>`;
    }).join('');
}

function filtrarTabla() { paginaActual = 1; renderTabla(); }
function cambiarPag(d) {
    const max = Math.ceil(filtrados().length / POR_PAGINA);
    paginaActual = Math.max(1, Math.min(paginaActual + d, max));
    renderTabla();
}

document.addEventListener('DOMContentLoaded', cargarOrdenes);