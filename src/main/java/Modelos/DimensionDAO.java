package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DimensionDAO {

    private Conexion conexion;

    public DimensionDAO() {
        this.conexion = new Conexion();
    }

    public void agregarDimension(int alto, int ancho) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = conexion.getConnection();
            String sql = "INSERT INTO dimensiones (alto, ancho) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, alto);
            ps.setInt(2, ancho);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al agregar la dimensión");
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

    public List<Dimensiones> obtenerDimensionesPorMaterial(int materialId) throws SQLException {
        List<Dimensiones> dimensionesList = new ArrayList<>();
        String sql = "SELECT d.id_dimensiones, d.alto, d.ancho "
                + "FROM dimensiones d "
                + "JOIN material m ON d.id_dimensiones = m.id_dimensiones "
                + "WHERE m.id_material = ?";
        try (Connection conn = conexion.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Dimensiones dimension = new Dimensiones();
                    dimension.setIdDimensiones(rs.getInt("id_dimensiones"));
                    dimension.setAlto(rs.getInt("alto"));
                    dimension.setAncho(rs.getInt("ancho"));
                    dimensionesList.add(dimension);
                }
            }
        }
        return dimensionesList;
    }
}
