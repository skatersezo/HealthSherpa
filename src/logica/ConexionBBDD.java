/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package logica;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Iván Taghavi Espinosa
 */
public class ConexionBBDD {   
    
    public static String bd = "healthsherpa";
    public static String login = "root";
    public static String password = "";
    public static String url = "jdbc:mysql://localhost:3306/" + ConexionBBDD.bd;
    public Statement stmt;//consultas
    public static Connection con;
    public ResultSet rs;
    public ResultSetMetaData rsMeta;
    /**
     * Conecta a la base de datos
     */
    public static void Conectar()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, login, password);
            System.out.println("CONEXION EXITOSA");
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e)
        {
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
        }

    }
    
    /**
     * 
     * @param sql sentencia sql como insertar, actualizar y eliminar
     * @return 0 si no hubo ningun cambio, devuelve 1 si hubo algun cambio 
     * en los registros
     */
    public static int guardarRegistro(String sql)
    {
        ConexionBBDD.Conectar();
        try{
            Statement st = ConexionBBDD.con.createStatement();
            int eu = st.executeUpdate(sql);
            st.close();
            ConexionBBDD.con.close();
            return eu;
            
        }catch(SQLException ex){           
          ex.printStackTrace();
        }           
        return 0;
    }
    
    public static ResultSet getRegistros(String sql)
    {
        ConexionBBDD.Conectar();
        try{
            Statement st = ConexionBBDD.con.createStatement();
            ResultSet eq = st.executeQuery(sql);            
            return eq;
            
        }catch(SQLException ex){           
          ex.printStackTrace();
        }
        
        return null;
    }

    
}