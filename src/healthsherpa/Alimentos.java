/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package healthsherpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class Alimentos {
    
    private int id_alimento;
    private int id_categoria;
    
    //definicion de atributos
     
    private String nombre;
    private double calorias;
    private double proteina;
    private double grasas;
    private double ch;
    
    //consultas sql
    public static String NOMBRE_TABLA = "alimentos";
    public static String SELECCIONAR_TODO = "select * from "+Alimentos.NOMBRE_TABLA;
    
    //constructor por defecto

    public Alimentos() {
    }
    
    //constructor con parámetros

    public Alimentos(int id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }
    
    //getters y setters

    public int getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    public double getCh() {
        return ch;
    }

    public void setCh(double ch) {
        this.ch = ch;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    
    
    //metodos estaticos de consulta
    public static String getAnterior(int id_alimento)
    {
        return Alimentos.SELECCIONAR_TODO+" where id_alimento < "+id_alimento+" limit 1";
    }
    public static String getSiguiente(int id_alimento)
    {
        return Alimentos.SELECCIONAR_TODO+" where id_alimento > "+id_alimento+" limit 1";
    }
    
    public static String getPrimero()
    {
        return Alimentos.SELECCIONAR_TODO+" limit 1";
    }
     
    public static String getUltimo()
    {
        return Alimentos.SELECCIONAR_TODO+" order by id_alimento desc limit 1";
    }
    
    public int grabar()
    {
       
        String sql = "INSERT INTO alimentos (id_categoria, nombre, ct, prot, gr, ch)"+
       "VALUES ("+this.id_categoria+", '"+this.nombre+"', "+this.calorias+", "+this.proteina+", "+this.grasas+", "+this.ch+")";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarAlimento()
    {
        String sql = "UPDATE alimentos SET "+
                    "nombre = '"+this.nombre+"',"+
                    "ct = '"+this.calorias+
                    "prot = '"+this.proteina+
                    "gr = '"+this.grasas+
                    "ch = '"+this.ch+
                   " WHERE id_alimento = "+this.id_alimento;
        return ConexionBBDD.guardarRegistro(sql);
    }
   
    public int borrar()
    {
        String sql = "DELETE FROM alimentos WHERE id_alimento = "+this.id_alimento;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int cambiarNombreDeAlimento(String nombre)
    {
        String sql = "UPDATE alimentos SET nombre = "+nombre+" WHERE id_alimento = "+this.id_alimento;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.nombre = nombre;
            return gr;
        }
        return 0;
    }
    
    public static Alimentos cargaAlimento(int id_alimento)
    {
        String sql = Alimentos.SELECCIONAR_TODO+" where id_alimento = "+id_alimento;
        ResultSet alimentos = ConexionBBDD.getRegistros(sql);
        Alimentos alimento = null;
           try{
            while(alimentos.next())
            {
                alimento = new Alimentos();
                alimento.setId_alimento(alimentos.getInt("id_alimento"));//id alimento
                alimento.setId_categoria(alimentos.getInt("id_categoria"));//id categoria
                alimento.setCalorias(alimentos.getDouble("ct"));//calorias
                alimento.setProteina(alimentos.getDouble("prot"));//proteina
                alimento.setGrasas(alimentos.getDouble("gr"));//grasas
                alimento.setCh(alimentos.getDouble("ch"));//carbohidratos
                          
            }
            alimentos.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
           return alimento;
    }
    
    public static ArrayList<Alimentos> getAlimentos()
    {
        ResultSet comida = ConexionBBDD.getRegistros(Alimentos.SELECCIONAR_TODO);
        ArrayList<Alimentos> ListaAlimentos = new ArrayList();
        try{
            while(comida.next())
            {
                Alimentos alimento = new Alimentos();
                alimento.setId_alimento(comida.getInt("id_alimento"));//id alimento
                alimento.setId_categoria(comida.getInt("id_categoria"));//nombre categoria
                alimento.setNombre(comida.getString("nombre"));//nombre
                alimento.setCalorias(comida.getDouble("ct"));//calorias
                alimento.setProteina(comida.getDouble("prot"));//altura
                alimento.setGrasas(comida.getInt("gr"));//nivel de actividad
                alimento.setCh(comida.getInt("ch"));//carbohidratos
                ListaAlimentos.add(alimento);
            }
            comida.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaAlimentos;
    }
    
    public static ArrayList<Alimentos> getAlimentosCategoria(int id_categoria)
    {
        ResultSet comida = ConexionBBDD.getRegistros(Alimentos.SELECCIONAR_TODO+" where id_categoria = "+id_categoria);
        ArrayList<Alimentos> ListaAlimentos = new ArrayList();
        try{
            while(comida.next())
            {
                Alimentos alimento = new Alimentos();
                alimento.setId_alimento(comida.getInt("id_alimento"));//id alimento
                alimento.setId_categoria(comida.getInt("id_categoria"));//nombre categoria
                alimento.setNombre(comida.getString("nombre"));//nombre
                alimento.setCalorias(comida.getDouble("ct"));//calorias
                alimento.setProteina(comida.getDouble("prot"));//altura
                alimento.setGrasas(comida.getInt("gr"));//nivel de actividad
                alimento.setCh(comida.getInt("ch"));//carbohidratos
                ListaAlimentos.add(alimento);
            }
            comida.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaAlimentos;
    }

    @Override
    public String toString() {
        return nombre + ", calorias=" + calorias + " Cal, proteina=" + proteina + "gr, grasas=" + grasas + "gr, ch=" + ch + " gr";
    }
   
    
    
}

