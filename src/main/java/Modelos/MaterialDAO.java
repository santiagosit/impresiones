package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    private Conexion conexion;

    public MaterialDAO() {
        this.conexion = new Conexion();
    }

    // Método para agregar un nuevo material
    public void agregarMaterial(String nombreMaterial, int idDimensiones) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = conexion.getConnection();
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

    // Nuevo método para obtener la lista de materiales únicos
    public List<Material> obtenerMaterialesUnicos() throws SQLException {
        List<Material> materialesList = new ArrayList<>();
        String sql = "SELECT DISTINCT nombre_material, id_material FROM material";
        try (Connection con = conexion.getConnection(); 
                PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Material material = new Material();
                material.setIdMaterial(rs.getInt("id_material"));
                material.setNombreMaterial(rs.getString("nombre_material"));
                materialesList.add(material);
            }
        }
        return materialesList;
    }
}
