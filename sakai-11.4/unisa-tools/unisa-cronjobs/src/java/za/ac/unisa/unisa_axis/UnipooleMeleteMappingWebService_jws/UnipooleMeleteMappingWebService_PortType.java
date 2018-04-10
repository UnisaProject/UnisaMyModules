/**
 * UnipooleMeleteMappingWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws;

public interface UnipooleMeleteMappingWebService_PortType extends java.rmi.Remote {
    public void sendEmail(java.lang.String subject, java.lang.String body, java.lang.String emailAddress) throws java.rmi.RemoteException;
    public void meleteDataToBeMapped(java.lang.String toolId, java.lang.String fromContext, java.lang.String toContext) throws java.rmi.RemoteException;
}
