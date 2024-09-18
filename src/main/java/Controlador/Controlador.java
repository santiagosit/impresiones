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
import Modelos.Impresion;
import Modelos.ImpresionDAO;
import Modelos.MaterialDAO;
import Modelos.UsuarioDAO;
import Modelos.Usuario;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            {
                try {
                    handleObtenerImpresiones(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            case "guardarmaterial":
                handleGuardarMaterial(request, response);
                break;
                
                
            case "subirimagen": {
                try {
                    handleSubirImagen(request, response);

                } catch (SQLException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "configurarimpresion":  // Nuevo caso
                handleConfigurarImpresion(request, response);
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

// Método modificado para manejar la subida de imágenes
    private void handleSubirImagen(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
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
            }

            // Crear un objeto Imagen con los datos binarios
            Imagen imagen = new Imagen(email, nombreImagen, imageData);

            // Guardar la imagen en la base de datos y obtener el id_imagen generado
            int idImagen = imagenDAO.guardarImagenEnBD(imagen);

            if (idImagen != -1) {
                // Insertar un registro en la tabla ordenes
                int idOrden = insertarOrden(email);

                // Redirigir a la página clienteimpresion.jsp con el id de la imagen
                request.setAttribute("mensaje", "Imagen subida correctamente");
                request.setAttribute("id_imagen", idImagen);  // Pasamos el id_imagen
                request.setAttribute("id_orden", idOrden);    // Pasamos el id_orden

                // Obtener lista de materiales de la base de datos
                MaterialDAO materialDAO = new MaterialDAO();
                request.setAttribute("materiales", materialDAO.obtenerMaterialesUnicos());  // Asume que hay un método para esto

                request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Error al subir la imagen");
                request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Error al subir la imagen. Asegúrate de que el archivo esté disponible y que estés registrado.");
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
        }
    }

    private void handleConfigurarImpresion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Validar y parsear id_imagen
            String idImagenStr = request.getParameter("id_imagen");
            if (idImagenStr == null || idImagenStr.isEmpty()) {
                throw new IllegalArgumentException("ID de imagen no proporcionado.");
            }
            int idImagen = Integer.parseInt(idImagenStr);

            // Validar y parsear id_orden
            String idOrdenStr = request.getParameter("id_orden");
            if (idOrdenStr == null || idOrdenStr.isEmpty()) {
                throw new IllegalArgumentException("ID de orden no proporcionado.");
            }
            int idOrden = Integer.parseInt(idOrdenStr);

            // Validar y parsear copias
            String copiasStr = request.getParameter("copias");
            if (copiasStr == null || copiasStr.isEmpty()) {
                throw new IllegalArgumentException("Número de copias no proporcionado.");
            }
            int copias = Integer.parseInt(copiasStr);

            // Validar tipo_impresion
            String tipoImpresion = request.getParameter("tipo_impresion");
            if (tipoImpresion == null || tipoImpresion.isEmpty()
                    || (!"color".equals(tipoImpresion) && !"blanco_negro".equals(tipoImpresion))) {
                throw new IllegalArgumentException("Tipo de impresión inválido.");
            }

            // Validar y parsear id_material
            String idMaterialStr = request.getParameter("material");
            if (idMaterialStr == null || idMaterialStr.isEmpty()) {
                throw new IllegalArgumentException("ID de material no proporcionado.");
            }
            int idMaterial = Integer.parseInt(idMaterialStr);

            // Validar y parsear id_dimensiones
            String idDimensionesStr = request.getParameter("dimensiones");
            if (idDimensionesStr == null || idDimensionesStr.isEmpty()) {
                throw new IllegalArgumentException("ID de dimensiones no proporcionado.");
            }
            int idDimensiones = Integer.parseInt(idDimensionesStr);

            // Determinar el tipo de impresión
            boolean color = "color".equals(tipoImpresion);
            boolean bn = "blanco_negro".equals(tipoImpresion);

            // Calcular el precio
            int precioPorCopia = color ? 600 : 400;
            int totalPrecio = precioPorCopia * copias;

            // Crear el objeto Impresion
            Impresion impresion = new Impresion();
            impresion.setIdImagen(idImagen);
            impresion.setIdMaterial(idMaterial);
            impresion.setIdDimensiones(idDimensiones); // Establecer dimensiones
            impresion.setCopias(copias);
            impresion.setColor(color);
            impresion.setBn(bn);
            impresion.setIdEstado(1); // Estado inicial
            impresion.setIdOrden(idOrden); // Usar id_orden

            // Guardar la impresión en la base de datos
            ImpresionDAO impresionDAO = new ImpresionDAO();
            impresionDAO.guardarImpresion(impresion);

            // Enviar el resultado a la página de confirmación
            request.setAttribute("totalPrecio", totalPrecio);
            request.getRequestDispatcher("Confirmacion.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Formato de número inválido. Verifique los valores ingresados.");
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al procesar la configuración de impresión.");
            request.getRequestDispatcher("clienteimpresion.jsp").forward(request, response);
        }
    }

    private int insertarOrden(String email) throws SQLException {
        int idOrden = -1;
        String url = "jdbc:mysql://localhost:3306/gestorimpresiones";
        String user = "root";
        String password = "";  // Reemplaza con la contraseña real si es necesario

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO orden (fecha) VALUES (NOW())";
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idOrden = rs.getInt(1);
                    }
                }
            }
        }
        return idOrden;
    }

    private void handleObtenerImpresiones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email != null) {
            ImpresionDAO impresionDAO = new ImpresionDAO();
            List<Impresion> impresiones = impresionDAO.obtenerImpresionesPorUsuario(email);

            request.setAttribute("impresiones", impresiones);
            request.getRequestDispatcher("cliente.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "No se encontraron impresiones para este usuario.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

}
