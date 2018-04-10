/**
 * DataCleanup_MeleteProcWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws;

public interface DataCleanup_MeleteProcWebService_PortType extends java.rmi.Remote {
    public void sendEmail(java.lang.String subject, java.lang.String body, java.lang.String emailAddress) throws java.rmi.RemoteException;
    public java.lang.String meleteDataCleanup(java.lang.String academicYear, java.lang.String semesterPeriod) throws java.rmi.RemoteException;
}
