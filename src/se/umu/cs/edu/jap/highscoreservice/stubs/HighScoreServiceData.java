/*
 * @(#)HighScoreServiceData.java
 * Time-stamp: "2008-12-08 11:53:48 anton"
 */

package se.umu.cs.edu.jap.highscoreservice.stubs;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import se.umu.cs.edu.jap.highscoreservice.Entry;

/**
 * HighScoreServiceData is used to store and manage entires from
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
     * TODO: Verify doc
     * Private contructor makes sure there can only be one instance of this
     * class, and that instance is created by the class itself from the
     * invokation of getInstance()
     */
    private HighScoreServiceData() {
        this.entries = new ArrayList<Entry>();
        logger.info("New instance fo HighScoreServiceData created.");
    }

    public static HighScoreServiceData getInstance() {
        return INSTANCE;
    }

    public Entry[] getEntries() {
        Entry[] result = new Entry[entries.size()];
        entries.toArray(result);
        return result;
    }

    public void storeEntry(Entry entry) {
        logger.info("New entry stored: " + entry + ".");
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
