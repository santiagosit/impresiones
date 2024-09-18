package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Impresion> obtenerImpresionesPorUsuario(String email) throws SQLException {
    String url = "jdbc:mysql://localhost:3306/gestorimpresiones";
    String user = "root";
    String password = "";
    
    List<Impresion> impresiones = new ArrayList<>();

    String sql = "SELECT i.id_imagen, i.id_material, i.id_dimensiones, i.id_estado, i.id_orden "
               + "FROM impresiones i "
               + "INNER JOIN imagenes img ON i.id_imagen = img.id_imagen "
               + "WHERE img.email_usuario = ?";

    try (Connection conn = DriverManager.getConnection(url, user, password); 
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Impresion impresion = new Impresion();
                impresion.setIdImagen(rs.getInt("id_imagen"));
                impresion.setIdMaterial(rs.getInt("id_material"));
                impresion.setIdDimensiones(rs.getInt("id_dimensiones"));
                impresion.setIdEstado(rs.getInt("id_estado"));
                impresion.setIdOrden(rs.getInt("id_orden"));

                impresiones.add(impresion);
            }
        }
    }

    return impresiones;
}

}
