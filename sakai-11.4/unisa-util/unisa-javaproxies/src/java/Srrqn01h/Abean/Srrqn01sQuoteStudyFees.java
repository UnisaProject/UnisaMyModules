package Srrqn01h.Abean;
 
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
public class Srrqn01sQuoteStudyFees  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srrqn01sQuoteStudyFees");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Srrqn01sQuoteStudyFees() {
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
 
 
   private Srrqn01sQuoteStudyFeesOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srrqn01sQuoteStudyFeesOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrrqn01sQuoteStudyFeesOperation();
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
      private Srrqn01sQuoteStudyFees rP;
      operListener(Srrqn01sQuoteStudyFees r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srrqn01sQuoteStudyFees", "Listener heard that Srrqn01sQuoteStudyFeesOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srrqn01sQuoteStudyFees ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srrqn01sQuoteStudyFees");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srrqn01sQuoteStudyFees ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srrqn01sQuoteStudyFees";
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
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      exportView.OutStudyUnitGroup_MA = IntAttr.getDefaultValue();
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
   }

   Srrqn01h.SRRQN01S_IA importView = Srrqn01h.SRRQN01S_IA.getInstance();
   Srrqn01h.SRRQN01S_OA exportView = Srrqn01h.SRRQN01S_OA.getInstance();
   public String getInPrintNqfCreditsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPrintNqfCreditsIefSuppliedFlag, 1);
   }
   public void setInPrintNqfCreditsIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor pe = new Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPrintNqfCreditsIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPrintNqfCreditsIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPrintNqfCreditsIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPrintNqfCreditsIefSuppliedFlag", null, null));
      }
      importView.InPrintNqfCreditsIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInPrintNqfLevelIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPrintNqfLevelIefSuppliedFlag, 1);
   }
   public void setInPrintNqfLevelIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor pe = new Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPrintNqfLevelIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPrintNqfLevelIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPrintNqfLevelIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPrintNqfLevelIefSuppliedFlag", null, null));
      }
      importView.InPrintNqfLevelIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInProddevCsfStringsString4() {
      return FixedStringAttr.valueOf(importView.InProddevCsfStringsString4, 4);
   }
   public void setInProddevCsfStringsString4(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InProddevCsfStringsString4 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InProddevCsfStringsString4", null, null));
      }
      importView.InProddevCsfStringsString4 = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInMatrExemptionIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InMatrExemptionIefSuppliedFlag, 1);
   }
   public void setInMatrExemptionIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor pe = new Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InMatrExemptionIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InMatrExemptionIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InMatrExemptionIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InMatrExemptionIefSuppliedFlag", null, null));
      }
      importView.InMatrExemptionIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInMatrExemptionIefSuppliedTotalCurrency() {
      return importView.InMatrExemptionIefSuppliedTotalCurrency;
   }
   public void setInMatrExemptionIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InMatrExemptionIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMatrExemptionIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InMatrExemptionIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InMatrExemptionIefSuppliedTotalCurrency", null, null));
      }
      importView.InMatrExemptionIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMatrExemptionIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMatrExemptionIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMatrExemptionIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInMatrExemptionIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMatrExemptionIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMatrExemptionIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public String getInSmartcardIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InSmartcardIefSuppliedFlag, 1);
   }
   public void setInSmartcardIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor pe = new Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InSmartcardIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InSmartcardIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSmartcardIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSmartcardIefSuppliedFlag", null, null));
      }
      importView.InSmartcardIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInSmartcardIefSuppliedTotalCurrency() {
      return importView.InSmartcardIefSuppliedTotalCurrency;
   }
   public void setInSmartcardIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InSmartcardIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InSmartcardIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InSmartcardIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InSmartcardIefSuppliedTotalCurrency", null, null));
      }
      importView.InSmartcardIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInSmartcardIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmartcardIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInSmartcardIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmartcardIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmartcardIefSuppliedTotalCurrency", null, null));
      }
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
 
   public String getInWsForeignLevyCategoryCode() {
      return FixedStringAttr.valueOf(importView.InWsForeignLevyCategoryCode, 1);
   }
   public void setInWsForeignLevyCategoryCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsForeignLevyCategoryCode must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsForeignLevyCategoryCode", null, null));
      }
      importView.InWsForeignLevyCategoryCode = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInLateRegIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InLateRegIefSuppliedFlag, 1);
   }
   public void setInLateRegIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor pe = new Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor();
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
 
   public double getInMatricExemFeeIefSuppliedAverageCurrency() {
      return importView.InMatricExemFeeIefSuppliedAverageCurrency;
   }
   public void setInMatricExemFeeIefSuppliedAverageCurrency(double s)
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
         throw new PropertyVetoException("InMatricExemFeeIefSuppliedAverageCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMatricExemFeeIefSuppliedAverageCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InMatricExemFeeIefSuppliedAverageCurrency has more than 9 integral digits.",
               new PropertyChangeEvent (this, "InMatricExemFeeIefSuppliedAverageCurrency", null, null));
      }
      importView.InMatricExemFeeIefSuppliedAverageCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMatricExemFeeIefSuppliedAverageCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMatricExemFeeIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMatricExemFeeIefSuppliedAverageCurrency", null, null));
      }
      try {
          setInMatricExemFeeIefSuppliedAverageCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMatricExemFeeIefSuppliedAverageCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMatricExemFeeIefSuppliedAverageCurrency", null, null));
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
 
   public Calendar getInSrrqn01gQuoteStudyFeesDate() {
      return DateAttr.toCalendar(importView.InSrrqn01gQuoteStudyFeesDate);
   }
   public int getAsIntInSrrqn01gQuoteStudyFeesDate() {
      return DateAttr.toInt(importView.InSrrqn01gQuoteStudyFeesDate);
   }
   public void setInSrrqn01gQuoteStudyFeesDate(Calendar s)
      throws PropertyVetoException {
      importView.InSrrqn01gQuoteStudyFeesDate = DateAttr.valueOf(s);
   }
   public void setAsStringInSrrqn01gQuoteStudyFeesDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSrrqn01gQuoteStudyFeesDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInSrrqn01gQuoteStudyFeesDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSrrqn01gQuoteStudyFeesDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InSrrqn01gQuoteStudyFeesDate", null, null));
         }
      }
   }
   public void setAsIntInSrrqn01gQuoteStudyFeesDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInSrrqn01gQuoteStudyFeesDate(temp);
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
 
   public int getInWsWorkstationUserNumber() {
      return importView.InWsWorkstationUserNumber;
   }
   public void setInWsWorkstationUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsWorkstationUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsWorkstationUserNumber", null, null));
      }
      importView.InWsWorkstationUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsWorkstationUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsWorkstationUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsWorkstationUserNumber", null, null));
      }
      try {
          setInWsWorkstationUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsWorkstationUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsWorkstationUserNumber", null, null));
      }
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
 
   public String getInWsUserName() {
      return FixedStringAttr.valueOf(importView.InWsUserName, 28);
   }
   public void setInWsUserName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsUserName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsUserName", null, null));
      }
      importView.InWsUserName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(importView.InWsUserPersonnelNo, 10);
   }
   public void setInWsUserPersonnelNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsUserPersonnelNo must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsUserPersonnelNo", null, null));
      }
      importView.InWsUserPersonnelNo = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(importView.InWsUserPhoneNumber, 20);
   }
   public void setInWsUserPhoneNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InWsUserPhoneNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InWsUserPhoneNumber", null, null));
      }
      importView.InWsUserPhoneNumber = FixedStringAttr.valueOf(s, (short)20);
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
 
   public final int InGroupMax = 50;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public short getInGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGCsfLineActionBarLineReturnCode[index];
   }
   public void setInGCsfLineActionBarLineReturnCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
      importView.InGCsfLineActionBarLineReturnCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGCsfLineActionBarLineReturnCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
      try {
          setInGCsfLineActionBarLineReturnCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGCsfLineActionBarLineReturnCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGCsfLineActionBarLineReturnCode", null, null));
      }
   }
 
   public String getInGStudentStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudentStudyUnitMkStudyUnitCode[index], 7);
   }
   public void setInGStudentStudyUnitMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGStudentStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGStudentStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InGStudentStudyUnitMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
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
 
   public String getOutPrintNqfCreditsIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutPrintNqfCreditsIefSuppliedFlag, 1);
   }
 
   public String getOutPrintNqfLevelIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutPrintNqfLevelIefSuppliedFlag, 1);
   }
 
   public String getOutProddevCsfStringsString4() {
      return FixedStringAttr.valueOf(exportView.OutProddevCsfStringsString4, 4);
   }
 
   public String getOutMatrExemptionIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutMatrExemptionIefSuppliedFlag, 1);
   }
 
   public double getOutMatrExemptionIefSuppliedTotalCurrency() {
      return exportView.OutMatrExemptionIefSuppliedTotalCurrency;
   }
 
   public String getOutSmartcardIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSmartcardIefSuppliedFlag, 1);
   }
 
   public double getOutSmartcardIefSuppliedTotalCurrency() {
      return exportView.OutSmartcardIefSuppliedTotalCurrency;
   }
 
   public String getOutFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNoCsfStringsString132, 132);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public double getOutWsPrescribedBooksAmount() {
      return exportView.OutWsPrescribedBooksAmount;
   }
 
   public final int OutStudyUnitGroupMax = 50;
   public short getOutStudyUnitGroupCount() {
      return (short)(exportView.OutStudyUnitGroup_MA);
   };
 
   public String getOutGInternetWsStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGInternetWsStudyUnitCode[index], 7);
   }
 
   public String getOutGInternetWsStudyUnitEngShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGInternetWsStudyUnitEngShortDescription[index], 28);
   }
 
   public short getOutGInternetWsStudyUnitNqfCategory(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGInternetWsStudyUnitNqfCategory[index];
   }
 
   public short getOutGInternetWsStudyUnitNqfCredits(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGInternetWsStudyUnitNqfCredits[index];
   }
 
   public short getOutGInternetWsStudyUnitPqmNqfLevel(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGInternetWsStudyUnitPqmNqfLevel[index];
   }
 
   public double getOutGStudyUnitCostIefSuppliedTotalCurrency(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGStudyUnitCostIefSuppliedTotalCurrency[index];
   }
 
   public double getOutTotalIefSuppliedTotalCurrency() {
      return exportView.OutTotalIefSuppliedTotalCurrency;
   }
 
   public double getOutRegPaymentIefSuppliedTotalCurrency() {
      return exportView.OutRegPaymentIefSuppliedTotalCurrency;
   }
 
   public double getOutForeignLevyIefSuppliedTotalCurrency() {
      return exportView.OutForeignLevyIefSuppliedTotalCurrency;
   }
 
   public double getOutSrcLevyIefSuppliedTotalCurrency() {
      return exportView.OutSrcLevyIefSuppliedTotalCurrency;
   }
 
   public String getOutFromEmailAddressCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFromEmailAddressCsfStringsString132, 132);
   }
 
   public String getOutToEmailAddressCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutToEmailAddressCsfStringsString132, 132);
   }
 
   public int getOutWsUserNumber() {
      return exportView.OutWsUserNumber;
   }
 
   public String getOutWsUserName() {
      return FixedStringAttr.valueOf(exportView.OutWsUserName, 28);
   }
 
   public String getOutWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(exportView.OutWsUserPersonnelNo, 10);
   }
 
   public String getOutWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsUserPhoneNumber, 20);
   }
 
   public String getOutWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutWsPrinterCode, 10);
   }
 
   public String getOutWsWorkstationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsWorkstationCode, 10);
   }
 
   public int getOutWsWorkstationUserNumber() {
      return exportView.OutWsWorkstationUserNumber;
   }
 
   public Calendar getOutSrrqn01gQuoteStudyFeesDate() {
      return DateAttr.toCalendar(exportView.OutSrrqn01gQuoteStudyFeesDate);
   }
   public int getAsIntOutSrrqn01gQuoteStudyFeesDate() {
      return DateAttr.toInt(exportView.OutSrrqn01gQuoteStudyFeesDate);
   }
 
   public int getOutStudentAcademicRecordMkStudentNr() {
      return exportView.OutStudentAcademicRecordMkStudentNr;
   }
 
   public String getOutStudentAcademicRecordMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordMkQualificationCode, 5);
   }
 
   public double getOutMatricExemFeeIefSuppliedAverageCurrency() {
      return exportView.OutMatricExemFeeIefSuppliedAverageCurrency;
   }
 
   public String getOutLateRegIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutLateRegIefSuppliedFlag, 1);
   }
 
   public String getOutLclWsAddressAddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressAddressLine1, 28);
   }
 
   public String getOutLclWsAddressAddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressAddressLine2, 28);
   }
 
   public String getOutLclWsAddressAddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressAddressLine3, 28);
   }
 
   public String getOutLclWsAddressAddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressAddressLine4, 28);
   }
 
   public String getOutLclWsAddressAddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressAddressLine5, 28);
   }
 
   public String getOutLclWsAddressAddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressAddressLine6, 28);
   }
 
   public short getOutLclWsAddressPostalCode() {
      return exportView.OutLclWsAddressPostalCode;
   }
 
   public int getOutLclWsAddressReferenceNo() {
      return exportView.OutLclWsAddressReferenceNo;
   }
 
   public short getOutLclWsAddressType() {
      return exportView.OutLclWsAddressType;
   }
 
   public short getOutLclWsAddressCategory() {
      return exportView.OutLclWsAddressCategory;
   }
 
   public String getOutLclWsAddressEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressEmailAddress, 28);
   }
 
   public int getOutLclWsStudentNr() {
      return exportView.OutLclWsStudentNr;
   }
 
   public String getOutLclWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(exportView.OutLclWsStudentMkCorrespondenceLanguage, 2);
   }
 
   public String getOutLclWsStudentMkTitle() {
      return FixedStringAttr.valueOf(exportView.OutLclWsStudentMkTitle, 10);
   }
 
   public String getOutLclWsStudentSurname() {
      return FixedStringAttr.valueOf(exportView.OutLclWsStudentSurname, 28);
   }
 
   public String getOutLclWsStudentFirstNames() {
      return FixedStringAttr.valueOf(exportView.OutLclWsStudentFirstNames, 60);
   }
 
   public String getOutLclWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutLclWsStudentInitials, 10);
   }
 
   public String getOutLclWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutLclWsStudentMkCountryCode, 4);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public String getOutCsfStringsString12() {
      return FixedStringAttr.valueOf(exportView.OutCsfStringsString12, 12);
   }
 
   public String getOutCsfStringsString7() {
      return FixedStringAttr.valueOf(exportView.OutCsfStringsString7, 7);
   }
 
   public String getOutCsfStringsString3() {
      return FixedStringAttr.valueOf(exportView.OutCsfStringsString3, 3);
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
 
   public final int OutGroupMax = 50;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public short getOutGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGStudentStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudentStudyUnitMkStudyUnitCode[index], 7);
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
 
   public String getOutWsCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsCountryCode, 4);
   }
 
   public String getOutWsCountryEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsCountryEngDescription, 28);
   }
 
};
