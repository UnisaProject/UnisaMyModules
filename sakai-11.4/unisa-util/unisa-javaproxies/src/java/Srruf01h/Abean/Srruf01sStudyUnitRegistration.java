package Srruf01h.Abean;
 
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.beans.*;
import java.util.*;
import java.net.URL;
import java.math.*;
import java.text.*;
import com.ca.gen80.jprt.*;
import com.ca.gen80.odc.coopflow.*;
import com.ca.gen80.odc.msgobj.*;
import com.ca.gen80.csu.trace.*;
import com.ca.gen80.vwrt.types.*;
 
/**
 * <strong>API bean documentation.</strong><p>
 *
 * This html file contains the public methods available in this bean.<br>
 * NOTE:  the links at the top of this page do not work, as they are not connected to anything. 
 * To get the images in the file to work, copy the images directory 
 * from 'jdk1.1.x/docs/api/images' to the directory where this file is located.
 */
public class Srruf01sStudyUnitRegistration  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srruf01sStudyUnitRegistration");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srruf01sStudyUnitRegistration() {
      super();
      nativeDateFormatter.setLenient(false);
      nativeTimeFormatter.setLenient(false);
      nativeTimestampFormatter.setLenient(false);
      Trace.initialize(null);
   }
   /**
    * Sets the traceflag to tell the bean to output diagnostic messages on stdout.
    *
    * @param traceFlag 1 to turn tracing on, 0 to turn tracing off.
    */
   public void setTracing(int traceFlag) {
      if (traceFlag == 0)
         Trace.setMask(Trace.MASK_NONE);
      else
         Trace.setMask(Trace.MASK_ALL);
   }
   /**
    * Gets the current state of tracing.
    *
    * @return traceFlag value
    */
   public int  getTracing() {
      if (Trace.getMask() == Trace.MASK_NONE)
         return 0;
      else
         return 1;
   }
   protected void traceOut(String x)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, x);
      }
   }
 
 
   private Srruf01sStudyUnitRegistrationOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srruf01sStudyUnitRegistrationOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrruf01sStudyUnitRegistrationOperation();
         notifyCompletionListeners();
      }
      catch (PropertyVetoException ePve)  {
         PropertyChangeEvent pce = ePve.getPropertyChangeEvent();
         String s = pce.getPropertyName();
         System.out.println("\nPropertyVetoException on " + s + ": " + ePve.toString());
         throw ePve;
      }
      catch (ProxyException e)  {
         notifyExceptionListeners(e.toString());
         return;
      }
   }
 
 
   private class operListener implements ActionListener, java.io.Serializable  {
      private Srruf01sStudyUnitRegistration rP;
      operListener(Srruf01sStudyUnitRegistration r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srruf01sStudyUnitRegistration", "Listener heard that Srruf01sStudyUnitRegistrationOperation completed with " + 
               aEvent.getActionCommand());
         }
         String excp = "Exception";
         if (excp.equalsIgnoreCase(aEvent.getActionCommand().substring(0,9)))
            System.out.println("\nException on " + aEvent.getActionCommand().substring(10));
      }
   }
 
   private Vector completionListeners = new Vector();
   /**
    * Adds an object to the list of listeners that are called when execute has completed.
    *
    * @param l a class object that implements the ActionListener interface.  See the test UI 
    * for an example.
    */
   public synchronized void addCompletionListener(ActionListener l)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "addCompletionListener registered");
      }
      completionListeners.addElement(l);     //add listeners
   }
   /**
    * Removes the object from the list of listeners that are called after completion of execute.
    *
    * @param l the class object that was registered as a CompletionListener.  See the test UI 
    * for an example.
    */
   public synchronized void removeCompletionListener(ActionListener l)  {
      completionListeners.removeElement(l);  //remove listeners
   }
   private void notifyCompletionListeners()  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srruf01sStudyUnitRegistration ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srruf01sStudyUnitRegistration");
      for (int i = 0; targets.size() > i; i++)  {
         ActionListener target = (ActionListener)targets.elementAt(i);
         target.actionPerformed(actionEvt);
      }
   }
 
   private Vector exceptionListeners = new Vector();
   /**
    * Adds an object to the list of listeners that are called when an exception occurs.
    *
    * @param l a class object that implements the ActionListener interface.  See the test UI 
    * for an example.
    */
   public synchronized void addExceptionListener(ActionListener l)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "addExceptionListener registered");
      }
      exceptionListeners.addElement(l);     //add listeners
   }
   /**
    * Removes the object from the list of listeners that are called when an exception occurs.
    *
    * @param l the class object that was registered as an ExceptionListener.  See the test UI 
    * for an example.
    */
   public synchronized void removeExceptionListener(ActionListener l)  {
      exceptionListeners.removeElement(l);  //remove listeners
   }
   private void notifyExceptionListeners(String s)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srruf01sStudyUnitRegistration ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srruf01sStudyUnitRegistration";
      synchronized (this)  {
         targets = (Vector) exceptionListeners.clone();
      }
      if (s.length() > 0)
          failCommand = failCommand.concat(s);
      actionEvt = new ActionEvent(this, 0, failCommand);
      for (int i = 0; targets.size() > i; i++)  {
         ActionListener target = (ActionListener)targets.elementAt(i);
         target.actionPerformed(actionEvt);
      }
   }
 
   /**
    * Called by the sender of Listener events.
    */
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
 
      if (command.equals("execute"))  {
         try {
            execute();
         } catch (PropertyVetoException pve) {}
      } else {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, BEANNAME, "ActionEvent " + e.toString());
         }
      }
   }
 
   //these are the standard properties that are passed into the Server Dialog 
   //all of these are checked when loaded by the operation into the srvdialog class
 
   private String commandSent = "";
   /**
    * Sets the command sent property to be sent to the server where
    * the procedure step's executable code is installed. This property should only be
    * set if the procedure step uses case of command construct.
    *
    * @param s a string representing the command name
    */
   public void setCommandSent(String s) {
      if (s == null) commandSent = "";
      else commandSent = s;
   }
   /**
    * Gets the command sent property to be sent to the server where
    * the procedure step's executable code is installed.
    *
    * @return a string representing the command name
    */
   public String getCommandSent() {
      return commandSent;
   }
 
   private String clientId = "";
   /**
    * Sets the client user id property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * user id is usually accompanied by a client user password, which can be set
    * with the clientPassword property.  Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @param s a string representing the client user id
    */
   public void setClientId(String s) {
      if (s == null) clientId = "";
      else clientId = s;
   }
   /**
    * Gets the client user id property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * user id is usually accompanied by a client user password, which can also be set
    * with the clientPassword property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @return a string representing the client user id
    */
   public String getClientId() {
      return clientId;
   }
 
   private String clientPassword = "";
   /**
    * Sets the client password property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * password usually accompanies a client user id, which can be set
    * with the clientId property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @param s a string representing the client password
    */
   public void setClientPassword(String s) {
      if (s == null) clientPassword = "";
      else  clientPassword = s;
   }
   /**
    * Gets the client password property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * password usually accompanies a client user id, which can be set
    * with the clientId property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @return a string representing the client password
    */
   public String getClientPassword() {
      return clientPassword;
   }
 
   private String nextLocation = "";
   /**
    * Sets the location name (NEXTLOC) property that may be
    * used by ODC user exit flow dlls.
    *
    * @param s a string representing the NEXTLOC value
    */
   public void setNextLocation(String s) {
      if (s == null) nextLocation = "";
      else nextLocation = s;
   }
   /**
    * Gets the location name (NEXTLOC) property that may be
    * used by ODC user exit flow dlls.
    *
    * @return a string representing the NEXTLOC value
    */
   public String getNextLocation() {
      return nextLocation;
   }
 
   private int exitStateSent = 0;
   /**
    * Sets the exit state property which will be sent to server procedure step.
    *
    * @param n an integer representing the exit state value
    */
   public void setExitStateSent(int n) {
      exitStateSent = n;
   }
   /**
    * Gets the exit state property which will be sent to server procedure step.
    *
    * @return an integer representing the exit state value
    */
   public int getExitStateSent() {
      return exitStateSent;
   }
 
   private String dialect = "DEFAULT";
   /**
    * Sets the dialect property.  It has the default value of "DEFAULT".
    *
    * @param s a string representing the dialect value
    */
   public void setDialect(String s) {
      if (s == null) dialect = "DEFAULT";
      else dialect = s;
   }
   /**
    * Gets the dialect property.  It has the default value of "DEFAULT".
    *
    * @return a string representing the dialect value
    */
   public String getDialect() {
      return dialect;
   }
 
   private String commandReturned;
   protected void setCommandReturned(String s) {
      commandReturned = s;
   }
   /**
    * Retrieves the command returned property, if any,
    * after the server procedure step has been executed.
    *
    * @return a string representing the command returned value
    */
   public String getCommandReturned() {
      return commandReturned;
   }
 
   private int exitStateReturned;
   protected void setExitStateReturned(int n) {
      exitStateReturned = n;
   }
   /**
    * Retrieves the exit state returned property, if any,
    * after the server procedure step has been executed.
    *
    * @return a string representing the exit state returned value
    */
   public int getExitStateReturned() {
      return exitStateReturned;
   }
 
   private int exitStateType;
   protected void setExitStateType(int n) {
      exitStateType = n;
   }
   /**
    * Gets the exit state type based upon the server procedure step exit state. 
    *
    * @return a string representing the exit state type value
    */
   public int getExitStateType() {
      return exitStateType;
   }
 
   private String exitStateMsg = "";
   protected void setExitStateMsg(String s) {
      exitStateMsg = s;
   }
   /**
    * Gets the current status text message, if
    * one exists. A status message is associated with a status code, and can
    * be returned by a Gen exit state.
    *
    * @return a string representing the exit state message value
    */
   public String getExitStateMsg() {
      return exitStateMsg;
   }
 
   private String comCfg = "";
   /**
    * Sets the value to be used for communications instead of the information in commcfg.properties.
    * For details on this information, refer to the commcfg.properties file provided.
    *
    * @param s a string containing the communications command value
    */
   public void setComCfg(String s) {
      if (s == null) comCfg = "";
      else  comCfg = s;
   }
   /**
    * Gets the value to be used for communications instead of the information in commcfg.properties.
    * For details on this information, refer to the commcfg.properties file provided.
    *
    * @return a string containing the communications command value
    */
   public String getComCfg() {
      return comCfg;
   }
 
   private URL serverLocation;
   /**
    * Sets the URL used to locate the servlet.  Set this property by calling
    * myObject.setServerLocation( getDocumentBase()) from your applet
    * or, force a server connection by using<br>
    * <code>try {new URL("http://localhost:80");} catch(MalformedURLException e) {}</code>
    * 
    * @param s a URL containing the base URL for the servlet
    */
   public void setServerLocation(URL newServerLoc) {
      serverLocation = newServerLoc;
   }
   /**
    * Gets the URL used to locate the servlet.
    * 
    * @return a URL containing the base URL for the servlet
    */
   public URL getServerLocation() {
      return serverLocation;
   }
 
   private String servletPath = "";
   /**
    * Sets the URL path to be used to locate the servlet.  Set this property by calling
    * myObject.setServletPath( ... ) from your applet.
    * If the servletPath is left blank, then a default path
    * of "servlet" will be used.
    * 
    * @param s a String containing the URL path for the servlet
    */
   public void setServletPath(String newServletPath) {
      if (newServletPath == null) servletPath = "";
      else servletPath = newServletPath;
   }
   /**
    * Gets the URL path that will be used to locate the servlet.
    * 
    * @return a String containing the URL path for the servlet
    */
   public String getServletPath() {
      return servletPath;
   }
 
   private String fileEncoding = "";
   /**
    * Sets the file encoding to be used for converting to/from UNICODE.
    *
    * @param s a string containing the file encoding value
    */
   public void setFileEncoding(String s) {
      if (s == null) fileEncoding = "";
      else  fileEncoding = s;
   }
   /**
    * Gets the file encoding that will be used for converting to/from UNICODE.
    *
    * @return a string the file encoding value
    */
   public String getFileEncoding() {
      return fileEncoding;
   }
 
   //  Property interfaces
   //     (names include full predicate viewname)
   //  get...  for each output predicate view
   //  set...  for each input predicate view
   //  both for input-output views
   //  export (set and fire pcs) for input-output and output views
 
   // support notifying bound properties when a attribute value changes
   // see pcs changes below
   protected transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
   /**
    * Adds an object to the list of listeners that are called when a property value changes.
    *
    * @param l a class object that implements the PropertyChangeListener interface.  See the test UI 
    * for an example.
    */
   public void addPropertyChangeListener (PropertyChangeListener l)
                           { pcs.addPropertyChangeListener (l);    }
   /**
    * Removes the object from the list of listeners that are called when a property value changes.
    *
    * @param l the class object that was registered as a PropertyChangeListener.  See the test UI 
    * for an example.
    */
   public void removePropertyChangeListener (PropertyChangeListener l)
                           { pcs.removePropertyChangeListener (l);    }
 
   /**
    * This method clears all import and export attribute properties. The
    * default value is used if one is specified in the model otherwise 0 is used
    * for numeric, date and time attributes, an empty string is used for string attributes
    * and "00000000000000000000" is used for timestamp attributes. For attributes in repeating
    * groups, the clear method sets the repeat count to 0. In addition to clearing
    * attribute properties, the <code>clear</code> method also clears the system properties
    * commandReturned, exitStateReturned, and exitStateMsg.
    *
    * @exception java.beans.PropertyVetoException
    * This is needed to cover the setXXXX calls used in the function.
    */
   public void clear()  throws PropertyVetoException  {
      setCommandReturned("");
      setExitStateReturned(0);
      setExitStateMsg("");
 
      importView.reset();
      exportView.reset();
      importView.InWsStudentSpecialCharacterFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentGender = FixedStringAttr.valueOf("U", 1);
      importView.InWsStudentDeceasedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      importView.InWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentDisciplinaryIncident = ShortAttr.valueOf((short)0);
      importView.InWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      importView.InWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentTwinFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAccessRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentPreviouslyPostGraduateFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentPreviouslyUnisaPostGradFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentResultBlockFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      importView.InWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAcademicRecordDistinctionFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordBursaryType = ShortAttr.valueOf((short)1);
      importView.InStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf("N", 1);
      importView.InSuGroup_MA = IntAttr.valueOf(InSuGroupMax);
      importView.InFlagGroup_MA = IntAttr.valueOf(InFlagGroupMax);
      importView.InMajorsGroup_MA = IntAttr.valueOf(InMajorsGroupMax);
      exportView.OutOrigGroup_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 50; index++)
         exportView.OutGOrigWsStudentFlagFlagType[index] = FixedStringAttr.valueOf("S", 1);
      for( int index = 0; index < 50; index++)
         exportView.OutGOrigWsStudentFlagOwner[index] = FixedStringAttr.valueOf("U", 1);
      for( int index = 0; index < 50; index++)
         exportView.OutGOrigWsStudentFlagDocumentType[index] = ShortAttr.valueOf((short)0);
      for( int index = 0; index < 50; index++)
         exportView.OutGOrigWsStudentFlagAdminOnlyFlag[index] = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      exportView.OutWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutAccountTypeContractFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutAccountTypeInUseFlag = FixedStringAttr.valueOf("Y", 1);
      exportView.OutAccountTypeIgnoreFinancialsFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAcademicRecordDistinctionFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordBursaryType = ShortAttr.valueOf((short)1);
      exportView.OutStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentSpecialCharacterFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentGender = FixedStringAttr.valueOf("U", 1);
      exportView.OutWsStudentDeceasedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentDisciplinaryIncident = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentTwinFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAccessRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPreviouslyPostGraduateFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPreviouslyUnisaPostGradFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentResultBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutSuGroup_MA = IntAttr.getDefaultValue();
      exportView.OutFlagGroup_MA = IntAttr.getDefaultValue();
      exportView.OutMajorsGroup_MA = IntAttr.getDefaultValue();
      exportView.OutWsQualificationType = FixedStringAttr.valueOf("G", 1);
      exportView.OutWsQualificationInUseFlag = FixedStringAttr.valueOf("Y", 1);
   }

   Srruf01h.SRRUF01S_IA importView = Srruf01h.SRRUF01S_IA.getInstance();
   Srruf01h.SRRUF01S_OA exportView = Srruf01h.SRRUF01S_OA.getInstance();
   public String getInWsExamCentreCode() {
      return FixedStringAttr.valueOf(importView.InWsExamCentreCode, 5);
   }
   public void setInWsExamCentreCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsExamCentreCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsExamCentreCode", null, null));
      }
      importView.InWsExamCentreCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInWsExamCentreEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsExamCentreEngDescription, 28);
   }
   public void setInWsExamCentreEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsExamCentreEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsExamCentreEngDescription", null, null));
      }
      importView.InWsExamCentreEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPrisonOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPrisonOverrideIefSuppliedFlag, 1);
   }
   public void setInPrisonOverrideIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPrisonOverrideIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPrisonOverrideIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPrisonOverrideIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPrisonOverrideIefSuppliedFlag", null, null));
      }
      importView.InPrisonOverrideIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInSpecialityCompulsaryIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InSpecialityCompulsaryIefSuppliedFlag, 1);
   }
   public void setInSpecialityCompulsaryIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InSpecialityCompulsaryIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InSpecialityCompulsaryIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSpecialityCompulsaryIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSpecialityCompulsaryIefSuppliedFlag", null, null));
      }
      importView.InSpecialityCompulsaryIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInExpertIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InExpertIefSuppliedFlag, 1);
   }
   public void setInExpertIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InExpertIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InExpertIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExpertIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExpertIefSuppliedFlag", null, null));
      }
      importView.InExpertIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailToCsfStringsString132, 132);
   }
   public void setInEmailToCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailToCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailToCsfStringsString132", null, null));
      }
      importView.InEmailToCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailFromCsfStringsString132, 132);
   }
   public void setInEmailFromCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailFromCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailFromCsfStringsString132", null, null));
      }
      importView.InEmailFromCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNameCsfStringsString132, 132);
   }
   public void setInFaxNameCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNameCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNameCsfStringsString132", null, null));
      }
      importView.InFaxNameCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxNrCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNrCsfStringsString132, 132);
   }
   public void setInFaxNrCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNrCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNrCsfStringsString132", null, null));
      }
      importView.InFaxNrCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxOrEmailCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InFaxOrEmailCsfStringsString1, 1);
   }
   public void setInFaxOrEmailCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InFaxOrEmailCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFaxOrEmailCsfStringsString1", null, null));
      }
      importView.InFaxOrEmailCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInTempBalanceIefSuppliedCount() {
      return importView.InTempBalanceIefSuppliedCount;
   }
   public void setInTempBalanceIefSuppliedCount(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InTempBalanceIefSuppliedCount has more than 9 digits.",
               new PropertyChangeEvent (this, "InTempBalanceIefSuppliedCount", null, null));
      }
      importView.InTempBalanceIefSuppliedCount = IntAttr.valueOf(s);
   }
   public void setAsStringInTempBalanceIefSuppliedCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempBalanceIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempBalanceIefSuppliedCount", null, null));
      }
      try {
          setInTempBalanceIefSuppliedCount(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempBalanceIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempBalanceIefSuppliedCount", null, null));
      }
   }
 
   public String getInAcadOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InAcadOverrideIefSuppliedFlag, 1);
   }
   public void setInAcadOverrideIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InAcadOverrideIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InAcadOverrideIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InAcadOverrideIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAcadOverrideIefSuppliedFlag", null, null));
      }
      importView.InAcadOverrideIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsWorkstationCode() {
      return FixedStringAttr.valueOf(importView.InWsWorkstationCode, 10);
   }
   public void setInWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsWorkstationCode", null, null));
      }
      importView.InWsWorkstationCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInStudentCsfStringsString50() {
      return FixedStringAttr.valueOf(importView.InStudentCsfStringsString50, 50);
   }
   public void setInStudentCsfStringsString50(String s)
      throws PropertyVetoException {
      if (s.length() > 50) {
         throw new PropertyVetoException("InStudentCsfStringsString50 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InStudentCsfStringsString50", null, null));
      }
      importView.InStudentCsfStringsString50 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInWsPrinterCode() {
      return FixedStringAttr.valueOf(importView.InWsPrinterCode, 10);
   }
   public void setInWsPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsPrinterCode", null, null));
      }
      importView.InWsPrinterCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInWsStudentNr() {
      return importView.InWsStudentNr;
   }
   public void setInWsStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStudentNr", null, null));
      }
      importView.InWsStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentNr", null, null));
      }
      try {
          setInWsStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentNr", null, null));
      }
   }
 
   public String getInWsStudentSpecialCharacterFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentSpecialCharacterFlag, 1);
   }
   public void setInWsStudentSpecialCharacterFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentSpecialCharacterFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentSpecialCharacterFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentSpecialCharacterFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentSpecialCharacterFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentSpecialCharacterFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentSpecialCharacterFlag", null, null));
      }
      importView.InWsStudentSpecialCharacterFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentMkTitle() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkTitle, 10);
   }
   public void setInWsStudentMkTitle(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStudentMkTitle must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkTitle", null, null));
      }
      importView.InWsStudentMkTitle = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStudentSurname() {
      return FixedStringAttr.valueOf(importView.InWsStudentSurname, 28);
   }
   public void setInWsStudentSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsStudentSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsStudentSurname", null, null));
      }
      importView.InWsStudentSurname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsStudentFirstNames() {
      return FixedStringAttr.valueOf(importView.InWsStudentFirstNames, 60);
   }
   public void setInWsStudentFirstNames(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InWsStudentFirstNames must be <= 60 characters.",
               new PropertyChangeEvent (this, "InWsStudentFirstNames", null, null));
      }
      importView.InWsStudentFirstNames = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public String getInWsStudentPreviousSurname() {
      return FixedStringAttr.valueOf(importView.InWsStudentPreviousSurname, 28);
   }
   public void setInWsStudentPreviousSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsStudentPreviousSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsStudentPreviousSurname", null, null));
      }
      importView.InWsStudentPreviousSurname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsStudentSquashCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentSquashCode, 8);
   }
   public void setInWsStudentSquashCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InWsStudentSquashCode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InWsStudentSquashCode", null, null));
      }
      importView.InWsStudentSquashCode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInWsStudentInitials() {
      return FixedStringAttr.valueOf(importView.InWsStudentInitials, 10);
   }
   public void setInWsStudentInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStudentInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStudentInitials", null, null));
      }
      importView.InWsStudentInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStudentIdentityNr() {
      return FixedStringAttr.valueOf(importView.InWsStudentIdentityNr, 13);
   }
   public void setInWsStudentIdentityNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 13) {
         throw new PropertyVetoException("InWsStudentIdentityNr must be <= 13 characters.",
               new PropertyChangeEvent (this, "InWsStudentIdentityNr", null, null));
      }
      importView.InWsStudentIdentityNr = FixedStringAttr.valueOf(s, (short)13);
   }
 
   public Calendar getInWsStudentBirthDate() {
      return DateAttr.toCalendar(importView.InWsStudentBirthDate);
   }
   public int getAsIntInWsStudentBirthDate() {
      return DateAttr.toInt(importView.InWsStudentBirthDate);
   }
   public void setInWsStudentBirthDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsStudentBirthDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsStudentBirthDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsStudentBirthDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsStudentBirthDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsStudentBirthDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsStudentBirthDate", null, null));
         }
      }
   }
   public void setAsIntInWsStudentBirthDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsStudentBirthDate(temp);
   }
 
   public String getInWsStudentGender() {
      return FixedStringAttr.valueOf(importView.InWsStudentGender, 1);
   }
   public void setInWsStudentGender(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentGenderPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentGenderPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentGender value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentGender", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentGender must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentGender", null, null));
      }
      importView.InWsStudentGender = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentMkNationality() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkNationality, 4);
   }
   public void setInWsStudentMkNationality(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsStudentMkNationality must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkNationality", null, null));
      }
      importView.InWsStudentMkNationality = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsStudentMkHomeLanguage() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkHomeLanguage, 2);
   }
   public void setInWsStudentMkHomeLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentMkHomeLanguage must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkHomeLanguage", null, null));
      }
      importView.InWsStudentMkHomeLanguage = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkCorrespondenceLanguage, 2);
   }
   public void setInWsStudentMkCorrespondenceLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentMkCorrespondenceLanguage must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkCorrespondenceLanguage", null, null));
      }
      importView.InWsStudentMkCorrespondenceLanguage = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInWsStudentDeceasedFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentDeceasedFlag, 1);
   }
   public void setInWsStudentDeceasedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentDeceasedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentDeceasedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentDeceasedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentDeceasedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentDeceasedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentDeceasedFlag", null, null));
      }
      importView.InWsStudentDeceasedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentLibraryBlackList() {
      return importView.InWsStudentLibraryBlackList;
   }
   public void setInWsStudentLibraryBlackList(short s)
      throws PropertyVetoException {
      Srruf01sStudyUnitRegistrationWsStudentLibraryBlackListPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentLibraryBlackListPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentLibraryBlackList value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentLibraryBlackList", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentLibraryBlackList has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentLibraryBlackList", null, null));
      }
      importView.InWsStudentLibraryBlackList = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentLibraryBlackList(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentLibraryBlackList is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentLibraryBlackList", null, null));
      }
      try {
          setInWsStudentLibraryBlackList(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentLibraryBlackList is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentLibraryBlackList", null, null));
      }
   }
 
   public String getInWsStudentExamFeesDebtFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentExamFeesDebtFlag, 1);
   }
   public void setInWsStudentExamFeesDebtFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentExamFeesDebtFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentExamFeesDebtFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentExamFeesDebtFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentExamFeesDebtFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentExamFeesDebtFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentExamFeesDebtFlag", null, null));
      }
      importView.InWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentDisciplinaryIncident() {
      return importView.InWsStudentDisciplinaryIncident;
   }
   public void setInWsStudentDisciplinaryIncident(short s)
      throws PropertyVetoException {
      Srruf01sStudyUnitRegistrationWsStudentDisciplinaryIncidentPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentDisciplinaryIncidentPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentDisciplinaryIncident value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentDisciplinaryIncident", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentDisciplinaryIncident has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentDisciplinaryIncident", null, null));
      }
      importView.InWsStudentDisciplinaryIncident = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentDisciplinaryIncident(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentDisciplinaryIncident is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentDisciplinaryIncident", null, null));
      }
      try {
          setInWsStudentDisciplinaryIncident(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentDisciplinaryIncident is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentDisciplinaryIncident", null, null));
      }
   }
 
   public String getInWsStudentPostGraduateStudiesApproved() {
      return FixedStringAttr.valueOf(importView.InWsStudentPostGraduateStudiesApproved, 1);
   }
   public void setInWsStudentPostGraduateStudiesApproved(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentPostGraduateStudiesApprovedPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentPostGraduateStudiesApprovedPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentPostGraduateStudiesApproved value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentPostGraduateStudiesApproved", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentPostGraduateStudiesApproved must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentPostGraduateStudiesApproved", null, null));
      }
      importView.InWsStudentPostGraduateStudiesApproved = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentPhasedOutStatus() {
      return importView.InWsStudentPhasedOutStatus;
   }
   public void setInWsStudentPhasedOutStatus(short s)
      throws PropertyVetoException {
      Srruf01sStudyUnitRegistrationWsStudentPhasedOutStatusPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentPhasedOutStatusPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentPhasedOutStatus value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentPhasedOutStatus", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentPhasedOutStatus has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentPhasedOutStatus", null, null));
      }
      importView.InWsStudentPhasedOutStatus = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentPhasedOutStatus(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentPhasedOutStatus is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentPhasedOutStatus", null, null));
      }
      try {
          setInWsStudentPhasedOutStatus(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentPhasedOutStatus is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentPhasedOutStatus", null, null));
      }
   }
 
   public String getInWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentFinancialBlockFlag, 1);
   }
   public void setInWsStudentFinancialBlockFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentFinancialBlockFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentFinancialBlockFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentFinancialBlockFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentFinancialBlockFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentFinancialBlockFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentFinancialBlockFlag", null, null));
      }
      importView.InWsStudentFinancialBlockFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentTwinFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentTwinFlag, 1);
   }
   public void setInWsStudentTwinFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentTwinFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentTwinFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentTwinFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentTwinFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentTwinFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentTwinFlag", null, null));
      }
      importView.InWsStudentTwinFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAccessRestrictedFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAccessRestrictedFlag, 1);
   }
   public void setInWsStudentAccessRestrictedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentAccessRestrictedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentAccessRestrictedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAccessRestrictedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAccessRestrictedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAccessRestrictedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAccessRestrictedFlag", null, null));
      }
      importView.InWsStudentAccessRestrictedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentNumberRestrictedFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentNumberRestrictedFlag, 1);
   }
   public void setInWsStudentNumberRestrictedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentNumberRestrictedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentNumberRestrictedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentNumberRestrictedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentNumberRestrictedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentNumberRestrictedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentNumberRestrictedFlag", null, null));
      }
      importView.InWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentUnisaUndergradYearsRegistered() {
      return importView.InWsStudentUnisaUndergradYearsRegistered;
   }
   public void setInWsStudentUnisaUndergradYearsRegistered(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentUnisaUndergradYearsRegistered has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentUnisaUndergradYearsRegistered", null, null));
      }
      importView.InWsStudentUnisaUndergradYearsRegistered = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentUnisaUndergradYearsRegistered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentUnisaUndergradYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaUndergradYearsRegistered", null, null));
      }
      try {
          setInWsStudentUnisaUndergradYearsRegistered(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentUnisaUndergradYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaUndergradYearsRegistered", null, null));
      }
   }
 
   public short getInWsStudentUnisaHonoursYearsRegistered() {
      return importView.InWsStudentUnisaHonoursYearsRegistered;
   }
   public void setInWsStudentUnisaHonoursYearsRegistered(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentUnisaHonoursYearsRegistered has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentUnisaHonoursYearsRegistered", null, null));
      }
      importView.InWsStudentUnisaHonoursYearsRegistered = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentUnisaHonoursYearsRegistered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentUnisaHonoursYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaHonoursYearsRegistered", null, null));
      }
      try {
          setInWsStudentUnisaHonoursYearsRegistered(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentUnisaHonoursYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaHonoursYearsRegistered", null, null));
      }
   }
 
   public short getInWsStudentUnisaMastersYearsRegistered() {
      return importView.InWsStudentUnisaMastersYearsRegistered;
   }
   public void setInWsStudentUnisaMastersYearsRegistered(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentUnisaMastersYearsRegistered has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentUnisaMastersYearsRegistered", null, null));
      }
      importView.InWsStudentUnisaMastersYearsRegistered = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentUnisaMastersYearsRegistered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentUnisaMastersYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaMastersYearsRegistered", null, null));
      }
      try {
          setInWsStudentUnisaMastersYearsRegistered(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentUnisaMastersYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaMastersYearsRegistered", null, null));
      }
   }
 
   public short getInWsStudentUnisaDoctrateYearsRegistered() {
      return importView.InWsStudentUnisaDoctrateYearsRegistered;
   }
   public void setInWsStudentUnisaDoctrateYearsRegistered(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentUnisaDoctrateYearsRegistered has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentUnisaDoctrateYearsRegistered", null, null));
      }
      importView.InWsStudentUnisaDoctrateYearsRegistered = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentUnisaDoctrateYearsRegistered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentUnisaDoctrateYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaDoctrateYearsRegistered", null, null));
      }
      try {
          setInWsStudentUnisaDoctrateYearsRegistered(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentUnisaDoctrateYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentUnisaDoctrateYearsRegistered", null, null));
      }
   }
 
   public short getInWsStudentOtherMastersYearsRegistered() {
      return importView.InWsStudentOtherMastersYearsRegistered;
   }
   public void setInWsStudentOtherMastersYearsRegistered(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentOtherMastersYearsRegistered has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentOtherMastersYearsRegistered", null, null));
      }
      importView.InWsStudentOtherMastersYearsRegistered = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentOtherMastersYearsRegistered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentOtherMastersYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentOtherMastersYearsRegistered", null, null));
      }
      try {
          setInWsStudentOtherMastersYearsRegistered(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentOtherMastersYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentOtherMastersYearsRegistered", null, null));
      }
   }
 
   public short getInWsStudentOtherDoctrateYearsRegistered() {
      return importView.InWsStudentOtherDoctrateYearsRegistered;
   }
   public void setInWsStudentOtherDoctrateYearsRegistered(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentOtherDoctrateYearsRegistered has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentOtherDoctrateYearsRegistered", null, null));
      }
      importView.InWsStudentOtherDoctrateYearsRegistered = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentOtherDoctrateYearsRegistered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentOtherDoctrateYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentOtherDoctrateYearsRegistered", null, null));
      }
      try {
          setInWsStudentOtherDoctrateYearsRegistered(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentOtherDoctrateYearsRegistered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentOtherDoctrateYearsRegistered", null, null));
      }
   }
 
   public String getInWsStudentPreviouslyPostGraduateFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentPreviouslyPostGraduateFlag, 1);
   }
   public void setInWsStudentPreviouslyPostGraduateFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentPreviouslyPostGraduateFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentPreviouslyPostGraduateFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentPreviouslyPostGraduateFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentPreviouslyPostGraduateFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentPreviouslyPostGraduateFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentPreviouslyPostGraduateFlag", null, null));
      }
      importView.InWsStudentPreviouslyPostGraduateFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentPreviouslyUnisaPostGradFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentPreviouslyUnisaPostGradFlag, 1);
   }
   public void setInWsStudentPreviouslyUnisaPostGradFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentPreviouslyUnisaPostGradFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentPreviouslyUnisaPostGradFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentPreviouslyUnisaPostGradFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentPreviouslyUnisaPostGradFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentPreviouslyUnisaPostGradFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentPreviouslyUnisaPostGradFlag", null, null));
      }
      importView.InWsStudentPreviouslyUnisaPostGradFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentResultBlockFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentResultBlockFlag, 1);
   }
   public void setInWsStudentResultBlockFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsStudentResultBlockFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsStudentResultBlockFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentResultBlockFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentResultBlockFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentResultBlockFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentResultBlockFlag", null, null));
      }
      importView.InWsStudentResultBlockFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentMkLastAcademicYear() {
      return importView.InWsStudentMkLastAcademicYear;
   }
   public void setInWsStudentMkLastAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudentMkLastAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudentMkLastAcademicYear", null, null));
      }
      importView.InWsStudentMkLastAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentMkLastAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentMkLastAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMkLastAcademicYear", null, null));
      }
      try {
          setInWsStudentMkLastAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentMkLastAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMkLastAcademicYear", null, null));
      }
   }
 
   public short getInWsStudentMkLastAcademicPeriod() {
      return importView.InWsStudentMkLastAcademicPeriod;
   }
   public void setInWsStudentMkLastAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentMkLastAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentMkLastAcademicPeriod", null, null));
      }
      importView.InWsStudentMkLastAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentMkLastAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentMkLastAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMkLastAcademicPeriod", null, null));
      }
      try {
          setInWsStudentMkLastAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentMkLastAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMkLastAcademicPeriod", null, null));
      }
   }
 
   public String getInWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentMkCountryCode, 4);
   }
   public void setInWsStudentMkCountryCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsStudentMkCountryCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsStudentMkCountryCode", null, null));
      }
      importView.InWsStudentMkCountryCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsStudentSmartCardIssued() {
      return FixedStringAttr.valueOf(importView.InWsStudentSmartCardIssued, 1);
   }
   public void setInWsStudentSmartCardIssued(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentSmartCardIssued must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentSmartCardIssued", null, null));
      }
      importView.InWsStudentSmartCardIssued = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsMatricStatusCode() {
      return FixedStringAttr.valueOf(importView.InWsMatricStatusCode, 4);
   }
   public void setInWsMatricStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsMatricStatusCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsMatricStatusCode", null, null));
      }
      importView.InWsMatricStatusCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInWsMatricStatusNumber() {
      return importView.InWsMatricStatusNumber;
   }
   public void setInWsMatricStatusNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsMatricStatusNumber has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsMatricStatusNumber", null, null));
      }
      importView.InWsMatricStatusNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricStatusNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricStatusNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusNumber", null, null));
      }
      try {
          setInWsMatricStatusNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricStatusNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusNumber", null, null));
      }
   }
 
   public short getInWsMatricStatusDateType() {
      return importView.InWsMatricStatusDateType;
   }
   public void setInWsMatricStatusDateType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsMatricStatusDateType has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsMatricStatusDateType", null, null));
      }
      importView.InWsMatricStatusDateType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricStatusDateType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricStatusDateType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusDateType", null, null));
      }
      try {
          setInWsMatricStatusDateType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricStatusDateType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricStatusDateType", null, null));
      }
   }
 
   public String getInWsMatricCertificateCode() {
      return FixedStringAttr.valueOf(importView.InWsMatricCertificateCode, 3);
   }
   public void setInWsMatricCertificateCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InWsMatricCertificateCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InWsMatricCertificateCode", null, null));
      }
      importView.InWsMatricCertificateCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInExemptionsGivenIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InExemptionsGivenIefSuppliedFlag, 1);
   }
   public void setInExemptionsGivenIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InExemptionsGivenIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InExemptionsGivenIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExemptionsGivenIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExemptionsGivenIefSuppliedFlag", null, null));
      }
      importView.InExemptionsGivenIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsUserNumber() {
      return importView.InWsUserNumber;
   }
   public void setInWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
      importView.InWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
      try {
          setInWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumber", null, null));
      }
   }
 
   public String getInWsUserType() {
      return FixedStringAttr.valueOf(importView.InWsUserType, 1);
   }
   public void setInWsUserType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsUserType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsUserType", null, null));
      }
      importView.InWsUserType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInQualificationSpecialityTypeSpecialityCode() {
      return FixedStringAttr.valueOf(importView.InQualificationSpecialityTypeSpecialityCode, 3);
   }
   public void setInQualificationSpecialityTypeSpecialityCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InQualificationSpecialityTypeSpecialityCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InQualificationSpecialityTypeSpecialityCode", null, null));
      }
      importView.InQualificationSpecialityTypeSpecialityCode = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInAnnualRecordExistsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InAnnualRecordExistsIefSuppliedFlag, 1);
   }
   public void setInAnnualRecordExistsIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InAnnualRecordExistsIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InAnnualRecordExistsIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InAnnualRecordExistsIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAnnualRecordExistsIefSuppliedFlag", null, null));
      }
      importView.InAnnualRecordExistsIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInAcademicRecordExistsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InAcademicRecordExistsIefSuppliedFlag, 1);
   }
   public void setInAcademicRecordExistsIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InAcademicRecordExistsIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InAcademicRecordExistsIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InAcademicRecordExistsIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAcademicRecordExistsIefSuppliedFlag", null, null));
      }
      importView.InAcademicRecordExistsIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCourierAddressExistsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InCourierAddressExistsIefSuppliedFlag, 1);
   }
   public void setInCourierAddressExistsIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InCourierAddressExistsIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InCourierAddressExistsIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCourierAddressExistsIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCourierAddressExistsIefSuppliedFlag", null, null));
      }
      importView.InCourierAddressExistsIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsMatricRecordCategory() {
      return importView.InWsMatricRecordCategory;
   }
   public void setInWsMatricRecordCategory(short s)
      throws PropertyVetoException {
      Srruf01sStudyUnitRegistrationWsMatricRecordCategoryPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsMatricRecordCategoryPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsMatricRecordCategory value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsMatricRecordCategory has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
      importView.InWsMatricRecordCategory = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordCategory(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
      try {
          setInWsMatricRecordCategory(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordCategory", null, null));
      }
   }
 
   public Calendar getInWsMatricRecordFullExemptionDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordFullExemptionDate);
   }
   public int getAsIntInWsMatricRecordFullExemptionDate() {
      return DateAttr.toInt(importView.InWsMatricRecordFullExemptionDate);
   }
   public void setInWsMatricRecordFullExemptionDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordFullExemptionDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordFullExemptionDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordFullExemptionDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordFullExemptionDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordFullExemptionDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordFullExemptionDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordFullExemptionDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordFullExemptionDate(temp);
   }
 
   public Calendar getInWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordExemptionEffectiveDate);
   }
   public int getAsIntInWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toInt(importView.InWsMatricRecordExemptionEffectiveDate);
   }
   public void setInWsMatricRecordExemptionEffectiveDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordExemptionEffectiveDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordExemptionEffectiveDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordExemptionEffectiveDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordExemptionEffectiveDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordExemptionEffectiveDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordExemptionEffectiveDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordExemptionEffectiveDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordExemptionEffectiveDate(temp);
   }
 
   public Calendar getInWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toCalendar(importView.InWsMatricRecordExemptionExpiryDate);
   }
   public int getAsIntInWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toInt(importView.InWsMatricRecordExemptionExpiryDate);
   }
   public void setInWsMatricRecordExemptionExpiryDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsMatricRecordExemptionExpiryDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordExemptionExpiryDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsMatricRecordExemptionExpiryDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsMatricRecordExemptionExpiryDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsMatricRecordExemptionExpiryDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsMatricRecordExemptionExpiryDate", null, null));
         }
      }
   }
   public void setAsIntInWsMatricRecordExemptionExpiryDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsMatricRecordExemptionExpiryDate(temp);
   }
 
   public String getInWsMatricRecordAuditedFlag() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordAuditedFlag, 1);
   }
   public void setInWsMatricRecordAuditedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationWsMatricRecordAuditedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationWsMatricRecordAuditedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsMatricRecordAuditedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsMatricRecordAuditedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsMatricRecordAuditedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordAuditedFlag", null, null));
      }
      importView.InWsMatricRecordAuditedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsMatricRecordMkUserCode() {
      return importView.InWsMatricRecordMkUserCode;
   }
   public void setInWsMatricRecordMkUserCode(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsMatricRecordMkUserCode has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsMatricRecordMkUserCode", null, null));
      }
      importView.InWsMatricRecordMkUserCode = IntAttr.valueOf(s);
   }
   public void setAsStringInWsMatricRecordMkUserCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMatricRecordMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordMkUserCode", null, null));
      }
      try {
          setInWsMatricRecordMkUserCode(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMatricRecordMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMatricRecordMkUserCode", null, null));
      }
   }
 
   public String getInWsMatricRecordSymbol() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordSymbol, 3);
   }
   public void setInWsMatricRecordSymbol(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InWsMatricRecordSymbol must be <= 3 characters.",
               new PropertyChangeEvent (this, "InWsMatricRecordSymbol", null, null));
      }
      importView.InWsMatricRecordSymbol = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInFinalYearIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InFinalYearIefSuppliedFlag, 1);
   }
   public void setInFinalYearIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InFinalYearIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InFinalYearIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InFinalYearIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFinalYearIefSuppliedFlag", null, null));
      }
      importView.InFinalYearIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInAbcYearsAYears() {
      return importView.InAbcYearsAYears;
   }
   public void setInAbcYearsAYears(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAbcYearsAYears has more than 2 digits.",
               new PropertyChangeEvent (this, "InAbcYearsAYears", null, null));
      }
      importView.InAbcYearsAYears = ShortAttr.valueOf(s);
   }
   public void setAsStringInAbcYearsAYears(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAbcYearsAYears is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAbcYearsAYears", null, null));
      }
      try {
          setInAbcYearsAYears(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAbcYearsAYears is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAbcYearsAYears", null, null));
      }
   }
 
   public short getInAbcYearsBYears() {
      return importView.InAbcYearsBYears;
   }
   public void setInAbcYearsBYears(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAbcYearsBYears has more than 2 digits.",
               new PropertyChangeEvent (this, "InAbcYearsBYears", null, null));
      }
      importView.InAbcYearsBYears = ShortAttr.valueOf(s);
   }
   public void setAsStringInAbcYearsBYears(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAbcYearsBYears is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAbcYearsBYears", null, null));
      }
      try {
          setInAbcYearsBYears(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAbcYearsBYears is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAbcYearsBYears", null, null));
      }
   }
 
   public short getInAbcYearsCYears() {
      return importView.InAbcYearsCYears;
   }
   public void setInAbcYearsCYears(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAbcYearsCYears has more than 2 digits.",
               new PropertyChangeEvent (this, "InAbcYearsCYears", null, null));
      }
      importView.InAbcYearsCYears = ShortAttr.valueOf(s);
   }
   public void setAsStringInAbcYearsCYears(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAbcYearsCYears is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAbcYearsCYears", null, null));
      }
      try {
          setInAbcYearsCYears(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAbcYearsCYears is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAbcYearsCYears", null, null));
      }
   }
 
   public short getInStudentAccountTypeAuditTrailReasonCode() {
      return importView.InStudentAccountTypeAuditTrailReasonCode;
   }
   public void setInStudentAccountTypeAuditTrailReasonCode(short s)
      throws PropertyVetoException {
      Srruf01sStudyUnitRegistrationStudentAccountTypeAuditTrailReasonCodePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAccountTypeAuditTrailReasonCodePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InStudentAccountTypeAuditTrailReasonCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAccountTypeAuditTrailReasonCode", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAccountTypeAuditTrailReasonCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAccountTypeAuditTrailReasonCode", null, null));
      }
      importView.InStudentAccountTypeAuditTrailReasonCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountTypeAuditTrailReasonCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountTypeAuditTrailReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountTypeAuditTrailReasonCode", null, null));
      }
      try {
          setInStudentAccountTypeAuditTrailReasonCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountTypeAuditTrailReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountTypeAuditTrailReasonCode", null, null));
      }
   }
 
   public String getInStudentAccountTypeAuditTrailAuthorisation() {
      return FixedStringAttr.valueOf(importView.InStudentAccountTypeAuditTrailAuthorisation, 28);
   }
   public void setInStudentAccountTypeAuditTrailAuthorisation(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InStudentAccountTypeAuditTrailAuthorisation must be <= 28 characters.",
               new PropertyChangeEvent (this, "InStudentAccountTypeAuditTrailAuthorisation", null, null));
      }
      importView.InStudentAccountTypeAuditTrailAuthorisation = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInAccountTypeCode() {
      return FixedStringAttr.valueOf(importView.InAccountTypeCode, 4);
   }
   public void setInAccountTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InAccountTypeCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InAccountTypeCode", null, null));
      }
      importView.InAccountTypeCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInAccountTypeBranchCode() {
      return importView.InAccountTypeBranchCode;
   }
   public void setInAccountTypeBranchCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InAccountTypeBranchCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InAccountTypeBranchCode", null, null));
      }
      importView.InAccountTypeBranchCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInAccountTypeBranchCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAccountTypeBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAccountTypeBranchCode", null, null));
      }
      try {
          setInAccountTypeBranchCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAccountTypeBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAccountTypeBranchCode", null, null));
      }
   }
 
   public int getInStudentAcademicRecordMkStudentNr() {
      return importView.InStudentAcademicRecordMkStudentNr;
   }
   public void setInStudentAcademicRecordMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordMkStudentNr", null, null));
      }
      importView.InStudentAcademicRecordMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordMkStudentNr", null, null));
      }
      try {
          setInStudentAcademicRecordMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordMkStudentNr", null, null));
      }
   }
 
   public String getInStudentAcademicRecordMkQualificationCode() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordMkQualificationCode, 5);
   }
   public void setInStudentAcademicRecordMkQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAcademicRecordMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordMkQualificationCode", null, null));
      }
      importView.InStudentAcademicRecordMkQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public short getInStudentAcademicRecordLastAcademicRegistrationYear() {
      return importView.InStudentAcademicRecordLastAcademicRegistrationYear;
   }
   public void setInStudentAcademicRecordLastAcademicRegistrationYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordLastAcademicRegistrationYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordLastAcademicRegistrationYear", null, null));
      }
      importView.InStudentAcademicRecordLastAcademicRegistrationYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordLastAcademicRegistrationYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordLastAcademicRegistrationYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordLastAcademicRegistrationYear", null, null));
      }
      try {
          setInStudentAcademicRecordLastAcademicRegistrationYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordLastAcademicRegistrationYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordLastAcademicRegistrationYear", null, null));
      }
   }
 
   public short getInStudentAcademicRecordActualCompletionYear() {
      return importView.InStudentAcademicRecordActualCompletionYear;
   }
   public void setInStudentAcademicRecordActualCompletionYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordActualCompletionYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordActualCompletionYear", null, null));
      }
      importView.InStudentAcademicRecordActualCompletionYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordActualCompletionYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordActualCompletionYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordActualCompletionYear", null, null));
      }
      try {
          setInStudentAcademicRecordActualCompletionYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordActualCompletionYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordActualCompletionYear", null, null));
      }
   }
 
   public short getInStudentAcademicRecordMkGraduationCeremonyCode() {
      return importView.InStudentAcademicRecordMkGraduationCeremonyCode;
   }
   public void setInStudentAcademicRecordMkGraduationCeremonyCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordMkGraduationCeremonyCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordMkGraduationCeremonyCode", null, null));
      }
      importView.InStudentAcademicRecordMkGraduationCeremonyCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordMkGraduationCeremonyCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordMkGraduationCeremonyCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordMkGraduationCeremonyCode", null, null));
      }
      try {
          setInStudentAcademicRecordMkGraduationCeremonyCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordMkGraduationCeremonyCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordMkGraduationCeremonyCode", null, null));
      }
   }
 
   public Calendar getInStudentAcademicRecordGraduationCeremonyDate() {
      return DateAttr.toCalendar(importView.InStudentAcademicRecordGraduationCeremonyDate);
   }
   public int getAsIntInStudentAcademicRecordGraduationCeremonyDate() {
      return DateAttr.toInt(importView.InStudentAcademicRecordGraduationCeremonyDate);
   }
   public void setInStudentAcademicRecordGraduationCeremonyDate(Calendar s)
      throws PropertyVetoException {
      importView.InStudentAcademicRecordGraduationCeremonyDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordGraduationCeremonyDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentAcademicRecordGraduationCeremonyDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentAcademicRecordGraduationCeremonyDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentAcademicRecordGraduationCeremonyDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentAcademicRecordGraduationCeremonyDate", null, null));
         }
      }
   }
   public void setAsIntInStudentAcademicRecordGraduationCeremonyDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentAcademicRecordGraduationCeremonyDate(temp);
   }
 
   public Calendar getInStudentAcademicRecordLastRegistrationDate() {
      return DateAttr.toCalendar(importView.InStudentAcademicRecordLastRegistrationDate);
   }
   public int getAsIntInStudentAcademicRecordLastRegistrationDate() {
      return DateAttr.toInt(importView.InStudentAcademicRecordLastRegistrationDate);
   }
   public void setInStudentAcademicRecordLastRegistrationDate(Calendar s)
      throws PropertyVetoException {
      importView.InStudentAcademicRecordLastRegistrationDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordLastRegistrationDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentAcademicRecordLastRegistrationDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentAcademicRecordLastRegistrationDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentAcademicRecordLastRegistrationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentAcademicRecordLastRegistrationDate", null, null));
         }
      }
   }
   public void setAsIntInStudentAcademicRecordLastRegistrationDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentAcademicRecordLastRegistrationDate(temp);
   }
 
   public Calendar getInStudentAcademicRecordFirstRegistrationDate() {
      return DateAttr.toCalendar(importView.InStudentAcademicRecordFirstRegistrationDate);
   }
   public int getAsIntInStudentAcademicRecordFirstRegistrationDate() {
      return DateAttr.toInt(importView.InStudentAcademicRecordFirstRegistrationDate);
   }
   public void setInStudentAcademicRecordFirstRegistrationDate(Calendar s)
      throws PropertyVetoException {
      importView.InStudentAcademicRecordFirstRegistrationDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordFirstRegistrationDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentAcademicRecordFirstRegistrationDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentAcademicRecordFirstRegistrationDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentAcademicRecordFirstRegistrationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentAcademicRecordFirstRegistrationDate", null, null));
         }
      }
   }
   public void setAsIntInStudentAcademicRecordFirstRegistrationDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentAcademicRecordFirstRegistrationDate(temp);
   }
 
   public Calendar getInStudentAcademicRecordLastExamDate() {
      return DateAttr.toCalendar(importView.InStudentAcademicRecordLastExamDate);
   }
   public int getAsIntInStudentAcademicRecordLastExamDate() {
      return DateAttr.toInt(importView.InStudentAcademicRecordLastExamDate);
   }
   public void setInStudentAcademicRecordLastExamDate(Calendar s)
      throws PropertyVetoException {
      importView.InStudentAcademicRecordLastExamDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordLastExamDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentAcademicRecordLastExamDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentAcademicRecordLastExamDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentAcademicRecordLastExamDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentAcademicRecordLastExamDate", null, null));
         }
      }
   }
   public void setAsIntInStudentAcademicRecordLastExamDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentAcademicRecordLastExamDate(temp);
   }
 
   public String getInStudentAcademicRecordStatusCode() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordStatusCode, 2);
   }
   public void setInStudentAcademicRecordStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAcademicRecordStatusCodePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAcademicRecordStatusCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAcademicRecordStatusCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordStatusCode", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAcademicRecordStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordStatusCode", null, null));
      }
      importView.InStudentAcademicRecordStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInStudentAcademicRecordTemporaryFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordTemporaryFlag, 1);
   }
   public void setInStudentAcademicRecordTemporaryFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAcademicRecordTemporaryFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAcademicRecordTemporaryFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAcademicRecordTemporaryFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordTemporaryFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAcademicRecordTemporaryFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordTemporaryFlag", null, null));
      }
      importView.InStudentAcademicRecordTemporaryFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAcademicRecordTemporaryStatusCode() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordTemporaryStatusCode, 2);
   }
   public void setInStudentAcademicRecordTemporaryStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAcademicRecordTemporaryStatusCodePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAcademicRecordTemporaryStatusCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAcademicRecordTemporaryStatusCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordTemporaryStatusCode", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAcademicRecordTemporaryStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordTemporaryStatusCode", null, null));
      }
      importView.InStudentAcademicRecordTemporaryStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInStudentAcademicRecordDistinctionFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordDistinctionFlag, 1);
   }
   public void setInStudentAcademicRecordDistinctionFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAcademicRecordDistinctionFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAcademicRecordDistinctionFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAcademicRecordDistinctionFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordDistinctionFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAcademicRecordDistinctionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordDistinctionFlag", null, null));
      }
      importView.InStudentAcademicRecordDistinctionFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAcademicRecordComment1() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordComment1, 50);
   }
   public void setInStudentAcademicRecordComment1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InStudentAcademicRecordComment1 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordComment1", null, null));
      }
      importView.InStudentAcademicRecordComment1 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInStudentAcademicRecordComment2() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordComment2, 50);
   }
   public void setInStudentAcademicRecordComment2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InStudentAcademicRecordComment2 must be <= 50 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordComment2", null, null));
      }
      importView.InStudentAcademicRecordComment2 = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public int getInStudentAcademicRecordLastUserCode() {
      return importView.InStudentAcademicRecordLastUserCode;
   }
   public void setInStudentAcademicRecordLastUserCode(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordLastUserCode has more than 5 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordLastUserCode", null, null));
      }
      importView.InStudentAcademicRecordLastUserCode = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordLastUserCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordLastUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordLastUserCode", null, null));
      }
      try {
          setInStudentAcademicRecordLastUserCode(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordLastUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordLastUserCode", null, null));
      }
   }
 
   public short getInStudentAcademicRecordFinalMarkControlTotal() {
      return importView.InStudentAcademicRecordFinalMarkControlTotal;
   }
   public void setInStudentAcademicRecordFinalMarkControlTotal(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordFinalMarkControlTotal has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordFinalMarkControlTotal", null, null));
      }
      importView.InStudentAcademicRecordFinalMarkControlTotal = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordFinalMarkControlTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordFinalMarkControlTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordFinalMarkControlTotal", null, null));
      }
      try {
          setInStudentAcademicRecordFinalMarkControlTotal(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordFinalMarkControlTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordFinalMarkControlTotal", null, null));
      }
   }
 
   public short getInStudentAcademicRecordTotalCreditsControlTotal() {
      return importView.InStudentAcademicRecordTotalCreditsControlTotal;
   }
   public void setInStudentAcademicRecordTotalCreditsControlTotal(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordTotalCreditsControlTotal has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordTotalCreditsControlTotal", null, null));
      }
      importView.InStudentAcademicRecordTotalCreditsControlTotal = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordTotalCreditsControlTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordTotalCreditsControlTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordTotalCreditsControlTotal", null, null));
      }
      try {
          setInStudentAcademicRecordTotalCreditsControlTotal(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordTotalCreditsControlTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordTotalCreditsControlTotal", null, null));
      }
   }
 
   public short getInStudentAcademicRecordAveragePercent() {
      return importView.InStudentAcademicRecordAveragePercent;
   }
   public void setInStudentAcademicRecordAveragePercent(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordAveragePercent has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordAveragePercent", null, null));
      }
      importView.InStudentAcademicRecordAveragePercent = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordAveragePercent(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordAveragePercent is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordAveragePercent", null, null));
      }
      try {
          setInStudentAcademicRecordAveragePercent(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordAveragePercent is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordAveragePercent", null, null));
      }
   }
 
   public String getInStudentAcademicRecordAdmissionRequirements() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordAdmissionRequirements, 50);
   }
   public void setInStudentAcademicRecordAdmissionRequirements(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InStudentAcademicRecordAdmissionRequirements must be <= 50 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordAdmissionRequirements", null, null));
      }
      importView.InStudentAcademicRecordAdmissionRequirements = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInStudentAcademicRecordOtherAdmissionRequirements() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordOtherAdmissionRequirements, 50);
   }
   public void setInStudentAcademicRecordOtherAdmissionRequirements(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InStudentAcademicRecordOtherAdmissionRequirements must be <= 50 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordOtherAdmissionRequirements", null, null));
      }
      importView.InStudentAcademicRecordOtherAdmissionRequirements = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public short getInStudentAcademicRecordWeeksExperience() {
      return importView.InStudentAcademicRecordWeeksExperience;
   }
   public void setInStudentAcademicRecordWeeksExperience(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordWeeksExperience has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordWeeksExperience", null, null));
      }
      importView.InStudentAcademicRecordWeeksExperience = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordWeeksExperience(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordWeeksExperience is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordWeeksExperience", null, null));
      }
      try {
          setInStudentAcademicRecordWeeksExperience(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordWeeksExperience is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordWeeksExperience", null, null));
      }
   }
 
   public String getInStudentAcademicRecordPrevQualRegistrationFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAcademicRecordPrevQualRegistrationFlag, 1);
   }
   public void setInStudentAcademicRecordPrevQualRegistrationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAcademicRecordPrevQualRegistrationFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAcademicRecordPrevQualRegistrationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAcademicRecordPrevQualRegistrationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordPrevQualRegistrationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAcademicRecordPrevQualRegistrationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordPrevQualRegistrationFlag", null, null));
      }
      importView.InStudentAcademicRecordPrevQualRegistrationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInStudentAcademicRecordEarliestExemptionUnisaYear() {
      return importView.InStudentAcademicRecordEarliestExemptionUnisaYear;
   }
   public void setInStudentAcademicRecordEarliestExemptionUnisaYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordEarliestExemptionUnisaYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordEarliestExemptionUnisaYear", null, null));
      }
      importView.InStudentAcademicRecordEarliestExemptionUnisaYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordEarliestExemptionUnisaYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordEarliestExemptionUnisaYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordEarliestExemptionUnisaYear", null, null));
      }
      try {
          setInStudentAcademicRecordEarliestExemptionUnisaYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordEarliestExemptionUnisaYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordEarliestExemptionUnisaYear", null, null));
      }
   }
 
   public short getInStudentAcademicRecordEarliestExemptionOtherYear() {
      return importView.InStudentAcademicRecordEarliestExemptionOtherYear;
   }
   public void setInStudentAcademicRecordEarliestExemptionOtherYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAcademicRecordEarliestExemptionOtherYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordEarliestExemptionOtherYear", null, null));
      }
      importView.InStudentAcademicRecordEarliestExemptionOtherYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordEarliestExemptionOtherYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordEarliestExemptionOtherYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordEarliestExemptionOtherYear", null, null));
      }
      try {
          setInStudentAcademicRecordEarliestExemptionOtherYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordEarliestExemptionOtherYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordEarliestExemptionOtherYear", null, null));
      }
   }
 
   public short getInStudentAcademicRecordReqvStatus() {
      return importView.InStudentAcademicRecordReqvStatus;
   }
   public void setInStudentAcademicRecordReqvStatus(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAcademicRecordReqvStatus has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAcademicRecordReqvStatus", null, null));
      }
      importView.InStudentAcademicRecordReqvStatus = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAcademicRecordReqvStatus(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAcademicRecordReqvStatus is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordReqvStatus", null, null));
      }
      try {
          setInStudentAcademicRecordReqvStatus(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAcademicRecordReqvStatus is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAcademicRecordReqvStatus", null, null));
      }
   }
 
   public int getInStudentAnnualRecordMkStudentNr() {
      return importView.InStudentAnnualRecordMkStudentNr;
   }
   public void setInStudentAnnualRecordMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkStudentNr", null, null));
      }
      importView.InStudentAnnualRecordMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkStudentNr", null, null));
      }
      try {
          setInStudentAnnualRecordMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkStudentNr", null, null));
      }
   }
 
   public short getInStudentAnnualRecordMkAcademicYear() {
      return importView.InStudentAnnualRecordMkAcademicYear;
   }
   public void setInStudentAnnualRecordMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicYear", null, null));
      }
      importView.InStudentAnnualRecordMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicYear", null, null));
      }
      try {
          setInStudentAnnualRecordMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicYear", null, null));
      }
   }
 
   public short getInStudentAnnualRecordMkAcademicPeriod() {
      return importView.InStudentAnnualRecordMkAcademicPeriod;
   }
   public void setInStudentAnnualRecordMkAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      importView.InStudentAnnualRecordMkAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      try {
          setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkAcademicPeriod", null, null));
      }
   }
 
   public short getInStudentAnnualRecordMkDisabilityTypeCode() {
      return importView.InStudentAnnualRecordMkDisabilityTypeCode;
   }
   public void setInStudentAnnualRecordMkDisabilityTypeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkDisabilityTypeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkDisabilityTypeCode", null, null));
      }
      importView.InStudentAnnualRecordMkDisabilityTypeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkDisabilityTypeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkDisabilityTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkDisabilityTypeCode", null, null));
      }
      try {
          setInStudentAnnualRecordMkDisabilityTypeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkDisabilityTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkDisabilityTypeCode", null, null));
      }
   }
 
   public String getInStudentAnnualRecordFellowStudentFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordFellowStudentFlag, 1);
   }
   public void setInStudentAnnualRecordFellowStudentFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordFellowStudentFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordFellowStudentFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordFellowStudentFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordFellowStudentFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordFellowStudentFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordFellowStudentFlag", null, null));
      }
      importView.InStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInStudentAnnualRecordBursaryType() {
      return importView.InStudentAnnualRecordBursaryType;
   }
   public void setInStudentAnnualRecordBursaryType(short s)
      throws PropertyVetoException {
      Srruf01sStudyUnitRegistrationStudentAnnualRecordBursaryTypePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordBursaryTypePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordBursaryType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryType", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAnnualRecordBursaryType has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryType", null, null));
      }
      importView.InStudentAnnualRecordBursaryType = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordBursaryType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordBursaryType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryType", null, null));
      }
      try {
          setInStudentAnnualRecordBursaryType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordBursaryType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryType", null, null));
      }
   }
 
   public int getInStudentAnnualRecordBursaryAmount() {
      return importView.InStudentAnnualRecordBursaryAmount;
   }
   public void setInStudentAnnualRecordBursaryAmount(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordBursaryAmount has more than 5 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryAmount", null, null));
      }
      importView.InStudentAnnualRecordBursaryAmount = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordBursaryAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordBursaryAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryAmount", null, null));
      }
      try {
          setInStudentAnnualRecordBursaryAmount(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordBursaryAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordBursaryAmount", null, null));
      }
   }
 
   public short getInStudentAnnualRecordMkRegionalOfficeCode() {
      return importView.InStudentAnnualRecordMkRegionalOfficeCode;
   }
   public void setInStudentAnnualRecordMkRegionalOfficeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkRegionalOfficeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
      importView.InStudentAnnualRecordMkRegionalOfficeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkRegionalOfficeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
      try {
          setInStudentAnnualRecordMkRegionalOfficeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
   }
 
   public String getInStudentAnnualRecordFirstRegistrationFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordFirstRegistrationFlag, 1);
   }
   public void setInStudentAnnualRecordFirstRegistrationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordFirstRegistrationFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordFirstRegistrationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordFirstRegistrationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordFirstRegistrationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordFirstRegistrationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordFirstRegistrationFlag", null, null));
      }
      importView.InStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordPreviousUnisaFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordPreviousUnisaFlag, 1);
   }
   public void setInStudentAnnualRecordPreviousUnisaFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordPreviousUnisaFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordPreviousUnisaFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordPreviousUnisaFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPreviousUnisaFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordPreviousUnisaFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPreviousUnisaFlag", null, null));
      }
      importView.InStudentAnnualRecordPreviousUnisaFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordMkPrevEducationalInstitCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMkPrevEducationalInstitCode, 4);
   }
   public void setInStudentAnnualRecordMkPrevEducationalInstitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InStudentAnnualRecordMkPrevEducationalInstitCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkPrevEducationalInstitCode", null, null));
      }
      importView.InStudentAnnualRecordMkPrevEducationalInstitCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInStudentAnnualRecordPrevEducationalInstitutionYr() {
      return importView.InStudentAnnualRecordPrevEducationalInstitutionYr;
   }
   public void setInStudentAnnualRecordPrevEducationalInstitutionYr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordPrevEducationalInstitutionYr has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPrevEducationalInstitutionYr", null, null));
      }
      importView.InStudentAnnualRecordPrevEducationalInstitutionYr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordPrevEducationalInstitutionYr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordPrevEducationalInstitutionYr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordPrevEducationalInstitutionYr", null, null));
      }
      try {
          setInStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordPrevEducationalInstitutionYr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordPrevEducationalInstitutionYr", null, null));
      }
   }
 
   public String getInStudentAnnualRecordMkOtherEducationalInstitCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMkOtherEducationalInstitCode, 4);
   }
   public void setInStudentAnnualRecordMkOtherEducationalInstitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InStudentAnnualRecordMkOtherEducationalInstitCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkOtherEducationalInstitCode", null, null));
      }
      importView.InStudentAnnualRecordMkOtherEducationalInstitCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInStudentAnnualRecordRegistrationMethodCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordRegistrationMethodCode, 1);
   }
   public void setInStudentAnnualRecordRegistrationMethodCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordRegistrationMethodCodePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordRegistrationMethodCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordRegistrationMethodCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordRegistrationMethodCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordRegistrationMethodCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordRegistrationMethodCode", null, null));
      }
      importView.InStudentAnnualRecordRegistrationMethodCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordDespatchMethodCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordDespatchMethodCode, 1);
   }
   public void setInStudentAnnualRecordDespatchMethodCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordDespatchMethodCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordDespatchMethodCode", null, null));
      }
      importView.InStudentAnnualRecordDespatchMethodCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordMkOccupationCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMkOccupationCode, 5);
   }
   public void setInStudentAnnualRecordMkOccupationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAnnualRecordMkOccupationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkOccupationCode", null, null));
      }
      importView.InStudentAnnualRecordMkOccupationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentAnnualRecordMkEconomicSectorCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMkEconomicSectorCode, 5);
   }
   public void setInStudentAnnualRecordMkEconomicSectorCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAnnualRecordMkEconomicSectorCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkEconomicSectorCode", null, null));
      }
      importView.InStudentAnnualRecordMkEconomicSectorCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordStatusCode, 2);
   }
   public void setInStudentAnnualRecordStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAnnualRecordStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordStatusCode", null, null));
      }
      importView.InStudentAnnualRecordStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInStudentAnnualRecordLibraryAccessLevel() {
      return importView.InStudentAnnualRecordLibraryAccessLevel;
   }
   public void setInStudentAnnualRecordLibraryAccessLevel(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InStudentAnnualRecordLibraryAccessLevel has more than 1 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      importView.InStudentAnnualRecordLibraryAccessLevel = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordLibraryAccessLevel(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      try {
          setInStudentAnnualRecordLibraryAccessLevel(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordLibraryAccessLevel", null, null));
      }
   }
 
   public short getInStudentAnnualRecordSpecialLibraryAccessLevel() {
      return importView.InStudentAnnualRecordSpecialLibraryAccessLevel;
   }
   public void setInStudentAnnualRecordSpecialLibraryAccessLevel(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InStudentAnnualRecordSpecialLibraryAccessLevel has more than 1 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      importView.InStudentAnnualRecordSpecialLibraryAccessLevel = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordSpecialLibraryAccessLevel(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordSpecialLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      try {
          setInStudentAnnualRecordSpecialLibraryAccessLevel(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordSpecialLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
   }
 
   public String getInStudentAnnualRecordMkHighestQualificationCode() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMkHighestQualificationCode, 5);
   }
   public void setInStudentAnnualRecordMkHighestQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAnnualRecordMkHighestQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkHighestQualificationCode", null, null));
      }
      importView.InStudentAnnualRecordMkHighestQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentAnnualRecordLateRegistrationFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordLateRegistrationFlag, 1);
   }
   public void setInStudentAnnualRecordLateRegistrationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordLateRegistrationFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordLateRegistrationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordLateRegistrationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordLateRegistrationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordLateRegistrationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordLateRegistrationFlag", null, null));
      }
      importView.InStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInStudentAnnualRecordPersonnelNr() {
      return importView.InStudentAnnualRecordPersonnelNr;
   }
   public void setInStudentAnnualRecordPersonnelNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordPersonnelNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPersonnelNr", null, null));
      }
      importView.InStudentAnnualRecordPersonnelNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordPersonnelNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordPersonnelNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordPersonnelNr", null, null));
      }
      try {
          setInStudentAnnualRecordPersonnelNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordPersonnelNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordPersonnelNr", null, null));
      }
   }
 
   public String getInStudentAnnualRecordTefsaApplicationFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordTefsaApplicationFlag, 1);
   }
   public void setInStudentAnnualRecordTefsaApplicationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordTefsaApplicationFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordTefsaApplicationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordTefsaApplicationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordTefsaApplicationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordTefsaApplicationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordTefsaApplicationFlag", null, null));
      }
      importView.InStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordMatriculationBoardFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordMatriculationBoardFlag, 1);
   }
   public void setInStudentAnnualRecordMatriculationBoardFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationStudentAnnualRecordMatriculationBoardFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentAnnualRecordMatriculationBoardFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordMatriculationBoardFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMatriculationBoardFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordMatriculationBoardFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMatriculationBoardFlag", null, null));
      }
      importView.InStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInStudentAnnualRecordSemesterType() {
      return importView.InStudentAnnualRecordSemesterType;
   }
   public void setInStudentAnnualRecordSemesterType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InStudentAnnualRecordSemesterType has more than 1 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordSemesterType", null, null));
      }
      importView.InStudentAnnualRecordSemesterType = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordSemesterType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordSemesterType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordSemesterType", null, null));
      }
      try {
          setInStudentAnnualRecordSemesterType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordSemesterType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordSemesterType", null, null));
      }
   }
 
   public String getInStudentAnnualRecordRegDeliveryMethod() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordRegDeliveryMethod, 1);
   }
   public void setInStudentAnnualRecordRegDeliveryMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordRegDeliveryMethod must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordRegDeliveryMethod", null, null));
      }
      importView.InStudentAnnualRecordRegDeliveryMethod = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordSpecialityCode() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordSpecialityCode);
   }
   public void setInStudentAnnualRecordSpecialityCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InStudentAnnualRecordSpecialityCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordSpecialityCode", null, null));
      }
      importView.InStudentAnnualRecordSpecialityCode = StringAttr.valueOf(s, (short)3);
   }
 
   public short getInCsfClientServerCommunicationsClientVersionNumber() {
      return importView.InCsfClientServerCommunicationsClientVersionNumber;
   }
   public void setInCsfClientServerCommunicationsClientVersionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsClientVersionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientVersionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsClientVersionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
   }
 
   public short getInCsfClientServerCommunicationsClientRevisionNumber() {
      return importView.InCsfClientServerCommunicationsClientRevisionNumber;
   }
   public void setInCsfClientServerCommunicationsClientRevisionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsClientRevisionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientRevisionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsClientRevisionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
   }
 
   public String getInCsfClientServerCommunicationsClientDevelopmentPhase() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientDevelopmentPhase, 1);
   }
   public void setInCsfClientServerCommunicationsClientDevelopmentPhase(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientDevelopmentPhase must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientDevelopmentPhase", null, null));
      }
      importView.InCsfClientServerCommunicationsClientDevelopmentPhase = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsAction, 2);
   }
   public void setInCsfClientServerCommunicationsAction(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsAction must be <= 2 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsAction", null, null));
      }
      importView.InCsfClientServerCommunicationsAction = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
   public void setInCsfClientServerCommunicationsClientIsGuiFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientIsGuiFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientIsGuiFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsClientIsGuiFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientDate() {
      return DateAttr.toCalendar(importView.InCsfClientServerCommunicationsClientDate);
   }
   public int getAsIntInCsfClientServerCommunicationsClientDate() {
      return DateAttr.toInt(importView.InCsfClientServerCommunicationsClientDate);
   }
   public void setInCsfClientServerCommunicationsClientDate(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientDate = DateAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInCsfClientServerCommunicationsClientDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientDate", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsClientDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsClientDate(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toCalendar(importView.InCsfClientServerCommunicationsClientTime);
   }
   public int getAsIntInCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toInt(importView.InCsfClientServerCommunicationsClientTime);
   }
   public void setInCsfClientServerCommunicationsClientTime(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientTime = TimeAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimeFormatter.parse(s.length() > 6 ? s.substring(0, 6) : s));
            setInCsfClientServerCommunicationsClientTime(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientTime has an invalid format (HHmmss).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTime", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsClientTime(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 6)
      {
         temp = "000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsClientTime(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toCalendar(importView.InCsfClientServerCommunicationsClientTimestamp);
   }
   public String getAsStringInCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toString(importView.InCsfClientServerCommunicationsClientTimestamp);
   }
   public void setInCsfClientServerCommunicationsClientTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InCsfClientServerCommunicationsClientTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTimestamp", null, null));
         }
      }
   }
 
   public String getInCsfClientServerCommunicationsClientTransactionCode() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientTransactionCode, 8);
   }
   public void setInCsfClientServerCommunicationsClientTransactionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientTransactionCode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTransactionCode", null, null));
      }
      importView.InCsfClientServerCommunicationsClientTransactionCode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsClientUserId() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientUserId, 8);
   }
   public void setInCsfClientServerCommunicationsClientUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientUserId must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientUserId", null, null));
      }
      importView.InCsfClientServerCommunicationsClientUserId = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsListLinkFlag, 1);
   }
   public void setInCsfClientServerCommunicationsListLinkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsListLinkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsListLinkFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsListLinkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsMaintLinkFlag, 1);
   }
   public void setInCsfClientServerCommunicationsMaintLinkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsMaintLinkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsMaintLinkFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsMaintLinkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsFirstpassFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsFirstpassFlag, 1);
   }
   public void setInCsfClientServerCommunicationsFirstpassFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsFirstpassFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsFirstpassFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsFirstpassFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int InSuGroupMax = 30;
   public short getInSuGroupCount() {
      return (short)(importView.InSuGroup_MA);
   };
 
   public void setInSuGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InSuGroupMax) {
         throw new PropertyVetoException("InSuGroupCount value is not a valid value. (0 to 30)",
               new PropertyChangeEvent (this, "InSuGroupCount", null, null));
      } else {
         importView.InSuGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGSuCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuCsfLineActionBarAction[index], 1);
   }
   public void setInGSuCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuCsfLineActionBarAction", null, null));
      }
      importView.InGSuCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGSuCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGSuCsfLineActionBarLineReturnCode[index];
   }
   public void setInGSuCsfLineActionBarLineReturnCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGSuCsfLineActionBarLineReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGSuCsfLineActionBarLineReturnCode", null, null));
      }
      importView.InGSuCsfLineActionBarLineReturnCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGSuCsfLineActionBarLineReturnCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSuCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuCsfLineActionBarLineReturnCode", null, null));
      }
      try {
          setInGSuCsfLineActionBarLineReturnCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSuCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuCsfLineActionBarLineReturnCode", null, null));
      }
   }
 
   public String getInGSuCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGSuCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGSuCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGSuCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGSuCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGSuCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGSuStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
   public void setInGSuStudentStudyUnitMkCourseStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGSuStudentStudyUnitMkCourseStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitMkCourseStudyUnitCode", null, null));
      }
      importView.InGSuStudentStudyUnitMkCourseStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInGSuStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
   public void setInGSuStudentStudyUnitMkStudyUnitOptionCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuStudentStudyUnitMkStudyUnitOptionCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitMkStudyUnitOptionCode", null, null));
      }
      importView.InGSuStudentStudyUnitMkStudyUnitOptionCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGSuStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuStudentStudyUnitLanguageCode[index], 1);
   }
   public void setInGSuStudentStudyUnitLanguageCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Srruf01sStudyUnitRegistrationStudentStudyUnitLanguageCodePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentStudyUnitLanguageCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGSuStudentStudyUnitLanguageCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitLanguageCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuStudentStudyUnitLanguageCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitLanguageCode", null, null));
      }
      importView.InGSuStudentStudyUnitLanguageCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGSuStudentStudyUnitExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGSuStudentStudyUnitExamYear[index];
   }
   public void setInGSuStudentStudyUnitExamYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGSuStudentStudyUnitExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitExamYear", null, null));
      }
      importView.InGSuStudentStudyUnitExamYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGSuStudentStudyUnitExamYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSuStudentStudyUnitExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitExamYear", null, null));
      }
      try {
          setInGSuStudentStudyUnitExamYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSuStudentStudyUnitExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitExamYear", null, null));
      }
   }
 
   public short getInGSuStudentStudyUnitMkExamPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGSuStudentStudyUnitMkExamPeriod[index];
   }
   public void setInGSuStudentStudyUnitMkExamPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGSuStudentStudyUnitMkExamPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitMkExamPeriod", null, null));
      }
      importView.InGSuStudentStudyUnitMkExamPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGSuStudentStudyUnitMkExamPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSuStudentStudyUnitMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitMkExamPeriod", null, null));
      }
      try {
          setInGSuStudentStudyUnitMkExamPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSuStudentStudyUnitMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitMkExamPeriod", null, null));
      }
   }
 
   public short getInGSuStudentStudyUnitSupplementaryExamReasonCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGSuStudentStudyUnitSupplementaryExamReasonCode[index];
   }
   public void setInGSuStudentStudyUnitSupplementaryExamReasonCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGSuStudentStudyUnitSupplementaryExamReasonCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitSupplementaryExamReasonCode", null, null));
      }
      importView.InGSuStudentStudyUnitSupplementaryExamReasonCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGSuStudentStudyUnitSupplementaryExamReasonCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSuStudentStudyUnitSupplementaryExamReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitSupplementaryExamReasonCode", null, null));
      }
      try {
          setInGSuStudentStudyUnitSupplementaryExamReasonCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSuStudentStudyUnitSupplementaryExamReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitSupplementaryExamReasonCode", null, null));
      }
   }
 
   public short getInGSuStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGSuStudentStudyUnitSemesterPeriod[index];
   }
   public void setInGSuStudentStudyUnitSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGSuStudentStudyUnitSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitSemesterPeriod", null, null));
      }
      importView.InGSuStudentStudyUnitSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGSuStudentStudyUnitSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSuStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitSemesterPeriod", null, null));
      }
      try {
          setInGSuStudentStudyUnitSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSuStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSuStudentStudyUnitSemesterPeriod", null, null));
      }
   }
 
   public String getInGSuStudentStudyUnitTutorialFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuStudentStudyUnitTutorialFlag[index], 1);
   }
   public void setInGSuStudentStudyUnitTutorialFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuStudentStudyUnitTutorialFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuStudentStudyUnitTutorialFlag", null, null));
      }
      importView.InGSuStudentStudyUnitTutorialFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGSuNdpIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuNdpIefSuppliedFlag[index], 1);
   }
   public void setInGSuNdpIefSuppliedFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGSuNdpIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGSuNdpIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuNdpIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuNdpIefSuppliedFlag", null, null));
      }
      importView.InGSuNdpIefSuppliedFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGSuOverrideIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSuOverrideIefSuppliedFlag[index], 1);
   }
   public void setInGSuOverrideIefSuppliedFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGSuOverrideIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGSuOverrideIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSuOverrideIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSuOverrideIefSuppliedFlag", null, null));
      }
      importView.InGSuOverrideIefSuppliedFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInhpGSuStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGSuStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
   public void setInhpGSuStudentStudyUnitMkCourseStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitMkCourseStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitMkCourseStudyUnitCode", null, null));
      }
      importView.InhpGSuStudentStudyUnitMkCourseStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public String getInhpGSuStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGSuStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
   public void setInhpGSuStudentStudyUnitMkStudyUnitOptionCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitMkStudyUnitOptionCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitMkStudyUnitOptionCode", null, null));
      }
      importView.InhpGSuStudentStudyUnitMkStudyUnitOptionCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInhpGSuStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGSuStudentStudyUnitLanguageCode[index], 1);
   }
   public void setInhpGSuStudentStudyUnitLanguageCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Srruf01sStudyUnitRegistrationStudentStudyUnitLanguageCodePropertyEditor pe = new Srruf01sStudyUnitRegistrationStudentStudyUnitLanguageCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitLanguageCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitLanguageCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitLanguageCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitLanguageCode", null, null));
      }
      importView.InhpGSuStudentStudyUnitLanguageCode[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInhpGSuStudentStudyUnitExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGSuStudentStudyUnitExamYear[index];
   }
   public void setInhpGSuStudentStudyUnitExamYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitExamYear", null, null));
      }
      importView.InhpGSuStudentStudyUnitExamYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGSuStudentStudyUnitExamYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitExamYear", null, null));
      }
      try {
          setInhpGSuStudentStudyUnitExamYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitExamYear", null, null));
      }
   }
 
   public short getInhpGSuStudentStudyUnitMkExamPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGSuStudentStudyUnitMkExamPeriod[index];
   }
   public void setInhpGSuStudentStudyUnitMkExamPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitMkExamPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitMkExamPeriod", null, null));
      }
      importView.InhpGSuStudentStudyUnitMkExamPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGSuStudentStudyUnitMkExamPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitMkExamPeriod", null, null));
      }
      try {
          setInhpGSuStudentStudyUnitMkExamPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitMkExamPeriod", null, null));
      }
   }
 
   public short getInhpGSuStudentStudyUnitSupplementaryExamReasonCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGSuStudentStudyUnitSupplementaryExamReasonCode[index];
   }
   public void setInhpGSuStudentStudyUnitSupplementaryExamReasonCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitSupplementaryExamReasonCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitSupplementaryExamReasonCode", null, null));
      }
      importView.InhpGSuStudentStudyUnitSupplementaryExamReasonCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGSuStudentStudyUnitSupplementaryExamReasonCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitSupplementaryExamReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitSupplementaryExamReasonCode", null, null));
      }
      try {
          setInhpGSuStudentStudyUnitSupplementaryExamReasonCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitSupplementaryExamReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitSupplementaryExamReasonCode", null, null));
      }
   }
 
   public short getInhpGSuStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGSuStudentStudyUnitSemesterPeriod[index];
   }
   public void setInhpGSuStudentStudyUnitSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InhpGSuStudentStudyUnitSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitSemesterPeriod", null, null));
      }
      importView.InhpGSuStudentStudyUnitSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGSuStudentStudyUnitSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitSemesterPeriod", null, null));
      }
      try {
          setInhpGSuStudentStudyUnitSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGSuStudentStudyUnitSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGSuStudentStudyUnitSemesterPeriod", null, null));
      }
   }
 
   public String getInhpGSuNdpIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGSuNdpIefSuppliedFlag[index], 1);
   }
   public void setInhpGSuNdpIefSuppliedFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InhpGSuNdpIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InhpGSuNdpIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InhpGSuNdpIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InhpGSuNdpIefSuppliedFlag", null, null));
      }
      importView.InhpGSuNdpIefSuppliedFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int InFlagGroupMax = 30;
   public short getInFlagGroupCount() {
      return (short)(importView.InFlagGroup_MA);
   };
 
   public void setInFlagGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InFlagGroupMax) {
         throw new PropertyVetoException("InFlagGroupCount value is not a valid value. (0 to 30)",
               new PropertyChangeEvent (this, "InFlagGroupCount", null, null));
      } else {
         importView.InFlagGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGFlagCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGFlagCsfLineActionBarAction[index], 1);
   }
   public void setInGFlagCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGFlagCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGFlagCsfLineActionBarAction", null, null));
      }
      importView.InGFlagCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGFlagCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGFlagCsfLineActionBarLineReturnCode[index];
   }
   public void setInGFlagCsfLineActionBarLineReturnCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGFlagCsfLineActionBarLineReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGFlagCsfLineActionBarLineReturnCode", null, null));
      }
      importView.InGFlagCsfLineActionBarLineReturnCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGFlagCsfLineActionBarLineReturnCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGFlagCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGFlagCsfLineActionBarLineReturnCode", null, null));
      }
      try {
          setInGFlagCsfLineActionBarLineReturnCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGFlagCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGFlagCsfLineActionBarLineReturnCode", null, null));
      }
   }
 
   public String getInGFlagCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGFlagCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGFlagCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGFlagCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGFlagCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGFlagCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGFlagCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGFlagCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGFlagCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGFlagCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGFlagCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGFlagCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGFlagWsStudentFlagCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InGFlagWsStudentFlagCode[index];
   }
   public void setInGFlagWsStudentFlagCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGFlagWsStudentFlagCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGFlagWsStudentFlagCode", null, null));
      }
      importView.InGFlagWsStudentFlagCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGFlagWsStudentFlagCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGFlagWsStudentFlagCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGFlagWsStudentFlagCode", null, null));
      }
      try {
          setInGFlagWsStudentFlagCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGFlagWsStudentFlagCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGFlagWsStudentFlagCode", null, null));
      }
   }
 
   public String getInGFlagWsStudentFlagDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGFlagWsStudentFlagDescription[index], 28);
   }
   public void setInGFlagWsStudentFlagDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InGFlagWsStudentFlagDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InGFlagWsStudentFlagDescription", null, null));
      }
      importView.InGFlagWsStudentFlagDescription[index] = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInhpGFlagWsStudentFlagCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return importView.InhpGFlagWsStudentFlagCode[index];
   }
   public void setInhpGFlagWsStudentFlagCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InhpGFlagWsStudentFlagCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InhpGFlagWsStudentFlagCode", null, null));
      }
      importView.InhpGFlagWsStudentFlagCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInhpGFlagWsStudentFlagCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InhpGFlagWsStudentFlagCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGFlagWsStudentFlagCode", null, null));
      }
      try {
          setInhpGFlagWsStudentFlagCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InhpGFlagWsStudentFlagCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InhpGFlagWsStudentFlagCode", null, null));
      }
   }
 
   public final int InMajorsGroupMax = 10;
   public short getInMajorsGroupCount() {
      return (short)(importView.InMajorsGroup_MA);
   };
 
   public void setInMajorsGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InMajorsGroupMax) {
         throw new PropertyVetoException("InMajorsGroupCount value is not a valid value. (0 to 10)",
               new PropertyChangeEvent (this, "InMajorsGroupCount", null, null));
      } else {
         importView.InMajorsGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGMajorCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGMajorCsfLineActionBarAction[index], 1);
   }
   public void setInGMajorCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGMajorCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGMajorCsfLineActionBarAction", null, null));
      }
      importView.InGMajorCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGMajorCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return importView.InGMajorCsfLineActionBarLineReturnCode[index];
   }
   public void setInGMajorCsfLineActionBarLineReturnCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGMajorCsfLineActionBarLineReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGMajorCsfLineActionBarLineReturnCode", null, null));
      }
      importView.InGMajorCsfLineActionBarLineReturnCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGMajorCsfLineActionBarLineReturnCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGMajorCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGMajorCsfLineActionBarLineReturnCode", null, null));
      }
      try {
          setInGMajorCsfLineActionBarLineReturnCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGMajorCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGMajorCsfLineActionBarLineReturnCode", null, null));
      }
   }
 
   public String getInGMajorCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGMajorCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGMajorCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGMajorCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGMajorCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGMajorCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGMajorCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGMajorCsfLineActionBarTranslatedAction[index], 1);
   }
   public void setInGMajorCsfLineActionBarTranslatedAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGMajorCsfLineActionBarTranslatedAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGMajorCsfLineActionBarTranslatedAction", null, null));
      }
      importView.InGMajorCsfLineActionBarTranslatedAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGMajorStudentQualificationMajorMkMajorSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGMajorStudentQualificationMajorMkMajorSubjectCode[index], 3);
   }
   public void setInGMajorStudentQualificationMajorMkMajorSubjectCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InGMajorStudentQualificationMajorMkMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InGMajorStudentQualificationMajorMkMajorSubjectCode", null, null));
      }
      importView.InGMajorStudentQualificationMajorMkMajorSubjectCode[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInGNewMajorCsfStringsString12(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGNewMajorCsfStringsString12[index], 12);
   }
   public void setInGNewMajorCsfStringsString12(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InGNewMajorCsfStringsString12 must be <= 12 characters.",
               new PropertyChangeEvent (this, "InGNewMajorCsfStringsString12", null, null));
      }
      importView.InGNewMajorCsfStringsString12[index] = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public String getInhpGMajorStudentQualificationMajorMkMajorSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InhpGMajorStudentQualificationMajorMkMajorSubjectCode[index], 3);
   }
   public void setInhpGMajorStudentQualificationMajorMkMajorSubjectCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InhpGMajorStudentQualificationMajorMkMajorSubjectCode must be <= 3 characters.",
               new PropertyChangeEvent (this, "InhpGMajorStudentQualificationMajorMkMajorSubjectCode", null, null));
      }
      importView.InhpGMajorStudentQualificationMajorMkMajorSubjectCode[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public int getInPpSupervisorWsUserNumber() {
      return importView.InPpSupervisorWsUserNumber;
   }
   public void setInPpSupervisorWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InPpSupervisorWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InPpSupervisorWsUserNumber", null, null));
      }
      importView.InPpSupervisorWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInPpSupervisorWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPpSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPpSupervisorWsUserNumber", null, null));
      }
      try {
          setInPpSupervisorWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPpSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPpSupervisorWsUserNumber", null, null));
      }
   }
 
   public String getInPpSupervisorWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InPpSupervisorWsUserPassword, 12);
   }
   public void setInPpSupervisorWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InPpSupervisorWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InPpSupervisorWsUserPassword", null, null));
      }
      importView.InPpSupervisorWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public int getInExpSupervisorWsUserNumber() {
      return importView.InExpSupervisorWsUserNumber;
   }
   public void setInExpSupervisorWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InExpSupervisorWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InExpSupervisorWsUserNumber", null, null));
      }
      importView.InExpSupervisorWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInExpSupervisorWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExpSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExpSupervisorWsUserNumber", null, null));
      }
      try {
          setInExpSupervisorWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExpSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExpSupervisorWsUserNumber", null, null));
      }
   }
 
   public String getInExpSupervisorWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InExpSupervisorWsUserPassword, 12);
   }
   public void setInExpSupervisorWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InExpSupervisorWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InExpSupervisorWsUserPassword", null, null));
      }
      importView.InExpSupervisorWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public int getInLateRegSupervisorWsUserNumber() {
      return importView.InLateRegSupervisorWsUserNumber;
   }
   public void setInLateRegSupervisorWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InLateRegSupervisorWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InLateRegSupervisorWsUserNumber", null, null));
      }
      importView.InLateRegSupervisorWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInLateRegSupervisorWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLateRegSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLateRegSupervisorWsUserNumber", null, null));
      }
      try {
          setInLateRegSupervisorWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLateRegSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLateRegSupervisorWsUserNumber", null, null));
      }
   }
 
   public String getInLateRegSupervisorWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InLateRegSupervisorWsUserPassword, 12);
   }
   public void setInLateRegSupervisorWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InLateRegSupervisorWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InLateRegSupervisorWsUserPassword", null, null));
      }
      importView.InLateRegSupervisorWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public String getInPpOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPpOverrideIefSuppliedFlag, 1);
   }
   public void setInPpOverrideIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPpOverrideIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPpOverrideIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPpOverrideIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPpOverrideIefSuppliedFlag", null, null));
      }
      importView.InPpOverrideIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInExpOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InExpOverrideIefSuppliedFlag, 1);
   }
   public void setInExpOverrideIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InExpOverrideIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InExpOverrideIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InExpOverrideIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InExpOverrideIefSuppliedFlag", null, null));
      }
      importView.InExpOverrideIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInLateRegIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InLateRegIefSuppliedFlag, 1);
   }
   public void setInLateRegIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor pe = new Srruf01sStudyUnitRegistrationIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InLateRegIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InLateRegIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InLateRegIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InLateRegIefSuppliedFlag", null, null));
      }
      importView.InLateRegIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInPpOverrideReasonReason() {
      return StringAttr.valueOf(importView.InPpOverrideReasonReason);
   }
   public void setInPpOverrideReasonReason(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InPpOverrideReasonReason must be <= 100 characters.",
               new PropertyChangeEvent (this, "InPpOverrideReasonReason", null, null));
      }
      importView.InPpOverrideReasonReason = StringAttr.valueOf(s, (short)100);
   }
 
   public String getInExpOverrideReasonReason() {
      return StringAttr.valueOf(importView.InExpOverrideReasonReason);
   }
   public void setInExpOverrideReasonReason(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InExpOverrideReasonReason must be <= 100 characters.",
               new PropertyChangeEvent (this, "InExpOverrideReasonReason", null, null));
      }
      importView.InExpOverrideReasonReason = StringAttr.valueOf(s, (short)100);
   }
 
   public String getInLateRegOverrideReasonReason() {
      return StringAttr.valueOf(importView.InLateRegOverrideReasonReason);
   }
   public void setInLateRegOverrideReasonReason(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 100) {
         throw new PropertyVetoException("InLateRegOverrideReasonReason must be <= 100 characters.",
               new PropertyChangeEvent (this, "InLateRegOverrideReasonReason", null, null));
      }
      importView.InLateRegOverrideReasonReason = StringAttr.valueOf(s, (short)100);
   }
 
   public int getInSecurityWsFunctionNumber() {
      return importView.InSecurityWsFunctionNumber;
   }
   public void setInSecurityWsFunctionNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSecurityWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
      importView.InSecurityWsFunctionNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsFunctionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
      try {
          setInSecurityWsFunctionNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
   }
 
   public String getInSecurityWsFunctionTrancode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsFunctionTrancode, 8);
   }
   public void setInSecurityWsFunctionTrancode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InSecurityWsFunctionTrancode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionTrancode", null, null));
      }
      importView.InSecurityWsFunctionTrancode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInSecurityWsFunctionDescription() {
      return FixedStringAttr.valueOf(importView.InSecurityWsFunctionDescription, 28);
   }
   public void setInSecurityWsFunctionDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InSecurityWsFunctionDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionDescription", null, null));
      }
      importView.InSecurityWsFunctionDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getOutWsExamCentreCode() {
      return FixedStringAttr.valueOf(exportView.OutWsExamCentreCode, 5);
   }
 
   public String getOutWsExamCentreEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsExamCentreEngDescription, 28);
   }
 
   public String getOutPrisonOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutPrisonOverrideIefSuppliedFlag, 1);
   }
 
   public String getOutSpecialityCompulsaryIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSpecialityCompulsaryIefSuppliedFlag, 1);
   }
 
   public String getOutExpertIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutExpertIefSuppliedFlag, 1);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutFaxNrCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNrCsfStringsString132, 132);
   }
 
   public String getOutFaxOrEmailCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutFaxOrEmailCsfStringsString1, 1);
   }
 
   public String getOutFaxOrEmailCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxOrEmailCsfStringsString132, 132);
   }
 
   public int getOutTempBalanceIefSuppliedCount() {
      return exportView.OutTempBalanceIefSuppliedCount;
   }
 
   public String getOutStudentCsfStringsString50() {
      return FixedStringAttr.valueOf(exportView.OutStudentCsfStringsString50, 50);
   }
 
   public final int OutOrigGroupMax = 50;
   public short getOutOrigGroupCount() {
      return (short)(exportView.OutOrigGroup_MA);
   };
 
   public short getOutGOrigWsStudentFlagCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGOrigWsStudentFlagCode[index];
   }
 
   public String getOutGOrigWsStudentFlagDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGOrigWsStudentFlagDescription[index], 28);
   }
 
   public String getOutGOrigWsStudentFlagFlagType(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGOrigWsStudentFlagFlagType[index], 1);
   }
 
   public String getOutGOrigWsStudentFlagOwner(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGOrigWsStudentFlagOwner[index], 1);
   }
 
   public short getOutGOrigWsStudentFlagDocumentType(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGOrigWsStudentFlagDocumentType[index];
   }
 
   public String getOutGOrigWsStudentFlagAdminOnlyFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGOrigWsStudentFlagAdminOnlyFlag[index], 1);
   }
 
   public String getOutWsMatricStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricStatusCode, 4);
   }
 
   public short getOutWsMatricStatusNumber() {
      return exportView.OutWsMatricStatusNumber;
   }
 
   public short getOutWsMatricStatusDateType() {
      return exportView.OutWsMatricStatusDateType;
   }
 
   public String getOutWsMatricCertificateCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricCertificateCode, 3);
   }
 
   public String getOutExemptionsGivenIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutExemptionsGivenIefSuppliedFlag, 1);
   }
 
   public String getOutBursaryAwardedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutBursaryAwardedIefSuppliedFlag, 1);
   }
 
   public int getOutWsUserNumber() {
      return exportView.OutWsUserNumber;
   }
 
   public String getOutWsUserType() {
      return FixedStringAttr.valueOf(exportView.OutWsUserType, 1);
   }
 
   public String getOutQualificationSpecialityTypeSpecialityCode() {
      return FixedStringAttr.valueOf(exportView.OutQualificationSpecialityTypeSpecialityCode, 3);
   }
 
   public String getOutQualificationSpecialityTypeExpertFlag() {
      return FixedStringAttr.valueOf(exportView.OutQualificationSpecialityTypeExpertFlag, 1);
   }
 
   public String getOutAnnualRecordExistsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutAnnualRecordExistsIefSuppliedFlag, 1);
   }
 
   public String getOutAcademicRecordExistsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutAcademicRecordExistsIefSuppliedFlag, 1);
   }
 
   public String getOutCourierAddressExistsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCourierAddressExistsIefSuppliedFlag, 1);
   }
 
   public short getOutWsMatricRecordCategory() {
      return exportView.OutWsMatricRecordCategory;
   }
 
   public Calendar getOutWsMatricRecordFullExemptionDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordFullExemptionDate);
   }
   public int getAsIntOutWsMatricRecordFullExemptionDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordFullExemptionDate);
   }
 
   public Calendar getOutWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordExemptionEffectiveDate);
   }
   public int getAsIntOutWsMatricRecordExemptionEffectiveDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordExemptionEffectiveDate);
   }
 
   public Calendar getOutWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toCalendar(exportView.OutWsMatricRecordExemptionExpiryDate);
   }
   public int getAsIntOutWsMatricRecordExemptionExpiryDate() {
      return DateAttr.toInt(exportView.OutWsMatricRecordExemptionExpiryDate);
   }
 
   public String getOutWsMatricRecordAuditedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordAuditedFlag, 1);
   }
 
   public int getOutWsMatricRecordMkUserCode() {
      return exportView.OutWsMatricRecordMkUserCode;
   }
 
   public String getOutWsMatricRecordSymbol() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordSymbol, 3);
   }
 
   public String getOutFinalYearIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutFinalYearIefSuppliedFlag, 1);
   }
 
   public short getOutAbcYearsAYears() {
      return exportView.OutAbcYearsAYears;
   }
 
   public short getOutAbcYearsBYears() {
      return exportView.OutAbcYearsBYears;
   }
 
   public short getOutAbcYearsCYears() {
      return exportView.OutAbcYearsCYears;
   }
 
   public short getOutStudentAccountTypeAuditTrailReasonCode() {
      return exportView.OutStudentAccountTypeAuditTrailReasonCode;
   }
 
   public String getOutStudentAccountTypeAuditTrailAuthorisation() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountTypeAuditTrailAuthorisation, 28);
   }
 
   public String getOutAccountTypeCode() {
      return FixedStringAttr.valueOf(exportView.OutAccountTypeCode, 4);
   }
 
   public short getOutAccountTypeBranchCode() {
      return exportView.OutAccountTypeBranchCode;
   }
 
   public String getOutAccountTypeEnglishDescription() {
      return FixedStringAttr.valueOf(exportView.OutAccountTypeEnglishDescription, 40);
   }
 
   public String getOutAccountTypeMinimumFeeFlag() {
      return FixedStringAttr.valueOf(exportView.OutAccountTypeMinimumFeeFlag, 1);
   }
 
   public short getOutAccountTypeStudyBenefitType() {
      return exportView.OutAccountTypeStudyBenefitType;
   }
 
   public Calendar getOutAccountTypeDate() {
      return DateAttr.toCalendar(exportView.OutAccountTypeDate);
   }
   public int getAsIntOutAccountTypeDate() {
      return DateAttr.toInt(exportView.OutAccountTypeDate);
   }
 
   public int getOutAccountTypeMkAddressReferenceNo() {
      return exportView.OutAccountTypeMkAddressReferenceNo;
   }
 
   public String getOutAccountTypeContractFlag() {
      return FixedStringAttr.valueOf(exportView.OutAccountTypeContractFlag, 1);
   }
 
   public String getOutAccountTypeInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutAccountTypeInUseFlag, 1);
   }
 
   public String getOutAccountTypeIgnoreFinancialsFlag() {
      return FixedStringAttr.valueOf(exportView.OutAccountTypeIgnoreFinancialsFlag, 1);
   }
 
   public String getOutDcNdpIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcNdpIefSuppliedFlag, 1);
   }
 
   public String getOutDcCertIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcCertIefSuppliedFlag, 1);
   }
 
   public String getOutDcUndergradIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcUndergradIefSuppliedFlag, 1);
   }
 
   public String getOutDcHedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcHedIefSuppliedFlag, 1);
   }
 
   public String getOutDcBedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcBedIefSuppliedFlag, 1);
   }
 
   public String getOutDcPostgradIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcPostgradIefSuppliedFlag, 1);
   }
 
   public String getOutDcHonoursIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcHonoursIefSuppliedFlag, 1);
   }
 
   public String getOutDcMastersIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcMastersIefSuppliedFlag, 1);
   }
 
   public String getOutDcDoctorsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDcDoctorsIefSuppliedFlag, 1);
   }
 
   public int getOutStudentAcademicRecordMkStudentNr() {
      return exportView.OutStudentAcademicRecordMkStudentNr;
   }
 
   public String getOutStudentAcademicRecordMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordMkQualificationCode, 5);
   }
 
   public short getOutStudentAcademicRecordLastAcademicRegistrationYear() {
      return exportView.OutStudentAcademicRecordLastAcademicRegistrationYear;
   }
 
   public short getOutStudentAcademicRecordActualCompletionYear() {
      return exportView.OutStudentAcademicRecordActualCompletionYear;
   }
 
   public short getOutStudentAcademicRecordMkGraduationCeremonyCode() {
      return exportView.OutStudentAcademicRecordMkGraduationCeremonyCode;
   }
 
   public Calendar getOutStudentAcademicRecordGraduationCeremonyDate() {
      return DateAttr.toCalendar(exportView.OutStudentAcademicRecordGraduationCeremonyDate);
   }
   public int getAsIntOutStudentAcademicRecordGraduationCeremonyDate() {
      return DateAttr.toInt(exportView.OutStudentAcademicRecordGraduationCeremonyDate);
   }
 
   public Calendar getOutStudentAcademicRecordLastRegistrationDate() {
      return DateAttr.toCalendar(exportView.OutStudentAcademicRecordLastRegistrationDate);
   }
   public int getAsIntOutStudentAcademicRecordLastRegistrationDate() {
      return DateAttr.toInt(exportView.OutStudentAcademicRecordLastRegistrationDate);
   }
 
   public Calendar getOutStudentAcademicRecordFirstRegistrationDate() {
      return DateAttr.toCalendar(exportView.OutStudentAcademicRecordFirstRegistrationDate);
   }
   public int getAsIntOutStudentAcademicRecordFirstRegistrationDate() {
      return DateAttr.toInt(exportView.OutStudentAcademicRecordFirstRegistrationDate);
   }
 
   public Calendar getOutStudentAcademicRecordLastExamDate() {
      return DateAttr.toCalendar(exportView.OutStudentAcademicRecordLastExamDate);
   }
   public int getAsIntOutStudentAcademicRecordLastExamDate() {
      return DateAttr.toInt(exportView.OutStudentAcademicRecordLastExamDate);
   }
 
   public String getOutStudentAcademicRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordStatusCode, 2);
   }
 
   public String getOutStudentAcademicRecordTemporaryFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordTemporaryFlag, 1);
   }
 
   public String getOutStudentAcademicRecordTemporaryStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordTemporaryStatusCode, 2);
   }
 
   public String getOutStudentAcademicRecordDistinctionFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordDistinctionFlag, 1);
   }
 
   public String getOutStudentAcademicRecordComment1() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordComment1, 50);
   }
 
   public String getOutStudentAcademicRecordComment2() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordComment2, 50);
   }
 
   public int getOutStudentAcademicRecordLastUserCode() {
      return exportView.OutStudentAcademicRecordLastUserCode;
   }
 
   public short getOutStudentAcademicRecordFinalMarkControlTotal() {
      return exportView.OutStudentAcademicRecordFinalMarkControlTotal;
   }
 
   public short getOutStudentAcademicRecordTotalCreditsControlTotal() {
      return exportView.OutStudentAcademicRecordTotalCreditsControlTotal;
   }
 
   public short getOutStudentAcademicRecordAveragePercent() {
      return exportView.OutStudentAcademicRecordAveragePercent;
   }
 
   public String getOutStudentAcademicRecordAdmissionRequirements() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordAdmissionRequirements, 50);
   }
 
   public String getOutStudentAcademicRecordOtherAdmissionRequirements() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordOtherAdmissionRequirements, 50);
   }
 
   public short getOutStudentAcademicRecordWeeksExperience() {
      return exportView.OutStudentAcademicRecordWeeksExperience;
   }
 
   public String getOutStudentAcademicRecordPrevQualRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordPrevQualRegistrationFlag, 1);
   }
 
   public short getOutStudentAcademicRecordEarliestExemptionUnisaYear() {
      return exportView.OutStudentAcademicRecordEarliestExemptionUnisaYear;
   }
 
   public short getOutStudentAcademicRecordEarliestExemptionOtherYear() {
      return exportView.OutStudentAcademicRecordEarliestExemptionOtherYear;
   }
 
   public short getOutStudentAcademicRecordReqvStatus() {
      return exportView.OutStudentAcademicRecordReqvStatus;
   }
 
   public int getOutStudentAnnualRecordMkStudentNr() {
      return exportView.OutStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutStudentAnnualRecordMkAcademicYear() {
      return exportView.OutStudentAnnualRecordMkAcademicYear;
   }
 
   public short getOutStudentAnnualRecordMkAcademicPeriod() {
      return exportView.OutStudentAnnualRecordMkAcademicPeriod;
   }
 
   public short getOutStudentAnnualRecordMkDisabilityTypeCode() {
      return exportView.OutStudentAnnualRecordMkDisabilityTypeCode;
   }
 
   public String getOutStudentAnnualRecordFellowStudentFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordFellowStudentFlag, 1);
   }
 
   public short getOutStudentAnnualRecordBursaryType() {
      return exportView.OutStudentAnnualRecordBursaryType;
   }
 
   public int getOutStudentAnnualRecordBursaryAmount() {
      return exportView.OutStudentAnnualRecordBursaryAmount;
   }
 
   public short getOutStudentAnnualRecordMkRegionalOfficeCode() {
      return exportView.OutStudentAnnualRecordMkRegionalOfficeCode;
   }
 
   public String getOutStudentAnnualRecordFirstRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordFirstRegistrationFlag, 1);
   }
 
   public String getOutStudentAnnualRecordPreviousUnisaFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordPreviousUnisaFlag, 1);
   }
 
   public String getOutStudentAnnualRecordMkPrevEducationalInstitCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMkPrevEducationalInstitCode, 4);
   }
 
   public short getOutStudentAnnualRecordPrevEducationalInstitutionYr() {
      return exportView.OutStudentAnnualRecordPrevEducationalInstitutionYr;
   }
 
   public String getOutStudentAnnualRecordMkOtherEducationalInstitCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMkOtherEducationalInstitCode, 4);
   }
 
   public String getOutStudentAnnualRecordRegistrationMethodCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordRegistrationMethodCode, 1);
   }
 
   public String getOutStudentAnnualRecordDespatchMethodCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordDespatchMethodCode, 1);
   }
 
   public String getOutStudentAnnualRecordMkOccupationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMkOccupationCode, 5);
   }
 
   public String getOutStudentAnnualRecordMkEconomicSectorCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMkEconomicSectorCode, 5);
   }
 
   public String getOutStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordStatusCode, 2);
   }
 
   public short getOutStudentAnnualRecordLibraryAccessLevel() {
      return exportView.OutStudentAnnualRecordLibraryAccessLevel;
   }
 
   public short getOutStudentAnnualRecordSpecialLibraryAccessLevel() {
      return exportView.OutStudentAnnualRecordSpecialLibraryAccessLevel;
   }
 
   public String getOutStudentAnnualRecordMkHighestQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMkHighestQualificationCode, 5);
   }
 
   public String getOutStudentAnnualRecordLateRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordLateRegistrationFlag, 1);
   }
 
   public int getOutStudentAnnualRecordPersonnelNr() {
      return exportView.OutStudentAnnualRecordPersonnelNr;
   }
 
   public String getOutStudentAnnualRecordTefsaApplicationFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordTefsaApplicationFlag, 1);
   }
 
   public String getOutStudentAnnualRecordMatriculationBoardFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMatriculationBoardFlag, 1);
   }
 
   public short getOutStudentAnnualRecordSemesterType() {
      return exportView.OutStudentAnnualRecordSemesterType;
   }
 
   public Calendar getOutStudentAnnualRecordRegistrationDate() {
      return DateAttr.toCalendar(exportView.OutStudentAnnualRecordRegistrationDate);
   }
   public int getAsIntOutStudentAnnualRecordRegistrationDate() {
      return DateAttr.toInt(exportView.OutStudentAnnualRecordRegistrationDate);
   }
 
   public String getOutStudentAnnualRecordRegDeliveryMethod() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordRegDeliveryMethod, 1);
   }
 
   public String getOutStudentAnnualRecordSpecialityCode() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordSpecialityCode);
   }
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
   }
 
   public String getOutWsStudentSpecialCharacterFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSpecialCharacterFlag, 1);
   }
 
   public String getOutWsStudentMkTitle() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkTitle, 10);
   }
 
   public String getOutWsStudentSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSurname, 28);
   }
 
   public String getOutWsStudentFirstNames() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFirstNames, 60);
   }
 
   public String getOutWsStudentPreviousSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviousSurname, 28);
   }
 
   public String getOutWsStudentSquashCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSquashCode, 8);
   }
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
   }
 
   public String getOutWsStudentIdentityNr() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentIdentityNr, 13);
   }
 
   public Calendar getOutWsStudentBirthDate() {
      return DateAttr.toCalendar(exportView.OutWsStudentBirthDate);
   }
   public int getAsIntOutWsStudentBirthDate() {
      return DateAttr.toInt(exportView.OutWsStudentBirthDate);
   }
 
   public String getOutWsStudentGender() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentGender, 1);
   }
 
   public String getOutWsStudentMkNationality() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkNationality, 4);
   }
 
   public String getOutWsStudentMkHomeLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkHomeLanguage, 2);
   }
 
   public String getOutWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCorrespondenceLanguage, 2);
   }
 
   public String getOutWsStudentDeceasedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentDeceasedFlag, 1);
   }
 
   public short getOutWsStudentLibraryBlackList() {
      return exportView.OutWsStudentLibraryBlackList;
   }
 
   public String getOutWsStudentExamFeesDebtFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentExamFeesDebtFlag, 1);
   }
 
   public short getOutWsStudentDisciplinaryIncident() {
      return exportView.OutWsStudentDisciplinaryIncident;
   }
 
   public String getOutWsStudentPostGraduateStudiesApproved() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPostGraduateStudiesApproved, 1);
   }
 
   public short getOutWsStudentPhasedOutStatus() {
      return exportView.OutWsStudentPhasedOutStatus;
   }
 
   public String getOutWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFinancialBlockFlag, 1);
   }
 
   public String getOutWsStudentTwinFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentTwinFlag, 1);
   }
 
   public String getOutWsStudentAccessRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAccessRestrictedFlag, 1);
   }
 
   public String getOutWsStudentNumberRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentNumberRestrictedFlag, 1);
   }
 
   public short getOutWsStudentUnisaUndergradYearsRegistered() {
      return exportView.OutWsStudentUnisaUndergradYearsRegistered;
   }
 
   public short getOutWsStudentUnisaHonoursYearsRegistered() {
      return exportView.OutWsStudentUnisaHonoursYearsRegistered;
   }
 
   public short getOutWsStudentUnisaMastersYearsRegistered() {
      return exportView.OutWsStudentUnisaMastersYearsRegistered;
   }
 
   public short getOutWsStudentUnisaDoctrateYearsRegistered() {
      return exportView.OutWsStudentUnisaDoctrateYearsRegistered;
   }
 
   public short getOutWsStudentOtherMastersYearsRegistered() {
      return exportView.OutWsStudentOtherMastersYearsRegistered;
   }
 
   public short getOutWsStudentOtherDoctrateYearsRegistered() {
      return exportView.OutWsStudentOtherDoctrateYearsRegistered;
   }
 
   public String getOutWsStudentPreviouslyPostGraduateFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviouslyPostGraduateFlag, 1);
   }
 
   public String getOutWsStudentPreviouslyUnisaPostGradFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviouslyUnisaPostGradFlag, 1);
   }
 
   public String getOutWsStudentResultBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentResultBlockFlag, 1);
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public short getOutWsStudentMkLastAcademicPeriod() {
      return exportView.OutWsStudentMkLastAcademicPeriod;
   }
 
   public String getOutWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCountryCode, 4);
   }
 
   public String getOutWsStudentSmartCardIssued() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSmartCardIssued, 1);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public short getOutCsfClientServerCommunicationsReturnCode() {
      return exportView.OutCsfClientServerCommunicationsReturnCode;
   }
 
   public short getOutCsfClientServerCommunicationsServerVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsServerRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerRevisionNumber;
   }
 
   public String getOutCsfClientServerCommunicationsServerDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerDevelopmentPhase, 1);
   }
 
   public String getOutCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsAction, 2);
   }
 
   public String getOutCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerDate() {
      return DateAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerDate);
   }
   public int getAsIntOutCsfClientServerCommunicationsServerDate() {
      return DateAttr.toInt(exportView.OutCsfClientServerCommunicationsServerDate);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerTime);
   }
   public int getAsIntOutCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toInt(exportView.OutCsfClientServerCommunicationsServerTime);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerTimestamp);
   }
   public String getAsStringOutCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toString(exportView.OutCsfClientServerCommunicationsServerTimestamp);
   }
 
   public String getOutCsfClientServerCommunicationsServerTransactionCode() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerTransactionCode, 8);
   }
 
   public String getOutCsfClientServerCommunicationsServerUserId() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerUserId, 8);
   }
 
   public String getOutCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsActionsPendingFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsActionsPendingFlag, 1);
   }
 
   public short getOutCsfClientServerCommunicationsClientVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsClientVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsClientRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsClientRevisionNumber;
   }
 
   public String getOutCsfClientServerCommunicationsClientDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientDevelopmentPhase, 1);
   }
 
   public String getOutCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsListLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsCancelFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsCancelFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsMaintLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsForceDatabaseRead() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsForceDatabaseRead, 1);
   }
 
   public final int OutSuGroupMax = 30;
   public short getOutSuGroupCount() {
      return (short)(exportView.OutSuGroup_MA);
   };
 
   public String getOutGSuCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGSuCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGSuCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGSuCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGSuCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGSuStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
 
   public String getOutGSuStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
 
   public String getOutGSuStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuStudentStudyUnitLanguageCode[index], 1);
   }
 
   public short getOutGSuStudentStudyUnitExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGSuStudentStudyUnitExamYear[index];
   }
 
   public short getOutGSuStudentStudyUnitMkExamPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGSuStudentStudyUnitMkExamPeriod[index];
   }
 
   public short getOutGSuStudentStudyUnitSupplementaryExamReasonCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGSuStudentStudyUnitSupplementaryExamReasonCode[index];
   }
 
   public short getOutGSuStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGSuStudentStudyUnitSemesterPeriod[index];
   }
 
   public String getOutGSuStudentStudyUnitTutorialFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuStudentStudyUnitTutorialFlag[index], 1);
   }
 
   public String getOutGSuNdpIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuNdpIefSuppliedFlag[index], 1);
   }
 
   public String getOutGSuOverrideIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSuOverrideIefSuppliedFlag[index], 1);
   }
 
   public String getOuthpGSuStudentStudyUnitMkCourseStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGSuStudentStudyUnitMkCourseStudyUnitCode[index], 7);
   }
 
   public String getOuthpGSuStudentStudyUnitMkStudyUnitOptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGSuStudentStudyUnitMkStudyUnitOptionCode[index], 1);
   }
 
   public String getOuthpGSuStudentStudyUnitLanguageCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGSuStudentStudyUnitLanguageCode[index], 1);
   }
 
   public short getOuthpGSuStudentStudyUnitExamYear(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGSuStudentStudyUnitExamYear[index];
   }
 
   public short getOuthpGSuStudentStudyUnitMkExamPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGSuStudentStudyUnitMkExamPeriod[index];
   }
 
   public short getOuthpGSuStudentStudyUnitSupplementaryExamReasonCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGSuStudentStudyUnitSupplementaryExamReasonCode[index];
   }
 
   public short getOuthpGSuStudentStudyUnitSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGSuStudentStudyUnitSemesterPeriod[index];
   }
 
   public String getOuthpGSuNdpIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGSuNdpIefSuppliedFlag[index], 1);
   }
 
   public final int OutFlagGroupMax = 30;
   public short getOutFlagGroupCount() {
      return (short)(exportView.OutFlagGroup_MA);
   };
 
   public String getOutGFlagCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGFlagCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGFlagCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGFlagCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGFlagCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGFlagCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGFlagCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGFlagCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public short getOutGFlagWsStudentFlagCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OutGFlagWsStudentFlagCode[index];
   }
 
   public String getOutGFlagWsStudentFlagDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGFlagWsStudentFlagDescription[index], 28);
   }
 
   public short getOuthpGFlagWsStudentFlagCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return exportView.OuthpGFlagWsStudentFlagCode[index];
   }
 
   public final int OutMajorsGroupMax = 10;
   public short getOutMajorsGroupCount() {
      return (short)(exportView.OutMajorsGroup_MA);
   };
 
   public String getOutGMajorCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGMajorCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGMajorCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return exportView.OutGMajorCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGMajorCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGMajorCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGMajorCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGMajorCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGMajorStudentQualificationMajorMkMajorSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGMajorStudentQualificationMajorMkMajorSubjectCode[index], 3);
   }
 
   public String getOutGNewMajorCsfStringsString12(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGNewMajorCsfStringsString12[index], 12);
   }
 
   public String getOuthpGMajorStudentQualificationMajorMkMajorSubjectCode(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OuthpGMajorStudentQualificationMajorMkMajorSubjectCode[index], 3);
   }
 
   public String getOutAcadOverrideIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutAcadOverrideIefSuppliedFlag, 1);
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationType() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationType, 1);
   }
 
   public String getOutWsQualificationInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationInUseFlag, 1);
   }
 
   public String getOutWsQualificationUnderPostCategory() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationUnderPostCategory, 1);
   }
 
   public int getOutSecurityWsFunctionNumber() {
      return exportView.OutSecurityWsFunctionNumber;
   }
 
   public String getOutSecurityWsFunctionTrancode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsFunctionTrancode, 8);
   }
 
   public String getOutSecurityWsFunctionDescription() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsFunctionDescription, 28);
   }
 
};
