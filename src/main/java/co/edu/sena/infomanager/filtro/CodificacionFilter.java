package co.edu.sena.infomanager.filtro;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Fija UTF-8 en todas las solicitudes para que tildes y la letra ñ lleguen bien desde los formularios.
 */
@WebFilter("/*")
public class CodificacionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest solicitud, ServletResponse respuesta, FilterChain cadena)
            throws IOException, ServletException {
        solicitud.setCharacterEncoding("UTF-8");
        respuesta.setCharacterEncoding("UTF-8");
        cadena.doFilter(solicitud, respuesta);
    }
}
