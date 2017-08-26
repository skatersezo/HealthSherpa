/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package ventanas;

import logica.Ingrediente;
import logica.Recetas;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class CustomListModelIngredientes extends AbstractListModel {
    
   private ArrayList <Ingrediente> listaIngredientes;

        public CustomListModelIngredientes() {
            this.listaIngredientes=new ArrayList();
        }
   
        public CustomListModelIngredientes(Recetas receta){
            
            this.listaIngredientes=receta.getIngredientes();
        }

        @Override
        public int getSize() {
            return listaIngredientes.size();
        }

        @Override
        public Object getElementAt(int index) {
            Ingrediente ingrediente = listaIngredientes.get(index);
            return ingrediente.toString();
        }
        
        public void addIngrediente(Ingrediente a){
        this.listaIngredientes.add(a);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
        }
        
        public void eliminarIngrediente(int index){
        this.listaIngredientes.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
        
        }
        
        public Ingrediente getIngrediente(int index){
        return this.listaIngredientes.get(index);
        }
    }
