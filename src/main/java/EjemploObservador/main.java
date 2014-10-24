package EjemploObservador;

 public class main{  

public static void main(String[] args) {

       
      // Crea el observado
      ListaDeTweets ListaDeTweets = new ListaDeTweets();
      
      // Crea los observadores
      AplicacionCelular AppCelular1 = new AplicacionCelular("Leo");
      AplicacionCelular AppCelular2 = new AplicacionCelular("Jose");
      AplicacionCelular AppCelular3 = new AplicacionCelular("Diego");
      
      // Agrega los observadores a la ListaDeTweets
      
      ListaDeTweets.addObserver(AppCelular1);
      ListaDeTweets.addObserver(AppCelular2);
      ListaDeTweets.addObserver(AppCelular3);
      
      // Inicia el buscador de Tweets
      
      BuscadorDeTweets BuscaTweets = new BuscadorDeTweets ("expodepoo",ListaDeTweets );
      BuscaTweets.start();
   }
 }