/*
 * @(#)StoreClient.java
 * Time-stamp: "2008-12-07 23:27:14 anton"
 */

package se.umu.cs.edu.jap.highscoreservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.umu.cs.edu.jap.highscoreservice.stubs.FailureFaultException;

public class StoreClient {

    private static final String DEFAULT_URL =
        //"http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";
        "http://localhost:8081/axis2/services/HighScoreService";

    private static String getProperty(String name) {
        String value = System.getProperty(name);
        return (value != null) ? value : "";
    }

    public static void main(String args[]) {
        Logger.getLogger("highscoreservice").setLevel(Level.ALL);

        final String propertyURL = getProperty("service.url");
        final String urlString = (propertyURL.length() > 0) ? propertyURL
            : DEFAULT_URL;

        if (args.length < 3 || (args.length % 3) != 0) {
            System.out.println("Usage:");
            System.out.println("  java StoreClient [<name> <date> <score>]+");
            return;
        }
        
        Entry[] entries = new Entry[args.length / 3];
        int count = 0;
        for (int i = 0; i < args.length; i += 3) {
            final String name = args[i];
            final String date = args[i + 1];
            final String score = args[i + 2];
            entries[count] = new Entry(name, date, score);
            System.out.println("Entry: " + new Entry(name, date, score));
            count++;
        }
        
        try {
            URL url = new URL(urlString);
            System.out.println("Sending entries");
            System.out.println("  " + entries);

            HighScoreServiceClient client =
                new HighScoreServiceClient(url);
            String[] response = client.store(entries);
            System.out.println("Response text: " + response);
        } catch (MalformedURLException e) {
            System.err.println("Malformed url");
            System.exit(-1);
        } catch (FailureFaultException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
