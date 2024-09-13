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
                <img src="logo.png" alt="">
                <span>ImprimeYa</span>
            </div>
            <ul class="nav-links">
                <li><a href="index.jsp">Inicio</a></li>
            </ul>
            <a href="index.jsp" class="Ingresar">Ingresar</a>
        </nav>
    </header>

    <div class="container">
        <div class="upload-gallery">
            <div class="upload-section">
                <form id="uploadForm" action="Controlador" method="POST" enctype="multipart/form-data">
                    <label for="archivo">Subir archivo:</label>
                    <input type="file" id="archivo" name="archivo" accept=".jpg,.jpeg,.png" required onchange="previewImage(event)">
                    <input type="hidden" name="accion" value="subirArchivo">
                    <button type="submit">Subir</button>
                </form>
                <div class="preview">
                    <img id="previewImg" src="" alt="Vista previa">
                </div>
                <button onclick="location.href = 'clienteimpresion.jsp'" type="submit">IMPRIMIR</button>
            </div>
        </div>
    </div>

    <script>
        function previewImage(event) {
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.onload = function() {
                document.getElementById('previewImg').src = reader.result;
            };
            if (file) {
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>
