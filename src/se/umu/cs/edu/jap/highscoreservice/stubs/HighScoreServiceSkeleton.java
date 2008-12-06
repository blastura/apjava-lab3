/**
 * HighScoreServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

package se.umu.cs.edu.jap.highscoreservice.stubs;

import org.apache.axiom.om.OMElement;
import se.umu.cs.edu.jap.highscoreservice.util.XMLUtil;
import se.umu.cs.edu.jap.highscoreservice.Entry;
import java.util.List;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMAbstractFactory;


/**
 * HighScoreServiceSkeleton java skeleton for the axisService
 */
public class HighScoreServiceSkeleton {
    // Namespace Service
    private static final String SERVICE =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";

    /**
     * Auto generated method signature
     * 
     * 
     * @param storeRequest An element containing StoreRequest-element ->
     * score-element -> of EntryType
     * @throws FailureFaultException
     * @return
     */
    public OMElement store(OMElement storeRequest)
            throws FailureFaultException {
        HighScoreServiceData data = HighScoreServiceData.getInstance();
        final String method = "store";
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(SERVICE, method);
        
        // Parse storeRequest
        Entry[] entryArray = XMLUtil.parseScores(storeRequest);
        // Add fetched entries to entries
        for (Entry entry : entryArray) {
            // TODO: Change this to a Logger
            System.out.println("Added entry: " + entry);
            data.storeEntry(entry);
        }
        
        // TODO: Remove or use Logger
        System.out.println("All entires in HighScoreServiceData:");
        System.out.println(data);
        
        // create a respons
        OMElement respons
            = factory.createOMElement("StoreResponse", namespace);
        return respons;
    }
    
    /**
     * Auto generated method signature
     * 
     * @param retrieveRequest
     * @throws FailureFaultException
     * @return
     */
    public OMElement retrieve(OMElement retrieveRequest)
        throws FailureFaultException {
        
        // TODO : fill this with the necessary business logic
        
        throw new java.lang.UnsupportedOperationException("Please implement "
                + this.getClass().getName() + "#retrieve");
    }
}
