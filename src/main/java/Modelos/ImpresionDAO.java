package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImpresionDAO {

    public void guardarImpresion(Impresion impresion) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/gestorimpresiones";
        String user = "root";
        String password = "";

        String sql = "INSERT INTO impresiones (id_imagen, id_material, id_dimensiones, id_estado, id_orden) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password); 
            
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, impresion.getIdImagen());
            ps.setInt(2, impresion.getIdMaterial());
            ps.setInt(3, impresion.getIdDimensiones());
            ps.setInt(4, impresion.getIdEstado());
            ps.setInt(5, impresion.getIdOrden());

            ps.executeUpdate();
        }
    }
}
