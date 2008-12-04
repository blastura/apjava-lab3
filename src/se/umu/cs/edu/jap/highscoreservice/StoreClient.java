package se.umu.cs.edu.jap.highscoreservice;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

// import java.io.*;
// import java.net.*;
// import java.util.*;
// import org.apache.axiom.om.*;
// import org.apache.axis2.*;
// import org.apache.axis2.addressing.*;
// import org.apache.axis2.client.*;

public class StoreClient {
    private static Logger logger = Logger.getLogger("highscoreservice");
    
    private static final String DEFAULT_URL =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";
    //"http://localhost:8080/axis2/services/HighScoreService";

    private static String getProperty(String name) {
        String value = System.getProperty(name);
        return (value != null) ? value : "";
    }

    public static void main(String args[]) {
        Logger.getLogger("highscoreservice").setLevel(Level.ALL);
        
        try {
            final String propertyURL = getProperty("service.url");
            final String url = (propertyURL.length() > 0) ? propertyURL
                    : DEFAULT_URL;

            if (args.length < 3) {
                System.out.println("Usage:");
                System.out.println("  java StoreClient <name> <date> <score>");
                return;
            }
            final String name = args[0];
            final String date = args[1];
            final String score = args[2];
            final Entry entry = new Entry(name, date, score);

            System.out.println("sending entry");
            System.out.println("  " + entry);

            // TODO: implement service invocation
            HighScoreServiceClient client =
                new HighScoreServiceClient(new URL(url));
            String reply = client.store(entry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
