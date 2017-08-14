/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package healthsherpa;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 *
 * @author Iván Taghavi Espinosa
 */
public class User {
    
    private int idUsuario;
    private String usuario;
    private String passwd;
    
    //definicion de atributos
     
    private String genero;
    private java.util.Date birthdate;
    
    //consultas sql
    public static String NOMBRE_TABLA = "user";
    public static String SELECCIONAR_TODO = "select * from "+User.NOMBRE_TABLA;
    
    //constructor por defecto

    public User() {
    }
    
    //constructor con parámetros

    public User(String usuario, String passwd) {
        this.usuario = usuario;
        this.passwd = passwd;
    }
    
    //getters y setters

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    public String getGenero(){
        return genero;
    }
    
    public void setGenero(String genero){
        this.genero=genero;
    }
    
    public java.util.Date getBirthdate(){
        return birthdate;
    }
    
    public void setBirthdate(java.util.Date birthdate){
        this.birthdate=birthdate;
    }
    
    //metodos estaticos de consulta
    public static String getAnterior(int idUser)
    {
        return User.SELECCIONAR_TODO+" where id_user < "+idUser+" limit 1";
    }
    public static String getSiguiente(int idUser)
    {
        return User.SELECCIONAR_TODO+" where id_user > "+idUser+" limit 1";
    }
    
    public static String getPrimero()
    {
        return User.SELECCIONAR_TODO+" limit 1";
    }
     
    public static String getUltimo()
    {
        return User.SELECCIONAR_TODO+" order by id_user desc limit 1";
    }
    
    public int grabar()
    {
       //Cambio del tipo de datos util.Date a sql.Date
        java.sql.Date sqlDate = new java.sql.Date(this.birthdate.getTime());
        
        String sql = "INSERT INTO user (nombre, password, genero, birthdate)"+
       "VALUES ('"+this.usuario+"','"+this.passwd+","+this.genero+","+sqlDate+")";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizar()
    {
        String sql = "UPDATE user SET "+
                    "nombre = '"+this.usuario+"',"+
                    "password = '"+this.passwd+
                   " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int borrar()
    {
        String sql = "DELETE FROM user WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int cambiarNombreDeUsuario(String user)
    {
        String sql = "UPDATE user SET nombre = "+user+" WHERE id_user = "+this.idUsuario;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.usuario = user;
            return gr;
        }
        return 0;
    }
    
    public int cambiarPassword(String pass)
    {
        String sql = "UPDATE user SET password = "+pass+" WHERE id_user = "+this.idUsuario;
        int gr = ConexionBBDD.guardarRegistro(sql);
        if(gr==1)
        {
            this.passwd = pass;
            return gr;
        }
        return 0;
    }
    
    public static ArrayList<User> getUsuarios()
    {
        ResultSet users = ConexionBBDD.getRegistros(User.SELECCIONAR_TODO);
        ArrayList<User> ListaUsuarios = new ArrayList();
        try{
            while(users.next())
            {
                User usuario = new User();
                usuario.setIdUsuario(users.getInt("id_user"));//idusuario
                usuario.setUsuario(users.getString("nombre"));//usuario
                usuario.setPasswd(users.getString("password"));//contraseña
                usuario.setGenero(users.getString("genero"));//genero
                usuario.setBirthdate(new java.util.Date(users.getDate("birthdate").getTime()));//fecha nacimiento
                ListaUsuarios.add(usuario);
            }
            users.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaUsuarios;
    }
    
    public static ArrayList<User> getBuscar(String nombre)
    {
        ResultSet users = ConexionBBDD.getRegistros(User.SELECCIONAR_TODO+" where nombre like '"+nombre+"%'");
        ArrayList<User> ListaUsuarios = new ArrayList();
        try{
            while(users.next())
            {
                User usuario = new User();
                usuario.setIdUsuario(users.getInt("id_user"));//idusuario
                usuario.setUsuario(users.getString("nombre"));//usuario
                usuario.setPasswd(users.getString("password"));//contraseña
                usuario.setGenero(users.getString("genero"));//genero
                usuario.setBirthdate(new java.util.Date(users.getDate("birthdate").getTime()));//fecha nacimiento
                ListaUsuarios.add(usuario);
            }
            users.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        if(ListaUsuarios.isEmpty())
            return null;
        
        return ListaUsuarios;
    }
    public static User Login(String user, String passwd)
    {
        String sql = User.SELECCIONAR_TODO+" where nombre = '"+user+"' and password = '"+passwd+"'";
        ResultSet users = ConexionBBDD.getRegistros(sql);
        User usuario = null;
           try{
            while(users.next())
            {
                usuario = new User();
                usuario.setIdUsuario(users.getInt("id_user"));//idusuario
                usuario.setUsuario(users.getString("nombre"));//usuario
                usuario.setPasswd(users.getString("password"));//contraseña
                usuario.setGenero(users.getString("genero"));//genero
                usuario.setBirthdate(new java.util.Date(users.getDate("birthdate").getTime()));//fecha nacimiento          
            }
            users.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
           return usuario;
    }
    
    
    
}
