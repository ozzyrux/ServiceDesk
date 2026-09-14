<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tickets | ServiceDesk 360</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilos.css">
</head>
<body>
<main class="panel-usuario">
    <h1>Tickets de soporte</h1>
    <c:if test="${not empty mensajeExito}">
        <div class="alerta exito"><c:out value="${mensajeExito}" /></div>
    </c:if>
    <p><a class="enlace-boton" href="${pageContext.request.contextPath}/tickets/nuevo">Abrir nuevo ticket</a></p>
    <table>
        <thead>
        <tr>
            <th>ID</th><th>Título</th><th>Solicitante</th><th>Prioridad</th><th>Estado</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ticket" items="${tickets}">
            <tr>
                <td><c:out value="${ticket.id}" /></td>
                <td><c:out value="${ticket.titulo}" /></td>
                <td><c:out value="${ticket.solicitante.nombreCompleto}" /></td>
                <td><c:out value="${ticket.prioridad}" /></td>
                <td><c:out value="${ticket.estado}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
</body>
</html>
