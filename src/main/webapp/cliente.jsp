<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro - ImprimeYa</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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

        <div class="container mt-4">
            <<form action="Controlador" class="col-md-6" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="accion" value="SubirImagen">  
                <div class="card">
                    <div class="card-header">
                        <h3>Subir Imagen</h3>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label>Nombre de la imagen</label>
                            <input type="text" name="txtnombre" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>Nombre de la imagen</label>
                            <input type="file" name="fileImagen" class="form-control">
                        </div>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn-outline-primary" type="submit">Guardar Imagen</button>
                    </div>
                </div>
            </form>
        </div>

        <script>
            function previewImage(event) {
                const file = event.target.files[0];
                const reader = new FileReader();
                reader.onload = function () {
                    document.getElementById('previewImg').src = reader.result;
                };
                if (file) {
                    reader.readAsDataURL(file);
                }
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script> 
    </body>
</html>
