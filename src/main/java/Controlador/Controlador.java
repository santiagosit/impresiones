package Controlador;

import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import Modelos.Imagen;
import Modelos.ImagenDAO;
import Modelos.MaterialDAO;
import Modelos.UsuarioDAO;
import Modelos.Usuario;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author Johan Herrera
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
@MultipartConfig
public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null) {
            request.setAttribute("errorMessage", "La acción no puede ser nula.");
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
            return;
        }

        switch (accion.toLowerCase()) {
            case "registro":
                handleRegistro(request, response);
                break;
            case "login":
                handleLogin(request, response);
                break;
            case "guardarmaterial":
                handleGuardarMaterial(request, response);
                break;
            case "subirimagen":
                handleSubirImagen(request, response);
                break;
            default:
                request.setAttribute("errorMessage", "Acción no reconocida.");
                request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
                break;
        }
    }

    private void handleRegistro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String ciudad = request.getParameter("ciudad");
        String documento = request.getParameter("documento");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestorimpresiones", "root", "")) {
                String sql = "INSERT INTO usuario (email, fullname, telefono, direccion, rol, ciudad, documento, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, fullname);
                ps.setString(3, telefono);
                ps.setString(4, direccion);
                ps.setInt(5, 1); // Rol 1 por defecto para usuario
                ps.setString(6, ciudad);
                ps.setString(7, documento);
                ps.setString(8, password);

                ps.executeUpdate();
            }
            response.sendRedirect("index.jsp");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error de clase no encontrada. Verifique la configuración.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Se ha producido un error inesperado. Por favor, inténtelo de nuevo.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("username");
        String contraseña = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || contraseña == null || contraseña.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Usuario o contraseña no pueden estar vacíos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usu = new Usuario(email, contraseña);

        try {
            int r = dao.validar(usu);
            if (r == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("email", usu.getEmail());
                session.setAttribute("rol", usu.getRol());

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
            request.setAttribute("errorMessage", "Se ha producido un error inesperado.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void handleGuardarMaterial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreMaterial = request.getParameter("nombreMaterial");
        int idDimensiones = Integer.parseInt(request.getParameter("idDimensiones"));

        MaterialDAO materialDAO = new MaterialDAO();

        try {
            materialDAO.agregarMaterial(nombreMaterial, idDimensiones);
            response.sendRedirect("admin.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al agregar el material: " + e.getMessage());
        }
    }

    private void handleSubirImagen(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreImagen = request.getParameter("txtnombre");
        Part filePart = request.getPart("fileImagen");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (filePart != null && email != null) {
            ImagenDAO imagenDAO = new ImagenDAO();
            String filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Leer el archivo como un InputStream
            byte[] imageData;
            try (InputStream inputStream = filePart.getInputStream()) {
                imageData = inputStream.readAllBytes();
                System.out.println("Tamaño del archivo: " + imageData.length + " bytes"); // Debugging
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error al leer el archivo.");
                request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
                return;
            }

            // Crear un objeto Imagen con los datos binarios
            Imagen imagen = new Imagen(email, nombreImagen, imageData);

            // Guardar la imagen en la base de datos
            boolean guardado = imagenDAO.guardarImagenEnBD(imagen);

            if (guardado) {
                request.setAttribute("mensaje", "Imagen subida correctamente");
            } else {
                request.setAttribute("mensaje", "Error al subir la imagen");
            }
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Error al subir la imagen. Asegúrate de que el archivo esté disponible y que estés registrado.");
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
        }
    }

}
