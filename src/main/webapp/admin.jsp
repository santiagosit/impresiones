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
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                Connection con = null;
                                Statement stmt = null;
                                ResultSet rs = null;

                                try {
                                    // Cargar el controlador JDBC
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    // Establecer la conexión con la base de datos
                                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                                    // Crear la sentencia
                                    stmt = con.createStatement();
                                    // Ejecutar la consulta para obtener los detalles de las impresiones
                                    String sql = "SELECT img.tipo_imagen AS nombre_imagen, est.estado AS estado_imagen, ord.fecha AS fecha_orden, " +
                                                 "mat.nombre_material AS material_impresion, usr.fullname AS usuario_imagen " +
                                                 "FROM impresiones imp " +
                                                 "JOIN imagen img ON imp.id_imagen = img.id_imagen " +
                                                 "JOIN estado est ON imp.id_estado = est.id_estado " +
                                                 "JOIN material mat ON imp.id_material = mat.id_material " +
                                                 "JOIN usuario usr ON img.id_usuario = usr.email " +
                                                 "JOIN orden ord ON imp.id_orden = ord.id_orden";
                                    rs = stmt.executeQuery(sql);

                                    // Iterar sobre el ResultSet y mostrar los resultados
                                    while (rs.next()) {
                                        String nombreImagen = rs.getString("nombre_imagen");
                                        String estadoImagen = rs.getString("estado_imagen");
                                        String fechaOrden = rs.getString("fecha_orden");
                                        String materialImpresion = rs.getString("material_impresion");
                                        String usuarioImagen = rs.getString("usuario_imagen");
                            %>
                            <tr>
                                <td><%= nombreImagen %></td>
                                <td><%= estadoImagen %></td>
                                <td><%= fechaOrden %></td>
                                <td><%= materialImpresion %></td>
                                <td><%= usuarioImagen %></td>
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
                        </tbody>
                    </table>
                </div>

                <!-- Tabla de materiales existente -->
                <div class="table">
                    <h3>Materiales</h3>
                    <table>
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
                                boolean hasMaterials = false;

                                try {
                                    // Cargar el controlador JDBC
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    // Establecer la conexión con la base de datos
                                    conMaterials = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                                    // Crear una sentencia
                                    stmtMaterials = conMaterials.createStatement();
                                    // Ejecutar la consulta con JOIN entre material y dimensiones, ordenando alfabéticamente
                                    String sqlMaterials = "SELECT mat.nombre_material, dim.alto, dim.ancho " +
                                                          "FROM material mat " +
                                                          "JOIN dimensiones dim ON mat.id_dimensiones = dim.id_dimensiones " +
                                                          "ORDER BY mat.nombre_material ASC";
                                    rsMaterials = stmtMaterials.executeQuery(sqlMaterials);

                                    // Iterar sobre el ResultSet y mostrar los resultados
                                    while (rsMaterials.next()) {
                                        String nombreMaterial = rsMaterials.getString("nombre_material");
                                        int alto = rsMaterials.getInt("alto");
                                        int ancho = rsMaterials.getInt("ancho");
                                        hasMaterials = true;
                            %>
                            <tr>
                                <td><%= nombreMaterial %></td>
                                <td><%= alto %> x <%= ancho %></td>
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
                                        if (rsMaterials != null) rsMaterials.close();
                                        if (stmtMaterials != null) stmtMaterials.close();
                                        if (conMaterials != null) conMaterials.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            %>
                            <% if (!hasMaterials) { %>
                            <tr>
                                <td colspan="2">No hay materiales disponibles.</td>
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
    </body>
</html>
