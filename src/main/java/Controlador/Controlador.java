/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelos.Usuario;
import Modelos.UsuarioDAO;
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
            String confirm_password = request.getParameter("confirm-password");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                String sql = "INSERT INTO usuarios(email,fullname,telefono,direccion,ciudad,documento,password) VALUES (?,?,?,?,?,?,?)";
                try (
                        PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email);
                    ps.setString(2, fullname);
                    ps.setString(3, telefono);
                    ps.setString(4, direccion);
                    ps.setString(5, ciudad);
                    ps.setString(6, documento);
                    ps.setString(7, password);
                    ps.executeUpdate();
                    response.sendRedirect("index.jsp");
                }
                conn.close();
            } catch (Exception e) {
                response.sendRedirect("registro.jsp");
            }

        } else if (accion.equalsIgnoreCase("login")) {
            UsuarioDAO dao = new UsuarioDAO();
            String email = request.getParameter("email");
            //String contraseña = UsuarioDAO.cifrarSHA256(request.getParameter("contraseñaL"));
            String password = request.getParameter("password");
            Usuario user = new Usuario(email, password);
            if (accion.equalsIgnoreCase("login")) {
                int r;
                r = dao.validar(user);
                if ("admin".equals(email) && "1234".equals(password)) {
                    HttpSession session = request.getSession();
                    response.sendRedirect("admin.jsp");
                    if (r == 1) {

                    } else if (r == 1) {
                        response.sendRedirect("cliente.jsp");
                    }
                } else {
                    response.sendRedirect("index.jsp");
                }
            }

        }

        if (accion.equalsIgnoreCase("subirArchivo")) {
            Part archivo = request.getPart("archivo");
            String nombreArchivo = Paths.get(archivo.getSubmittedFileName()).getFileName().toString();
            
            // Validar el tipo de archivo
            if (archivo.getContentType().startsWith("image/") && archivo.getSize() <= 20 * 1024 * 1024) {
                // Guardar el archivo en el servidor
                String rutaSubida = getServletContext().getRealPath("/") + "uploads";
                File directorio = new File(rutaSubida);
                if (!directorio.exists()) {
                    directorio.mkdirs();  // Crear el directorio si no existe
                }
                archivo.write(rutaSubida + File.separator + nombreArchivo);
                
                // Guardar los detalles del archivo en la base de datos
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "");
                    String sql = "INSERT INTO archivos (usuario, nombre, ruta) VALUES (?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, request.getSession().getAttribute("usuario").toString());  // Guardar con el usuario
                    ps.setString(2, nombreArchivo);
                    ps.setString(3, "uploads/" + nombreArchivo);
                    ps.executeUpdate();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Redirigir a la página del cliente después de subir el archivo
                response.sendRedirect("cliente.jsp");
            } else {
                // Archivo no válido
                response.getWriter().println("Archivo no permitido o demasiado grande.");
            }
        }
    }
}
