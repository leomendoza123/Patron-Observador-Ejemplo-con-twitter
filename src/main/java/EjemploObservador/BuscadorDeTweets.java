/**
 * Copyright 2013 Twitter, Inc. Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 */
package EjemploObservador;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import java.util.Observable;
import twitter4j.JSONException;
import twitter4j.JSONObject;

public class BuscadorDeTweets extends Thread {

    Authentication auth;
    BlockingQueue<String> queue;
    StatusesFilterEndpoint endpoint;
    Client client;
    String hashtag;
    ListaDeTweets ListaDeTweets;

    int CantidadDeTwets;

    public BuscadorDeTweets(String hashtag, ListaDeTweets watched) {
        this.hashtag = hashtag;
        IniciaVariablesDeConeccion();
        this.ListaDeTweets = watched;
    }

    @Override
    public void run() {
        IniciaVariablesDeConeccion();
        client.connect();
        while (true) {
            try {
                /// Toma el JSON devuelto por el API de tweeter 
                /// Y extrae los datos en el TweetText

                String msg = queue.take();
                JSONObject Tweet = new JSONObject(msg);
                String Text = Tweet.getString("text");
                String User = Tweet.getJSONObject("user").getString("name");
                String location = Tweet.getJSONObject("user").getString("location");
                String TweetText = ">>>>>>>> "+ location +"\n"+User+": "+Text+"\n"; 
                
              /// Envia el TweetText al la Lista
                ListaDeTweets.agregaTweet(TweetText);

            } catch (InterruptedException ex) {
                Logger.getLogger(BuscadorDeTweets.class.getName()).log(Level.SEVERE, null, ex);

            } catch (JSONException ex) {
                Logger.getLogger(BuscadorDeTweets.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    

    private void IniciaVariablesDeConeccion() {
        queue = new LinkedBlockingQueue<String>(10000);

        endpoint = new StatusesFilterEndpoint();
        // add some track terms
        endpoint.trackTerms(Lists.newArrayList("twitterapi", "#" + hashtag));

        auth = new OAuth1("HoYbHoofgmIon4OqUPfRK6992",
                "ZQi2WV88Mvw6v6igVYf97Kt43wU37OGODj1iEaVrEfM87TLLTc",
                "165246521-IeLwMmaYD3Kx9LLMzZiqDH1WhtzLjKejmJ3CwVW3",
                "c3Sj6JDef3o1ToOCTy9ms9ioENqXzhPv2MCvaCZR6pUAs");

        client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();
    }

}
