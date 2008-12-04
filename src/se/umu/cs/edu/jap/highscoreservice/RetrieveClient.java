package se.umu.cs.edu.jap.highscoreservice;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.axiom.om.*;
import org.apache.axis2.*;
import org.apache.axis2.addressing.*;
import org.apache.axis2.client.*;

public class RetrieveClient {
    private static final String DEFAULT_URL = "http://localhost:8080/axis2/services/HighScoreService";

    // ---------------------------------------------------------
    // ---------------------------------------------------------
    private static String getProperty(String name) {
        String value = System.getProperty(name);
        return (value != null) ? value : "";
    }

    // ---------------------------------------------------------
    public static void main(String args[]) {
        try {
            final String propertyURL = getProperty("service.url");
            final String url = (propertyURL.length() > 0) ? propertyURL
                    : DEFAULT_URL;

            System.out.println("retrieving entries");

            // TODO: implement service invocation
            Entry[] entrys = new Entry[0];

            System.out.println("retrieved " + entrys.length + " entries");
            for (Entry entry : entrys) {
                System.out.println("name  " + entry.getName());
                System.out.println("date  " + entry.getDate());
                System.out.println("score " + entry.getScore());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
