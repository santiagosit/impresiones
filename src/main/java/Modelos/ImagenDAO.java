/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Johan Herrera
 */
public class ImagenDAO {

    public int guardarImagenEnBD(Imagen imagen) {
        String sql = "INSERT INTO imagen (email, nombre, imagen_blob) VALUES (?, ?, ?)";
        int idImagen = -1; // Para almacenar el id_imagen que se generará
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", ""); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, imagen.getEmail());
            ps.setString(2, imagen.getNombre());
            ps.setBytes(3, imagen.getImagenBlob());  // Guardar los datos binarios

            int result = ps.executeUpdate();
            if (result > 0) {
                // Obtener el ID generado para la nueva imagen
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idImagen = generatedKeys.getInt(1);  // Recupera el id_imagen generado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idImagen;  // Devolver el ID de la imagen insertada, o -1 si falló
    }

    public Imagen obtenerImagenPorId(int idImagen) {
        Imagen imagen = null;
        String sql = "SELECT * FROM imagen WHERE id_imagen = ?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", ""); 
            
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idImagen);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String nombre = rs.getString("nombre");
                byte[] data = rs.getBytes("imagen_blob");
                imagen = new Imagen(email, nombre, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imagen;
    }

}
