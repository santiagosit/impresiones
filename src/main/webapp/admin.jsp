<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.SQLException, java.sql.Statement" %>
<%@ page import="java.util.Set, java.util.HashSet" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro - ImprimeYa</title>
        <link rel="stylesheet" href="styles.css">

        <!-- Incluimos DataTables para la paginación -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>

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

            select {
                width: 100%;
                padding: 5px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            input[type="submit"] {
                padding: 5px 10px;
                border: none;
                border-radius: 4px;
                background-color: #4CAF50;
                color: white;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }
        </style>

        <script>
            // Inicializar DataTables con paginación
            $(document).ready(function () {
                $('#materialesTable').DataTable({
                    "pageLength": 10, // Mostrar solo 10 líneas
                    "lengthChange": false // Ocultar la opción de cambiar el número de filas
                });
            });
        </script>
    </head>

    <body>
        <header>
            <nav class="navbar">
                <div class="logo">
                    <span>ImprimeYa</span>
                </div>
                <ul class="nav-links">
                    <li><a href="index.jsp">Inicio</a></li>
                </ul>
            </nav>
        </header>

        <div class="container">
            <div class="notification-section">
                <h2>NOTIFICACIONES</h2>

                <!-- Tabla de documentos impresos y por imprimir -->
                <div class="table">
                    <h3>Documentos Impresos y por Imprimir</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>Nombre de la Imagen</th>
                                <th>Estado</th>
                                <th>Fecha de Orden</th>
                                <th>Material</th>
                                <th>Nombre del Usuario</th>
                                <th>Actualizar Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                Connection con = null;
                                Statement stmt = null;
                                ResultSet rs = null;

                                try {
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                                    stmt = con.createStatement();
                                    String sql = "SELECT img.id_imagen, img.nombre AS nombre_imagen, est.id_estado AS id_estado, est.estado AS estado_imagen, ord.fecha AS fecha_orden, " +
                                                 "mat.nombre_material AS material_impresion, usr.fullname AS usuario_imagen " +
                                                 "FROM impresiones imp " +
                                                 "JOIN imagen img ON imp.id_imagen = img.id_imagen " +
                                                 "JOIN estado est ON imp.id_estado = est.id_estado " +
                                                 "JOIN material mat ON imp.id_material = mat.id_material " +
                                                 "JOIN usuario usr ON img.email = usr.email " +
                                                 "JOIN orden ord ON imp.id_orden = ord.id_orden " +
                                                 "ORDER BY ord.fecha ASC";

                                    rs = stmt.executeQuery(sql);

                                    while (rs.next()) {
                                        String nombreImagen = rs.getString("nombre_imagen");
                                        String estadoImagen = rs.getString("estado_imagen");
                                        String fechaOrden = rs.getString("fecha_orden");
                                        String materialImpresion = rs.getString("material_impresion");
                                        String usuarioImagen = rs.getString("usuario_imagen");
                                        int idImagen = rs.getInt("id_imagen");
                                        int idEstado = rs.getInt("id_estado");
                            %>
                            <tr>
                                <td><%= nombreImagen %></td>
                                <td><%= estadoImagen %></td>
                                <td><%= fechaOrden %></td>
                                <td><%= materialImpresion %></td>
                                <td><%= usuarioImagen %></td>
                                <td>
                                    <form action="ActualizarEstadoServlet" method="post">
                                        <input type="hidden" name="id_imagen" value="<%= idImagen %>">
                                        <select name="estado">
                                            <% 
                                                Connection conEstado = null;
                                                Statement stmtEstado = null;
                                                ResultSet rsEstado = null;
                                                try {
                                                    conEstado = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                                                    stmtEstado = conEstado.createStatement();
                                                    String sqlEstado = "SELECT id_estado, estado FROM estado";
                                                    rsEstado = stmtEstado.executeQuery(sqlEstado);

                                                    while (rsEstado.next()) {
                                                        int idEstadoOption = rsEstado.getInt("id_estado");
                                                        String estadoOption = rsEstado.getString("estado");
                                            %>
                                            <option value="<%= idEstadoOption %>" <%= (idEstado == idEstadoOption) ? "selected" : "" %>><%= estadoOption %></option>
                                            <% 
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            if (rsEstado != null) rsEstado.close();
                                            if (stmtEstado != null) stmtEstado.close();
                                            if (conEstado != null) conEstado.close();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                            %>
                                        </select>
                                        <input type="submit" value="Actualizar">
                                    </form>
                                </td>
                            </tr>
                            <% 
                                    }
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (rs != null) rs.close();
                                        if (stmt != null) stmt.close();
                                        if (con != null) con.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <!-- Tabla de materiales existente con paginación y sin duplicados -->
                <div class="table">
                    <h3>Materiales</h3>
                    <table id="materialesTable" class="display">
                        <thead>
                            <tr>
                                <th>Material</th>
                                <th>Dimensiones (Alto x Ancho)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                Connection conMaterials = null;
                                Statement stmtMaterials = null;
                                ResultSet rsMaterials = null;
                                Set<String> materialesUnicos = new HashSet<>(); // Set para evitar duplicados

                                try {
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    conMaterials = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                                    stmtMaterials = conMaterials.createStatement();
                                    String sqlMaterials = "SELECT mat.nombre_material, dim.alto, dim.ancho " +
                                                          "FROM material mat " +
                                                          "JOIN dimensiones dim ON mat.id_dimensiones = dim.id_dimensiones " +
                                                          "ORDER BY mat.nombre_material ASC";
                                    rsMaterials = stmtMaterials.executeQuery(sqlMaterials);

                                    while (rsMaterials.next()) {
                                        String nombreMaterial = rsMaterials.getString("nombre_material");
                                        int alto = rsMaterials.getInt("alto");
                                        int ancho = rsMaterials.getInt("ancho");

                                        // Evitar duplicados
                                        if (materialesUnicos.add(nombreMaterial)) {
                            %>
                            <tr>
                                <td><%= nombreMaterial %></td>
                                <td><%= alto %> x <%= ancho %></td>
                            </tr>
                            <% 
                                        }
                                    }
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (rsMaterials != null) rsMaterials.close();
                                        if (stmtMaterials != null) stmtMaterials.close();
                                        if (conMaterials != null) conMaterials.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            %>
                        </tbody>
                    </table>                        
                    <a href="nuevoMaterial.jsp">
                        <input type="submit" value="Nuevo Material">
                    </a>
                    <div class="report-button-section">
                        <form action="adminreporte.jsp">
                            <input type="submit" value="Generar Reporte" style="padding: 10px 20px; background-color: #007BFF; color: white; border: none; border-radius: 4px; cursor: pointer;">
                        </form>
                    </div>
                </div>


            </div>
        </div>
    </body>
</html>
