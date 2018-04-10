package Exerp01h.Abean;
 
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
public class Exerp01sPrtResultsAndTimetab  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Exerp01sPrtResultsAndTimetab");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Exerp01sPrtResultsAndTimetab() {
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
 
 
   private Exerp01sPrtResultsAndTimetabOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Exerp01sPrtResultsAndTimetabOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doExerp01sPrtResultsAndTimetabOperation();
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
      private Exerp01sPrtResultsAndTimetab rP;
      operListener(Exerp01sPrtResultsAndTimetab r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Exerp01sPrtResultsAndTimetab", "Listener heard that Exerp01sPrtResultsAndTimetabOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Exerp01sPrtResultsAndTimetab ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Exerp01sPrtResultsAndTimetab");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Exerp01sPrtResultsAndTimetab ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Exerp01sPrtResultsAndTimetab";
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
      importView.InWsStudentDeceasedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      importView.InWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentDisciplinaryIncident = ShortAttr.valueOf((short)0);
      importView.InWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      importView.InWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAccessRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentResultBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutResultGroup_MA = IntAttr.getDefaultValue();
      exportView.OutWsStudentDeceasedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentDisciplinaryIncident = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAccessRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentNumberRestrictedFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentResultBlockFlag = FixedStringAttr.valueOf("N", 1);
   }

   Exerp01h.EXERP01S_IA importView = Exerp01h.EXERP01S_IA.getInstance();
   Exerp01h.EXERP01S_OA exportView = Exerp01h.EXERP01S_OA.getInstance();
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
 
   public int getInExamDetailStudentAnnualRecordMkStudentNr() {
      return importView.InExamDetailStudentAnnualRecordMkStudentNr;
   }
   public void setInExamDetailStudentAnnualRecordMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkStudentNr", null, null));
      }
      importView.InExamDetailStudentAnnualRecordMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInExamDetailStudentAnnualRecordMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkStudentNr", null, null));
      }
      try {
          setInExamDetailStudentAnnualRecordMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkStudentNr", null, null));
      }
   }
 
   public short getInExamDetailStudentAnnualRecordMkAcademicYear() {
      return importView.InExamDetailStudentAnnualRecordMkAcademicYear;
   }
   public void setInExamDetailStudentAnnualRecordMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkAcademicYear", null, null));
      }
      importView.InExamDetailStudentAnnualRecordMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamDetailStudentAnnualRecordMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkAcademicYear", null, null));
      }
      try {
          setInExamDetailStudentAnnualRecordMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkAcademicYear", null, null));
      }
   }
 
   public short getInExamDetailStudentAnnualRecordMkAcademicPeriod() {
      return importView.InExamDetailStudentAnnualRecordMkAcademicPeriod;
   }
   public void setInExamDetailStudentAnnualRecordMkAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      importView.InExamDetailStudentAnnualRecordMkAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamDetailStudentAnnualRecordMkAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      try {
          setInExamDetailStudentAnnualRecordMkAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkAcademicPeriod", null, null));
      }
   }
 
   public String getInExamDetailStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(importView.InExamDetailStudentAnnualRecordStatusCode, 2);
   }
   public void setInExamDetailStudentAnnualRecordStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordStatusCode", null, null));
      }
      importView.InExamDetailStudentAnnualRecordStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInExamDetailStudentAnnualRecordLibraryAccessLevel() {
      return importView.InExamDetailStudentAnnualRecordLibraryAccessLevel;
   }
   public void setInExamDetailStudentAnnualRecordLibraryAccessLevel(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordLibraryAccessLevel has more than 1 digits.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      importView.InExamDetailStudentAnnualRecordLibraryAccessLevel = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamDetailStudentAnnualRecordLibraryAccessLevel(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      try {
          setInExamDetailStudentAnnualRecordLibraryAccessLevel(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordLibraryAccessLevel", null, null));
      }
   }
 
   public short getInExamDetailStudentAnnualRecordSpecialLibraryAccessLevel() {
      return importView.InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel;
   }
   public void setInExamDetailStudentAnnualRecordSpecialLibraryAccessLevel(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel has more than 1 digits.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      importView.InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel = ShortAttr.valueOf(s);
   }
   public void setAsStringInExamDetailStudentAnnualRecordSpecialLibraryAccessLevel(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      try {
          setInExamDetailStudentAnnualRecordSpecialLibraryAccessLevel(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
   }
 
   public String getInExamDetailStudentAnnualRecordMkHighestQualificationCode() {
      return FixedStringAttr.valueOf(importView.InExamDetailStudentAnnualRecordMkHighestQualificationCode, 5);
   }
   public void setInExamDetailStudentAnnualRecordMkHighestQualificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InExamDetailStudentAnnualRecordMkHighestQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InExamDetailStudentAnnualRecordMkHighestQualificationCode", null, null));
      }
      importView.InExamDetailStudentAnnualRecordMkHighestQualificationCode = FixedStringAttr.valueOf(s, (short)5);
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
      Exerp01sPrtResultsAndTimetabWsStudentDeceasedFlagPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentDeceasedFlagPropertyEditor();
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
      Exerp01sPrtResultsAndTimetabWsStudentLibraryBlackListPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentLibraryBlackListPropertyEditor();
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
      Exerp01sPrtResultsAndTimetabWsStudentExamFeesDebtFlagPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentExamFeesDebtFlagPropertyEditor();
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
      Exerp01sPrtResultsAndTimetabWsStudentDisciplinaryIncidentPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentDisciplinaryIncidentPropertyEditor();
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
 
   public short getInWsStudentPhasedOutStatus() {
      return importView.InWsStudentPhasedOutStatus;
   }
   public void setInWsStudentPhasedOutStatus(short s)
      throws PropertyVetoException {
      Exerp01sPrtResultsAndTimetabWsStudentPhasedOutStatusPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentPhasedOutStatusPropertyEditor();
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
      Exerp01sPrtResultsAndTimetabWsStudentFinancialBlockFlagPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentFinancialBlockFlagPropertyEditor();
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
 
   public String getInWsStudentAccessRestrictedFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAccessRestrictedFlag, 1);
   }
   public void setInWsStudentAccessRestrictedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Exerp01sPrtResultsAndTimetabWsStudentAccessRestrictedFlagPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentAccessRestrictedFlagPropertyEditor();
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
      Exerp01sPrtResultsAndTimetabWsStudentNumberRestrictedFlagPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentNumberRestrictedFlagPropertyEditor();
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
 
   public String getInWsStudentResultBlockFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentResultBlockFlag, 1);
   }
   public void setInWsStudentResultBlockFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Exerp01sPrtResultsAndTimetabWsStudentResultBlockFlagPropertyEditor pe = new Exerp01sPrtResultsAndTimetabWsStudentResultBlockFlagPropertyEditor();
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
 
   public String getInSecurityWsActionCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsActionCode, 2);
   }
   public void setInSecurityWsActionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InSecurityWsActionCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InSecurityWsActionCode", null, null));
      }
      importView.InSecurityWsActionCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInSecurityWsActionDescription() {
      return FixedStringAttr.valueOf(importView.InSecurityWsActionDescription, 10);
   }
   public void setInSecurityWsActionDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsActionDescription must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsActionDescription", null, null));
      }
      importView.InSecurityWsActionDescription = FixedStringAttr.valueOf(s, (short)10);
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
 
   public String getInSecurityWsWorkstationCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsWorkstationCode, 10);
   }
   public void setInSecurityWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsWorkstationCode", null, null));
      }
      importView.InSecurityWsWorkstationCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInIpAddressCsfStringsString15() {
      return FixedStringAttr.valueOf(importView.InIpAddressCsfStringsString15, 15);
   }
   public void setInIpAddressCsfStringsString15(String s)
      throws PropertyVetoException {
      if (s.length() > 15) {
         throw new PropertyVetoException("InIpAddressCsfStringsString15 must be <= 15 characters.",
               new PropertyChangeEvent (this, "InIpAddressCsfStringsString15", null, null));
      }
      importView.InIpAddressCsfStringsString15 = FixedStringAttr.valueOf(s, (short)15);
   }
 
   public String getOutLine1CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutLine1CsfStringsString100, 100);
   }
 
   public String getOutLine2CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutLine2CsfStringsString100, 100);
   }
 
   public String getOutLine3CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutLine3CsfStringsString100, 100);
   }
 
   public String getOutLine4CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutLine4CsfStringsString100, 100);
   }
 
   public String getOutLine5CsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutLine5CsfStringsString100, 100);
   }
 
   public String getOutFinalOrProvCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutFinalOrProvCsfStringsString1, 1);
   }
 
   public String getOutFromEmailCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFromEmailCsfStringsString132, 132);
   }
 
   public String getOutToEmailCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutToEmailCsfStringsString132, 132);
   }
 
   public String getOutEmailWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutEmailWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutSubjectCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutSubjectCsfStringsString132, 132);
   }
 
   public String getOutEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutEmailOrFaxCsfStringsString1, 1);
   }
 
   public String getOutFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNoCsfStringsString132, 132);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public final int OutResultGroupMax = 40;
   public short getOutResultGroupCount() {
      return (short)(exportView.OutResultGroup_MA);
   };
 
   public String getOutGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGRemarkCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGRemarkCsfStringsString1[index], 1);
   }
 
   public String getOutGAcademicRecordStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAcademicRecordStudyUnitMkStudyUnitCode[index], 7);
   }
 
   public String getOutGFinalMarkCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGFinalMarkCsfStringsString3[index], 3);
   }
 
   public String getOutGRemarksCsfStringsString50(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGRemarksCsfStringsString50[index], 50);
   }
 
   public short getOutGStudentExamResultAdjustmentMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultAdjustmentMark[index];
   }
 
   public short getOutGStudentExamResultFinalMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultFinalMark[index];
   }
 
   public short getOutGStudentExamResultMkResultTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultMkResultTypeCode[index];
   }
 
   public short getOutGStudentExamResultSubminAdjust(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultSubminAdjust[index];
   }
 
   public double getOutGStudentExamResultYearMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultYearMark[index];
   }
 
   public double getOutGStudentExamResultPortfolioMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultPortfolioMark[index];
   }
 
   public double getOutGStudentExamResultExamMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGStudentExamResultExamMark[index];
   }
 
   public String getOutGStudentExamResultExamMarkOverrite(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentExamResultExamMarkOverrite[index], 1);
   }
 
   public short getOutGP1StudentExamPaperResultNr(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP1StudentExamPaperResultNr[index];
   }
 
   public double getOutGP1StudentExamPaperResultPaperMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP1StudentExamPaperResultPaperMark[index];
   }
 
   public double getOutGP1StudentExamPaperResultMarkreadingMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP1StudentExamPaperResultMarkreadingMark[index];
   }
 
   public double getOutGP1StudentExamPaperResultWrittenMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP1StudentExamPaperResultWrittenMark[index];
   }
 
   public String getOutGP1StudentExamPaperResultOralFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGP1StudentExamPaperResultOralFlag[index], 1);
   }
 
   public short getOutGP2StudentExamPaperResultNr(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP2StudentExamPaperResultNr[index];
   }
 
   public double getOutGP2StudentExamPaperResultPaperMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP2StudentExamPaperResultPaperMark[index];
   }
 
   public double getOutGP2StudentExamPaperResultMarkreadingMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP2StudentExamPaperResultMarkreadingMark[index];
   }
 
   public double getOutGP2StudentExamPaperResultWrittenMark(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP2StudentExamPaperResultWrittenMark[index];
   }
 
   public String getOutGP2StudentExamPaperResultOralFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGP2StudentExamPaperResultOralFlag[index], 1);
   }
 
   public double getOutGP1ExamPeriodDatePaperWeight(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP1ExamPeriodDatePaperWeight[index];
   }
 
   public short getOutGP1ExamPeriodDatePaperSubmin(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP1ExamPeriodDatePaperSubmin[index];
   }
 
   public double getOutGP2ExamPeriodDatePaperWeight(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP2ExamPeriodDatePaperWeight[index];
   }
 
   public short getOutGP2ExamPeriodDatePaperSubmin(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGP2ExamPeriodDatePaperSubmin[index];
   }
 
   public double getOutGWsFinalMarkCalculationYearMarkWeight(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGWsFinalMarkCalculationYearMarkWeight[index];
   }
 
   public double getOutGWsFinalMarkCalculationExaminationWeight(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGWsFinalMarkCalculationExaminationWeight[index];
   }
 
   public double getOutGWsFinalMarkCalculationPortfolioWeight(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGWsFinalMarkCalculationPortfolioWeight[index];
   }
 
   public short getOutGWsFinalMarkCalculationYmSubminimum(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGWsFinalMarkCalculationYmSubminimum[index];
   }
 
   public short getOutGWsFinalMarkCalculationPfSubminimum(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGWsFinalMarkCalculationPfSubminimum[index];
   }
 
   public short getOutGWsFinalMarkCalculationXamSubminimum(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return exportView.OutGWsFinalMarkCalculationXamSubminimum[index];
   }
 
   public String getOutGWsFinalMarkCalculationCalcYmSuppl(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsFinalMarkCalculationCalcYmSuppl[index], 1);
   }
 
   public String getOutGWsFinalMarkCalculationCalcYmSubmin(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsFinalMarkCalculationCalcYmSubmin[index], 1);
   }
 
   public String getOutGMessageCsfStringsString100(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGMessageCsfStringsString100[index], 100);
   }
 
   public String getOutGShowDetailsIefSuppliedFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (39 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 39, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGShowDetailsIefSuppliedFlag[index], 1);
   }
 
   public String getOutWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutWizfuncReportingControlPrinterCode);
   }
 
   public String getOutWizfuncReportingControlWizfuncReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWizfuncReportingControlWizfuncReturnCode, 8);
   }
 
   public String getOutWizfuncReportingControlWizfuncReturnMessage() {
      return StringAttr.valueOf(exportView.OutWizfuncReportingControlWizfuncReturnMessage);
   }
 
   public int getOutExamDetailStudentAnnualRecordMkStudentNr() {
      return exportView.OutExamDetailStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutExamDetailStudentAnnualRecordMkAcademicYear() {
      return exportView.OutExamDetailStudentAnnualRecordMkAcademicYear;
   }
 
   public short getOutExamDetailStudentAnnualRecordMkAcademicPeriod() {
      return exportView.OutExamDetailStudentAnnualRecordMkAcademicPeriod;
   }
 
   public String getOutExamDetailStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutExamDetailStudentAnnualRecordStatusCode, 2);
   }
 
   public short getOutExamDetailStudentAnnualRecordLibraryAccessLevel() {
      return exportView.OutExamDetailStudentAnnualRecordLibraryAccessLevel;
   }
 
   public short getOutExamDetailStudentAnnualRecordSpecialLibraryAccessLevel() {
      return exportView.OutExamDetailStudentAnnualRecordSpecialLibraryAccessLevel;
   }
 
   public String getOutExamDetailStudentAnnualRecordMkHighestQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutExamDetailStudentAnnualRecordMkHighestQualificationCode, 5);
   }
 
   public String getOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMethodResultReturnCode, 2);
   }
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
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
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
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
 
   public short getOutWsStudentPhasedOutStatus() {
      return exportView.OutWsStudentPhasedOutStatus;
   }
 
   public String getOutWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFinancialBlockFlag, 1);
   }
 
   public String getOutWsStudentAccessRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAccessRestrictedFlag, 1);
   }
 
   public String getOutWsStudentNumberRestrictedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentNumberRestrictedFlag, 1);
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
 
   public int getOutStudentAnnualRecordMkStudentNr() {
      return exportView.OutStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutStudentAnnualRecordMkAcademicYear() {
      return exportView.OutStudentAnnualRecordMkAcademicYear;
   }
 
   public short getOutStudentAnnualRecordMkAcademicPeriod() {
      return exportView.OutStudentAnnualRecordMkAcademicPeriod;
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
 
   public String getOutSecurityWsActionCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsActionCode, 2);
   }
 
   public String getOutSecurityWsActionDescription() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsActionDescription, 10);
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
 
   public String getOutSecurityWsWorkstationCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsWorkstationCode, 10);
   }
 
};
