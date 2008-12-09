/*
 * @(#)HighScoreServiceClient.java
 * Time-stamp: "2008-12-09 23:37:43 anton"
 */

package se.umu.cs.edu.jap.highscoreservice;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import se.umu.cs.edu.jap.highscoreservice.stubs.FailureFaultException;
import se.umu.cs.edu.jap.highscoreservice.util.XMLUtil;

/**
 * HighScoreServiceClient has methods to communicate with the HighScoreService.
 *
 * @author Anton Johansson, dit06ajn
 * @version 1.0
 */
public class HighScoreServiceClient {
    // Namespace Service
    private static final String SERVICE =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";

    // URL for connection to service
    private final URL url;

    /**
     * Creates a new HighScoreServiceClient instance. Supplied URL gives the
     * address to the HighScoreService to use.
     *
     * @param url The URL to the HighScoreService this class should communicate
     * with.
     */
    public HighScoreServiceClient(URL url) {
        this.url = url;
    }

    /**
     * Send a single "store" request.
     *
     * @param entry The entry to send.
     * @return The response.
     * @exception FailureFaultException If an error communication with the
     * service occurs.
     */
    public String store(Entry entry) throws FailureFaultException {
        String[] results = store(new Entry[] {entry});
        return (results.length == 1) ? results[0] : "";
    }

    /**
     * Sends multiple entries of type Entry to the service.
     *
     * @param entries The entries to send.
     * @return The responses.
     * @exception FailureFaultException If an error communication with the
     * service occurs.
     */
    public String[] store(Entry[] entries) throws FailureFaultException {
        final String method = "store";

        // Create a request
        OMElement request = XMLUtil.createScoreElements("StoreRequest", method, entries);

        // Print debug if system property "debug.messages" is set.
        printDebug("REQUEST", request);

        // Configure connection
        Options options = new Options();
        options.setTo(new EndpointReference(url.toString()));
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setAction(method);

        // Create a client
        try {
            ServiceClient client = new ServiceClient();
            client.setOptions(options);
            // Could throw AxisFault
            OMElement response = client.sendReceive(request);

            // Debug
            printDebug("RESPONSE", response);

            // Return possible results from elements of type:
            // <xsd:element name="StoreResponse" type="tns:StoreResponseType"/>
            // StoreResponseType = <xsd:simpleType name="StoreResponseType"/>
            ArrayList<String> resultList = new ArrayList<String>();
            @SuppressWarnings("unchecked") // Doesn't support generics
                Iterator<OMElement> elementIterator = response.getChildElements();
            while (elementIterator.hasNext()) {
                OMElement ge = elementIterator.next();
                @SuppressWarnings("unchecked") // Doesn't support generics
                    Iterator<OMElement> it = ge.getChildElements();
                String responseText = it.next().getText();
                resultList.add(responseText);
            }
            String[] result = new String[resultList.size()];
            resultList.toArray(result);
            return result;
        } catch (AxisFault e) {
            throw new FailureFaultException("Exception from service: ", e);
        }
    }


    /**
     * Retrieve all entries from service.
     *
     * @return All entries of type Entry from the service.
     * @exception FailureFaultException If an error communication with the
     * service occurs.
     */
    public Entry[] retrieve() throws FailureFaultException {
        final String method = "retrieve";
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(SERVICE, method);

        // Create a request
        OMElement request
            = factory.createOMElement("RetrieveRequest", namespace);

        // Configure connection
        Options options = new Options();
        options.setTo(new EndpointReference(url.toString()));
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setAction(method);

        // Create a client
        try {
            ServiceClient client = new ServiceClient();
            client.setOptions(options);

            // Try send request and receive response, could throw AxisFault
            OMElement response = client.sendReceive(request);

            // Debug
            printDebug("RESPONSE", response);

            // Parse and return result
            Entry[] result = XMLUtil.parseScores(response);
            return result;
        } catch (AxisFault e) {
            throw new FailureFaultException("Exception from service: ", e);
        }
    }

    /**
     * Copied from HelloWorldServiceClient.java
     *
     * @param message A string to be printed before printing information from
     * element.
     * @param element The element containing information to be printed.
     */
    private static void printDebug (String message, OMElement element) {
        String propertyDebugMessages = System.getProperty("debug.messages");
        if (propertyDebugMessages != null) {
            if (Boolean.parseBoolean(propertyDebugMessages) == true) {
                System.out.println(message);
                System.out.println(element.toString());
            }
        }
    }
}
