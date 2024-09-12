package Modelos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        ps.setString(1, usu.getUsuario());
        ps.setString(2, usu.getContraseña());
        rs = ps.executeQuery();
        if (rs.next()) {
            // Setear el rol del usuario
            usu.setRol(rs.getInt("rol"));
            return 1; 
        } else {
            return 0; 
        }

    } catch (Exception e) {
        return 0; 
    }
}

    @Override
    public int macotadelete(String nombre){
        String sql = "DELETE FROM perros WHERE NOMBRE=?";
        String sqlEventos = "DELETE FROM calendario WHERE MASCOTA=?";
        int resultado = 0;
        try {
            con = cn.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement("SELECT * FROM calendario WHERE MASCOTA=?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = con.prepareStatement(sqlEventos);
                ps.setString(1, nombre);
                ps.executeUpdate();                
            }
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);            
            resultado = ps.executeUpdate();
            con.commit(); 
        } catch (SQLException e) {
            try {
                con.rollback(); 
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;   
    }
    @Override
    public int eliminar(String usuario, String contraseña) {
        String sqlUsuario = "DELETE FROM usuarios WHERE USUARIO=? AND CONTRASENA=?";
        String sqlPerros = "DELETE FROM perros WHERE DUENO =?";
        String sqlEventos = "DELETE FROM calendario WHERE DUENO=?";
        int resultado = 0;
        try {
            con = cn.getConnection();
            con.setAutoCommit(false); 
            ps = con.prepareStatement("SELECT * FROM perros WHERE DUENO=?");
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = con.prepareStatement(sqlPerros);
                ps.setString(1, usuario);
                ps.executeUpdate();
                if (rs.next()) {
                    ps = con.prepareStatement(sqlEventos);
                    ps.setString(1, usuario);
                    ps.executeUpdate();
                }
            }
            ps = con.prepareStatement(sqlUsuario);
            ps.setString(1, usuario);
            ps.setString(2, contraseña);
            resultado = ps.executeUpdate();
            con.commit(); 
        } catch (SQLException e) {
            try {
                con.rollback(); 
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;   
    }
}