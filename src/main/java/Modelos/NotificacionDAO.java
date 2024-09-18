import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {
    Modelos.Conexion cone = new Modelos.Conexion();
    public List<Notificacion> obtenerNotificacionesPorUsuario(String email) {
        List<Notificacion> listaNotificaciones = new ArrayList<>();
        try {
            Connection con = cone.getConnection();
            String sql = "SELECT n.id_notificacion, n.id_orden, o.fecha "
                       + "FROM notificaciones n "
                       + "JOIN orden o ON n.id_orden = o.id_orden "
                       + "WHERE n.id_usuario = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notificacion notificacion = new Notificacion();
                notificacion.setIdNotificacion(rs.getInt("id_notificacion"));
                notificacion.setIdOrden(rs.getInt("id_orden"));
                notificacion.setFechaOrden(rs.getTimestamp("fecha"));
                listaNotificaciones.add(notificacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaNotificaciones;
    }
}
