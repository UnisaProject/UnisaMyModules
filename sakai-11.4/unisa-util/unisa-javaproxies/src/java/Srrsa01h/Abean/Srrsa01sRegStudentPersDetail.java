package Srrsa01h.Abean;
 
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
public class Srrsa01sRegStudentPersDetail  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srrsa01sRegStudentPersDetail");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Srrsa01sRegStudentPersDetail() {
      super();
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      symbols.setGroupingSeparator(',');
      decimalFormatter = new DecimalFormat("###################.###################", symbols);
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
 
 
   private Srrsa01sRegStudentPersDetailOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srrsa01sRegStudentPersDetailOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrrsa01sRegStudentPersDetailOperation();
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
      private Srrsa01sRegStudentPersDetail rP;
      operListener(Srrsa01sRegStudentPersDetail r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srrsa01sRegStudentPersDetail", "Listener heard that Srrsa01sRegStudentPersDetailOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srrsa01sRegStudentPersDetail ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srrsa01sRegStudentPersDetail");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srrsa01sRegStudentPersDetail ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srrsa01sRegStudentPersDetail";
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
      importView.InStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordVotersRollFlag = FixedStringAttr.valueOf("N", 1);
      importView.InStudentAnnualRecordAvailableForSrcFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentSpecialCharacterFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentGender = FixedStringAttr.valueOf("U", 1);
      importView.InWsStudentPreviouslyPostGraduateFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      importView.InWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentReferenceOldInstitution = ShortAttr.valueOf((short)0);
      importView.InWsStudentApplicationMatricCertificate = StringAttr.valueOf(" ");
      importView.InWsStudentApplicationHeAdmission = StringAttr.valueOf(" ");
      exportView.OutStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordBursaryType = ShortAttr.valueOf((short)1);
      exportView.OutStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordVotersRollFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordAvailableForSrcFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAnnualRecordBooksellersPermissionFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentSpecialCharacterFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentGender = FixedStringAttr.valueOf("U", 1);
      exportView.OutWsStudentPreviouslyPostGraduateFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPreviouslyUnisaPostGradFlag = FixedStringAttr.valueOf("N", 1);
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
      exportView.OutWsStudentResultBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsMatricRecordCategory = ShortAttr.valueOf((short)6);
      exportView.OutWsMatricRecordAuditedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentReferenceOldInstitution = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentApplicationMatricCertificate = StringAttr.valueOf(" ");
      exportView.OutWsStudentApplicationHeAdmission = StringAttr.valueOf(" ");
   }

   Srrsa01h.SRRSA01S_IA importView = Srrsa01h.SRRSA01S_IA.getInstance();
   Srrsa01h.SRRSA01S_OA exportView = Srrsa01h.SRRSA01S_OA.getInstance();
   public String getInOverrideYearIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InOverrideYearIefSuppliedFlag, 1);
   }
   public void setInOverrideYearIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailIefSuppliedFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InOverrideYearIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InOverrideYearIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InOverrideYearIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InOverrideYearIefSuppliedFlag", null, null));
      }
      importView.InOverrideYearIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InEmailOrFaxCsfStringsString1, 1);
   }
   public void setInEmailOrFaxCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InEmailOrFaxCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InEmailOrFaxCsfStringsString1", null, null));
      }
      importView.InEmailOrFaxCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNoCsfStringsString132, 132);
   }
   public void setInFaxNoCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNoCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNoCsfStringsString132", null, null));
      }
      importView.InFaxNoCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
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
 
   public String getInNationalityWsCountryEngDescription() {
      return FixedStringAttr.valueOf(importView.InNationalityWsCountryEngDescription, 28);
   }
   public void setInNationalityWsCountryEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InNationalityWsCountryEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InNationalityWsCountryEngDescription", null, null));
      }
      importView.InNationalityWsCountryEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public String getInRotorMblFlagsCsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InRotorMblFlagsCsfStringsString100, 100);
   }
   public void setInRotorMblFlagsCsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InRotorMblFlagsCsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InRotorMblFlagsCsfStringsString100", null, null));
      }
      importView.InRotorMblFlagsCsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInSblQualificationCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InSblQualificationCsfStringsString1, 1);
   }
   public void setInSblQualificationCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InSblQualificationCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSblQualificationCsfStringsString1", null, null));
      }
      importView.InSblQualificationCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInForeignCountryNameWsCountryEngDescription() {
      return FixedStringAttr.valueOf(importView.InForeignCountryNameWsCountryEngDescription, 28);
   }
   public void setInForeignCountryNameWsCountryEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InForeignCountryNameWsCountryEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InForeignCountryNameWsCountryEngDescription", null, null));
      }
      importView.InForeignCountryNameWsCountryEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInOverrideUserCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InOverrideUserCsfStringsString1, 1);
   }
   public void setInOverrideUserCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InOverrideUserCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InOverrideUserCsfStringsString1", null, null));
      }
      importView.InOverrideUserCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsFunctionNumber() {
      return importView.InWsFunctionNumber;
   }
   public void setInWsFunctionNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
      importView.InWsFunctionNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsFunctionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
      try {
          setInWsFunctionNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsFunctionNumber", null, null));
      }
   }
 
   public String getInWsFunctionTrancode() {
      return FixedStringAttr.valueOf(importView.InWsFunctionTrancode, 8);
   }
   public void setInWsFunctionTrancode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InWsFunctionTrancode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InWsFunctionTrancode", null, null));
      }
      importView.InWsFunctionTrancode = FixedStringAttr.valueOf(s, (short)8);
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
 
   public String getInCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
   public void setInCsfClientServerCommunicationsObjectRetrievedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsObjectRetrievedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsObjectRetrievedFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsObjectRetrievedFlag = FixedStringAttr.valueOf(s, (short)1);
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
      Srrsa01sRegStudentPersDetailStudentAnnualRecordFellowStudentFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordFellowStudentFlagPropertyEditor();
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
 
   public String getInStudentAnnualRecordPreviousUnisaFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordPreviousUnisaFlag, 1);
   }
   public void setInStudentAnnualRecordPreviousUnisaFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailStudentAnnualRecordPreviousUnisaFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordPreviousUnisaFlagPropertyEditor();
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
      Srrsa01sRegStudentPersDetailStudentAnnualRecordRegistrationMethodCodePropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordRegistrationMethodCodePropertyEditor();
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
 
   public String getInStudentAnnualRecordTefsaApplicationFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordTefsaApplicationFlag, 1);
   }
   public void setInStudentAnnualRecordTefsaApplicationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailStudentAnnualRecordTefsaApplicationFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordTefsaApplicationFlagPropertyEditor();
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
      Srrsa01sRegStudentPersDetailStudentAnnualRecordMatriculationBoardFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordMatriculationBoardFlagPropertyEditor();
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
 
   public String getInStudentAnnualRecordVotersRollFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordVotersRollFlag, 1);
   }
   public void setInStudentAnnualRecordVotersRollFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailStudentAnnualRecordVotersRollFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordVotersRollFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordVotersRollFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordVotersRollFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordVotersRollFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordVotersRollFlag", null, null));
      }
      importView.InStudentAnnualRecordVotersRollFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordAvailableForSrcFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordAvailableForSrcFlag, 1);
   }
   public void setInStudentAnnualRecordAvailableForSrcFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailStudentAnnualRecordAvailableForSrcFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailStudentAnnualRecordAvailableForSrcFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAnnualRecordAvailableForSrcFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordAvailableForSrcFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordAvailableForSrcFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordAvailableForSrcFlag", null, null));
      }
      importView.InStudentAnnualRecordAvailableForSrcFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordPipelineStudent() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordPipelineStudent, 5);
   }
   public void setInStudentAnnualRecordPipelineStudent(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAnnualRecordPipelineStudent must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPipelineStudent", null, null));
      }
      importView.InStudentAnnualRecordPipelineStudent = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public short getInStudentAnnualRecordMkContactCentre() {
      return importView.InStudentAnnualRecordMkContactCentre;
   }
   public void setInStudentAnnualRecordMkContactCentre(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentAnnualRecordMkContactCentre has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordMkContactCentre", null, null));
      }
      importView.InStudentAnnualRecordMkContactCentre = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAnnualRecordMkContactCentre(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAnnualRecordMkContactCentre is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkContactCentre", null, null));
      }
      try {
          setInStudentAnnualRecordMkContactCentre(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAnnualRecordMkContactCentre is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAnnualRecordMkContactCentre", null, null));
      }
   }
 
   public String getInStudentAnnualRecordSmDeliveryMethod() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordSmDeliveryMethod, 1);
   }
   public void setInStudentAnnualRecordSmDeliveryMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordSmDeliveryMethod must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordSmDeliveryMethod", null, null));
      }
      importView.InStudentAnnualRecordSmDeliveryMethod = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordCommunicationMethod() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordCommunicationMethod, 1);
   }
   public void setInStudentAnnualRecordCommunicationMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordCommunicationMethod must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordCommunicationMethod", null, null));
      }
      importView.InStudentAnnualRecordCommunicationMethod = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordActivityLastYear() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordActivityLastYear, 2);
   }
   public void setInStudentAnnualRecordActivityLastYear(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAnnualRecordActivityLastYear must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordActivityLastYear", null, null));
      }
      importView.InStudentAnnualRecordActivityLastYear = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInStudentAnnualRecordJobRelatedToStudy() {
      return FixedStringAttr.valueOf(importView.InStudentAnnualRecordJobRelatedToStudy, 1);
   }
   public void setInStudentAnnualRecordJobRelatedToStudy(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordJobRelatedToStudy must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordJobRelatedToStudy", null, null));
      }
      importView.InStudentAnnualRecordJobRelatedToStudy = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInStudentAnnualRecordTutorialMethod() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordTutorialMethod);
   }
   public void setInStudentAnnualRecordTutorialMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAnnualRecordTutorialMethod must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordTutorialMethod", null, null));
      }
      importView.InStudentAnnualRecordTutorialMethod = StringAttr.valueOf(s, (short)2);
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
 
   public String getInStudentAnnualRecordCurrentStaff() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordCurrentStaff);
   }
   public void setInStudentAnnualRecordCurrentStaff(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordCurrentStaff must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordCurrentStaff", null, null));
      }
      importView.InStudentAnnualRecordCurrentStaff = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordDependantStaff() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordDependantStaff);
   }
   public void setInStudentAnnualRecordDependantStaff(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordDependantStaff must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordDependantStaff", null, null));
      }
      importView.InStudentAnnualRecordDependantStaff = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordQualCompleting() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordQualCompleting);
   }
   public void setInStudentAnnualRecordQualCompleting(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordQualCompleting must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordQualCompleting", null, null));
      }
      importView.InStudentAnnualRecordQualCompleting = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAnnualRecordQualExplain() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordQualExplain);
   }
   public void setInStudentAnnualRecordQualExplain(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InStudentAnnualRecordQualExplain must be <= 100 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordQualExplain", null, null));
      }
      importView.InStudentAnnualRecordQualExplain = StringAttr.valueOf(s, (short)100);
   }
 
   public String getInStudentAnnualRecordPrisoner() {
      return StringAttr.valueOf(importView.InStudentAnnualRecordPrisoner);
   }
   public void setInStudentAnnualRecordPrisoner(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAnnualRecordPrisoner must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAnnualRecordPrisoner", null, null));
      }
      importView.InStudentAnnualRecordPrisoner = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsAddressAuditFlag() {
      return FixedStringAttr.valueOf(importView.InWsAddressAuditFlag, 1);
   }
   public void setInWsAddressAuditFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsAddressAuditFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsAddressAuditFlag", null, null));
      }
      importView.InWsAddressAuditFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressLine1, 28);
   }
   public void setInWsAddressAddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressAddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressLine1", null, null));
      }
      importView.InWsAddressAddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressLine2, 28);
   }
   public void setInWsAddressAddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressAddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressLine2", null, null));
      }
      importView.InWsAddressAddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressLine3, 28);
   }
   public void setInWsAddressAddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressAddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressLine3", null, null));
      }
      importView.InWsAddressAddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressLine4, 28);
   }
   public void setInWsAddressAddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressAddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressLine4", null, null));
      }
      importView.InWsAddressAddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressLine5, 28);
   }
   public void setInWsAddressAddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressAddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressLine5", null, null));
      }
      importView.InWsAddressAddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressLine6, 28);
   }
   public void setInWsAddressAddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressAddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressLine6", null, null));
      }
      importView.InWsAddressAddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInWsAddressPostalCode() {
      return importView.InWsAddressPostalCode;
   }
   public void setInWsAddressPostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsAddressPostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsAddressPostalCode", null, null));
      }
      importView.InWsAddressPostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressPostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressPostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressPostalCode", null, null));
      }
      try {
          setInWsAddressPostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressPostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressPostalCode", null, null));
      }
   }
 
   public String getInWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressFaxNumber, 28);
   }
   public void setInWsAddressFaxNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressFaxNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressFaxNumber", null, null));
      }
      importView.InWsAddressFaxNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(importView.InWsAddressEmailAddress, 28);
   }
   public void setInWsAddressEmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressEmailAddress must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressEmailAddress", null, null));
      }
      importView.InWsAddressEmailAddress = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressHomeNumber, 28);
   }
   public void setInWsAddressHomeNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressHomeNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressHomeNumber", null, null));
      }
      importView.InWsAddressHomeNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressWorkNumber, 28);
   }
   public void setInWsAddressWorkNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressWorkNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressWorkNumber", null, null));
      }
      importView.InWsAddressWorkNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsCountryCode() {
      return FixedStringAttr.valueOf(importView.InWsCountryCode, 4);
   }
   public void setInWsCountryCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsCountryCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsCountryCode", null, null));
      }
      importView.InWsCountryCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsCountryEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsCountryEngDescription, 28);
   }
   public void setInWsCountryEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsCountryEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsCountryEngDescription", null, null));
      }
      importView.InWsCountryEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCorrLanguageWsLanguageEngDescription() {
      return FixedStringAttr.valueOf(importView.InCorrLanguageWsLanguageEngDescription, 28);
   }
   public void setInCorrLanguageWsLanguageEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InCorrLanguageWsLanguageEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCorrLanguageWsLanguageEngDescription", null, null));
      }
      importView.InCorrLanguageWsLanguageEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInWsDisabilityTypeCode() {
      return importView.InWsDisabilityTypeCode;
   }
   public void setInWsDisabilityTypeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsDisabilityTypeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsDisabilityTypeCode", null, null));
      }
      importView.InWsDisabilityTypeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsDisabilityTypeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsDisabilityTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsDisabilityTypeCode", null, null));
      }
      try {
          setInWsDisabilityTypeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsDisabilityTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsDisabilityTypeCode", null, null));
      }
   }
 
   public String getInWsDisabilityTypeEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsDisabilityTypeEngDescription, 28);
   }
   public void setInWsDisabilityTypeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsDisabilityTypeEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsDisabilityTypeEngDescription", null, null));
      }
      importView.InWsDisabilityTypeEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsQualificationCode() {
      return FixedStringAttr.valueOf(importView.InWsQualificationCode, 5);
   }
   public void setInWsQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsQualificationCode", null, null));
      }
      importView.InWsQualificationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInWsQualificationEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsQualificationEngDescription, 28);
   }
   public void setInWsQualificationEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsQualificationEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsQualificationEngDescription", null, null));
      }
      importView.InWsQualificationEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
      Srrsa01sRegStudentPersDetailWsStudentSpecialCharacterFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentSpecialCharacterFlagPropertyEditor();
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
      Srrsa01sRegStudentPersDetailWsStudentGenderPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentGenderPropertyEditor();
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
 
   public String getInWsStudentPreviouslyPostGraduateFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentPreviouslyPostGraduateFlag, 1);
   }
   public void setInWsStudentPreviouslyPostGraduateFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailWsStudentPreviouslyPostGraduateFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentPreviouslyPostGraduateFlagPropertyEditor();
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
 
   public String getInWsStudentPassportNo() {
      return StringAttr.valueOf(importView.InWsStudentPassportNo);
   }
   public void setInWsStudentPassportNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InWsStudentPassportNo must be <= 30 characters.",
               new PropertyChangeEvent (this, "InWsStudentPassportNo", null, null));
      }
      importView.InWsStudentPassportNo = StringAttr.valueOf(s, (short)30);
   }
 
   public double getInWsStudentMtrSchool() {
      return importView.InWsStudentMtrSchool;
   }
   public void setInWsStudentMtrSchool(double s)
      throws PropertyVetoException {
      int decimals = 0;
      boolean decimal_found = false;
      String tempDataStr = decimalFormatter.format(s);
      for (int i=tempDataStr.length(); i>0; i--) {
         if (tempDataStr.charAt(i-1) == '.') {
            decimal_found = true;
            break;
         }
         decimals++;
      }
      if (decimal_found == true && decimals > 0) {
         throw new PropertyVetoException("InWsStudentMtrSchool has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InWsStudentMtrSchool", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000.0) {
         throw new PropertyVetoException("InWsStudentMtrSchool has more than 10 integral digits.",
               new PropertyChangeEvent (this, "InWsStudentMtrSchool", null, null));
      }
      importView.InWsStudentMtrSchool = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsStudentMtrSchool(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentMtrSchool is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMtrSchool", null, null));
      }
      try {
          setInWsStudentMtrSchool(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentMtrSchool is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentMtrSchool", null, null));
      }
   }
 
   public String getInWsEconomicSectorEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsEconomicSectorEngDescription, 28);
   }
   public void setInWsEconomicSectorEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsEconomicSectorEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsEconomicSectorEngDescription", null, null));
      }
      importView.InWsEconomicSectorEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsEthnicGroupCode() {
      return FixedStringAttr.valueOf(importView.InWsEthnicGroupCode, 4);
   }
   public void setInWsEthnicGroupCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsEthnicGroupCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsEthnicGroupCode", null, null));
      }
      importView.InWsEthnicGroupCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsEthnicGroupEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsEthnicGroupEngDescription, 28);
   }
   public void setInWsEthnicGroupEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsEthnicGroupEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsEthnicGroupEngDescription", null, null));
      }
      importView.InWsEthnicGroupEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public String getInHomeLanguageWsLanguageEngDescription() {
      return FixedStringAttr.valueOf(importView.InHomeLanguageWsLanguageEngDescription, 28);
   }
   public void setInHomeLanguageWsLanguageEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InHomeLanguageWsLanguageEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InHomeLanguageWsLanguageEngDescription", null, null));
      }
      importView.InHomeLanguageWsLanguageEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public short getInWsMatricRecordCategory() {
      return importView.InWsMatricRecordCategory;
   }
   public void setInWsMatricRecordCategory(short s)
      throws PropertyVetoException {
      Srrsa01sRegStudentPersDetailWsMatricRecordCategoryPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsMatricRecordCategoryPropertyEditor();
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
 
   public String getInWsMatricRecordAuditedFlag() {
      return FixedStringAttr.valueOf(importView.InWsMatricRecordAuditedFlag, 1);
   }
   public void setInWsMatricRecordAuditedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailWsMatricRecordAuditedFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsMatricRecordAuditedFlagPropertyEditor();
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
 
   public String getInWsOccupationEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsOccupationEngDescription, 28);
   }
   public void setInWsOccupationEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsOccupationEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsOccupationEngDescription", null, null));
      }
      importView.InWsOccupationEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInStudentExamCentreMkExamCentreCode() {
      return FixedStringAttr.valueOf(importView.InStudentExamCentreMkExamCentreCode, 5);
   }
   public void setInStudentExamCentreMkExamCentreCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentExamCentreMkExamCentreCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentExamCentreMkExamCentreCode", null, null));
      }
      importView.InStudentExamCentreMkExamCentreCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInPrevWsEducationalInstitutionEngName() {
      return FixedStringAttr.valueOf(importView.InPrevWsEducationalInstitutionEngName, 28);
   }
   public void setInPrevWsEducationalInstitutionEngName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InPrevWsEducationalInstitutionEngName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPrevWsEducationalInstitutionEngName", null, null));
      }
      importView.InPrevWsEducationalInstitutionEngName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInOtherWsEducationalInstitutionEngName() {
      return FixedStringAttr.valueOf(importView.InOtherWsEducationalInstitutionEngName, 28);
   }
   public void setInOtherWsEducationalInstitutionEngName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InOtherWsEducationalInstitutionEngName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InOtherWsEducationalInstitutionEngName", null, null));
      }
      importView.InOtherWsEducationalInstitutionEngName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInAddressControlCodeCsfStringsString28() {
      return FixedStringAttr.valueOf(importView.InAddressControlCodeCsfStringsString28, 28);
   }
   public void setInAddressControlCodeCsfStringsString28(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InAddressControlCodeCsfStringsString28 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InAddressControlCodeCsfStringsString28", null, null));
      }
      importView.InAddressControlCodeCsfStringsString28 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInChangedAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InChangedAddressCsfStringsString1, 1);
   }
   public void setInChangedAddressCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InChangedAddressCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InChangedAddressCsfStringsString1", null, null));
      }
      importView.InChangedAddressCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInChangedContactNumberCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InChangedContactNumberCsfStringsString1, 1);
   }
   public void setInChangedContactNumberCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InChangedContactNumberCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InChangedContactNumberCsfStringsString1", null, null));
      }
      importView.InChangedContactNumberCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInChangedStudentCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InChangedStudentCsfStringsString1, 1);
   }
   public void setInChangedStudentCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InChangedStudentCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InChangedStudentCsfStringsString1", null, null));
      }
      importView.InChangedStudentCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInChangedStudentAnnRecordCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InChangedStudentAnnRecordCsfStringsString1, 1);
   }
   public void setInChangedStudentAnnRecordCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InChangedStudentAnnRecordCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InChangedStudentAnnRecordCsfStringsString1", null, null));
      }
      importView.InChangedStudentAnnRecordCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentV3Initials() {
      return FixedStringAttr.valueOf(importView.InWsStudentV3Initials, 20);
   }
   public void setInWsStudentV3Initials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InWsStudentV3Initials must be <= 20 characters.",
               new PropertyChangeEvent (this, "InWsStudentV3Initials", null, null));
      }
      importView.InWsStudentV3Initials = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CellNumber, 20);
   }
   public void setInWsAddressV2CellNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 20) {
         throw new PropertyVetoException("InWsAddressV2CellNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CellNumber", null, null));
      }
      importView.InWsAddressV2CellNumber = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2EmailAddress, 60);
   }
   public void setInWsAddressV2EmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InWsAddressV2EmailAddress must be <= 60 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2EmailAddress", null, null));
      }
      importView.InWsAddressV2EmailAddress = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public String getInWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CourierContactNo, 40);
   }
   public void setInWsAddressV2CourierContactNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InWsAddressV2CourierContactNo must be <= 40 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CourierContactNo", null, null));
      }
      importView.InWsAddressV2CourierContactNo = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getInPOBoxCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InPOBoxCsfStringsString1, 1);
   }
   public void setInPOBoxCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InPOBoxCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPOBoxCsfStringsString1", null, null));
      }
      importView.InPOBoxCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInOverrideWsUserNumber() {
      return importView.InOverrideWsUserNumber;
   }
   public void setInOverrideWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InOverrideWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InOverrideWsUserNumber", null, null));
      }
      importView.InOverrideWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInOverrideWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOverrideWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOverrideWsUserNumber", null, null));
      }
      try {
          setInOverrideWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOverrideWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOverrideWsUserNumber", null, null));
      }
   }
 
   public String getInOverrideWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InOverrideWsUserPassword, 12);
   }
   public void setInOverrideWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InOverrideWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InOverrideWsUserPassword", null, null));
      }
      importView.InOverrideWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public int getInSolWsStudentNr() {
      return importView.InSolWsStudentNr;
   }
   public void setInSolWsStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InSolWsStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InSolWsStudentNr", null, null));
      }
      importView.InSolWsStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSolWsStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSolWsStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSolWsStudentNr", null, null));
      }
      try {
          setInSolWsStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSolWsStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSolWsStudentNr", null, null));
      }
   }
 
   public String getInPrisonCsfStringsString2() {
      return FixedStringAttr.valueOf(importView.InPrisonCsfStringsString2, 2);
   }
   public void setInPrisonCsfStringsString2(String s)
      throws PropertyVetoException {
      if (s.length() > 2) {
         throw new PropertyVetoException("InPrisonCsfStringsString2 must be <= 2 characters.",
               new PropertyChangeEvent (this, "InPrisonCsfStringsString2", null, null));
      }
      importView.InPrisonCsfStringsString2 = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInPhysicalWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressAddressLine1, 28);
   }
   public void setInPhysicalWsAddressAddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressAddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressAddressLine1", null, null));
      }
      importView.InPhysicalWsAddressAddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressAddressLine2, 28);
   }
   public void setInPhysicalWsAddressAddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressAddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressAddressLine2", null, null));
      }
      importView.InPhysicalWsAddressAddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressAddressLine3, 28);
   }
   public void setInPhysicalWsAddressAddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressAddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressAddressLine3", null, null));
      }
      importView.InPhysicalWsAddressAddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressAddressLine4, 28);
   }
   public void setInPhysicalWsAddressAddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressAddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressAddressLine4", null, null));
      }
      importView.InPhysicalWsAddressAddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressAddressLine5, 28);
   }
   public void setInPhysicalWsAddressAddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressAddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressAddressLine5", null, null));
      }
      importView.InPhysicalWsAddressAddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressAddressLine6, 28);
   }
   public void setInPhysicalWsAddressAddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressAddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressAddressLine6", null, null));
      }
      importView.InPhysicalWsAddressAddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInPhysicalWsAddressPostalCode() {
      return importView.InPhysicalWsAddressPostalCode;
   }
   public void setInPhysicalWsAddressPostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressPostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressPostalCode", null, null));
      }
      importView.InPhysicalWsAddressPostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressPostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressPostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressPostalCode", null, null));
      }
      try {
          setInPhysicalWsAddressPostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressPostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressPostalCode", null, null));
      }
   }
 
   public int getInPhysicalWsAddressReferenceNo() {
      return importView.InPhysicalWsAddressReferenceNo;
   }
   public void setInPhysicalWsAddressReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressReferenceNo", null, null));
      }
      importView.InPhysicalWsAddressReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressReferenceNo", null, null));
      }
      try {
          setInPhysicalWsAddressReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressReferenceNo", null, null));
      }
   }
 
   public short getInPhysicalWsAddressType() {
      return importView.InPhysicalWsAddressType;
   }
   public void setInPhysicalWsAddressType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InPhysicalWsAddressType has more than 2 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressType", null, null));
      }
      importView.InPhysicalWsAddressType = ShortAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressType", null, null));
      }
      try {
          setInPhysicalWsAddressType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressType", null, null));
      }
   }
 
   public short getInPhysicalWsAddressCategory() {
      return importView.InPhysicalWsAddressCategory;
   }
   public void setInPhysicalWsAddressCategory(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressCategory has more than 3 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressCategory", null, null));
      }
      importView.InPhysicalWsAddressCategory = ShortAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressCategory(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressCategory", null, null));
      }
      try {
          setInPhysicalWsAddressCategory(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressCategory", null, null));
      }
   }
 
   public String getInCourierWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressAddressLine1, 28);
   }
   public void setInCourierWsAddressAddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressAddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressAddressLine1", null, null));
      }
      importView.InCourierWsAddressAddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressAddressLine2, 28);
   }
   public void setInCourierWsAddressAddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressAddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressAddressLine2", null, null));
      }
      importView.InCourierWsAddressAddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressAddressLine3, 28);
   }
   public void setInCourierWsAddressAddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressAddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressAddressLine3", null, null));
      }
      importView.InCourierWsAddressAddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressAddressLine4, 28);
   }
   public void setInCourierWsAddressAddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressAddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressAddressLine4", null, null));
      }
      importView.InCourierWsAddressAddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressAddressLine5, 28);
   }
   public void setInCourierWsAddressAddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressAddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressAddressLine5", null, null));
      }
      importView.InCourierWsAddressAddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressAddressLine6, 28);
   }
   public void setInCourierWsAddressAddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressAddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressAddressLine6", null, null));
      }
      importView.InCourierWsAddressAddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInCourierWsAddressPostalCode() {
      return importView.InCourierWsAddressPostalCode;
   }
   public void setInCourierWsAddressPostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCourierWsAddressPostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressPostalCode", null, null));
      }
      importView.InCourierWsAddressPostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressPostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressPostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressPostalCode", null, null));
      }
      try {
          setInCourierWsAddressPostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressPostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressPostalCode", null, null));
      }
   }
 
   public int getInCourierWsAddressReferenceNo() {
      return importView.InCourierWsAddressReferenceNo;
   }
   public void setInCourierWsAddressReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InCourierWsAddressReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressReferenceNo", null, null));
      }
      importView.InCourierWsAddressReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressReferenceNo", null, null));
      }
      try {
          setInCourierWsAddressReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressReferenceNo", null, null));
      }
   }
 
   public short getInCourierWsAddressType() {
      return importView.InCourierWsAddressType;
   }
   public void setInCourierWsAddressType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InCourierWsAddressType has more than 2 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressType", null, null));
      }
      importView.InCourierWsAddressType = ShortAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressType", null, null));
      }
      try {
          setInCourierWsAddressType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressType", null, null));
      }
   }
 
   public short getInCourierWsAddressCategory() {
      return importView.InCourierWsAddressCategory;
   }
   public void setInCourierWsAddressCategory(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InCourierWsAddressCategory has more than 3 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressCategory", null, null));
      }
      importView.InCourierWsAddressCategory = ShortAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressCategory(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressCategory", null, null));
      }
      try {
          setInCourierWsAddressCategory(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressCategory", null, null));
      }
   }
 
   public String getInPhysicalPOBoxCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InPhysicalPOBoxCsfStringsString1, 1);
   }
   public void setInPhysicalPOBoxCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InPhysicalPOBoxCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPhysicalPOBoxCsfStringsString1", null, null));
      }
      importView.InPhysicalPOBoxCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInChangedPhysicalAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InChangedPhysicalAddressCsfStringsString1, 1);
   }
   public void setInChangedPhysicalAddressCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InChangedPhysicalAddressCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InChangedPhysicalAddressCsfStringsString1", null, null));
      }
      importView.InChangedPhysicalAddressCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCheckBoxIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InCheckBoxIefSuppliedFlag, 1);
   }
   public void setInCheckBoxIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailIefSuppliedFlagPropertyEditor pe = new Srrsa01sRegStudentPersDetailIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InCheckBoxIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InCheckBoxIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCheckBoxIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCheckBoxIefSuppliedFlag", null, null));
      }
      importView.InCheckBoxIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInChangedCourierAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InChangedCourierAddressCsfStringsString1, 1);
   }
   public void setInChangedCourierAddressCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InChangedCourierAddressCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InChangedCourierAddressCsfStringsString1", null, null));
      }
      importView.InChangedCourierAddressCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsStudentReferenceMkStudentNr() {
      return importView.InWsStudentReferenceMkStudentNr;
   }
   public void setInWsStudentReferenceMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStudentReferenceMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStudentReferenceMkStudentNr", null, null));
      }
      importView.InWsStudentReferenceMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentReferenceMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentReferenceMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentReferenceMkStudentNr", null, null));
      }
      try {
          setInWsStudentReferenceMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentReferenceMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentReferenceMkStudentNr", null, null));
      }
   }
 
   public double getInWsStudentReferenceOldStudentNr() {
      return importView.InWsStudentReferenceOldStudentNr;
   }
   public void setInWsStudentReferenceOldStudentNr(double s)
      throws PropertyVetoException {
      int decimals = 0;
      boolean decimal_found = false;
      String tempDataStr = decimalFormatter.format(s);
      for (int i=tempDataStr.length(); i>0; i--) {
         if (tempDataStr.charAt(i-1) == '.') {
            decimal_found = true;
            break;
         }
         decimals++;
      }
      if (decimal_found == true && decimals > 0) {
         throw new PropertyVetoException("InWsStudentReferenceOldStudentNr has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InWsStudentReferenceOldStudentNr", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000000000.0) {
         throw new PropertyVetoException("InWsStudentReferenceOldStudentNr has more than 15 integral digits.",
               new PropertyChangeEvent (this, "InWsStudentReferenceOldStudentNr", null, null));
      }
      importView.InWsStudentReferenceOldStudentNr = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsStudentReferenceOldStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentReferenceOldStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentReferenceOldStudentNr", null, null));
      }
      try {
          setInWsStudentReferenceOldStudentNr(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentReferenceOldStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentReferenceOldStudentNr", null, null));
      }
   }
 
   public short getInWsStudentReferenceOldInstitution() {
      return importView.InWsStudentReferenceOldInstitution;
   }
   public void setInWsStudentReferenceOldInstitution(short s)
      throws PropertyVetoException {
      Srrsa01sRegStudentPersDetailWsStudentReferenceOldInstitutionPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentReferenceOldInstitutionPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentReferenceOldInstitution value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentReferenceOldInstitution", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentReferenceOldInstitution has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentReferenceOldInstitution", null, null));
      }
      importView.InWsStudentReferenceOldInstitution = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentReferenceOldInstitution(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentReferenceOldInstitution is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentReferenceOldInstitution", null, null));
      }
      try {
          setInWsStudentReferenceOldInstitution(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentReferenceOldInstitution is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentReferenceOldInstitution", null, null));
      }
   }
 
   public String getInAuditUserCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InAuditUserCsfStringsString1, 1);
   }
   public void setInAuditUserCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InAuditUserCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAuditUserCsfStringsString1", null, null));
      }
      importView.InAuditUserCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInQualificationSpecialityTypeEnglishDescription() {
      return FixedStringAttr.valueOf(importView.InQualificationSpecialityTypeEnglishDescription, 132);
   }
   public void setInQualificationSpecialityTypeEnglishDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InQualificationSpecialityTypeEnglishDescription must be <= 132 characters.",
               new PropertyChangeEvent (this, "InQualificationSpecialityTypeEnglishDescription", null, null));
      }
      importView.InQualificationSpecialityTypeEnglishDescription = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public int getInWsStudentApplicationApplicationNr() {
      return importView.InWsStudentApplicationApplicationNr;
   }
   public void setInWsStudentApplicationApplicationNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStudentApplicationApplicationNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStudentApplicationApplicationNr", null, null));
      }
      importView.InWsStudentApplicationApplicationNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationApplicationNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentApplicationApplicationNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationApplicationNr", null, null));
      }
      try {
          setInWsStudentApplicationApplicationNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentApplicationApplicationNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationApplicationNr", null, null));
      }
   }
 
   public Calendar getInWsStudentApplicationApplicationDate() {
      return DateAttr.toCalendar(importView.InWsStudentApplicationApplicationDate);
   }
   public int getAsIntInWsStudentApplicationApplicationDate() {
      return DateAttr.toInt(importView.InWsStudentApplicationApplicationDate);
   }
   public void setInWsStudentApplicationApplicationDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsStudentApplicationApplicationDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationApplicationDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsStudentApplicationApplicationDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsStudentApplicationApplicationDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsStudentApplicationApplicationDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsStudentApplicationApplicationDate", null, null));
         }
      }
   }
   public void setAsIntInWsStudentApplicationApplicationDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsStudentApplicationApplicationDate(temp);
   }
 
   public String getInWsStudentApplicationFinancialAidEduloan() {
      return StringAttr.valueOf(importView.InWsStudentApplicationFinancialAidEduloan);
   }
   public void setInWsStudentApplicationFinancialAidEduloan(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationFinancialAidEduloan must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationFinancialAidEduloan", null, null));
      }
      importView.InWsStudentApplicationFinancialAidEduloan = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationFinancialAidNsfas() {
      return StringAttr.valueOf(importView.InWsStudentApplicationFinancialAidNsfas);
   }
   public void setInWsStudentApplicationFinancialAidNsfas(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationFinancialAidNsfas must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationFinancialAidNsfas", null, null));
      }
      importView.InWsStudentApplicationFinancialAidNsfas = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationCareerCounselling() {
      return StringAttr.valueOf(importView.InWsStudentApplicationCareerCounselling);
   }
   public void setInWsStudentApplicationCareerCounselling(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationCareerCounselling must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationCareerCounselling", null, null));
      }
      importView.InWsStudentApplicationCareerCounselling = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationDocSuppliedForm() {
      return StringAttr.valueOf(importView.InWsStudentApplicationDocSuppliedForm);
   }
   public void setInWsStudentApplicationDocSuppliedForm(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationDocSuppliedForm must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationDocSuppliedForm", null, null));
      }
      importView.InWsStudentApplicationDocSuppliedForm = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationDocSuppliedSchool() {
      return StringAttr.valueOf(importView.InWsStudentApplicationDocSuppliedSchool);
   }
   public void setInWsStudentApplicationDocSuppliedSchool(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationDocSuppliedSchool must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationDocSuppliedSchool", null, null));
      }
      importView.InWsStudentApplicationDocSuppliedSchool = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationDocSuppliedAcadrec() {
      return StringAttr.valueOf(importView.InWsStudentApplicationDocSuppliedAcadrec);
   }
   public void setInWsStudentApplicationDocSuppliedAcadrec(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationDocSuppliedAcadrec must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationDocSuppliedAcadrec", null, null));
      }
      importView.InWsStudentApplicationDocSuppliedAcadrec = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationDocSuppliedId() {
      return StringAttr.valueOf(importView.InWsStudentApplicationDocSuppliedId);
   }
   public void setInWsStudentApplicationDocSuppliedId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationDocSuppliedId must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationDocSuppliedId", null, null));
      }
      importView.InWsStudentApplicationDocSuppliedId = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationDocSuppliedMarriage() {
      return StringAttr.valueOf(importView.InWsStudentApplicationDocSuppliedMarriage);
   }
   public void setInWsStudentApplicationDocSuppliedMarriage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationDocSuppliedMarriage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationDocSuppliedMarriage", null, null));
      }
      importView.InWsStudentApplicationDocSuppliedMarriage = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationFeePaid() {
      return StringAttr.valueOf(importView.InWsStudentApplicationFeePaid);
   }
   public void setInWsStudentApplicationFeePaid(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationFeePaid must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationFeePaid", null, null));
      }
      importView.InWsStudentApplicationFeePaid = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationLateApplication() {
      return StringAttr.valueOf(importView.InWsStudentApplicationLateApplication);
   }
   public void setInWsStudentApplicationLateApplication(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationLateApplication must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationLateApplication", null, null));
      }
      importView.InWsStudentApplicationLateApplication = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationMatricCertificate() {
      return StringAttr.valueOf(importView.InWsStudentApplicationMatricCertificate);
   }
   public void setInWsStudentApplicationMatricCertificate(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailWsStudentApplicationMatricCertificatePropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentApplicationMatricCertificatePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentApplicationMatricCertificate value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentApplicationMatricCertificate", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentApplicationMatricCertificate must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationMatricCertificate", null, null));
      }
      importView.InWsStudentApplicationMatricCertificate = StringAttr.valueOf(s, (short)2);
   }
 
   public String getInWsStudentApplicationHeAdmission() {
      return StringAttr.valueOf(importView.InWsStudentApplicationHeAdmission);
   }
   public void setInWsStudentApplicationHeAdmission(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailWsStudentApplicationHeAdmissionPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentApplicationHeAdmissionPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentApplicationHeAdmission value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentApplicationHeAdmission", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationHeAdmission must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationHeAdmission", null, null));
      }
      importView.InWsStudentApplicationHeAdmission = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationMatricExamNr() {
      return StringAttr.valueOf(importView.InWsStudentApplicationMatricExamNr);
   }
   public void setInWsStudentApplicationMatricExamNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 13) {
         throw new PropertyVetoException("InWsStudentApplicationMatricExamNr must be <= 13 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationMatricExamNr", null, null));
      }
      importView.InWsStudentApplicationMatricExamNr = StringAttr.valueOf(s, (short)13);
   }
 
   public short getInWsStudentApplicationMatricYear() {
      return importView.InWsStudentApplicationMatricYear;
   }
   public void setInWsStudentApplicationMatricYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudentApplicationMatricYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudentApplicationMatricYear", null, null));
      }
      importView.InWsStudentApplicationMatricYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationMatricYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentApplicationMatricYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationMatricYear", null, null));
      }
      try {
          setInWsStudentApplicationMatricYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentApplicationMatricYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationMatricYear", null, null));
      }
   }
 
   public short getInWsStudentApplicationMatricProvince() {
      return importView.InWsStudentApplicationMatricProvince;
   }
   public void setInWsStudentApplicationMatricProvince(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentApplicationMatricProvince has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentApplicationMatricProvince", null, null));
      }
      importView.InWsStudentApplicationMatricProvince = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationMatricProvince(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentApplicationMatricProvince is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationMatricProvince", null, null));
      }
      try {
          setInWsStudentApplicationMatricProvince(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentApplicationMatricProvince is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationMatricProvince", null, null));
      }
   }
 
   public String getInWsStudentApplicationApplyExemptions() {
      return StringAttr.valueOf(importView.InWsStudentApplicationApplyExemptions);
   }
   public void setInWsStudentApplicationApplyExemptions(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationApplyExemptions must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationApplyExemptions", null, null));
      }
      importView.InWsStudentApplicationApplyExemptions = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationPrevInstSudnr() {
      return StringAttr.valueOf(importView.InWsStudentApplicationPrevInstSudnr);
   }
   public void setInWsStudentApplicationPrevInstSudnr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStudentApplicationPrevInstSudnr must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationPrevInstSudnr", null, null));
      }
      importView.InWsStudentApplicationPrevInstSudnr = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStudentApplicationMatricAutoVerified() {
      return StringAttr.valueOf(importView.InWsStudentApplicationMatricAutoVerified);
   }
   public void setInWsStudentApplicationMatricAutoVerified(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationMatricAutoVerified must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationMatricAutoVerified", null, null));
      }
      importView.InWsStudentApplicationMatricAutoVerified = StringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationApplicationMethod() {
      return FixedStringAttr.valueOf(importView.InWsStudentApplicationApplicationMethod, 1);
   }
   public void setInWsStudentApplicationApplicationMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrsa01sRegStudentPersDetailWsStudentApplicationApplicationMethodPropertyEditor pe = new Srrsa01sRegStudentPersDetailWsStudentApplicationApplicationMethodPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentApplicationApplicationMethod value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentApplicationApplicationMethod", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationApplicationMethod must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationApplicationMethod", null, null));
      }
      importView.InWsStudentApplicationApplicationMethod = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationCaoPaidFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentApplicationCaoPaidFlag, 1);
   }
   public void setInWsStudentApplicationCaoPaidFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationCaoPaidFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationCaoPaidFlag", null, null));
      }
      importView.InWsStudentApplicationCaoPaidFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentApplicationRegFromYear() {
      return importView.InWsStudentApplicationRegFromYear;
   }
   public void setInWsStudentApplicationRegFromYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudentApplicationRegFromYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudentApplicationRegFromYear", null, null));
      }
      importView.InWsStudentApplicationRegFromYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationRegFromYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentApplicationRegFromYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationRegFromYear", null, null));
      }
      try {
          setInWsStudentApplicationRegFromYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentApplicationRegFromYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationRegFromYear", null, null));
      }
   }
 
   public short getInWsStudentApplicationRegFromPeriod() {
      return importView.InWsStudentApplicationRegFromPeriod;
   }
   public void setInWsStudentApplicationRegFromPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentApplicationRegFromPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentApplicationRegFromPeriod", null, null));
      }
      importView.InWsStudentApplicationRegFromPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationRegFromPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentApplicationRegFromPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationRegFromPeriod", null, null));
      }
      try {
          setInWsStudentApplicationRegFromPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentApplicationRegFromPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationRegFromPeriod", null, null));
      }
   }
 
   public String getInWsStudentApplicationDocSuppliedToefl() {
      return FixedStringAttr.valueOf(importView.InWsStudentApplicationDocSuppliedToefl, 1);
   }
   public void setInWsStudentApplicationDocSuppliedToefl(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationDocSuppliedToefl must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationDocSuppliedToefl", null, null));
      }
      importView.InWsStudentApplicationDocSuppliedToefl = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentApplicationInternetApplicati() {
      return FixedStringAttr.valueOf(importView.InWsStudentApplicationInternetApplicati, 1);
   }
   public void setInWsStudentApplicationInternetApplicati(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentApplicationInternetApplicati must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationInternetApplicati", null, null));
      }
      importView.InWsStudentApplicationInternetApplicati = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentApplicationApplicationPeriod() {
      return importView.InWsStudentApplicationApplicationPeriod;
   }
   public void setInWsStudentApplicationApplicationPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentApplicationApplicationPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentApplicationApplicationPeriod", null, null));
      }
      importView.InWsStudentApplicationApplicationPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentApplicationApplicationPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentApplicationApplicationPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationApplicationPeriod", null, null));
      }
      try {
          setInWsStudentApplicationApplicationPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentApplicationApplicationPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentApplicationApplicationPeriod", null, null));
      }
   }
 
   public String getInShortUserCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InShortUserCsfStringsString1, 1);
   }
   public void setInShortUserCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InShortUserCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InShortUserCsfStringsString1", null, null));
      }
      importView.InShortUserCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsRegionalOfficeEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsRegionalOfficeEngDescription, 28);
   }
   public void setInWsRegionalOfficeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsRegionalOfficeEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsRegionalOfficeEngDescription", null, null));
      }
      importView.InWsRegionalOfficeEngDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInActivityWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(importView.InActivityWsGenericCodeEngDescription, 40);
   }
   public void setInActivityWsGenericCodeEngDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InActivityWsGenericCodeEngDescription must be <= 40 characters.",
               new PropertyChangeEvent (this, "InActivityWsGenericCodeEngDescription", null, null));
      }
      importView.InActivityWsGenericCodeEngDescription = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getOutOverrideYearIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutOverrideYearIefSuppliedFlag, 1);
   }
 
   public String getOutEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutEmailOrFaxCsfStringsString1, 1);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNoCsfStringsString132, 132);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutWsPrinterCode, 10);
   }
 
   public String getOutForeignCountryNameWsCountryEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutForeignCountryNameWsCountryEngDescription, 28);
   }
 
   public String getOutNationalityWsCountryInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutNationalityWsCountryInUseFlag, 1);
   }
 
   public String getOutNationalityWsCountryEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutNationalityWsCountryEngDescription, 28);
   }
 
   public String getOutNationalityWsCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutNationalityWsCountryCode, 4);
   }
 
   public String getOutOverrideUserCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutOverrideUserCsfStringsString1, 1);
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
 
   public Calendar getOutCsfClientServerCommunicationsClientDate() {
      return DateAttr.toCalendar(exportView.OutCsfClientServerCommunicationsClientDate);
   }
   public int getAsIntOutCsfClientServerCommunicationsClientDate() {
      return DateAttr.toInt(exportView.OutCsfClientServerCommunicationsClientDate);
   }
 
   public Calendar getOutCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toCalendar(exportView.OutCsfClientServerCommunicationsClientTime);
   }
   public int getAsIntOutCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toInt(exportView.OutCsfClientServerCommunicationsClientTime);
   }
 
   public Calendar getOutCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutCsfClientServerCommunicationsClientTimestamp);
   }
   public String getAsStringOutCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toString(exportView.OutCsfClientServerCommunicationsClientTimestamp);
   }
 
   public String getOutCsfClientServerCommunicationsClientTransactionCode() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientTransactionCode, 8);
   }
 
   public String getOutCsfClientServerCommunicationsClientUserId() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientUserId, 8);
   }
 
   public String getOutCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsFirstpassFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsFirstpassFlag, 1);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
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
 
   public String getOutStudentAnnualRecordTefsaApplicationFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordTefsaApplicationFlag, 1);
   }
 
   public String getOutStudentAnnualRecordMatriculationBoardFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordMatriculationBoardFlag, 1);
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
 
   public String getOutStudentAnnualRecordVotersRollFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordVotersRollFlag, 1);
   }
 
   public String getOutStudentAnnualRecordAvailableForSrcFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordAvailableForSrcFlag, 1);
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
 
   public short getOutStudentAnnualRecordTefsaIndicator() {
      return exportView.OutStudentAnnualRecordTefsaIndicator;
   }
 
   public String getOutStudentAnnualRecordBooksellersPermissionFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordBooksellersPermissionFlag, 1);
   }
 
   public String getOutStudentAnnualRecordPipelineStudent() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordPipelineStudent, 5);
   }
 
   public String getOutStudentAnnualRecordOverrideFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordOverrideFlag, 1);
   }
 
   public String getOutStudentAnnualRecordSolUserFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordSolUserFlag, 1);
   }
 
   public short getOutStudentAnnualRecordMkContactCentre() {
      return exportView.OutStudentAnnualRecordMkContactCentre;
   }
 
   public String getOutStudentAnnualRecordSmDeliveryMethod() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordSmDeliveryMethod, 1);
   }
 
   public String getOutStudentAnnualRecordCommunicationMethod() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordCommunicationMethod, 1);
   }
 
   public String getOutStudentAnnualRecordActivityLastYear() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordActivityLastYear, 2);
   }
 
   public String getOutStudentAnnualRecordJobRelatedToStudy() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordJobRelatedToStudy, 1);
   }
 
   public String getOutStudentAnnualRecordRegDeliveryMethod() {
      return FixedStringAttr.valueOf(exportView.OutStudentAnnualRecordRegDeliveryMethod, 1);
   }
 
   public String getOutStudentAnnualRecordTutorialMethod() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordTutorialMethod);
   }
 
   public String getOutStudentAnnualRecordSpecialityCode() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordSpecialityCode);
   }
 
   public String getOutStudentAnnualRecordCurrentStaff() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordCurrentStaff);
   }
 
   public String getOutStudentAnnualRecordDependantStaff() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordDependantStaff);
   }
 
   public String getOutStudentAnnualRecordQualCompleting() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordQualCompleting);
   }
 
   public String getOutStudentAnnualRecordQualExplain() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordQualExplain);
   }
 
   public String getOutStudentAnnualRecordPrisoner() {
      return StringAttr.valueOf(exportView.OutStudentAnnualRecordPrisoner);
   }
 
   public String getOutWsAddressAuditFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAuditFlag, 1);
   }
 
   public String getOutWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine1, 28);
   }
 
   public String getOutWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine2, 28);
   }
 
   public String getOutWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine3, 28);
   }
 
   public String getOutWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine4, 28);
   }
 
   public String getOutWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine5, 28);
   }
 
   public String getOutWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressLine6, 28);
   }
 
   public short getOutWsAddressPostalCode() {
      return exportView.OutWsAddressPostalCode;
   }
 
   public int getOutWsAddressReferenceNo() {
      return exportView.OutWsAddressReferenceNo;
   }
 
   public String getOutWsAddressAddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressAddressReferenceFlag, 1);
   }
 
   public short getOutWsAddressType() {
      return exportView.OutWsAddressType;
   }
 
   public String getOutWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressFaxNumber, 28);
   }
 
   public String getOutWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressEmailAddress, 28);
   }
 
   public Calendar getOutWsAddressAddressTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutWsAddressAddressTimestamp);
   }
   public String getAsStringOutWsAddressAddressTimestamp() {
      return TimestampAttr.toString(exportView.OutWsAddressAddressTimestamp);
   }
 
   public Calendar getOutWsAddressContactTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutWsAddressContactTimestamp);
   }
   public String getAsStringOutWsAddressContactTimestamp() {
      return TimestampAttr.toString(exportView.OutWsAddressContactTimestamp);
   }
 
   public short getOutWsAddressCategory() {
      return exportView.OutWsAddressCategory;
   }
 
   public String getOutWsAddressCategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressCategoryDescription, 28);
   }
 
   public String getOutWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressHomeNumber, 28);
   }
 
   public String getOutWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressWorkNumber, 28);
   }
 
   public String getOutWsCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsCountryCode, 4);
   }
 
   public String getOutWsCountryEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsCountryEngDescription, 28);
   }
 
   public String getOutCorrLanguageWsLanguageEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutCorrLanguageWsLanguageEngDescription, 28);
   }
 
   public String getOutCorrLanguageWsLanguageCorrespondenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutCorrLanguageWsLanguageCorrespondenceFlag, 1);
   }
 
   public short getOutWsDisabilityTypeCode() {
      return exportView.OutWsDisabilityTypeCode;
   }
 
   public String getOutWsDisabilityTypeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsDisabilityTypeEngDescription, 28);
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationEngDescription, 28);
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
 
   public String getOutWsStudentPreviouslyPostGraduateFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviouslyPostGraduateFlag, 1);
   }
 
   public String getOutWsStudentPreviouslyUnisaPostGradFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentPreviouslyUnisaPostGradFlag, 1);
   }
 
   public String getOutWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCountryCode, 4);
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
 
   public String getOutWsStudentResultBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentResultBlockFlag, 1);
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public short getOutWsStudentMkLastAcademicPeriod() {
      return exportView.OutWsStudentMkLastAcademicPeriod;
   }
 
   public String getOutWsStudentPassportNo() {
      return StringAttr.valueOf(exportView.OutWsStudentPassportNo);
   }
 
   public String getOutWsStudentIdBlock() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentIdBlock, 1);
   }
 
   public String getOutWsStudentCaoAdmissionChecked() {
      return StringAttr.valueOf(exportView.OutWsStudentCaoAdmissionChecked);
   }
 
   public short getOutWsStudentNsfasContractBlock() {
      return exportView.OutWsStudentNsfasContractBlock;
   }
 
   public String getOutWsStudentApplicationStatus() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationStatus);
   }
 
   public double getOutWsStudentMtrSchool() {
      return exportView.OutWsStudentMtrSchool;
   }
 
   public String getOutWsEconomicSectorEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsEconomicSectorEngDescription, 28);
   }
 
   public String getOutWsEthnicGroupCode() {
      return FixedStringAttr.valueOf(exportView.OutWsEthnicGroupCode, 4);
   }
 
   public String getOutWsEthnicGroupEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsEthnicGroupEngDescription, 28);
   }
 
   public String getOutWsExamCentreCode() {
      return FixedStringAttr.valueOf(exportView.OutWsExamCentreCode, 5);
   }
 
   public String getOutWsExamCentreInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsExamCentreInUseFlag, 1);
   }
 
   public String getOutWsExamCentreEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsExamCentreEngDescription, 28);
   }
 
   public String getOutHomeLanguageWsLanguageEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutHomeLanguageWsLanguageEngDescription, 28);
   }
 
   public String getOutWsMatricCertificateCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricCertificateCode, 3);
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
 
   public String getOutWsMatricRecordSymbol() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordSymbol, 3);
   }
 
   public String getOutWsMatricRecordAuditedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricRecordAuditedFlag, 1);
   }
 
   public String getOutWsMatricStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMatricStatusCode, 4);
   }
 
   public String getOutWsOccupationEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsOccupationEngDescription, 28);
   }
 
   public String getOutStudentExamCentreMkExamCentreCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentExamCentreMkExamCentreCode, 5);
   }
 
   public String getOutPrevWsEducationalInstitutionEngName() {
      return FixedStringAttr.valueOf(exportView.OutPrevWsEducationalInstitutionEngName, 28);
   }
 
   public String getOutOtherWsEducationalInstitutionEngName() {
      return FixedStringAttr.valueOf(exportView.OutOtherWsEducationalInstitutionEngName, 28);
   }
 
   public String getOutAddressControlCodeCsfStringsString28() {
      return FixedStringAttr.valueOf(exportView.OutAddressControlCodeCsfStringsString28, 28);
   }
 
   public String getOutSblStudentCreatedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSblStudentCreatedIefSuppliedFlag, 1);
   }
 
   public String getOutChangedAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutChangedAddressCsfStringsString1, 1);
   }
 
   public String getOutChangedContactNumberCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutChangedContactNumberCsfStringsString1, 1);
   }
 
   public String getOutChangedStudentCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutChangedStudentCsfStringsString1, 1);
   }
 
   public String getOutChangedStudentAnnRecordCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutChangedStudentAnnRecordCsfStringsString1, 1);
   }
 
   public String getOutWsStudentV3Initials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentV3Initials, 20);
   }
 
   public String getOutWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CellNumber, 20);
   }
 
   public String getOutWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2EmailAddress, 60);
   }
 
   public String getOutWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CourierContactNo, 40);
   }
 
   public String getOutPOBoxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPOBoxCsfStringsString1, 1);
   }
 
   public int getOutOverrideWsUserNumber() {
      return exportView.OutOverrideWsUserNumber;
   }
 
   public String getOutOverrideWsUserPassword() {
      return FixedStringAttr.valueOf(exportView.OutOverrideWsUserPassword, 12);
   }
 
   public int getOutSolWsStudentNr() {
      return exportView.OutSolWsStudentNr;
   }
 
   public String getOutPrisonCsfStringsString2() {
      return FixedStringAttr.valueOf(exportView.OutPrisonCsfStringsString2, 2);
   }
 
   public String getOutPhysicalWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressAddressLine1, 28);
   }
 
   public String getOutPhysicalWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressAddressLine2, 28);
   }
 
   public String getOutPhysicalWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressAddressLine3, 28);
   }
 
   public String getOutPhysicalWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressAddressLine4, 28);
   }
 
   public String getOutPhysicalWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressAddressLine5, 28);
   }
 
   public String getOutPhysicalWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressAddressLine6, 28);
   }
 
   public short getOutPhysicalWsAddressPostalCode() {
      return exportView.OutPhysicalWsAddressPostalCode;
   }
 
   public int getOutPhysicalWsAddressReferenceNo() {
      return exportView.OutPhysicalWsAddressReferenceNo;
   }
 
   public short getOutPhysicalWsAddressType() {
      return exportView.OutPhysicalWsAddressType;
   }
 
   public short getOutPhysicalWsAddressCategory() {
      return exportView.OutPhysicalWsAddressCategory;
   }
 
   public String getOutCourierWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressAddressLine1, 28);
   }
 
   public String getOutCourierWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressAddressLine2, 28);
   }
 
   public String getOutCourierWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressAddressLine3, 28);
   }
 
   public String getOutCourierWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressAddressLine4, 28);
   }
 
   public String getOutCourierWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressAddressLine5, 28);
   }
 
   public String getOutCourierWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressAddressLine6, 28);
   }
 
   public short getOutCourierWsAddressPostalCode() {
      return exportView.OutCourierWsAddressPostalCode;
   }
 
   public int getOutCourierWsAddressReferenceNo() {
      return exportView.OutCourierWsAddressReferenceNo;
   }
 
   public short getOutCourierWsAddressType() {
      return exportView.OutCourierWsAddressType;
   }
 
   public short getOutCourierWsAddressCategory() {
      return exportView.OutCourierWsAddressCategory;
   }
 
   public String getOutPhysicalPOBoxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalPOBoxCsfStringsString1, 1);
   }
 
   public String getOutChangedPhysicalAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutChangedPhysicalAddressCsfStringsString1, 1);
   }
 
   public String getOutCheckBoxIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCheckBoxIefSuppliedFlag, 1);
   }
 
   public String getOutChangedCourierAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutChangedCourierAddressCsfStringsString1, 1);
   }
 
   public int getOutWsStudentReferenceMkStudentNr() {
      return exportView.OutWsStudentReferenceMkStudentNr;
   }
 
   public double getOutWsStudentReferenceOldStudentNr() {
      return exportView.OutWsStudentReferenceOldStudentNr;
   }
 
   public short getOutWsStudentReferenceOldInstitution() {
      return exportView.OutWsStudentReferenceOldInstitution;
   }
 
   public String getOutAuditUserCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutAuditUserCsfStringsString1, 1);
   }
 
   public String getOutQualificationSpecialityTypeEnglishDescription() {
      return FixedStringAttr.valueOf(exportView.OutQualificationSpecialityTypeEnglishDescription, 132);
   }
 
   public int getOutWsStudentApplicationApplicationNr() {
      return exportView.OutWsStudentApplicationApplicationNr;
   }
 
   public Calendar getOutWsStudentApplicationApplicationDate() {
      return DateAttr.toCalendar(exportView.OutWsStudentApplicationApplicationDate);
   }
   public int getAsIntOutWsStudentApplicationApplicationDate() {
      return DateAttr.toInt(exportView.OutWsStudentApplicationApplicationDate);
   }
 
   public String getOutWsStudentApplicationFinancialAidEduloan() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationFinancialAidEduloan);
   }
 
   public String getOutWsStudentApplicationFinancialAidNsfas() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationFinancialAidNsfas);
   }
 
   public String getOutWsStudentApplicationCareerCounselling() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationCareerCounselling);
   }
 
   public String getOutWsStudentApplicationDocSuppliedForm() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationDocSuppliedForm);
   }
 
   public String getOutWsStudentApplicationDocSuppliedSchool() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationDocSuppliedSchool);
   }
 
   public String getOutWsStudentApplicationDocSuppliedAcadrec() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationDocSuppliedAcadrec);
   }
 
   public String getOutWsStudentApplicationDocSuppliedId() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationDocSuppliedId);
   }
 
   public String getOutWsStudentApplicationDocSuppliedMarriage() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationDocSuppliedMarriage);
   }
 
   public String getOutWsStudentApplicationFeePaid() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationFeePaid);
   }
 
   public String getOutWsStudentApplicationLateApplication() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationLateApplication);
   }
 
   public String getOutWsStudentApplicationMatricCertificate() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationMatricCertificate);
   }
 
   public String getOutWsStudentApplicationHeAdmission() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationHeAdmission);
   }
 
   public String getOutWsStudentApplicationMatricExamNr() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationMatricExamNr);
   }
 
   public short getOutWsStudentApplicationMatricYear() {
      return exportView.OutWsStudentApplicationMatricYear;
   }
 
   public short getOutWsStudentApplicationMatricProvince() {
      return exportView.OutWsStudentApplicationMatricProvince;
   }
 
   public String getOutWsStudentApplicationApplyExemptions() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationApplyExemptions);
   }
 
   public String getOutWsStudentApplicationPrevInstSudnr() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationPrevInstSudnr);
   }
 
   public String getOutWsStudentApplicationMatricAutoVerified() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationMatricAutoVerified);
   }
 
   public String getOutWsStudentApplicationApplicationMethod() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentApplicationApplicationMethod, 1);
   }
 
   public String getOutWsStudentApplicationCaoPaidFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentApplicationCaoPaidFlag, 1);
   }
 
   public short getOutWsStudentApplicationRegFromYear() {
      return exportView.OutWsStudentApplicationRegFromYear;
   }
 
   public short getOutWsStudentApplicationRegFromPeriod() {
      return exportView.OutWsStudentApplicationRegFromPeriod;
   }
 
   public String getOutWsStudentApplicationDocSuppliedToefl() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentApplicationDocSuppliedToefl, 1);
   }
 
   public String getOutWsStudentApplicationInternetApplicati() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentApplicationInternetApplicati, 1);
   }
 
   public short getOutWsStudentApplicationApplicationPeriod() {
      return exportView.OutWsStudentApplicationApplicationPeriod;
   }
 
   public String getOutShortUserCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutShortUserCsfStringsString1, 1);
   }
 
   public String getOutWsRegionalOfficeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsRegionalOfficeEngDescription, 28);
   }
 
   public String getOutActivityWsGenericCodeEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutActivityWsGenericCodeEngDescription, 40);
   }
 
};
