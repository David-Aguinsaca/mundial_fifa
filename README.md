# Mundial FIFA — Sistema de Gestión

ERP para la administración de torneos, selecciones, partidos y estadísticas del Mundial FIFA.  
Desarrollado en **Java 21 SE** con interfaz **Swing** y base de datos **PostgreSQL** vía **JDBC**, en **Eclipse IDE** (sin Maven/Gradle).

## Clonar e iniciar el proyecto

```bash
# Clonar el repositorio
git clone <url-del-repositorio>
cd mundial_fifa

# (Opcional) Inicializar Git en un proyecto existente
git init
git add .
git commit -m "feat: estructura inicial del proyecto"

# Configurar la base de datos
cp config.properties.example config.properties   # si existe el ejemplo
# Editar config.properties con tus credenciales de PostgreSQL

# Compilar
javac -d bin -p lib/postgresql-42.7.2.jar $(find src -name '*.java')

# Ejecutar
java -p bin:lib/postgresql-42.7.2.jar -m mundial_fifa/mundial_fifa.controller.Main
```

> **Nota:** `config.properties` está en `.gitignore` para no subir credenciales al repositorio. Cada desarrollador debe crear su propio archivo con los datos de su base de datos local.

## Estructura del proyecto

```
mundial_fifa/
├── src/
│   ├── module-info.java                          # Módulo Java: java.desktop + java.sql
│   └── mundial_fifa/
│       ├── controller/
│       │   ├── Main.java                         # Punto de entrada Swing
│       │   ├── ContinenteController.java         # Controlador CRUD Continente
│       │   └── TestConnection.java               # Prueba de conexión a BD
│       ├── model/
│       │   ├── entity/
│       │   │   ├── Base.java                     # Clase base: estado, fechaCreacion, fechaModificacion
│       │   │   ├── Continente.java               # Entidad Continente
│       │   │   └── Conferacion.java              # Stub
│       │   ├── repository/
│       │   │   ├── CrudRepository.java           # Interfaz genérica CRUD <T, ID>
│       │   │   ├── ContinenteRepository.java     # Implementación CRUD para Continente
│       │   │   └── DatabaseConnection.java       # Singleton de conexión JDBC
│       │   └── service/
│       │       └── ContinenteService.java        # Lógica de negocio de Continente
│       └── view/
│           ├── MainLayout.java                  # JFrame principal con sidebar + panel central
│           └── component/
│               ├── BotonSidebar.java            # Botón personalizado para la barra lateral
│               ├── ContinentePanel.java         # Panel CRUD de Continentes
│               ├── EstadoCeldaRenderer.java     # Renderer para columna estado (Activo/Inactivo)
│               └── FechaCeldaRenderer.java      # Renderer para columna fecha (yyyy-MM-dd HH:mm)
├── bin/                                          # Compilados .class
├── lib/
│   └── postgresql-42.7.2.jar                     # Driver JDBC PostgreSQL
├── config.properties                             # Configuración de conexión a la BD
├── AGENTS.md                                     # Instrucciones para asistentes IA
└── .classpath / .project                         # Configuración de Eclipse
```

## Requisitos

- **Java 21 SE** (JDK 21+)
- **PostgreSQL** corriendo en `localhost:5432`
- Esquema `mundial_fifa` creado en la base de datos

## Configuración

Editar `config.properties` en la raíz del proyecto:

```properties
db.url=jdbc:postgresql://localhost:5432/administracion_bd
db.user=app_user
db.password=app_password
```

## Compilar

```bash
javac -d bin -p lib/postgresql-42.7.2.jar $(find src -name '*.java')
```

## Ejecutar

| Acción | Comando |
|--------|---------|
| Iniciar aplicación Swing | `java -p bin:lib/postgresql-42.7.2.jar -m mundial_fifa/mundial_fifa.controller.Main` |
| Probar conexión a BD | `java -p bin:lib/postgresql-42.7.2.jar -m mundial_fifa/mundial_fifa.controller.TestConnection` |

## Arquitectura

```
[ Vista (Swing) ] → [ Controlador ] → [ Servicio ] → [ Repositorio (JDBC) ] → [ PostgreSQL ]
```

- **Vista**: Paneles Swing reutilizables con tablas, botones y renderers personalizados.
- **Controlador**: Conecta la vista con los servicios y maneja eventos de UI.
- **Servicio**: Capa de lógica de negocio con validaciones.
- **Repositorio**: Implementación genérica CRUD mediante `PreparedStatement` con manejo de excepciones vía `RuntimeException`.

## Convenciones

- Las IDs de entidades usan snake_case → camelCase (`id_continente` → `idContinente`).
- Las consultas SQL usan esquema calificado (`mundial_fifa.continente`).
- Los repositorios propagan errores SQL como `RuntimeException`.
- `DatabaseConnection` es un singleton estático que lee `config.properties` al iniciar y cachea la conexión.
