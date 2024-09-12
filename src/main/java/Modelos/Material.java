
package Modelos;

public class Material {
    private int idMaterial;
    private String nombreMaterial;
    private int idDimensiones;

    // Constructor vacío
    public Material() {}

    // Constructor con parámetros
    public Material(int idMaterial, String nombreMaterial, int idDimensiones) {
        this.idMaterial = idMaterial;
        this.nombreMaterial = nombreMaterial;
        this.idDimensiones = idDimensiones;
    }

    // Getters y Setters
    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public int getIdDimensiones() {
        return idDimensiones;
    }

    public void setIdDimensiones(int idDimensiones) {
        this.idDimensiones = idDimensiones;
    }
}

