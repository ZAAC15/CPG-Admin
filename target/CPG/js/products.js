const BASE = window.location.pathname.split('/').slice(0, 2).join('/') + '/api';

let todosProductos = [], filtroActual = 'todos', paginaActual = 1;
const POR_PAGINA = 20;

function fmtPrecio(v) {
    return '$' + parseFloat(v || 0).toLocaleString('es-CO', { minimumFractionDigits: 0 }) + ' COP';
}

function insigniaBadge(ins) {
    if (!ins) return '';
    const i = ins.toLowerCase();
    if (i.includes('vend') || i.includes('best')) return '<span class="badge badge-bestseller">MÁS VENDIDA</span>';
    if (i.includes('nuevo') || i.includes('new'))  return '<span class="badge badge-new">NUEVO</span>';
    if (i.includes('stock') || i.includes('agot'))  return '<span class="badge badge-outstock">FUERA DE STOCK</span>';
    return `<span class="badge badge-basic">${ins.toUpperCase()}</span>`;
}

function stockClass(s) {
    if (s === 0) return 'stock-out';
    if (s <= 20) return 'stock-low';
    return 'stock-ok';
}

function cargarProductos() {
    fetch(BASE + '/productos')
        .then(r => r.json())
        .then(data => { todosProductos = data; renderTabla(); })
        .catch(() => {
            document.getElementById('tabla-body').innerHTML =
                '<tr><td colspan="5" style="text-align:center;color:var(--red);padding:32px">Error al cargar productos</td></tr>';
        });
}

function filtrados() {
    const q = (document.getElementById('search-input').value || '').toLowerCase();
    return todosProductos.filter(p => {
        const mc = filtroActual === 'todos' || (p.categoria || '').toLowerCase() === filtroActual;
        const mq = !q || (p.nombre || '').toLowerCase().includes(q)
            || (p.categoria || '').toLowerCase().includes(q);
        return mc && mq;
    });
}

function renderTabla() {
    const lista = filtrados(), total = lista.length;
    const inicio = (paginaActual - 1) * POR_PAGINA;
    const pagina = lista.slice(inicio, inicio + POR_PAGINA);
    document.getElementById('pag-info').textContent =
        `Mostrando ${total === 0 ? 0 : inicio + 1}–${Math.min(inicio + POR_PAGINA, total)} de ${total} productos`;
    if (!pagina.length) {
        document.getElementById('tabla-body').innerHTML =
            '<tr><td colspan="5" style="text-align:center;padding:32px;color:var(--text-muted)">Sin productos</td></tr>';
        return;
    }
    document.getElementById('tabla-body').innerHTML = pagina.map(p => {
        const img = p.imagenUrl
            ? `<img src="${p.imagenUrl}" alt="${p.nombre}" class="prod-img" onerror="this.style.display='none';this.nextElementSibling.style.display='flex'">`
            : '';
        const fallback = `<div class="prod-img-fallback" style="${p.imagenUrl ? 'display:none' : ''}">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="8.5" cy="8.5" r="1.5"/><polyline points="21 15 16 10 5 21"/></svg>
        </div>`;
        return `<tr>
            <td><div class="prod-cell">
                <div class="prod-thumb">${img}${fallback}</div>
                <div>
                    <div class="prod-name">${p.nombre || '—'}</div>
                    <div>${insigniaBadge(p.insignia)}</div>
                </div>
            </div></td>
            <td class="text-muted-sm">${p.categoria || '—'}</td>
            <td><span class="amount-main">${fmtPrecio(p.precio)}</span></td>
            <td><span class="${stockClass(p.stock || 0)}">${p.stock ?? 0}</span> <span class="text-muted-sm">uds</span></td>
            <td>
                <button class="action-btn" title="Editar" onclick='abrirModalEditar(${JSON.stringify(p)})'>
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
                </button>
                <button class="action-btn del" title="Eliminar" onclick="eliminarProducto('${p.id}','${(p.nombre||'').replace(/'/g,"\\'")}')">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/><path d="M10 11v6"/><path d="M14 11v6"/><path d="M9 6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v2"/></svg>
                </button>
            </td>
        </tr>`;
    }).join('');
}

function poblarCategorias() {
    const cats = [...new Set(todosProductos.map(p => p.categoria).filter(Boolean))];
    const cont = document.getElementById('filtros-categorias');
    if (!cont) return;
    cont.innerHTML = '<button class="filter-btn active" onclick="setFiltro(\'todos\', this)">Todos</button>' +
        cats.map(c => `<button class="filter-btn" onclick="setFiltro('${c.toLowerCase()}', this)">${c}</button>`).join('');
}

function setFiltro(f, btn) {
    filtroActual = f;
    paginaActual = 1;
    document.querySelectorAll('#filtros-categorias .filter-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    renderTabla();
}

function filtrarTabla() { paginaActual = 1; renderTabla(); }
function cambiarPag(d) {
    const max = Math.ceil(filtrados().length / POR_PAGINA);
    paginaActual = Math.max(1, Math.min(paginaActual + d, max));
    renderTabla();
}

/* ── MODAL ── */
function abrirModalNuevo() {
    document.getElementById('modal-titulo').textContent = 'Agregar Producto';
    ['p-id','p-nombre','p-descripcion','p-precio','p-categoria','p-insignia','p-imagen','p-stock']
        .forEach(id => document.getElementById(id).value = '');
    document.getElementById('p-activo').checked = true;
    document.querySelector('.btn-save').textContent = 'Crear producto';
    document.getElementById('modal-overlay').classList.add('open');
}

function abrirModalEditar(p) {
    document.getElementById('modal-titulo').textContent = 'Editar Producto';
    document.getElementById('p-id').value        = p.id || '';
    document.getElementById('p-nombre').value    = p.nombre || '';
    document.getElementById('p-descripcion').value = p.descripcion || '';
    document.getElementById('p-precio').value    = p.precio || '';
    document.getElementById('p-categoria').value = p.categoria || '';
    document.getElementById('p-insignia').value  = p.insignia || '';
    document.getElementById('p-imagen').value    = p.imagenUrl || '';
    document.getElementById('p-stock').value     = p.stock ?? 0;
    document.getElementById('p-activo').checked  = p.activo !== false;
    document.querySelector('.btn-save').textContent = 'Guardar cambios';
    document.getElementById('modal-overlay').classList.add('open');
}

function cerrarModal() { document.getElementById('modal-overlay').classList.remove('open'); }

function guardarProducto() {
    const id = document.getElementById('p-id').value;
    const precioVal = document.getElementById('p-precio').value;
    const dto = {
        nombre:      document.getElementById('p-nombre').value,
        descripcion: document.getElementById('p-descripcion').value,
        precio:      precioVal ? parseFloat(precioVal) : null,
        categoria:   document.getElementById('p-categoria').value,
        insignia:    document.getElementById('p-insignia').value,
        imagenUrl:   document.getElementById('p-imagen').value,
        stock:       parseInt(document.getElementById('p-stock').value) || 0,
        activo:      document.getElementById('p-activo').checked
    };
    if (!dto.nombre) { mostrarToast('El nombre es requerido', 'error'); return; }
    const url    = id ? `${BASE}/productos/${id}` : `${BASE}/productos`;
    const method = id ? 'PUT' : 'POST';
    fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(dto) })
        .then(r => { if (!r.ok) throw new Error(); return r.json(); })
        .then(nuevo => {
            if (id) {
                const i = todosProductos.findIndex(p => p.id === id);
                if (i >= 0) todosProductos[i] = nuevo;
            } else {
                todosProductos.unshift(nuevo);
            }
            renderTabla();
            cerrarModal();
            mostrarToast(id ? 'Producto actualizado' : 'Producto creado', 'success');
        })
        .catch(() => mostrarToast('Error al guardar producto', 'error'));
}

function eliminarProducto(id, nombre) {
    if (!confirm(`¿Eliminar "${nombre}"? Esta acción no se puede deshacer.`)) return;
    fetch(`${BASE}/productos/${id}`, { method: 'DELETE' })
        .then(r => {
            if (r.ok || r.status === 204) {
                todosProductos = todosProductos.filter(p => p.id !== id);
                renderTabla();
                mostrarToast('Producto eliminado', 'success');
            } else throw new Error();
        })
        .catch(() => mostrarToast('Error al eliminar producto', 'error'));
}

function mostrarToast(msg, tipo) {
    const t = document.getElementById('toast');
    t.textContent = msg;
    t.className = `toast ${tipo} show`;
    setTimeout(() => t.classList.remove('show'), 3000);
}

document.addEventListener('DOMContentLoaded', () => {
    cargarProductos();
    document.getElementById('modal-overlay').addEventListener('click', function (e) {
        if (e.target === this) cerrarModal();
    });
});