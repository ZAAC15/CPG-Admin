# CPG Admin — Panel Administrativo Camine pal Gym

Panel web de administración para la app móvil **Camine pal Gym**. Permite gestionar en tiempo real los usuarios, productos, órdenes, suscripciones y el foro de la aplicación Android, todo conectado a la misma base de datos en la nube que usa la app.

---

## Contexto del proyecto

**Camine pal Gym** es una aplicación móvil Android para la gestión de un gimnasio. Este panel administrativo web fue desarrollado como complemento para que los administradores puedan gestionar la plataforma desde un navegador, sin necesidad de usar la app móvil.

Tanto la app Android como este panel web comparten la **misma base de datos en Supabase** — cualquier cambio hecho desde el panel se refleja inmediatamente en la app y viceversa. Esto incluye la creación de usuarios, que pasa por el sistema de autenticación de Supabase para mantener la consistencia con los usuarios registrados en la app.

---

## Tecnologías utilizadas

| Capa | Tecnología | Versión |
|---|---|---|
| Lenguaje backend | Java | 17 |
| Servidor de aplicaciones | Apache Tomcat | 11.0.22 |
| Framework REST | Jersey (JAX-RS) | 3.1.3 |
| ORM | EclipseLink (JPA) | 4.0.2 |
| Serialización JSON | Jackson | 2.15.2 |
| Base de datos | PostgreSQL vía Supabase | — |
| Driver JDBC | PostgreSQL Driver | 42.7.3 |
| Gestión de dependencias | Maven | 3.9+ |
| IDE recomendado | IntelliJ IDEA | 2024+ |
| Frontend | JSP + CSS + JavaScript Vanilla | — |
| Autenticación de usuarios | Supabase Admin API | — |

---

## Funcionalidades del panel

### Dashboard
Vista general con métricas en tiempo real:
- Total de usuarios registrados
- Total de productos activos
- Suscripciones activas
- Ganancia mensual acumulada
- Usuarios recientes
- Órdenes recientes

### Gestión de Usuarios
- Listado completo de usuarios registrados en la app Android
- Filtros por rol: Básico, PRO y Admin
- Búsqueda en tiempo real por nombre, apellido o correo
- Crear usuario — se crea directamente en Supabase Auth y se sincroniza con la tabla de usuarios de la app
- Editar nombre, apellidos, usuario, correo, celular y rol
- Eliminar usuario

### Gestión de Productos
- Listado de productos del gimnasio con imagen, categoría, precio y stock
- Filtros dinámicos por categoría
- Crear, editar y eliminar productos
- Soporte para insignias: Nuevo, Más vendida, Fuera de stock

### Ordenes
- Listado de órdenes realizadas desde la app Android
- Muestra el nombre real del cliente mediante JOIN con la tabla de usuarios
- Cantidad real de productos por orden mediante JOIN con `orden_items`
- Fecha y monto total de cada orden

### Moderación del Foro
- Listado de posts publicados por los usuarios desde la app
- Filtros por estado: Todos, Activos y Ocultos
- Contadores en tiempo real por categoría
- Ocultar post — el post deja de ser visible en la app sin eliminarlo
- Mostrar post — reactiva un post oculto
- Eliminar post permanentemente

### Suscripciones
- Vista de suscripciones activas de los usuarios

### Autenticación
- Login con usuario y contraseña para acceso al panel
- Sesión HTTP con cierre de sesión
- Protección de todas las páginas mediante filtro de autenticación

---

## Base de datos compartida con la app Android

El proyecto se conecta a **Supabase**, una plataforma de base de datos PostgreSQL en la nube. Esta es la **misma base de datos** que utiliza la app Android, por lo que los datos son compartidos y sincronizados en tiempo real.

### Tablas principales

| Tabla | Descripción |
|---|---|
| `"Usuarios"` | Usuarios registrados en la app. Vinculada a `auth.users` de Supabase |
| `"Productos"` | Catálogo de productos del gimnasio |
| `ordenes` | Órdenes de compra realizadas desde la app |
| `orden_items` | Detalle de productos de cada orden |
| `"Posts"` | Publicaciones del foro de la app |
| `PostLikes` | Likes de los posts del foro |

### Consideraciones importantes

- La tabla `"Usuarios"` tiene una llave foránea que apunta a `auth.users` de Supabase. Por esto, la creación de usuarios desde el panel usa la **Supabase Admin API** en lugar de un INSERT directo.
- Un **trigger de base de datos** copia automáticamente los datos del nuevo usuario de `auth.users` a la tabla `"Usuarios"` cada vez que se registra alguien desde la app o desde el panel.
- Los IDs en todas las tablas son de tipo `uuid`. El panel usa `CAST(? AS uuid)` en todas las queries nativas para compatibilidad con PostgreSQL.

---

## Requisitos previos

Antes de correr el proyecto de forma local necesitas tener instalado:

- [JDK 17](https://adoptium.net/) o superior
- [Apache Tomcat 11](https://tomcat.apache.org/download-11.cgi)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community o Ultimate)
- Maven (viene incluido en IntelliJ)
- Conexión a internet (para conectar con Supabase)

---

## Instalación y ejecución local

### 1. Clonar el repositorio

```bash
git clone https://github.com/ZAAC15/CPG-Admin.git
cd CPG-Admin
```

### 2. Abrir en IntelliJ IDEA

- File → Open → selecciona la carpeta del proyecto
- Espera que Maven descargue las dependencias automáticamente

### 3. Configurar Tomcat en IntelliJ

1. Run → Edit Configurations → `+` → Tomcat Server → Local
2. En **Application server** selecciona tu instalación de Tomcat 11
3. En la pestaña **Deployment** → `+` → Artifact → selecciona `CPG:war exploded`
4. Cambia el **Application context** a `/CPG`

### 4. Credenciales de base de datos

Las credenciales de conexión a Supabase están configuradas directamente en `JPAUtil.java`. No se requiere ninguna configuración adicional para correr el proyecto localmente.

### 5. Ejecutar

Haz clic en **Run** en IntelliJ. El panel estará disponible en:

```
http://localhost:8080/CPG
```

### 6. Iniciar sesión

```
Usuario:    admin
Contraseña: admin
```

---

## Estructura del proyecto

```
CPG-Admin/
├── src/
│   ├── main/
│   │   ├── java/com/compensar/cpg1/
│   │   │   ├── controller/        # Endpoints REST (Jersey JAX-RS)
│   │   │   ├── DAO/               # Acceso a datos con JPA y SQL nativo
│   │   │   ├── DTO/               # Objetos de transferencia de datos
│   │   │   ├── model/entity/      # Entidades JPA mapeadas a Supabase
│   │   │   ├── service/           # Lógica de negocio
│   │   │   ├── servlet/           # LoginServlet, LogoutServlet, AuthFilter
│   │   │   └── util/              # JPAUtil — gestión del EntityManager
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml
│   │   └── webapp/
│   │       ├── css/
│   │       │   ├── styles.css       # Estilos globales del panel
│   │       │   └── login.css        # Estilos de la pantalla de login
│   │       ├── img/
│   │       ├── js/
│   │       │   ├── index.js
│   │       │   ├── users.js
│   │       │   ├── products.js
│   │       │   ├── orders.js
│   │       │   └── forum.js
│   │       ├── index.jsp
│   │       ├── users.jsp
│   │       ├── products.jsp
│   │       ├── orders.jsp
│   │       ├── forum.jsp
│   │       ├── subscriptions.jsp
│   │       └── login.jsp
└── pom.xml
```

---

## API REST

El panel expone una API REST interna en `/CPG/api/` utilizada por el frontend:

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/dashboard` | Métricas generales |
| GET | `/api/clientes` | Listar usuarios |
| POST | `/api/clientes` | Crear usuario |
| PUT | `/api/clientes/{id}` | Editar usuario |
| DELETE | `/api/clientes/{id}` | Eliminar usuario |
| GET | `/api/productos` | Listar productos |
| POST | `/api/productos` | Crear producto |
| PUT | `/api/productos/{id}` | Editar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| GET | `/api/pedidos` | Listar ordenes |
| GET | `/api/posts` | Listar posts del foro |
| PUT | `/api/posts/{id}/ocultar` | Ocultar post |
| PUT | `/api/posts/{id}/mostrar` | Mostrar post |
| DELETE | `/api/posts/{id}` | Eliminar post |

---

## Notas técnicas

- El proyecto compila con Java 17 aunque el JDK instalado sea superior.
- Se usa SQL nativo con `CAST(? AS uuid)` en todas las operaciones de escritura para compatibilidad con el tipo `uuid` de PostgreSQL.
- La creación de usuarios usa la Supabase Admin API junto con un trigger de base de datos para mantener la integridad referencial con `auth.users`.
- Las credenciales de base de datos están configuradas en `JPAUtil.java`. Opcionalmente pueden sobreescribirse con variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`) para despliegues en producción.
