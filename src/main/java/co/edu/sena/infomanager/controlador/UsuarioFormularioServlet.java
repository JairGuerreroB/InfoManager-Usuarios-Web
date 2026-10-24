package co.edu.sena.infomanager.controlador;

import co.edu.sena.infomanager.excepcion.AccesoDatosException;
import co.edu.sena.infomanager.excepcion.ValidacionException;
import co.edu.sena.infomanager.modelo.EstadoUsuario;
import co.edu.sena.infomanager.modelo.Rol;
import co.edu.sena.infomanager.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Inserción y actualización. GET muestra el formulario vacío (nuevo) o con los datos del usuario
 * (?id=). POST guarda: si llega un id actualiza, si no registra.
 */
@WebServlet("/usuarios/formulario")
public class UsuarioFormularioServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        int idUsuario = leerEntero(solicitud.getParameter("id"));
        try {
            if (idUsuario > 0) {
                Optional<Usuario> usuario = usuarioServicio.buscarPorId(idUsuario);
                if (!usuario.isPresent()) {
                    avisar(solicitud, ERROR, "El usuario solicitado no existe.");
                    redirigir(solicitud, respuesta, "/usuarios");
                    return;
                }
                solicitud.setAttribute("usuario", usuario.get());
            }
            solicitud.setAttribute("roles", usuarioServicio.listarRoles());
        } catch (AccesoDatosException e) {
            solicitud.setAttribute(ERROR, e.getMessage());
        }
        mostrarVista(solicitud, respuesta, "formulario");
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        int idUsuario = leerEntero(solicitud.getParameter("id"));
        String nombre = solicitud.getParameter("nombre");
        String apellido = solicitud.getParameter("apellido");
        String correo = solicitud.getParameter("correo");
        String contrasena = solicitud.getParameter("contrasena");
        int idRol = leerEntero(solicitud.getParameter("idRol"));
        EstadoUsuario estado = leerEstado(solicitud.getParameter("estado"));

        try {
            if (idUsuario > 0) {
                boolean actualizado = usuarioServicio.actualizarUsuario(
                        idUsuario, nombre, apellido, correo, idRol, estado);
                if (actualizado) {
                    avisar(solicitud, MENSAJE, "Usuario actualizado correctamente.");
                } else {
                    avisar(solicitud, ERROR, "El usuario ya no existe.");
                }
            } else {
                usuarioServicio.registrarUsuario(nombre, apellido, correo, contrasena, idRol);
                avisar(solicitud, MENSAJE, "Usuario registrado correctamente.");
            }
            redirigir(solicitud, respuesta, "/usuarios");
            return;
        } catch (ValidacionException | AccesoDatosException e) {
            solicitud.setAttribute(ERROR, e.getMessage());
        }

        // Hubo un error: se vuelve al formulario conservando lo que la persona escribió.
        Usuario digitado = new Usuario();
        digitado.setIdUsuario(idUsuario);
        digitado.setNombre(nombre);
        digitado.setApellido(apellido);
        digitado.setCorreo(correo);
        digitado.setRol(new Rol(idRol, null, null));
        digitado.setEstado(estado);
        solicitud.setAttribute("usuario", digitado);
        try {
            solicitud.setAttribute("roles", usuarioServicio.listarRoles());
        } catch (AccesoDatosException e) {
            solicitud.setAttribute(ERROR, e.getMessage());
        }
        mostrarVista(solicitud, respuesta, "formulario");
    }

    private EstadoUsuario leerEstado(String valor) {
        try {
            return EstadoUsuario.desdeValorBD(valor);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
