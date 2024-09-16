package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO implements Validar {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public int validar(Usuario usu) {
        String sql = "SELECT * FROM usuario WHERE email=? AND password=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, usu.getEmail());
            ps.setString(2, usu.getContraseña());

            rs = ps.executeQuery();

            if (rs.next()) {
                // Setear el rol del usuario desde la base de datos
                usu.setRol(rs.getInt("rol"));
                return 1;  // Usuario válido
            } else {
                return 0;  // Usuario no encontrado
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // Error en la validación
        }
    }
}
