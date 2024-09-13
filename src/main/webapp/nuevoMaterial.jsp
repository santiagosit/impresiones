<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.SQLException, java.sql.Statement" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Nuevo Material</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <nav class="navbar">
            <div class="logo">
                <!--<img src="logo.png" alt=""> comment -->
                <span>ImprimeYa</span>
            </div>
            <ul class="nav-links">
                <li><a href="index.html">Inicio</a></li>
            </ul>
            <a href="login.html" class="Ingresar">Ingresar</a>
        </nav>
    </header>

    <div class="containernuevomatetial">
        <div class="notification-section">
            <h2>Agregar Nuevo Material</h2>
            <form action="Controlador" method="post">
                <input type="hidden" name="accion" value="guardarMaterial">
                
                <label for="nombre">Nombre del Material:</label>
                <input type="text" id="nombre" name="nombreMaterial" required><br>

                <label for="dimensiones">Dimensiones:</label>
                <select id="dimensiones" name="idDimensiones" required>
                    <option value="">Seleccionar dimensión</option>
                    <% 
                        Connection con = null;
                        Statement stmt = null;
                        ResultSet rs = null;

                        try {
                            // Cargar el controlador JDBC
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            // Establecer la conexión con la base de datos
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                            // Crear una sentencia
                            stmt = con.createStatement();
                            // Ejecutar la consulta
                            String sql = "SELECT id_dimensiones, alto, ancho FROM dimensiones";
                            rs = stmt.executeQuery(sql);

                            // Iterar sobre el ResultSet y llenar el dropdown
                            while (rs.next()) {
                                int idDimensiones = rs.getInt("id_dimensiones");
                                int alto = rs.getInt("alto");
                                int ancho = rs.getInt("ancho");
                    %>
                                <option value="<%= idDimensiones %>"><%= alto %> x <%= ancho %></option>
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
                </select><br>

                <input type="submit" value="Guardar Material">
            </form>

            <form action="Controlador" method="post" style="margin-top: 20px;">
                <input type="hidden" name="accion" value="guardarDimension">
                <label for="nuevoDimensiones">Agregar Nueva Dimensión:</label>
                Alto: <input type="number" id="nuevoAlto" name="nuevoAlto" min="1" step="1" required><br>
                Ancho: <input type="number" id="nuevoAncho" name="nuevoAncho" min="1" step="1" required><br>
                <input type="submit" value="Agregar Dimensión">
            </form>
        </div>
        <a href="admin.jsp">Regresar</a>
    </div>
</body>
</html>
