/*
 * @(#)XMLUtil.java
 * Time-stamp: "2008-12-08 11:23:01 anton"
 */

package se.umu.cs.edu.jap.highscoreservice.util;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import se.umu.cs.edu.jap.highscoreservice.Entry;

/**
 * XMLUtil declares useful methods parsing XML.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public final class XMLUtil {
    
    // Namespace Service
    private static final String SERVICE =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";
    
    /**
     * Parse response Element (<xsd:element name="RetrieveResponse"
     * type="tns:RetrieveResponseType"/>), create and return an Entry made from
     * the respone.
     * TODO: doc and maybe rename method and parameter.
     * @param responseElement 
     * @return 
     */
    public static Entry[] parseScores(OMElement scores) {
        // parse and echo response
        ArrayList<Entry> entryList = new ArrayList<Entry>();
        
        @SuppressWarnings("unchecked") // Doesn't support generics
            Iterator<OMElement> elementIterator = scores.getChildElements();
        while (elementIterator.hasNext()) {
            OMElement ge = elementIterator.next();
            @SuppressWarnings("unchecked") // Doesn't support generics
                Iterator<OMElement> it = ge.getChildElements();
            // Validate to make sure elements are in correct order
            String name = it.next().getText();
            String date = it.next().getText();
            String score = it.next().getText();
            entryList.add(new Entry(name, date, score));
        }
        Entry[] result = new Entry[entryList.size()];
        entryList.toArray(result);
        return result;
    }


    /**
     * Creates an OMElement with supplied root-element name, containing score
     * entires of type EntryType.  TODO: doc
     *
     * @param rootName The name of the root-element to store entires in.
     * @param method the name of the method used to create the OMElement. This
     * is used as namespace prefix.
     * @param entries The entires to store in the OMElement
     * @return An OMElement with root-element named from rootName, containing
     * score elements of type EntryType.
     */
    public static OMElement createScoreElements(String rootName, String method,
                                                Entry[] entries) {
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(SERVICE, method);
        
        // Create an element to fill with information from entries
        OMElement resultElement
            = factory.createOMElement(rootName, namespace);
        
        // Loop through entries and add them to resultNode
        for (Entry entry : entries) {
            // create name
            OMElement nameElement = factory.createOMElement("name", namespace);
            String nameText = entry.getName();
            nameElement.addChild(factory.createOMText(nameText));
            
            // create date
            OMElement dateElement = factory.createOMElement("date", namespace);
            String dateText = entry.getDate();
            dateElement.addChild(factory.createOMText(dateText));
            
            // create score
            OMElement scoreElement = factory.createOMElement("score", namespace);
            String scoreText = entry.getScore();
            scoreElement.addChild(factory.createOMText(scoreText));
            
            //Attatch name-, date-, scoreElement to scoreRootElement
            OMElement scoreRootElement = factory.createOMElement("score", namespace);
            scoreRootElement.addChild(nameElement);
            scoreRootElement.addChild(dateElement);
            scoreRootElement.addChild(scoreElement);
                        
            // Attatch score to request
            resultElement.addChild(scoreRootElement);
        }
        return resultElement;
    }
}
