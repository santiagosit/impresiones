package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DimensionDAO {

    public void agregarDimension(int alto, int ancho) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = new Conexion().getConnection();
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
}
