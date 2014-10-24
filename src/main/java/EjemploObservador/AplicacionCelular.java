package EjemploObservador;


import java.util.Observable;
import java.util.Observer;


public class AplicacionCelular implements Observer {
   String Dueño; 
    AplicacionCelular(String Dueño) {
        this.Dueño = Dueño; 
    }
   
   @Override
   public void update(Observable obj, Object arg) {
      /// Cuando hay un cambio en el observado
       // Se hacen las acciones requeridas
       
       // En este caso simula avisar al usuario sobre es uso del hashtag
      System.out.println(Dueño + "!!: Se uso el hashtag! " ); 
   }
   
}