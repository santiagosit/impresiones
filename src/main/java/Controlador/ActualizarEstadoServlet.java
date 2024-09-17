package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ActualizarEstadoServlet")
public class ActualizarEstadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idImagen = Integer.parseInt(request.getParameter("id_imagen"));
        int nuevoEstado = Integer.parseInt(request.getParameter("estado"));

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
            
            String sql = "UPDATE impresiones SET id_estado = ? WHERE id_imagen = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, nuevoEstado);
            pstmt.setInt(2, idImagen);

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                response.sendRedirect("admin.jsp"); // Redirigir al JSP con la tabla actualizada
            } else {
                response.getWriter().println("Error al actualizar el estado.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
