<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="edicion" value="${not empty usuario and usuario.idUsuario > 0}"/>
<jsp:include page="fragmentos/encabezado.jsp">
    <jsp:param name="titulo" value="Usuario"/>
</jsp:include>
<h1>${edicion ? 'Editar usuario' : 'Nuevo usuario'}</h1>
<form class="formulario" action="${ctx}/usuarios/formulario" method="post">
    <input type="hidden" name="id" value="${edicion ? usuario.idUsuario : ''}">

    <label for="nombre">Nombre</label>
    <input id="nombre" name="nombre" type="text" maxlength="50" required value="<c:out value='${usuario.nombre}'/>">

    <label for="apellido">Apellido</label>
    <input id="apellido" name="apellido" type="text" maxlength="50" required value="<c:out value='${usuario.apellido}'/>">

    <label for="correo">Correo</label>
    <input id="correo" name="correo" type="email" maxlength="100" required value="<c:out value='${usuario.correo}'/>">

    <c:if test="${not edicion}">
        <label for="contrasena">Contraseña (mínimo 8 caracteres)</label>
        <input id="contrasena" name="contrasena" type="password" minlength="8" required>
    </c:if>

    <label for="idRol">Rol</label>
    <select id="idRol" name="idRol" required>
        <option value="">Seleccione un rol</option>
        <c:forEach var="r" items="${roles}">
            <option value="${r.idRol}" ${r.idRol == usuario.rol.idRol ? 'selected' : ''}><c:out value="${r.nombreRol}"/></option>
        </c:forEach>
    </select>

    <c:if test="${edicion}">
        <label for="estado">Estado</label>
        <select id="estado" name="estado">
            <option value="activo" ${usuario.estado.valorBD == 'activo' ? 'selected' : ''}>Activo</option>
            <option value="inactivo" ${usuario.estado.valorBD == 'inactivo' ? 'selected' : ''}>Inactivo</option>
        </select>
    </c:if>

    <div class="botones">
        <button type="submit" class="principal">${edicion ? 'Guardar cambios' : 'Registrar usuario'}</button>
        <a href="${ctx}/usuarios">Cancelar</a>
    </div>
</form>
<%@ include file="fragmentos/pie.jspf" %>
