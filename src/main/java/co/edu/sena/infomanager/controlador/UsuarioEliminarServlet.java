package co.edu.sena.infomanager.controlador;

import co.edu.sena.infomanager.excepcion.AccesoDatosException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Eliminación. Solo ejecuta con POST, porque borrar datos no debe poder hacerse con solo abrir un enlace. */
@WebServlet("/usuarios/eliminar")
public class UsuarioEliminarServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        redirigir(solicitud, respuesta, "/usuarios");
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        int idUsuario = leerEntero(solicitud.getParameter("id"));
        try {
            if (usuarioServicio.eliminarUsuario(idUsuario)) {
                avisar(solicitud, MENSAJE, "Usuario eliminado correctamente.");
            } else {
                avisar(solicitud, ERROR, "El usuario ya no existe.");
            }
        } catch (AccesoDatosException e) {
            avisar(solicitud, ERROR, e.getMessage());
        }
        redirigir(solicitud, respuesta, "/usuarios");
    }
}
