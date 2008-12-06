/*
 * @(#)HighScoreServiceData.java
 * Time-stamp: "2008-12-06 18:08:02 anton"
 */

package se.umu.cs.edu.jap.highscoreservice.stubs;

import se.umu.cs.edu.jap.highscoreservice.Entry;
import java.util.List;
import java.util.ArrayList;

/**
 * HighScoreServiceData is used to store and manage entires from
 * HighScoreServiceSkeleton. This class conforms to the design-pattern
 * Singleton, that is there can only be one instance of this class.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class HighScoreServiceData { 
    private static HighScoreServiceData instance = new HighScoreServiceData();
    private List<Entry> entries;
    
    /**
     * TODO: Verify doc
     * Private contructor makes sure there can only be one instance of this
     * class, and that instance is created by the class itself from the
     * invokation of getInstance()
     */
    private HighScoreServiceData() {
        this.entries = new ArrayList<Entry>();
    }

    public static HighScoreServiceData getInstance() {
        return instance;
    }

    public Entry[] getEntries() {
        Entry[] result = new Entry[entries.size()];
        entries.toArray(result);
        return result;
    }

    public void storeEntry(Entry entry) {
        entries.add(entry);
    }
    
    @Override
    public String toString() {
        String result = "";
        for (Entry entry : entries) {
            result += entry.toString() + "\n";
        }
        return result;
    }
}
