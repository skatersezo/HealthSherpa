/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package healthsherpa;

import java.util.Calendar;

/**
 *
 * @author ivans
 */
public class Calculos {
    
    /**
     * Método para calcular los años de una persona
     * @param fechaNac
     * @return años
     */
    
     public static int calcularEdad(Calendar fechaNac) {
        Calendar fechaActual = Calendar.getInstance();
 
        // Cálculo de las diferencias.
        int years = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        int months = fechaActual.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
        int days = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
 
        // Hay que comprobar si el día de su cumpleaños es posterior
        // a la fecha actual, para restar 1 a la diferencia de años,
        // pues aún no ha sido su cumpleaños.
 
        if(months < 0 // Aún no es el mes de su cumpleaños
           || (months==0 && days < 0)) { // o es el mes pero no ha llegado el día.
            years--;
        }
        return years;
    }
    /**
     * Calcula Tasa metabólica basal
     * @param usuario
     * @param peso
     * @return 
     */
     public static double calcularTMB(User usuario, double peso)
     {
         if(usuario.getGenero().equals(User.HOMBRE))
         {
             return (10*peso)+(6.25*usuario.getAltura())-(5*Calculos.calcularEdad(Calculos.toCalendar(usuario.getBirthdate()))) - 161;
         } else
         {
             return (10*peso)+(6.25*usuario.getAltura())-(5*Calculos.calcularEdad(Calculos.toCalendar(usuario.getBirthdate()))) + 5;
         }
     }
     
     public static Calendar toCalendar(java.util.Date date){ 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
}
     /**
      * calcular gasto energético total
      * @param usuario
      * @param tmb
      * @return 
      */
     public static double calcularGET(User usuario, double tmb)
     {
        double get=0;
         switch(usuario.getNivel_actividad()){
             case 0:
                  get=tmb*1.2;
                 break;
             case 1:
                 get= tmb*1.375;
                 break;
             case 2:
                 get= tmb*1.55;
                 break;
             case 3:
                 get= tmb*1.725;
                 break;
             case 4:
                 get = tmb*1.9;
                 break;
         }
         return get;
     }
     /**
      * Calcular porcentaje de grasa corporal
      * @param usuario
      * @param cintura
      * @param cuello
      * @param cadera
      * @return 
      */
     public static double calcularPorcentajeGrasa(User usuario, double cintura, double cuello, double cadera){
        double fatPercent = 0;
         
         if(usuario.getGenero().equals(User.HOMBRE))
         {
             
             fatPercent = -1*(495/(1.0324-0.19077*(Math.log(cintura-cuello))+0.15456*(Math.log(usuario.getAltura()))) - 450);
         }else
         {
             fatPercent = -1*(495/(1.29579-0.35004*(Math.log(cadera+cintura-cuello))+0.221*(Math.log(usuario.getAltura()))) - 450);
         }
         return fatPercent;
     }
     /**
      * Calcular kg de masa magra
      * @param peso
      * @param porcentaje_grasa
      * @return 
      */
     public static double calcularMasaMagra(double peso, double porcentaje_grasa)
     {
         return peso * (porcentaje_grasa/100);
         
     }
}
