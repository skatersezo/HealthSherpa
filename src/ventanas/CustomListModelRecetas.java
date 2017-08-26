/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package ventanas;

import logica.Recetas;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class CustomListModelRecetas extends AbstractListModel {
    
   private ArrayList <Recetas> listaRecetas;
        
        public CustomListModelRecetas(){
            this.listaRecetas=Recetas.getRecetas();
            
        }

        @Override
        public int getSize() {
            return listaRecetas.size();
        }

        @Override
        public Object getElementAt(int index) {
            Recetas receta = listaRecetas.get(index);
            
            return receta.toString();
        }
        
        public void addReceta(Recetas a){
        a.cargaValores();
        this.listaRecetas.add(a);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
        }
        
        public void eliminarReceta(int index){
        this.listaRecetas.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
        
        }
        
        public Recetas getReceta(int index){
        return this.listaRecetas.get(index);
        }
    }