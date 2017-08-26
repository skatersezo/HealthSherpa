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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class Registros {
    
    private int idRegistro;
    private int id_usuario;
    private double peso;
    private double cintura;
    private double cuello;
    private double cadera;
    private java.util.Date fecha;
    
    //consultas sql
    public static String NOMBRE_TABLA = "registros";
    public static String SELECCIONAR_TODO = "select * from "+Registros.NOMBRE_TABLA;
    
    //constructor por defecto

    public Registros() {
    }
    
    //constructor con parámetros

    public Registros(int idRegistro, int id_usuario) {
        this.idRegistro = idRegistro;
        this.id_usuario = id_usuario;
    }

    public Registros(int id_usuario, double peso, double cintura, double cuello, double cadera, Date fecha) {
        this.id_usuario = id_usuario;
        this.peso = peso;
        this.cintura = cintura;
        this.cuello = cuello;
        this.cadera = cadera;
        this.fecha = fecha;
    }
    
    
    
    //getters y setters

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    public void setCuello(double cuello) {
        this.cuello = cuello;
    }

    public void setCadera(double cadera) {
        this.cadera = cadera;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public double getPeso() {
        return peso;
    }

    public double getCintura() {
        return cintura;
    }

    public double getCuello() {
        return cuello;
    }

    public double getCadera() {
        return cadera;
    }

    public Date getFecha() {
        return fecha;
    }
    
    //metodos estaticos de consulta
    public static String getAnterior(int id_user)
    {
        return Registros.SELECCIONAR_TODO+" where id_user < "+id_user+" limit 1";
    }
    public static String getSiguiente(int idUser)
    {
        return Registros.SELECCIONAR_TODO+" where id_user > "+idUser+" limit 1";
    }
    
    public static String getPrimero(int id_user)
    {
        return Registros.SELECCIONAR_TODO+" where id_user = "+id_user+" order by fecha_reg asc limit 1";
    }
     
    public static String getUltimo(int id_user)
    {
        return Registros.SELECCIONAR_TODO+" where id_user = "+id_user+" order by id_registro desc limit 1";
    }
    
    public int grabar()
    {
       //Cambio del tipo de datos util.Date a sql.Date
        java.sql.Date sqlDate = new java.sql.Date(this.fecha.getTime());
        
        String sql = "INSERT INTO registros (id_user, peso, cintura, cuello, cadera, fecha_reg)"+
       "VALUES ("+this.id_usuario+","+this.peso+","+this.cintura+","+this.cuello+","+this.cadera+",'"+sqlDate+"')";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizar()
    {
        //Cambio del tipo de datos util.Date a sql.Date
        java.sql.Date sqlDate = new java.sql.Date(this.fecha.getTime());
        
        String sql = "UPDATE registros SET "+
                    "peso = '"+this.peso+"',"+
                    "cintura = '"+this.cintura+
                    "cuello = '"+this.cuello+
                    "cadera = '"+this.cadera+
                    "fecha_reg = '"+sqlDate+"'"+
                   " WHERE id_user = "+this.id_usuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int borrar()
    {
        String sql = "DELETE FROM registros WHERE id_user = "+this.id_usuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int cambiarPesoUsuario(double peso)
    {
        String sql = "UPDATE registros SET peso = "+peso+" WHERE id_registro = "+this.idRegistro;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.peso = peso;
            return gr;
        }
        return 0;
    }
    
    public int cambiarCinturaUsuario(double cintura)
    {
        String sql = "UPDATE registros SET cintura = "+cintura+" WHERE id_registro = "+this.idRegistro;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.cintura = cintura;
            return gr;
        }
        return 0;
    }
    
    public int cambiarCuelloUsuario(double cuello)
    {
        String sql = "UPDATE registros SET cuello = "+cuello+" WHERE id_registro = "+this.idRegistro;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.cuello = cuello;
            return gr;
        }
        return 0;
    }
    
    public int cambiarCaderaUsuario(double peso)
    {
        String sql = "UPDATE registros SET cadera = "+cadera+" WHERE id_registro = "+this.idRegistro;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.cadera = cadera;
            return gr;
        }
        return 0;
    }
    /**
     * Obtiene todos los registros de un usuario
     * @param id_user
     * @return 
     */
    
    public static Registros getUltimoRegistro(int id_user)
    {
        ResultSet registros = ConexionBBDD.getRegistros(Registros.getUltimo(id_user));
        Registros ultimo = new Registros();
        try{
            registros.next();
        ultimo.setPeso(registros.getDouble("peso"));//peso 
        ultimo.setCintura(registros.getDouble("cintura"));//cintura 
        ultimo.setCuello(registros.getDouble("cuello"));//contraseña
        ultimo.setCadera(registros.getDouble("cadera"));//cadera
        ultimo.setFecha(new java.util.Date(registros.getDate("fecha_reg").getTime()));//fecha
        
        registros.close();
        ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ultimo;
    }
    public static ArrayList<Registros> getRegistros(int id_user)
    {
        ResultSet registros = ConexionBBDD.getRegistros(Registros.SELECCIONAR_TODO+" WHERE id_user="+id_user+" order by fecha_reg desc");
        
        ArrayList<Registros> ListaRegistros = new ArrayList();
        try{
            
            while(registros.next())
            {
                Registros registro = new Registros();
                registro.setPeso(registros.getDouble("peso"));//peso 
                registro.setCintura(registros.getDouble("cintura"));//cintura 
                registro.setCuello(registros.getDouble("cuello"));//contraseña
                registro.setCadera(registros.getDouble("cadera"));//cadera
                registro.setFecha(new java.util.Date(registros.getDate("fecha_reg").getTime()));//fecha 
                ListaRegistros.add(registro);
            }
            registros.close();
            ConexionBBDD.con.close();
            
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaRegistros;
    }
    
    
    
    
    
    
}
