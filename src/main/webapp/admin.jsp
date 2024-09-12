<%-- 
    Document   : admin
    Created on : 11/09/2024, 9:24:07 p. m.
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
                <img src="logo.png" alt=>
                <span>ImprimeYa</span>
            </div>
            <ul class="nav-links">
                <li><a href="index.html">Inicio</a></li>
            </ul>
            <a href="login.html" class="Ingresar">Ingresar</a>
        </nav>
    </header>
<div class="container"></div>
    <div class="notification-section">
        <h2>NOTIFICACIONES</h2>
        <div class="table-container">
            <!-- Tabla de documentos impresos -->
            <div class="table">
                <h3>DOCUMENTOS IMPRESOS</h3>
                <table>
                    <tr>
                        <th>Fecha</th>
                        <th>Documento</th>
                        <th>Monto</th>
                    </tr>
                    <tr>
                        <td>23/05/2024</td>
                        <td>DOCUMENTO 1</td>
                        <td>$2000</td>
                    </tr>
                    <tr>
                        <td>12/06/2024</td>
                        <td>DOCUMENTO 2</td>
                        <td>$3500</td>
                    </tr>
                    <tr>
                        <td>17/07/2024</td>
                        <td>DOCUMENTO 3</td>
                        <td>$200</td>
                    </tr>
                    <tr>
                        <td>01/08/2024</td>
                        <td>DOCUMENTO 4</td>
                        <td>$5700</td>
                    </tr>
                </table>
            </div>
            <!-- Tabla de documentos por imprimir -->
            <div class="table">
                <h3>DOCUMENTOS POR IMPRIMIR</h3>
                <table>
                    <tr>
                        <th>Fecha</th>
                        <th>Documento</th>
                        <th>Monto</th>
                    </tr>
                    <tr>
                        <td>05/09/2024</td>
                        <td>DOCUMENTO 5</td>
                        <td>$30000</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
