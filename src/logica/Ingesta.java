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
import java.util.Date;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class Ingesta {
    
    private int id_user;
    private int id_timing;
    private int id_alimento;
    private double cantidad;
    private java.util.Date fecha;
    
    //consultas sql
    public static String NOMBRE_TABLA = "ingesta";
    public static String SELECCIONAR_TODO = "select * from "+Ingesta.NOMBRE_TABLA;

    public Ingesta() {
    }

    public Ingesta(int id_user, int id_timing, int id_alimento, double cantidad, Date fecha) {
        this.id_user = id_user;
        this.id_timing = id_timing;
        this.id_alimento = id_alimento;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_timing() {
        return id_timing;
    }

    public void setId_timing(int id_timing) {
        this.id_timing = id_timing;
    }

    public int getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    //metodos estaticos de consulta
    public static String getAnterior(int id_user)
    {
        return Ingesta.SELECCIONAR_TODO+" where id_user < "+id_user+" limit 1";
    }
    public static String getSiguiente(int id_user)
    {
        return Ingesta.SELECCIONAR_TODO+" where id_user > "+id_user+" limit 1";
    }
    
    public static String getPrimero(int id_user)
    {
        return Ingesta.SELECCIONAR_TODO+" where id_user= "+id_user+" order by fecha_ing limit 1" ;
    }
     
    public static String getUltimo(int id_user)
    {
        return Ingesta.SELECCIONAR_TODO+" where id_user= "+id_user+" order by fecha_ing desc limit 1" ;     
    }
    
    public int grabar()
    {
        //Cambio del tipo de datos util.Date a sql.Date
        java.sql.Date sqlDate = new java.sql.Date(this.fecha.getTime());
        
        String sql = "INSERT INTO ingesta (id_user, id_timing, id_alimento, cantidad, fecha_ing)"+
       "VALUES ("+this.id_user+", "+this.id_timing+", "+id_alimento+", "+cantidad+", '"+sqlDate+"')";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarReceta()
    {
        String sql = "UPDATE ingesta SET "+
                    "id_alimento = "+this.id_alimento+","+
                    "cantidad = "+this.cantidad+
                   " WHERE id_user = "+this.id_user;
        return ConexionBBDD.guardarRegistro(sql);
    }
   
    public int borrar()
    {
        String sql = "DELETE FROM ingesta WHERE id_user = "+this.id_user;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    
    
    public static Ingesta cargaUltimaIngesta(int id_user)
    {
        String sql = Ingesta.getUltimo(id_user);
        ResultSet ingestas = ConexionBBDD.getRegistros(sql);
        Ingesta ingesta = null;
           try{
            while(ingestas.next())
            {
                ingesta = new Ingesta();
                ingesta.setId_user(ingestas.getInt("id_user"));//id user
                ingesta.setId_timing(ingestas.getInt("id_timing"));//id timing
                ingesta.setId_alimento(ingestas.getInt("id_alimento"));//id alimento
                ingesta.setCantidad(ingestas.getDouble("cantidad"));//cantidad
                ingesta.setFecha((new java.util.Date(ingestas.getDate("fecha_ing").getTime()))); //fecha
            }
                ingestas.close();
                ConexionBBDD.con.close();
            
        }catch(SQLException ex){ex.printStackTrace();}
           return ingesta;
    }
     
    /**
     * Obtiene todos los registros de la ingesta de un usuario
     * @param id_user
     * @return 
     */
    public static ArrayList<Ingesta> getIngestasDesc(int id_user)
    {
        ResultSet ingestas = ConexionBBDD.getRegistros(Ingesta.SELECCIONAR_TODO+" WHERE id_user="+id_user+" order by fecha_ing desc");
        ArrayList<Ingesta> ListaIngestas = new ArrayList();
        try{
            while(ingestas.next())
            {
                Ingesta ingesta = new Ingesta();
                ingesta.setId_user(ingestas.getInt("id_user"));//id_user 
                ingesta.setId_timing(ingestas.getInt("id_timing"));//id timing 
                ingesta.setId_alimento(ingestas.getInt("id_alimento"));//id alimento
                ingesta.setFecha((new java.util.Date(ingestas.getDate("fecha_ing").getTime())));
                ListaIngestas.add(ingesta);
            }
            ingestas.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaIngestas;
    }
    
    public static ArrayList<Ingesta> getIngestasPorFecha(int id_user, Date fecha)
    {
        
        //Cambio del tipo de datos util.Date a sql.Date
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
        
        ResultSet ingestas = ConexionBBDD.getRegistros(Ingesta.SELECCIONAR_TODO+" WHERE id_user="+id_user+" and fecha_ing='"+sqlDate+"'");
        ArrayList<Ingesta> ListaIngestas = new ArrayList();
        try{
            while(ingestas.next())
            {
                Ingesta ingesta = new Ingesta();
                ingesta.setId_user(ingestas.getInt("id_user"));//id_user 
                ingesta.setId_timing(ingestas.getInt("id_timing"));//id timing 
                ingesta.setId_alimento(ingestas.getInt("id_alimento"));//id alimento
                ingesta.setCantidad(ingestas.getDouble("cantidad")); //cantidad
                ingesta.setFecha((new java.util.Date(ingestas.getDate("fecha_ing").getTime())));
                ListaIngestas.add(ingesta);
            }
            ingestas.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaIngestas;
    }
    
    public static ArrayList<Ingesta> getIngestasPorFechayTimming(int id_user, Date fecha, int id_timing)
    {
        
        //Cambio del tipo de datos util.Date a sql.Date
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
        
        ResultSet ingestas = ConexionBBDD.getRegistros(Ingesta.SELECCIONAR_TODO+" WHERE id_user="+id_user+" and fecha_ing='"+sqlDate+"' and id_timing="+id_timing);
        ArrayList<Ingesta> ListaIngestas = new ArrayList();
        try{
            while(ingestas.next())
            {
                Ingesta ingesta = new Ingesta();
                ingesta.setId_user(ingestas.getInt("id_user"));//id_user 
                ingesta.setId_timing(ingestas.getInt("id_timing"));//id timing 
                ingesta.setId_alimento(ingestas.getInt("id_alimento"));//id alimento
                ingesta.setCantidad(ingestas.getDouble("cantidad")); //cantidad
                ingesta.setFecha((new java.util.Date(ingestas.getDate("fecha_ing").getTime())));
                ListaIngestas.add(ingesta);
            }
            ingestas.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaIngestas;
    }
}
