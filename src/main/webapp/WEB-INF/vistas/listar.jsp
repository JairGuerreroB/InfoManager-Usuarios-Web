<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragmentos/encabezado.jsp">
    <jsp:param name="titulo" value="Usuarios"/>
</jsp:include>
<div class="cabecera-seccion">
    <h1>Usuarios</h1>
    <form class="busqueda" action="${ctx}/usuarios" method="get" role="search">
        <label for="buscar">Buscar por nombre</label>
        <input id="buscar" name="buscar" type="search" value="<c:out value='${buscar}'/>">
        <button type="submit">Buscar</button>
        <c:if test="${not empty buscar}"><a href="${ctx}/usuarios">Ver todos</a></c:if>
    </form>
</div>
<c:choose>
    <c:when test="${empty usuarios}">
        <p class="vacio">
            <c:choose>
                <c:when test="${not empty buscar}">Ningún usuario coincide con la búsqueda.</c:when>
                <c:otherwise>Aún no hay usuarios. <a href="${ctx}/usuarios/formulario">Registre el primero.</a></c:otherwise>
            </c:choose>
        </p>
    </c:when>
    <c:otherwise>
        <div class="tabla-envoltura">
            <table>
                <thead>
                    <tr><th>Id</th><th>Nombre</th><th>Correo</th><th>Rol</th><th>Estado</th><th>Registro</th><th>Acciones</th></tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${usuarios}">
                    <tr>
                        <td>${u.idUsuario}</td>
                        <td><c:out value="${u.nombreCompleto}"/></td>
                        <td><c:out value="${u.correo}"/></td>
                        <td><c:out value="${u.rol.nombreRol}"/></td>
                        <td><span class="estado estado-${u.estado.valorBD}">${u.estado.valorBD}</span></td>
                        <td>${u.fechaRegistro.toLocalDate()}</td>
                        <td class="acciones">
                            <a href="${ctx}/usuarios/formulario?id=${u.idUsuario}">Editar</a>
                            <a href="${ctx}/usuarios/contrasena?id=${u.idUsuario}">Cambiar contraseña</a>
                            <form action="${ctx}/usuarios/eliminar" method="post"
                                  onsubmit="return confirm('¿Eliminar este usuario? Esta acción no se puede deshacer.');">
                                <input type="hidden" name="id" value="${u.idUsuario}">
                                <button type="submit" class="peligro">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>
<%@ include file="fragmentos/pie.jspf" %>
