/**
 * FailureFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

package se.umu.cs.edu.jap.highscoreservice.stubs;

public class FailureFaultException extends java.lang.Exception {

    private org.apache.axiom.om.OMElement faultMessage;

    public FailureFaultException() {
        super("FailureFaultException");
    }

    public FailureFaultException(java.lang.String s) {
        super(s);
    }

    public FailureFaultException(java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public void setFaultMessage(org.apache.axiom.om.OMElement msg) {
        faultMessage = msg;
    }

    public org.apache.axiom.om.OMElement getFaultMessage() {
        return faultMessage;
    }
}
