package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection con;
    String url = "jdbc:mysql://localhost:3306/gestorimpresiones";
    String user = "root";
    String pass = "";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexion Exitosa");
        } catch (Exception e) {
            System.out.println("Conexion FALLIDA"+e.getMessage());
        }
        return con;
    }
}
