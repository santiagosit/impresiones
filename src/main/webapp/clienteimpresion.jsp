<%@ page import="java.util.List" %>
<%@ page import="Modelos.MaterialDAO, Modelos.Material, Modelos.Dimensiones, Modelos.DimensionDAO" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Configuración de Impresión</title>
    </head>
    <body>
        <h1>Configuración de Impresión</h1>
        <form action="Controlador" method="post">
            <input type="hidden" name="accion" value="configurarimpresion">
            <input type="hidden" name="id_imagen" value="<%= request.getAttribute("id_imagen") %>">           
            <input type="hidden" name="id_orden" value="<%= request.getAttribute("id_orden") %>">

            <label for="material">Seleccionar Material:</label>
            <select id="material" name="material" onchange="fetchDimensiones(this.value)" required>
                <option value="">Seleccione un material</option>
                <% 
                    MaterialDAO materialDAO = new MaterialDAO();
                    List<Material> materiales = materialDAO.obtenerMaterialesUnicos(); // Método para obtener materiales
                    for (Material material : materiales) {
                %>
                <option value="<%= material.getIdMaterial() %>"><%= material.getNombreMaterial() %></option>
                <% 
                    } 
                %>
            </select>
            <br>

            <label for="dimensiones">Seleccionar Dimensiones:</label>
            <select id="dimensiones" name="dimensiones" required>
                <option value="">Seleccione una dimensión</option>
            </select>
            <br>

            <label for="copias">Número de Copias:</label>
            <input type="number" id="copias" name="copias" min="1" required>
            <br>

            <label for="tipo_impresion">Tipo de Impresión:</label>
            <select id="tipo_impresion" name="tipo_impresion" required>
                <option value="blanco_negro">Blanco y Negro</option>
                <option value="color">Color</option>
            </select>
            <br>

            <input type="submit" value="Calcular y Guardar">
        </form>

        <script>
            function fetchDimensiones(materialId) {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "getDimensiones?materialId=" + materialId, true);
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        var dimensiones = JSON.parse(xhr.responseText);
                        var select = document.getElementById("dimensiones");
                        select.innerHTML = '<option value="">Seleccione una dimensión</option>';
                        for (var i = 0; i < dimensiones.length; i++) {
                            var option = document.createElement("option");
                            option.value = dimensiones[i].id;
                            option.text = dimensiones[i].nombre;
                            select.add(option);
                        }
                    } else {
                        console.error("Error al cargar dimensiones:", xhr.status, xhr.statusText);
                    }
                };
                xhr.send();
            }

        </script>

        <% 
            // Verificar si totalPrecio está disponible
            if (request.getAttribute("totalPrecio") != null) {
                int totalPrecio = (int) request.getAttribute("totalPrecio");
        %>
        <h2>Total a Pagar: $<%= totalPrecio %></h2>
        <a href="cliente.jsp">Regresar a la Página de Cliente</a>
        <% 
            }
        %>
    </body>
</html>
