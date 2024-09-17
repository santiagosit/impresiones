package Controlador;

import Modelos.DimensionDAO;
import Modelos.Dimensiones;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "GetDimensionesServlet", urlPatterns = {"/getDimensiones"})
public class GetDimensionesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int materialId = Integer.parseInt(request.getParameter("materialId"));

        DimensionDAO dimensionDAO = new DimensionDAO();
        List<Dimensiones> dimensionesList = null;
        try {
            dimensionesList = dimensionDAO.obtenerDimensionesPorMaterial(materialId);
        } catch (SQLException ex) {
            Logger.getLogger(GetDimensionesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Convertir lista de dimensiones a formato JSON
        String json = "["; // Comienza el arreglo JSON
        for (Dimensiones dimension : dimensionesList) {
            json += String.format("{\"id\": %d, \"nombre\": \"%dx%d\"},",
                    dimension.getIdDimensiones(),
                    dimension.getAlto(),
                    dimension.getAncho());
        }
        if (json.endsWith(",")) {
            json = json.substring(0, json.length() - 1); // Elimina la última coma
        }
        json += "]"; // Cierra el arreglo JSON

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
