package co.edu.sena.infomanager.controlador;

import co.edu.sena.infomanager.excepcion.AccesoDatosException;
import co.edu.sena.infomanager.excepcion.ValidacionException;
import co.edu.sena.infomanager.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/** Actualización de la contraseña. GET muestra el formulario del usuario (?id=); POST guarda la nueva clave. */
@WebServlet("/usuarios/contrasena")
public class ContrasenaServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        mostrarFormulario(solicitud, respuesta, leerEntero(solicitud.getParameter("id")));
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        int idUsuario = leerEntero(solicitud.getParameter("id"));
        String nueva = solicitud.getParameter("nueva");
        String confirmacion = solicitud.getParameter("confirmacion");

        if (nueva == null || !nueva.equals(confirmacion)) {
            solicitud.setAttribute(ERROR, "Las contraseñas no coinciden.");
            mostrarFormulario(solicitud, respuesta, idUsuario);
            return;
        }
        try {
            if (usuarioServicio.cambiarContrasena(idUsuario, nueva)) {
                avisar(solicitud, MENSAJE, "Contraseña actualizada correctamente.");
            } else {
                avisar(solicitud, ERROR, "El usuario ya no existe.");
            }
            redirigir(solicitud, respuesta, "/usuarios");
        } catch (ValidacionException e) {
            solicitud.setAttribute(ERROR, e.getMessage());
            mostrarFormulario(solicitud, respuesta, idUsuario);
        } catch (AccesoDatosException e) {
            avisar(solicitud, ERROR, e.getMessage());
            redirigir(solicitud, respuesta, "/usuarios");
        }
    }

    private void mostrarFormulario(HttpServletRequest solicitud, HttpServletResponse respuesta, int idUsuario)
            throws ServletException, IOException {
        try {
            Optional<Usuario> usuario = usuarioServicio.buscarPorId(idUsuario);
            if (usuario.isPresent()) {
                solicitud.setAttribute("usuario", usuario.get());
                mostrarVista(solicitud, respuesta, "contrasena");
                return;
            }
            avisar(solicitud, ERROR, "El usuario solicitado no existe.");
        } catch (AccesoDatosException e) {
            avisar(solicitud, ERROR, e.getMessage());
        }
        redirigir(solicitud, respuesta, "/usuarios");
    }
}
