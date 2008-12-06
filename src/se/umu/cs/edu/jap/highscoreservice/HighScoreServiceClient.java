package se.umu.cs.edu.jap.highscoreservice;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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
import java.util.logging.Logger;
import java.io.StringWriter;
import javax.xml.stream.XMLOutputFactory;

public class HighScoreServiceClient {
    // Logger
    private static Logger logger = Logger.getLogger("highscoreservice");
    
    // Namespace Service
    private static final String SERVICE =
        "http://nemi.cs.umu.se:8080/axis2/services/HighScoreService";
    
    // URL for connection to service
    private final URL url;
    
    public HighScoreServiceClient(URL url) {
        this.url = url;
    }
    
    /**
     * Send a single "store" request.
     *
     * @param entry an <code>Entry</code> value
     * @return a <code>String</code> value
     * @exception FailureFaultException if an error occurs
     */
    public String store(Entry entry) throws FailureFaultException {
        String[] results = store(new Entry[] {entry});
        return results[0];
    }
    
    /**
     * Send Entry to Service
     *
     * <wsdl:operation name="store">
     *   <wsdl:input message="tns:StoreRequestMessage"/>
     *   <wsdl:output message="tns:StoreResponseMessage"/>
     *  <wsdl:fault message="tns:FailureFaultException" name="FailureFault"/>
     * </wsdl:operation>
     *
     * <StoreRequest>
     *   <score>
     *     <name></name>
     *     <date></date>
     *     <score></score>
     *   </score>
     * </StoreRequest>     
     *
     *<StoreResponse>
     *  <!-- StoreResponseType, no restirctions -->
     *</StoreResponse>
     */       
    public String[] store(Entry[] entries) throws FailureFaultException {
        final String method = "store";
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(SERVICE, method);
        
        // create a request
        OMElement request
            = factory.createOMElement("StoreRequest", namespace);
        
        // Loop through entries and add them to request
        for (Entry entry : entries) {
            // create name
            OMElement nameElement = factory.createOMElement("name", namespace);
            String nameText = entry.getName();
            logger.info("nameText: " + nameText);
            nameElement.addChild(factory.createOMText(nameText));
            
            // create date
            OMElement dateElement = factory.createOMElement("date", namespace);
            String dateText = entry.getDate();
            logger.info("dateText: " + dateText);
            dateElement.addChild(factory.createOMText(dateText));
            
            // create score
            OMElement scoreElement = factory.createOMElement("score", namespace);
            String scoreText = entry.getScore();
            logger.info("scoreText: " + scoreText);
            scoreElement.addChild(factory.createOMText(scoreText));
            
            //Attatch name-, date-, scoreElement to scoreRootElement
            OMElement scoreRootElement = factory.createOMElement("score", namespace);
            scoreRootElement.addChild(nameElement);
            scoreRootElement.addChild(dateElement);
            scoreRootElement.addChild(scoreElement);
                        
            // Attatch score to request
            request.addChild(scoreRootElement);
        }

        // Print debug if system property "debug.messages" is set.
        printDebug("REQUEST", request);
            
        // Configure connection
        Options options = new Options();
        options.setTo(new EndpointReference(url.toString()));
        // TODO: Make sure the right Constants are imported
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setAction(method);

        // Create a client
        try {
            ServiceClient client = new ServiceClient();
            client.setOptions(options);
            OMElement response = client.sendReceive(request);

            // test / debug
            printDebug("RESPONSE", response);
            
            // Return result
            // TODO: How can several responses be stored?
            // <xsd:element name="StoreResponse" type="tns:StoreResponseType"/>
            // StoreResponseType = <xsd:simpleType name="StoreResponseType"/>
            ArrayList<String> resultList = new ArrayList<String>();
            @SuppressWarnings("unchecked") // Doesn't support generics
                Iterator<OMElement> elementIterator = response.getChildElements();
            // TODO
            while (elementIterator.hasNext()) {
                logger.info("response element has child elements!");
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
            //TODO - fix error message
            e.printStackTrace();
        }
        // TODO: If this happend something went wrong?
        return new String[] {"Something went wrong?"};
    }
    
    
    /**
     * Retriev all entries from service.
     *
     * @return All entries from the service.
     */
    public Entry[] retrieve() {
        final String method = "retrieve";
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(SERVICE, method);
        
        // create a request
        OMElement request
            = factory.createOMElement("RetrieveRequest", namespace);
        
        // Configure connection
        Options options = new Options();
        options.setTo(new EndpointReference(url.toString()));
        // TODO: Make sure the right Constants are imported
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setAction(method);

        // Create a client
        try {
            ServiceClient client = new ServiceClient();
            client.setOptions(options);
            
            // Try send request and receive response
            OMElement response = client.sendReceive(request);

            // test / debug
            printDebug("RESPONSE", response);
            
            // Return result Response is one or more elements "RetrieveResponse"
            // with an element "score" of type "EntryType""
            return XMLUtil.parseScores(response);
        } catch (AxisFault e) {
            //TODO - fix error message
            e.printStackTrace();
        }
        // Something went wrong.
        return new Entry[] {};
    }
    
    /**
     * Copied from HelloWorldServiceClient.java
     * TODO: Write doc
     *
     * @param message 
     * @param element 
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

    /**
     * TODO: Copied code, prints exactly the same as element.toString()
     *
     * @param element 
     * @return 
     */
    private static String toString (OMElement element) {
        try {
            StringWriter writer = new StringWriter();
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            element.serialize(factory.createXMLStreamWriter(writer));
            writer.flush();
            return writer.toString();
        } catch (Exception e) {
            return "[unable to transform element to string]";
        }
    }
}
