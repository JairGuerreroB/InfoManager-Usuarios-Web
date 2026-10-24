<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${param.titulo}"/> - InfoManager</title>
    <link rel="stylesheet" href="${ctx}/css/estilos.css">
</head>
<body>
<header class="barra">
    <a class="marca" href="${ctx}/usuarios">InfoManager</a>
    <nav>
        <a href="${ctx}/usuarios">Usuarios</a>
        <a href="${ctx}/usuarios/formulario">Nuevo usuario</a>
        <c:choose>
            <c:when test="${not empty sessionScope.usuarioActivo}">
                <span class="sesion"><c:out value="${sessionScope.usuarioActivo.nombreCompleto}"/></span>
                <a href="${ctx}/login?salir=1">Cerrar sesión</a>
            </c:when>
            <c:otherwise>
                <a href="${ctx}/login">Iniciar sesión</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>
<main>
<%-- Avisos: los de sesión sobreviven a una redirección y se muestran una sola vez --%>
<c:if test="${not empty sessionScope.mensaje}">
    <p class="aviso aviso-ok" role="status"><c:out value="${sessionScope.mensaje}"/></p>
    <c:remove var="mensaje" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.error}">
    <p class="aviso aviso-error" role="alert"><c:out value="${sessionScope.error}"/></p>
    <c:remove var="error" scope="session"/>
</c:if>
<c:if test="${not empty requestScope.error}">
    <p class="aviso aviso-error" role="alert"><c:out value="${requestScope.error}"/></p>
</c:if>
