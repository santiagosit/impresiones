<%-- 
    Document   : clienteimpresion
    Created on : 11/09/2024, 9:25:05 p. m.
    Author     : ESTUDIANTE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Impresiones</title>
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

<div class="containerclienteimpresiones">
    <!-- Sección de subida de archivo -->
    <div class="upload-section">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m2 0a2 2 0 002-2V6a2 2 0 00-2-2H9l-4 4v8a2 2 0 002 2h10a2 2 0 002-2V8" />
        </svg>
        <p>IMPRIMIR ARCHIVO</p>
        <div class="page-count">#Pag: 4</div>
    </div>

    <!-- Sección de opciones de impresión -->
    <div class="options-section">
        <label for="copias">Copias</label>
        <input type="number" id="copias" name="copias" value="1" min="1">

        <label for="tipo-impresion">Tipo de Impresión</label>
        <select id="tipo-impresion" name="tipo-impresion">
            <option value="normal">Normal</option>
            <option value="calidad">Alta Calidad</option>
        </select>

        <label for="material">Material</label>
        <select id="material" name="material">
            <option value="Fichero">Fichero</option>
            <option value="Protter">Cartulina</option>
            <option value="Cartulina">Papel</option>
            <option value="Fotografia">Cartulina</option>
        </select>
        
    <div id="dimensionesFichero" style="display:none;">
        <label for="dimensiones">Dimensiones para Fichero</label>
        <select id="dimensionesFicheroSelect" name="dimensionesFichero">
          <option value="A4">A4</option>
          <option value="A5">A5</option>
        </select>
      </div>
  
      <div id="dimensionesProtter" style="display:none;">
        <label for="dimensiones">Dimensiones para Protter</label>
        <select id="dimensionesProtterSelect" name="dimensionesProtter">
          <option value="A3">A3</option>
          <option value="A2">A2</option>
        </select>
      </div>
  
      <div id="dimensionesCartulina" style="display:none;">
        <label for="dimensiones">Dimensiones para Cartulina</label>
        <select id="dimensionesCartulinaSelect" name="dimensionesCartulina">
          <option value="A4">A4</option>
          <option value="A3">A3</option>
        </select>
      </div>
  
      <div id="dimensionesFotografia" style="display:none;">
        <label for="dimensiones">Dimensiones para Fotografía</label>
        <select id="dimensionesFotografiaSelect" name="dimensionesFotografia">
          <option value="5x7">5x7</option>
          <option value="8x10">8x10</option>
        </select>
      </div>

    </form>
  
    <script>
      document.getElementById("material").addEventListener("change", function() {
        var selectedMaterial = this.value;
  
        // Ocultar todas las opciones adicionales de dimensiones primero
        document.getElementById("dimensionesFichero").style.display = "none";
        document.getElementById("dimensionesProtter").style.display = "none";
        document.getElementById("dimensionesCartulina").style.display = "none";
        document.getElementById("dimensionesFotografia").style.display = "none";
  
        // Mostrar las dimensiones según el material seleccionado
        if (selectedMaterial === "Fichero") {
          document.getElementById("dimensionesFichero").style.display = "block";
        } else if (selectedMaterial === "Protter") {
          document.getElementById("dimensionesProtter").style.display = "block";
        } else if (selectedMaterial === "Cartulina") {
          document.getElementById("dimensionesCartulina").style.display = "block";
        } else if (selectedMaterial === "Fotografia") {
          document.getElementById("dimensionesFotografia").style.display = "block";
        }
      });
    </script>
        
      <div class="checkbox-group">
            <label><input type="checkbox" name="double-sided" checked> Ambos Lados</label>
            <label><input type="checkbox" name="color"> Color</label>
            <label><input type="checkbox" name="bn" checked> Blanco y Negro</label>
        </div>

        <div class="price-group">
            <div>
                <label for="unit-price">Valor Unitario</label>
                <span>$700</span>
            </div>
            <div>
                <label for="total-price">Valor Total</label>
                <span>$700</span>
            </div>
        </div>

        <button class="pay-button">Imprimir</button>
    </div>
</div>

</body>
</html>

