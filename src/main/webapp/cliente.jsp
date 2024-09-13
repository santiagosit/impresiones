<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
    String emailUsuario = (String) session.getAttribute("email");
    if (emailUsuario == null) {
        out.println("No estás autenticado.");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cliente - ImprimeYa</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <header>
            <nav class="navbar">
                <div class="logo">
                    <img src="logo.png" alt="Logo ImprimeYa">
                    <span>ImprimeYa</span>
                </div>
                <ul class="nav-links">
                    <li><a href="index.html">Inicio</a></li>
                </ul>
                <a href="index.html" class="Ingresar">Ingresar</a>
            </nav>
        </header>

        <div class="container">
            <div class="upload-section">
                
                <form id="uploadForm" action="Controlador" method="POST" enctype="multipart/form-data"> 
                    <input type="hidden" name="accion" value="subirArchivo">
                    <label for="archivo">Subir archivo:</label>
                    <input type="file" id="archivo" name="archivo" accept=".jpg,.jpeg,.png" required onchange="previewImage(event)">
                    <button type="submit">Imprimir</button>
                </form>
                <div class="upload-preview-container">
                    <img id="previewImg" src="" alt="Vista previa">
                </div>
            </div>
        </div>

        <script>
            function previewImage(event) {
                const previewImg = document.getElementById('previewImg');
                const reader = new FileReader();

                reader.onload = function () {
                    previewImg.src = reader.result;
                };

                reader.readAsDataURL(event.target.files[0]);
            }
        </script>
    </body>
</html>
