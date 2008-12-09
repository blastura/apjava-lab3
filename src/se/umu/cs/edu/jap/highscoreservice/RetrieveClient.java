/*
 * @(#)RetrieveClient.java
 * Time-stamp: "2008-12-09 15:00:09 anton"
 */

package se.umu.cs.edu.jap.highscoreservice;

import java.net.MalformedURLException;
import java.net.URL;
import se.umu.cs.edu.jap.highscoreservice.stubs.FailureFaultException;

public class RetrieveClient {
    private static final String DEFAULT_URL =
        "http://localhost:8081/axis2/services/HighScoreService";

    /**
     * TODO: Have not changed this one.
     *
     * @param name a <code>String</code> value
     * @return a <code>String</code> value
     */
    private static String getProperty(String name) {
        String value = System.getProperty(name);
        return (value != null) ? value : "";
    }

    /**
     * TODO: doc
     *
     * @param args 
     */
    public static void main(String args[]) {
        final String propertyURL = getProperty("service.url");
        final String urlString = (propertyURL.length() > 0) ? propertyURL
            : DEFAULT_URL;
        
        try {
            URL url = new URL(urlString);

            HighScoreServiceClient client = new HighScoreServiceClient(url);
            
            System.out.println("Retrieving entries");
            Entry[] entries = client.retrieve();

            System.out.println("Retrieved " + entries.length + " entries.");
            for (Entry entry : entries) {
                System.out.println("Name:  " + entry.getName());
                System.out.println("Date:  " + entry.getDate());
                System.out.println("Score: " + entry.getScore());
                System.out.println();
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed url.");
            System.exit(-1);
        } catch (FailureFaultException e) {
            System.err.println("Error trying to communicat with Service:"
                               + urlString);
            System.exit(-1);
        }
    }
}
