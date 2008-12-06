/*
 * @(#)XMLUtil.java
 * Time-stamp: "2008-12-06 18:06:36 anton"
 */

package se.umu.cs.edu.jap.highscoreservice.util;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.axiom.om.OMElement;
import se.umu.cs.edu.jap.highscoreservice.Entry;

/**
 * XMLUtil declares useful methods parsing XML.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public final class XMLUtil {
    
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
            String name = it.next().getText();
            String date = it.next().getText();
            String score = it.next().getText();
            entryList.add(new Entry(name, date, score));
        }
        Entry[] result = new Entry[entryList.size()];
        entryList.toArray(result);
        return result;
    }
}
