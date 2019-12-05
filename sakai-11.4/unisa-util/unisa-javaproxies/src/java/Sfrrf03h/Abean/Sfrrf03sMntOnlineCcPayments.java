package Sfrrf03h.Abean;
 
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
public class Sfrrf03sMntOnlineCcPayments  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Sfrrf03sMntOnlineCcPayments");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Sfrrf03sMntOnlineCcPayments() {
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
 
 
   private Sfrrf03sMntOnlineCcPaymentsOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Sfrrf03sMntOnlineCcPaymentsOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSfrrf03sMntOnlineCcPaymentsOperation();
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
      private Sfrrf03sMntOnlineCcPayments rP;
      operListener(Sfrrf03sMntOnlineCcPayments r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Sfrrf03sMntOnlineCcPayments", "Listener heard that Sfrrf03sMntOnlineCcPaymentsOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Sfrrf03sMntOnlineCcPayments ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Sfrrf03sMntOnlineCcPayments");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Sfrrf03sMntOnlineCcPayments ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Sfrrf03sMntOnlineCcPayments";
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
      importView.InWsStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAnnualRecordBursaryType = ShortAttr.valueOf((short)01);
      importView.InWsStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAnnualRecordLibraryAccessLevel = ShortAttr.valueOf((short)0);
      importView.InWsStudentAnnualRecordSpecialLibraryAccessLevel = ShortAttr.valueOf((short)0);
      importView.InWsStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf("N", 1);
      importView.InBundleDocumentAutoSignatureFlag = FixedStringAttr.valueOf("N", 1);
      importView.InBundleDocumentSalaryFlag = FixedStringAttr.valueOf(" ", 1);
      importView.InWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      importView.InWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      importView.InBundlePostGradFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutStudentAccountUnclaimedCreditFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAnnualRecordBursaryType = ShortAttr.valueOf((short)01);
      exportView.OutWsStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAnnualRecordLibraryAccessLevel = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentAnnualRecordSpecialLibraryAccessLevel = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutBundleDocumentAutoSignatureFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutBundleDocumentSalaryFlag = FixedStringAttr.valueOf(" ", 1);
      exportView.OutWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutBundlePostGradFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutTmpAllocGroup_MA = IntAttr.getDefaultValue();
      exportView.OutWsQualificationInUseFlag = FixedStringAttr.valueOf("Y", 1);
   }

   Sfrrf03h.SFRRF03S_IA importView = Sfrrf03h.SFRRF03S_IA.getInstance();
   Sfrrf03h.SFRRF03S_OA exportView = Sfrrf03h.SFRRF03S_OA.getInstance();
   public String getInLdDtCrIndCsfStringsString6() {
      return FixedStringAttr.valueOf(importView.InLdDtCrIndCsfStringsString6, 6);
   }
   public void setInLdDtCrIndCsfStringsString6(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InLdDtCrIndCsfStringsString6 must be <= 6 characters.",
               new PropertyChangeEvent (this, "InLdDtCrIndCsfStringsString6", null, null));
      }
      importView.InLdDtCrIndCsfStringsString6 = FixedStringAttr.valueOf(s, (short)6);
   }
 
   public double getInLdDueStudentAccountBalance() {
      return importView.InLdDueStudentAccountBalance;
   }
   public void setInLdDueStudentAccountBalance(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InLdDueStudentAccountBalance has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InLdDueStudentAccountBalance", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InLdDueStudentAccountBalance has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InLdDueStudentAccountBalance", null, null));
      }
      importView.InLdDueStudentAccountBalance = DoubleAttr.valueOf(s);
   }
   public void setAsStringInLdDueStudentAccountBalance(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLdDueStudentAccountBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLdDueStudentAccountBalance", null, null));
      }
      try {
          setInLdDueStudentAccountBalance(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLdDueStudentAccountBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLdDueStudentAccountBalance", null, null));
      }
   }
 
   public double getInLdBundleDocumentTotalAmount() {
      return importView.InLdBundleDocumentTotalAmount;
   }
   public void setInLdBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InLdBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InLdBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InLdBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InLdBundleDocumentTotalAmount", null, null));
      }
      importView.InLdBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInLdBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLdBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLdBundleDocumentTotalAmount", null, null));
      }
      try {
          setInLdBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLdBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLdBundleDocumentTotalAmount", null, null));
      }
   }
 
   public double getInApplicationBundleDocumentTotalAmount() {
      return importView.InApplicationBundleDocumentTotalAmount;
   }
   public void setInApplicationBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InApplicationBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InApplicationBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InApplicationBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InApplicationBundleDocumentTotalAmount", null, null));
      }
      importView.InApplicationBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInApplicationBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InApplicationBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InApplicationBundleDocumentTotalAmount", null, null));
      }
      try {
          setInApplicationBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InApplicationBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InApplicationBundleDocumentTotalAmount", null, null));
      }
   }
 
   public double getInWsSmartCardDataBalance() {
      return importView.InWsSmartCardDataBalance;
   }
   public void setInWsSmartCardDataBalance(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InWsSmartCardDataBalance has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InWsSmartCardDataBalance", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000000.0) {
         throw new PropertyVetoException("InWsSmartCardDataBalance has more than 12 integral digits.",
               new PropertyChangeEvent (this, "InWsSmartCardDataBalance", null, null));
      }
      importView.InWsSmartCardDataBalance = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsSmartCardDataBalance(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsSmartCardDataBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsSmartCardDataBalance", null, null));
      }
      try {
          setInWsSmartCardDataBalance(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsSmartCardDataBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsSmartCardDataBalance", null, null));
      }
   }
 
   public String getInCvvWsPostilionTranStartCvvNr() {
      return FixedStringAttr.valueOf(importView.InCvvWsPostilionTranStartCvvNr, 3);
   }
   public void setInCvvWsPostilionTranStartCvvNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InCvvWsPostilionTranStartCvvNr must be <= 3 characters.",
               new PropertyChangeEvent (this, "InCvvWsPostilionTranStartCvvNr", null, null));
      }
      importView.InCvvWsPostilionTranStartCvvNr = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInRetryXtnIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InRetryXtnIefSuppliedFlag, 1);
   }
   public void setInRetryXtnIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsIefSuppliedFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InRetryXtnIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InRetryXtnIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InRetryXtnIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InRetryXtnIefSuppliedFlag", null, null));
      }
      importView.InRetryXtnIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInSmartcardAndApplCostWsAcademicYearSmartcardCost() {
      return importView.InSmartcardAndApplCostWsAcademicYearSmartcardCost;
   }
   public void setInSmartcardAndApplCostWsAcademicYearSmartcardCost(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearSmartcardCost has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearSmartcardCost", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearSmartcardCost has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearSmartcardCost", null, null));
      }
      importView.InSmartcardAndApplCostWsAcademicYearSmartcardCost = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmartcardAndApplCostWsAcademicYearSmartcardCost(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearSmartcardCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearSmartcardCost", null, null));
      }
      try {
          setInSmartcardAndApplCostWsAcademicYearSmartcardCost(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearSmartcardCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearSmartcardCost", null, null));
      }
   }
 
   public double getInSmartcardAndApplCostWsAcademicYearApplicationCost() {
      return importView.InSmartcardAndApplCostWsAcademicYearApplicationCost;
   }
   public void setInSmartcardAndApplCostWsAcademicYearApplicationCost(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearApplicationCost has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearApplicationCost", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearApplicationCost has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearApplicationCost", null, null));
      }
      importView.InSmartcardAndApplCostWsAcademicYearApplicationCost = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmartcardAndApplCostWsAcademicYearApplicationCost(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearApplicationCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearApplicationCost", null, null));
      }
      try {
          setInSmartcardAndApplCostWsAcademicYearApplicationCost(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmartcardAndApplCostWsAcademicYearApplicationCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplCostWsAcademicYearApplicationCost", null, null));
      }
   }
 
   public double getInMrReApplicationCostMrFlagAmount() {
      return importView.InMrReApplicationCostMrFlagAmount;
   }
   public void setInMrReApplicationCostMrFlagAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InMrReApplicationCostMrFlagAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMrReApplicationCostMrFlagAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InMrReApplicationCostMrFlagAmount has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InMrReApplicationCostMrFlagAmount", null, null));
      }
      importView.InMrReApplicationCostMrFlagAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMrReApplicationCostMrFlagAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMrReApplicationCostMrFlagAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrReApplicationCostMrFlagAmount", null, null));
      }
      try {
          setInMrReApplicationCostMrFlagAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMrReApplicationCostMrFlagAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrReApplicationCostMrFlagAmount", null, null));
      }
   }
 
   public double getInMrFirstApplicationCostMrFlagAmount() {
      return importView.InMrFirstApplicationCostMrFlagAmount;
   }
   public void setInMrFirstApplicationCostMrFlagAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InMrFirstApplicationCostMrFlagAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMrFirstApplicationCostMrFlagAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InMrFirstApplicationCostMrFlagAmount has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InMrFirstApplicationCostMrFlagAmount", null, null));
      }
      importView.InMrFirstApplicationCostMrFlagAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMrFirstApplicationCostMrFlagAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMrFirstApplicationCostMrFlagAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrFirstApplicationCostMrFlagAmount", null, null));
      }
      try {
          setInMrFirstApplicationCostMrFlagAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMrFirstApplicationCostMrFlagAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrFirstApplicationCostMrFlagAmount", null, null));
      }
   }
 
   public int getInWsAddressV2ReferenceNo() {
      return importView.InWsAddressV2ReferenceNo;
   }
   public void setInWsAddressV2ReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsAddressV2ReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2ReferenceNo", null, null));
      }
      importView.InWsAddressV2ReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2ReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ReferenceNo", null, null));
      }
      try {
          setInWsAddressV2ReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ReferenceNo", null, null));
      }
   }
 
   public short getInWsAddressV2Type() {
      return importView.InWsAddressV2Type;
   }
   public void setInWsAddressV2Type(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsAddressV2Type has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2Type", null, null));
      }
      importView.InWsAddressV2Type = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2Type(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Type", null, null));
      }
      try {
          setInWsAddressV2Type(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Type", null, null));
      }
   }
 
   public short getInWsAddressV2Category() {
      return importView.InWsAddressV2Category;
   }
   public void setInWsAddressV2Category(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsAddressV2Category has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2Category", null, null));
      }
      importView.InWsAddressV2Category = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2Category(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Category", null, null));
      }
      try {
          setInWsAddressV2Category(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2Category", null, null));
      }
   }
 
   public String getInWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2HomeNumber, 28);
   }
   public void setInWsAddressV2HomeNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2HomeNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2HomeNumber", null, null));
      }
      importView.InWsAddressV2HomeNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CellNumber, 20);
   }
   public void setInWsAddressV2CellNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
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
 
   public String getInWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine1, 28);
   }
   public void setInWsAddressV2AddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine1", null, null));
      }
      importView.InWsAddressV2AddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine2, 28);
   }
   public void setInWsAddressV2AddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine2", null, null));
      }
      importView.InWsAddressV2AddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine3, 28);
   }
   public void setInWsAddressV2AddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine3", null, null));
      }
      importView.InWsAddressV2AddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine4, 28);
   }
   public void setInWsAddressV2AddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine4", null, null));
      }
      importView.InWsAddressV2AddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine5, 28);
   }
   public void setInWsAddressV2AddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine5", null, null));
      }
      importView.InWsAddressV2AddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressLine6, 28);
   }
   public void setInWsAddressV2AddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2AddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressLine6", null, null));
      }
      importView.InWsAddressV2AddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInWsAddressV2PostalCode() {
      return importView.InWsAddressV2PostalCode;
   }
   public void setInWsAddressV2PostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsAddressV2PostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2PostalCode", null, null));
      }
      importView.InWsAddressV2PostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2PostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2PostalCode", null, null));
      }
      try {
          setInWsAddressV2PostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2PostalCode", null, null));
      }
   }
 
   public String getInSourceBundleMkBundleOrigin() {
      return FixedStringAttr.valueOf(importView.InSourceBundleMkBundleOrigin, 2);
   }
   public void setInSourceBundleMkBundleOrigin(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InSourceBundleMkBundleOrigin must be <= 2 characters.",
               new PropertyChangeEvent (this, "InSourceBundleMkBundleOrigin", null, null));
      }
      importView.InSourceBundleMkBundleOrigin = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public int getInSourceBundleMkUserCode() {
      return importView.InSourceBundleMkUserCode;
   }
   public void setInSourceBundleMkUserCode(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSourceBundleMkUserCode has more than 5 digits.",
               new PropertyChangeEvent (this, "InSourceBundleMkUserCode", null, null));
      }
      importView.InSourceBundleMkUserCode = IntAttr.valueOf(s);
   }
   public void setAsStringInSourceBundleMkUserCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSourceBundleMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSourceBundleMkUserCode", null, null));
      }
      try {
          setInSourceBundleMkUserCode(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSourceBundleMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSourceBundleMkUserCode", null, null));
      }
   }
 
   public String getInStudentAccountClassificationCode() {
      return FixedStringAttr.valueOf(importView.InStudentAccountClassificationCode, 2);
   }
   public void setInStudentAccountClassificationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentAccountClassificationCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentAccountClassificationCode", null, null));
      }
      importView.InStudentAccountClassificationCode = FixedStringAttr.valueOf(s, (short)2);
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
 
   public int getInWsPostilionTranStartPosSystemIdentifier() {
      return importView.InWsPostilionTranStartPosSystemIdentifier;
   }
   public void setInWsPostilionTranStartPosSystemIdentifier(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartPosSystemIdentifier has more than 6 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartPosSystemIdentifier", null, null));
      }
      importView.InWsPostilionTranStartPosSystemIdentifier = IntAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartPosSystemIdentifier(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartPosSystemIdentifier is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosSystemIdentifier", null, null));
      }
      try {
          setInWsPostilionTranStartPosSystemIdentifier(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartPosSystemIdentifier is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosSystemIdentifier", null, null));
      }
   }
 
   public short getInWsPostilionTranStartMessageType() {
      return importView.InWsPostilionTranStartMessageType;
   }
   public void setInWsPostilionTranStartMessageType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsPostilionTranStartMessageType has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartMessageType", null, null));
      }
      importView.InWsPostilionTranStartMessageType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartMessageType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartMessageType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartMessageType", null, null));
      }
      try {
          setInWsPostilionTranStartMessageType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartMessageType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartMessageType", null, null));
      }
   }
 
   public short getInWsPostilionTranStartTestTransactionInd() {
      return importView.InWsPostilionTranStartTestTransactionInd;
   }
   public void setInWsPostilionTranStartTestTransactionInd(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsPostilionTranStartTestTransactionInd has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartTestTransactionInd", null, null));
      }
      importView.InWsPostilionTranStartTestTransactionInd = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartTestTransactionInd(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartTestTransactionInd is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartTestTransactionInd", null, null));
      }
      try {
          setInWsPostilionTranStartTestTransactionInd(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartTestTransactionInd is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartTestTransactionInd", null, null));
      }
   }
 
   public short getInWsPostilionTranStartTransactionType() {
      return importView.InWsPostilionTranStartTransactionType;
   }
   public void setInWsPostilionTranStartTransactionType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsPostilionTranStartTransactionType has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartTransactionType", null, null));
      }
      importView.InWsPostilionTranStartTransactionType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartTransactionType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartTransactionType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartTransactionType", null, null));
      }
      try {
          setInWsPostilionTranStartTransactionType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartTransactionType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartTransactionType", null, null));
      }
   }
 
   public int getInWsPostilionTranStartSystemTraceAuditNr() {
      return importView.InWsPostilionTranStartSystemTraceAuditNr;
   }
   public void setInWsPostilionTranStartSystemTraceAuditNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartSystemTraceAuditNr has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartSystemTraceAuditNr", null, null));
      }
      importView.InWsPostilionTranStartSystemTraceAuditNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartSystemTraceAuditNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartSystemTraceAuditNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartSystemTraceAuditNr", null, null));
      }
      try {
          setInWsPostilionTranStartSystemTraceAuditNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartSystemTraceAuditNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartSystemTraceAuditNr", null, null));
      }
   }
 
   public double getInWsPostilionTranStartRetrievalReferenceNr() {
      return importView.InWsPostilionTranStartRetrievalReferenceNr;
   }
   public void setInWsPostilionTranStartRetrievalReferenceNr(double s)
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
         throw new PropertyVetoException("InWsPostilionTranStartRetrievalReferenceNr has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartRetrievalReferenceNr", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartRetrievalReferenceNr has more than 12 integral digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartRetrievalReferenceNr", null, null));
      }
      importView.InWsPostilionTranStartRetrievalReferenceNr = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartRetrievalReferenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartRetrievalReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartRetrievalReferenceNr", null, null));
      }
      try {
          setInWsPostilionTranStartRetrievalReferenceNr(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartRetrievalReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartRetrievalReferenceNr", null, null));
      }
   }
 
   public short getInWsPostilionTranStartAccountType() {
      return importView.InWsPostilionTranStartAccountType;
   }
   public void setInWsPostilionTranStartAccountType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsPostilionTranStartAccountType has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartAccountType", null, null));
      }
      importView.InWsPostilionTranStartAccountType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartAccountType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartAccountType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartAccountType", null, null));
      }
      try {
          setInWsPostilionTranStartAccountType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartAccountType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartAccountType", null, null));
      }
   }
 
   public int getInWsPostilionTranStartPosJournalNr() {
      return importView.InWsPostilionTranStartPosJournalNr;
   }
   public void setInWsPostilionTranStartPosJournalNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartPosJournalNr has more than 6 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartPosJournalNr", null, null));
      }
      importView.InWsPostilionTranStartPosJournalNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartPosJournalNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartPosJournalNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosJournalNr", null, null));
      }
      try {
          setInWsPostilionTranStartPosJournalNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartPosJournalNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosJournalNr", null, null));
      }
   }
 
   public short getInWsPostilionTranStartPosLaneNr() {
      return importView.InWsPostilionTranStartPosLaneNr;
   }
   public void setInWsPostilionTranStartPosLaneNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartPosLaneNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartPosLaneNr", null, null));
      }
      importView.InWsPostilionTranStartPosLaneNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartPosLaneNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartPosLaneNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosLaneNr", null, null));
      }
      try {
          setInWsPostilionTranStartPosLaneNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartPosLaneNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosLaneNr", null, null));
      }
   }
 
   public int getInWsPostilionTranStartPosOperatorId() {
      return importView.InWsPostilionTranStartPosOperatorId;
   }
   public void setInWsPostilionTranStartPosOperatorId(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartPosOperatorId has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartPosOperatorId", null, null));
      }
      importView.InWsPostilionTranStartPosOperatorId = IntAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartPosOperatorId(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartPosOperatorId is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosOperatorId", null, null));
      }
      try {
          setInWsPostilionTranStartPosOperatorId(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartPosOperatorId is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPosOperatorId", null, null));
      }
   }
 
   public String getInWsPostilionTranStartLocalDate() {
      return FixedStringAttr.valueOf(importView.InWsPostilionTranStartLocalDate, 4);
   }
   public void setInWsPostilionTranStartLocalDate(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsPostilionTranStartLocalDate must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartLocalDate", null, null));
      }
      importView.InWsPostilionTranStartLocalDate = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public Calendar getInWsPostilionTranStartLocalTime() {
      return TimeAttr.toCalendar(importView.InWsPostilionTranStartLocalTime);
   }
   public int getAsIntInWsPostilionTranStartLocalTime() {
      return TimeAttr.toInt(importView.InWsPostilionTranStartLocalTime);
   }
   public void setInWsPostilionTranStartLocalTime(Calendar s)
      throws PropertyVetoException {
      importView.InWsPostilionTranStartLocalTime = TimeAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartLocalTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsPostilionTranStartLocalTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimeFormatter.parse(s.length() > 6 ? s.substring(0, 6) : s));
            setInWsPostilionTranStartLocalTime(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsPostilionTranStartLocalTime has an invalid format (HHmmss).",
                  new PropertyChangeEvent (this, "InWsPostilionTranStartLocalTime", null, null));
         }
      }
   }
   public void setAsIntInWsPostilionTranStartLocalTime(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 6)
      {
         temp = "000000".substring(temp.length()) + temp;
      }
      setAsStringInWsPostilionTranStartLocalTime(temp);
   }
 
   public double getInWsPostilionTranStartTotalAmount() {
      return importView.InWsPostilionTranStartTotalAmount;
   }
   public void setInWsPostilionTranStartTotalAmount(double s)
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
         throw new PropertyVetoException("InWsPostilionTranStartTotalAmount has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartTotalAmount has more than 12 integral digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartTotalAmount", null, null));
      }
      importView.InWsPostilionTranStartTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartTotalAmount", null, null));
      }
      try {
          setInWsPostilionTranStartTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartTotalAmount", null, null));
      }
   }
 
   public double getInWsPostilionTranStartCashAmount() {
      return importView.InWsPostilionTranStartCashAmount;
   }
   public void setInWsPostilionTranStartCashAmount(double s)
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
         throw new PropertyVetoException("InWsPostilionTranStartCashAmount has more than 0 fractional digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartCashAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartCashAmount has more than 12 integral digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartCashAmount", null, null));
      }
      importView.InWsPostilionTranStartCashAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartCashAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartCashAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartCashAmount", null, null));
      }
      try {
          setInWsPostilionTranStartCashAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartCashAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartCashAmount", null, null));
      }
   }
 
   public short getInWsPostilionTranStartPanEntryMode() {
      return importView.InWsPostilionTranStartPanEntryMode;
   }
   public void setInWsPostilionTranStartPanEntryMode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsPostilionTranStartPanEntryMode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartPanEntryMode", null, null));
      }
      importView.InWsPostilionTranStartPanEntryMode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartPanEntryMode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartPanEntryMode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPanEntryMode", null, null));
      }
      try {
          setInWsPostilionTranStartPanEntryMode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartPanEntryMode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartPanEntryMode", null, null));
      }
   }
 
   public String getInWsPostilionTranStartPanData() {
      return FixedStringAttr.valueOf(importView.InWsPostilionTranStartPanData, 49);
   }
   public void setInWsPostilionTranStartPanData(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 49) {
         throw new PropertyVetoException("InWsPostilionTranStartPanData must be <= 49 characters.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartPanData", null, null));
      }
      importView.InWsPostilionTranStartPanData = FixedStringAttr.valueOf(s, (short)49);
   }
 
   public String getInWsPostilionTranStartCardExpirationDate() {
      return FixedStringAttr.valueOf(importView.InWsPostilionTranStartCardExpirationDate, 4);
   }
   public void setInWsPostilionTranStartCardExpirationDate(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsPostilionTranStartCardExpirationDate must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartCardExpirationDate", null, null));
      }
      importView.InWsPostilionTranStartCardExpirationDate = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInWsPostilionTranStartExtendedPaymentCode() {
      return importView.InWsPostilionTranStartExtendedPaymentCode;
   }
   public void setInWsPostilionTranStartExtendedPaymentCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsPostilionTranStartExtendedPaymentCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartExtendedPaymentCode", null, null));
      }
      importView.InWsPostilionTranStartExtendedPaymentCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartExtendedPaymentCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartExtendedPaymentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartExtendedPaymentCode", null, null));
      }
      try {
          setInWsPostilionTranStartExtendedPaymentCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartExtendedPaymentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartExtendedPaymentCode", null, null));
      }
   }
 
   public short getInWsPostilionTranStartMultiTenderInd() {
      return importView.InWsPostilionTranStartMultiTenderInd;
   }
   public void setInWsPostilionTranStartMultiTenderInd(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsPostilionTranStartMultiTenderInd has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartMultiTenderInd", null, null));
      }
      importView.InWsPostilionTranStartMultiTenderInd = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartMultiTenderInd(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartMultiTenderInd is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartMultiTenderInd", null, null));
      }
      try {
          setInWsPostilionTranStartMultiTenderInd(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartMultiTenderInd is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartMultiTenderInd", null, null));
      }
   }
 
   public String getInWsPostilionTranStartAuthorizationCode() {
      return FixedStringAttr.valueOf(importView.InWsPostilionTranStartAuthorizationCode, 6);
   }
   public void setInWsPostilionTranStartAuthorizationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InWsPostilionTranStartAuthorizationCode must be <= 6 characters.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartAuthorizationCode", null, null));
      }
      importView.InWsPostilionTranStartAuthorizationCode = FixedStringAttr.valueOf(s, (short)6);
   }
 
   public int getInWsPostilionTranStartApplicDataBufLength() {
      return importView.InWsPostilionTranStartApplicDataBufLength;
   }
   public void setInWsPostilionTranStartApplicDataBufLength(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InWsPostilionTranStartApplicDataBufLength has more than 6 digits.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartApplicDataBufLength", null, null));
      }
      importView.InWsPostilionTranStartApplicDataBufLength = IntAttr.valueOf(s);
   }
   public void setAsStringInWsPostilionTranStartApplicDataBufLength(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsPostilionTranStartApplicDataBufLength is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartApplicDataBufLength", null, null));
      }
      try {
          setInWsPostilionTranStartApplicDataBufLength(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsPostilionTranStartApplicDataBufLength is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsPostilionTranStartApplicDataBufLength", null, null));
      }
   }
 
   public String getInWsPostilionTranStartApplicDataBuf() {
      return FixedStringAttr.valueOf(importView.InWsPostilionTranStartApplicDataBuf, 10);
   }
   public void setInWsPostilionTranStartApplicDataBuf(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsPostilionTranStartApplicDataBuf must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsPostilionTranStartApplicDataBuf", null, null));
      }
      importView.InWsPostilionTranStartApplicDataBuf = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInBatchrptDetailCsfStringsString28() {
      return FixedStringAttr.valueOf(importView.InBatchrptDetailCsfStringsString28, 28);
   }
   public void setInBatchrptDetailCsfStringsString28(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InBatchrptDetailCsfStringsString28 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBatchrptDetailCsfStringsString28", null, null));
      }
      importView.InBatchrptDetailCsfStringsString28 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(importView.InWsMethodResultReturnCode, 2);
   }
   public void setInWsMethodResultReturnCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsMethodResultReturnCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsMethodResultReturnCode", null, null));
      }
      importView.InWsMethodResultReturnCode = FixedStringAttr.valueOf(s, (short)2);
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
 
   public short getInTempSfCalculatedAcademicYear() {
      return importView.InTempSfCalculatedAcademicYear;
   }
   public void setInTempSfCalculatedAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InTempSfCalculatedAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedAcademicYear", null, null));
      }
      importView.InTempSfCalculatedAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInTempSfCalculatedAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempSfCalculatedAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedAcademicYear", null, null));
      }
      try {
          setInTempSfCalculatedAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempSfCalculatedAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedAcademicYear", null, null));
      }
   }
 
   public short getInTempSfCalculatedAcademicPeriod() {
      return importView.InTempSfCalculatedAcademicPeriod;
   }
   public void setInTempSfCalculatedAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InTempSfCalculatedAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedAcademicPeriod", null, null));
      }
      importView.InTempSfCalculatedAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInTempSfCalculatedAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempSfCalculatedAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedAcademicPeriod", null, null));
      }
      try {
          setInTempSfCalculatedAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempSfCalculatedAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedAcademicPeriod", null, null));
      }
   }
 
   public int getInTempSfCalculatedMkStudentNr() {
      return importView.InTempSfCalculatedMkStudentNr;
   }
   public void setInTempSfCalculatedMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InTempSfCalculatedMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedMkStudentNr", null, null));
      }
      importView.InTempSfCalculatedMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInTempSfCalculatedMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempSfCalculatedMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedMkStudentNr", null, null));
      }
      try {
          setInTempSfCalculatedMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempSfCalculatedMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedMkStudentNr", null, null));
      }
   }
 
   public double getInTempSfCalculatedStudyFeesCalculated() {
      return importView.InTempSfCalculatedStudyFeesCalculated;
   }
   public void setInTempSfCalculatedStudyFeesCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InTempSfCalculatedStudyFeesCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InTempSfCalculatedStudyFeesCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesCalculated", null, null));
      }
      importView.InTempSfCalculatedStudyFeesCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInTempSfCalculatedStudyFeesCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempSfCalculatedStudyFeesCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesCalculated", null, null));
      }
      try {
          setInTempSfCalculatedStudyFeesCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempSfCalculatedStudyFeesCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesCalculated", null, null));
      }
   }
 
   public double getInTempSfCalculatedStudyFeesMinimum() {
      return importView.InTempSfCalculatedStudyFeesMinimum;
   }
   public void setInTempSfCalculatedStudyFeesMinimum(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InTempSfCalculatedStudyFeesMinimum has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesMinimum", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InTempSfCalculatedStudyFeesMinimum has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesMinimum", null, null));
      }
      importView.InTempSfCalculatedStudyFeesMinimum = DoubleAttr.valueOf(s);
   }
   public void setAsStringInTempSfCalculatedStudyFeesMinimum(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempSfCalculatedStudyFeesMinimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesMinimum", null, null));
      }
      try {
          setInTempSfCalculatedStudyFeesMinimum(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempSfCalculatedStudyFeesMinimum is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedStudyFeesMinimum", null, null));
      }
   }
 
   public double getInTempSfCalculatedAdditionFeesCalc() {
      return importView.InTempSfCalculatedAdditionFeesCalc;
   }
   public void setInTempSfCalculatedAdditionFeesCalc(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InTempSfCalculatedAdditionFeesCalc has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedAdditionFeesCalc", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InTempSfCalculatedAdditionFeesCalc has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InTempSfCalculatedAdditionFeesCalc", null, null));
      }
      importView.InTempSfCalculatedAdditionFeesCalc = DoubleAttr.valueOf(s);
   }
   public void setAsStringInTempSfCalculatedAdditionFeesCalc(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTempSfCalculatedAdditionFeesCalc is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedAdditionFeesCalc", null, null));
      }
      try {
          setInTempSfCalculatedAdditionFeesCalc(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTempSfCalculatedAdditionFeesCalc is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTempSfCalculatedAdditionFeesCalc", null, null));
      }
   }
 
   public int getInWsStudentAnnualRecordMkStudentNr() {
      return importView.InWsStudentAnnualRecordMkStudentNr;
   }
   public void setInWsStudentAnnualRecordMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkStudentNr", null, null));
      }
      importView.InWsStudentAnnualRecordMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkStudentNr", null, null));
      }
      try {
          setInWsStudentAnnualRecordMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkStudentNr", null, null));
      }
   }
 
   public short getInWsStudentAnnualRecordMkAcademicYear() {
      return importView.InWsStudentAnnualRecordMkAcademicYear;
   }
   public void setInWsStudentAnnualRecordMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkAcademicYear", null, null));
      }
      importView.InWsStudentAnnualRecordMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkAcademicYear", null, null));
      }
      try {
          setInWsStudentAnnualRecordMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkAcademicYear", null, null));
      }
   }
 
   public short getInWsStudentAnnualRecordMkAcademicPeriod() {
      return importView.InWsStudentAnnualRecordMkAcademicPeriod;
   }
   public void setInWsStudentAnnualRecordMkAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      importView.InWsStudentAnnualRecordMkAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordMkAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkAcademicPeriod", null, null));
      }
      try {
          setInWsStudentAnnualRecordMkAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkAcademicPeriod", null, null));
      }
   }
 
   public short getInWsStudentAnnualRecordMkDisabilityTypeCode() {
      return importView.InWsStudentAnnualRecordMkDisabilityTypeCode;
   }
   public void setInWsStudentAnnualRecordMkDisabilityTypeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkDisabilityTypeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkDisabilityTypeCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkDisabilityTypeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordMkDisabilityTypeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkDisabilityTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkDisabilityTypeCode", null, null));
      }
      try {
          setInWsStudentAnnualRecordMkDisabilityTypeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkDisabilityTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkDisabilityTypeCode", null, null));
      }
   }
 
   public String getInWsStudentAnnualRecordFellowStudentFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordFellowStudentFlag, 1);
   }
   public void setInWsStudentAnnualRecordFellowStudentFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordFellowStudentFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordFellowStudentFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordFellowStudentFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordFellowStudentFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordFellowStudentFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordFellowStudentFlag", null, null));
      }
      importView.InWsStudentAnnualRecordFellowStudentFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentAnnualRecordBursaryType() {
      return importView.InWsStudentAnnualRecordBursaryType;
   }
   public void setInWsStudentAnnualRecordBursaryType(short s)
      throws PropertyVetoException {
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordBursaryTypePropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordBursaryTypePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordBursaryType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryType", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordBursaryType has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryType", null, null));
      }
      importView.InWsStudentAnnualRecordBursaryType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordBursaryType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordBursaryType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryType", null, null));
      }
      try {
          setInWsStudentAnnualRecordBursaryType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordBursaryType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryType", null, null));
      }
   }
 
   public int getInWsStudentAnnualRecordBursaryAmount() {
      return importView.InWsStudentAnnualRecordBursaryAmount;
   }
   public void setInWsStudentAnnualRecordBursaryAmount(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordBursaryAmount has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryAmount", null, null));
      }
      importView.InWsStudentAnnualRecordBursaryAmount = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordBursaryAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordBursaryAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryAmount", null, null));
      }
      try {
          setInWsStudentAnnualRecordBursaryAmount(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordBursaryAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordBursaryAmount", null, null));
      }
   }
 
   public short getInWsStudentAnnualRecordMkRegionalOfficeCode() {
      return importView.InWsStudentAnnualRecordMkRegionalOfficeCode;
   }
   public void setInWsStudentAnnualRecordMkRegionalOfficeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkRegionalOfficeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkRegionalOfficeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordMkRegionalOfficeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
      try {
          setInWsStudentAnnualRecordMkRegionalOfficeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordMkRegionalOfficeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkRegionalOfficeCode", null, null));
      }
   }
 
   public String getInWsStudentAnnualRecordFirstRegistrationFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordFirstRegistrationFlag, 1);
   }
   public void setInWsStudentAnnualRecordFirstRegistrationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordFirstRegistrationFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordFirstRegistrationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordFirstRegistrationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordFirstRegistrationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordFirstRegistrationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordFirstRegistrationFlag", null, null));
      }
      importView.InWsStudentAnnualRecordFirstRegistrationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAnnualRecordPreviousUnisaFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordPreviousUnisaFlag, 1);
   }
   public void setInWsStudentAnnualRecordPreviousUnisaFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordPreviousUnisaFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordPreviousUnisaFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordPreviousUnisaFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordPreviousUnisaFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordPreviousUnisaFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordPreviousUnisaFlag", null, null));
      }
      importView.InWsStudentAnnualRecordPreviousUnisaFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAnnualRecordMkPrevEducationalInstitCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMkPrevEducationalInstitCode, 4);
   }
   public void setInWsStudentAnnualRecordMkPrevEducationalInstitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkPrevEducationalInstitCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkPrevEducationalInstitCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkPrevEducationalInstitCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInWsStudentAnnualRecordPrevEducationalInstitutionYr() {
      return importView.InWsStudentAnnualRecordPrevEducationalInstitutionYr;
   }
   public void setInWsStudentAnnualRecordPrevEducationalInstitutionYr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordPrevEducationalInstitutionYr has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordPrevEducationalInstitutionYr", null, null));
      }
      importView.InWsStudentAnnualRecordPrevEducationalInstitutionYr = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordPrevEducationalInstitutionYr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordPrevEducationalInstitutionYr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordPrevEducationalInstitutionYr", null, null));
      }
      try {
          setInWsStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordPrevEducationalInstitutionYr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordPrevEducationalInstitutionYr", null, null));
      }
   }
 
   public String getInWsStudentAnnualRecordMkOtherEducationalInstitCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMkOtherEducationalInstitCode, 4);
   }
   public void setInWsStudentAnnualRecordMkOtherEducationalInstitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkOtherEducationalInstitCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkOtherEducationalInstitCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkOtherEducationalInstitCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInWsStudentAnnualRecordRegistrationMethodCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordRegistrationMethodCode, 1);
   }
   public void setInWsStudentAnnualRecordRegistrationMethodCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordRegistrationMethodCodePropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordRegistrationMethodCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordRegistrationMethodCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordRegistrationMethodCode", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordRegistrationMethodCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordRegistrationMethodCode", null, null));
      }
      importView.InWsStudentAnnualRecordRegistrationMethodCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAnnualRecordDespatchMethodCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordDespatchMethodCode, 1);
   }
   public void setInWsStudentAnnualRecordDespatchMethodCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordDespatchMethodCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordDespatchMethodCode", null, null));
      }
      importView.InWsStudentAnnualRecordDespatchMethodCode = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAnnualRecordMkOccupationCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMkOccupationCode, 5);
   }
   public void setInWsStudentAnnualRecordMkOccupationCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkOccupationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkOccupationCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkOccupationCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInWsStudentAnnualRecordMkEconomicSectorCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMkEconomicSectorCode, 5);
   }
   public void setInWsStudentAnnualRecordMkEconomicSectorCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkEconomicSectorCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkEconomicSectorCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkEconomicSectorCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInWsStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordStatusCode, 2);
   }
   public void setInWsStudentAnnualRecordStatusCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordStatusCodePropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordStatusCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordStatusCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordStatusCode", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentAnnualRecordStatusCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordStatusCode", null, null));
      }
      importView.InWsStudentAnnualRecordStatusCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInWsStudentAnnualRecordLibraryAccessLevel() {
      return importView.InWsStudentAnnualRecordLibraryAccessLevel;
   }
   public void setInWsStudentAnnualRecordLibraryAccessLevel(short s)
      throws PropertyVetoException {
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordLibraryAccessLevelPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordLibraryAccessLevelPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordLibraryAccessLevel value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordLibraryAccessLevel has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      importView.InWsStudentAnnualRecordLibraryAccessLevel = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordLibraryAccessLevel(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordLibraryAccessLevel", null, null));
      }
      try {
          setInWsStudentAnnualRecordLibraryAccessLevel(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordLibraryAccessLevel", null, null));
      }
   }
 
   public short getInWsStudentAnnualRecordSpecialLibraryAccessLevel() {
      return importView.InWsStudentAnnualRecordSpecialLibraryAccessLevel;
   }
   public void setInWsStudentAnnualRecordSpecialLibraryAccessLevel(short s)
      throws PropertyVetoException {
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordSpecialLibraryAccessLevelPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordSpecialLibraryAccessLevelPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordSpecialLibraryAccessLevel value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordSpecialLibraryAccessLevel has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      importView.InWsStudentAnnualRecordSpecialLibraryAccessLevel = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordSpecialLibraryAccessLevel(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordSpecialLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
      try {
          setInWsStudentAnnualRecordSpecialLibraryAccessLevel(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordSpecialLibraryAccessLevel is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordSpecialLibraryAccessLevel", null, null));
      }
   }
 
   public String getInWsStudentAnnualRecordMkHighestQualCode() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMkHighestQualCode, 5);
   }
   public void setInWsStudentAnnualRecordMkHighestQualCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMkHighestQualCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMkHighestQualCode", null, null));
      }
      importView.InWsStudentAnnualRecordMkHighestQualCode = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInWsStudentAnnualRecordLateRegistrationFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordLateRegistrationFlag, 1);
   }
   public void setInWsStudentAnnualRecordLateRegistrationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordLateRegistrationFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordLateRegistrationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordLateRegistrationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordLateRegistrationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordLateRegistrationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordLateRegistrationFlag", null, null));
      }
      importView.InWsStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInWsStudentAnnualRecordPersonnelNr() {
      return importView.InWsStudentAnnualRecordPersonnelNr;
   }
   public void setInWsStudentAnnualRecordPersonnelNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordPersonnelNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordPersonnelNr", null, null));
      }
      importView.InWsStudentAnnualRecordPersonnelNr = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordPersonnelNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordPersonnelNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordPersonnelNr", null, null));
      }
      try {
          setInWsStudentAnnualRecordPersonnelNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordPersonnelNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordPersonnelNr", null, null));
      }
   }
 
   public String getInWsStudentAnnualRecordTefsaApplicationFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordTefsaApplicationFlag, 1);
   }
   public void setInWsStudentAnnualRecordTefsaApplicationFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordTefsaApplicationFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordTefsaApplicationFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordTefsaApplicationFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordTefsaApplicationFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordTefsaApplicationFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordTefsaApplicationFlag", null, null));
      }
      importView.InWsStudentAnnualRecordTefsaApplicationFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentAnnualRecordMatriculationBoardFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentAnnualRecordMatriculationBoardFlag, 1);
   }
   public void setInWsStudentAnnualRecordMatriculationBoardFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordMatriculationBoardFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentAnnualRecordMatriculationBoardFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMatriculationBoardFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMatriculationBoardFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAnnualRecordMatriculationBoardFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordMatriculationBoardFlag", null, null));
      }
      importView.InWsStudentAnnualRecordMatriculationBoardFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsStudentAnnualRecordSemesterType() {
      return importView.InWsStudentAnnualRecordSemesterType;
   }
   public void setInWsStudentAnnualRecordSemesterType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentAnnualRecordSemesterType has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentAnnualRecordSemesterType", null, null));
      }
      importView.InWsStudentAnnualRecordSemesterType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentAnnualRecordSemesterType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentAnnualRecordSemesterType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordSemesterType", null, null));
      }
      try {
          setInWsStudentAnnualRecordSemesterType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentAnnualRecordSemesterType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentAnnualRecordSemesterType", null, null));
      }
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
 
   public double getInStudyFeeBundleDocumentTotalAmount() {
      return importView.InStudyFeeBundleDocumentTotalAmount;
   }
   public void setInStudyFeeBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InStudyFeeBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudyFeeBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudyFeeBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudyFeeBundleDocumentTotalAmount", null, null));
      }
      importView.InStudyFeeBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudyFeeBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudyFeeBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudyFeeBundleDocumentTotalAmount", null, null));
      }
      try {
          setInStudyFeeBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudyFeeBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudyFeeBundleDocumentTotalAmount", null, null));
      }
   }
 
   public double getInMrBundleDocumentTotalAmount() {
      return importView.InMrBundleDocumentTotalAmount;
   }
   public void setInMrBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InMrBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMrBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InMrBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InMrBundleDocumentTotalAmount", null, null));
      }
      importView.InMrBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMrBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMrBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrBundleDocumentTotalAmount", null, null));
      }
      try {
          setInMrBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMrBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrBundleDocumentTotalAmount", null, null));
      }
   }
 
   public short getInBundleDocumentDocumentSeqNr() {
      return importView.InBundleDocumentDocumentSeqNr;
   }
   public void setInBundleDocumentDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InBundleDocumentDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentDocumentSeqNr", null, null));
      }
      importView.InBundleDocumentDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentDocumentSeqNr", null, null));
      }
      try {
          setInBundleDocumentDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentDocumentSeqNr", null, null));
      }
   }
 
   public short getInBundleDocumentJournalSequenceNr() {
      return importView.InBundleDocumentJournalSequenceNr;
   }
   public void setInBundleDocumentJournalSequenceNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InBundleDocumentJournalSequenceNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentJournalSequenceNr", null, null));
      }
      importView.InBundleDocumentJournalSequenceNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentJournalSequenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentJournalSequenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentJournalSequenceNr", null, null));
      }
      try {
          setInBundleDocumentJournalSequenceNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentJournalSequenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentJournalSequenceNr", null, null));
      }
   }
 
   public int getInBundleDocumentChequeNr() {
      return importView.InBundleDocumentChequeNr;
   }
   public void setInBundleDocumentChequeNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InBundleDocumentChequeNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentChequeNr", null, null));
      }
      importView.InBundleDocumentChequeNr = IntAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentChequeNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentChequeNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentChequeNr", null, null));
      }
      try {
          setInBundleDocumentChequeNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentChequeNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentChequeNr", null, null));
      }
   }
 
   public String getInBundleDocumentDtCrInd() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentDtCrInd, 1);
   }
   public void setInBundleDocumentDtCrInd(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundleDocumentDtCrIndPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleDocumentDtCrIndPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundleDocumentDtCrInd value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleDocumentDtCrInd", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleDocumentDtCrInd must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentDtCrInd", null, null));
      }
      importView.InBundleDocumentDtCrInd = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInBundleDocumentTotalAmount() {
      return importView.InBundleDocumentTotalAmount;
   }
   public void setInBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentTotalAmount", null, null));
      }
      importView.InBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentTotalAmount", null, null));
      }
      try {
          setInBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentTotalAmount", null, null));
      }
   }
 
   public int getInBundleDocumentMkStudentNr() {
      return importView.InBundleDocumentMkStudentNr;
   }
   public void setInBundleDocumentMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InBundleDocumentMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentMkStudentNr", null, null));
      }
      importView.InBundleDocumentMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentMkStudentNr", null, null));
      }
      try {
          setInBundleDocumentMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentMkStudentNr", null, null));
      }
   }
 
   public String getInBundleDocumentChequePayeeName() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentChequePayeeName, 50);
   }
   public void setInBundleDocumentChequePayeeName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InBundleDocumentChequePayeeName must be <= 50 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentChequePayeeName", null, null));
      }
      importView.InBundleDocumentChequePayeeName = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInBundleDocumentChequePayeeInitials() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentChequePayeeInitials, 10);
   }
   public void setInBundleDocumentChequePayeeInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InBundleDocumentChequePayeeInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentChequePayeeInitials", null, null));
      }
      importView.InBundleDocumentChequePayeeInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInBundleDocumentLanguage() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentLanguage, 1);
   }
   public void setInBundleDocumentLanguage(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleDocumentLanguage must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentLanguage", null, null));
      }
      importView.InBundleDocumentLanguage = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInBundleDocumentPostGradFlag() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPostGradFlag, 1);
   }
   public void setInBundleDocumentPostGradFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundleDocumentPostGradFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleDocumentPostGradFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundleDocumentPostGradFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleDocumentPostGradFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleDocumentPostGradFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPostGradFlag", null, null));
      }
      importView.InBundleDocumentPostGradFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInBundleDocumentCreditCardExpiryDate() {
      return importView.InBundleDocumentCreditCardExpiryDate;
   }
   public void setInBundleDocumentCreditCardExpiryDate(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InBundleDocumentCreditCardExpiryDate has more than 6 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentCreditCardExpiryDate", null, null));
      }
      importView.InBundleDocumentCreditCardExpiryDate = IntAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentCreditCardExpiryDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentCreditCardExpiryDate is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentCreditCardExpiryDate", null, null));
      }
      try {
          setInBundleDocumentCreditCardExpiryDate(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentCreditCardExpiryDate is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentCreditCardExpiryDate", null, null));
      }
   }
 
   public short getInBundleDocumentCreditCardBudgetPeriod() {
      return importView.InBundleDocumentCreditCardBudgetPeriod;
   }
   public void setInBundleDocumentCreditCardBudgetPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InBundleDocumentCreditCardBudgetPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentCreditCardBudgetPeriod", null, null));
      }
      importView.InBundleDocumentCreditCardBudgetPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentCreditCardBudgetPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentCreditCardBudgetPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentCreditCardBudgetPeriod", null, null));
      }
      try {
          setInBundleDocumentCreditCardBudgetPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentCreditCardBudgetPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentCreditCardBudgetPeriod", null, null));
      }
   }
 
   public double getInBundleDocumentAmountCash() {
      return importView.InBundleDocumentAmountCash;
   }
   public void setInBundleDocumentAmountCash(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountCash has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountCash", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountCash has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountCash", null, null));
      }
      importView.InBundleDocumentAmountCash = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountCash(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountCash is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountCash", null, null));
      }
      try {
          setInBundleDocumentAmountCash(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountCash is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountCash", null, null));
      }
   }
 
   public double getInBundleDocumentAmountCheque() {
      return importView.InBundleDocumentAmountCheque;
   }
   public void setInBundleDocumentAmountCheque(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountCheque has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountCheque", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountCheque has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountCheque", null, null));
      }
      importView.InBundleDocumentAmountCheque = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountCheque(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountCheque is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountCheque", null, null));
      }
      try {
          setInBundleDocumentAmountCheque(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountCheque is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountCheque", null, null));
      }
   }
 
   public double getInBundleDocumentAmountPostOrder() {
      return importView.InBundleDocumentAmountPostOrder;
   }
   public void setInBundleDocumentAmountPostOrder(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountPostOrder has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountPostOrder", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountPostOrder has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountPostOrder", null, null));
      }
      importView.InBundleDocumentAmountPostOrder = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountPostOrder(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountPostOrder is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountPostOrder", null, null));
      }
      try {
          setInBundleDocumentAmountPostOrder(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountPostOrder is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountPostOrder", null, null));
      }
   }
 
   public double getInBundleDocumentAmountMoneyOrder() {
      return importView.InBundleDocumentAmountMoneyOrder;
   }
   public void setInBundleDocumentAmountMoneyOrder(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountMoneyOrder has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountMoneyOrder", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountMoneyOrder has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountMoneyOrder", null, null));
      }
      importView.InBundleDocumentAmountMoneyOrder = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountMoneyOrder(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountMoneyOrder is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountMoneyOrder", null, null));
      }
      try {
          setInBundleDocumentAmountMoneyOrder(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountMoneyOrder is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountMoneyOrder", null, null));
      }
   }
 
   public double getInBundleDocumentAmountForeignDraft() {
      return importView.InBundleDocumentAmountForeignDraft;
   }
   public void setInBundleDocumentAmountForeignDraft(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountForeignDraft has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountForeignDraft", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountForeignDraft has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountForeignDraft", null, null));
      }
      importView.InBundleDocumentAmountForeignDraft = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountForeignDraft(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountForeignDraft is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountForeignDraft", null, null));
      }
      try {
          setInBundleDocumentAmountForeignDraft(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountForeignDraft is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountForeignDraft", null, null));
      }
   }
 
   public double getInBundleDocumentAmountCreditCard() {
      return importView.InBundleDocumentAmountCreditCard;
   }
   public void setInBundleDocumentAmountCreditCard(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountCreditCard has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountCreditCard", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountCreditCard has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountCreditCard", null, null));
      }
      importView.InBundleDocumentAmountCreditCard = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountCreditCard(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountCreditCard is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountCreditCard", null, null));
      }
      try {
          setInBundleDocumentAmountCreditCard(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountCreditCard is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountCreditCard", null, null));
      }
   }
 
   public double getInBundleDocumentAmountTmo() {
      return importView.InBundleDocumentAmountTmo;
   }
   public void setInBundleDocumentAmountTmo(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleDocumentAmountTmo has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountTmo", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleDocumentAmountTmo has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleDocumentAmountTmo", null, null));
      }
      importView.InBundleDocumentAmountTmo = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentAmountTmo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentAmountTmo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountTmo", null, null));
      }
      try {
          setInBundleDocumentAmountTmo(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentAmountTmo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentAmountTmo", null, null));
      }
   }
 
   public int getInBundleDocumentMkEbankBranchCode() {
      return importView.InBundleDocumentMkEbankBranchCode;
   }
   public void setInBundleDocumentMkEbankBranchCode(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InBundleDocumentMkEbankBranchCode has more than 6 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentMkEbankBranchCode", null, null));
      }
      importView.InBundleDocumentMkEbankBranchCode = IntAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentMkEbankBranchCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentMkEbankBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentMkEbankBranchCode", null, null));
      }
      try {
          setInBundleDocumentMkEbankBranchCode(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentMkEbankBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentMkEbankBranchCode", null, null));
      }
   }
 
   public String getInBundleDocumentAccountNr() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentAccountNr, 20);
   }
   public void setInBundleDocumentAccountNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InBundleDocumentAccountNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentAccountNr", null, null));
      }
      importView.InBundleDocumentAccountNr = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInBundleDocumentEbankAccountType() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentEbankAccountType, 1);
   }
   public void setInBundleDocumentEbankAccountType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleDocumentEbankAccountType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentEbankAccountType", null, null));
      }
      importView.InBundleDocumentEbankAccountType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInBundleDocumentAccountHolder() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentAccountHolder, 28);
   }
   public void setInBundleDocumentAccountHolder(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentAccountHolder must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentAccountHolder", null, null));
      }
      importView.InBundleDocumentAccountHolder = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentAutoSignatureFlag() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentAutoSignatureFlag, 1);
   }
   public void setInBundleDocumentAutoSignatureFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundleDocumentAutoSignatureFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleDocumentAutoSignatureFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundleDocumentAutoSignatureFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleDocumentAutoSignatureFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleDocumentAutoSignatureFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentAutoSignatureFlag", null, null));
      }
      importView.InBundleDocumentAutoSignatureFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInBundleDocumentSalaryFlag() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentSalaryFlag, 1);
   }
   public void setInBundleDocumentSalaryFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundleDocumentSalaryFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleDocumentSalaryFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundleDocumentSalaryFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleDocumentSalaryFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleDocumentSalaryFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentSalaryFlag", null, null));
      }
      importView.InBundleDocumentSalaryFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInBundleDocumentPaidByName() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByName, 28);
   }
   public void setInBundleDocumentPaidByName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByName", null, null));
      }
      importView.InBundleDocumentPaidByName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentPaidByInitials() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByInitials, 10);
   }
   public void setInBundleDocumentPaidByInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InBundleDocumentPaidByInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByInitials", null, null));
      }
      importView.InBundleDocumentPaidByInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInBundleDocumentPaidByTitle() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByTitle, 10);
   }
   public void setInBundleDocumentPaidByTitle(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InBundleDocumentPaidByTitle must be <= 10 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByTitle", null, null));
      }
      importView.InBundleDocumentPaidByTitle = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInBundleDocumentPaidByAddress1() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByAddress1, 28);
   }
   public void setInBundleDocumentPaidByAddress1(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByAddress1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByAddress1", null, null));
      }
      importView.InBundleDocumentPaidByAddress1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentPaidByAddress2() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByAddress2, 28);
   }
   public void setInBundleDocumentPaidByAddress2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByAddress2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByAddress2", null, null));
      }
      importView.InBundleDocumentPaidByAddress2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentPaidByAddress3() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByAddress3, 28);
   }
   public void setInBundleDocumentPaidByAddress3(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByAddress3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByAddress3", null, null));
      }
      importView.InBundleDocumentPaidByAddress3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentPaidByAddress4() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByAddress4, 28);
   }
   public void setInBundleDocumentPaidByAddress4(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByAddress4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByAddress4", null, null));
      }
      importView.InBundleDocumentPaidByAddress4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentPaidByAddress5() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByAddress5, 28);
   }
   public void setInBundleDocumentPaidByAddress5(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByAddress5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByAddress5", null, null));
      }
      importView.InBundleDocumentPaidByAddress5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentPaidByAddress6() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPaidByAddress6, 28);
   }
   public void setInBundleDocumentPaidByAddress6(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InBundleDocumentPaidByAddress6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByAddress6", null, null));
      }
      importView.InBundleDocumentPaidByAddress6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInBundleDocumentClassification() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentClassification, 2);
   }
   public void setInBundleDocumentClassification(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InBundleDocumentClassification must be <= 2 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentClassification", null, null));
      }
      importView.InBundleDocumentClassification = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInBundleDocumentPaidByPostCode() {
      return importView.InBundleDocumentPaidByPostCode;
   }
   public void setInBundleDocumentPaidByPostCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InBundleDocumentPaidByPostCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InBundleDocumentPaidByPostCode", null, null));
      }
      importView.InBundleDocumentPaidByPostCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleDocumentPaidByPostCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleDocumentPaidByPostCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentPaidByPostCode", null, null));
      }
      try {
          setInBundleDocumentPaidByPostCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleDocumentPaidByPostCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleDocumentPaidByPostCode", null, null));
      }
   }
 
   public String getInBundleDocumentCreditCardAuthCode() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentCreditCardAuthCode, 6);
   }
   public void setInBundleDocumentCreditCardAuthCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InBundleDocumentCreditCardAuthCode must be <= 6 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentCreditCardAuthCode", null, null));
      }
      importView.InBundleDocumentCreditCardAuthCode = FixedStringAttr.valueOf(s, (short)6);
   }
 
   public String getInBundleDocumentPayMethod() {
      return FixedStringAttr.valueOf(importView.InBundleDocumentPayMethod, 7);
   }
   public void setInBundleDocumentPayMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InBundleDocumentPayMethod must be <= 7 characters.",
               new PropertyChangeEvent (this, "InBundleDocumentPayMethod", null, null));
      }
      importView.InBundleDocumentPayMethod = FixedStringAttr.valueOf(s, (short)7);
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
 
   public short getInWsStudentLibraryBlackList() {
      return importView.InWsStudentLibraryBlackList;
   }
   public void setInWsStudentLibraryBlackList(short s)
      throws PropertyVetoException {
      Sfrrf03sMntOnlineCcPaymentsWsStudentLibraryBlackListPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentLibraryBlackListPropertyEditor();
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
      Sfrrf03sMntOnlineCcPaymentsWsStudentExamFeesDebtFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentExamFeesDebtFlagPropertyEditor();
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
 
   public String getInWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentFinancialBlockFlag, 1);
   }
   public void setInWsStudentFinancialBlockFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsWsStudentFinancialBlockFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsWsStudentFinancialBlockFlagPropertyEditor();
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
 
   public String getInWsStudentApplicationStatus() {
      return StringAttr.valueOf(importView.InWsStudentApplicationStatus);
   }
   public void setInWsStudentApplicationStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsStudentApplicationStatus must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsStudentApplicationStatus", null, null));
      }
      importView.InWsStudentApplicationStatus = StringAttr.valueOf(s, (short)2);
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
 
   public Calendar getInBundleBundleDate() {
      return DateAttr.toCalendar(importView.InBundleBundleDate);
   }
   public int getAsIntInBundleBundleDate() {
      return DateAttr.toInt(importView.InBundleBundleDate);
   }
   public void setInBundleBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InBundleBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInBundleBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInBundleBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInBundleBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InBundleBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InBundleBundleDate", null, null));
         }
      }
   }
   public void setAsIntInBundleBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInBundleBundleDate(temp);
   }
 
   public short getInBundleBundleNr() {
      return importView.InBundleBundleNr;
   }
   public void setInBundleBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InBundleBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InBundleBundleNr", null, null));
      }
      importView.InBundleBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleBundleNr", null, null));
      }
      try {
          setInBundleBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleBundleNr", null, null));
      }
   }
 
   public short getInBundleType() {
      return importView.InBundleType;
   }
   public void setInBundleType(short s)
      throws PropertyVetoException {
      Sfrrf03sMntOnlineCcPaymentsBundleTypePropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleTypePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InBundleType value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleType", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InBundleType has more than 2 digits.",
               new PropertyChangeEvent (this, "InBundleType", null, null));
      }
      importView.InBundleType = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleType", null, null));
      }
      try {
          setInBundleType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleType", null, null));
      }
   }
 
   public short getInBundleFinancialSection() {
      return importView.InBundleFinancialSection;
   }
   public void setInBundleFinancialSection(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InBundleFinancialSection has more than 2 digits.",
               new PropertyChangeEvent (this, "InBundleFinancialSection", null, null));
      }
      importView.InBundleFinancialSection = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleFinancialSection(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleFinancialSection is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleFinancialSection", null, null));
      }
      try {
          setInBundleFinancialSection(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleFinancialSection is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleFinancialSection", null, null));
      }
   }
 
   public short getInBundleFinancialYear() {
      return importView.InBundleFinancialYear;
   }
   public void setInBundleFinancialYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InBundleFinancialYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InBundleFinancialYear", null, null));
      }
      importView.InBundleFinancialYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleFinancialYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleFinancialYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleFinancialYear", null, null));
      }
      try {
          setInBundleFinancialYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleFinancialYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleFinancialYear", null, null));
      }
   }
 
   public short getInBundleFinancialPeriod() {
      return importView.InBundleFinancialPeriod;
   }
   public void setInBundleFinancialPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InBundleFinancialPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InBundleFinancialPeriod", null, null));
      }
      importView.InBundleFinancialPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleFinancialPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleFinancialPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleFinancialPeriod", null, null));
      }
      try {
          setInBundleFinancialPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleFinancialPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleFinancialPeriod", null, null));
      }
   }
 
   public String getInBundleStatus() {
      return FixedStringAttr.valueOf(importView.InBundleStatus, 2);
   }
   public void setInBundleStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundleStatusPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleStatusPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundleStatus value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleStatus", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InBundleStatus must be <= 2 characters.",
               new PropertyChangeEvent (this, "InBundleStatus", null, null));
      }
      importView.InBundleStatus = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInBundleLastDocumentNr() {
      return importView.InBundleLastDocumentNr;
   }
   public void setInBundleLastDocumentNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InBundleLastDocumentNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InBundleLastDocumentNr", null, null));
      }
      importView.InBundleLastDocumentNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleLastDocumentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleLastDocumentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleLastDocumentNr", null, null));
      }
      try {
          setInBundleLastDocumentNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleLastDocumentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleLastDocumentNr", null, null));
      }
   }
 
   public short getInBundleCreditCardCount() {
      return importView.InBundleCreditCardCount;
   }
   public void setInBundleCreditCardCount(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InBundleCreditCardCount has more than 2 digits.",
               new PropertyChangeEvent (this, "InBundleCreditCardCount", null, null));
      }
      importView.InBundleCreditCardCount = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleCreditCardCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleCreditCardCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleCreditCardCount", null, null));
      }
      try {
          setInBundleCreditCardCount(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleCreditCardCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleCreditCardCount", null, null));
      }
   }
 
   public String getInBundleInstitution() {
      return FixedStringAttr.valueOf(importView.InBundleInstitution, 1);
   }
   public void setInBundleInstitution(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleInstitution must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleInstitution", null, null));
      }
      importView.InBundleInstitution = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInBundleTotalCreditCardCalculated() {
      return importView.InBundleTotalCreditCardCalculated;
   }
   public void setInBundleTotalCreditCardCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalCreditCardCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditCardCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalCreditCardCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditCardCalculated", null, null));
      }
      importView.InBundleTotalCreditCardCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalCreditCardCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalCreditCardCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditCardCalculated", null, null));
      }
      try {
          setInBundleTotalCreditCardCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalCreditCardCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditCardCalculated", null, null));
      }
   }
 
   public double getInBundleTotalCreditCalculated() {
      return importView.InBundleTotalCreditCalculated;
   }
   public void setInBundleTotalCreditCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalCreditCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalCreditCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditCalculated", null, null));
      }
      importView.InBundleTotalCreditCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalCreditCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalCreditCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditCalculated", null, null));
      }
      try {
          setInBundleTotalCreditCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalCreditCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditCalculated", null, null));
      }
   }
 
   public double getInBundleTotalCreditCardEntered() {
      return importView.InBundleTotalCreditCardEntered;
   }
   public void setInBundleTotalCreditCardEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalCreditCardEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditCardEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalCreditCardEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditCardEntered", null, null));
      }
      importView.InBundleTotalCreditCardEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalCreditCardEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalCreditCardEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditCardEntered", null, null));
      }
      try {
          setInBundleTotalCreditCardEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalCreditCardEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditCardEntered", null, null));
      }
   }
 
   public double getInBundleTotalCreditsEntered() {
      return importView.InBundleTotalCreditsEntered;
   }
   public void setInBundleTotalCreditsEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalCreditsEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditsEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalCreditsEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalCreditsEntered", null, null));
      }
      importView.InBundleTotalCreditsEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalCreditsEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalCreditsEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditsEntered", null, null));
      }
      try {
          setInBundleTotalCreditsEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalCreditsEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCreditsEntered", null, null));
      }
   }
 
   public int getInBundleMkUserCode() {
      return importView.InBundleMkUserCode;
   }
   public void setInBundleMkUserCode(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InBundleMkUserCode has more than 5 digits.",
               new PropertyChangeEvent (this, "InBundleMkUserCode", null, null));
      }
      importView.InBundleMkUserCode = IntAttr.valueOf(s);
   }
   public void setAsStringInBundleMkUserCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleMkUserCode", null, null));
      }
      try {
          setInBundleMkUserCode(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleMkUserCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleMkUserCode", null, null));
      }
   }
 
   public String getInBundleOriginatingSection() {
      return FixedStringAttr.valueOf(importView.InBundleOriginatingSection, 7);
   }
   public void setInBundleOriginatingSection(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InBundleOriginatingSection must be <= 7 characters.",
               new PropertyChangeEvent (this, "InBundleOriginatingSection", null, null));
      }
      importView.InBundleOriginatingSection = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInBundleChequeCount() {
      return importView.InBundleChequeCount;
   }
   public void setInBundleChequeCount(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InBundleChequeCount has more than 2 digits.",
               new PropertyChangeEvent (this, "InBundleChequeCount", null, null));
      }
      importView.InBundleChequeCount = ShortAttr.valueOf(s);
   }
   public void setAsStringInBundleChequeCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleChequeCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleChequeCount", null, null));
      }
      try {
          setInBundleChequeCount(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleChequeCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleChequeCount", null, null));
      }
   }
 
   public String getInBundleMkBundleOrigin() {
      return FixedStringAttr.valueOf(importView.InBundleMkBundleOrigin, 2);
   }
   public void setInBundleMkBundleOrigin(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InBundleMkBundleOrigin must be <= 2 characters.",
               new PropertyChangeEvent (this, "InBundleMkBundleOrigin", null, null));
      }
      importView.InBundleMkBundleOrigin = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInBundleRegisteredMailFlag() {
      return FixedStringAttr.valueOf(importView.InBundleRegisteredMailFlag, 1);
   }
   public void setInBundleRegisteredMailFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundleRegisteredMailFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundleRegisteredMailFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundleRegisteredMailFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundleRegisteredMailFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundleRegisteredMailFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundleRegisteredMailFlag", null, null));
      }
      importView.InBundleRegisteredMailFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInBundlePostGradFlag() {
      return FixedStringAttr.valueOf(importView.InBundlePostGradFlag, 1);
   }
   public void setInBundlePostGradFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfrrf03sMntOnlineCcPaymentsBundlePostGradFlagPropertyEditor pe = new Sfrrf03sMntOnlineCcPaymentsBundlePostGradFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InBundlePostGradFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InBundlePostGradFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InBundlePostGradFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InBundlePostGradFlag", null, null));
      }
      importView.InBundlePostGradFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInBundleMkUserJnlApproved() {
      return importView.InBundleMkUserJnlApproved;
   }
   public void setInBundleMkUserJnlApproved(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InBundleMkUserJnlApproved has more than 5 digits.",
               new PropertyChangeEvent (this, "InBundleMkUserJnlApproved", null, null));
      }
      importView.InBundleMkUserJnlApproved = IntAttr.valueOf(s);
   }
   public void setAsStringInBundleMkUserJnlApproved(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleMkUserJnlApproved is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleMkUserJnlApproved", null, null));
      }
      try {
          setInBundleMkUserJnlApproved(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleMkUserJnlApproved is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleMkUserJnlApproved", null, null));
      }
   }
 
   public String getInBundleGeneralLedgerSource() {
      return FixedStringAttr.valueOf(importView.InBundleGeneralLedgerSource, 5);
   }
   public void setInBundleGeneralLedgerSource(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InBundleGeneralLedgerSource must be <= 5 characters.",
               new PropertyChangeEvent (this, "InBundleGeneralLedgerSource", null, null));
      }
      importView.InBundleGeneralLedgerSource = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public Calendar getInBundleBundleCaptureDate() {
      return DateAttr.toCalendar(importView.InBundleBundleCaptureDate);
   }
   public int getAsIntInBundleBundleCaptureDate() {
      return DateAttr.toInt(importView.InBundleBundleCaptureDate);
   }
   public void setInBundleBundleCaptureDate(Calendar s)
      throws PropertyVetoException {
      importView.InBundleBundleCaptureDate = DateAttr.valueOf(s);
   }
   public void setAsStringInBundleBundleCaptureDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInBundleBundleCaptureDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInBundleBundleCaptureDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InBundleBundleCaptureDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InBundleBundleCaptureDate", null, null));
         }
      }
   }
   public void setAsIntInBundleBundleCaptureDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInBundleBundleCaptureDate(temp);
   }
 
   public double getInBundleTotalCashCalculated() {
      return importView.InBundleTotalCashCalculated;
   }
   public void setInBundleTotalCashCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalCashCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalCashCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalCashCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalCashCalculated", null, null));
      }
      importView.InBundleTotalCashCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalCashCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalCashCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCashCalculated", null, null));
      }
      try {
          setInBundleTotalCashCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalCashCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCashCalculated", null, null));
      }
   }
 
   public double getInBundleTotalChequeCalculated() {
      return importView.InBundleTotalChequeCalculated;
   }
   public void setInBundleTotalChequeCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalChequeCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalChequeCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalChequeCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalChequeCalculated", null, null));
      }
      importView.InBundleTotalChequeCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalChequeCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalChequeCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalChequeCalculated", null, null));
      }
      try {
          setInBundleTotalChequeCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalChequeCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalChequeCalculated", null, null));
      }
   }
 
   public double getInBundleTotalPostOrderCalculated() {
      return importView.InBundleTotalPostOrderCalculated;
   }
   public void setInBundleTotalPostOrderCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalPostOrderCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalPostOrderCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalPostOrderCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalPostOrderCalculated", null, null));
      }
      importView.InBundleTotalPostOrderCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalPostOrderCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalPostOrderCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalPostOrderCalculated", null, null));
      }
      try {
          setInBundleTotalPostOrderCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalPostOrderCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalPostOrderCalculated", null, null));
      }
   }
 
   public double getInBundleTotalMoneyOrderCalculated() {
      return importView.InBundleTotalMoneyOrderCalculated;
   }
   public void setInBundleTotalMoneyOrderCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalMoneyOrderCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalMoneyOrderCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalMoneyOrderCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalMoneyOrderCalculated", null, null));
      }
      importView.InBundleTotalMoneyOrderCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalMoneyOrderCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalMoneyOrderCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalMoneyOrderCalculated", null, null));
      }
      try {
          setInBundleTotalMoneyOrderCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalMoneyOrderCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalMoneyOrderCalculated", null, null));
      }
   }
 
   public double getInBundleTotalForeignDraftCalculated() {
      return importView.InBundleTotalForeignDraftCalculated;
   }
   public void setInBundleTotalForeignDraftCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalForeignDraftCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalForeignDraftCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalForeignDraftCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalForeignDraftCalculated", null, null));
      }
      importView.InBundleTotalForeignDraftCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalForeignDraftCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalForeignDraftCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalForeignDraftCalculated", null, null));
      }
      try {
          setInBundleTotalForeignDraftCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalForeignDraftCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalForeignDraftCalculated", null, null));
      }
   }
 
   public double getInBundleTotalTmoCalculated() {
      return importView.InBundleTotalTmoCalculated;
   }
   public void setInBundleTotalTmoCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalTmoCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalTmoCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalTmoCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalTmoCalculated", null, null));
      }
      importView.InBundleTotalTmoCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalTmoCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalTmoCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalTmoCalculated", null, null));
      }
      try {
          setInBundleTotalTmoCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalTmoCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalTmoCalculated", null, null));
      }
   }
 
   public double getInBundleTotalDebitsCalculated() {
      return importView.InBundleTotalDebitsCalculated;
   }
   public void setInBundleTotalDebitsCalculated(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalDebitsCalculated has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalDebitsCalculated", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalDebitsCalculated has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalDebitsCalculated", null, null));
      }
      importView.InBundleTotalDebitsCalculated = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalDebitsCalculated(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalDebitsCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalDebitsCalculated", null, null));
      }
      try {
          setInBundleTotalDebitsCalculated(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalDebitsCalculated is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalDebitsCalculated", null, null));
      }
   }
 
   public double getInBundleTotalCashEntered() {
      return importView.InBundleTotalCashEntered;
   }
   public void setInBundleTotalCashEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalCashEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalCashEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalCashEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalCashEntered", null, null));
      }
      importView.InBundleTotalCashEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalCashEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalCashEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCashEntered", null, null));
      }
      try {
          setInBundleTotalCashEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalCashEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalCashEntered", null, null));
      }
   }
 
   public double getInBundleTotalChequeEntered() {
      return importView.InBundleTotalChequeEntered;
   }
   public void setInBundleTotalChequeEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalChequeEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalChequeEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalChequeEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalChequeEntered", null, null));
      }
      importView.InBundleTotalChequeEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalChequeEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalChequeEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalChequeEntered", null, null));
      }
      try {
          setInBundleTotalChequeEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalChequeEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalChequeEntered", null, null));
      }
   }
 
   public double getInBundleTotalPostOrderEntered() {
      return importView.InBundleTotalPostOrderEntered;
   }
   public void setInBundleTotalPostOrderEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalPostOrderEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalPostOrderEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalPostOrderEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalPostOrderEntered", null, null));
      }
      importView.InBundleTotalPostOrderEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalPostOrderEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalPostOrderEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalPostOrderEntered", null, null));
      }
      try {
          setInBundleTotalPostOrderEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalPostOrderEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalPostOrderEntered", null, null));
      }
   }
 
   public double getInBundleTotalMoneyOrderEntered() {
      return importView.InBundleTotalMoneyOrderEntered;
   }
   public void setInBundleTotalMoneyOrderEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalMoneyOrderEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalMoneyOrderEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalMoneyOrderEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalMoneyOrderEntered", null, null));
      }
      importView.InBundleTotalMoneyOrderEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalMoneyOrderEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalMoneyOrderEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalMoneyOrderEntered", null, null));
      }
      try {
          setInBundleTotalMoneyOrderEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalMoneyOrderEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalMoneyOrderEntered", null, null));
      }
   }
 
   public double getInBundleTotalForeignDraftEntered() {
      return importView.InBundleTotalForeignDraftEntered;
   }
   public void setInBundleTotalForeignDraftEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalForeignDraftEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalForeignDraftEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalForeignDraftEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalForeignDraftEntered", null, null));
      }
      importView.InBundleTotalForeignDraftEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalForeignDraftEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalForeignDraftEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalForeignDraftEntered", null, null));
      }
      try {
          setInBundleTotalForeignDraftEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalForeignDraftEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalForeignDraftEntered", null, null));
      }
   }
 
   public double getInBundleTotalTmoEntered() {
      return importView.InBundleTotalTmoEntered;
   }
   public void setInBundleTotalTmoEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalTmoEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalTmoEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalTmoEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalTmoEntered", null, null));
      }
      importView.InBundleTotalTmoEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalTmoEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalTmoEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalTmoEntered", null, null));
      }
      try {
          setInBundleTotalTmoEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalTmoEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalTmoEntered", null, null));
      }
   }
 
   public double getInBundleTotalDebitsEntered() {
      return importView.InBundleTotalDebitsEntered;
   }
   public void setInBundleTotalDebitsEntered(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InBundleTotalDebitsEntered has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBundleTotalDebitsEntered", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InBundleTotalDebitsEntered has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InBundleTotalDebitsEntered", null, null));
      }
      importView.InBundleTotalDebitsEntered = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBundleTotalDebitsEntered(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBundleTotalDebitsEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalDebitsEntered", null, null));
      }
      try {
          setInBundleTotalDebitsEntered(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBundleTotalDebitsEntered is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBundleTotalDebitsEntered", null, null));
      }
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
 
   public String getInNameCsfStringsString40() {
      return FixedStringAttr.valueOf(importView.InNameCsfStringsString40, 40);
   }
   public void setInNameCsfStringsString40(String s)
      throws PropertyVetoException {
      if (s.length() > 40) {
         throw new PropertyVetoException("InNameCsfStringsString40 must be <= 40 characters.",
               new PropertyChangeEvent (this, "InNameCsfStringsString40", null, null));
      }
      importView.InNameCsfStringsString40 = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public double getInStudentAccountBalance() {
      return importView.InStudentAccountBalance;
   }
   public void setInStudentAccountBalance(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InStudentAccountBalance has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountBalance", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountBalance has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountBalance", null, null));
      }
      importView.InStudentAccountBalance = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountBalance(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountBalance", null, null));
      }
      try {
          setInStudentAccountBalance(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountBalance", null, null));
      }
   }
 
   public double getInStudentAccountDueImmediately() {
      return importView.InStudentAccountDueImmediately;
   }
   public void setInStudentAccountDueImmediately(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InStudentAccountDueImmediately has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountDueImmediately", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountDueImmediately has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountDueImmediately", null, null));
      }
      importView.InStudentAccountDueImmediately = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountDueImmediately(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountDueImmediately is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountDueImmediately", null, null));
      }
      try {
          setInStudentAccountDueImmediately(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountDueImmediately is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountDueImmediately", null, null));
      }
   }
 
   public double getInDueImmediatelyIefSuppliedTotalCurrency() {
      return importView.InDueImmediatelyIefSuppliedTotalCurrency;
   }
   public void setInDueImmediatelyIefSuppliedTotalCurrency(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InDueImmediatelyIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InDueImmediatelyIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedTotalCurrency", null, null));
      }
      importView.InDueImmediatelyIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInDueImmediatelyIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InDueImmediatelyIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInDueImmediatelyIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InDueImmediatelyIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInDueImmediatelyIefSuppliedAverageCurrency() {
      return importView.InDueImmediatelyIefSuppliedAverageCurrency;
   }
   public void setInDueImmediatelyIefSuppliedAverageCurrency(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InDueImmediatelyIefSuppliedAverageCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedAverageCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InDueImmediatelyIefSuppliedAverageCurrency has more than 9 integral digits.",
               new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedAverageCurrency", null, null));
      }
      importView.InDueImmediatelyIefSuppliedAverageCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInDueImmediatelyIefSuppliedAverageCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InDueImmediatelyIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedAverageCurrency", null, null));
      }
      try {
          setInDueImmediatelyIefSuppliedAverageCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InDueImmediatelyIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InDueImmediatelyIefSuppliedAverageCurrency", null, null));
      }
   }
 
   public double getInMrDueIefSuppliedAverageCurrency() {
      return importView.InMrDueIefSuppliedAverageCurrency;
   }
   public void setInMrDueIefSuppliedAverageCurrency(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InMrDueIefSuppliedAverageCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMrDueIefSuppliedAverageCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InMrDueIefSuppliedAverageCurrency has more than 9 integral digits.",
               new PropertyChangeEvent (this, "InMrDueIefSuppliedAverageCurrency", null, null));
      }
      importView.InMrDueIefSuppliedAverageCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMrDueIefSuppliedAverageCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMrDueIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrDueIefSuppliedAverageCurrency", null, null));
      }
      try {
          setInMrDueIefSuppliedAverageCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMrDueIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrDueIefSuppliedAverageCurrency", null, null));
      }
   }
 
   public double getInSmarctcardBundleDocumentTotalAmount() {
      return importView.InSmarctcardBundleDocumentTotalAmount;
   }
   public void setInSmarctcardBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InSmarctcardBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmarctcardBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InSmarctcardBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InSmarctcardBundleDocumentTotalAmount", null, null));
      }
      importView.InSmarctcardBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmarctcardBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmarctcardBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmarctcardBundleDocumentTotalAmount", null, null));
      }
      try {
          setInSmarctcardBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmarctcardBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmarctcardBundleDocumentTotalAmount", null, null));
      }
   }
 
   public double getInSmartcardAndApplDueWsAcademicYearSmartcardCost() {
      return importView.InSmartcardAndApplDueWsAcademicYearSmartcardCost;
   }
   public void setInSmartcardAndApplDueWsAcademicYearSmartcardCost(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearSmartcardCost has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearSmartcardCost", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearSmartcardCost has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearSmartcardCost", null, null));
      }
      importView.InSmartcardAndApplDueWsAcademicYearSmartcardCost = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmartcardAndApplDueWsAcademicYearSmartcardCost(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearSmartcardCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearSmartcardCost", null, null));
      }
      try {
          setInSmartcardAndApplDueWsAcademicYearSmartcardCost(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearSmartcardCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearSmartcardCost", null, null));
      }
   }
 
   public double getInSmartcardAndApplDueWsAcademicYearApplicationCost() {
      return importView.InSmartcardAndApplDueWsAcademicYearApplicationCost;
   }
   public void setInSmartcardAndApplDueWsAcademicYearApplicationCost(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearApplicationCost has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearApplicationCost", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearApplicationCost has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearApplicationCost", null, null));
      }
      importView.InSmartcardAndApplDueWsAcademicYearApplicationCost = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmartcardAndApplDueWsAcademicYearApplicationCost(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearApplicationCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearApplicationCost", null, null));
      }
      try {
          setInSmartcardAndApplDueWsAcademicYearApplicationCost(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearApplicationCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearApplicationCost", null, null));
      }
   }
 
   public double getInSmartcardAndApplDueWsAcademicYearOnlineApplFee() {
      return importView.InSmartcardAndApplDueWsAcademicYearOnlineApplFee;
   }
   public void setInSmartcardAndApplDueWsAcademicYearOnlineApplFee(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearOnlineApplFee has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearOnlineApplFee", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearOnlineApplFee has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearOnlineApplFee", null, null));
      }
      importView.InSmartcardAndApplDueWsAcademicYearOnlineApplFee = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmartcardAndApplDueWsAcademicYearOnlineApplFee(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearOnlineApplFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearOnlineApplFee", null, null));
      }
      try {
          setInSmartcardAndApplDueWsAcademicYearOnlineApplFee(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmartcardAndApplDueWsAcademicYearOnlineApplFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardAndApplDueWsAcademicYearOnlineApplFee", null, null));
      }
   }
 
   public double getInStudyDueIefSuppliedAverageCurrency() {
      return importView.InStudyDueIefSuppliedAverageCurrency;
   }
   public void setInStudyDueIefSuppliedAverageCurrency(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InStudyDueIefSuppliedAverageCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudyDueIefSuppliedAverageCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InStudyDueIefSuppliedAverageCurrency has more than 9 integral digits.",
               new PropertyChangeEvent (this, "InStudyDueIefSuppliedAverageCurrency", null, null));
      }
      importView.InStudyDueIefSuppliedAverageCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudyDueIefSuppliedAverageCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudyDueIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudyDueIefSuppliedAverageCurrency", null, null));
      }
      try {
          setInStudyDueIefSuppliedAverageCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudyDueIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudyDueIefSuppliedAverageCurrency", null, null));
      }
   }
 
   public String getInDtCrIndCsfStringsString6() {
      return FixedStringAttr.valueOf(importView.InDtCrIndCsfStringsString6, 6);
   }
   public void setInDtCrIndCsfStringsString6(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InDtCrIndCsfStringsString6 must be <= 6 characters.",
               new PropertyChangeEvent (this, "InDtCrIndCsfStringsString6", null, null));
      }
      importView.InDtCrIndCsfStringsString6 = FixedStringAttr.valueOf(s, (short)6);
   }
 
   public double getInMrStudentAccountBalance() {
      return importView.InMrStudentAccountBalance;
   }
   public void setInMrStudentAccountBalance(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("InMrStudentAccountBalance has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMrStudentAccountBalance", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InMrStudentAccountBalance has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InMrStudentAccountBalance", null, null));
      }
      importView.InMrStudentAccountBalance = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMrStudentAccountBalance(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMrStudentAccountBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrStudentAccountBalance", null, null));
      }
      try {
          setInMrStudentAccountBalance(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMrStudentAccountBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMrStudentAccountBalance", null, null));
      }
   }
 
   public double getIn3gBundleDocumentTotalAmount() {
      return importView.In3gBundleDocumentTotalAmount;
   }
   public void setIn3gBundleDocumentTotalAmount(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("In3gBundleDocumentTotalAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "In3gBundleDocumentTotalAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("In3gBundleDocumentTotalAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "In3gBundleDocumentTotalAmount", null, null));
      }
      importView.In3gBundleDocumentTotalAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringIn3gBundleDocumentTotalAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("In3gBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "In3gBundleDocumentTotalAmount", null, null));
      }
      try {
          setIn3gBundleDocumentTotalAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("In3gBundleDocumentTotalAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "In3gBundleDocumentTotalAmount", null, null));
      }
   }
 
   public double getIn3gDueStudent3gModemFee() {
      return importView.In3gDueStudent3gModemFee;
   }
   public void setIn3gDueStudent3gModemFee(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("In3gDueStudent3gModemFee has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "In3gDueStudent3gModemFee", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("In3gDueStudent3gModemFee has more than 4 integral digits.",
               new PropertyChangeEvent (this, "In3gDueStudent3gModemFee", null, null));
      }
      importView.In3gDueStudent3gModemFee = DoubleAttr.valueOf(s);
   }
   public void setAsStringIn3gDueStudent3gModemFee(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("In3gDueStudent3gModemFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "In3gDueStudent3gModemFee", null, null));
      }
      try {
          setIn3gDueStudent3gModemFee(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("In3gDueStudent3gModemFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "In3gDueStudent3gModemFee", null, null));
      }
   }
 
   public double getIn3gDueStudent3gSimFee() {
      return importView.In3gDueStudent3gSimFee;
   }
   public void setIn3gDueStudent3gSimFee(double s)
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
      if (decimal_found == true && decimals > 2) {
         throw new PropertyVetoException("In3gDueStudent3gSimFee has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "In3gDueStudent3gSimFee", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("In3gDueStudent3gSimFee has more than 4 integral digits.",
               new PropertyChangeEvent (this, "In3gDueStudent3gSimFee", null, null));
      }
      importView.In3gDueStudent3gSimFee = DoubleAttr.valueOf(s);
   }
   public void setAsStringIn3gDueStudent3gSimFee(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("In3gDueStudent3gSimFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "In3gDueStudent3gSimFee", null, null));
      }
      try {
          setIn3gDueStudent3gSimFee(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("In3gDueStudent3gSimFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "In3gDueStudent3gSimFee", null, null));
      }
   }
 
   public String getOutLdDtCrIndCsfStringsString6() {
      return FixedStringAttr.valueOf(exportView.OutLdDtCrIndCsfStringsString6, 6);
   }
 
   public double getOutLdDueStudentAccountBalance() {
      return exportView.OutLdDueStudentAccountBalance;
   }
 
   public double getOutLdBundleDocumentTotalAmount() {
      return exportView.OutLdBundleDocumentTotalAmount;
   }
 
   public double getOutApplicationBundleDocumentTotalAmount() {
      return exportView.OutApplicationBundleDocumentTotalAmount;
   }
 
   public String getOutApplicationWizfuncReportingControlFunction() {
      return StringAttr.valueOf(exportView.OutApplicationWizfuncReportingControlFunction);
   }
 
   public String getOutApplicationWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutApplicationWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutApplicationWizfuncReportingControlProgramName() {
      return FixedStringAttr.valueOf(exportView.OutApplicationWizfuncReportingControlProgramName, 8);
   }
 
   public short getOutApplicationWizfuncReportingControlReportWidth() {
      return exportView.OutApplicationWizfuncReportingControlReportWidth;
   }
 
   public int getOutApplicationWizfuncReportingControlStartingLineCount() {
      return exportView.OutApplicationWizfuncReportingControlStartingLineCount;
   }
 
   public int getOutApplicationWizfuncReportingControlEndingLineCount() {
      return exportView.OutApplicationWizfuncReportingControlEndingLineCount;
   }
 
   public String getOutApplicationWizfuncReportingControlSupressFormFeed() {
      return FixedStringAttr.valueOf(exportView.OutApplicationWizfuncReportingControlSupressFormFeed, 1);
   }
 
   public String getOutApplicationWizfuncReportingControlPrintOptions() {
      return StringAttr.valueOf(exportView.OutApplicationWizfuncReportingControlPrintOptions);
   }
 
   public String getOutApplicationWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutApplicationWizfuncReportingControlPrinterCode);
   }
 
   public double getOutWsSmartCardDataBalance() {
      return exportView.OutWsSmartCardDataBalance;
   }
 
   public String getOutCvvWsPostilionTranStartCvvNr() {
      return FixedStringAttr.valueOf(exportView.OutCvvWsPostilionTranStartCvvNr, 3);
   }
 
   public String getOutErrorIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutErrorIefSuppliedFlag, 1);
   }
 
   public String getOutRetryXtnIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutRetryXtnIefSuppliedFlag, 1);
   }
 
   public double getOutSmartcardAndApplCostWsAcademicYearSmartcardCost() {
      return exportView.OutSmartcardAndApplCostWsAcademicYearSmartcardCost;
   }
 
   public double getOutSmartcardAndApplCostWsAcademicYearApplicationCost() {
      return exportView.OutSmartcardAndApplCostWsAcademicYearApplicationCost;
   }
 
   public double getOutMrReApplicationCostMrFlagAmount() {
      return exportView.OutMrReApplicationCostMrFlagAmount;
   }
 
   public double getOutMrFirstApplicationCostMrFlagAmount() {
      return exportView.OutMrFirstApplicationCostMrFlagAmount;
   }
 
   public int getOutWsAddressV2ReferenceNo() {
      return exportView.OutWsAddressV2ReferenceNo;
   }
 
   public short getOutWsAddressV2Type() {
      return exportView.OutWsAddressV2Type;
   }
 
   public short getOutWsAddressV2Category() {
      return exportView.OutWsAddressV2Category;
   }
 
   public String getOutWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2HomeNumber, 28);
   }
 
   public String getOutWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CellNumber, 20);
   }
 
   public String getOutWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2EmailAddress, 60);
   }
 
   public String getOutWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine1, 28);
   }
 
   public String getOutWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine2, 28);
   }
 
   public String getOutWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine3, 28);
   }
 
   public String getOutWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine4, 28);
   }
 
   public String getOutWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine5, 28);
   }
 
   public String getOutWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressLine6, 28);
   }
 
   public short getOutWsAddressV2PostalCode() {
      return exportView.OutWsAddressV2PostalCode;
   }
 
   public String getOutSourceBundleMkBundleOrigin() {
      return FixedStringAttr.valueOf(exportView.OutSourceBundleMkBundleOrigin, 2);
   }
 
   public int getOutSourceBundleMkUserCode() {
      return exportView.OutSourceBundleMkUserCode;
   }
 
   public String getOutSmartcardWizfuncReportingControlFunction() {
      return StringAttr.valueOf(exportView.OutSmartcardWizfuncReportingControlFunction);
   }
 
   public String getOutSmartcardWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutSmartcardWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutSmartcardWizfuncReportingControlProgramName() {
      return FixedStringAttr.valueOf(exportView.OutSmartcardWizfuncReportingControlProgramName, 8);
   }
 
   public short getOutSmartcardWizfuncReportingControlReportWidth() {
      return exportView.OutSmartcardWizfuncReportingControlReportWidth;
   }
 
   public int getOutSmartcardWizfuncReportingControlStartingLineCount() {
      return exportView.OutSmartcardWizfuncReportingControlStartingLineCount;
   }
 
   public int getOutSmartcardWizfuncReportingControlEndingLineCount() {
      return exportView.OutSmartcardWizfuncReportingControlEndingLineCount;
   }
 
   public String getOutSmartcardWizfuncReportingControlSupressFormFeed() {
      return FixedStringAttr.valueOf(exportView.OutSmartcardWizfuncReportingControlSupressFormFeed, 1);
   }
 
   public String getOutSmartcardWizfuncReportingControlPrintOptions() {
      return StringAttr.valueOf(exportView.OutSmartcardWizfuncReportingControlPrintOptions);
   }
 
   public String getOutSmartcardWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutSmartcardWizfuncReportingControlPrinterCode);
   }
 
   public String getOutStudentAccountClassificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountClassificationCode, 2);
   }
 
   public double getOutDueStudentAccountBalance() {
      return exportView.OutDueStudentAccountBalance;
   }
 
   public double getOutDueStudentAccountDueImmediately() {
      return exportView.OutDueStudentAccountDueImmediately;
   }
 
   public String getOutCreditCardProcessedIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCreditCardProcessedIefSuppliedFlag, 1);
   }
 
   public String getOutDuplCredCardIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutDuplCredCardIefSuppliedFlag, 1);
   }
 
   public int getOutWsPostilionTranStartPosSystemIdentifier() {
      return exportView.OutWsPostilionTranStartPosSystemIdentifier;
   }
 
   public short getOutWsPostilionTranStartMessageType() {
      return exportView.OutWsPostilionTranStartMessageType;
   }
 
   public short getOutWsPostilionTranStartTestTransactionInd() {
      return exportView.OutWsPostilionTranStartTestTransactionInd;
   }
 
   public short getOutWsPostilionTranStartTransactionType() {
      return exportView.OutWsPostilionTranStartTransactionType;
   }
 
   public int getOutWsPostilionTranStartSystemTraceAuditNr() {
      return exportView.OutWsPostilionTranStartSystemTraceAuditNr;
   }
 
   public double getOutWsPostilionTranStartRetrievalReferenceNr() {
      return exportView.OutWsPostilionTranStartRetrievalReferenceNr;
   }
 
   public short getOutWsPostilionTranStartAccountType() {
      return exportView.OutWsPostilionTranStartAccountType;
   }
 
   public int getOutWsPostilionTranStartPosJournalNr() {
      return exportView.OutWsPostilionTranStartPosJournalNr;
   }
 
   public short getOutWsPostilionTranStartPosLaneNr() {
      return exportView.OutWsPostilionTranStartPosLaneNr;
   }
 
   public int getOutWsPostilionTranStartPosOperatorId() {
      return exportView.OutWsPostilionTranStartPosOperatorId;
   }
 
   public String getOutWsPostilionTranStartLocalDate() {
      return FixedStringAttr.valueOf(exportView.OutWsPostilionTranStartLocalDate, 4);
   }
 
   public Calendar getOutWsPostilionTranStartLocalTime() {
      return TimeAttr.toCalendar(exportView.OutWsPostilionTranStartLocalTime);
   }
   public int getAsIntOutWsPostilionTranStartLocalTime() {
      return TimeAttr.toInt(exportView.OutWsPostilionTranStartLocalTime);
   }
 
   public double getOutWsPostilionTranStartTotalAmount() {
      return exportView.OutWsPostilionTranStartTotalAmount;
   }
 
   public double getOutWsPostilionTranStartCashAmount() {
      return exportView.OutWsPostilionTranStartCashAmount;
   }
 
   public short getOutWsPostilionTranStartPanEntryMode() {
      return exportView.OutWsPostilionTranStartPanEntryMode;
   }
 
   public String getOutWsPostilionTranStartPanData() {
      return FixedStringAttr.valueOf(exportView.OutWsPostilionTranStartPanData, 49);
   }
 
   public String getOutWsPostilionTranStartCardExpirationDate() {
      return FixedStringAttr.valueOf(exportView.OutWsPostilionTranStartCardExpirationDate, 4);
   }
 
   public short getOutWsPostilionTranStartExtendedPaymentCode() {
      return exportView.OutWsPostilionTranStartExtendedPaymentCode;
   }
 
   public short getOutWsPostilionTranStartMultiTenderInd() {
      return exportView.OutWsPostilionTranStartMultiTenderInd;
   }
 
   public String getOutWsPostilionTranStartAuthorizationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsPostilionTranStartAuthorizationCode, 6);
   }
 
   public int getOutWsPostilionTranStartApplicDataBufLength() {
      return exportView.OutWsPostilionTranStartApplicDataBufLength;
   }
 
   public String getOutWsPostilionTranStartApplicDataBuf() {
      return FixedStringAttr.valueOf(exportView.OutWsPostilionTranStartApplicDataBuf, 10);
   }
 
   public String getOutModifyCcIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutModifyCcIefSuppliedFlag, 1);
   }
 
   public String getOutBatchrptDetailCsfStringsString28() {
      return FixedStringAttr.valueOf(exportView.OutBatchrptDetailCsfStringsString28, 28);
   }
 
   public String getOutWsWorkstationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsWorkstationCode, 10);
   }
 
   public int getOutStudentAccountMkStudentNr() {
      return exportView.OutStudentAccountMkStudentNr;
   }
 
   public double getOutStudentAccountMinRegistrationFee() {
      return exportView.OutStudentAccountMinRegistrationFee;
   }
 
   public double getOutStudentAccountCreditTotal() {
      return exportView.OutStudentAccountCreditTotal;
   }
 
   public double getOutStudentAccountFirstPayment() {
      return exportView.OutStudentAccountFirstPayment;
   }
 
   public double getOutStudentAccountSemester1Final() {
      return exportView.OutStudentAccountSemester1Final;
   }
 
   public double getOutStudentAccountBalance() {
      return exportView.OutStudentAccountBalance;
   }
 
   public String getOutStudentAccountUnclaimedCreditFlag() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountUnclaimedCreditFlag, 1);
   }
 
   public Calendar getOutStudentAccountLastTransDate() {
      return DateAttr.toCalendar(exportView.OutStudentAccountLastTransDate);
   }
   public int getAsIntOutStudentAccountLastTransDate() {
      return DateAttr.toInt(exportView.OutStudentAccountLastTransDate);
   }
 
   public short getOutStudentAccountTransCount() {
      return exportView.OutStudentAccountTransCount;
   }
 
   public short getOutStudentAccountEbankInd() {
      return exportView.OutStudentAccountEbankInd;
   }
 
   public String getOutStudentAccountCreditBlockedInd() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountCreditBlockedInd, 1);
   }
 
   public short getOutStudentAccountRefundBlockedInd() {
      return exportView.OutStudentAccountRefundBlockedInd;
   }
 
   public double getOutStudentAccountRefundForfeited() {
      return exportView.OutStudentAccountRefundForfeited;
   }
 
   public String getOutStudentAccountComments() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountComments, 60);
   }
 
   public double getOutStudentAccountDiverseTotal() {
      return exportView.OutStudentAccountDiverseTotal;
   }
 
   public double getOutStudentAccountRegTotal() {
      return exportView.OutStudentAccountRegTotal;
   }
 
   public double getOutStudentAccountFinalPayment() {
      return exportView.OutStudentAccountFinalPayment;
   }
 
   public String getOutStudentAccountSrcLevyPaid() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountSrcLevyPaid, 1);
   }
 
   public double getOutStudentAccountDueImmediately() {
      return exportView.OutStudentAccountDueImmediately;
   }
 
   public double getOutStudentAccountSem6FirstPayment() {
      return exportView.OutStudentAccountSem6FirstPayment;
   }
 
   public double getOutStudentAccountSem6FinalPayment() {
      return exportView.OutStudentAccountSem6FinalPayment;
   }
 
   public double getOutStudentAccountPrevSem6First() {
      return exportView.OutStudentAccountPrevSem6First;
   }
 
   public short getOutTempSfCalculatedAcademicYear() {
      return exportView.OutTempSfCalculatedAcademicYear;
   }
 
   public short getOutTempSfCalculatedAcademicPeriod() {
      return exportView.OutTempSfCalculatedAcademicPeriod;
   }
 
   public int getOutTempSfCalculatedMkStudentNr() {
      return exportView.OutTempSfCalculatedMkStudentNr;
   }
 
   public double getOutTempSfCalculatedStudyFeesCalculated() {
      return exportView.OutTempSfCalculatedStudyFeesCalculated;
   }
 
   public double getOutTempSfCalculatedStudyFeesMinimum() {
      return exportView.OutTempSfCalculatedStudyFeesMinimum;
   }
 
   public double getOutTempSfCalculatedAdditionFeesCalc() {
      return exportView.OutTempSfCalculatedAdditionFeesCalc;
   }
 
   public int getOutWsStudentAnnualRecordMkStudentNr() {
      return exportView.OutWsStudentAnnualRecordMkStudentNr;
   }
 
   public short getOutWsStudentAnnualRecordMkAcademicYear() {
      return exportView.OutWsStudentAnnualRecordMkAcademicYear;
   }
 
   public short getOutWsStudentAnnualRecordMkAcademicPeriod() {
      return exportView.OutWsStudentAnnualRecordMkAcademicPeriod;
   }
 
   public short getOutWsStudentAnnualRecordMkDisabilityTypeCode() {
      return exportView.OutWsStudentAnnualRecordMkDisabilityTypeCode;
   }
 
   public String getOutWsStudentAnnualRecordFellowStudentFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordFellowStudentFlag, 1);
   }
 
   public short getOutWsStudentAnnualRecordBursaryType() {
      return exportView.OutWsStudentAnnualRecordBursaryType;
   }
 
   public int getOutWsStudentAnnualRecordBursaryAmount() {
      return exportView.OutWsStudentAnnualRecordBursaryAmount;
   }
 
   public short getOutWsStudentAnnualRecordMkRegionalOfficeCode() {
      return exportView.OutWsStudentAnnualRecordMkRegionalOfficeCode;
   }
 
   public String getOutWsStudentAnnualRecordFirstRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordFirstRegistrationFlag, 1);
   }
 
   public String getOutWsStudentAnnualRecordPreviousUnisaFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordPreviousUnisaFlag, 1);
   }
 
   public String getOutWsStudentAnnualRecordMkPrevEducationalInstitCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMkPrevEducationalInstitCode, 4);
   }
 
   public short getOutWsStudentAnnualRecordPrevEducationalInstitutionYr() {
      return exportView.OutWsStudentAnnualRecordPrevEducationalInstitutionYr;
   }
 
   public String getOutWsStudentAnnualRecordMkOtherEducationalInstitCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMkOtherEducationalInstitCode, 4);
   }
 
   public String getOutWsStudentAnnualRecordRegistrationMethodCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordRegistrationMethodCode, 1);
   }
 
   public String getOutWsStudentAnnualRecordDespatchMethodCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordDespatchMethodCode, 1);
   }
 
   public String getOutWsStudentAnnualRecordMkOccupationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMkOccupationCode, 5);
   }
 
   public String getOutWsStudentAnnualRecordMkEconomicSectorCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMkEconomicSectorCode, 5);
   }
 
   public String getOutWsStudentAnnualRecordStatusCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordStatusCode, 2);
   }
 
   public short getOutWsStudentAnnualRecordLibraryAccessLevel() {
      return exportView.OutWsStudentAnnualRecordLibraryAccessLevel;
   }
 
   public short getOutWsStudentAnnualRecordSpecialLibraryAccessLevel() {
      return exportView.OutWsStudentAnnualRecordSpecialLibraryAccessLevel;
   }
 
   public String getOutWsStudentAnnualRecordMkHighestQualCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMkHighestQualCode, 5);
   }
 
   public String getOutWsStudentAnnualRecordLateRegistrationFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordLateRegistrationFlag, 1);
   }
 
   public int getOutWsStudentAnnualRecordPersonnelNr() {
      return exportView.OutWsStudentAnnualRecordPersonnelNr;
   }
 
   public String getOutWsStudentAnnualRecordTefsaApplicationFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordTefsaApplicationFlag, 1);
   }
 
   public String getOutWsStudentAnnualRecordMatriculationBoardFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAnnualRecordMatriculationBoardFlag, 1);
   }
 
   public String getOutReturnWizfuncReportingControlWizfuncReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutReturnWizfuncReportingControlWizfuncReturnCode, 8);
   }
 
   public String getOutReturnWizfuncReportingControlWizfuncReturnMessage() {
      return StringAttr.valueOf(exportView.OutReturnWizfuncReportingControlWizfuncReturnMessage);
   }
 
   public double getOutStudyFeeBundleDocumentTotalAmount() {
      return exportView.OutStudyFeeBundleDocumentTotalAmount;
   }
 
   public double getOutMrBundleDocumentTotalAmount() {
      return exportView.OutMrBundleDocumentTotalAmount;
   }
 
   public short getOutBundleDocumentDocumentSeqNr() {
      return exportView.OutBundleDocumentDocumentSeqNr;
   }
 
   public short getOutBundleDocumentJournalSequenceNr() {
      return exportView.OutBundleDocumentJournalSequenceNr;
   }
 
   public int getOutBundleDocumentChequeNr() {
      return exportView.OutBundleDocumentChequeNr;
   }
 
   public String getOutBundleDocumentDtCrInd() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentDtCrInd, 1);
   }
 
   public double getOutBundleDocumentTotalAmount() {
      return exportView.OutBundleDocumentTotalAmount;
   }
 
   public int getOutBundleDocumentMkStudentNr() {
      return exportView.OutBundleDocumentMkStudentNr;
   }
 
   public String getOutBundleDocumentChequePayeeName() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentChequePayeeName, 50);
   }
 
   public String getOutBundleDocumentChequePayeeInitials() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentChequePayeeInitials, 10);
   }
 
   public String getOutBundleDocumentLanguage() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentLanguage, 1);
   }
 
   public String getOutBundleDocumentPostGradFlag() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPostGradFlag, 1);
   }
 
   public int getOutBundleDocumentCreditCardExpiryDate() {
      return exportView.OutBundleDocumentCreditCardExpiryDate;
   }
 
   public short getOutBundleDocumentCreditCardBudgetPeriod() {
      return exportView.OutBundleDocumentCreditCardBudgetPeriod;
   }
 
   public double getOutBundleDocumentAmountCash() {
      return exportView.OutBundleDocumentAmountCash;
   }
 
   public double getOutBundleDocumentAmountCheque() {
      return exportView.OutBundleDocumentAmountCheque;
   }
 
   public double getOutBundleDocumentAmountPostOrder() {
      return exportView.OutBundleDocumentAmountPostOrder;
   }
 
   public double getOutBundleDocumentAmountMoneyOrder() {
      return exportView.OutBundleDocumentAmountMoneyOrder;
   }
 
   public double getOutBundleDocumentAmountForeignDraft() {
      return exportView.OutBundleDocumentAmountForeignDraft;
   }
 
   public double getOutBundleDocumentAmountCreditCard() {
      return exportView.OutBundleDocumentAmountCreditCard;
   }
 
   public double getOutBundleDocumentAmountTmo() {
      return exportView.OutBundleDocumentAmountTmo;
   }
 
   public int getOutBundleDocumentMkEbankBranchCode() {
      return exportView.OutBundleDocumentMkEbankBranchCode;
   }
 
   public String getOutBundleDocumentAccountNr() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentAccountNr, 20);
   }
 
   public String getOutBundleDocumentEbankAccountType() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentEbankAccountType, 1);
   }
 
   public String getOutBundleDocumentAccountHolder() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentAccountHolder, 28);
   }
 
   public String getOutBundleDocumentAutoSignatureFlag() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentAutoSignatureFlag, 1);
   }
 
   public String getOutBundleDocumentSalaryFlag() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentSalaryFlag, 1);
   }
 
   public String getOutBundleDocumentPaidByName() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByName, 28);
   }
 
   public String getOutBundleDocumentPaidByInitials() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByInitials, 10);
   }
 
   public String getOutBundleDocumentPaidByTitle() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByTitle, 10);
   }
 
   public String getOutBundleDocumentPaidByAddress1() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByAddress1, 28);
   }
 
   public String getOutBundleDocumentPaidByAddress2() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByAddress2, 28);
   }
 
   public String getOutBundleDocumentPaidByAddress3() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByAddress3, 28);
   }
 
   public String getOutBundleDocumentPaidByAddress4() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByAddress4, 28);
   }
 
   public String getOutBundleDocumentPaidByAddress5() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByAddress5, 28);
   }
 
   public String getOutBundleDocumentPaidByAddress6() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPaidByAddress6, 28);
   }
 
   public String getOutBundleDocumentClassification() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentClassification, 2);
   }
 
   public short getOutBundleDocumentPaidByPostCode() {
      return exportView.OutBundleDocumentPaidByPostCode;
   }
 
   public String getOutBundleDocumentCreditCardAuthCode() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentCreditCardAuthCode, 6);
   }
 
   public String getOutBundleDocumentPayMethod() {
      return FixedStringAttr.valueOf(exportView.OutBundleDocumentPayMethod, 7);
   }
 
   public String getOutMrWizfuncReportingControlFunction() {
      return StringAttr.valueOf(exportView.OutMrWizfuncReportingControlFunction);
   }
 
   public String getOutMrWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutMrWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutMrWizfuncReportingControlProgramName() {
      return FixedStringAttr.valueOf(exportView.OutMrWizfuncReportingControlProgramName, 8);
   }
 
   public short getOutMrWizfuncReportingControlReportWidth() {
      return exportView.OutMrWizfuncReportingControlReportWidth;
   }
 
   public int getOutMrWizfuncReportingControlStartingLineCount() {
      return exportView.OutMrWizfuncReportingControlStartingLineCount;
   }
 
   public int getOutMrWizfuncReportingControlEndingLineCount() {
      return exportView.OutMrWizfuncReportingControlEndingLineCount;
   }
 
   public String getOutMrWizfuncReportingControlSupressFormFeed() {
      return FixedStringAttr.valueOf(exportView.OutMrWizfuncReportingControlSupressFormFeed, 1);
   }
 
   public String getOutMrWizfuncReportingControlPrintOptions() {
      return StringAttr.valueOf(exportView.OutMrWizfuncReportingControlPrintOptions);
   }
 
   public String getOutMrWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutMrWizfuncReportingControlPrinterCode);
   }
 
   public String getOutLetterWizfuncReportingControlFunction() {
      return StringAttr.valueOf(exportView.OutLetterWizfuncReportingControlFunction);
   }
 
   public String getOutLetterWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutLetterWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutLetterWizfuncReportingControlProgramName() {
      return FixedStringAttr.valueOf(exportView.OutLetterWizfuncReportingControlProgramName, 8);
   }
 
   public short getOutLetterWizfuncReportingControlReportWidth() {
      return exportView.OutLetterWizfuncReportingControlReportWidth;
   }
 
   public int getOutLetterWizfuncReportingControlStartingLineCount() {
      return exportView.OutLetterWizfuncReportingControlStartingLineCount;
   }
 
   public int getOutLetterWizfuncReportingControlEndingLineCount() {
      return exportView.OutLetterWizfuncReportingControlEndingLineCount;
   }
 
   public String getOutLetterWizfuncReportingControlSupressFormFeed() {
      return FixedStringAttr.valueOf(exportView.OutLetterWizfuncReportingControlSupressFormFeed, 1);
   }
 
   public String getOutLetterWizfuncReportingControlPrintOptions() {
      return StringAttr.valueOf(exportView.OutLetterWizfuncReportingControlPrintOptions);
   }
 
   public String getOutLetterWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutLetterWizfuncReportingControlPrinterCode);
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
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
   }
 
   public String getOutWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCorrespondenceLanguage, 2);
   }
 
   public short getOutWsStudentLibraryBlackList() {
      return exportView.OutWsStudentLibraryBlackList;
   }
 
   public String getOutWsStudentExamFeesDebtFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentExamFeesDebtFlag, 1);
   }
 
   public String getOutWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFinancialBlockFlag, 1);
   }
 
   public String getOutWsStudentFirstNames() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFirstNames, 60);
   }
 
   public Calendar getOutWsStudentBirthDate() {
      return DateAttr.toCalendar(exportView.OutWsStudentBirthDate);
   }
   public int getAsIntOutWsStudentBirthDate() {
      return DateAttr.toInt(exportView.OutWsStudentBirthDate);
   }
 
   public String getOutWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCountryCode, 4);
   }
 
   public String getOutWsStudentSmartCardIssued() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSmartCardIssued, 1);
   }
 
   public String getOutWsStudentIdentityNr() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentIdentityNr, 13);
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public String getOutWsStudentApplicationStatus() {
      return StringAttr.valueOf(exportView.OutWsStudentApplicationStatus);
   }
 
   public String getOutWsStudentHandedOverFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentHandedOverFlag, 1);
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
 
   public String getOutCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1);
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
 
   public Calendar getOutBundleBundleDate() {
      return DateAttr.toCalendar(exportView.OutBundleBundleDate);
   }
   public int getAsIntOutBundleBundleDate() {
      return DateAttr.toInt(exportView.OutBundleBundleDate);
   }
 
   public short getOutBundleBundleNr() {
      return exportView.OutBundleBundleNr;
   }
 
   public short getOutBundleType() {
      return exportView.OutBundleType;
   }
 
   public short getOutBundleFinancialSection() {
      return exportView.OutBundleFinancialSection;
   }
 
   public short getOutBundleFinancialYear() {
      return exportView.OutBundleFinancialYear;
   }
 
   public short getOutBundleFinancialPeriod() {
      return exportView.OutBundleFinancialPeriod;
   }
 
   public String getOutBundleStatus() {
      return FixedStringAttr.valueOf(exportView.OutBundleStatus, 2);
   }
 
   public short getOutBundleLastDocumentNr() {
      return exportView.OutBundleLastDocumentNr;
   }
 
   public short getOutBundleCreditCardCount() {
      return exportView.OutBundleCreditCardCount;
   }
 
   public String getOutBundleInstitution() {
      return FixedStringAttr.valueOf(exportView.OutBundleInstitution, 1);
   }
 
   public double getOutBundleTotalCreditCardCalculated() {
      return exportView.OutBundleTotalCreditCardCalculated;
   }
 
   public double getOutBundleTotalCreditCalculated() {
      return exportView.OutBundleTotalCreditCalculated;
   }
 
   public double getOutBundleTotalCreditCardEntered() {
      return exportView.OutBundleTotalCreditCardEntered;
   }
 
   public double getOutBundleTotalCreditsEntered() {
      return exportView.OutBundleTotalCreditsEntered;
   }
 
   public int getOutBundleMkUserCode() {
      return exportView.OutBundleMkUserCode;
   }
 
   public String getOutBundleOriginatingSection() {
      return FixedStringAttr.valueOf(exportView.OutBundleOriginatingSection, 7);
   }
 
   public short getOutBundleChequeCount() {
      return exportView.OutBundleChequeCount;
   }
 
   public String getOutBundleMkBundleOrigin() {
      return FixedStringAttr.valueOf(exportView.OutBundleMkBundleOrigin, 2);
   }
 
   public String getOutBundleRegisteredMailFlag() {
      return FixedStringAttr.valueOf(exportView.OutBundleRegisteredMailFlag, 1);
   }
 
   public String getOutBundlePostGradFlag() {
      return FixedStringAttr.valueOf(exportView.OutBundlePostGradFlag, 1);
   }
 
   public int getOutBundleMkUserJnlApproved() {
      return exportView.OutBundleMkUserJnlApproved;
   }
 
   public String getOutBundleGeneralLedgerSource() {
      return FixedStringAttr.valueOf(exportView.OutBundleGeneralLedgerSource, 5);
   }
 
   public Calendar getOutBundleBundleCaptureDate() {
      return DateAttr.toCalendar(exportView.OutBundleBundleCaptureDate);
   }
   public int getAsIntOutBundleBundleCaptureDate() {
      return DateAttr.toInt(exportView.OutBundleBundleCaptureDate);
   }
 
   public double getOutBundleTotalCashCalculated() {
      return exportView.OutBundleTotalCashCalculated;
   }
 
   public double getOutBundleTotalChequeCalculated() {
      return exportView.OutBundleTotalChequeCalculated;
   }
 
   public double getOutBundleTotalPostOrderCalculated() {
      return exportView.OutBundleTotalPostOrderCalculated;
   }
 
   public double getOutBundleTotalMoneyOrderCalculated() {
      return exportView.OutBundleTotalMoneyOrderCalculated;
   }
 
   public double getOutBundleTotalForeignDraftCalculated() {
      return exportView.OutBundleTotalForeignDraftCalculated;
   }
 
   public double getOutBundleTotalTmoCalculated() {
      return exportView.OutBundleTotalTmoCalculated;
   }
 
   public double getOutBundleTotalDebitsCalculated() {
      return exportView.OutBundleTotalDebitsCalculated;
   }
 
   public double getOutBundleTotalCashEntered() {
      return exportView.OutBundleTotalCashEntered;
   }
 
   public double getOutBundleTotalChequeEntered() {
      return exportView.OutBundleTotalChequeEntered;
   }
 
   public double getOutBundleTotalPostOrderEntered() {
      return exportView.OutBundleTotalPostOrderEntered;
   }
 
   public double getOutBundleTotalMoneyOrderEntered() {
      return exportView.OutBundleTotalMoneyOrderEntered;
   }
 
   public double getOutBundleTotalForeignDraftEntered() {
      return exportView.OutBundleTotalForeignDraftEntered;
   }
 
   public double getOutBundleTotalTmoEntered() {
      return exportView.OutBundleTotalTmoEntered;
   }
 
   public double getOutBundleTotalDebitsEntered() {
      return exportView.OutBundleTotalDebitsEntered;
   }
 
   public final int OutTmpAllocGroupMax = 20;
   public short getOutTmpAllocGroupCount() {
      return (short)(exportView.OutTmpAllocGroup_MA);
   };
 
   public short getOutTmpGBundleDocumentAllocAllocSeqNr(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutTmpGBundleDocumentAllocAllocSeqNr[index];
   }
 
   public String getOutTmpGBundleDocumentAllocMkGlFund(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutTmpGBundleDocumentAllocMkGlFund[index], 5);
   }
 
   public String getOutTmpGBundleDocumentAllocMkGlEntity(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutTmpGBundleDocumentAllocMkGlEntity[index], 5);
   }
 
   public String getOutTmpGBundleDocumentAllocMkGlAccount(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutTmpGBundleDocumentAllocMkGlAccount[index], 5);
   }
 
   public String getOutTmpGBundleDocumentAllocMkGlItem(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutTmpGBundleDocumentAllocMkGlItem[index], 5);
   }
 
   public double getOutTmpGBundleDocumentAllocAmount(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutTmpGBundleDocumentAllocAmount[index];
   }
 
   public String getOutTmpGBundleDocumentAllocComments(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutTmpGBundleDocumentAllocComments[index], 60);
   }
 
   public short getOutTmpGBundleDocumentAllocMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutTmpGBundleDocumentAllocMkAcademicYear[index];
   }
 
   public short getOutTmpGBundleDocumentAllocMkAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutTmpGBundleDocumentAllocMkAcademicPeriod[index];
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationInUseFlag, 1);
   }
 
   public String getOutWsQualificationEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationEngDescription, 28);
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
 
   public short getOutWsAddressType() {
      return exportView.OutWsAddressType;
   }
 
   public short getOutWsAddressCategory() {
      return exportView.OutWsAddressCategory;
   }
 
   public String getOutNameCsfStringsString40() {
      return FixedStringAttr.valueOf(exportView.OutNameCsfStringsString40, 40);
   }
 
   public String getOutWsGlobalSystemVariablesDataDirectory() {
      return FixedStringAttr.valueOf(exportView.OutWsGlobalSystemVariablesDataDirectory, 50);
   }
 
   public double getOutDueImmediatelyIefSuppliedTotalCurrency() {
      return exportView.OutDueImmediatelyIefSuppliedTotalCurrency;
   }
 
   public double getOutDueImmediatelyIefSuppliedAverageCurrency() {
      return exportView.OutDueImmediatelyIefSuppliedAverageCurrency;
   }
 
   public double getOutMrDueIefSuppliedAverageCurrency() {
      return exportView.OutMrDueIefSuppliedAverageCurrency;
   }
 
   public double getOutSmartcardBundleDocumentTotalAmount() {
      return exportView.OutSmartcardBundleDocumentTotalAmount;
   }
 
   public short getOutSmartcardAndApplDueWsAcademicYearYear() {
      return exportView.OutSmartcardAndApplDueWsAcademicYearYear;
   }
 
   public short getOutSmartcardAndApplDueWsAcademicYearPeriod() {
      return exportView.OutSmartcardAndApplDueWsAcademicYearPeriod;
   }
 
   public double getOutSmartcardAndApplDueWsAcademicYearSmartcardCost() {
      return exportView.OutSmartcardAndApplDueWsAcademicYearSmartcardCost;
   }
 
   public double getOutSmartcardAndApplDueWsAcademicYearApplicationCost() {
      return exportView.OutSmartcardAndApplDueWsAcademicYearApplicationCost;
   }
 
   public double getOutSmartcardAndApplDueWsAcademicYearOnlineApplFee() {
      return exportView.OutSmartcardAndApplDueWsAcademicYearOnlineApplFee;
   }
 
   public double getOutStudyDueIefSuppliedAverageCurrency() {
      return exportView.OutStudyDueIefSuppliedAverageCurrency;
   }
 
   public String getOutputCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutputCsfStringsString500);
   }
 
   public String getOutDtCrIndCsfStringsString6() {
      return FixedStringAttr.valueOf(exportView.OutDtCrIndCsfStringsString6, 6);
   }
 
   public String getOutReceiptNoCsfStringsString17() {
      return FixedStringAttr.valueOf(exportView.OutReceiptNoCsfStringsString17, 17);
   }
 
   public double getOutMrStudentAccountBalance() {
      return exportView.OutMrStudentAccountBalance;
   }
 
   public double getOut3gBundleDocumentTotalAmount() {
      return exportView.Out3gBundleDocumentTotalAmount;
   }
 
   public double getOut3gDueStudent3gModemFee() {
      return exportView.Out3gDueStudent3gModemFee;
   }
 
   public double getOut3gDueStudent3gSimFee() {
      return exportView.Out3gDueStudent3gSimFee;
   }
 
   public String getOutLdWizfuncReportingControlFunction() {
      return StringAttr.valueOf(exportView.OutLdWizfuncReportingControlFunction);
   }
 
   public String getOutLdWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutLdWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutLdWizfuncReportingControlProgramName() {
      return FixedStringAttr.valueOf(exportView.OutLdWizfuncReportingControlProgramName, 8);
   }
 
   public short getOutLdWizfuncReportingControlReportWidth() {
      return exportView.OutLdWizfuncReportingControlReportWidth;
   }
 
   public int getOutLdWizfuncReportingControlStartingLineCount() {
      return exportView.OutLdWizfuncReportingControlStartingLineCount;
   }
 
   public int getOutLdWizfuncReportingControlEndingLineCount() {
      return exportView.OutLdWizfuncReportingControlEndingLineCount;
   }
 
   public String getOutLdWizfuncReportingControlSupressFormFeed() {
      return FixedStringAttr.valueOf(exportView.OutLdWizfuncReportingControlSupressFormFeed, 1);
   }
 
   public String getOutLdWizfuncReportingControlPrintOptions() {
      return StringAttr.valueOf(exportView.OutLdWizfuncReportingControlPrintOptions);
   }
 
   public String getOutLdWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutLdWizfuncReportingControlPrinterCode);
   }
 
};
