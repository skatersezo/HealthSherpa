/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package healthsherpa;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class Validaciones extends Exception {
    
    public Validaciones(){}
    
    public Validaciones(String msg){
        super(msg);
    }
    /**
     * Método para validar la edad
     * @param fechanacimiento
     * @throws Validaciones 
     */
    public static void CompruebaEdad(Calendar fechanacimiento) throws Validaciones{
        int edad = Calculos.calcularEdad(fechanacimiento);
        if(edad<16 || edad > 100){
            throw new Validaciones("La edad de uso permitida está entre los 16 y los 100 años.");
        }
    }
    /**
     * Comprueba altura
     * @param altura
     * @throws Validaciones 
     */
    public static void CompruebaAltura(double altura) throws Validaciones{
        
        if(altura < 30 || altura > 350){
            throw new Validaciones("La altura introducida es irreal.");
        }
    }
    /**
     * Comprueba peso
     * @param peso
     * @throws Validaciones 
     */
    public static void CompruebaPeso(double peso) throws Validaciones{
        
        if(peso<30 || peso > 300){
            throw new Validaciones("El peso introducido es irreal.");
        }
    }
    /**
     * Comprueba cm cuello
     * @param cuello
     * @throws Validaciones 
     */
    public static void CompruebaCuello(double cuello) throws Validaciones{
        
        if(cuello<25 || cuello > 150){
            throw new Validaciones("La medida introducida para el cuello es irreal.");
        }
    }
    /**
     * Comprueba cm cintura
     * @param cintura
     * @throws Validaciones 
     */
    public static void CompruebaCintura(double cintura) throws Validaciones{
        
        if(cintura<40 || cintura > 500){
            throw new Validaciones("La medida introducida para la cintura es irreal.");
        }
    }
    /**
     * Comprueba cm cadera
     * @param cadera
     * @throws Validaciones 
     */
    public static void CompruebaCadera(double cadera) throws Validaciones{
        
        if(cadera<50 || cadera > 600){
            throw new Validaciones("La medida introducida para la cadera es irreal.");
        }
    }
}
