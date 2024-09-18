<%-- 
    Document   : Registro
    Created on : 11/09/2024, 11:49:45 a. m.
    Author     : ESTUDIANTE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro - ImprimeYa</title>
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
                    <li><a href="index.html">Inicio</a></li>
                </ul>
                <a href="index.html" class="Ingresar">Ingresar</a>
            </nav>
        </header>

        <div class="register-container">
            <h2>Registro</h2>
            <% if (request.getAttribute("errorMessage") != null) {%>
            <p style="color: red;"><%= request.getAttribute("errorMessage")%></p>
            <% }%>
            <form action="Controlador" method="POST">
                <input type="hidden" name="accion" value="registro"
                       <div class="input-group">
                    <label for="fullname">Nombre Completo</label>
                    <input type="text" id="fullname" name="fullname" required>
                </div>
                <div class="input-group">
                    <label for="email">Correo Electrónico</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="input-group">
                    <label for="username">telefono</label>
                    <input type="telefono" id="telefono" name="telefono" required>
                </div>
                <div class="input-group">
                    <label for="username">direccion</label>
                    <input type="direccion" id="direccion" name="direccion" required>
                </div>
                <div class="input-group">
                    <label for="username">ciudad</label>
                    <input type="ciudad" id="ciudad" name="ciudad" required>
                </div>
                <div class="input-group">
                    <label for="username">documento</label>
                    <input type="documento" id="documento" name="documento" required>
                </div>
                <div class="input-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="input-group">
                    <label for="confirm-password">Confirmar Contraseña</label>
                    <input type="password" id="confirm-password" name="confirm-password" required>
                </div>
                <button type="submit">Registrarse</button>
            </form>
            <div class="existing-user">
                <p>¿Ya tienes una cuenta? <a href="../pages/login.html">Inicia sesión aquí</a></p>
            </div>
        </div>
    </body>
</html>




