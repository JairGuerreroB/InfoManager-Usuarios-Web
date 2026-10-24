<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragmentos/encabezado.jsp">
    <jsp:param name="titulo" value="Iniciar sesión"/>
</jsp:include>
<h1>Iniciar sesión</h1>
<form class="formulario" action="${ctx}/login" method="post">
    <label for="correo">Correo</label>
    <input id="correo" name="correo" type="email" required autofocus value="<c:out value='${correo}'/>">

    <label for="contrasena">Contraseña</label>
    <input id="contrasena" name="contrasena" type="password" required>

    <div class="botones">
        <button type="submit" class="principal">Entrar</button>
        <a href="${ctx}/usuarios/formulario">Crear un usuario</a>
    </div>
</form>
<%@ include file="fragmentos/pie.jspf" %>
