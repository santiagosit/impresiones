<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación de Impresión</title>
</head>
<body>
    <h1>Confirmación de Impresión</h1>
    
    <p>¡La configuración de impresión se ha guardado correctamente!</p>
    
    <% 
        // Verificar si totalPrecio está disponible
        if (request.getAttribute("totalPrecio") != null) {
            int totalPrecio = (int) request.getAttribute("totalPrecio");
    %>
        <h2>Total a Pagar: $<%= totalPrecio %></h2>
    <% 
        }
    %>
    
    <a href="cliente.jsp">Regresar a la Página de Cliente</a>
</body>
</html>
