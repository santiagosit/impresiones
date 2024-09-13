/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelos.UsuarioDAO;
import Modelos.Usuario;
import jakarta.servlet.annotation.MultipartConfig;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *  *
 *  * @author Johan Herrera  
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
@MultipartConfig
public class Controlador extends HttpServlet {

    /**
     *  * Processes requests for both HTTP <code>GET</code> and
     * <code>POST</code>  * methods.  *  * @param request servlet request  
     *
     *
     * @param response servlet response  * @throws ServletException if a
     * servlet-specific error occurs  * @throws IOException if an I/O error
     * occurs  
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controlador at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     *  * Handles the HTTP <code>GET</code> method.  *  * @param request servlet
     * request  * @param response servlet response  * @throws ServletException
     * if a servlet-specific error occurs  * @throws IOException if an I/O error
     * occurs  
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedImage imagen = (BufferedImage) request.getSession().getAttribute("imagenSeleccionada");
        String tipoContenido = (String) request.getSession().getAttribute("tipoContenidoImagen");

        if (imagen != null && tipoContenido != null) {
            response.setContentType(tipoContenido);
            String extension = tipoContenido.substring(tipoContenido.lastIndexOf('/') + 1);
            ImageIO.write(imagen, extension, response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // Enviar error 404 si no hay imagen
        }
        
    }
    

    /**
     *  * Handles the HTTP <code>POST</code> method.  *  * @param request
     * servlet request  * @param response servlet response  * @throws
     * ServletException if a servlet-specific error occurs  * @throws
     * IOException if an I/O error occurs  
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion.equalsIgnoreCase("registro")) {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String ciudad = request.getParameter("ciudad");
            String documento = request.getParameter("documento");
            String password = request.getParameter("password");

            try {
// Intentar cargar el driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

// Obtener la conexión
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "1234");
                if (conn == null) {
                    throw new SQLException("No se pudo establecer la conexión a la base de datos.");
                }

// Sentencia SQL
                String sql = "INSERT INTO usuario (email, fullname, telefono, direccion, rol, ciudad, documento, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, fullname);
                ps.setString(3, telefono);
                ps.setString(4, direccion);
                ps.setInt(5, 1);
                ps.setString(6, ciudad);
                ps.setString(7, documento);
                ps.setString(8, password);

                ps.executeUpdate();
                conn.close();
                response.sendRedirect("index.jsp");

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error al conectar con la base de datos. Por favor, inténtelo de nuevo más tarde.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", e);
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Se ha producido un error inesperado. Por favor, inténtelo de nuevo.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
        } else if (accion.equalsIgnoreCase("login")) {
// Obtener los parámetros de inicio de sesión
            String usuario = request.getParameter("username");
            String contraseña = request.getParameter("password");

// Validar los parámetros de inicio de sesión
            if (usuario == null || usuario.trim().isEmpty() || contraseña == null || contraseña.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Usuario o contraseña no pueden estar vacíos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            UsuarioDAO dao = new UsuarioDAO();
            Usuario usu = new Usuario(usuario, contraseña);

            try {
                int r = dao.validar(usu);
                if (r == 1) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", usu.getUsuario());
                    session.setAttribute("rol", usu.getRol());

// Redirigir según el rol
                    if (usu.getRol() == 1) {
                        response.sendRedirect("cliente.jsp");
                    } else if (usu.getRol() == 2) {
                        response.sendRedirect("admin.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Rol no válido.");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Credenciales incorrectas. Por favor, intente nuevamente.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else if (accion.equalsIgnoreCase("subir")) {
            Part archivo = request.getPart("archivo");
            String nombreArchivo = Paths.get(archivo.getSubmittedFileName()).getFileName().toString();

// ... (código para guardar el archivo en el servidor) ...
            String emailUsuario = (String) request.getSession().getAttribute("email");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "1234");

// Obtener las dimensiones de la imagen (puedes usar una librería como ImageIO)
                BufferedImage imagen = ImageIO.read(archivo.getInputStream());
                String tipoContenido = archivo.getContentType();
                request.getSession().setAttribute("imagenSeleccionada", imagen); 
                request.getSession().setAttribute("tipoContenidoImagen", tipoContenido);
                int ancho = imagen.getWidth();
                int alto = imagen.getHeight();

// Insertar en la tabla `imagen`
                String sqlInsertImagen = "INSERT INTO imagen (id_usuario, tipo_imagen, alto, ancho, ruta_imagen) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psImagen = conn.prepareStatement(sqlInsertImagen);
                psImagen.setString(1, emailUsuario); // Asegúrate de tener el email del usuario en la sesión
                psImagen.setString(2, archivo.getContentType()); // O puedes obtener la extensión del archivo y guardarla
                psImagen.setInt(3, alto);
                psImagen.setInt(4, ancho);
                psImagen.setString(5, "uploads/" + nombreArchivo);
                psImagen.executeUpdate();

                conn.close();
                response.sendRedirect("clienteimpresion.jsp"); 
            } catch (Exception e) {
                e.printStackTrace();
            }

// Redirigir al usuario a clienteimpresion.jsp
            response.sendRedirect("clienteimpresion.jsp");
        } else {
            response.getWriter().println("Archivo no permitido o demasiado grande.");
        }
    }
}
