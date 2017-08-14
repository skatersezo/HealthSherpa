/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package healthsherpa;

import java.util.ArrayList;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class TestBBDD {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<User> usuarios = User.getUsuarios();
        for(User us : usuarios )
        {
            System.out.println(us.getUsuario());
        }
    }
}
