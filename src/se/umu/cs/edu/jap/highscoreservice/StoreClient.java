/*
 * @(#)StoreClient.java
 * Time-stamp: "2008-12-10 00:18:35 dit06ajn"
 */

package se.umu.cs.edu.jap.highscoreservice;

import java.net.MalformedURLException;
import java.net.URL;
import se.umu.cs.edu.jap.highscoreservice.stubs.FailureFaultException;

/**
 * StoreClient simplifies requests to store entries in a HighScoreService.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class StoreClient {

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
     * Runs the program, sets up a request and tries to store all entries
     * supplied as parameters when calling this class from a terminal.
     *
     * @param args Is used to create new entries. Every 3 parameters represent
     * an Entry as name, date, score.
     */
    public static void main(String args[]) {
        final String propertyURL = getProperty("service.url");
        final String urlString = (propertyURL.length() > 0) ? propertyURL
            : DEFAULT_URL;

        if (args.length < 3 || (args.length % 3) != 0) {
            System.out.println("Usage:");
            System.out.println("  java StoreClient (<name> <date> <score>)+");
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
            String[] responses = client.store(entries);
            for (String response : responses) {
                System.out.println("Response text: " + response);
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed url");
            System.exit(-1);
        } catch (FailureFaultException e) {
            System.err.println("Error trying to communicate with Service: "
                               + e.getMessage());
            System.exit(-1);
        }
    }
}
