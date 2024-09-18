package Modelos;

/**
 *
 * @author Johan Herrera
 */
public class Imagen {

    private int id;
    private String email;
    private String nombre;
    private byte[] imagenBlob;

    public Imagen() {
    }

    public Imagen(String email, String nombre, byte[] imagenBlob) {
        this.email = email;
        this.nombre = nombre;
        this.imagenBlob = imagenBlob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagenBlob() {
        return imagenBlob;
    }

    public void setImagenBlob(byte[] imagenBlob) {
        this.imagenBlob = imagenBlob;
    }

}
