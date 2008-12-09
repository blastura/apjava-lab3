/*
 * @(#)RetrieveClient.java
 * Time-stamp: "2008-12-10 00:20:14 dit06ajn"
 */

package se.umu.cs.edu.jap.highscoreservice;

import java.net.MalformedURLException;
import java.net.URL;
import se.umu.cs.edu.jap.highscoreservice.stubs.FailureFaultException;

/**
 * RetrieveClient simplifies requests to retrieve entries stored in service from
 * a terminal.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class RetrieveClient {
    private static final String DEFAULT_URL =
        "http://localhost:37080/axis2/services/HighScoreService";

    /**
     * Copied from HelloWorldService. Gets a System property.
     *
     * @param name The property to get.
     * @return The value stored in the property or an empty string if null is
     * stored.
     */
    private static String getProperty(String name) {
        String value = System.getProperty(name);
        return (value != null) ? value : "";
    }

    /**
     * Runs the program, sets up a request and tries to retrieve all entries
     * from the DEFAULT_URL service of from the address in property
     * "service.url" if it is set.
     *
     * @param args Is not used.
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
            System.err.println("Error trying to communicate with Service: "
                               + urlString);
            System.exit(-1);
        }
    }
}
