package co.edu.sena.infomanager.controlador;

import co.edu.sena.infomanager.excepcion.AccesoDatosException;
import co.edu.sena.infomanager.excepcion.ValidacionException;
import co.edu.sena.infomanager.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** Consulta: lista todos los usuarios o filtra por nombre con un formulario GET (?buscar=texto). */
@WebServlet("/usuarios")
public class UsuarioListarServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        String buscar = solicitud.getParameter("buscar");
        try {
            boolean sinFiltro = buscar == null || buscar.trim().isEmpty();
            List<Usuario> usuarios = sinFiltro
                    ? usuarioServicio.listarUsuarios()
                    : usuarioServicio.buscarPorNombre(buscar);
            solicitud.setAttribute("usuarios", usuarios);
        } catch (ValidacionException | AccesoDatosException e) {
            solicitud.setAttribute(ERROR, e.getMessage());
        }
        solicitud.setAttribute("buscar", buscar);
        mostrarVista(solicitud, respuesta, "listar");
    }
}
