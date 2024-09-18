<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicio de Sesión - ImprimeYa</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <header>
            <nav class="navbar">
                <div class="logo">
                    <img src="logo.png" alt="ImprimeYa Logo">
                    <span>ImprimeYa</span>
                </div>
                <ul class="nav-links">
                    <li><a href="index.jsp">Inicio</a></li>
                </ul>
                <a href="login.html" class="Ingresar">Ingresar</a>
            </nav>
        </header>

        <div class="login-container">
            <% if (request.getAttribute("errorMessage") != null) {%>
            <p style="color: red;"><%= request.getAttribute("errorMessage")%></p>
            <% }%>
            <form action="Controlador" method="POST">
                <input type="hidden" name="accion" value="login">                
                <div class="input-group">
                    <label for="email">Usuario</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="input-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Ingresar </button>
            </form>

            <div class="new-user">
                <p>¿Nuevo usuario? <a href="registro.jsp">Regístrate aquí</a></p>
            </div>

        </div>
    </body>
</html>