package Exsug07h.Abean;
 
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
public class Exsug07sPrtAdmissionLetter  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Exsug07sPrtAdmissionLetter");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Exsug07sPrtAdmissionLetter() {
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
 
 
   private Exsug07sPrtAdmissionLetterOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Exsug07sPrtAdmissionLetterOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doExsug07sPrtAdmissionLetterOperation();
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
 
   /**
    * Calls the transaction/procedure step on the server.
    * Input and Export data are passed via XML streams.
    *
    */
   public void executeXML(java.io.Reader requestXML,
                          java.io.Writer responseXML) throws NullPointerException, IOException {
      if (requestXML == null)
      {
         throw new NullPointerException("requestXML parameter to executeXML may not be null");
      }
      if (responseXML == null)
      {
         throw new NullPointerException("responseXML parameter to executeXML may not be null");
      }
 
      PrintWriter output = new PrintWriter(responseXML);
 
      Exsug07sPrtAdmissionLetterParser parser = new Exsug07sPrtAdmissionLetterParser();
      try  {
         if (oper == null) {
            oper = new Exsug07sPrtAdmissionLetterOperation(this);
         }
 

 
         parser.parse(requestXML);
 
         oper.doExsug07sPrtAdmissionLetterOperation();
 
         parser.buildOutput(output);
 
         output.flush();
      }
      catch (Throwable e)  {
         parser.buildError(output, e);
         output.flush();
         return;
      }
   }
 
   private String saxParser = "org.apache.xerces.parsers.SAXParser";
   /**
    * Sets the SAX parser classname to be used for XML parsing.
    *
    * @param s a string containing the classname
    */
   public void setSAXParser(String s) {
      if (s == null) saxParser = "";
      else  saxParser = s;
   }
   /**
    * Gets the SAX parser classname to be used for XML parsing.
    *
    * @return a string the classname
    */
   public String getSAXParser() {
      return saxParser;
   }
 
 
   private class operListener implements ActionListener, java.io.Serializable  {
      private Exsug07sPrtAdmissionLetter rP;
      operListener(Exsug07sPrtAdmissionLetter r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Exsug07sPrtAdmissionLetter", "Listener heard that Exsug07sPrtAdmissionLetterOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Exsug07sPrtAdmissionLetter ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Exsug07sPrtAdmissionLetter");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Exsug07sPrtAdmissionLetter ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Exsug07sPrtAdmissionLetter";
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
      importView.InPracticalFlagWsPracticalsValuesString1 = FixedStringAttr.valueOf("N", 1);
      exportView.OutTimetableGroup_MA = IntAttr.getDefaultValue();
      exportView.OutPracticalFlagWsPracticalsValuesString1 = FixedStringAttr.valueOf("N", 1);
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
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
      exportView.OutGroupAdmission_MA = IntAttr.getDefaultValue();
      exportView.OutGroupFc_MA = IntAttr.getDefaultValue();
      exportView.OutGroupNoAdmission_MA = IntAttr.getDefaultValue();
      exportView.OutAdmParGroup_MA = IntAttr.getDefaultValue();
      exportView.OutSupParGroup_MA = IntAttr.getDefaultValue();
   }

   Exsug07h.EXSUG07S_IA importView = Exsug07h.EXSUG07S_IA.getInstance();
   Exsug07h.EXSUG07S_OA exportView = Exsug07h.EXSUG07S_OA.getInstance();
   public String getInInternetFlagCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InInternetFlagCsfStringsString1, 1);
   }
   public void setInInternetFlagCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InInternetFlagCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InInternetFlagCsfStringsString1", null, null));
      }
      importView.InInternetFlagCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInToEmailAddressCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InToEmailAddressCsfStringsString132, 132);
   }
   public void setInToEmailAddressCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InToEmailAddressCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InToEmailAddressCsfStringsString132", null, null));
      }
      importView.InToEmailAddressCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFromEmailAddressCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFromEmailAddressCsfStringsString132, 132);
   }
   public void setInFromEmailAddressCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFromEmailAddressCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFromEmailAddressCsfStringsString132", null, null));
      }
      importView.InFromEmailAddressCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInSecurityWsWorkstationCode() {
      return StringAttr.valueOf(importView.InSecurityWsWorkstationCode);
   }
   public void setInSecurityWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsWorkstationCode", null, null));
      }
      importView.InSecurityWsWorkstationCode = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInPracticalFlagWsPracticalsValuesString1() {
      return FixedStringAttr.valueOf(importView.InPracticalFlagWsPracticalsValuesString1, 1);
   }
   public void setInPracticalFlagWsPracticalsValuesString1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Exsug07sPrtAdmissionLetterWsPracticalsValuesString1PropertyEditor pe = new Exsug07sPrtAdmissionLetterWsPracticalsValuesString1PropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPracticalFlagWsPracticalsValuesString1 value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPracticalFlagWsPracticalsValuesString1", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPracticalFlagWsPracticalsValuesString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPracticalFlagWsPracticalsValuesString1", null, null));
      }
      importView.InPracticalFlagWsPracticalsValuesString1 = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsPrinterCode, 10);
   }
   public void setInSecurityWsPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsPrinterCode", null, null));
      }
      importView.InSecurityWsPrinterCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInSecurityWsUserNumber() {
      return importView.InSecurityWsUserNumber;
   }
   public void setInSecurityWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSecurityWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
      importView.InSecurityWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
      try {
          setInSecurityWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
   }
 
   public short getInSecurityWsUserNumberOfLogonAttempts() {
      return importView.InSecurityWsUserNumberOfLogonAttempts;
   }
   public void setInSecurityWsUserNumberOfLogonAttempts(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts has more than 1 digits.",
               new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
      importView.InSecurityWsUserNumberOfLogonAttempts = ShortAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserNumberOfLogonAttempts(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
      try {
          setInSecurityWsUserNumberOfLogonAttempts(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
   }
 
   public String getInSecurityWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserLoggedOnFlag, 1);
   }
   public void setInSecurityWsUserLoggedOnFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSecurityWsUserLoggedOnFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserLoggedOnFlag", null, null));
      }
      importView.InSecurityWsUserLoggedOnFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInSecurityWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserInUsedFlag, 1);
   }
   public void setInSecurityWsUserInUsedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSecurityWsUserInUsedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserInUsedFlag", null, null));
      }
      importView.InSecurityWsUserInUsedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInSecurityWsUserLastLogonDate() {
      return DateAttr.toCalendar(importView.InSecurityWsUserLastLogonDate);
   }
   public int getAsIntInSecurityWsUserLastLogonDate() {
      return DateAttr.toInt(importView.InSecurityWsUserLastLogonDate);
   }
   public void setInSecurityWsUserLastLogonDate(Calendar s)
      throws PropertyVetoException {
      importView.InSecurityWsUserLastLogonDate = DateAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserLastLogonDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSecurityWsUserLastLogonDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInSecurityWsUserLastLogonDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSecurityWsUserLastLogonDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InSecurityWsUserLastLogonDate", null, null));
         }
      }
   }
   public void setAsIntInSecurityWsUserLastLogonDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInSecurityWsUserLastLogonDate(temp);
   }
 
   public String getInSecurityWsUserName() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserName, 28);
   }
   public void setInSecurityWsUserName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InSecurityWsUserName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserName", null, null));
      }
      importView.InSecurityWsUserName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPersonnelNo, 10);
   }
   public void setInSecurityWsUserPersonnelNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsUserPersonnelNo must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPersonnelNo", null, null));
      }
      importView.InSecurityWsUserPersonnelNo = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInSecurityWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPhoneNumber, 20);
   }
   public void setInSecurityWsUserPhoneNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSecurityWsUserPhoneNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPhoneNumber", null, null));
      }
      importView.InSecurityWsUserPhoneNumber = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInSecurityWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPassword, 12);
   }
   public void setInSecurityWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InSecurityWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPassword", null, null));
      }
      importView.InSecurityWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
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
 
   public short getInStudentStudyUnitExamYear() {
      return importView.InStudentStudyUnitExamYear;
   }
   public void setInStudentStudyUnitExamYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentStudyUnitExamYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentStudyUnitExamYear", null, null));
      }
      importView.InStudentStudyUnitExamYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentStudyUnitExamYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentStudyUnitExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentStudyUnitExamYear", null, null));
      }
      try {
          setInStudentStudyUnitExamYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentStudyUnitExamYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentStudyUnitExamYear", null, null));
      }
   }
 
   public short getInStudentStudyUnitMkExamPeriod() {
      return importView.InStudentStudyUnitMkExamPeriod;
   }
   public void setInStudentStudyUnitMkExamPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentStudyUnitMkExamPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentStudyUnitMkExamPeriod", null, null));
      }
      importView.InStudentStudyUnitMkExamPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentStudyUnitMkExamPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentStudyUnitMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentStudyUnitMkExamPeriod", null, null));
      }
      try {
          setInStudentStudyUnitMkExamPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentStudyUnitMkExamPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentStudyUnitMkExamPeriod", null, null));
      }
   }
 
   public String getInComment1CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment1CsfStringsString100, 100);
   }
   public void setInComment1CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment1CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment1CsfStringsString100", null, null));
      }
      importView.InComment1CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment2CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment2CsfStringsString100, 100);
   }
   public void setInComment2CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment2CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment2CsfStringsString100", null, null));
      }
      importView.InComment2CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment3CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment3CsfStringsString100, 100);
   }
   public void setInComment3CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment3CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment3CsfStringsString100", null, null));
      }
      importView.InComment3CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment4CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment4CsfStringsString100, 100);
   }
   public void setInComment4CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment4CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment4CsfStringsString100", null, null));
      }
      importView.InComment4CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment5CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment5CsfStringsString100, 100);
   }
   public void setInComment5CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment5CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment5CsfStringsString100", null, null));
      }
      importView.InComment5CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment6CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment6CsfStringsString100, 100);
   }
   public void setInComment6CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment6CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment6CsfStringsString100", null, null));
      }
      importView.InComment6CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment7CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment7CsfStringsString100, 100);
   }
   public void setInComment7CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment7CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment7CsfStringsString100", null, null));
      }
      importView.InComment7CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment8CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment8CsfStringsString100, 100);
   }
   public void setInComment8CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment8CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment8CsfStringsString100", null, null));
      }
      importView.InComment8CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment9CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment9CsfStringsString100, 100);
   }
   public void setInComment9CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment9CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment9CsfStringsString100", null, null));
      }
      importView.InComment9CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment10CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment10CsfStringsString100, 100);
   }
   public void setInComment10CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment10CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment10CsfStringsString100", null, null));
      }
      importView.InComment10CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment11CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment11CsfStringsString100, 100);
   }
   public void setInComment11CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment11CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment11CsfStringsString100", null, null));
      }
      importView.InComment11CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInComment12CsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InComment12CsfStringsString100, 100);
   }
   public void setInComment12CsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InComment12CsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InComment12CsfStringsString100", null, null));
      }
      importView.InComment12CsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInInvigilatorAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InInvigilatorAddressCsfStringsString1, 1);
   }
   public void setInInvigilatorAddressCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InInvigilatorAddressCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InInvigilatorAddressCsfStringsString1", null, null));
      }
      importView.InInvigilatorAddressCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getOutContactWsAddressV2ReferenceNo() {
      return exportView.OutContactWsAddressV2ReferenceNo;
   }
 
   public String getOutContactWsAddressV2AddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressReferenceFlag, 1);
   }
 
   public short getOutContactWsAddressV2Type() {
      return exportView.OutContactWsAddressV2Type;
   }
 
   public short getOutContactWsAddressV2Category() {
      return exportView.OutContactWsAddressV2Category;
   }
 
   public String getOutContactWsAddressV2CategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2CategoryDescription, 28);
   }
 
   public String getOutContactWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressLine1, 28);
   }
 
   public String getOutContactWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressLine2, 28);
   }
 
   public String getOutContactWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressLine3, 28);
   }
 
   public String getOutContactWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressLine4, 28);
   }
 
   public String getOutContactWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressLine5, 28);
   }
 
   public String getOutContactWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2AddressLine6, 28);
   }
 
   public short getOutContactWsAddressV2PostalCode() {
      return exportView.OutContactWsAddressV2PostalCode;
   }
 
   public String getOutContactWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2HomeNumber, 28);
   }
 
   public String getOutContactWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2WorkNumber, 28);
   }
 
   public String getOutContactWsAddressV2FaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2FaxNumber, 28);
   }
 
   public String getOutContactWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2CellNumber, 20);
   }
 
   public String getOutContactWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutContactWsAddressV2EmailAddress, 60);
   }
 
   public short getOutContactWsAddressV2ErrorCode() {
      return exportView.OutContactWsAddressV2ErrorCode;
   }
 
   public int getOutContactWsAddressV2UserNumber() {
      return exportView.OutContactWsAddressV2UserNumber;
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutEmailOrFaxCsfStringsString1, 1);
   }
 
   public String getOutFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNoCsfStringsString132, 132);
   }
 
   public String getOutSubjectCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutSubjectCsfStringsString132, 132);
   }
 
   public String getOutEmailWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutEmailWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutToEmailAddressCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutToEmailAddressCsfStringsString132, 132);
   }
 
   public String getOutFromEmailAddressCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFromEmailAddressCsfStringsString132, 132);
   }
 
   public String getOutSecurityWsWorkstationCode() {
      return StringAttr.valueOf(exportView.OutSecurityWsWorkstationCode);
   }
 
   public String getOutPrelimOrFinalTimetableCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPrelimOrFinalTimetableCsfStringsString1, 1);
   }
 
   public String getOutPrelimOrFinalTimetableCsfStringsString40() {
      return FixedStringAttr.valueOf(exportView.OutPrelimOrFinalTimetableCsfStringsString40, 40);
   }
 
   public short getOutWsDepartmentCode() {
      return exportView.OutWsDepartmentCode;
   }
 
   public String getOutWsDepartmentTelephoneNo() {
      return FixedStringAttr.valueOf(exportView.OutWsDepartmentTelephoneNo, 20);
   }
 
   public String getOutWsDepartmentFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsDepartmentFaxNumber, 20);
   }
 
   public String getOutInvigilatorContactWsAddressAuditFlag() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAuditFlag, 1);
   }
 
   public String getOutInvigilatorContactWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressLine1, 28);
   }
 
   public String getOutInvigilatorContactWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressLine2, 28);
   }
 
   public String getOutInvigilatorContactWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressLine3, 28);
   }
 
   public String getOutInvigilatorContactWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressLine4, 28);
   }
 
   public String getOutInvigilatorContactWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressLine5, 28);
   }
 
   public String getOutInvigilatorContactWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressLine6, 28);
   }
 
   public short getOutInvigilatorContactWsAddressPostalCode() {
      return exportView.OutInvigilatorContactWsAddressPostalCode;
   }
 
   public int getOutInvigilatorContactWsAddressReferenceNo() {
      return exportView.OutInvigilatorContactWsAddressReferenceNo;
   }
 
   public String getOutInvigilatorContactWsAddressAddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressAddressReferenceFlag, 1);
   }
 
   public short getOutInvigilatorContactWsAddressType() {
      return exportView.OutInvigilatorContactWsAddressType;
   }
 
   public Calendar getOutInvigilatorContactWsAddressAddressTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutInvigilatorContactWsAddressAddressTimestamp);
   }
   public String getAsStringOutInvigilatorContactWsAddressAddressTimestamp() {
      return TimestampAttr.toString(exportView.OutInvigilatorContactWsAddressAddressTimestamp);
   }
 
   public Calendar getOutInvigilatorContactWsAddressContactTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutInvigilatorContactWsAddressContactTimestamp);
   }
   public String getAsStringOutInvigilatorContactWsAddressContactTimestamp() {
      return TimestampAttr.toString(exportView.OutInvigilatorContactWsAddressContactTimestamp);
   }
 
   public short getOutInvigilatorContactWsAddressCategory() {
      return exportView.OutInvigilatorContactWsAddressCategory;
   }
 
   public String getOutInvigilatorContactWsAddressCategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressCategoryDescription, 28);
   }
 
   public String getOutInvigilatorContactWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressFaxNumber, 28);
   }
 
   public String getOutInvigilatorContactWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressEmailAddress, 28);
   }
 
   public String getOutInvigilatorContactWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressHomeNumber, 28);
   }
 
   public String getOutInvigilatorContactWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorContactWsAddressWorkNumber, 28);
   }
 
   public String getOutVenueWsAddressAuditFlag() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAuditFlag, 1);
   }
 
   public String getOutVenueWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressLine1, 28);
   }
 
   public String getOutVenueWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressLine2, 28);
   }
 
   public String getOutVenueWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressLine3, 28);
   }
 
   public String getOutVenueWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressLine4, 28);
   }
 
   public String getOutVenueWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressLine5, 28);
   }
 
   public String getOutVenueWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressLine6, 28);
   }
 
   public short getOutVenueWsAddressPostalCode() {
      return exportView.OutVenueWsAddressPostalCode;
   }
 
   public int getOutVenueWsAddressReferenceNo() {
      return exportView.OutVenueWsAddressReferenceNo;
   }
 
   public String getOutVenueWsAddressAddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressAddressReferenceFlag, 1);
   }
 
   public short getOutVenueWsAddressType() {
      return exportView.OutVenueWsAddressType;
   }
 
   public String getOutVenueWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressFaxNumber, 28);
   }
 
   public String getOutVenueWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressEmailAddress, 28);
   }
 
   public Calendar getOutVenueWsAddressAddressTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutVenueWsAddressAddressTimestamp);
   }
   public String getAsStringOutVenueWsAddressAddressTimestamp() {
      return TimestampAttr.toString(exportView.OutVenueWsAddressAddressTimestamp);
   }
 
   public Calendar getOutVenueWsAddressContactTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutVenueWsAddressContactTimestamp);
   }
   public String getAsStringOutVenueWsAddressContactTimestamp() {
      return TimestampAttr.toString(exportView.OutVenueWsAddressContactTimestamp);
   }
 
   public short getOutVenueWsAddressCategory() {
      return exportView.OutVenueWsAddressCategory;
   }
 
   public String getOutVenueWsAddressCategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressCategoryDescription, 28);
   }
 
   public String getOutVenueWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressHomeNumber, 28);
   }
 
   public String getOutVenueWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutVenueWsAddressWorkNumber, 28);
   }
 
   public String getOutWsExamVenueCode() {
      return FixedStringAttr.valueOf(exportView.OutWsExamVenueCode, 7);
   }
 
   public String getOutWsExamVenueInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsExamVenueInUseFlag, 1);
   }
 
   public String getOutWsExamVenueCourierFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsExamVenueCourierFlag, 1);
   }
 
   public int getOutWsExamVenueAddressRefNo() {
      return exportView.OutWsExamVenueAddressRefNo;
   }
 
   public String getOutWsExamVenueEngName() {
      return FixedStringAttr.valueOf(exportView.OutWsExamVenueEngName, 28);
   }
 
   public String getOutWsExamVenueAfrName() {
      return FixedStringAttr.valueOf(exportView.OutWsExamVenueAfrName, 28);
   }
 
   public int getOutWsExamVenueNumberOfSeats() {
      return exportView.OutWsExamVenueNumberOfSeats;
   }
 
   public int getOutWsStudentExamCentreMkStudentNr() {
      return exportView.OutWsStudentExamCentreMkStudentNr;
   }
 
   public short getOutWsStudentExamCentreMkExamPeriodCode() {
      return exportView.OutWsStudentExamCentreMkExamPeriodCode;
   }
 
   public String getOutWsStudentExamCentreMkExamCentreCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentExamCentreMkExamCentreCode, 5);
   }
 
   public final int OutTimetableGroupMax = 30;
   public short getOutTimetableGroupCount() {
      return (short)(exportView.OutTimetableGroup_MA);
   };
 
   public String getOutGcCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGcCsfLineActionBarAction[index], 1);
   }
 
   public String getOutGcCalcCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGcCalcCsfStringsString1[index], 1);
   }
 
   public String getOutGcWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGcWsStudyUnitCode[index], 7);
   }
 
   public String getOutGcWsStudyUnitEngLongDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGcWsStudyUnitEngLongDescription[index], 168);
   }
 
   public String getOutGcPaperNrCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGcPaperNrCsfStringsString1[index], 1);
   }
 
   public String getOutGcDateTimeCsfStringsString25(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGcDateTimeCsfStringsString25[index], 25);
   }
 
   public String getOutPracticalFlagWsPracticalsValuesString1() {
      return FixedStringAttr.valueOf(exportView.OutPracticalFlagWsPracticalsValuesString1, 1);
   }
 
   public final int OutGroupMax = 12;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGCsfStringsString100(int index) throws ArrayIndexOutOfBoundsException {
      if (11 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 11, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfStringsString100[index], 100);
   }
 
   public String getLclWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.LclWizfuncReportingControlPrinterCode);
   }
 
   public short getLclOutWsExamPeriodCode() {
      return exportView.LclOutWsExamPeriodCode;
   }
 
   public String getLclOutWsExamPeriodEngDescription() {
      return FixedStringAttr.valueOf(exportView.LclOutWsExamPeriodEngDescription, 28);
   }
 
   public String getLclOutWsExamPeriodAfrDescription() {
      return FixedStringAttr.valueOf(exportView.LclOutWsExamPeriodAfrDescription, 28);
   }
 
   public String getOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMethodResultReturnCode, 2);
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
 
   public String getOutSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsPrinterCode, 10);
   }
 
   public int getOutSecurityWsUserNumber() {
      return exportView.OutSecurityWsUserNumber;
   }
 
   public short getOutSecurityWsUserNumberOfLogonAttempts() {
      return exportView.OutSecurityWsUserNumberOfLogonAttempts;
   }
 
   public String getOutSecurityWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserLoggedOnFlag, 1);
   }
 
   public String getOutSecurityWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserInUsedFlag, 1);
   }
 
   public Calendar getOutSecurityWsUserLastLogonDate() {
      return DateAttr.toCalendar(exportView.OutSecurityWsUserLastLogonDate);
   }
   public int getAsIntOutSecurityWsUserLastLogonDate() {
      return DateAttr.toInt(exportView.OutSecurityWsUserLastLogonDate);
   }
 
   public String getOutSecurityWsUserName() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserName, 28);
   }
 
   public String getOutSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPersonnelNo, 10);
   }
 
   public String getOutSecurityWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPhoneNumber, 20);
   }
 
   public String getOutSecurityWsUserPassword() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPassword, 12);
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
 
   public String getOutWsStudentPassportNo() {
      return StringAttr.valueOf(exportView.OutWsStudentPassportNo);
   }
 
   public int getOutStudentAnnualRecordMkStudentNr() {
      return exportView.OutStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutStudentStudyUnitExamYear() {
      return exportView.OutStudentStudyUnitExamYear;
   }
 
   public short getOutStudentStudyUnitMkExamPeriod() {
      return exportView.OutStudentStudyUnitMkExamPeriod;
   }
 
   public String getOutComment1CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment1CsfStringsString100, 100);
   }
 
   public String getOutComment2CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment2CsfStringsString100, 100);
   }
 
   public String getOutComment3CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment3CsfStringsString100, 100);
   }
 
   public String getOutComment4CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment4CsfStringsString100, 100);
   }
 
   public String getOutComment5CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment5CsfStringsString100, 100);
   }
 
   public String getOutComment6CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment6CsfStringsString100, 100);
   }
 
   public String getOutComment7CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment7CsfStringsString100, 100);
   }
 
   public String getOutComment8CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment8CsfStringsString100, 100);
   }
 
   public String getOutComment9CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment9CsfStringsString100, 100);
   }
 
   public String getOutComment10CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment10CsfStringsString100, 100);
   }
 
   public String getOutComment11CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment11CsfStringsString100, 100);
   }
 
   public String getOutComment12CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutComment12CsfStringsString100, 100);
   }
 
   public String getOutInvigilatorAddressCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutInvigilatorAddressCsfStringsString1, 1);
   }
 
   public String getOutVenueDirectionsWsAddressAuditFlag() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAuditFlag, 1);
   }
 
   public String getOutVenueDirectionsWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressLine1, 28);
   }
 
   public String getOutVenueDirectionsWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressLine2, 28);
   }
 
   public String getOutVenueDirectionsWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressLine3, 28);
   }
 
   public String getOutVenueDirectionsWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressLine4, 28);
   }
 
   public String getOutVenueDirectionsWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressLine5, 28);
   }
 
   public String getOutVenueDirectionsWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressLine6, 28);
   }
 
   public short getOutVenueDirectionsWsAddressPostalCode() {
      return exportView.OutVenueDirectionsWsAddressPostalCode;
   }
 
   public int getOutVenueDirectionsWsAddressReferenceNo() {
      return exportView.OutVenueDirectionsWsAddressReferenceNo;
   }
 
   public String getOutVenueDirectionsWsAddressAddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressAddressReferenceFlag, 1);
   }
 
   public short getOutVenueDirectionsWsAddressType() {
      return exportView.OutVenueDirectionsWsAddressType;
   }
 
   public String getOutVenueDirectionsWsAddressFaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressFaxNumber, 28);
   }
 
   public String getOutVenueDirectionsWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressEmailAddress, 28);
   }
 
   public Calendar getOutVenueDirectionsWsAddressAddressTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutVenueDirectionsWsAddressAddressTimestamp);
   }
   public String getAsStringOutVenueDirectionsWsAddressAddressTimestamp() {
      return TimestampAttr.toString(exportView.OutVenueDirectionsWsAddressAddressTimestamp);
   }
 
   public Calendar getOutVenueDirectionsWsAddressContactTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutVenueDirectionsWsAddressContactTimestamp);
   }
   public String getAsStringOutVenueDirectionsWsAddressContactTimestamp() {
      return TimestampAttr.toString(exportView.OutVenueDirectionsWsAddressContactTimestamp);
   }
 
   public short getOutVenueDirectionsWsAddressCategory() {
      return exportView.OutVenueDirectionsWsAddressCategory;
   }
 
   public String getOutVenueDirectionsWsAddressCategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressCategoryDescription, 28);
   }
 
   public String getOutVenueDirectionsWsAddressHomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressHomeNumber, 28);
   }
 
   public String getOutVenueDirectionsWsAddressWorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutVenueDirectionsWsAddressWorkNumber, 28);
   }
 
   public final int OutGroupAdmissionMax = 30;
   public short getOutGroupAdmissionCount() {
      return (short)(exportView.OutGroupAdmission_MA);
   };
 
   public String getOutGAdmissionWizfuncReportDetailAttr(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAdmissionWizfuncReportDetailAttr[index], 130);
   }
 
   public final int OutGroupFcMax = 30;
   public short getOutGroupFcCount() {
      return (short)(exportView.OutGroupFc_MA);
   };
 
   public String getOutGFcWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGFcWsStudyUnitCode[index], 7);
   }
 
   public final int OutGroupNoAdmissionMax = 30;
   public short getOutGroupNoAdmissionCount() {
      return (short)(exportView.OutGroupNoAdmission_MA);
   };
 
   public String getOutGNoAdmissionWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (29 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 29, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGNoAdmissionWsStudyUnitCode[index], 7);
   }
 
   public final int OutAdmParGroupMax = 20;
   public short getOutAdmParGroupCount() {
      return (short)(exportView.OutAdmParGroup_MA);
   };
 
   public short getOutGWsDocumentParagraphCode(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutGWsDocumentParagraphCode[index];
   }
 
   public final int OutSupParGroupMax = 10;
   public short getOutSupParGroupCount() {
      return (short)(exportView.OutSupParGroup_MA);
   };
 
   public String getOutGWsDocumentParagraphLineText(int index) throws ArrayIndexOutOfBoundsException {
      if (9 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 9, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGWsDocumentParagraphLineText[index]);
   }
 
   public int getLclWsStudentChangeAuditTrailMkStudentNr() {
      return exportView.LclWsStudentChangeAuditTrailMkStudentNr;
   }
 
   public Calendar getLclWsStudentChangeAuditTrailTimestamp() {
      return TimestampAttr.toCalendar(exportView.LclWsStudentChangeAuditTrailTimestamp);
   }
   public String getAsStringLclWsStudentChangeAuditTrailTimestamp() {
      return TimestampAttr.toString(exportView.LclWsStudentChangeAuditTrailTimestamp);
   }
 
   public int getLclWsStudentChangeAuditTrailMkUserCode() {
      return exportView.LclWsStudentChangeAuditTrailMkUserCode;
   }
 
   public String getLclWsStudentChangeAuditTrailChangeCode() {
      return FixedStringAttr.valueOf(exportView.LclWsStudentChangeAuditTrailChangeCode, 2);
   }
 
   public int getLclWsStudentChangeAuditTrailSupervisorCode() {
      return exportView.LclWsStudentChangeAuditTrailSupervisorCode;
   }
 
   public String getLclWsStudentChangeAuditTrailErrorFlag() {
      return FixedStringAttr.valueOf(exportView.LclWsStudentChangeAuditTrailErrorFlag, 1);
   }
 
   public String getLclWsStudentChangeAuditTrailValueChanged() {
      return FixedStringAttr.valueOf(exportView.LclWsStudentChangeAuditTrailValueChanged, 10);
   }
 
   public String getLclOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.LclOutWsMethodResultReturnCode, 2);
   }
 
   private class Exsug07sPrtAdmissionLetterParser
                  extends org.xml.sax.helpers.DefaultHandler
                  implements org.xml.sax.ErrorHandler  {
       private int currentLevel;
       private short [] numberOfChildren = new short[20];
       private String [] elementNames = new String[20];
       private String noNamespaceSchemaLocation;
       private StringBuffer textBuffer = new StringBuffer();
 
       public Exsug07sPrtAdmissionLetterParser() {
       }
 
 
       /**
        * Internal method to parse special characters in XML exception data.
        **/
       private String dumpException(Throwable z) {
           StringWriter sw = new StringWriter();
           PrintWriter pw = new PrintWriter(sw);
           z.printStackTrace(pw);
           return(escapeText(sw.toString()));
       }
 
       private String escapeText(String input) {
           StringBuffer result = new StringBuffer();
           for (int i = 0; i < input.length(); i++)
           {
               switch (input.charAt(i))
               {
                   case '&':  result.append("&amp;"); break;
                   case '<':  result.append("&lt;"); break;
                   case '>':  result.append("&gt;"); break;
                   case '\'': result.append("&apos;"); break;
                   case '\"': result.append("&quot;"); break;
                   default:   result.append(input.charAt(i)); break;
               }
           }
           return result.toString();
       }
 
       public void parse(Reader input) throws Exception {
           System.setProperty("org.xml.sax.driver", getSAXParser());
           org.xml.sax.XMLReader xmlReader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
           xmlReader.setErrorHandler(this);
           xmlReader.setContentHandler(this);
           xmlReader.setFeature("http://xml.org/sax/features/validation", true);
           xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
           try {
               xmlReader.setFeature("http://apache.org/xml/features/validation/dynamic", true);
           } catch (org.xml.sax.SAXNotRecognizedException e) {
              // This was an optional feature, just continue
           } catch (org.xml.sax.SAXNotSupportedException e) {
              // This was an optional feature, just continue
           }
           xmlReader.parse(new org.xml.sax.InputSource(input));
       }
 
       public void buildError(PrintWriter output, Throwable e) {
           output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
           output.println("");
           output.print("<Exsug07sPrtAdmissionLetter xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
           if (noNamespaceSchemaLocation != null && noNamespaceSchemaLocation.length() > 0)
           {
               output.println("");
               output.print("               xsi:noNamespaceSchemaLocation=\"" + noNamespaceSchemaLocation + "\"");
           }
           output.println(">");
           output.println("");
           output.println(" <error>");
           if (e instanceof org.xml.sax.SAXParseException)
           {
              // Error generated by the parser
              // Unpack the delivered exception to get the exception it contains
              Throwable  x = e;
              if (((org.xml.sax.SAXParseException)e).getException() != null)
                  x = (Throwable)((org.xml.sax.SAXParseException)e).getException();
              output.println("  <type>" + x.getClass().getName() + "</type>");
              output.print("  <description>LINE: " + ((org.xml.sax.SAXParseException)e).getLineNumber() + ", URI: " + ((org.xml.sax.SAXParseException)e).getSystemId());
              output.print(", MESSAGE: " + escapeText(x.toString()));
              output.println("</description>");
              output.println("  <trace>");
              output.print(dumpException(x));
              output.println("  </trace>");
           }
           else if (e instanceof org.xml.sax.SAXException)
           {
              // Error generated by this application
              // (or a parser-initialization error)
              Throwable  x = e;
              if (((org.xml.sax.SAXException)e).getException() != null)
                  x = (Throwable)((org.xml.sax.SAXException)e).getException();
              output.println("  <type>" + x.getClass().getName() + "</type>");
              output.println("  <description>" + escapeText(x.toString())+ "</description>");
              output.println("  <trace>");
              output.print(dumpException(x));
              output.println("  </trace>");
           }
           else
           {
              output.println("  <type>" + e.getClass().getName() + "</type>");
              output.println("  <description>" + escapeText(e.toString())+ "</description>");
              output.println("  <trace>");
              output.print(dumpException(e));
              output.println("  </trace>");
           }
           output.println(" </error>");
           output.println("");
           output.println("</Exsug07sPrtAdmissionLetter>");
           output.flush();
       }
 
       public void buildOutput(PrintWriter output) throws Exception {
           output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
           output.println("");
           output.print("<Exsug07sPrtAdmissionLetter xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
           if (noNamespaceSchemaLocation != null && noNamespaceSchemaLocation.length() > 0)
           {
               output.println("");
               output.print("               xsi:noNamespaceSchemaLocation=\"" + noNamespaceSchemaLocation + "\"");
           }
           output.println(">");
           output.println("");
           output.println(" <response command=\"" + escapeText(getCommandReturned()) + "\"");
           output.println("           exitState=\"" + getExitStateReturned() + "\"");
           switch (getExitStateType())
           {
              case IInMessage.TYPE_INFORMATIONAL:
                 output.println("           exitStateType=\"Informational\"");
                 break;
              case IInMessage.TYPE_WARNING:
                 output.println("           exitStateType=\"Warning\"");
                 break;
              case IInMessage.TYPE_ERROR:
                 output.println("           exitStateType=\"Error\"");
                 break;
              case IInMessage.TYPE_UNKNOWN:
              default:
                 output.println("           exitStateType=\"OK\"");
                 break;
           }
           output.print("           exitStateMsg=\"" + escapeText(getExitStateMsg()) + "\"");
           output.println(">");
           output.println("  <OutContactWsAddressV2>");
            output.print("   <ReferenceNo>");
            output.print(escapeText(IntAttr.toString(exportView.OutContactWsAddressV2ReferenceNo)));
            output.println("</ReferenceNo>");
            output.print("   <AddressReferenceFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressReferenceFlag, 1)));
            output.println("</AddressReferenceFlag>");
            output.print("   <Type>");
            output.print(escapeText(ShortAttr.toString(exportView.OutContactWsAddressV2Type)));
            output.println("</Type>");
            output.print("   <Category>");
            output.print(escapeText(ShortAttr.toString(exportView.OutContactWsAddressV2Category)));
            output.println("</Category>");
            output.print("   <CategoryDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2CategoryDescription, 28)));
            output.println("</CategoryDescription>");
            output.print("   <AddressLine1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressLine1, 28)));
            output.println("</AddressLine1>");
            output.print("   <AddressLine2>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressLine2, 28)));
            output.println("</AddressLine2>");
            output.print("   <AddressLine3>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressLine3, 28)));
            output.println("</AddressLine3>");
            output.print("   <AddressLine4>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressLine4, 28)));
            output.println("</AddressLine4>");
            output.print("   <AddressLine5>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressLine5, 28)));
            output.println("</AddressLine5>");
            output.print("   <AddressLine6>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2AddressLine6, 28)));
            output.println("</AddressLine6>");
            output.print("   <PostalCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutContactWsAddressV2PostalCode)));
            output.println("</PostalCode>");
            output.print("   <HomeNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2HomeNumber, 28)));
            output.println("</HomeNumber>");
            output.print("   <WorkNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2WorkNumber, 28)));
            output.println("</WorkNumber>");
            output.print("   <FaxNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2FaxNumber, 28)));
            output.println("</FaxNumber>");
            output.print("   <CellNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2CellNumber, 20)));
            output.println("</CellNumber>");
            output.print("   <EmailAddress>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutContactWsAddressV2EmailAddress, 60)));
            output.println("</EmailAddress>");
            output.print("   <ErrorCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutContactWsAddressV2ErrorCode)));
            output.println("</ErrorCode>");
            output.print("   <UserNumber>");
            output.print(escapeText(IntAttr.toString(exportView.OutContactWsAddressV2UserNumber)));
            output.println("</UserNumber>");
           output.println("  </OutContactWsAddressV2>");
           output.println("  <OutFaxNameCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutFaxNameCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutFaxNameCsfStrings>");
           output.println("  <OutEmailOrFaxCsfStrings>");
            output.print("   <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutEmailOrFaxCsfStringsString1, 1)));
            output.println("</String1>");
           output.println("  </OutEmailOrFaxCsfStrings>");
           output.println("  <OutFaxNoCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutFaxNoCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutFaxNoCsfStrings>");
           output.println("  <OutSubjectCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSubjectCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutSubjectCsfStrings>");
           output.println("  <OutEmailWizfuncReportingControl>");
            output.print("   <PathAndFilename>");
            output.print(escapeText(StringAttr.toString(exportView.OutEmailWizfuncReportingControlPathAndFilename)));
            output.println("</PathAndFilename>");
           output.println("  </OutEmailWizfuncReportingControl>");
           output.println("  <OutToEmailAddressCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutToEmailAddressCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutToEmailAddressCsfStrings>");
           output.println("  <OutFromEmailAddressCsfStrings>");
            output.print("   <String132>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutFromEmailAddressCsfStringsString132, 132)));
            output.println("</String132>");
           output.println("  </OutFromEmailAddressCsfStrings>");
           output.println("  <OutSecurityWsWorkstation>");
            output.print("   <Code>");
            output.print(escapeText(StringAttr.toString(exportView.OutSecurityWsWorkstationCode)));
            output.println("</Code>");
           output.println("  </OutSecurityWsWorkstation>");
           output.println("  <OutPrelimOrFinalTimetableCsfStrings>");
            output.print("   <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutPrelimOrFinalTimetableCsfStringsString1, 1)));
            output.println("</String1>");
            output.print("   <String40>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutPrelimOrFinalTimetableCsfStringsString40, 40)));
            output.println("</String40>");
           output.println("  </OutPrelimOrFinalTimetableCsfStrings>");
           output.println("  <OutWsDepartment>");
            output.print("   <Code>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsDepartmentCode)));
            output.println("</Code>");
            output.print("   <TelephoneNo>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsDepartmentTelephoneNo, 20)));
            output.println("</TelephoneNo>");
            output.print("   <FaxNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsDepartmentFaxNumber, 20)));
            output.println("</FaxNumber>");
           output.println("  </OutWsDepartment>");
           output.println("  <OutInvigilatorContactWsAddress>");
            output.print("   <AuditFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAuditFlag, 1)));
            output.println("</AuditFlag>");
            output.print("   <AddressLine1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressLine1, 28)));
            output.println("</AddressLine1>");
            output.print("   <AddressLine2>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressLine2, 28)));
            output.println("</AddressLine2>");
            output.print("   <AddressLine3>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressLine3, 28)));
            output.println("</AddressLine3>");
            output.print("   <AddressLine4>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressLine4, 28)));
            output.println("</AddressLine4>");
            output.print("   <AddressLine5>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressLine5, 28)));
            output.println("</AddressLine5>");
            output.print("   <AddressLine6>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressLine6, 28)));
            output.println("</AddressLine6>");
            output.print("   <PostalCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutInvigilatorContactWsAddressPostalCode)));
            output.println("</PostalCode>");
            output.print("   <ReferenceNo>");
            output.print(escapeText(IntAttr.toString(exportView.OutInvigilatorContactWsAddressReferenceNo)));
            output.println("</ReferenceNo>");
            output.print("   <AddressReferenceFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressAddressReferenceFlag, 1)));
            output.println("</AddressReferenceFlag>");
            output.print("   <Type>");
            output.print(escapeText(ShortAttr.toString(exportView.OutInvigilatorContactWsAddressType)));
            output.println("</Type>");
            output.print("   <AddressTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutInvigilatorContactWsAddressAddressTimestamp)));
            output.println("</AddressTimestamp>");
            output.print("   <ContactTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutInvigilatorContactWsAddressContactTimestamp)));
            output.println("</ContactTimestamp>");
            output.print("   <Category>");
            output.print(escapeText(ShortAttr.toString(exportView.OutInvigilatorContactWsAddressCategory)));
            output.println("</Category>");
            output.print("   <CategoryDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressCategoryDescription, 28)));
            output.println("</CategoryDescription>");
            output.print("   <FaxNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressFaxNumber, 28)));
            output.println("</FaxNumber>");
            output.print("   <EmailAddress>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressEmailAddress, 28)));
            output.println("</EmailAddress>");
            output.print("   <HomeNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressHomeNumber, 28)));
            output.println("</HomeNumber>");
            output.print("   <WorkNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorContactWsAddressWorkNumber, 28)));
            output.println("</WorkNumber>");
           output.println("  </OutInvigilatorContactWsAddress>");
           output.println("  <OutVenueWsAddress>");
            output.print("   <AuditFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAuditFlag, 1)));
            output.println("</AuditFlag>");
            output.print("   <AddressLine1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressLine1, 28)));
            output.println("</AddressLine1>");
            output.print("   <AddressLine2>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressLine2, 28)));
            output.println("</AddressLine2>");
            output.print("   <AddressLine3>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressLine3, 28)));
            output.println("</AddressLine3>");
            output.print("   <AddressLine4>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressLine4, 28)));
            output.println("</AddressLine4>");
            output.print("   <AddressLine5>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressLine5, 28)));
            output.println("</AddressLine5>");
            output.print("   <AddressLine6>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressLine6, 28)));
            output.println("</AddressLine6>");
            output.print("   <PostalCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutVenueWsAddressPostalCode)));
            output.println("</PostalCode>");
            output.print("   <ReferenceNo>");
            output.print(escapeText(IntAttr.toString(exportView.OutVenueWsAddressReferenceNo)));
            output.println("</ReferenceNo>");
            output.print("   <AddressReferenceFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressAddressReferenceFlag, 1)));
            output.println("</AddressReferenceFlag>");
            output.print("   <Type>");
            output.print(escapeText(ShortAttr.toString(exportView.OutVenueWsAddressType)));
            output.println("</Type>");
            output.print("   <FaxNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressFaxNumber, 28)));
            output.println("</FaxNumber>");
            output.print("   <EmailAddress>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressEmailAddress, 28)));
            output.println("</EmailAddress>");
            output.print("   <AddressTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutVenueWsAddressAddressTimestamp)));
            output.println("</AddressTimestamp>");
            output.print("   <ContactTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutVenueWsAddressContactTimestamp)));
            output.println("</ContactTimestamp>");
            output.print("   <Category>");
            output.print(escapeText(ShortAttr.toString(exportView.OutVenueWsAddressCategory)));
            output.println("</Category>");
            output.print("   <CategoryDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressCategoryDescription, 28)));
            output.println("</CategoryDescription>");
            output.print("   <HomeNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressHomeNumber, 28)));
            output.println("</HomeNumber>");
            output.print("   <WorkNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueWsAddressWorkNumber, 28)));
            output.println("</WorkNumber>");
           output.println("  </OutVenueWsAddress>");
           output.println("  <OutWsExamVenue>");
            output.print("   <Code>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsExamVenueCode, 7)));
            output.println("</Code>");
            output.print("   <InUseFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsExamVenueInUseFlag, 1)));
            output.println("</InUseFlag>");
            output.print("   <CourierFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsExamVenueCourierFlag, 1)));
            output.println("</CourierFlag>");
            output.print("   <AddressRefNo>");
            output.print(escapeText(IntAttr.toString(exportView.OutWsExamVenueAddressRefNo)));
            output.println("</AddressRefNo>");
            output.print("   <EngName>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsExamVenueEngName, 28)));
            output.println("</EngName>");
            output.print("   <AfrName>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsExamVenueAfrName, 28)));
            output.println("</AfrName>");
            output.print("   <NumberOfSeats>");
            output.print(escapeText(IntAttr.toString(exportView.OutWsExamVenueNumberOfSeats)));
            output.println("</NumberOfSeats>");
           output.println("  </OutWsExamVenue>");
           output.println("  <OutWsStudentExamCentre>");
            output.print("   <MkStudentNr>");
            output.print(escapeText(IntAttr.toString(exportView.OutWsStudentExamCentreMkStudentNr)));
            output.println("</MkStudentNr>");
            output.print("   <MkExamPeriodCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentExamCentreMkExamPeriodCode)));
            output.println("</MkExamPeriodCode>");
            output.print("   <MkExamCentreCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentExamCentreMkExamCentreCode, 5)));
            output.println("</MkExamCentreCode>");
           output.println("  </OutWsStudentExamCentre>");
           output.println("  <OutTimetableGroup>");
           for (int i = 0; i < getOutTimetableGroupCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGcCsfLineActionBar>");
            output.print("     <Action>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGcCsfLineActionBarAction[i], 1)));
            output.println("</Action>");
           output.println("    </OutGcCsfLineActionBar>");
           output.println("    <OutGcCalcCsfStrings>");
            output.print("     <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGcCalcCsfStringsString1[i], 1)));
            output.println("</String1>");
           output.println("    </OutGcCalcCsfStrings>");
           output.println("    <OutGcWsStudyUnit>");
            output.print("     <Code>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGcWsStudyUnitCode[i], 7)));
            output.println("</Code>");
            output.print("     <EngLongDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGcWsStudyUnitEngLongDescription[i], 168)));
            output.println("</EngLongDescription>");
           output.println("    </OutGcWsStudyUnit>");
           output.println("    <OutGcPaperNrCsfStrings>");
            output.print("     <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGcPaperNrCsfStringsString1[i], 1)));
            output.println("</String1>");
           output.println("    </OutGcPaperNrCsfStrings>");
           output.println("    <OutGcDateTimeCsfStrings>");
            output.print("     <String25>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGcDateTimeCsfStringsString25[i], 25)));
            output.println("</String25>");
           output.println("    </OutGcDateTimeCsfStrings>");
            output.println("   </row>");
           }
           output.println("  </OutTimetableGroup>");
           output.println("  <OutPracticalFlagWsPracticalsValues>");
            output.print("   <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutPracticalFlagWsPracticalsValuesString1, 1)));
            output.println("</String1>");
           output.println("  </OutPracticalFlagWsPracticalsValues>");
           output.println("  <OutGroup>");
           for (int i = 0; i < getOutGroupCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGCsfStrings>");
            output.print("     <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGCsfStringsString100[i], 100)));
            output.println("</String100>");
           output.println("    </OutGCsfStrings>");
            output.println("   </row>");
           }
           output.println("  </OutGroup>");
           output.println("  <LclWizfuncReportingControl>");
            output.print("   <PrinterCode>");
            output.print(escapeText(StringAttr.toString(exportView.LclWizfuncReportingControlPrinterCode)));
            output.println("</PrinterCode>");
           output.println("  </LclWizfuncReportingControl>");
           output.println("  <LclOutWsExamPeriod>");
            output.print("   <Code>");
            output.print(escapeText(ShortAttr.toString(exportView.LclOutWsExamPeriodCode)));
            output.println("</Code>");
            output.print("   <EngDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.LclOutWsExamPeriodEngDescription, 28)));
            output.println("</EngDescription>");
            output.print("   <AfrDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.LclOutWsExamPeriodAfrDescription, 28)));
            output.println("</AfrDescription>");
           output.println("  </LclOutWsExamPeriod>");
           output.println("  <OutWsMethodResult>");
            output.print("   <ReturnCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsMethodResultReturnCode, 2)));
            output.println("</ReturnCode>");
           output.println("  </OutWsMethodResult>");
           output.println("  <OutCsfStrings>");
            output.print("   <String500>");
            output.print(escapeText(StringAttr.toString(exportView.OutCsfStringsString500)));
            output.println("</String500>");
           output.println("  </OutCsfStrings>");
           output.println("  <OutCsfClientServerCommunications>");
            output.print("   <ReturnCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutCsfClientServerCommunicationsReturnCode)));
            output.println("</ReturnCode>");
            output.print("   <ServerVersionNumber>");
            output.print(escapeText(ShortAttr.toString(exportView.OutCsfClientServerCommunicationsServerVersionNumber)));
            output.println("</ServerVersionNumber>");
            output.print("   <ServerRevisionNumber>");
            output.print(escapeText(ShortAttr.toString(exportView.OutCsfClientServerCommunicationsServerRevisionNumber)));
            output.println("</ServerRevisionNumber>");
            output.print("   <ServerDevelopmentPhase>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsServerDevelopmentPhase, 1)));
            output.println("</ServerDevelopmentPhase>");
            output.print("   <Action>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsAction, 2)));
            output.println("</Action>");
            output.print("   <ClientIsGuiFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1)));
            output.println("</ClientIsGuiFlag>");
            output.print("   <ServerDate>");
            output.print(escapeText(DateAttr.toString(exportView.OutCsfClientServerCommunicationsServerDate)));
            output.println("</ServerDate>");
            output.print("   <ServerTime>");
            output.print(escapeText(TimeAttr.toString(exportView.OutCsfClientServerCommunicationsServerTime)));
            output.println("</ServerTime>");
            output.print("   <ServerTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutCsfClientServerCommunicationsServerTimestamp)));
            output.println("</ServerTimestamp>");
            output.print("   <ServerTransactionCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsServerTransactionCode, 8)));
            output.println("</ServerTransactionCode>");
            output.print("   <ServerUserId>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsServerUserId, 8)));
            output.println("</ServerUserId>");
            output.print("   <ServerRollbackFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1)));
            output.println("</ServerRollbackFlag>");
            output.print("   <ActionsPendingFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsActionsPendingFlag, 1)));
            output.println("</ActionsPendingFlag>");
            output.print("   <ClientVersionNumber>");
            output.print(escapeText(ShortAttr.toString(exportView.OutCsfClientServerCommunicationsClientVersionNumber)));
            output.println("</ClientVersionNumber>");
            output.print("   <ClientRevisionNumber>");
            output.print(escapeText(ShortAttr.toString(exportView.OutCsfClientServerCommunicationsClientRevisionNumber)));
            output.println("</ClientRevisionNumber>");
            output.print("   <ClientDevelopmentPhase>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsClientDevelopmentPhase, 1)));
            output.println("</ClientDevelopmentPhase>");
            output.print("   <ListLinkFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsListLinkFlag, 1)));
            output.println("</ListLinkFlag>");
            output.print("   <CancelFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsCancelFlag, 1)));
            output.println("</CancelFlag>");
            output.print("   <MaintLinkFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsMaintLinkFlag, 1)));
            output.println("</MaintLinkFlag>");
            output.print("   <ForceDatabaseRead>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutCsfClientServerCommunicationsForceDatabaseRead, 1)));
            output.println("</ForceDatabaseRead>");
           output.println("  </OutCsfClientServerCommunications>");
           output.println("  <OutSecurityWsPrinter>");
            output.print("   <Code>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsPrinterCode, 10)));
            output.println("</Code>");
           output.println("  </OutSecurityWsPrinter>");
           output.println("  <OutSecurityWsUser>");
            output.print("   <Number>");
            output.print(escapeText(IntAttr.toString(exportView.OutSecurityWsUserNumber)));
            output.println("</Number>");
            output.print("   <NumberOfLogonAttempts>");
            output.print(escapeText(ShortAttr.toString(exportView.OutSecurityWsUserNumberOfLogonAttempts)));
            output.println("</NumberOfLogonAttempts>");
            output.print("   <LoggedOnFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsUserLoggedOnFlag, 1)));
            output.println("</LoggedOnFlag>");
            output.print("   <InUsedFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsUserInUsedFlag, 1)));
            output.println("</InUsedFlag>");
            output.print("   <LastLogonDate>");
            output.print(escapeText(DateAttr.toString(exportView.OutSecurityWsUserLastLogonDate)));
            output.println("</LastLogonDate>");
            output.print("   <Name>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsUserName, 28)));
            output.println("</Name>");
            output.print("   <PersonnelNo>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsUserPersonnelNo, 10)));
            output.println("</PersonnelNo>");
            output.print("   <PhoneNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsUserPhoneNumber, 20)));
            output.println("</PhoneNumber>");
            output.print("   <Password>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutSecurityWsUserPassword, 12)));
            output.println("</Password>");
           output.println("  </OutSecurityWsUser>");
           output.println("  <OutWsStudent>");
            output.print("   <Nr>");
            output.print(escapeText(IntAttr.toString(exportView.OutWsStudentNr)));
            output.println("</Nr>");
            output.print("   <SpecialCharacterFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentSpecialCharacterFlag, 1)));
            output.println("</SpecialCharacterFlag>");
            output.print("   <MkTitle>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentMkTitle, 10)));
            output.println("</MkTitle>");
            output.print("   <Surname>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentSurname, 28)));
            output.println("</Surname>");
            output.print("   <FirstNames>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentFirstNames, 60)));
            output.println("</FirstNames>");
            output.print("   <PreviousSurname>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentPreviousSurname, 28)));
            output.println("</PreviousSurname>");
            output.print("   <SquashCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentSquashCode, 8)));
            output.println("</SquashCode>");
            output.print("   <Initials>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentInitials, 10)));
            output.println("</Initials>");
            output.print("   <IdentityNr>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentIdentityNr, 13)));
            output.println("</IdentityNr>");
            output.print("   <BirthDate>");
            output.print(escapeText(DateAttr.toString(exportView.OutWsStudentBirthDate)));
            output.println("</BirthDate>");
            output.print("   <Gender>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentGender, 1)));
            output.println("</Gender>");
            output.print("   <MkNationality>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentMkNationality, 4)));
            output.println("</MkNationality>");
            output.print("   <MkHomeLanguage>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentMkHomeLanguage, 2)));
            output.println("</MkHomeLanguage>");
            output.print("   <MkCorrespondenceLanguage>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentMkCorrespondenceLanguage, 2)));
            output.println("</MkCorrespondenceLanguage>");
            output.print("   <DeceasedFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentDeceasedFlag, 1)));
            output.println("</DeceasedFlag>");
            output.print("   <LibraryBlackList>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentLibraryBlackList)));
            output.println("</LibraryBlackList>");
            output.print("   <ExamFeesDebtFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentExamFeesDebtFlag, 1)));
            output.println("</ExamFeesDebtFlag>");
            output.print("   <DisciplinaryIncident>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentDisciplinaryIncident)));
            output.println("</DisciplinaryIncident>");
            output.print("   <PostGraduateStudiesApproved>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentPostGraduateStudiesApproved, 1)));
            output.println("</PostGraduateStudiesApproved>");
            output.print("   <PhasedOutStatus>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentPhasedOutStatus)));
            output.println("</PhasedOutStatus>");
            output.print("   <FinancialBlockFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentFinancialBlockFlag, 1)));
            output.println("</FinancialBlockFlag>");
            output.print("   <TwinFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentTwinFlag, 1)));
            output.println("</TwinFlag>");
            output.print("   <AccessRestrictedFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentAccessRestrictedFlag, 1)));
            output.println("</AccessRestrictedFlag>");
            output.print("   <NumberRestrictedFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentNumberRestrictedFlag, 1)));
            output.println("</NumberRestrictedFlag>");
            output.print("   <UnisaUndergradYearsRegistered>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentUnisaUndergradYearsRegistered)));
            output.println("</UnisaUndergradYearsRegistered>");
            output.print("   <UnisaHonoursYearsRegistered>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentUnisaHonoursYearsRegistered)));
            output.println("</UnisaHonoursYearsRegistered>");
            output.print("   <UnisaMastersYearsRegistered>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentUnisaMastersYearsRegistered)));
            output.println("</UnisaMastersYearsRegistered>");
            output.print("   <UnisaDoctrateYearsRegistered>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentUnisaDoctrateYearsRegistered)));
            output.println("</UnisaDoctrateYearsRegistered>");
            output.print("   <OtherMastersYearsRegistered>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentOtherMastersYearsRegistered)));
            output.println("</OtherMastersYearsRegistered>");
            output.print("   <OtherDoctrateYearsRegistered>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentOtherDoctrateYearsRegistered)));
            output.println("</OtherDoctrateYearsRegistered>");
            output.print("   <PreviouslyPostGraduateFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentPreviouslyPostGraduateFlag, 1)));
            output.println("</PreviouslyPostGraduateFlag>");
            output.print("   <PreviouslyUnisaPostGradFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentPreviouslyUnisaPostGradFlag, 1)));
            output.println("</PreviouslyUnisaPostGradFlag>");
            output.print("   <ResultBlockFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentResultBlockFlag, 1)));
            output.println("</ResultBlockFlag>");
            output.print("   <MkLastAcademicYear>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentMkLastAcademicYear)));
            output.println("</MkLastAcademicYear>");
            output.print("   <MkLastAcademicPeriod>");
            output.print(escapeText(ShortAttr.toString(exportView.OutWsStudentMkLastAcademicPeriod)));
            output.println("</MkLastAcademicPeriod>");
            output.print("   <MkCountryCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutWsStudentMkCountryCode, 4)));
            output.println("</MkCountryCode>");
            output.print("   <PassportNo>");
            output.print(escapeText(StringAttr.toString(exportView.OutWsStudentPassportNo)));
            output.println("</PassportNo>");
           output.println("  </OutWsStudent>");
           output.println("  <OutStudentAnnualRecord>");
            output.print("   <MkStudentNr>");
            output.print(escapeText(IntAttr.toString(exportView.OutStudentAnnualRecordMkStudentNr)));
            output.println("</MkStudentNr>");
           output.println("  </OutStudentAnnualRecord>");
           output.println("  <OutStudentStudyUnit>");
            output.print("   <ExamYear>");
            output.print(escapeText(ShortAttr.toString(exportView.OutStudentStudyUnitExamYear)));
            output.println("</ExamYear>");
            output.print("   <MkExamPeriod>");
            output.print(escapeText(ShortAttr.toString(exportView.OutStudentStudyUnitMkExamPeriod)));
            output.println("</MkExamPeriod>");
           output.println("  </OutStudentStudyUnit>");
           output.println("  <OutComment1CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment1CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment1CsfStrings>");
           output.println("  <OutComment2CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment2CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment2CsfStrings>");
           output.println("  <OutComment3CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment3CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment3CsfStrings>");
           output.println("  <OutComment4CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment4CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment4CsfStrings>");
           output.println("  <OutComment5CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment5CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment5CsfStrings>");
           output.println("  <OutComment6CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment6CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment6CsfStrings>");
           output.println("  <OutComment7CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment7CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment7CsfStrings>");
           output.println("  <OutComment8CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment8CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment8CsfStrings>");
           output.println("  <OutComment9CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment9CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment9CsfStrings>");
           output.println("  <OutComment10CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment10CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment10CsfStrings>");
           output.println("  <OutComment11CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment11CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment11CsfStrings>");
           output.println("  <OutComment12CsfStrings>");
            output.print("   <String100>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutComment12CsfStringsString100, 100)));
            output.println("</String100>");
           output.println("  </OutComment12CsfStrings>");
           output.println("  <OutInvigilatorAddressCsfStrings>");
            output.print("   <String1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutInvigilatorAddressCsfStringsString1, 1)));
            output.println("</String1>");
           output.println("  </OutInvigilatorAddressCsfStrings>");
           output.println("  <OutVenueDirectionsWsAddress>");
            output.print("   <AuditFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAuditFlag, 1)));
            output.println("</AuditFlag>");
            output.print("   <AddressLine1>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressLine1, 28)));
            output.println("</AddressLine1>");
            output.print("   <AddressLine2>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressLine2, 28)));
            output.println("</AddressLine2>");
            output.print("   <AddressLine3>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressLine3, 28)));
            output.println("</AddressLine3>");
            output.print("   <AddressLine4>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressLine4, 28)));
            output.println("</AddressLine4>");
            output.print("   <AddressLine5>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressLine5, 28)));
            output.println("</AddressLine5>");
            output.print("   <AddressLine6>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressLine6, 28)));
            output.println("</AddressLine6>");
            output.print("   <PostalCode>");
            output.print(escapeText(ShortAttr.toString(exportView.OutVenueDirectionsWsAddressPostalCode)));
            output.println("</PostalCode>");
            output.print("   <ReferenceNo>");
            output.print(escapeText(IntAttr.toString(exportView.OutVenueDirectionsWsAddressReferenceNo)));
            output.println("</ReferenceNo>");
            output.print("   <AddressReferenceFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressAddressReferenceFlag, 1)));
            output.println("</AddressReferenceFlag>");
            output.print("   <Type>");
            output.print(escapeText(ShortAttr.toString(exportView.OutVenueDirectionsWsAddressType)));
            output.println("</Type>");
            output.print("   <FaxNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressFaxNumber, 28)));
            output.println("</FaxNumber>");
            output.print("   <EmailAddress>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressEmailAddress, 28)));
            output.println("</EmailAddress>");
            output.print("   <AddressTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutVenueDirectionsWsAddressAddressTimestamp)));
            output.println("</AddressTimestamp>");
            output.print("   <ContactTimestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.OutVenueDirectionsWsAddressContactTimestamp)));
            output.println("</ContactTimestamp>");
            output.print("   <Category>");
            output.print(escapeText(ShortAttr.toString(exportView.OutVenueDirectionsWsAddressCategory)));
            output.println("</Category>");
            output.print("   <CategoryDescription>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressCategoryDescription, 28)));
            output.println("</CategoryDescription>");
            output.print("   <HomeNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressHomeNumber, 28)));
            output.println("</HomeNumber>");
            output.print("   <WorkNumber>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutVenueDirectionsWsAddressWorkNumber, 28)));
            output.println("</WorkNumber>");
           output.println("  </OutVenueDirectionsWsAddress>");
           output.println("  <OutGroupAdmission>");
           for (int i = 0; i < getOutGroupAdmissionCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGAdmissionWizfuncReportDetail>");
            output.print("     <Attr>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGAdmissionWizfuncReportDetailAttr[i], 130)));
            output.println("</Attr>");
           output.println("    </OutGAdmissionWizfuncReportDetail>");
            output.println("   </row>");
           }
           output.println("  </OutGroupAdmission>");
           output.println("  <OutGroupFc>");
           for (int i = 0; i < getOutGroupFcCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGFcWsStudyUnit>");
            output.print("     <Code>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGFcWsStudyUnitCode[i], 7)));
            output.println("</Code>");
           output.println("    </OutGFcWsStudyUnit>");
            output.println("   </row>");
           }
           output.println("  </OutGroupFc>");
           output.println("  <OutGroupNoAdmission>");
           for (int i = 0; i < getOutGroupNoAdmissionCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGNoAdmissionWsStudyUnit>");
            output.print("     <Code>");
            output.print(escapeText(FixedStringAttr.toString(exportView.OutGNoAdmissionWsStudyUnitCode[i], 7)));
            output.println("</Code>");
           output.println("    </OutGNoAdmissionWsStudyUnit>");
            output.println("   </row>");
           }
           output.println("  </OutGroupNoAdmission>");
           output.println("  <OutAdmParGroup>");
           for (int i = 0; i < getOutAdmParGroupCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGWsDocumentParagraph>");
            output.print("     <Code>");
            output.print(escapeText(ShortAttr.toString(exportView.OutGWsDocumentParagraphCode[i])));
            output.println("</Code>");
           output.println("    </OutGWsDocumentParagraph>");
            output.println("   </row>");
           }
           output.println("  </OutAdmParGroup>");
           output.println("  <OutSupParGroup>");
           for (int i = 0; i < getOutSupParGroupCount(); i++) {
            output.println("   <row>");
           output.println("    <OutGWsDocumentParagraphLine>");
            output.print("     <Text>");
            output.print(escapeText(StringAttr.toString(exportView.OutGWsDocumentParagraphLineText[i])));
            output.println("</Text>");
           output.println("    </OutGWsDocumentParagraphLine>");
            output.println("   </row>");
           }
           output.println("  </OutSupParGroup>");
           output.println("  <LclWsStudentChangeAuditTrail>");
            output.print("   <MkStudentNr>");
            output.print(escapeText(IntAttr.toString(exportView.LclWsStudentChangeAuditTrailMkStudentNr)));
            output.println("</MkStudentNr>");
            output.print("   <Timestamp>");
            output.print(escapeText(TimestampAttr.toString(exportView.LclWsStudentChangeAuditTrailTimestamp)));
            output.println("</Timestamp>");
            output.print("   <MkUserCode>");
            output.print(escapeText(IntAttr.toString(exportView.LclWsStudentChangeAuditTrailMkUserCode)));
            output.println("</MkUserCode>");
            output.print("   <ChangeCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.LclWsStudentChangeAuditTrailChangeCode, 2)));
            output.println("</ChangeCode>");
            output.print("   <SupervisorCode>");
            output.print(escapeText(IntAttr.toString(exportView.LclWsStudentChangeAuditTrailSupervisorCode)));
            output.println("</SupervisorCode>");
            output.print("   <ErrorFlag>");
            output.print(escapeText(FixedStringAttr.toString(exportView.LclWsStudentChangeAuditTrailErrorFlag, 1)));
            output.println("</ErrorFlag>");
            output.print("   <ValueChanged>");
            output.print(escapeText(FixedStringAttr.toString(exportView.LclWsStudentChangeAuditTrailValueChanged, 10)));
            output.println("</ValueChanged>");
           output.println("  </LclWsStudentChangeAuditTrail>");
           output.println("  <LclOutWsMethodResult>");
            output.print("   <ReturnCode>");
            output.print(escapeText(FixedStringAttr.toString(exportView.LclOutWsMethodResultReturnCode, 2)));
            output.println("</ReturnCode>");
           output.println("  </LclOutWsMethodResult>");
           output.println(" </response>");
           output.println("");
           output.println("</Exsug07sPrtAdmissionLetter>");
           output.flush();
       }
 
       public void error(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
           throw e;
       }
 
       public void fatalError(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException {
           throw e;
       }
 
       public void startDocument() throws org.xml.sax.SAXException {
           currentLevel = 0;
           numberOfChildren[0] = 0;
           elementNames[0] = "";
       }
 
       public void startElement(String namespaceURI,
                                String simpleName,
                                String qualifiedName,
                                org.xml.sax.Attributes attrs) throws org.xml.sax.SAXException {
           textBuffer.setLength(0);
 
           numberOfChildren[currentLevel]++;
           currentLevel++;
 
           String tmpName = simpleName;
           if ("".equals(tmpName) == true)
               tmpName = qualifiedName;
 
           if (currentLevel == 0)
               elementNames[currentLevel] = tmpName;
           else
               elementNames[currentLevel] = elementNames[currentLevel-1] + tmpName;
 
           String attrName;
           if (attrs != null && "Exsug07sPrtAdmissionLetter".equals(tmpName) == true)
           {
               for (int i = 0; i < attrs.getLength(); i++)
               {
                   attrName = attrs.getLocalName(i);
                   if ("".equals(attrName) == true)
                       attrName = attrs.getQName(i);
 
                   if ("noNamespaceSchemaLocation".equals(attrName) == true)
                   {
                       noNamespaceSchemaLocation = attrs.getValue(i);
                       break;
                   }
               }
           }
           else if (attrs != null && tmpName.equals("request") == true)
           {
               for (int i = 0; i < attrs.getLength(); i++)
               {
                   attrName = attrs.getLocalName(i);
                   if ("".equals(attrName) == true)
                       attrName = attrs.getQName(i);
 
                   if ("command".equals(attrName) == true)
                       setCommandSent(attrs.getValue(i));
                   else if ("clientId".equals(attrName) == true)
                       setClientId(attrs.getValue(i));
                   else if ("clientPassword".equals(attrName) == true)
                       setClientPassword(attrs.getValue(i));
                   else if ("nextLocation".equals(attrName) == true)
                       setNextLocation(attrs.getValue(i));
                   else if ("exitState".equals(attrName) == true)
                   {
                       try {
                           setExitStateSent(Integer.parseInt(attrs.getValue(i)));
                       } catch (NumberFormatException e) {
                           throw new org.xml.sax.SAXException(e);
                       }
                   }
                   else if ("dialect".equals(attrName) == true)
                       setDialect(attrs.getValue(i));
                   else if ("comCfg".equals(attrName) == true)
                       setComCfg(attrs.getValue(i));
                   else if ("serverLocation".equals(attrName) == true)
                   {
                       try {
                           setServerLocation(new java.net.URL(attrs.getValue(i)));
                       } catch (java.net.MalformedURLException e) {
                           throw new org.xml.sax.SAXException(e);
                       }
                   }
                   else if ("servletPath".equals(attrName) == true)
                       setServletPath(attrs.getValue(i));
                   else if ("fileEncoding".equals(attrName) == true)
                       setFileEncoding(attrs.getValue(i));
                   else if ("tracing".equals(attrName) == true &&
                            "true".equals(attrs.getValue(i)) == true)
                       setTracing(1);
                   else if ("tracing".equals(attrName) == true &&
                            "false".equals(attrs.getValue(i)) == true)
                       setTracing(0);
               }
           }
       }
 
       public void endElement(String namespaceURI,
                              String simpleName,
                              String qualifiedName) throws org.xml.sax.SAXException {
           try {
              if (elementNames[currentLevel].endsWith("InInternetFlagCsfStringsString1") == true) {
                  setInInternetFlagCsfStringsString1(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InFaxNameCsfStringsString132") == true) {
                  setInFaxNameCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InFaxNoCsfStringsString132") == true) {
                  setInFaxNoCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InToEmailAddressCsfStringsString132") == true) {
                  setInToEmailAddressCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InFromEmailAddressCsfStringsString132") == true) {
                  setInFromEmailAddressCsfStringsString132(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsWorkstationCode") == true) {
                  setInSecurityWsWorkstationCode(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InPracticalFlagWsPracticalsValuesString1") == true) {
                  setInPracticalFlagWsPracticalsValuesString1(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientVersionNumber") == true) {
                  setAsStringInCsfClientServerCommunicationsClientVersionNumber(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientRevisionNumber") == true) {
                  setAsStringInCsfClientServerCommunicationsClientRevisionNumber(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientDevelopmentPhase") == true) {
                  setInCsfClientServerCommunicationsClientDevelopmentPhase(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsAction") == true) {
                  setInCsfClientServerCommunicationsAction(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientIsGuiFlag") == true) {
                  setInCsfClientServerCommunicationsClientIsGuiFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientDate") == true) {
                  setAsStringInCsfClientServerCommunicationsClientDate(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientTime") == true) {
                  setAsStringInCsfClientServerCommunicationsClientTime(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientTimestamp") == true) {
                  setAsStringInCsfClientServerCommunicationsClientTimestamp(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientTransactionCode") == true) {
                  setInCsfClientServerCommunicationsClientTransactionCode(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsClientUserId") == true) {
                  setInCsfClientServerCommunicationsClientUserId(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsListLinkFlag") == true) {
                  setInCsfClientServerCommunicationsListLinkFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsMaintLinkFlag") == true) {
                  setInCsfClientServerCommunicationsMaintLinkFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InCsfClientServerCommunicationsFirstpassFlag") == true) {
                  setInCsfClientServerCommunicationsFirstpassFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsPrinterCode") == true) {
                  setInSecurityWsPrinterCode(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserNumber") == true) {
                  setAsStringInSecurityWsUserNumber(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserNumberOfLogonAttempts") == true) {
                  setAsStringInSecurityWsUserNumberOfLogonAttempts(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserLoggedOnFlag") == true) {
                  setInSecurityWsUserLoggedOnFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserInUsedFlag") == true) {
                  setInSecurityWsUserInUsedFlag(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserLastLogonDate") == true) {
                  setAsStringInSecurityWsUserLastLogonDate(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserName") == true) {
                  setInSecurityWsUserName(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserPersonnelNo") == true) {
                  setInSecurityWsUserPersonnelNo(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserPhoneNumber") == true) {
                  setInSecurityWsUserPhoneNumber(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InSecurityWsUserPassword") == true) {
                  setInSecurityWsUserPassword(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InWsStudentSurname") == true) {
                  setInWsStudentSurname(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InWsStudentInitials") == true) {
                  setInWsStudentInitials(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InStudentAnnualRecordMkStudentNr") == true) {
                  setAsStringInStudentAnnualRecordMkStudentNr(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InStudentStudyUnitExamYear") == true) {
                  setAsStringInStudentStudyUnitExamYear(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InStudentStudyUnitMkExamPeriod") == true) {
                  setAsStringInStudentStudyUnitMkExamPeriod(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment1CsfStringsString100") == true) {
                  setInComment1CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment2CsfStringsString100") == true) {
                  setInComment2CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment3CsfStringsString100") == true) {
                  setInComment3CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment4CsfStringsString100") == true) {
                  setInComment4CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment5CsfStringsString100") == true) {
                  setInComment5CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment6CsfStringsString100") == true) {
                  setInComment6CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment7CsfStringsString100") == true) {
                  setInComment7CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment8CsfStringsString100") == true) {
                  setInComment8CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment9CsfStringsString100") == true) {
                  setInComment9CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment10CsfStringsString100") == true) {
                  setInComment10CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment11CsfStringsString100") == true) {
                  setInComment11CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InComment12CsfStringsString100") == true) {
                  setInComment12CsfStringsString100(textBuffer.toString());
              }
              else if (elementNames[currentLevel].endsWith("InInvigilatorAddressCsfStringsString1") == true) {
                  setInInvigilatorAddressCsfStringsString1(textBuffer.toString());
              }
           } catch (PropertyVetoException e) {
               throw new org.xml.sax.SAXException(e);
           }
           numberOfChildren[currentLevel] = 0;
           elementNames[currentLevel] = "";
           currentLevel--;
       }
 
       public void characters(char buffer[],
                              int offset,
                              int length) throws org.xml.sax.SAXException {
           textBuffer.append(new String(buffer, offset, length));
       }
   }

};
