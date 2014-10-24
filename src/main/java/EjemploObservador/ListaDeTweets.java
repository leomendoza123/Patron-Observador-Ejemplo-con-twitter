package EjemploObservador;


import java.util.ArrayList;
import java.util.Observable;
import twitter4j.JSONObject;

class ListaDeTweets extends Observable {

   ArrayList<String> Tweets;  

    public ListaDeTweets() {
        Tweets = new ArrayList<String>();
    }
   
   public void agregaTweet(String Tweet) {
      // Agrega el nuevo Tweet a la lista
      Tweets.add(Tweet); 
      System.out.println(Tweet);
      // Se hizo un cambio en la lista
      // Este se notifica a los observadores
      setChanged();                  
      notifyObservers();
   }
   
}