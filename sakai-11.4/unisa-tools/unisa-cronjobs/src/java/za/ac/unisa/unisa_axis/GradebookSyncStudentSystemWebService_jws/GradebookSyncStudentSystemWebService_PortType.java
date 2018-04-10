/**
 * GradebookSyncStudentSystemWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws;

public interface GradebookSyncStudentSystemWebService_PortType extends java.rmi.Remote {
    public void sendEmail(java.lang.String subject, java.lang.String body, java.lang.String emailAddress) throws java.rmi.RemoteException;
    public void updateAssignment(java.lang.String module, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String assignmentNr, java.lang.String studentNr, java.lang.String gradebookPercentage, java.lang.String onlineType) throws java.rmi.RemoteException;
    public java.lang.String getGradebookMarks(java.lang.String module, java.lang.String moduleSite, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String assignmentNr, java.lang.String onlineType, java.lang.String primaryLecturer, java.lang.String primaryLecturerEmail) throws java.rmi.RemoteException;
    public boolean updateStudentSystem(java.lang.String module, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String assignmentNr, java.lang.String studentNr, java.lang.String gradebookPercentage, java.lang.String lecturer, java.lang.String onlineType) throws java.rmi.RemoteException;
    public boolean studentModuleRecordExist(java.lang.String module, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String studentNr) throws java.rmi.RemoteException;
    public void insertLogStuast(java.lang.String module, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String assignmentNr, java.lang.String studentNr, java.lang.String gradebookPercentage, java.lang.String comment, java.lang.String lecturer) throws java.rmi.RemoteException;
    public void insertAssignment(java.lang.String module, java.lang.String acadYear, java.lang.String semPeriod, java.lang.String assignmentNr, java.lang.String studentNr, java.lang.String gradebookPercentage, java.lang.String assignmentSequence) throws java.rmi.RemoteException;
    public void insertSXPLOG(java.lang.String acadYear, java.lang.String semPeriod, java.lang.String module, java.lang.String assignmentNr, java.lang.String studentNr, java.lang.String log1Comment, java.lang.String lecturer) throws java.rmi.RemoteException;
    public void createAssignmentLogFiles(java.lang.String acadYear, java.lang.String semPeriod, java.lang.String studentNr, java.lang.String module, java.lang.String assignmentNr, java.lang.String gradebookPercentage, java.lang.String lecturer, java.lang.String processGC139, java.lang.String fromProgram, java.lang.String statusGC140, java.lang.String subStatusGC140, java.lang.String logMessage) throws java.rmi.RemoteException;
    public java.lang.String selectDateReceived() throws java.rmi.RemoteException;
    public void deleteSTUASA(java.lang.String acadYear, java.lang.String semPeriod, java.lang.String studentNr, java.lang.String module, java.lang.String assignmentNr) throws java.rmi.RemoteException;
    public void insertSTUASA(java.lang.String acadYear, java.lang.String semPeriod, java.lang.String studentNr, java.lang.String module, java.lang.String assignmentNr, java.lang.String dateReceived, java.lang.String processGC139, java.lang.String fromProgram, java.lang.String statusGC140, java.lang.String subStatusGC140, java.lang.String percentage, java.lang.String lecturer, java.lang.String logMessage) throws java.rmi.RemoteException;
    public void insertSTUAST(java.lang.String acadYear, java.lang.String semPeriod, java.lang.String studentNr, java.lang.String module, java.lang.String assignmentNr, java.lang.String dateReceived, java.lang.String processGC139, java.lang.String fromProgram, java.lang.String statusGC140, java.lang.String subStatusGC140, java.lang.String percentage, java.lang.String lecturer, java.lang.String logMessage) throws java.rmi.RemoteException;
}
