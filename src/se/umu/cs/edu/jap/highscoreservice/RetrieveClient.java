package se.umu.cs.edu.jap.highscoreservice;

import java.net.URL;

// import java.io.*;
// import java.net.*;
// import java.util.*;
// import org.apache.axiom.om.*;
// import org.apache.axis2.*;
// import org.apache.axis2.addressing.*;
// import org.apache.axis2.client.*;

public class RetrieveClient {
    private static final String DEFAULT_URL =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";
    //"http://localhost:8080/axis2/services/HighScoreService";

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
        try {
            final String propertyURL = getProperty("service.url");
            final String url = (propertyURL.length() > 0) ? propertyURL
                : DEFAULT_URL;

            System.out.println("retrieving entries");

            // TODO: implement service invocation
            HighScoreServiceClient client =
                new HighScoreServiceClient(new URL(url));
            
            Entry[] entries = client.retrieve();

            System.out.println("retrieved " + entries.length + " entries");
            for (Entry entry : entries) {
                System.out.println("Name:  " + entry.getName());
                System.out.println("Date:  " + entry.getDate());
                System.out.println("Score: " + entry.getScore());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
