package co.edu.sena.infomanager.controlador;

import co.edu.sena.infomanager.servicio.UsuarioServicio;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Comportamiento común de los servlets del módulo: acceso al servicio, envío a las vistas JSP
 * y avisos que sobreviven a una redirección (patrón Post/Redirect/Get).
 */
public abstract class BaseServlet extends HttpServlet {

    protected static final String MENSAJE = "mensaje";
    protected static final String ERROR = "error";

    private static final String RUTA_VISTAS = "/WEB-INF/vistas/";

    protected final UsuarioServicio usuarioServicio = new UsuarioServicio();

    /** Envía la solicitud a una página JSP ubicada en WEB-INF/vistas. */
    protected void mostrarVista(HttpServletRequest solicitud, HttpServletResponse respuesta, String vista)
            throws ServletException, IOException {
        solicitud.getRequestDispatcher(RUTA_VISTAS + vista + ".jsp").forward(solicitud, respuesta);
    }

    /** Guarda un aviso en la sesión; la página siguiente lo muestra una sola vez. */
    protected void avisar(HttpServletRequest solicitud, String tipo, String texto) {
        solicitud.getSession().setAttribute(tipo, texto);
    }

    protected void redirigir(HttpServletRequest solicitud, HttpServletResponse respuesta, String ruta)
            throws IOException {
        respuesta.sendRedirect(solicitud.getContextPath() + ruta);
    }

    /** Convierte un parámetro en número entero; devuelve 0 si falta o no es válido. */
    protected static int leerEntero(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }
}
