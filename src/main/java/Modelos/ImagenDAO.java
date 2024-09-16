/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Johan Herrera
 */
public class ImagenDAO {

    public boolean guardarImagenEnBD(Imagen imagen) {
        String sql = "INSERT INTO imagen (email, nombre, imagen_blob) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", ""); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, imagen.getEmail());
            ps.setString(2, imagen.getNombre());
            ps.setBytes(3, imagen.getImagenBlob());  // Guardar los datos binarios

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
