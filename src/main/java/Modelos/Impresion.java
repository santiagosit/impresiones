package Modelos;

public class Impresion {
    
    private int idImagen;
    private int idMaterial;
    private int idDimensiones; // Nuevo campo
    private int copias;
    private boolean color;
    private boolean bn;
    private int idEstado;
    private int idOrden;

    public Impresion() {
    }

    public Impresion(int idImagen, int idMaterial, int idDimensiones, int copias, boolean color, boolean bn, int idEstado, int idOrden) {
        this.idImagen = idImagen;
        this.idMaterial = idMaterial;
        this.idDimensiones = idDimensiones;
        this.copias = copias;
        this.color = color;
        this.bn = bn;
        this.idEstado = idEstado;
        this.idOrden = idOrden;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdDimensiones() {
        return idDimensiones;
    }

    public void setIdDimensiones(int idDimensiones) {
        this.idDimensiones = idDimensiones;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isBn() {
        return bn;
    }

    public void setBn(boolean bn) {
        this.bn = bn;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    
}
