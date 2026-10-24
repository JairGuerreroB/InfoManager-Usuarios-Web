package co.edu.sena.infomanager.controlador;

import co.edu.sena.infomanager.excepcion.AccesoDatosException;
import co.edu.sena.infomanager.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/** Inicio y cierre de sesión. GET muestra el formulario (o cierra la sesión); POST valida las credenciales. */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    private static final String USUARIO_ACTIVO = "usuarioActivo";

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        if (solicitud.getParameter("salir") != null) {
            HttpSession sesion = solicitud.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
            redirigir(solicitud, respuesta, "/login");
            return;
        }
        mostrarVista(solicitud, respuesta, "login");
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        String correo = solicitud.getParameter("correo");
        String contrasena = solicitud.getParameter("contrasena");
        try {
            Optional<Usuario> autenticado = usuarioServicio.autenticar(correo, contrasena);
            if (autenticado.isPresent()) {
                Usuario usuario = autenticado.get();
                usuario.setContrasena(null); // el hash no debe quedar en la sesión
                solicitud.changeSessionId();
                solicitud.getSession().setAttribute(USUARIO_ACTIVO, usuario);
                avisar(solicitud, MENSAJE, "Bienvenido, " + usuario.getNombre() + ".");
                redirigir(solicitud, respuesta, "/usuarios");
                return;
            }
            solicitud.setAttribute(ERROR, "Correo o contraseña incorrectos, o el usuario está inactivo.");
        } catch (AccesoDatosException e) {
            solicitud.setAttribute(ERROR, e.getMessage());
        }
        solicitud.setAttribute("correo", correo);
        mostrarVista(solicitud, respuesta, "login");
    }
}
