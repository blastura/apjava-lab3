/*
 * @(#)HighScoreServiceData.java
 * Time-stamp: "2008-12-09 23:52:14 anton"
 */

package se.umu.cs.edu.jap.highscoreservice.stubs;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import se.umu.cs.edu.jap.highscoreservice.Entry;

/**
 * HighScoreServiceData is used to store and manage entries from
 * HighScoreServiceSkeleton. This class conforms to the design-pattern
 * Singleton, that is there can only be one instance of this class.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class HighScoreServiceData { 
    private static Logger logger = Logger.getLogger("highscoreservice");
    
    private static final HighScoreServiceData INSTANCE = new HighScoreServiceData();
    private List<Entry> entries;
    
    /**
     * Private constructor makes sure there can only be one instance of this
     * class, and that instance is created by the class itself from the first
     * invocation of getInstance()
     */
    private HighScoreServiceData() {
        this.entries = new ArrayList<Entry>();
        logger.info("New instance of HighScoreServiceData created.");
    }

    /**
     * Gets the only instance of this class, if none exists one is created.
     *
     * @return The only instance of this class.
     */
    public static HighScoreServiceData getInstance() {
        return INSTANCE;
    }

    /**
     * Gets all entries stored in this class.
     *
     * @return All the entries stored in this class.
     */
    public synchronized Entry[] getEntries() {
        Entry[] result = new Entry[entries.size()];
        entries.toArray(result);
        return result;
    }

    /**
     * Store an entry.
     *
     * @param entry The entry to store.
     */
    public synchronized void storeEntry(Entry entry) {
        logger.info("New entry stored: " + entry + ".");
        entries.add(entry);
    }
    
    /**
     * Returns a string representation of all entries stored in this class.
     *
     * @return A string representation of all entries stored in this class.
     */
    @Override
    public synchronized String toString() {
        String result = "";
        for (Entry entry : entries) {
            result += entry.toString() + "\n";
        }
        return result;
    }
}
