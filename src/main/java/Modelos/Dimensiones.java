package Modelos;

public class Dimensiones extends Material {
    private int alto;
    private int ancho;

    // Constructor vacío
    public Dimensiones() {
        super(); // Llama al constructor de la clase padre (Material)
    }

    // Constructor con parámetros
    public Dimensiones(int idMaterial, String nombreMaterial, int idDimensiones, int alto, int ancho) {
        super(idMaterial, nombreMaterial, idDimensiones); // Llama al constructor de Material
        this.alto = alto;
        this.ancho = ancho;
    }

    // Getters y Setters
    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
}
