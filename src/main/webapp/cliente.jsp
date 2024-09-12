<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
    String emailUsuario = (String) session.getAttribute("email");
    if (emailUsuario == null) {
        out.println("No estás autenticado.");
        return;
    }
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");

    String sql = "SELECT ruta_imagen FROM impresiones WHERE email_usuario = ? ORDER BY id_imagen DESC LIMIT 5";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, emailUsuario);
    ResultSet rs = ps.executeQuery();
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
          <label for="archivo">Subir archivo:</label>
          <input type="file" id="archivo" name="archivo" accept=".jpg,.jpeg,.png" required onchange="previewImage(event)">
          <input type="hidden" name="accion" value="subirArchivo">
          <button type="submit value="subirArchivo">Imprimir </button>
        </form>
        <div class="upload-preview-container">
          <img id="previewImg" src="" alt="Vista previa">
        </div>
      </div>

      <div class="gallery">
        <h2>Últimas imágenes subidas</h2>
        <%
          while (rs.next()) {
              String rutaImagen = rs.getString("ruta_imagen");
        %>
            <img src="<%=rutaImagen%>" alt="Imagen subida" onclick="selectImageFromGallery(event)">
        <%
          }
          rs.close();
          conn.close();
        %>
      </div>

      <% 
        String imagenSubida = request.getParameter("imagenSubida");
        if (imagenSubida != null && !imagenSubida.isEmpty()) { 
      %>
        <button id="printButton" onclick="redirectToPrintPage()">Imprimir</button>
      <% } %>
    </div>

    <script>
      function previewImage(event) {
        const previewImg = document.getElementById('previewImg');
        const reader = new FileReader();

        reader.onload = function() {
          previewImg.src = reader.result;
        };

        reader.readAsDataURL(event.target.files[0]);
      }

      function selectImageFromGallery(event) {
        const galleryImages = document.querySelectorAll('.gallery img');
        
        galleryImages.forEach(img => img.classList.remove('selected'));

        event.target.classList.add('selected');

        const previewImg = document.getElementById('previewImg');
        previewImg.src = event.target.src;
      }

      document.querySelectorAll('.gallery img').forEach(img => {
        img.addEventListener('click', selectImageFromGallery);
      });


    </script>
  </body>
</html>
