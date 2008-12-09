/*
 * @(#)HighScoreServiceSkeleton.java
 * Time-stamp: "2008-12-09 15:47:13 anton"
 */

package se.umu.cs.edu.jap.highscoreservice.stubs;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.log4j.Logger;
import se.umu.cs.edu.jap.highscoreservice.Entry;
import se.umu.cs.edu.jap.highscoreservice.util.XMLUtil;

/**
 * HighScoreServiceSkeleton is an axisService used to store and retireve
 * highscore entries. See file wsdl/highscoreservice.wsdl.
 */
public class HighScoreServiceSkeleton {
    private static Logger logger = Logger.getLogger("highscoreservice");
    
    // Namespace Service
    private static final String SERVICE =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";

    public HighScoreServiceSkeleton() {
        logger.info("New instance of HighScoreServiceSkeleton created.");
    }
    
    /**
     * Stores all entries from storeRequest element in HighScoreServiceData.
     * 
     * @param storeRequest An element containing StoreRequest-element ->
     * score-element -> of EntryType
     * @throws FailureFaultException
     * @return An OMElement containing respons, currently an emtpy element named
     * StoreResponse is returned.
     */
    public OMElement store(OMElement storeRequest)
            throws FailureFaultException {
        HighScoreServiceData data = HighScoreServiceData.getInstance();
        final String method = "store";
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(SERVICE, method);
        
        // Parse storeRequest
        Entry[] entries = XMLUtil.parseScores(storeRequest);
        
        // Add fetched entries to entries
        for (Entry entry : entries) {
            // TODO: Change this to a Logger
            data.storeEntry(entry);
            logger.info("Added entry: " + entry);
        }
        
        // TODO: Remove
        logger.info("All entires in HighScoreServiceData:\n" + data);
        
        // create a respons
        OMElement respons
            = factory.createOMElement("StoreResponse", namespace);
        return respons;
    }
    
    /**
     * Retrieves and returns all entries stored in HighScoreServiceData.
     * 
     * @param retrieveRequest The request Element, currently not used. All
     * entries are returned.
     * @throws FailureFaultException If an error occures. TODO: Check what this
     * really could be.
     * @return An OMElement containing all entries stored in
     * HighScoreServiceData. The root-element will be named RetrieveResponse and
     * entries are stored in score-elements.
     */
    public OMElement retrieve(OMElement retrieveRequest)
        throws FailureFaultException {
        HighScoreServiceData data = HighScoreServiceData.getInstance();
        Entry[] entries = data.getEntries();
        OMElement resultElement = XMLUtil.createScoreElements("RetrieveResponse",
                                                              "retrieve",
                                                              entries);
        return resultElement;
    }
}
