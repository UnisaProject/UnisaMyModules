package Srsms02h.Abean;
 
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
public class Srsms02sSendSmsToStudList  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srsms02sSendSmsToStudList");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Srsms02sSendSmsToStudList() {
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
 
 
   private Srsms02sSendSmsToStudListOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srsms02sSendSmsToStudListOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrsms02sSendSmsToStudListOperation();
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
      private Srsms02sSendSmsToStudList rP;
      operListener(Srsms02sSendSmsToStudList r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srsms02sSendSmsToStudList", "Listener heard that Srsms02sSendSmsToStudListOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srsms02sSendSmsToStudList ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srsms02sSendSmsToStudList");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srsms02sSendSmsToStudList ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srsms02sSendSmsToStudList";
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
      importView.InMagisterialSelectionGroup_MA = IntAttr.valueOf(InMagisterialSelectionGroupMax);
      importView.InRegistrationSelectionGroup_MA = IntAttr.valueOf(InRegistrationSelectionGroupMax);
      exportView.OutRcGroup_MA = IntAttr.getDefaultValue();
      exportView.OutListGroup_MA = IntAttr.getDefaultValue();
      exportView.OutMagisterialSelectionGroup_MA = IntAttr.getDefaultValue();
      exportView.OutRegistrationSelectionGroup_MA = IntAttr.getDefaultValue();
   }

   Srsms02h.SRSMS02S_IA importView = Srsms02h.SRSMS02S_IA.getInstance();
   Srsms02h.SRSMS02S_OA exportView = Srsms02h.SRSMS02S_OA.getInstance();
   public double getInTotalCostIefSuppliedTotalCurrency() {
      return importView.InTotalCostIefSuppliedTotalCurrency;
   }
   public void setInTotalCostIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InTotalCostIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InTotalCostIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InTotalCostIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InTotalCostIefSuppliedTotalCurrency", null, null));
      }
      importView.InTotalCostIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInTotalCostIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InTotalCostIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTotalCostIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInTotalCostIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InTotalCostIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InTotalCostIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInAvailableBugetAmountIefSuppliedTotalCurrency() {
      return importView.InAvailableBugetAmountIefSuppliedTotalCurrency;
   }
   public void setInAvailableBugetAmountIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InAvailableBugetAmountIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InAvailableBugetAmountIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InAvailableBugetAmountIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InAvailableBugetAmountIefSuppliedTotalCurrency", null, null));
      }
      importView.InAvailableBugetAmountIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInAvailableBugetAmountIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAvailableBugetAmountIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAvailableBugetAmountIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInAvailableBugetAmountIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAvailableBugetAmountIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAvailableBugetAmountIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInBudgetAmountIefSuppliedTotalCurrency() {
      return importView.InBudgetAmountIefSuppliedTotalCurrency;
   }
   public void setInBudgetAmountIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InBudgetAmountIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InBudgetAmountIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InBudgetAmountIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InBudgetAmountIefSuppliedTotalCurrency", null, null));
      }
      importView.InBudgetAmountIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInBudgetAmountIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InBudgetAmountIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBudgetAmountIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInBudgetAmountIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InBudgetAmountIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InBudgetAmountIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInSmsCostCostPerSms() {
      return importView.InSmsCostCostPerSms;
   }
   public void setInSmsCostCostPerSms(double s)
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
      if (decimal_found == true && decimals > 3) {
         throw new PropertyVetoException("InSmsCostCostPerSms has more than 3 fractional digits.",
               new PropertyChangeEvent (this, "InSmsCostCostPerSms", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InSmsCostCostPerSms has more than 2 integral digits.",
               new PropertyChangeEvent (this, "InSmsCostCostPerSms", null, null));
      }
      importView.InSmsCostCostPerSms = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmsCostCostPerSms(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsCostCostPerSms is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsCostCostPerSms", null, null));
      }
      try {
          setInSmsCostCostPerSms(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsCostCostPerSms is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsCostCostPerSms", null, null));
      }
   }
 
   public String getInRcDesriptionCsfStringsString60() {
      return FixedStringAttr.valueOf(importView.InRcDesriptionCsfStringsString60, 60);
   }
   public void setInRcDesriptionCsfStringsString60(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InRcDesriptionCsfStringsString60 must be <= 60 characters.",
               new PropertyChangeEvent (this, "InRcDesriptionCsfStringsString60", null, null));
      }
      importView.InRcDesriptionCsfStringsString60 = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public String getInFileNameWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(importView.InFileNameWizfuncReportingControlPathAndFilename);
   }
   public void setInFileNameWizfuncReportingControlPathAndFilename(String s)
      throws PropertyVetoException {
      if (s.length() > 256) {
         throw new PropertyVetoException("InFileNameWizfuncReportingControlPathAndFilename must be <= 256 characters.",
               new PropertyChangeEvent (this, "InFileNameWizfuncReportingControlPathAndFilename", null, null));
      }
      importView.InFileNameWizfuncReportingControlPathAndFilename = StringAttr.valueOf(s, (short)256);
   }
 
   public String getInSendSmsCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InSendSmsCsfStringsString1, 1);
   }
   public void setInSendSmsCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InSendSmsCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSendSmsCsfStringsString1", null, null));
      }
      importView.InSendSmsCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInMagisterialCriteriaTypeCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InMagisterialCriteriaTypeCsfStringsString1, 1);
   }
   public void setInMagisterialCriteriaTypeCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InMagisterialCriteriaTypeCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InMagisterialCriteriaTypeCsfStringsString1", null, null));
      }
      importView.InMagisterialCriteriaTypeCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInRegistrationCriteriaTypeCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InRegistrationCriteriaTypeCsfStringsString1, 1);
   }
   public void setInRegistrationCriteriaTypeCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InRegistrationCriteriaTypeCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InRegistrationCriteriaTypeCsfStringsString1", null, null));
      }
      importView.InRegistrationCriteriaTypeCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int InMagisterialSelectionGroupMax = 20;
   public short getInMagisterialSelectionGroupCount() {
      return (short)(importView.InMagisterialSelectionGroup_MA);
   };
 
   public void setInMagisterialSelectionGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InMagisterialSelectionGroupMax) {
         throw new PropertyVetoException("InMagisterialSelectionGroupCount value is not a valid value. (0 to 20)",
               new PropertyChangeEvent (this, "InMagisterialSelectionGroupCount", null, null));
      } else {
         importView.InMagisterialSelectionGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInMagisterialGpCsfStringsString15(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InMagisterialGpCsfStringsString15[index], 15);
   }
   public void setInMagisterialGpCsfStringsString15(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 15) {
         throw new PropertyVetoException("InMagisterialGpCsfStringsString15 must be <= 15 characters.",
               new PropertyChangeEvent (this, "InMagisterialGpCsfStringsString15", null, null));
      }
      importView.InMagisterialGpCsfStringsString15[index] = FixedStringAttr.valueOf(s, (short)15);
   }
 
   public final int InRegistrationSelectionGroupMax = 20;
   public short getInRegistrationSelectionGroupCount() {
      return (short)(importView.InRegistrationSelectionGroup_MA);
   };
 
   public void setInRegistrationSelectionGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InRegistrationSelectionGroupMax) {
         throw new PropertyVetoException("InRegistrationSelectionGroupCount value is not a valid value. (0 to 20)",
               new PropertyChangeEvent (this, "InRegistrationSelectionGroupCount", null, null));
      } else {
         importView.InRegistrationSelectionGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInRegistrationGpCsfStringsString15(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InRegistrationGpCsfStringsString15[index], 15);
   }
   public void setInRegistrationGpCsfStringsString15(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      if (s.length() > 15) {
         throw new PropertyVetoException("InRegistrationGpCsfStringsString15 must be <= 15 characters.",
               new PropertyChangeEvent (this, "InRegistrationGpCsfStringsString15", null, null));
      }
      importView.InRegistrationGpCsfStringsString15[index] = FixedStringAttr.valueOf(s, (short)15);
   }
 
   public short getInWsStudyUnitPeriodDetailMkAcademicYear() {
      return importView.InWsStudyUnitPeriodDetailMkAcademicYear;
   }
   public void setInWsStudyUnitPeriodDetailMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsStudyUnitPeriodDetailMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsStudyUnitPeriodDetailMkAcademicYear", null, null));
      }
      importView.InWsStudyUnitPeriodDetailMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudyUnitPeriodDetailMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudyUnitPeriodDetailMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudyUnitPeriodDetailMkAcademicYear", null, null));
      }
      try {
          setInWsStudyUnitPeriodDetailMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudyUnitPeriodDetailMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudyUnitPeriodDetailMkAcademicYear", null, null));
      }
   }
 
   public short getInWsStudyUnitPeriodDetailMkAcademicPeriod() {
      return importView.InWsStudyUnitPeriodDetailMkAcademicPeriod;
   }
   public void setInWsStudyUnitPeriodDetailMkAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsStudyUnitPeriodDetailMkAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsStudyUnitPeriodDetailMkAcademicPeriod", null, null));
      }
      importView.InWsStudyUnitPeriodDetailMkAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudyUnitPeriodDetailMkAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudyUnitPeriodDetailMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudyUnitPeriodDetailMkAcademicPeriod", null, null));
      }
      try {
          setInWsStudyUnitPeriodDetailMkAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudyUnitPeriodDetailMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudyUnitPeriodDetailMkAcademicPeriod", null, null));
      }
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
 
   public int getInSmsRequestBatchNr() {
      return importView.InSmsRequestBatchNr;
   }
   public void setInSmsRequestBatchNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InSmsRequestBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InSmsRequestBatchNr", null, null));
      }
      importView.InSmsRequestBatchNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestBatchNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestBatchNr", null, null));
      }
      try {
          setInSmsRequestBatchNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestBatchNr", null, null));
      }
   }
 
   public int getInSmsRequestMkPersNr() {
      return importView.InSmsRequestMkPersNr;
   }
   public void setInSmsRequestMkPersNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InSmsRequestMkPersNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InSmsRequestMkPersNr", null, null));
      }
      importView.InSmsRequestMkPersNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestMkPersNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestMkPersNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestMkPersNr", null, null));
      }
      try {
          setInSmsRequestMkPersNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestMkPersNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestMkPersNr", null, null));
      }
   }
 
   public String getInSmsRequestMkRcCode() {
      return StringAttr.valueOf(importView.InSmsRequestMkRcCode);
   }
   public void setInSmsRequestMkRcCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InSmsRequestMkRcCode must be <= 6 characters.",
               new PropertyChangeEvent (this, "InSmsRequestMkRcCode", null, null));
      }
      importView.InSmsRequestMkRcCode = StringAttr.valueOf(s, (short)6);
   }
 
   public short getInSmsRequestMkDepartmentCode() {
      return importView.InSmsRequestMkDepartmentCode;
   }
   public void setInSmsRequestMkDepartmentCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InSmsRequestMkDepartmentCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InSmsRequestMkDepartmentCode", null, null));
      }
      importView.InSmsRequestMkDepartmentCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestMkDepartmentCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestMkDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestMkDepartmentCode", null, null));
      }
      try {
          setInSmsRequestMkDepartmentCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestMkDepartmentCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestMkDepartmentCode", null, null));
      }
   }
 
   public Calendar getInSmsRequestRequestTimestamp() {
      return TimestampAttr.toCalendar(importView.InSmsRequestRequestTimestamp);
   }
   public String getAsStringInSmsRequestRequestTimestamp() {
      return TimestampAttr.toString(importView.InSmsRequestRequestTimestamp);
   }
   public void setInSmsRequestRequestTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InSmsRequestRequestTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestRequestTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSmsRequestRequestTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InSmsRequestRequestTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSmsRequestRequestTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InSmsRequestRequestTimestamp", null, null));
         }
      }
   }
 
   public String getInSmsRequestSmsMsg() {
      return FixedStringAttr.valueOf(importView.InSmsRequestSmsMsg, 160);
   }
   public void setInSmsRequestSmsMsg(String s)
      throws PropertyVetoException {
      if (s.length() > 160) {
         throw new PropertyVetoException("InSmsRequestSmsMsg must be <= 160 characters.",
               new PropertyChangeEvent (this, "InSmsRequestSmsMsg", null, null));
      }
      importView.InSmsRequestSmsMsg = FixedStringAttr.valueOf(s, (short)160);
   }
 
   public String getInSmsRequestControlCellNr() {
      return StringAttr.valueOf(importView.InSmsRequestControlCellNr);
   }
   public void setInSmsRequestControlCellNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSmsRequestControlCellNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSmsRequestControlCellNr", null, null));
      }
      importView.InSmsRequestControlCellNr = StringAttr.valueOf(s, (short)20);
   }
 
   public int getInSmsRequestMessageCnt() {
      return importView.InSmsRequestMessageCnt;
   }
   public void setInSmsRequestMessageCnt(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InSmsRequestMessageCnt has more than 7 digits.",
               new PropertyChangeEvent (this, "InSmsRequestMessageCnt", null, null));
      }
      importView.InSmsRequestMessageCnt = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestMessageCnt(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestMessageCnt is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestMessageCnt", null, null));
      }
      try {
          setInSmsRequestMessageCnt(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestMessageCnt is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestMessageCnt", null, null));
      }
   }
 
   public double getInSmsRequestTotalCost() {
      return importView.InSmsRequestTotalCost;
   }
   public void setInSmsRequestTotalCost(double s)
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
         throw new PropertyVetoException("InSmsRequestTotalCost has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmsRequestTotalCost", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InSmsRequestTotalCost has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InSmsRequestTotalCost", null, null));
      }
      importView.InSmsRequestTotalCost = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestTotalCost(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestTotalCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestTotalCost", null, null));
      }
      try {
          setInSmsRequestTotalCost(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestTotalCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestTotalCost", null, null));
      }
   }
 
   public short getInSmsRequestFromSystemGc26() {
      return importView.InSmsRequestFromSystemGc26;
   }
   public void setInSmsRequestFromSystemGc26(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InSmsRequestFromSystemGc26 has more than 3 digits.",
               new PropertyChangeEvent (this, "InSmsRequestFromSystemGc26", null, null));
      }
      importView.InSmsRequestFromSystemGc26 = ShortAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestFromSystemGc26(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestFromSystemGc26 is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestFromSystemGc26", null, null));
      }
      try {
          setInSmsRequestFromSystemGc26(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestFromSystemGc26 is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestFromSystemGc26", null, null));
      }
   }
 
   public int getInSmsRequestReasonGc27() {
      return importView.InSmsRequestReasonGc27;
   }
   public void setInSmsRequestReasonGc27(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSmsRequestReasonGc27 has more than 5 digits.",
               new PropertyChangeEvent (this, "InSmsRequestReasonGc27", null, null));
      }
      importView.InSmsRequestReasonGc27 = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsRequestReasonGc27(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsRequestReasonGc27 is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestReasonGc27", null, null));
      }
      try {
          setInSmsRequestReasonGc27(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsRequestReasonGc27 is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsRequestReasonGc27", null, null));
      }
   }
 
   public String getInSmsRequestSelectionCriteria() {
      return StringAttr.valueOf(importView.InSmsRequestSelectionCriteria);
   }
   public void setInSmsRequestSelectionCriteria(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 250) {
         throw new PropertyVetoException("InSmsRequestSelectionCriteria must be <= 250 characters.",
               new PropertyChangeEvent (this, "InSmsRequestSelectionCriteria", null, null));
      }
      importView.InSmsRequestSelectionCriteria = StringAttr.valueOf(s, (short)250);
   }
 
   public String getInSmsRequestProgramName() {
      return FixedStringAttr.valueOf(importView.InSmsRequestProgramName, 20);
   }
   public void setInSmsRequestProgramName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSmsRequestProgramName must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSmsRequestProgramName", null, null));
      }
      importView.InSmsRequestProgramName = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInSmsRequestControlCellNr2() {
      return FixedStringAttr.valueOf(importView.InSmsRequestControlCellNr2, 20);
   }
   public void setInSmsRequestControlCellNr2(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSmsRequestControlCellNr2 must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSmsRequestControlCellNr2", null, null));
      }
      importView.InSmsRequestControlCellNr2 = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInSmsRequestControlCellNr3() {
      return FixedStringAttr.valueOf(importView.InSmsRequestControlCellNr3, 20);
   }
   public void setInSmsRequestControlCellNr3(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSmsRequestControlCellNr3 must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSmsRequestControlCellNr3", null, null));
      }
      importView.InSmsRequestControlCellNr3 = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInErrorFlagCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InErrorFlagCsfStringsString1, 1);
   }
   public void setInErrorFlagCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InErrorFlagCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InErrorFlagCsfStringsString1", null, null));
      }
      importView.InErrorFlagCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInNovellCodeWsStaffEmailAddress() {
      return FixedStringAttr.valueOf(importView.InNovellCodeWsStaffEmailAddress, 60);
   }
   public void setInNovellCodeWsStaffEmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InNovellCodeWsStaffEmailAddress must be <= 60 characters.",
               new PropertyChangeEvent (this, "InNovellCodeWsStaffEmailAddress", null, null));
      }
      importView.InNovellCodeWsStaffEmailAddress = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public final int OutRcGroupMax = 25;
   public short getOutRcGroupCount() {
      return (short)(exportView.OutRcGroup_MA);
   };
 
   public String getOutGRcWsStaffRcCodeMkRcCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGRcWsStaffRcCodeMkRcCode[index], 6);
   }
 
   public String getOutGRcWsStaffRcCodeAppointmentType(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGRcWsStaffRcCodeAppointmentType[index], 1);
   }
 
   public String getOutGRcDescriptionCsfStringsString60(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGRcDescriptionCsfStringsString60[index], 60);
   }
 
   public double getOutGRcAvailableBudgetIefSuppliedTotalCurrency(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutGRcAvailableBudgetIefSuppliedTotalCurrency[index];
   }
 
   public double getOutGRcTotalBudgetIefSuppliedTotalCurrency(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return exportView.OutGRcTotalBudgetIefSuppliedTotalCurrency[index];
   }
 
   public double getOutTotalCostIefSuppliedTotalCurrency() {
      return exportView.OutTotalCostIefSuppliedTotalCurrency;
   }
 
   public double getOutAvailableBudgetAmountIefSuppliedTotalCurrency() {
      return exportView.OutAvailableBudgetAmountIefSuppliedTotalCurrency;
   }
 
   public double getOutBudgetAmountIefSuppliedTotalCurrency() {
      return exportView.OutBudgetAmountIefSuppliedTotalCurrency;
   }
 
   public double getOutSmsCostCostPerSms() {
      return exportView.OutSmsCostCostPerSms;
   }
 
   public String getOutRcDesriptionCsfStringsString60() {
      return FixedStringAttr.valueOf(exportView.OutRcDesriptionCsfStringsString60, 60);
   }
 
   public String getOutFileNameWizfuncReportingControlPathAndFilename() {
      return StringAttr.valueOf(exportView.OutFileNameWizfuncReportingControlPathAndFilename);
   }
 
   public String getOutSendSmsCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutSendSmsCsfStringsString1, 1);
   }
 
   public final int OutListGroupMax = 25;
   public short getOutListGroupCount() {
      return (short)(exportView.OutListGroup_MA);
   };
 
   public String getOutGpWsGenericCodeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGpWsGenericCodeCode[index], 10);
   }
 
   public String getOutGpWsGenericCodeEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (24 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 24, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGpWsGenericCodeEngDescription[index], 40);
   }
 
   public String getOutMagisterialCriteriaTypeCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutMagisterialCriteriaTypeCsfStringsString1, 1);
   }
 
   public String getOutRegistrationCriteriaTypeCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutRegistrationCriteriaTypeCsfStringsString1, 1);
   }
 
   public final int OutMagisterialSelectionGroupMax = 20;
   public short getOutMagisterialSelectionGroupCount() {
      return (short)(exportView.OutMagisterialSelectionGroup_MA);
   };
 
   public String getOutMagisterialGpCsfStringsString15(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutMagisterialGpCsfStringsString15[index], 15);
   }
 
   public final int OutRegistrationSelectionGroupMax = 20;
   public short getOutRegistrationSelectionGroupCount() {
      return (short)(exportView.OutRegistrationSelectionGroup_MA);
   };
 
   public String getOutRegistrationGpCsfStringsString15(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutRegistrationGpCsfStringsString15[index], 15);
   }
 
   public short getOutWsStudyUnitPeriodDetailMkAcademicYear() {
      return exportView.OutWsStudyUnitPeriodDetailMkAcademicYear;
   }
 
   public short getOutWsStudyUnitPeriodDetailMkAcademicPeriod() {
      return exportView.OutWsStudyUnitPeriodDetailMkAcademicPeriod;
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
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public int getOutSmsRequestBatchNr() {
      return exportView.OutSmsRequestBatchNr;
   }
 
   public int getOutSmsRequestMkPersNr() {
      return exportView.OutSmsRequestMkPersNr;
   }
 
   public String getOutSmsRequestMkRcCode() {
      return StringAttr.valueOf(exportView.OutSmsRequestMkRcCode);
   }
 
   public short getOutSmsRequestMkDepartmentCode() {
      return exportView.OutSmsRequestMkDepartmentCode;
   }
 
   public Calendar getOutSmsRequestRequestTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutSmsRequestRequestTimestamp);
   }
   public String getAsStringOutSmsRequestRequestTimestamp() {
      return TimestampAttr.toString(exportView.OutSmsRequestRequestTimestamp);
   }
 
   public String getOutSmsRequestSmsMsg() {
      return FixedStringAttr.valueOf(exportView.OutSmsRequestSmsMsg, 160);
   }
 
   public String getOutSmsRequestControlCellNr() {
      return StringAttr.valueOf(exportView.OutSmsRequestControlCellNr);
   }
 
   public int getOutSmsRequestMessageCnt() {
      return exportView.OutSmsRequestMessageCnt;
   }
 
   public double getOutSmsRequestTotalCost() {
      return exportView.OutSmsRequestTotalCost;
   }
 
   public short getOutSmsRequestFromSystemGc26() {
      return exportView.OutSmsRequestFromSystemGc26;
   }
 
   public int getOutSmsRequestReasonGc27() {
      return exportView.OutSmsRequestReasonGc27;
   }
 
   public String getOutSmsRequestSelectionCriteria() {
      return StringAttr.valueOf(exportView.OutSmsRequestSelectionCriteria);
   }
 
   public String getOutSmsRequestProgramName() {
      return FixedStringAttr.valueOf(exportView.OutSmsRequestProgramName, 20);
   }
 
   public String getOutSmsRequestControlCellNr2() {
      return FixedStringAttr.valueOf(exportView.OutSmsRequestControlCellNr2, 20);
   }
 
   public String getOutSmsRequestControlCellNr3() {
      return FixedStringAttr.valueOf(exportView.OutSmsRequestControlCellNr3, 20);
   }
 
   public String getOutErrorFlagCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutErrorFlagCsfStringsString1, 1);
   }
 
   public String getOutNovellCodeWsStaffEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutNovellCodeWsStaffEmailAddress, 60);
   }
 
};
