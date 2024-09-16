package Modelos;

public class Usuario {
    private String email;
    private String contraseña;
    private int rol;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    // Constructor con todos los campos
    public Usuario(String email, String contraseña, int rol) {
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}
