/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package healthsherpa;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Iván Taghavi Espinosa
 */
public class CustomListModelAlimentos extends AbstractListModel{
    
        private ArrayList <Alimentos> listaAlimentos;
        
        public CustomListModelAlimentos(){
            this.listaAlimentos=Alimentos.getAlimentos();
        }
        
        public CustomListModelAlimentos (int categoria){
            this.listaAlimentos=Alimentos.getAlimentosCategoria(categoria);
        }

        @Override
        public int getSize() {
            return listaAlimentos.size();
        }

        @Override
        public Object getElementAt(int index) {
            Alimentos alimento = listaAlimentos.get(index);
            return alimento.toString();
        }
        
        public void addAlimento(Alimentos a){
        this.listaAlimentos.add(a);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
        }
        
        public void eliminarAlimento(int index){
        this.listaAlimentos.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
        
        }
        
        public Alimentos getAlimento(int index){
        return this.listaAlimentos.get(index);
        }
    }
    

