<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.SQLException, java.sql.Statement" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro - ImprimeYa</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                font-size: 18px;
                text-align: left;
            }

            th, td {
                padding: 12px;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f4f4f4;
                color: #333;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            tr:hover {
                background-color: #f1f1f1;
            }
        </style>
    </head>
    <body>
        <header>
            <nav class="navbar">
                <div class="logo">
                    <img src="logo.png" alt="">
                    <span>ImprimeYa</span>
                </div>
                <ul class="nav-links">
                    <li><a href="index.html">Inicio</a></li>
                </ul>
                <a href="login.html" class="Ingresar">Ingresar</a>
            </nav>
        </header>

        <div class="container">
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

                    <!--Tabla de materiales -->
                    <div class="table">
                        <h3>Materiales</h3>
                        <table>
                            <thead>
                                <tr>
                                    <th>Material</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    Connection con = null;
                                    Statement stmt = null;
                                    ResultSet rs = null;
                                    boolean hasMaterials = false;

                                    try {
                                        // Cargar el controlador JDBC
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                        // Establecer la conexión con la base de datos
                                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                                        // Crear una sentencia
                                        stmt = con.createStatement();
                                        // Ejecutar la consulta
                                        String sql = "SELECT nombre_material FROM material";
                                        rs = stmt.executeQuery(sql);

                                        // Iterar sobre el ResultSet y mostrar los resultados
                                        while (rs.next()) {
                                            String nombreMaterial = rs.getString("nombre_material");
                                            hasMaterials = true;
                                %>
                                <tr>
                                    <td><%= nombreMaterial %></td>
                                </tr>
                                <% 
                                        }
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } finally {
                                        // Cerrar recursos
                                        try {
                                            if (rs != null) rs.close();
                                            if (stmt != null) stmt.close();
                                            if (con != null) con.close();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                %>
                                <% if (!hasMaterials) { %>
                                <tr>
                                    <td>No hay materiales disponibles.</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>                        
                        <a href="nuevoMaterial.jsp">
                            <input type="submit" value="Nuevo Material">
                        </a>                                                           
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
