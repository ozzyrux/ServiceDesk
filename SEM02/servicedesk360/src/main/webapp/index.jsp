<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ServiceDesk 360</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilos.css">
</head>
<body>
<header class="app-header">
    <a href="${pageContext.request.contextPath}/">ServiceDesk 360</a>
</header>
<main class="contenido-inicio">
    <h1>Sistema Empresarial Multiplataforma</h1>
    <p>Caso modelo: TecnoSoporte Centroamérica. Registre una cuenta o inicie acceso
        para gestionar tickets de soporte.</p>
    <div class="acciones-formulario">
        <a class="enlace-boton" href="${pageContext.request.contextPath}/registro">Crear cuenta temporal</a>
        <a class="enlace-boton" href="${pageContext.request.contextPath}/acceso">Iniciar acceso</a>
    </div>
</main>
</body>
</html>
