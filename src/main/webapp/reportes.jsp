<%-- 
    Document   : reportes
    Created on : 11/09/2024, 9:25:25 p. m.
    Author     : ESTUDIANTE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - ImprimeYa</title>
    <link rel="stylesheet" href="../assets/css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Nombre</h1>
        <h2>Impresiones</h2>
        <div class="section">
            <h3>Linea de Tiempo de Impresiones</h3>
            <table>
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Nombre Documento</th>
                        <th>Nombre Usuario</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>23/05/2024</td>
                        <td>Documento 1</td>
                        <td>Usuario 1</td>
                        <td>$2000</td>
                    </tr>
                    </tbody>
            </table>
        </div>
        <div class="section">
            <h3>Reportes</h3>
            <div class="report">
                <img src="icon-report.png" alt="Icono de reporte">
                <p>12/08/24</p>
            </div>
            <div class="checkboxes">
                <input type="checkbox" id="cliente">
                <label for="cliente">Cliente</label><br>
                <input type="checkbox" id="numeros-clientes">
                <label for="numeros-clientes">Números de Clientes</label><br>
                <input type="checkbox" id="total-impresiones">
                <label for="total-impresiones">Total Impresiones</label><br>
            </div>
            <button>Imprimir</button>
        </div>
    </div>
</body>
</html>
