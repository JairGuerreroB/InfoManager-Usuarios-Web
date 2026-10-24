<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragmentos/encabezado.jsp">
    <jsp:param name="titulo" value="Cambiar contraseña"/>
</jsp:include>
<h1>Cambiar contraseña</h1>
<p>Usuario: <strong><c:out value="${usuario.nombreCompleto}"/></strong> (<c:out value="${usuario.correo}"/>)</p>
<form class="formulario" action="${ctx}/usuarios/contrasena" method="post">
    <input type="hidden" name="id" value="${usuario.idUsuario}">

    <label for="nueva">Nueva contraseña (mínimo 8 caracteres)</label>
    <input id="nueva" name="nueva" type="password" minlength="8" required autofocus>

    <label for="confirmacion">Repita la contraseña</label>
    <input id="confirmacion" name="confirmacion" type="password" minlength="8" required>

    <div class="botones">
        <button type="submit" class="principal">Guardar contraseña</button>
        <a href="${ctx}/usuarios">Cancelar</a>
    </div>
</form>
<%@ include file="fragmentos/pie.jspf" %>
