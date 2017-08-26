/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package logica;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class Ingrediente {
    
    private int id_alimento;
    private String nombre;
    private double cantidad;

    public Ingrediente() {
    }

    public Ingrediente(int id_alimento, String nombre, double cantidad) {
        this.id_alimento=id_alimento;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + ", cantidad: " + cantidad + " gramos";
    }
    
    public int grabar(Recetas receta)
    {
        String sql = "INSERT INTO ingredientes (id_receta, id_alimento, cantidad)"+
       "VALUES ("+receta.getId_receta()+", "+this.id_alimento+", "+this.cantidad+")";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
}
