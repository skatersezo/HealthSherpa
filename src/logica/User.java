/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package logica;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



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
    private double altura;
    private int nivel_actividad;
    private int objetivo;
    private int tipoPlan;
    
    //consultas sql
    public static String NOMBRE_TABLA = "user";
    public static String SELECCIONAR_TODO = "select * from "+User.NOMBRE_TABLA;
    
    //constantes de clase
    public static final String HOMBRE = "hombre";
    public static final String MUJER = "mujer";
    public static final int SEDENTARIO = 1;
    public static final int ALGO_ACTIVO = 2;
    public static final int ACTIVO = 3;
    public static final int MUY_ACTIVO = 4;
    public static final int DEPORTISTA_PROFESIONAL = 5;
    public static final int DEFINICION=1;
    public static final int MANTENIMIENTO=2;
    public static final int VOLUMEN=3;

    
    
    //constructor por defecto

    public User() {
    }
    
    //constructor con parámetros

    public User(String usuario, String passwd) {
        this.usuario = usuario;
        this.passwd = passwd;
    }
    
    //getters y setters

    public int getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(int tipoPlan) {
        this.tipoPlan = tipoPlan;
    }
    
    

    public int getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(int objetivo) {
        this.objetivo = objetivo;
    }
    
    

    public int getNivel_actividad() {
        return nivel_actividad;
    }

    public void setNivel_actividad(int nivel_actividad) {
        this.nivel_actividad = nivel_actividad;
    }
    
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
    
    public double getAltura(){
        return altura;
    }
    
    public void setAltura(double altura){
        this.altura=altura;
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
        
        String sql = "INSERT INTO user (nombre, password, genero, altura, nivel_actividad, objetivos, tipo_plan, birthdate)"+
       "VALUES ('"+this.usuario+"', '"+this.passwd+"', '"+this.genero+"', "+this.altura+", "+this.nivel_actividad+", "+this.objetivo+", "+this.tipoPlan+", '"+sqlDate+"')";
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarNombreyPass()
    {
        String sql = "UPDATE user SET "+
                    "nombre = '"+this.usuario+"',"+
                    "password = '"+this.passwd+"'"+
                   " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarObjetivo()
    {
        String sql = "UPDATE user SET "+
                    "objetivos = "+this.objetivo+
                   " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarTipoPlan()
    {
        String sql = "UPDATE user SET "+
                "tipo_plan = "+this.tipoPlan+
                " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarNivelActividad()
    {
        String sql = "UPDATE user SET "+
                "nivel_actividad = "+this.nivel_actividad+
                " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int borrar()
    {
        String sql = "DELETE FROM user WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarNombreUsuario()
    {
        String sql = "UPDATE user SET "+
                    "nombre = '"+this.usuario+"'"+
                   " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
    }
    
    public int actualizarPassword()
    {
        String sql = "UPDATE user SET "+
                    "password = '"+this.passwd+"'"+
                   " WHERE id_user = "+this.idUsuario;
        return ConexionBBDD.guardarRegistro(sql);
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
                usuario.setAltura(users.getDouble("altura"));//altura
                usuario.setNivel_actividad(users.getInt("nivel_actividad"));//nivel de actividad
                usuario.setTipoPlan(users.getInt("tipo_plan"));//moderado en ch o cetogenico
                usuario.setObjetivo(users.getInt("objetivos"));//objetivo
                usuario.setBirthdate(new java.util.Date(users.getDate("birthdate").getTime()));//fecha nacimiento
                ListaUsuarios.add(usuario);
            }
            users.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
        return ListaUsuarios;
    }
    /**
     * Comprueba si un usuario existe en la base de datos
     * @param nombre
     * @return 
     */
    public static boolean isAlready(String nombre)
    {
        ResultSet users = ConexionBBDD.getRegistros(User.SELECCIONAR_TODO+" where nombre like '"+nombre+"'");
        try {
            if(users.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return false;
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
                usuario.setAltura(users.getDouble("altura"));//altura
                usuario.setNivel_actividad(users.getInt("nivel_actividad"));//nivel de actividad
                usuario.setTipoPlan(users.getInt("tipo_plan"));//moderado en ch o cetogenico
                usuario.setObjetivo(users.getInt("objetivos"));//objetivo
                usuario.setBirthdate(new java.util.Date(users.getDate("birthdate").getTime()));//fecha nacimiento          
            }
            users.close();
            ConexionBBDD.con.close();
        }catch(SQLException ex){ex.printStackTrace();}
           return usuario;
    }
    
    public void cargaID()
    {
        String sql = User.SELECCIONAR_TODO+" WHERE nombre = '"+this.usuario+"' limit 1";
        ResultSet user = ConexionBBDD.getRegistros(sql);
        try{
        user.next();
        this.idUsuario=user.getInt(1);
        }catch(SQLException ex){ex.printStackTrace();}
        
    }
    
}
