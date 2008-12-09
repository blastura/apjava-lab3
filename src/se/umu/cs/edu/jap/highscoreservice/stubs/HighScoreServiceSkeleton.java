/*
 * @(#)HighScoreServiceSkeleton.java
 * Time-stamp: "2008-12-10 00:33:20 anton"
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
 * HighScoreServiceSkeleton is an axisService used to store and retrieve
 * high score entries. See file wsdl/highscoreservice.wsdl.
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class HighScoreServiceSkeleton {
    private static Logger logger = Logger.getLogger("highscoreservice");

    // Namespace Service
    private static final String SERVICE =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";

    /**
     * Creates a new HighScoreServiceSkeleton instance. Used for printing
     * information to log.
     *
     */
    public HighScoreServiceSkeleton() {
        logger.info("New instance of HighScoreServiceSkeleton created.");
    }

    /**
     * Stores all entries from storeRequest element in HighScoreServiceData.
     *
     * @param storeRequest An element containing StoreRequest-element ->
     * score-element -> of EntryType
     * @return An OMElement containing response, currently an empty element named
     * StoreResponse is returned.
     * @exception FailureFaultException TODO: Doesn't really throws this error
     * in this implementation?
     */
    public OMElement store(OMElement storeRequest)
        throws FailureFaultException {
        try {
            HighScoreServiceData data = HighScoreServiceData.getInstance();
            final String method = "store";
            OMFactory factory = OMAbstractFactory.getOMFactory();
            OMNamespace namespace = factory.createOMNamespace(SERVICE, method);

            // Parse storeRequest
            Entry[] entries = XMLUtil.parseScores(storeRequest);

            // Add fetched entries to entries
            for (Entry entry : entries) {
                data.storeEntry(entry);
                logger.info("Added entry: " + entry);
            }

            // Logs information about all entries in this service.
            logger.info("All entires in HighScoreServiceData:\n" + data);

            // Create a respons
            OMElement respons
                = factory.createOMElement("StoreResponse", namespace);
            return respons;
        } catch (Exception e) {
            throw new FailureFaultException("Operation failure: ", e);
        }
    }

    /**
     * Retrieves and returns all entries stored in HighScoreServiceData.
     *
     * @param retrieveRequest The request Element, currently not used. All
     * entries are returned.
     * @return An OMElement containing all entries stored in
     * HighScoreServiceData. The root-element will be named RetrieveResponse and
     * entries are stored in score-elements.
     * @exception FailureFaultException TODO: Doesn't really throws this error
     * in this implementation?
     */
    public OMElement retrieve(OMElement retrieveRequest)
        throws FailureFaultException {
        try {
            HighScoreServiceData data = HighScoreServiceData.getInstance();
            Entry[] entries = data.getEntries();
            OMElement resultElement = XMLUtil.createScoreElements("RetrieveResponse",
                                                                  "retrieve",
                                                                  entries);
            return resultElement;
        } catch (Exception e) {
            throw new FailureFaultException("Operation failure: ", e);
        }
    }
}
