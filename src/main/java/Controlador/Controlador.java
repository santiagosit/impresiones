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

/**
 *
 * @author Johan Herrera
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
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
                    session.setAttribute("rol", usu.getRol());  // Guardar el rol en la sesión

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
        }

if (accion.equalsIgnoreCase("subirArchivo")) {
    Part archivo = request.getPart("archivo");
    String nombreArchivo = Paths.get(archivo.getSubmittedFileName()).getFileName().toString();

    // Verificar el tipo de archivo y su tamaño
    if (archivo.getContentType().startsWith("image/") && archivo.getSize() <= 20 * 1024 * 1024) {
        // Guardar el archivo en el servidor
        String rutaSubida = getServletContext().getRealPath("/") + "uploads";
        File directorio = new File(rutaSubida);
        if (!directorio.exists()) {
            directorio.mkdirs();  // Crear el directorio si no existe
        }
        archivo.write(rutaSubida + File.separator + nombreArchivo);

        // Obtener el email del usuario que ha iniciado sesión
        String emailUsuario = (String) request.getSession().getAttribute("email");

        // Guardar los detalles del archivo en la base de datos
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");

            // Insertar imagen en la tabla `impresiones`
            String sqlInsertImpresion = "INSERT INTO impresiones (id_imagen, ruta_imagen, email_usuario) VALUES (?, ?, ?)";
            PreparedStatement psImpresion = conn.prepareStatement(sqlInsertImpresion);
            psImpresion.setInt(1, 0);  // Ajuste según el tipo de auto-incrementación o id generado
            psImpresion.setString(2, "uploads/" + nombreArchivo);
            psImpresion.setString(3, emailUsuario);
            psImpresion.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirigir a la página del cliente después de subir el archivo
        response.sendRedirect("cliente.jsp?imagenSubida=" + nombreArchivo);
    } else {
        // Archivo no válido
        response.getWriter().println("Archivo no permitido o demasiado grande.");
    }
}

    }
}
