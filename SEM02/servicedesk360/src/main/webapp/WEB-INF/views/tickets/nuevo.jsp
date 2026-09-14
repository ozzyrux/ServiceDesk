<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo ticket | ServiceDesk 360</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilos.css">
</head>
<body>
<main class="contenedor-formulario">
    <h1>Registrar ticket de soporte</h1>
    <c:if test="${not empty errores}">
        <div class="alerta error">
            <ul>
                <c:forEach var="error" items="${errores}">
                    <li><c:out value="${error}" /></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/tickets/nuevo">
        <div class="grupo-campo">
            <label for="titulo">Título</label>
            <input id="titulo" type="text" name="titulo" maxlength="100"
                   value="${tituloAnterior}" required>
        </div>
        <div class="grupo-campo">
            <label for="descripcion">Descripción</label>
            <textarea id="descripcion" name="descripcion" rows="6" required><c:out value="${descripcionAnterior}" /></textarea>
        </div>
        <div class="grupo-campo">
            <label for="prioridad">Prioridad</label>
            <select id="prioridad" name="prioridad" required>
                <option value="">Seleccione</option>
                <option value="BAJA">Baja</option>
                <option value="MEDIA">Media</option>
                <option value="ALTA">Alta</option>
                <option value="CRITICA">Crítica</option>
            </select>
        </div>
        <div class="acciones-formulario">
            <button type="submit">Registrar ticket</button>
        </div>
    </form>
    <p><a href="${pageContext.request.contextPath}/tickets">Volver al listado</a></p>
</main>
</body>
</html>
