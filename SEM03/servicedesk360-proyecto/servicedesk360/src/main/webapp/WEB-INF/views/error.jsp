<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error controlado | ServiceDesk 360</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilos.css">
</head>
<body>
<main class="contenedor-formulario">
    <h1>No fue posible completar la operación</h1>
    <p><c:out value="${mensajeError}" /></p>
    <a href="${pageContext.request.contextPath}/panel">Volver al panel</a>
</main>
</body>
</html>
