<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.SQLException, java.sql.Statement, java.util.ArrayList, java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte de Impresiones - ImprimeYa</title>
    <link rel="stylesheet" href="styles.css">
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
        <h2>Filtrar Reporte de Impresiones</h2>

        <%
            Connection con = null;
            Statement stmt = null;
            ResultSet rsEmails = null;
            List<String> emails = new ArrayList<>();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                stmt = con.createStatement();

                // Consulta para obtener todos los correos
                rsEmails = stmt.executeQuery("SELECT DISTINCT email FROM usuario");

                // Almacenamos los correos en una lista
                while (rsEmails.next()) {
                    emails.add(rsEmails.getString("email"));
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rsEmails != null) rsEmails.close();
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>

        <form method="GET" action="adminreporte.jsp">
            <div class="filter-section">
                <label for="estado">Estado:</label>
                <select name="estado" id="estado">
                    <option value="">-- Seleccionar Estado --</option>
                    <option value="Recibido">Recibido</option>
                    <option value="Imprimiendo">Imprimiendo</option>
                    <option value="Impreso">Impreso</option>
                    <option value="Entregado">Entregado</option>
                </select>

                <label for="material">Material:</label>
                <select name="material" id="material">
                    <option value="">-- Seleccionar Material --</option>
                    <option value="Afiche">Afiche</option>
                    <option value="Fichero">Fichero</option>
                    <option value="Protter">Protter</option>
                    <option value="Cartulina">Cartulina</option>
                    <option value="Fotos">Fotos</option>
                    <option value="Papel Fotografico">Papel Fotografico</option>
                    <option value="Hoja">Hoja</option>
                </select>

                <label for="fecha">Fecha de Orden (YYYY-MM-DD):</label>
                <input type="date" name="fecha" id="fecha">

                <!-- Filtro por Email -->
                <label for="correo">Correo del Usuario:</label>
                <select name="correo" id="correo">
                    <option value="">-- Seleccionar Correo --</option>
                    <% for (String email : emails) { %>
                        <option value="<%= email %>"><%= email %></option>
                    <% } %>
                </select>

                <input type="submit" value="Filtrar" style="padding: 10px 20px; background-color: #007BFF; color: white; border: none; border-radius: 4px; cursor: pointer;">
            </div>
        </form>

        <div class="table-section">
            <h3>Resultados del Reporte</h3>
            <table>
                <thead>
                    <tr>
                        <th>Nombre de Imagen</th>
                        <th>Estado</th>
                        <th>Fecha de Orden</th>
                        <th>Material</th>
                        <th>Usuario</th>
                        <th>Email Usuario</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Connection conReporte = null;
                        Statement stmtReporte = null;
                        ResultSet rsReporte = null;

                        String estado = request.getParameter("estado");
                        String material = request.getParameter("material");
                        String fecha = request.getParameter("fecha");
                        String correo = request.getParameter("correo");

                        StringBuilder query = new StringBuilder("SELECT img.nombre AS nombre_imagen, est.estado AS estado_imagen, ord.fecha AS fecha_orden, ");
                        query.append("mat.nombre_material AS material_impresion, usr.fullname AS usuario_imagen, usr.email AS email_usuario ");
                        query.append("FROM impresiones imp ");
                        query.append("JOIN imagen img ON imp.id_imagen = img.id_imagen ");
                        query.append("JOIN estado est ON imp.id_estado = est.id_estado ");
                        query.append("JOIN material mat ON imp.id_material = mat.id_material ");
                        query.append("JOIN usuario usr ON img.email = usr.email ");
                        query.append("JOIN orden ord ON imp.id_orden = ord.id_orden ");
                        query.append("WHERE 1=1 ");

                        if (estado != null && !estado.isEmpty()) {
                            query.append("AND est.estado = '").append(estado).append("' ");
                        }
                        if (material != null && !material.isEmpty()) {
                            query.append("AND mat.nombre_material = '").append(material).append("' ");
                        }
                        if (fecha != null && !fecha.isEmpty()) {
                            query.append("AND DATE(ord.fecha) = '").append(fecha).append("' ");
                        }
                        if (correo != null && !correo.isEmpty()) {
                            query.append("AND usr.email = '").append(correo).append("' ");
                        }

                        query.append("ORDER BY ord.fecha ASC");

                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            conReporte = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                            stmtReporte = conReporte.createStatement();
                            rsReporte = stmtReporte.executeQuery(query.toString());

                            while (rsReporte.next()) {
                                String nombreImagen = rsReporte.getString("nombre_imagen");
                                String estadoImagen = rsReporte.getString("estado_imagen");
                                String fechaOrden = rsReporte.getString("fecha_orden");
                                String materialImpresion = rsReporte.getString("material_impresion");
                                String usuarioImagen = rsReporte.getString("usuario_imagen");
                                String emailUsuario = rsReporte.getString("email_usuario");
                    %>
                    <tr>
                        <td><%= nombreImagen %></td>
                        <td><%= estadoImagen %></td>
                        <td><%= fechaOrden %></td>
                        <td><%= materialImpresion %></td>
                        <td><%= usuarioImagen %></td>
                        <td><%= emailUsuario %></td>
                    </tr>
                    <%
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (rsReporte != null) rsReporte.close();
                                if (stmtReporte != null) stmtReporte.close();
                                if (conReporte != null) conReporte.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    %>
                </tbody>
            </table>
                <input type="button" value="Volver al menu" 
       style="padding: 10px 20px; background-color: #007BFF; color: white; border: none; border-radius: 4px; cursor: pointer;"
       onclick="window.location.href='admin.jsp';">
                
        </div>
    </div>
</body>
</html>
