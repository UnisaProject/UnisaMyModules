/**
 * ExtractGradebookResultsBatchWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.ExtractGradebookResultsBatchWebService_jws;

public interface ExtractGradebookResultsBatchWebService_PortType extends java.rmi.Remote {
    public void sendEmail(java.lang.String subject, java.lang.String body, java.lang.String emailAddress) throws java.rmi.RemoteException;
    public java.lang.String getGradebookMarks(java.lang.String module, java.lang.String moduleSite, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String assignmentNr, java.lang.String onlineType, java.lang.String primaryLecturer, java.lang.String primaryLecturerEmail) throws java.rmi.RemoteException;
}
