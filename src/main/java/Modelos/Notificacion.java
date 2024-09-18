import java.sql.Timestamp;

public class Notificacion {
    private int idNotificacion;
    private int idOrden;
    private Timestamp fechaOrden;

    // Getters y Setters
    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public Timestamp getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Timestamp fechaOrden) {
        this.fechaOrden = fechaOrden;
    }
}
