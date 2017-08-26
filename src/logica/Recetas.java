/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class Recetas {
    
    private int id_receta;
    
     //definicion de atributos
     
    private String nombre;
    private String descripcion;
    private double calorias;
    private double proteina;
    private double grasas;
    private double ch;
    
    //consultas sql
    public static String NOMBRE_TABLA = "recetas";
    public static String SELECCIONAR_TODO = "select * from "+Recetas.NOMBRE_TABLA;
    
    //constructor por defecto

    public Recetas() {
    }
    
    //constructor con parámetros

    public Recetas(String nombre, String descripcion) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        
    }
    
    //getters y setters

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getProteina() {
        return proteina;
    }

    public void setProteina(double proteina) {
        this.proteina = proteina;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public double getCh() {
        return ch;
    }

    public void setCh(double ch) {
        this.ch = ch;
    }
    

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //metodos estaticos de consulta
    public static String getAnterior(int id_receta)
    {
        return Recetas.SELECCIONAR_TODO+" where id_receta < "+id_receta+" limit 1";
    }
    public static String getSiguiente(int id_receta)
    {
        return Recetas.SELECCIONAR_TODO+" where id_receta > "+id_receta+" limit 1";
    }
    
    public static String getPrimero()
    {
        return Recetas.SELECCIONAR_TODO+" limit 1";
    }
     
    public static String getUltimo()
    {
        return Recetas.SELECCIONAR_TODO+" order by id_receta desc limit 1";
    }
    
    public int grabar()
    {
        String sql = "INSERT INTO recetas (nombre, descripcion)"+
       "VALUES ('"+this.nombre+"', '"+this.descripcion+"')";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarReceta()
    {
        String sql = "UPDATE recetas SET "+
                    "nombre = '"+this.nombre+"',"+
                    "descripcion = '"+this.descripcion+"'"+
                   " WHERE id_receta = "+this.id_receta;
        return ConexionBBDD.guardarRegistro(sql);
    }
   
    public int borrar()
    {
        String sql = "DELETE FROM recetas WHERE id_receta = "+this.id_receta;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int cambiarNombreDeReceta(String nombre)
    {
        String sql = "UPDATE recetas SET nombre = '"+nombre+"' WHERE id_receta = "+this.id_receta;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.nombre = nombre;
            return gr;
        }
        return 0;
    }
    
    public static Recetas cargaReceta(int id_receta)
    {
        String sql = Recetas.SELECCIONAR_TODO+" where id_receta = "+id_receta;
        ResultSet recetas = ConexionBBDD.getRegistros(sql);
        Recetas receta = null;
           try{
            while(recetas.next())
            {
                receta = new Recetas();
                receta.setId_receta(recetas.getInt("id_receta"));//id receta
                receta.setNombre(recetas.getString("nombre"));//nombre
                receta.setDescripcion(recetas.getString("descripcion"));//descripcion        
            }
            recetas.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
           return receta;
    }
    
    public static Recetas cargaUltimaReceta()
    {
        String sql = Recetas.getUltimo();
        ResultSet recetas = ConexionBBDD.getRegistros(sql);
        Recetas receta = null;
           try{
            while(recetas.next())
            {
                receta = new Recetas();
                receta.setId_receta(recetas.getInt("id_receta"));//id receta
                receta.setNombre(recetas.getString("nombre"));//nombre
                receta.setDescripcion(recetas.getString("descripcion"));//descripcion        
            }
            recetas.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
           return receta;
    }
    
    public static ArrayList<Recetas> getRecetas()
    {
        ResultSet comida = ConexionBBDD.getRegistros(Recetas.SELECCIONAR_TODO);
        ArrayList<Recetas> ListaRecetas = new ArrayList();
        try{
            while(comida.next())
            {
                Recetas receta = new Recetas();
                receta.setId_receta(comida.getInt("id_receta"));//id receta
                receta.setNombre(comida.getString("nombre"));//nombre
                receta.setDescripcion(comida.getString("descripcion"));//descripcion
                receta.cargaValores();
                ListaRecetas.add(receta);
            }
            comida.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaRecetas;
    }
    
    public ArrayList<Ingrediente> getIngredientes()
    {
        ResultSet ingredientes = ConexionBBDD.getRegistros("SELECT alimentos.id_alimento, alimentos.nombre, ingredientes.cantidad FROM alimentos INNER JOIN ingredientes ON alimentos.id_alimento=ingredientes.id_alimento WHERE ingredientes.id_receta="+this.id_receta);
        ArrayList<Ingrediente> ListaIngredientes = new ArrayList();
        try{
            while(ingredientes.next())
            {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setId_alimento(ingredientes.getInt("id_alimento"));//id_alimento
                ingrediente.setNombre(ingredientes.getString("nombre"));//nombre alimento
                ingrediente.setCantidad(ingredientes.getDouble("cantidad"));//cantidad del alimento
                ListaIngredientes.add(ingrediente);
            }
            ingredientes.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaIngredientes;
    }
    

    public void cargaValores(){
        ResultSet ingredientes = ConexionBBDD.getRegistros("SELECT alimentos.ct, alimentos.prot, alimentos.gr, alimentos.ch, ingredientes.cantidad FROM alimentos INNER JOIN ingredientes ON alimentos.id_alimento=ingredientes.id_alimento WHERE ingredientes.id_receta="+this.id_receta);
        
        try{
            while(ingredientes.next())
            {
                Ingrediente ingrediente = new Ingrediente();
                this.calorias += (ingredientes.getDouble("ct")*ingredientes.getDouble("cantidad"))/100;//calorias de la receta
                this.proteina += (ingredientes.getDouble("prot")*ingredientes.getDouble("cantidad"))/100;//proteina de la receta
                this.grasas += (ingredientes.getDouble("gr")*ingredientes.getDouble("cantidad"))/100;//grasas de la receta
                this.ch += (ingredientes.getDouble("ch")*ingredientes.getDouble("cantidad"))/100;//ch de la receta
            }
            ingredientes.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
    }
    
    @Override
    public String toString() {
        return "<html>" +nombre + "<br> calorias=" + String.format("%.2f", calorias) + " Cal, proteina=" + String.format("%.2f", proteina) + "gr, grasas=" + String.format("%.2f", grasas) + "gr, ch=" + String.format("%.2f", ch) + " gr</html>";    
        }
}