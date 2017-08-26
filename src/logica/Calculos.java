/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

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
     
     public static double calcularCaloriasObjetivo(User usuario, double get)
     {
         double calObjetivo=0;
         switch(usuario.getObjetivo())
         {
             case 1:
                 calObjetivo=get - ((get*15)/100);
                 break;
             case 2:
                 calObjetivo=get;
                 break;
             case 3:
                 calObjetivo=get + ((get*15)/100);
                 break;
         }
         
         return calObjetivo;
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
             fatPercent = (495/(1.0324-0.19077*(Math.log(cintura-cuello))+0.15456*(Math.log(usuario.getAltura()))) - 450);
         }else
         {
             fatPercent = (495/(1.29579-0.35004*(Math.log(cadera+cintura-cuello))+0.221*(Math.log(usuario.getAltura()))) - 450);
         }
         
         if(fatPercent<0)
         {
             fatPercent = -1*fatPercent;
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
     
     public static double calculaSumaCalorias(ArrayList<Ingesta> listaIngesta){
         
         double sumaCalorias=0;
         if(listaIngesta.isEmpty()){
             return 0;
         } else {
             Iterator<Ingesta> itrIng = listaIngesta.iterator();
             while(itrIng.hasNext()){
                 Ingesta ingesta = itrIng.next();
                 Alimentos comida = Alimentos.cargaAlimento(ingesta.getId_alimento());
                 sumaCalorias += (comida.getCalorias()*ingesta.getCantidad())/100;
             }
             
             return sumaCalorias;
         }
        
     }
     
      public static double calculaSumaProt(ArrayList<Ingesta> listaIngesta){
         double sumaProt=0;
         if(listaIngesta.isEmpty()){
             return 0;
         } else {
             Iterator<Ingesta> itrIng = listaIngesta.iterator();
             while(itrIng.hasNext()){
                 Ingesta ingesta = itrIng.next();
                 Alimentos comida = Alimentos.cargaAlimento(ingesta.getId_alimento());
                 sumaProt += (comida.getProteina()*ingesta.getCantidad())/100;
             }
             
             return sumaProt;
         }
        
     }
     
      public static double calculaSumaGr(ArrayList<Ingesta> listaIngesta){
         double sumaGras=0;
         if(listaIngesta.isEmpty()){
             return 0;
         } else {
             Iterator<Ingesta> itrIng = listaIngesta.iterator();
             while(itrIng.hasNext()){
                 Ingesta ingesta = itrIng.next();
                 Alimentos comida = Alimentos.cargaAlimento(ingesta.getId_alimento());
                 sumaGras += (comida.getGrasas()*ingesta.getCantidad())/100;
             }
             
             return sumaGras;
         }
        
     }
      
      public static double calculaSumaCh(ArrayList<Ingesta> listaIngesta){
         double sumaCH=0;
         if(listaIngesta.isEmpty()){
             return 0;
         } else {
             Iterator<Ingesta> itrIng = listaIngesta.iterator();
             while(itrIng.hasNext()){
                 Ingesta ingesta = itrIng.next();
                 Alimentos comida = Alimentos.cargaAlimento(ingesta.getId_alimento());
                 sumaCH += (comida.getCh()*ingesta.getCantidad())/100;
             }
             
             return sumaCH;
         }
        
     }
     
      public static int calcularProtObjetivoPorcentaje(User usuario)
      {
          int objetivo = usuario.getObjetivo();
         int protObjetivo = 0;
         
         if(usuario.getTipoPlan()==0)
         {  
         switch(objetivo){
             case 1:
                 protObjetivo = 25;
                 break;
             case 2:
                 protObjetivo = 20;
                 break;
             case 3:
                 protObjetivo = 20;
         }
         
         return protObjetivo;
         } else {
             switch(objetivo){
             case 1:
                 protObjetivo = 20;
                 break;
             case 2:
                 protObjetivo = 20;
                 break;
             case 3:
                 protObjetivo = 20;
         }
         
         return protObjetivo;
         }
      }
      
      public static int calcularGrObjetivoPorcentaje(User usuario)
      {
           int objetivo = usuario.getObjetivo();
         int grObjetivo = 0;
         
         if(usuario.getTipoPlan()==0)
         {  
         switch(objetivo){
             case 1:
                 grObjetivo = 40;
                 break;
             case 2:
                 grObjetivo = 45;
                 break;
             case 3:
                 grObjetivo = 40;
         }
         
         return grObjetivo;
         } else {
             switch(objetivo){
             case 1:
                 grObjetivo = 70;
                 break;
             case 2:
                 grObjetivo = 70;
                 break;
             case 3:
                 grObjetivo = 70;
         }
         
         return grObjetivo;
         }
      }
      
      public static int calcularChObjetivoPorcentaje(User usuario){
           int objetivo = usuario.getObjetivo();
         int chObjetivo = 0;
         
         if(usuario.getTipoPlan()==0)
         {  
         switch(objetivo){
             case 1:
                 chObjetivo = 35;
                 break;
             case 2:
                 chObjetivo = 35;
                 break;
             case 3:
                 chObjetivo = 40;
         }
         
         return chObjetivo;
         } else {
             switch(objetivo){
             case 1:
                 chObjetivo = 10;
                 break;
             case 2:
                 chObjetivo = 10;
                 break;
             case 3:
                 chObjetivo = 10;
         }
         
         return chObjetivo;
         }
      }
     
     public static double calcularProtObjetivo(User usuario, double get){
         
         int objetivo = usuario.getObjetivo();
         double protObjetivo = 0;
         
         switch(objetivo){
             case 1:
                 double getDeficit = get - ((get*15)/100);
                 protObjetivo = ((getDeficit*25)/100)/4;
                 break;
             case 2:
                 protObjetivo = ((get*20)/100)/4;
                 break;
             case 3:
                 double getSuperavit = get + ((get*15)/100);
                 protObjetivo = ((getSuperavit*20)/100)/4;
         }
         
         return protObjetivo;
     }
     
     public static double calcularProtObjetivoCeto(User usuario, double get)
     {
        int objetivo = usuario.getObjetivo();
         double protObjetivo = 0;
         
         switch(objetivo){
             case 1:
                 double getDeficit = get - ((get*15)/100);
                 protObjetivo = ((getDeficit*20)/100)/4;
                 break;
             case 2:
                 protObjetivo = ((get*20)/100)/4;
                 break;
             case 3:
                 double getSuperavit = get + ((get*15)/100);
                 protObjetivo = ((getSuperavit*20)/100)/4;
         }
         
         return protObjetivo; 
     }
     
     public static double CalcularGrObjetivo(User usuario, double get){
         
         int objetivo = usuario.getObjetivo();
         double grObjetivo = 0;
         
         switch(objetivo){
             case 1:
                 double getDeficit = get - ((get*15)/100);
                 grObjetivo = ((getDeficit*40)/100)/4;
                 break;
             case 2:
                 grObjetivo = ((get*45)/100)/4;
                 break;
             case 3:
                 double getSuperavit = get + ((get*15)/100);
                 grObjetivo = ((getSuperavit*40)/100)/4;
         }
         
         return grObjetivo;
     }
     
     public static double CalcularGrObjetivoCeto(User usuario, double get)
     {
       int objetivo = usuario.getObjetivo();
         double grObjetivo = 0;
         
         switch(objetivo){
             case 1:
                 double getDeficit = get - ((get*15)/100);
                 grObjetivo = ((getDeficit*70)/100)/4;
                 break;
             case 2:
                 grObjetivo = ((get*70)/100)/4;
                 break;
             case 3:
                 double getSuperavit = get + ((get*15)/100);
                 grObjetivo = ((getSuperavit*70)/100)/4;
         }
         
         return grObjetivo;  
     }
     
     public static double CalcularChObjetivo(User usuario, double get){
         int objetivo = usuario.getObjetivo();
         double chObjetivo = 0;
         
         switch(objetivo){
             case 1:
                 double getDeficit = get - ((get*15)/100);
                 chObjetivo = ((getDeficit*35)/100)/4;
                 break;
             case 2:
                 chObjetivo = ((get*35)/100)/4;
                 break;
             case 3:
                 double getSuperavit = get + ((get*15)/100);
                 chObjetivo = ((getSuperavit*40)/100)/4;
         }
         
         return chObjetivo;
     }
     
     public static double CalcularChObjetivoCeto(User usuario, double get)
     {
       int objetivo = usuario.getObjetivo();
         double chObjetivo = 0;
         
         switch(objetivo){
             case 1:
                 double getDeficit = get - ((get*15)/100);
                 chObjetivo = ((getDeficit*10)/100)/4;
                 break;
             case 2:
                 chObjetivo = ((get*10)/100)/4;
                 break;
             case 3:
                 double getSuperavit = get + ((get*15)/100);
                 chObjetivo = ((getSuperavit*10)/100)/4;
         }
         
         return chObjetivo;  
     }
}
