# InfoManager System — Módulo de Usuarios (Web con Servlets y JSP)

Versión web del módulo de usuarios de InfoManager System. Reutiliza la capa de acceso a datos de la evidencia
anterior (JDBC + MySQL) y agrega servlets, formularios HTML (GET y POST) y páginas JSP.

Evidencia **GA7-220501096-AA2-EV02** — Módulos de software codificados y probados.
Aprendiz: Jair Guerrero · Ficha 3336142 · Instructor: Juan Hernández.

## Requisitos

- JDK 11 o superior
- MySQL Server 8.x con la base `infomanager_db` (los mismos scripts de la evidencia anterior)
- Apache Tomcat 9.0.x (Tomcat 10 o superior **no** sirve: usa `jakarta.servlet` en lugar de `javax.servlet`)
- Maven (o un IDE con Maven, como NetBeans, IntelliJ o Eclipse)

## Puesta en marcha

1. **Base de datos.** En MySQL Workbench ejecute en orden `database/01_infomanager_system_script.sql` y
   `database/02_datos_iniciales.sql` (crea las tablas y los roles Administrador y Usuario).
2. **Conexión.** En `src/main/resources/` copie `db.properties.example` como `db.properties` y escriba la
   contraseña de MySQL en `db.clave`. Ese archivo no se sube a GitHub.
3. **Generar el WAR.** Dentro de esta carpeta: `mvn clean package`. Se crea `target/infomanager.war`.
4. **Desplegar en Tomcat.** Copie `infomanager.war` en la carpeta `webapps` de Tomcat e inicie Tomcat
   (`bin/startup.bat` en Windows).
5. **Abrir** http://localhost:8080/infomanager/

Para detener Tomcat: `bin/shutdown.bat`.

## Qué cubre la evidencia

| Requisito | Dónde está |
|---|---|
| Formularios HTML con servlets | `formulario.jsp`, `login.jsp`, `contrasena.jsp` y `listar.jsp` (búsqueda y eliminar) |
| Método GET | Listar y buscar (`/usuarios?buscar=`), abrir formularios (`?id=`), cerrar sesión (`/login?salir=1`) |
| Método POST | Registrar y actualizar, cambiar contraseña, eliminar, iniciar sesión |
| Elementos de JSP | Directivas `page`, `include` y `taglib`; acciones `jsp:include` y `jsp:param`; JSTL (`c:forEach`, `c:if`, `c:choose`, `c:set`, `c:remove`, `c:out`, `c:redirect`); lenguaje de expresiones (EL); declaración, scriptlet y expresión en `pie.jspf`; comentarios JSP |
| Inserción, consulta, actualización, eliminación | `UsuarioFormularioServlet`, `UsuarioListarServlet`, `UsuarioEliminarServlet` |

## Rutas

| Ruta | Servlet | GET | POST |
|---|---|---|---|
| `/usuarios` | `UsuarioListarServlet` | Lista o busca por nombre | — |
| `/usuarios/formulario` | `UsuarioFormularioServlet` | Formulario nuevo o con `?id=` | Registra o actualiza |
| `/usuarios/contrasena` | `ContrasenaServlet` | Formulario de `?id=` | Cambia la contraseña |
| `/usuarios/eliminar` | `UsuarioEliminarServlet` | Redirige a la lista | Elimina |
| `/login` | `LoginServlet` | Formulario, o cierra sesión con `?salir=1` | Valida credenciales |

Las páginas JSP están en `WEB-INF/vistas`, por lo que solo se alcanzan a través de un servlet.
Tras guardar o eliminar, el servlet redirige a la lista (patrón Post/Redirect/Get) para que recargar la página
no repita la operación.

## Estructura

```
InfoManager-Usuarios-Web/
├── pom.xml
├── database/        Scripts SQL
├── docs/            Artefactos del proyecto (diagramas y documento)
└── src/main/
    ├── java/co/edu/sena/infomanager/
    │   ├── controlador/  Servlets
    │   ├── filtro/       CodificacionFilter (UTF-8)
    │   ├── servicio/     UsuarioServicio (validaciones y reglas)
    │   ├── dao/          UsuarioDAO, RolDAO (JDBC)
    │   ├── conexion/     ConexionBD
    │   ├── modelo/       Usuario, Rol, EstadoUsuario
    │   ├── excepcion/    ValidacionException, AccesoDatosException
    │   └── util/         ContrasenaUtil (PBKDF2)
    ├── resources/        db.properties.example
    └── webapp/           index.jsp, css/, WEB-INF/vistas/ (JSP)
```

Las contraseñas se guardan cifradas (PBKDF2 con sal), el correo es único y solo los usuarios activos pueden
iniciar sesión.
