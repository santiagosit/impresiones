<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notificaciones - ImprimeYa</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <header>
        <nav class="navbar">
            <div class="logo">
                <img src="logo.png" alt="">
                <span>ImprimeYa</span>
            </div>
            <ul class="nav-links">
                <li><a href="index.jsp">Inicio</a></li>
            </ul>
            <a href="index.jsp" class="Ingresar">Ingresar</a>
        </nav>
    </header>
    
    <div class="container mt-4">
        <h3>Notificaciones</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>ID Notificación</th>
                    <th>ID Orden</th>
                    <th>Fecha de Orden</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="notificacion" items="${notificaciones}">
                    <tr>
                        <td>${notificacion.idNotificacion}</td>
                        <td>${notificacion.idOrden}</td>
                        <td>${notificacion.fechaOrden}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
    