package Modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO { 

    // Método para agregar un nuevo material
    public void agregarMaterial(String nombreMaterial, int idDimensiones) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = new Conexion().getConnection();
            String sql = "INSERT INTO material (nombre_material, id_dimensiones) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreMaterial);
            ps.setInt(2, idDimensiones);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al agregar el material");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
