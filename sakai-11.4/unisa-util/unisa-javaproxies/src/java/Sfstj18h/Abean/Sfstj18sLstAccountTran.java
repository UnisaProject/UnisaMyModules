package Sfstj18h.Abean;
 
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
public class Sfstj18sLstAccountTran  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Sfstj18sLstAccountTran");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Sfstj18sLstAccountTran() {
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
 
 
   private Sfstj18sLstAccountTranOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Sfstj18sLstAccountTranOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSfstj18sLstAccountTranOperation();
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
      private Sfstj18sLstAccountTran rP;
      operListener(Sfstj18sLstAccountTran r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Sfstj18sLstAccountTran", "Listener heard that Sfstj18sLstAccountTranOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Sfstj18sLstAccountTran ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Sfstj18sLstAccountTran");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Sfstj18sLstAccountTran ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Sfstj18sLstAccountTran";
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
      importView.InStudentAccountClassificationInUseFlag = FixedStringAttr.valueOf("Y", 1);
      importView.InWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      importView.InWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      importView.InWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      importView.InWsStudentAnnualRecordLateRegistrationFlag = FixedStringAttr.valueOf("N", 1);
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
      exportView.OutWsStudentLibraryBlackList = ShortAttr.valueOf((short)0);
      exportView.OutWsStudentFinancialBlockFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentExamFeesDebtFlag = FixedStringAttr.valueOf("N", 1);
      exportView.OutWsStudentPhasedOutStatus = ShortAttr.valueOf((short)0);
      exportView.OutGrp_MA = IntAttr.getDefaultValue();
      for( int index = 0; index < 50; index++)
         exportView.OutGWsDocumentParagraphLineAmountFlag[index] = FixedStringAttr.valueOf("N", 1);
      for( int index = 0; index < 50; index++)
         exportView.OutGWsDocumentParagraphLineDateFlag[index] = FixedStringAttr.valueOf("N", 1);
      for( int index = 0; index < 50; index++)
         exportView.OutGWsDocumentParagraphLineTextFlag[index] = FixedStringAttr.valueOf("N", 1);
      for( int index = 0; index < 50; index++)
         exportView.OutGWsDocumentParagraphLineNumberFlag[index] = FixedStringAttr.valueOf("N", 1);
   }

   Sfstj18h.SFSTJ18S_IA importView = Sfstj18h.SFSTJ18S_IA.getInstance();
   Sfstj18h.SFSTJ18S_OA exportView = Sfstj18h.SFSTJ18S_OA.getInstance();
   public double getInNextMarchIefSuppliedTotalCurrency() {
      return importView.InNextMarchIefSuppliedTotalCurrency;
   }
   public void setInNextMarchIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InNextMarchIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InNextMarchIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InNextMarchIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InNextMarchIefSuppliedTotalCurrency", null, null));
      }
      importView.InNextMarchIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInNextMarchIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InNextMarchIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNextMarchIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInNextMarchIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InNextMarchIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNextMarchIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInNovemberIefSuppliedTotalCurrency() {
      return importView.InNovemberIefSuppliedTotalCurrency;
   }
   public void setInNovemberIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InNovemberIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InNovemberIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InNovemberIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InNovemberIefSuppliedTotalCurrency", null, null));
      }
      importView.InNovemberIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInNovemberIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InNovemberIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNovemberIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInNovemberIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InNovemberIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InNovemberIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public String getInHistoryIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InHistoryIefSuppliedFlag, 1);
   }
   public void setInHistoryIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfstj18sLstAccountTranIefSuppliedFlagPropertyEditor pe = new Sfstj18sLstAccountTranIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InHistoryIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InHistoryIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InHistoryIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InHistoryIefSuppliedFlag", null, null));
      }
      importView.InHistoryIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInHighStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toCalendar(importView.InHighStudentFinancialTransactionHiBundleDate);
   }
   public int getAsIntInHighStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toInt(importView.InHighStudentFinancialTransactionHiBundleDate);
   }
   public void setInHighStudentFinancialTransactionHiBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InHighStudentFinancialTransactionHiBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInHighStudentFinancialTransactionHiBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInHighStudentFinancialTransactionHiBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleDate", null, null));
         }
      }
   }
   public void setAsIntInHighStudentFinancialTransactionHiBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInHighStudentFinancialTransactionHiBundleDate(temp);
   }
 
   public short getInHighStudentFinancialTransactionHiBundleNr() {
      return importView.InHighStudentFinancialTransactionHiBundleNr;
   }
   public void setInHighStudentFinancialTransactionHiBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleNr", null, null));
      }
      importView.InHighStudentFinancialTransactionHiBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleNr", null, null));
      }
      try {
          setInHighStudentFinancialTransactionHiBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleNr", null, null));
      }
   }
 
   public short getInHighStudentFinancialTransactionHiDocumentSeqNr() {
      return importView.InHighStudentFinancialTransactionHiDocumentSeqNr;
   }
   public void setInHighStudentFinancialTransactionHiDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
      importView.InHighStudentFinancialTransactionHiDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
      try {
          setInHighStudentFinancialTransactionHiDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
   }
 
   public short getInHighStudentFinancialTransactionHiJournalSeqNr() {
      return importView.InHighStudentFinancialTransactionHiJournalSeqNr;
   }
   public void setInHighStudentFinancialTransactionHiJournalSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
      importView.InHighStudentFinancialTransactionHiJournalSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiJournalSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
      try {
          setInHighStudentFinancialTransactionHiJournalSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
   }
 
   public double getInHighStudentFinancialTransactionHiAmount() {
      return importView.InHighStudentFinancialTransactionHiAmount;
   }
   public void setInHighStudentFinancialTransactionHiAmount(double s)
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
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiAmount", null, null));
      }
      importView.InHighStudentFinancialTransactionHiAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiAmount", null, null));
      }
      try {
          setInHighStudentFinancialTransactionHiAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiAmount", null, null));
      }
   }
 
   public short getInHighStudentFinancialTransactionHiBundleType() {
      return importView.InHighStudentFinancialTransactionHiBundleType;
   }
   public void setInHighStudentFinancialTransactionHiBundleType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleType has more than 2 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleType", null, null));
      }
      importView.InHighStudentFinancialTransactionHiBundleType = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiBundleType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleType", null, null));
      }
      try {
          setInHighStudentFinancialTransactionHiBundleType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionHiBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiBundleType", null, null));
      }
   }
 
   public String getInHighStudentFinancialTransactionHiReference() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiReference, 10);
   }
   public void setInHighStudentFinancialTransactionHiReference(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiReference must be <= 10 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiReference", null, null));
      }
      importView.InHighStudentFinancialTransactionHiReference = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInHighStudentFinancialTransactionHiRegistrationType() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiRegistrationType, 2);
   }
   public void setInHighStudentFinancialTransactionHiRegistrationType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiRegistrationType must be <= 2 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiRegistrationType", null, null));
      }
      importView.InHighStudentFinancialTransactionHiRegistrationType = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInHighStudentFinancialTransactionHiMkGlFund() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiMkGlFund, 5);
   }
   public void setInHighStudentFinancialTransactionHiMkGlFund(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiMkGlFund must be <= 5 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiMkGlFund", null, null));
      }
      importView.InHighStudentFinancialTransactionHiMkGlFund = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInHighStudentFinancialTransactionHiMkGlEntity() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiMkGlEntity, 5);
   }
   public void setInHighStudentFinancialTransactionHiMkGlEntity(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiMkGlEntity must be <= 5 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiMkGlEntity", null, null));
      }
      importView.InHighStudentFinancialTransactionHiMkGlEntity = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInHighStudentFinancialTransactionHiMkGlAccount() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiMkGlAccount, 5);
   }
   public void setInHighStudentFinancialTransactionHiMkGlAccount(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiMkGlAccount must be <= 5 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiMkGlAccount", null, null));
      }
      importView.InHighStudentFinancialTransactionHiMkGlAccount = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInHighStudentFinancialTransactionHiMkGlItem() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiMkGlItem, 5);
   }
   public void setInHighStudentFinancialTransactionHiMkGlItem(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiMkGlItem must be <= 5 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiMkGlItem", null, null));
      }
      importView.InHighStudentFinancialTransactionHiMkGlItem = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInHighStudentFinancialTransactionHiPayMethod() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionHiPayMethod, 7);
   }
   public void setInHighStudentFinancialTransactionHiPayMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionHiPayMethod must be <= 7 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiPayMethod", null, null));
      }
      importView.InHighStudentFinancialTransactionHiPayMethod = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public Calendar getInHighStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toCalendar(importView.InHighStudentFinancialTransactionHiDateTime);
   }
   public String getAsStringInHighStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toString(importView.InHighStudentFinancialTransactionHiDateTime);
   }
   public void setInHighStudentFinancialTransactionHiDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InHighStudentFinancialTransactionHiDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionHiDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInHighStudentFinancialTransactionHiDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InHighStudentFinancialTransactionHiDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InHighStudentFinancialTransactionHiDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InHighStudentFinancialTransactionHiDateTime", null, null));
         }
      }
   }
 
   public Calendar getInLowStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toCalendar(importView.InLowStudentFinancialTransactionHiBundleDate);
   }
   public int getAsIntInLowStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toInt(importView.InLowStudentFinancialTransactionHiBundleDate);
   }
   public void setInLowStudentFinancialTransactionHiBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InLowStudentFinancialTransactionHiBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInLowStudentFinancialTransactionHiBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInLowStudentFinancialTransactionHiBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleDate", null, null));
         }
      }
   }
   public void setAsIntInLowStudentFinancialTransactionHiBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInLowStudentFinancialTransactionHiBundleDate(temp);
   }
 
   public short getInLowStudentFinancialTransactionHiBundleNr() {
      return importView.InLowStudentFinancialTransactionHiBundleNr;
   }
   public void setInLowStudentFinancialTransactionHiBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleNr", null, null));
      }
      importView.InLowStudentFinancialTransactionHiBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleNr", null, null));
      }
      try {
          setInLowStudentFinancialTransactionHiBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleNr", null, null));
      }
   }
 
   public short getInLowStudentFinancialTransactionHiDocumentSeqNr() {
      return importView.InLowStudentFinancialTransactionHiDocumentSeqNr;
   }
   public void setInLowStudentFinancialTransactionHiDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
      importView.InLowStudentFinancialTransactionHiDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
      try {
          setInLowStudentFinancialTransactionHiDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
   }
 
   public short getInLowStudentFinancialTransactionHiJournalSeqNr() {
      return importView.InLowStudentFinancialTransactionHiJournalSeqNr;
   }
   public void setInLowStudentFinancialTransactionHiJournalSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
      importView.InLowStudentFinancialTransactionHiJournalSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiJournalSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
      try {
          setInLowStudentFinancialTransactionHiJournalSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
   }
 
   public double getInLowStudentFinancialTransactionHiAmount() {
      return importView.InLowStudentFinancialTransactionHiAmount;
   }
   public void setInLowStudentFinancialTransactionHiAmount(double s)
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
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiAmount", null, null));
      }
      importView.InLowStudentFinancialTransactionHiAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiAmount", null, null));
      }
      try {
          setInLowStudentFinancialTransactionHiAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiAmount", null, null));
      }
   }
 
   public short getInLowStudentFinancialTransactionHiBundleType() {
      return importView.InLowStudentFinancialTransactionHiBundleType;
   }
   public void setInLowStudentFinancialTransactionHiBundleType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleType has more than 2 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleType", null, null));
      }
      importView.InLowStudentFinancialTransactionHiBundleType = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiBundleType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleType", null, null));
      }
      try {
          setInLowStudentFinancialTransactionHiBundleType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionHiBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiBundleType", null, null));
      }
   }
 
   public String getInLowStudentFinancialTransactionHiReference() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiReference, 10);
   }
   public void setInLowStudentFinancialTransactionHiReference(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiReference must be <= 10 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiReference", null, null));
      }
      importView.InLowStudentFinancialTransactionHiReference = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInLowStudentFinancialTransactionHiRegistrationType() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiRegistrationType, 2);
   }
   public void setInLowStudentFinancialTransactionHiRegistrationType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiRegistrationType must be <= 2 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiRegistrationType", null, null));
      }
      importView.InLowStudentFinancialTransactionHiRegistrationType = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInLowStudentFinancialTransactionHiMkGlFund() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiMkGlFund, 5);
   }
   public void setInLowStudentFinancialTransactionHiMkGlFund(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiMkGlFund must be <= 5 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiMkGlFund", null, null));
      }
      importView.InLowStudentFinancialTransactionHiMkGlFund = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInLowStudentFinancialTransactionHiMkGlEntity() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiMkGlEntity, 5);
   }
   public void setInLowStudentFinancialTransactionHiMkGlEntity(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiMkGlEntity must be <= 5 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiMkGlEntity", null, null));
      }
      importView.InLowStudentFinancialTransactionHiMkGlEntity = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInLowStudentFinancialTransactionHiMkGlAccount() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiMkGlAccount, 5);
   }
   public void setInLowStudentFinancialTransactionHiMkGlAccount(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiMkGlAccount must be <= 5 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiMkGlAccount", null, null));
      }
      importView.InLowStudentFinancialTransactionHiMkGlAccount = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInLowStudentFinancialTransactionHiMkGlItem() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiMkGlItem, 5);
   }
   public void setInLowStudentFinancialTransactionHiMkGlItem(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiMkGlItem must be <= 5 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiMkGlItem", null, null));
      }
      importView.InLowStudentFinancialTransactionHiMkGlItem = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInLowStudentFinancialTransactionHiPayMethod() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionHiPayMethod, 7);
   }
   public void setInLowStudentFinancialTransactionHiPayMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionHiPayMethod must be <= 7 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiPayMethod", null, null));
      }
      importView.InLowStudentFinancialTransactionHiPayMethod = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public Calendar getInLowStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toCalendar(importView.InLowStudentFinancialTransactionHiDateTime);
   }
   public String getAsStringInLowStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toString(importView.InLowStudentFinancialTransactionHiDateTime);
   }
   public void setInLowStudentFinancialTransactionHiDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InLowStudentFinancialTransactionHiDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionHiDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInLowStudentFinancialTransactionHiDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InLowStudentFinancialTransactionHiDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InLowStudentFinancialTransactionHiDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InLowStudentFinancialTransactionHiDateTime", null, null));
         }
      }
   }
 
   public short getInFinGlobalCurrentAcademicYear() {
      return importView.InFinGlobalCurrentAcademicYear;
   }
   public void setInFinGlobalCurrentAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InFinGlobalCurrentAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InFinGlobalCurrentAcademicYear", null, null));
      }
      importView.InFinGlobalCurrentAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInFinGlobalCurrentAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFinGlobalCurrentAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinGlobalCurrentAcademicYear", null, null));
      }
      try {
          setInFinGlobalCurrentAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFinGlobalCurrentAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFinGlobalCurrentAcademicYear", null, null));
      }
   }
 
   public Calendar getInStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toCalendar(importView.InStudentFinancialTransactionHiBundleDate);
   }
   public int getAsIntInStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toInt(importView.InStudentFinancialTransactionHiBundleDate);
   }
   public void setInStudentFinancialTransactionHiBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InStudentFinancialTransactionHiBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentFinancialTransactionHiBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStudentFinancialTransactionHiBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentFinancialTransactionHiBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleDate", null, null));
         }
      }
   }
   public void setAsIntInStudentFinancialTransactionHiBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStudentFinancialTransactionHiBundleDate(temp);
   }
 
   public short getInStudentFinancialTransactionHiBundleNr() {
      return importView.InStudentFinancialTransactionHiBundleNr;
   }
   public void setInStudentFinancialTransactionHiBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleNr", null, null));
      }
      importView.InStudentFinancialTransactionHiBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleNr", null, null));
      }
      try {
          setInStudentFinancialTransactionHiBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleNr", null, null));
      }
   }
 
   public short getInStudentFinancialTransactionHiDocumentSeqNr() {
      return importView.InStudentFinancialTransactionHiDocumentSeqNr;
   }
   public void setInStudentFinancialTransactionHiDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
      importView.InStudentFinancialTransactionHiDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
      try {
          setInStudentFinancialTransactionHiDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiDocumentSeqNr", null, null));
      }
   }
 
   public short getInStudentFinancialTransactionHiJournalSeqNr() {
      return importView.InStudentFinancialTransactionHiJournalSeqNr;
   }
   public void setInStudentFinancialTransactionHiJournalSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
      importView.InStudentFinancialTransactionHiJournalSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiJournalSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
      try {
          setInStudentFinancialTransactionHiJournalSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiJournalSeqNr", null, null));
      }
   }
 
   public double getInStudentFinancialTransactionHiAmount() {
      return importView.InStudentFinancialTransactionHiAmount;
   }
   public void setInStudentFinancialTransactionHiAmount(double s)
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
         throw new PropertyVetoException("InStudentFinancialTransactionHiAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiAmount", null, null));
      }
      importView.InStudentFinancialTransactionHiAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiAmount", null, null));
      }
      try {
          setInStudentFinancialTransactionHiAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiAmount", null, null));
      }
   }
 
   public short getInStudentFinancialTransactionHiBundleType() {
      return importView.InStudentFinancialTransactionHiBundleType;
   }
   public void setInStudentFinancialTransactionHiBundleType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiBundleType has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleType", null, null));
      }
      importView.InStudentFinancialTransactionHiBundleType = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiBundleType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleType", null, null));
      }
      try {
          setInStudentFinancialTransactionHiBundleType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFinancialTransactionHiBundleType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFinancialTransactionHiBundleType", null, null));
      }
   }
 
   public String getInStudentFinancialTransactionHiReference() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiReference, 10);
   }
   public void setInStudentFinancialTransactionHiReference(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiReference must be <= 10 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiReference", null, null));
      }
      importView.InStudentFinancialTransactionHiReference = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInStudentFinancialTransactionHiRegistrationType() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiRegistrationType, 2);
   }
   public void setInStudentFinancialTransactionHiRegistrationType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiRegistrationType must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiRegistrationType", null, null));
      }
      importView.InStudentFinancialTransactionHiRegistrationType = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInStudentFinancialTransactionHiMkGlFund() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiMkGlFund, 5);
   }
   public void setInStudentFinancialTransactionHiMkGlFund(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiMkGlFund must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiMkGlFund", null, null));
      }
      importView.InStudentFinancialTransactionHiMkGlFund = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentFinancialTransactionHiMkGlEntity() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiMkGlEntity, 5);
   }
   public void setInStudentFinancialTransactionHiMkGlEntity(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiMkGlEntity must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiMkGlEntity", null, null));
      }
      importView.InStudentFinancialTransactionHiMkGlEntity = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentFinancialTransactionHiMkGlAccount() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiMkGlAccount, 5);
   }
   public void setInStudentFinancialTransactionHiMkGlAccount(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiMkGlAccount must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiMkGlAccount", null, null));
      }
      importView.InStudentFinancialTransactionHiMkGlAccount = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentFinancialTransactionHiMkGlItem() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiMkGlItem, 5);
   }
   public void setInStudentFinancialTransactionHiMkGlItem(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiMkGlItem must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiMkGlItem", null, null));
      }
      importView.InStudentFinancialTransactionHiMkGlItem = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentFinancialTransactionHiPayMethod() {
      return FixedStringAttr.valueOf(importView.InStudentFinancialTransactionHiPayMethod, 7);
   }
   public void setInStudentFinancialTransactionHiPayMethod(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InStudentFinancialTransactionHiPayMethod must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStudentFinancialTransactionHiPayMethod", null, null));
      }
      importView.InStudentFinancialTransactionHiPayMethod = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public Calendar getInStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toCalendar(importView.InStudentFinancialTransactionHiDateTime);
   }
   public String getAsStringInStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toString(importView.InStudentFinancialTransactionHiDateTime);
   }
   public void setInStudentFinancialTransactionHiDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InStudentFinancialTransactionHiDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInStudentFinancialTransactionHiDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStudentFinancialTransactionHiDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InStudentFinancialTransactionHiDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStudentFinancialTransactionHiDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InStudentFinancialTransactionHiDateTime", null, null));
         }
      }
   }
 
   public short getInStudentAccountHistoryMkAcademicYear() {
      return importView.InStudentAccountHistoryMkAcademicYear;
   }
   public void setInStudentAccountHistoryMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMkAcademicYear", null, null));
      }
      importView.InStudentAccountHistoryMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMkAcademicYear", null, null));
      }
      try {
          setInStudentAccountHistoryMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMkAcademicYear", null, null));
      }
   }
 
   public int getInStudentAccountHistoryMkStudentNr() {
      return importView.InStudentAccountHistoryMkStudentNr;
   }
   public void setInStudentAccountHistoryMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMkStudentNr", null, null));
      }
      importView.InStudentAccountHistoryMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMkStudentNr", null, null));
      }
      try {
          setInStudentAccountHistoryMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMkStudentNr", null, null));
      }
   }
 
   public String getInStudentAccountHistoryMkAccountType() {
      return FixedStringAttr.valueOf(importView.InStudentAccountHistoryMkAccountType, 4);
   }
   public void setInStudentAccountHistoryMkAccountType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InStudentAccountHistoryMkAccountType must be <= 4 characters.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMkAccountType", null, null));
      }
      importView.InStudentAccountHistoryMkAccountType = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInStudentAccountHistoryMkBranchCode() {
      return importView.InStudentAccountHistoryMkBranchCode;
   }
   public void setInStudentAccountHistoryMkBranchCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentAccountHistoryMkBranchCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMkBranchCode", null, null));
      }
      importView.InStudentAccountHistoryMkBranchCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryMkBranchCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryMkBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMkBranchCode", null, null));
      }
      try {
          setInStudentAccountHistoryMkBranchCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryMkBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMkBranchCode", null, null));
      }
   }
 
   public String getInStudentAccountHistoryMkQualification() {
      return FixedStringAttr.valueOf(importView.InStudentAccountHistoryMkQualification, 5);
   }
   public void setInStudentAccountHistoryMkQualification(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAccountHistoryMkQualification must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMkQualification", null, null));
      }
      importView.InStudentAccountHistoryMkQualification = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public double getInStudentAccountHistoryBalance() {
      return importView.InStudentAccountHistoryBalance;
   }
   public void setInStudentAccountHistoryBalance(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryBalance has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryBalance", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryBalance has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryBalance", null, null));
      }
      importView.InStudentAccountHistoryBalance = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryBalance(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryBalance", null, null));
      }
      try {
          setInStudentAccountHistoryBalance(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryBalance is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryBalance", null, null));
      }
   }
 
   public double getInStudentAccountHistoryMinRegistrationF() {
      return importView.InStudentAccountHistoryMinRegistrationF;
   }
   public void setInStudentAccountHistoryMinRegistrationF(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryMinRegistrationF has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMinRegistrationF", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryMinRegistrationF has more than 5 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryMinRegistrationF", null, null));
      }
      importView.InStudentAccountHistoryMinRegistrationF = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryMinRegistrationF(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryMinRegistrationF is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMinRegistrationF", null, null));
      }
      try {
          setInStudentAccountHistoryMinRegistrationF(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryMinRegistrationF is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryMinRegistrationF", null, null));
      }
   }
 
   public double getInStudentAccountHistoryDiverseTotal() {
      return importView.InStudentAccountHistoryDiverseTotal;
   }
   public void setInStudentAccountHistoryDiverseTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryDiverseTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryDiverseTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryDiverseTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryDiverseTotal", null, null));
      }
      importView.InStudentAccountHistoryDiverseTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryDiverseTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryDiverseTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryDiverseTotal", null, null));
      }
      try {
          setInStudentAccountHistoryDiverseTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryDiverseTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryDiverseTotal", null, null));
      }
   }
 
   public double getInStudentAccountHistoryCreditTotal() {
      return importView.InStudentAccountHistoryCreditTotal;
   }
   public void setInStudentAccountHistoryCreditTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryCreditTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryCreditTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryCreditTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryCreditTotal", null, null));
      }
      importView.InStudentAccountHistoryCreditTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryCreditTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryCreditTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryCreditTotal", null, null));
      }
      try {
          setInStudentAccountHistoryCreditTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryCreditTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryCreditTotal", null, null));
      }
   }
 
   public double getInStudentAccountHistoryRegTotal() {
      return importView.InStudentAccountHistoryRegTotal;
   }
   public void setInStudentAccountHistoryRegTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryRegTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryRegTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryRegTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryRegTotal", null, null));
      }
      importView.InStudentAccountHistoryRegTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryRegTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryRegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryRegTotal", null, null));
      }
      try {
          setInStudentAccountHistoryRegTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryRegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryRegTotal", null, null));
      }
   }
 
   public double getInStudentAccountHistorySemester1Final() {
      return importView.InStudentAccountHistorySemester1Final;
   }
   public void setInStudentAccountHistorySemester1Final(double s)
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
         throw new PropertyVetoException("InStudentAccountHistorySemester1Final has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySemester1Final", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistorySemester1Final has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySemester1Final", null, null));
      }
      importView.InStudentAccountHistorySemester1Final = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistorySemester1Final(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistorySemester1Final is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySemester1Final", null, null));
      }
      try {
          setInStudentAccountHistorySemester1Final(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistorySemester1Final is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySemester1Final", null, null));
      }
   }
 
   public double getInStudentAccountHistoryFirstPayment() {
      return importView.InStudentAccountHistoryFirstPayment;
   }
   public void setInStudentAccountHistoryFirstPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryFirstPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryFirstPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryFirstPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryFirstPayment", null, null));
      }
      importView.InStudentAccountHistoryFirstPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryFirstPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryFirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryFirstPayment", null, null));
      }
      try {
          setInStudentAccountHistoryFirstPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryFirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryFirstPayment", null, null));
      }
   }
 
   public double getInStudentAccountHistoryFinalPayment() {
      return importView.InStudentAccountHistoryFinalPayment;
   }
   public void setInStudentAccountHistoryFinalPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryFinalPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryFinalPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryFinalPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryFinalPayment", null, null));
      }
      importView.InStudentAccountHistoryFinalPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryFinalPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryFinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryFinalPayment", null, null));
      }
      try {
          setInStudentAccountHistoryFinalPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryFinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryFinalPayment", null, null));
      }
   }
 
   public String getInStudentAccountHistoryComments() {
      return FixedStringAttr.valueOf(importView.InStudentAccountHistoryComments, 60);
   }
   public void setInStudentAccountHistoryComments(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InStudentAccountHistoryComments must be <= 60 characters.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryComments", null, null));
      }
      importView.InStudentAccountHistoryComments = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public double getInStudentAccountHistoryDueImmediately() {
      return importView.InStudentAccountHistoryDueImmediately;
   }
   public void setInStudentAccountHistoryDueImmediately(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryDueImmediately has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryDueImmediately", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryDueImmediately has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryDueImmediately", null, null));
      }
      importView.InStudentAccountHistoryDueImmediately = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryDueImmediately(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryDueImmediately is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryDueImmediately", null, null));
      }
      try {
          setInStudentAccountHistoryDueImmediately(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryDueImmediately is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryDueImmediately", null, null));
      }
   }
 
   public double getInStudentAccountHistorySem6RegTotal() {
      return importView.InStudentAccountHistorySem6RegTotal;
   }
   public void setInStudentAccountHistorySem6RegTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountHistorySem6RegTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySem6RegTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistorySem6RegTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySem6RegTotal", null, null));
      }
      importView.InStudentAccountHistorySem6RegTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistorySem6RegTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistorySem6RegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySem6RegTotal", null, null));
      }
      try {
          setInStudentAccountHistorySem6RegTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistorySem6RegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySem6RegTotal", null, null));
      }
   }
 
   public double getInStudentAccountHistorySem6FirstPayment() {
      return importView.InStudentAccountHistorySem6FirstPayment;
   }
   public void setInStudentAccountHistorySem6FirstPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountHistorySem6FirstPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySem6FirstPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistorySem6FirstPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySem6FirstPayment", null, null));
      }
      importView.InStudentAccountHistorySem6FirstPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistorySem6FirstPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistorySem6FirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySem6FirstPayment", null, null));
      }
      try {
          setInStudentAccountHistorySem6FirstPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistorySem6FirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySem6FirstPayment", null, null));
      }
   }
 
   public double getInStudentAccountHistorySem6FinalPayment() {
      return importView.InStudentAccountHistorySem6FinalPayment;
   }
   public void setInStudentAccountHistorySem6FinalPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountHistorySem6FinalPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySem6FinalPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistorySem6FinalPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistorySem6FinalPayment", null, null));
      }
      importView.InStudentAccountHistorySem6FinalPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistorySem6FinalPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistorySem6FinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySem6FinalPayment", null, null));
      }
      try {
          setInStudentAccountHistorySem6FinalPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistorySem6FinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistorySem6FinalPayment", null, null));
      }
   }
 
   public double getInStudentAccountHistoryPrevSem6First() {
      return importView.InStudentAccountHistoryPrevSem6First;
   }
   public void setInStudentAccountHistoryPrevSem6First(double s)
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
         throw new PropertyVetoException("InStudentAccountHistoryPrevSem6First has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryPrevSem6First", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountHistoryPrevSem6First has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountHistoryPrevSem6First", null, null));
      }
      importView.InStudentAccountHistoryPrevSem6First = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountHistoryPrevSem6First(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountHistoryPrevSem6First is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryPrevSem6First", null, null));
      }
      try {
          setInStudentAccountHistoryPrevSem6First(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountHistoryPrevSem6First is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountHistoryPrevSem6First", null, null));
      }
   }
 
   public Calendar getInStartingStudentFinancialTransactionBundleDate() {
      return DateAttr.toCalendar(importView.InStartingStudentFinancialTransactionBundleDate);
   }
   public int getAsIntInStartingStudentFinancialTransactionBundleDate() {
      return DateAttr.toInt(importView.InStartingStudentFinancialTransactionBundleDate);
   }
   public void setInStartingStudentFinancialTransactionBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InStartingStudentFinancialTransactionBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStartingStudentFinancialTransactionBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInStartingStudentFinancialTransactionBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStartingStudentFinancialTransactionBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionBundleDate", null, null));
         }
      }
   }
   public void setAsIntInStartingStudentFinancialTransactionBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInStartingStudentFinancialTransactionBundleDate(temp);
   }
 
   public short getInStartingStudentFinancialTransactionBundleNr() {
      return importView.InStartingStudentFinancialTransactionBundleNr;
   }
   public void setInStartingStudentFinancialTransactionBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionBundleNr", null, null));
      }
      importView.InStartingStudentFinancialTransactionBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionBundleNr", null, null));
      }
      try {
          setInStartingStudentFinancialTransactionBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionBundleNr", null, null));
      }
   }
 
   public short getInStartingStudentFinancialTransactionDocumentSeqNr() {
      return importView.InStartingStudentFinancialTransactionDocumentSeqNr;
   }
   public void setInStartingStudentFinancialTransactionDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      importView.InStartingStudentFinancialTransactionDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      try {
          setInStartingStudentFinancialTransactionDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionDocumentSeqNr", null, null));
      }
   }
 
   public double getInStartingStudentFinancialTransactionAmount() {
      return importView.InStartingStudentFinancialTransactionAmount;
   }
   public void setInStartingStudentFinancialTransactionAmount(double s)
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
         throw new PropertyVetoException("InStartingStudentFinancialTransactionAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAmount", null, null));
      }
      importView.InStartingStudentFinancialTransactionAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAmount", null, null));
      }
      try {
          setInStartingStudentFinancialTransactionAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAmount", null, null));
      }
   }
 
   public String getInStartingStudentFinancialTransactionRegistrationTypeCode() {
      return FixedStringAttr.valueOf(importView.InStartingStudentFinancialTransactionRegistrationTypeCode, 2);
   }
   public void setInStartingStudentFinancialTransactionRegistrationTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionRegistrationTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionRegistrationTypeCode", null, null));
      }
      importView.InStartingStudentFinancialTransactionRegistrationTypeCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInStartingStudentFinancialTransactionAcademicYear() {
      return importView.InStartingStudentFinancialTransactionAcademicYear;
   }
   public void setInStartingStudentFinancialTransactionAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAcademicYear", null, null));
      }
      importView.InStartingStudentFinancialTransactionAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAcademicYear", null, null));
      }
      try {
          setInStartingStudentFinancialTransactionAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAcademicYear", null, null));
      }
   }
 
   public short getInStartingStudentFinancialTransactionAcademicPeriod() {
      return importView.InStartingStudentFinancialTransactionAcademicPeriod;
   }
   public void setInStartingStudentFinancialTransactionAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAcademicPeriod", null, null));
      }
      importView.InStartingStudentFinancialTransactionAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAcademicPeriod", null, null));
      }
      try {
          setInStartingStudentFinancialTransactionAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionAcademicPeriod", null, null));
      }
   }
 
   public Calendar getInStartingStudentFinancialTransactionDateTime() {
      return TimestampAttr.toCalendar(importView.InStartingStudentFinancialTransactionDateTime);
   }
   public String getAsStringInStartingStudentFinancialTransactionDateTime() {
      return TimestampAttr.toString(importView.InStartingStudentFinancialTransactionDateTime);
   }
   public void setInStartingStudentFinancialTransactionDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InStartingStudentFinancialTransactionDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStartingStudentFinancialTransactionDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InStartingStudentFinancialTransactionDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStartingStudentFinancialTransactionDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionDateTime", null, null));
         }
      }
   }
 
   public short getInStartingStudentFinancialTransactionJournalSeqNr() {
      return importView.InStartingStudentFinancialTransactionJournalSeqNr;
   }
   public void setInStartingStudentFinancialTransactionJournalSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InStartingStudentFinancialTransactionJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionJournalSeqNr", null, null));
      }
      importView.InStartingStudentFinancialTransactionJournalSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInStartingStudentFinancialTransactionJournalSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionJournalSeqNr", null, null));
      }
      try {
          setInStartingStudentFinancialTransactionJournalSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingStudentFinancialTransactionJournalSeqNr", null, null));
      }
   }
 
   public Calendar getInHighStudentFinancialTransactionBundleDate() {
      return DateAttr.toCalendar(importView.InHighStudentFinancialTransactionBundleDate);
   }
   public int getAsIntInHighStudentFinancialTransactionBundleDate() {
      return DateAttr.toInt(importView.InHighStudentFinancialTransactionBundleDate);
   }
   public void setInHighStudentFinancialTransactionBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InHighStudentFinancialTransactionBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInHighStudentFinancialTransactionBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInHighStudentFinancialTransactionBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InHighStudentFinancialTransactionBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InHighStudentFinancialTransactionBundleDate", null, null));
         }
      }
   }
   public void setAsIntInHighStudentFinancialTransactionBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInHighStudentFinancialTransactionBundleDate(temp);
   }
 
   public short getInHighStudentFinancialTransactionBundleNr() {
      return importView.InHighStudentFinancialTransactionBundleNr;
   }
   public void setInHighStudentFinancialTransactionBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionBundleNr", null, null));
      }
      importView.InHighStudentFinancialTransactionBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionBundleNr", null, null));
      }
      try {
          setInHighStudentFinancialTransactionBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionBundleNr", null, null));
      }
   }
 
   public short getInHighStudentFinancialTransactionDocumentSeqNr() {
      return importView.InHighStudentFinancialTransactionDocumentSeqNr;
   }
   public void setInHighStudentFinancialTransactionDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      importView.InHighStudentFinancialTransactionDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      try {
          setInHighStudentFinancialTransactionDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionDocumentSeqNr", null, null));
      }
   }
 
   public double getInHighStudentFinancialTransactionAmount() {
      return importView.InHighStudentFinancialTransactionAmount;
   }
   public void setInHighStudentFinancialTransactionAmount(double s)
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
         throw new PropertyVetoException("InHighStudentFinancialTransactionAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAmount", null, null));
      }
      importView.InHighStudentFinancialTransactionAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAmount", null, null));
      }
      try {
          setInHighStudentFinancialTransactionAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAmount", null, null));
      }
   }
 
   public String getInHighStudentFinancialTransactionRegistrationTypeCode() {
      return FixedStringAttr.valueOf(importView.InHighStudentFinancialTransactionRegistrationTypeCode, 2);
   }
   public void setInHighStudentFinancialTransactionRegistrationTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionRegistrationTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionRegistrationTypeCode", null, null));
      }
      importView.InHighStudentFinancialTransactionRegistrationTypeCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInHighStudentFinancialTransactionAcademicYear() {
      return importView.InHighStudentFinancialTransactionAcademicYear;
   }
   public void setInHighStudentFinancialTransactionAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAcademicYear", null, null));
      }
      importView.InHighStudentFinancialTransactionAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAcademicYear", null, null));
      }
      try {
          setInHighStudentFinancialTransactionAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAcademicYear", null, null));
      }
   }
 
   public short getInHighStudentFinancialTransactionAcademicPeriod() {
      return importView.InHighStudentFinancialTransactionAcademicPeriod;
   }
   public void setInHighStudentFinancialTransactionAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAcademicPeriod", null, null));
      }
      importView.InHighStudentFinancialTransactionAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAcademicPeriod", null, null));
      }
      try {
          setInHighStudentFinancialTransactionAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionAcademicPeriod", null, null));
      }
   }
 
   public Calendar getInHighStudentFinancialTransactionDateTime() {
      return TimestampAttr.toCalendar(importView.InHighStudentFinancialTransactionDateTime);
   }
   public String getAsStringInHighStudentFinancialTransactionDateTime() {
      return TimestampAttr.toString(importView.InHighStudentFinancialTransactionDateTime);
   }
   public void setInHighStudentFinancialTransactionDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InHighStudentFinancialTransactionDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInHighStudentFinancialTransactionDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InHighStudentFinancialTransactionDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InHighStudentFinancialTransactionDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InHighStudentFinancialTransactionDateTime", null, null));
         }
      }
   }
 
   public short getInHighStudentFinancialTransactionJournalSeqNr() {
      return importView.InHighStudentFinancialTransactionJournalSeqNr;
   }
   public void setInHighStudentFinancialTransactionJournalSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InHighStudentFinancialTransactionJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InHighStudentFinancialTransactionJournalSeqNr", null, null));
      }
      importView.InHighStudentFinancialTransactionJournalSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighStudentFinancialTransactionJournalSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionJournalSeqNr", null, null));
      }
      try {
          setInHighStudentFinancialTransactionJournalSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighStudentFinancialTransactionJournalSeqNr", null, null));
      }
   }
 
   public Calendar getInLowStudentFinancialTransactionBundleDate() {
      return DateAttr.toCalendar(importView.InLowStudentFinancialTransactionBundleDate);
   }
   public int getAsIntInLowStudentFinancialTransactionBundleDate() {
      return DateAttr.toInt(importView.InLowStudentFinancialTransactionBundleDate);
   }
   public void setInLowStudentFinancialTransactionBundleDate(Calendar s)
      throws PropertyVetoException {
      importView.InLowStudentFinancialTransactionBundleDate = DateAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionBundleDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInLowStudentFinancialTransactionBundleDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInLowStudentFinancialTransactionBundleDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InLowStudentFinancialTransactionBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InLowStudentFinancialTransactionBundleDate", null, null));
         }
      }
   }
   public void setAsIntInLowStudentFinancialTransactionBundleDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInLowStudentFinancialTransactionBundleDate(temp);
   }
 
   public short getInLowStudentFinancialTransactionBundleNr() {
      return importView.InLowStudentFinancialTransactionBundleNr;
   }
   public void setInLowStudentFinancialTransactionBundleNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionBundleNr", null, null));
      }
      importView.InLowStudentFinancialTransactionBundleNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionBundleNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionBundleNr", null, null));
      }
      try {
          setInLowStudentFinancialTransactionBundleNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionBundleNr", null, null));
      }
   }
 
   public short getInLowStudentFinancialTransactionDocumentSeqNr() {
      return importView.InLowStudentFinancialTransactionDocumentSeqNr;
   }
   public void setInLowStudentFinancialTransactionDocumentSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      importView.InLowStudentFinancialTransactionDocumentSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionDocumentSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      try {
          setInLowStudentFinancialTransactionDocumentSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionDocumentSeqNr", null, null));
      }
   }
 
   public double getInLowStudentFinancialTransactionAmount() {
      return importView.InLowStudentFinancialTransactionAmount;
   }
   public void setInLowStudentFinancialTransactionAmount(double s)
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
         throw new PropertyVetoException("InLowStudentFinancialTransactionAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAmount", null, null));
      }
      importView.InLowStudentFinancialTransactionAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAmount", null, null));
      }
      try {
          setInLowStudentFinancialTransactionAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAmount", null, null));
      }
   }
 
   public String getInLowStudentFinancialTransactionRegistrationTypeCode() {
      return FixedStringAttr.valueOf(importView.InLowStudentFinancialTransactionRegistrationTypeCode, 2);
   }
   public void setInLowStudentFinancialTransactionRegistrationTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionRegistrationTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionRegistrationTypeCode", null, null));
      }
      importView.InLowStudentFinancialTransactionRegistrationTypeCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInLowStudentFinancialTransactionAcademicYear() {
      return importView.InLowStudentFinancialTransactionAcademicYear;
   }
   public void setInLowStudentFinancialTransactionAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAcademicYear", null, null));
      }
      importView.InLowStudentFinancialTransactionAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAcademicYear", null, null));
      }
      try {
          setInLowStudentFinancialTransactionAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAcademicYear", null, null));
      }
   }
 
   public short getInLowStudentFinancialTransactionAcademicPeriod() {
      return importView.InLowStudentFinancialTransactionAcademicPeriod;
   }
   public void setInLowStudentFinancialTransactionAcademicPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAcademicPeriod", null, null));
      }
      importView.InLowStudentFinancialTransactionAcademicPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionAcademicPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAcademicPeriod", null, null));
      }
      try {
          setInLowStudentFinancialTransactionAcademicPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionAcademicPeriod", null, null));
      }
   }
 
   public Calendar getInLowStudentFinancialTransactionDateTime() {
      return TimestampAttr.toCalendar(importView.InLowStudentFinancialTransactionDateTime);
   }
   public String getAsStringInLowStudentFinancialTransactionDateTime() {
      return TimestampAttr.toString(importView.InLowStudentFinancialTransactionDateTime);
   }
   public void setInLowStudentFinancialTransactionDateTime(Calendar s)
      throws PropertyVetoException {
      importView.InLowStudentFinancialTransactionDateTime = TimestampAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionDateTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInLowStudentFinancialTransactionDateTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InLowStudentFinancialTransactionDateTime = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InLowStudentFinancialTransactionDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InLowStudentFinancialTransactionDateTime", null, null));
         }
      }
   }
 
   public short getInLowStudentFinancialTransactionJournalSeqNr() {
      return importView.InLowStudentFinancialTransactionJournalSeqNr;
   }
   public void setInLowStudentFinancialTransactionJournalSeqNr(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InLowStudentFinancialTransactionJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InLowStudentFinancialTransactionJournalSeqNr", null, null));
      }
      importView.InLowStudentFinancialTransactionJournalSeqNr = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowStudentFinancialTransactionJournalSeqNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionJournalSeqNr", null, null));
      }
      try {
          setInLowStudentFinancialTransactionJournalSeqNr(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowStudentFinancialTransactionJournalSeqNr", null, null));
      }
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
 
   public double getInWsLibraryAmount() {
      return importView.InWsLibraryAmount;
   }
   public void setInWsLibraryAmount(double s)
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
         throw new PropertyVetoException("InWsLibraryAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InWsLibraryAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InWsLibraryAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InWsLibraryAmount", null, null));
      }
      importView.InWsLibraryAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInWsLibraryAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsLibraryAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsLibraryAmount", null, null));
      }
      try {
          setInWsLibraryAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsLibraryAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsLibraryAmount", null, null));
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
 
   public String getInStudentAccountClassificationDescription() {
      return FixedStringAttr.valueOf(importView.InStudentAccountClassificationDescription, 30);
   }
   public void setInStudentAccountClassificationDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InStudentAccountClassificationDescription must be <= 30 characters.",
               new PropertyChangeEvent (this, "InStudentAccountClassificationDescription", null, null));
      }
      importView.InStudentAccountClassificationDescription = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public String getInStudentAccountClassificationInUseFlag() {
      return FixedStringAttr.valueOf(importView.InStudentAccountClassificationInUseFlag, 1);
   }
   public void setInStudentAccountClassificationInUseFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfstj18sLstAccountTranStudentAccountClassificationInUseFlagPropertyEditor pe = new Sfstj18sLstAccountTranStudentAccountClassificationInUseFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InStudentAccountClassificationInUseFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAccountClassificationInUseFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAccountClassificationInUseFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAccountClassificationInUseFlag", null, null));
      }
      importView.InStudentAccountClassificationInUseFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInStudentAccountClassificationControlEntity() {
      return StringAttr.valueOf(importView.InStudentAccountClassificationControlEntity);
   }
   public void setInStudentAccountClassificationControlEntity(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAccountClassificationControlEntity must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAccountClassificationControlEntity", null, null));
      }
      importView.InStudentAccountClassificationControlEntity = StringAttr.valueOf(s, (short)5);
   }
 
   public String getInStudentAccountClassificationControlAccount() {
      return StringAttr.valueOf(importView.InStudentAccountClassificationControlAccount);
   }
   public void setInStudentAccountClassificationControlAccount(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InStudentAccountClassificationControlAccount must be <= 5 characters.",
               new PropertyChangeEvent (this, "InStudentAccountClassificationControlAccount", null, null));
      }
      importView.InStudentAccountClassificationControlAccount = StringAttr.valueOf(s, (short)5);
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
 
   public short getInWsStudentLibraryBlackList() {
      return importView.InWsStudentLibraryBlackList;
   }
   public void setInWsStudentLibraryBlackList(short s)
      throws PropertyVetoException {
      Sfstj18sLstAccountTranWsStudentLibraryBlackListPropertyEditor pe = new Sfstj18sLstAccountTranWsStudentLibraryBlackListPropertyEditor();
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
 
   public short getInWsStudentPhasedOutStatus() {
      return importView.InWsStudentPhasedOutStatus;
   }
   public void setInWsStudentPhasedOutStatus(short s)
      throws PropertyVetoException {
      Sfstj18sLstAccountTranWsStudentPhasedOutStatusPropertyEditor pe = new Sfstj18sLstAccountTranWsStudentPhasedOutStatusPropertyEditor();
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
      Sfstj18sLstAccountTranWsStudentFinancialBlockFlagPropertyEditor pe = new Sfstj18sLstAccountTranWsStudentFinancialBlockFlagPropertyEditor();
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
 
   public String getInWsStudentExamFeesDebtFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentExamFeesDebtFlag, 1);
   }
   public void setInWsStudentExamFeesDebtFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Sfstj18sLstAccountTranWsStudentExamFeesDebtFlagPropertyEditor pe = new Sfstj18sLstAccountTranWsStudentExamFeesDebtFlagPropertyEditor();
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
 
   public short getInWsStudentRdChequeCode() {
      return importView.InWsStudentRdChequeCode;
   }
   public void setInWsStudentRdChequeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentRdChequeCode has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentRdChequeCode", null, null));
      }
      importView.InWsStudentRdChequeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentRdChequeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentRdChequeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentRdChequeCode", null, null));
      }
      try {
          setInWsStudentRdChequeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentRdChequeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentRdChequeCode", null, null));
      }
   }
 
   public short getInWsStudentNsfasContractBlock() {
      return importView.InWsStudentNsfasContractBlock;
   }
   public void setInWsStudentNsfasContractBlock(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsStudentNsfasContractBlock has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsStudentNsfasContractBlock", null, null));
      }
      importView.InWsStudentNsfasContractBlock = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsStudentNsfasContractBlock(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStudentNsfasContractBlock is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentNsfasContractBlock", null, null));
      }
      try {
          setInWsStudentNsfasContractBlock(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStudentNsfasContractBlock is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStudentNsfasContractBlock", null, null));
      }
   }
 
   public String getInWsStudentAdministrationOrd() {
      return FixedStringAttr.valueOf(importView.InWsStudentAdministrationOrd, 1);
   }
   public void setInWsStudentAdministrationOrd(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentAdministrationOrd must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentAdministrationOrd", null, null));
      }
      importView.InWsStudentAdministrationOrd = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsStudentHandedOverFlag() {
      return FixedStringAttr.valueOf(importView.InWsStudentHandedOverFlag, 1);
   }
   public void setInWsStudentHandedOverFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsStudentHandedOverFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsStudentHandedOverFlag", null, null));
      }
      importView.InWsStudentHandedOverFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
   public void setInCsfClientServerCommunicationsRgvScrollUpFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsRgvScrollUpFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsRgvScrollUpFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsRgvScrollUpFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
   public void setInCsfClientServerCommunicationsRgvScrollDownFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsRgvScrollDownFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsRgvScrollDownFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsRgvScrollDownFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public int getInWsAddressReferenceNo() {
      return importView.InWsAddressReferenceNo;
   }
   public void setInWsAddressReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsAddressReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsAddressReferenceNo", null, null));
      }
      importView.InWsAddressReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInWsAddressReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressReferenceNo", null, null));
      }
      try {
          setInWsAddressReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressReferenceNo", null, null));
      }
   }
 
   public String getInWsAddressAddressReferenceFlag() {
      return FixedStringAttr.valueOf(importView.InWsAddressAddressReferenceFlag, 1);
   }
   public void setInWsAddressAddressReferenceFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsAddressAddressReferenceFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsAddressAddressReferenceFlag", null, null));
      }
      importView.InWsAddressAddressReferenceFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInWsAddressType() {
      return importView.InWsAddressType;
   }
   public void setInWsAddressType(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsAddressType has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsAddressType", null, null));
      }
      importView.InWsAddressType = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressType(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressType", null, null));
      }
      try {
          setInWsAddressType(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressType is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressType", null, null));
      }
   }
 
   public short getInWsAddressCategory() {
      return importView.InWsAddressCategory;
   }
   public void setInWsAddressCategory(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsAddressCategory has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsAddressCategory", null, null));
      }
      importView.InWsAddressCategory = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressCategory(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressCategory", null, null));
      }
      try {
          setInWsAddressCategory(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressCategory is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressCategory", null, null));
      }
   }
 
   public String getInWsAddressCategoryDescription() {
      return FixedStringAttr.valueOf(importView.InWsAddressCategoryDescription, 28);
   }
   public void setInWsAddressCategoryDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressCategoryDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressCategoryDescription", null, null));
      }
      importView.InWsAddressCategoryDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public String getInWsAccountTypeCode() {
      return FixedStringAttr.valueOf(importView.InWsAccountTypeCode, 4);
   }
   public void setInWsAccountTypeCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InWsAccountTypeCode must be <= 4 characters.",
               new PropertyChangeEvent (this, "InWsAccountTypeCode", null, null));
      }
      importView.InWsAccountTypeCode = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public short getInWsAccountTypeBranchCode() {
      return importView.InWsAccountTypeBranchCode;
   }
   public void setInWsAccountTypeBranchCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsAccountTypeBranchCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsAccountTypeBranchCode", null, null));
      }
      importView.InWsAccountTypeBranchCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAccountTypeBranchCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAccountTypeBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAccountTypeBranchCode", null, null));
      }
      try {
          setInWsAccountTypeBranchCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAccountTypeBranchCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAccountTypeBranchCode", null, null));
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
      Sfstj18sLstAccountTranWsStudentAnnualRecordLateRegistrationFlagPropertyEditor pe = new Sfstj18sLstAccountTranWsStudentAnnualRecordLateRegistrationFlagPropertyEditor();
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
 
   public short getInWsDocumentParagraphCode() {
      return importView.InWsDocumentParagraphCode;
   }
   public void setInWsDocumentParagraphCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InWsDocumentParagraphCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InWsDocumentParagraphCode", null, null));
      }
      importView.InWsDocumentParagraphCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsDocumentParagraphCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsDocumentParagraphCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsDocumentParagraphCode", null, null));
      }
      try {
          setInWsDocumentParagraphCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsDocumentParagraphCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsDocumentParagraphCode", null, null));
      }
   }
 
   public String getInWsDocumentParagraphMkLanguageCode() {
      return StringAttr.valueOf(importView.InWsDocumentParagraphMkLanguageCode);
   }
   public void setInWsDocumentParagraphMkLanguageCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsDocumentParagraphMkLanguageCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsDocumentParagraphMkLanguageCode", null, null));
      }
      importView.InWsDocumentParagraphMkLanguageCode = StringAttr.valueOf(s, (short)2);
   }
 
   public String getInWsDocumentParagraphWidthType() {
      return FixedStringAttr.valueOf(importView.InWsDocumentParagraphWidthType, 1);
   }
   public void setInWsDocumentParagraphWidthType(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsDocumentParagraphWidthType must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsDocumentParagraphWidthType", null, null));
      }
      importView.InWsDocumentParagraphWidthType = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsDocumentParagraphContent() {
      return StringAttr.valueOf(importView.InWsDocumentParagraphContent);
   }
   public void setInWsDocumentParagraphContent(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InWsDocumentParagraphContent must be <= 2 characters.",
               new PropertyChangeEvent (this, "InWsDocumentParagraphContent", null, null));
      }
      importView.InWsDocumentParagraphContent = StringAttr.valueOf(s, (short)2);
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
 
   public short getInStudentAccountRefundBlockedInd() {
      return importView.InStudentAccountRefundBlockedInd;
   }
   public void setInStudentAccountRefundBlockedInd(short s)
      throws PropertyVetoException {
      Sfstj18sLstAccountTranStudentAccountRefundBlockedIndPropertyEditor pe = new Sfstj18sLstAccountTranStudentAccountRefundBlockedIndPropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InStudentAccountRefundBlockedInd value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InStudentAccountRefundBlockedInd", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InStudentAccountRefundBlockedInd has more than 1 digits.",
               new PropertyChangeEvent (this, "InStudentAccountRefundBlockedInd", null, null));
      }
      importView.InStudentAccountRefundBlockedInd = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountRefundBlockedInd(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountRefundBlockedInd is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountRefundBlockedInd", null, null));
      }
      try {
          setInStudentAccountRefundBlockedInd(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountRefundBlockedInd is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountRefundBlockedInd", null, null));
      }
   }
 
   public String getInStudentAccountComments() {
      return FixedStringAttr.valueOf(importView.InStudentAccountComments, 60);
   }
   public void setInStudentAccountComments(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 60) {
         throw new PropertyVetoException("InStudentAccountComments must be <= 60 characters.",
               new PropertyChangeEvent (this, "InStudentAccountComments", null, null));
      }
      importView.InStudentAccountComments = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public int getInStudentAccountMkStudentNr() {
      return importView.InStudentAccountMkStudentNr;
   }
   public void setInStudentAccountMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentAccountMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentAccountMkStudentNr", null, null));
      }
      importView.InStudentAccountMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountMkStudentNr", null, null));
      }
      try {
          setInStudentAccountMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountMkStudentNr", null, null));
      }
   }
 
   public double getInStudentAccountMinRegistrationFee() {
      return importView.InStudentAccountMinRegistrationFee;
   }
   public void setInStudentAccountMinRegistrationFee(double s)
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
         throw new PropertyVetoException("InStudentAccountMinRegistrationFee has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountMinRegistrationFee", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InStudentAccountMinRegistrationFee has more than 5 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountMinRegistrationFee", null, null));
      }
      importView.InStudentAccountMinRegistrationFee = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountMinRegistrationFee(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountMinRegistrationFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountMinRegistrationFee", null, null));
      }
      try {
          setInStudentAccountMinRegistrationFee(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountMinRegistrationFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountMinRegistrationFee", null, null));
      }
   }
 
   public double getInStudentAccountDiverseTotal() {
      return importView.InStudentAccountDiverseTotal;
   }
   public void setInStudentAccountDiverseTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountDiverseTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountDiverseTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountDiverseTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountDiverseTotal", null, null));
      }
      importView.InStudentAccountDiverseTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountDiverseTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountDiverseTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountDiverseTotal", null, null));
      }
      try {
          setInStudentAccountDiverseTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountDiverseTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountDiverseTotal", null, null));
      }
   }
 
   public double getInStudentAccountCreditTotal() {
      return importView.InStudentAccountCreditTotal;
   }
   public void setInStudentAccountCreditTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountCreditTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountCreditTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountCreditTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountCreditTotal", null, null));
      }
      importView.InStudentAccountCreditTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountCreditTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountCreditTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountCreditTotal", null, null));
      }
      try {
          setInStudentAccountCreditTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountCreditTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountCreditTotal", null, null));
      }
   }
 
   public double getInStudentAccountRegTotal() {
      return importView.InStudentAccountRegTotal;
   }
   public void setInStudentAccountRegTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountRegTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountRegTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountRegTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountRegTotal", null, null));
      }
      importView.InStudentAccountRegTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountRegTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountRegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountRegTotal", null, null));
      }
      try {
          setInStudentAccountRegTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountRegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountRegTotal", null, null));
      }
   }
 
   public double getInStudentAccountFirstPayment() {
      return importView.InStudentAccountFirstPayment;
   }
   public void setInStudentAccountFirstPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountFirstPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountFirstPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountFirstPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountFirstPayment", null, null));
      }
      importView.InStudentAccountFirstPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountFirstPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountFirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountFirstPayment", null, null));
      }
      try {
          setInStudentAccountFirstPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountFirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountFirstPayment", null, null));
      }
   }
 
   public double getInStudentAccountFinalPayment() {
      return importView.InStudentAccountFinalPayment;
   }
   public void setInStudentAccountFinalPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountFinalPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountFinalPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountFinalPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountFinalPayment", null, null));
      }
      importView.InStudentAccountFinalPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountFinalPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountFinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountFinalPayment", null, null));
      }
      try {
          setInStudentAccountFinalPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountFinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountFinalPayment", null, null));
      }
   }
 
   public String getInStudentAccountSrcLevyPaid() {
      return FixedStringAttr.valueOf(importView.InStudentAccountSrcLevyPaid, 1);
   }
   public void setInStudentAccountSrcLevyPaid(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAccountSrcLevyPaid must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAccountSrcLevyPaid", null, null));
      }
      importView.InStudentAccountSrcLevyPaid = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public double getInStudentAccountSemester1Final() {
      return importView.InStudentAccountSemester1Final;
   }
   public void setInStudentAccountSemester1Final(double s)
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
         throw new PropertyVetoException("InStudentAccountSemester1Final has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountSemester1Final", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountSemester1Final has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountSemester1Final", null, null));
      }
      importView.InStudentAccountSemester1Final = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountSemester1Final(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountSemester1Final is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSemester1Final", null, null));
      }
      try {
          setInStudentAccountSemester1Final(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountSemester1Final is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSemester1Final", null, null));
      }
   }
 
   public String getInStudentAccountSoftwareFeesCharged() {
      return FixedStringAttr.valueOf(importView.InStudentAccountSoftwareFeesCharged, 1);
   }
   public void setInStudentAccountSoftwareFeesCharged(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InStudentAccountSoftwareFeesCharged must be <= 1 characters.",
               new PropertyChangeEvent (this, "InStudentAccountSoftwareFeesCharged", null, null));
      }
      importView.InStudentAccountSoftwareFeesCharged = FixedStringAttr.valueOf(s, (short)1);
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
 
   public double getInStudentAccountSem6RegTotal() {
      return importView.InStudentAccountSem6RegTotal;
   }
   public void setInStudentAccountSem6RegTotal(double s)
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
         throw new PropertyVetoException("InStudentAccountSem6RegTotal has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountSem6RegTotal", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountSem6RegTotal has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountSem6RegTotal", null, null));
      }
      importView.InStudentAccountSem6RegTotal = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountSem6RegTotal(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountSem6RegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSem6RegTotal", null, null));
      }
      try {
          setInStudentAccountSem6RegTotal(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountSem6RegTotal is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSem6RegTotal", null, null));
      }
   }
 
   public double getInStudentAccountSem6FirstPayment() {
      return importView.InStudentAccountSem6FirstPayment;
   }
   public void setInStudentAccountSem6FirstPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountSem6FirstPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountSem6FirstPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountSem6FirstPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountSem6FirstPayment", null, null));
      }
      importView.InStudentAccountSem6FirstPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountSem6FirstPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountSem6FirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSem6FirstPayment", null, null));
      }
      try {
          setInStudentAccountSem6FirstPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountSem6FirstPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSem6FirstPayment", null, null));
      }
   }
 
   public double getInStudentAccountSem6FinalPayment() {
      return importView.InStudentAccountSem6FinalPayment;
   }
   public void setInStudentAccountSem6FinalPayment(double s)
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
         throw new PropertyVetoException("InStudentAccountSem6FinalPayment has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountSem6FinalPayment", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountSem6FinalPayment has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountSem6FinalPayment", null, null));
      }
      importView.InStudentAccountSem6FinalPayment = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountSem6FinalPayment(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountSem6FinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSem6FinalPayment", null, null));
      }
      try {
          setInStudentAccountSem6FinalPayment(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountSem6FinalPayment is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountSem6FinalPayment", null, null));
      }
   }
 
   public double getInStudentAccountPrevSem6First() {
      return importView.InStudentAccountPrevSem6First;
   }
   public void setInStudentAccountPrevSem6First(double s)
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
         throw new PropertyVetoException("InStudentAccountPrevSem6First has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentAccountPrevSem6First", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentAccountPrevSem6First has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentAccountPrevSem6First", null, null));
      }
      importView.InStudentAccountPrevSem6First = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentAccountPrevSem6First(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentAccountPrevSem6First is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountPrevSem6First", null, null));
      }
      try {
          setInStudentAccountPrevSem6First(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentAccountPrevSem6First is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentAccountPrevSem6First", null, null));
      }
   }
 
   public double getInImmediateIefSuppliedTotalCurrency() {
      return importView.InImmediateIefSuppliedTotalCurrency;
   }
   public void setInImmediateIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InImmediateIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InImmediateIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InImmediateIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InImmediateIefSuppliedTotalCurrency", null, null));
      }
      importView.InImmediateIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInImmediateIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InImmediateIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InImmediateIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInImmediateIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InImmediateIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InImmediateIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInMarchIefSuppliedTotalCurrency() {
      return importView.InMarchIefSuppliedTotalCurrency;
   }
   public void setInMarchIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InMarchIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMarchIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InMarchIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InMarchIefSuppliedTotalCurrency", null, null));
      }
      importView.InMarchIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMarchIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMarchIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarchIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInMarchIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMarchIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMarchIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInMayIefSuppliedTotalCurrency() {
      return importView.InMayIefSuppliedTotalCurrency;
   }
   public void setInMayIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InMayIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InMayIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InMayIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InMayIefSuppliedTotalCurrency", null, null));
      }
      importView.InMayIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInMayIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InMayIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMayIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInMayIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InMayIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InMayIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public double getInAugustIefSuppliedTotalCurrency() {
      return importView.InAugustIefSuppliedTotalCurrency;
   }
   public void setInAugustIefSuppliedTotalCurrency(double s)
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
         throw new PropertyVetoException("InAugustIefSuppliedTotalCurrency has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InAugustIefSuppliedTotalCurrency", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000000000.0) {
         throw new PropertyVetoException("InAugustIefSuppliedTotalCurrency has more than 13 integral digits.",
               new PropertyChangeEvent (this, "InAugustIefSuppliedTotalCurrency", null, null));
      }
      importView.InAugustIefSuppliedTotalCurrency = DoubleAttr.valueOf(s);
   }
   public void setAsStringInAugustIefSuppliedTotalCurrency(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAugustIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAugustIefSuppliedTotalCurrency", null, null));
      }
      try {
          setInAugustIefSuppliedTotalCurrency(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAugustIefSuppliedTotalCurrency is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAugustIefSuppliedTotalCurrency", null, null));
      }
   }
 
   public short getInWsLearningCentreCode() {
      return importView.InWsLearningCentreCode;
   }
   public void setInWsLearningCentreCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsLearningCentreCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsLearningCentreCode", null, null));
      }
      importView.InWsLearningCentreCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsLearningCentreCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsLearningCentreCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsLearningCentreCode", null, null));
      }
      try {
          setInWsLearningCentreCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsLearningCentreCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsLearningCentreCode", null, null));
      }
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
 
   public String getInGScreenDescriptionCsfStringsString20(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGScreenDescriptionCsfStringsString20[index], 20);
   }
   public void setInGScreenDescriptionCsfStringsString20(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGScreenDescriptionCsfStringsString20 must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGScreenDescriptionCsfStringsString20", null, null));
      }
      importView.InGScreenDescriptionCsfStringsString20[index] = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInGAllocCsfStringsString11(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAllocCsfStringsString11[index], 11);
   }
   public void setInGAllocCsfStringsString11(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 11) {
         throw new PropertyVetoException("InGAllocCsfStringsString11 must be <= 11 characters.",
               new PropertyChangeEvent (this, "InGAllocCsfStringsString11", null, null));
      }
      importView.InGAllocCsfStringsString11[index] = FixedStringAttr.valueOf(s, (short)11);
   }
 
   public String getInGYearCsfStringsString4(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGYearCsfStringsString4[index], 4);
   }
   public void setInGYearCsfStringsString4(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InGYearCsfStringsString4 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InGYearCsfStringsString4", null, null));
      }
      importView.InGYearCsfStringsString4[index] = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInGBundleNrDocCsfStringsString8(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGBundleNrDocCsfStringsString8[index], 8);
   }
   public void setInGBundleNrDocCsfStringsString8(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InGBundleNrDocCsfStringsString8 must be <= 8 characters.",
               new PropertyChangeEvent (this, "InGBundleNrDocCsfStringsString8", null, null));
      }
      importView.InGBundleNrDocCsfStringsString8[index] = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGCsfLineActionBarAction[index], 1);
   }
   public void setInGCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGCsfLineActionBarAction", null, null));
      }
      importView.InGCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInGDebitStudentFinancialTransactionBundleDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGDebitStudentFinancialTransactionBundleDate[index]);
   }
   public int getAsIntInGDebitStudentFinancialTransactionBundleDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(importView.InGDebitStudentFinancialTransactionBundleDate[index]);
   }
   public void setInGDebitStudentFinancialTransactionBundleDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGDebitStudentFinancialTransactionBundleDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionBundleDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGDebitStudentFinancialTransactionBundleDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGDebitStudentFinancialTransactionBundleDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGDebitStudentFinancialTransactionBundleDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionBundleDate", null, null));
         }
      }
   }
   public void setAsIntInGDebitStudentFinancialTransactionBundleDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGDebitStudentFinancialTransactionBundleDate(index, temp);
   }
 
   public short getInGDebitStudentFinancialTransactionBundleNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGDebitStudentFinancialTransactionBundleNr[index];
   }
   public void setInGDebitStudentFinancialTransactionBundleNr(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionBundleNr has more than 4 digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionBundleNr", null, null));
      }
      importView.InGDebitStudentFinancialTransactionBundleNr[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionBundleNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionBundleNr", null, null));
      }
      try {
          setInGDebitStudentFinancialTransactionBundleNr(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionBundleNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionBundleNr", null, null));
      }
   }
 
   public double getInGDebitStudentFinancialTransactionAmount(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGDebitStudentFinancialTransactionAmount[index];
   }
   public void setInGDebitStudentFinancialTransactionAmount(int index, double s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
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
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionAmount has more than 7 integral digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAmount", null, null));
      }
      importView.InGDebitStudentFinancialTransactionAmount[index] = DoubleAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionAmount(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAmount", null, null));
      }
      try {
          setInGDebitStudentFinancialTransactionAmount(index, new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAmount", null, null));
      }
   }
 
   public short getInGDebitStudentFinancialTransactionDocumentSeqNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGDebitStudentFinancialTransactionDocumentSeqNr[index];
   }
   public void setInGDebitStudentFinancialTransactionDocumentSeqNr(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionDocumentSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      importView.InGDebitStudentFinancialTransactionDocumentSeqNr[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionDocumentSeqNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionDocumentSeqNr", null, null));
      }
      try {
          setInGDebitStudentFinancialTransactionDocumentSeqNr(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionDocumentSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionDocumentSeqNr", null, null));
      }
   }
 
   public short getInGDebitStudentFinancialTransactionAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGDebitStudentFinancialTransactionAcademicYear[index];
   }
   public void setInGDebitStudentFinancialTransactionAcademicYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAcademicYear", null, null));
      }
      importView.InGDebitStudentFinancialTransactionAcademicYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionAcademicYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAcademicYear", null, null));
      }
      try {
          setInGDebitStudentFinancialTransactionAcademicYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAcademicYear", null, null));
      }
   }
 
   public short getInGDebitStudentFinancialTransactionAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGDebitStudentFinancialTransactionAcademicPeriod[index];
   }
   public void setInGDebitStudentFinancialTransactionAcademicPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAcademicPeriod", null, null));
      }
      importView.InGDebitStudentFinancialTransactionAcademicPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionAcademicPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAcademicPeriod", null, null));
      }
      try {
          setInGDebitStudentFinancialTransactionAcademicPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionAcademicPeriod", null, null));
      }
   }
 
   public String getInGDebitStudentFinancialTransactionRegistrationTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGDebitStudentFinancialTransactionRegistrationTypeCode[index], 2);
   }
   public void setInGDebitStudentFinancialTransactionRegistrationTypeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionRegistrationTypeCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionRegistrationTypeCode", null, null));
      }
      importView.InGDebitStudentFinancialTransactionRegistrationTypeCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public Calendar getInGDebitStudentFinancialTransactionDateTime(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGDebitStudentFinancialTransactionDateTime[index]);
   }
   public String getAsStringInGDebitStudentFinancialTransactionDateTime(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(importView.InGDebitStudentFinancialTransactionDateTime[index]);
   }
   public void setInGDebitStudentFinancialTransactionDateTime(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGDebitStudentFinancialTransactionDateTime[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionDateTime(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGDebitStudentFinancialTransactionDateTime(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGDebitStudentFinancialTransactionDateTime[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGDebitStudentFinancialTransactionDateTime has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionDateTime", null, null));
         }
      }
   }
 
   public short getInGDebitStudentFinancialTransactionJournalSeqNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGDebitStudentFinancialTransactionJournalSeqNr[index];
   }
   public void setInGDebitStudentFinancialTransactionJournalSeqNr(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGDebitStudentFinancialTransactionJournalSeqNr has more than 3 digits.",
               new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionJournalSeqNr", null, null));
      }
      importView.InGDebitStudentFinancialTransactionJournalSeqNr[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGDebitStudentFinancialTransactionJournalSeqNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionJournalSeqNr", null, null));
      }
      try {
          setInGDebitStudentFinancialTransactionJournalSeqNr(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGDebitStudentFinancialTransactionJournalSeqNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGDebitStudentFinancialTransactionJournalSeqNr", null, null));
      }
   }
 
   public String getInGDebitCreditCsfStringsString2(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGDebitCreditCsfStringsString2[index], 2);
   }
   public void setInGDebitCreditCsfStringsString2(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGDebitCreditCsfStringsString2 must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGDebitCreditCsfStringsString2", null, null));
      }
      importView.InGDebitCreditCsfStringsString2[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInGTransTypeAllocationDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGTransTypeAllocationDescription[index], 30);
   }
   public void setInGTransTypeAllocationDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 30) {
         throw new PropertyVetoException("InGTransTypeAllocationDescription must be <= 30 characters.",
               new PropertyChangeEvent (this, "InGTransTypeAllocationDescription", null, null));
      }
      importView.InGTransTypeAllocationDescription[index] = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public double getInGTotalsWsTutorialRegistrationAmount(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGTotalsWsTutorialRegistrationAmount[index];
   }
   public void setInGTotalsWsTutorialRegistrationAmount(int index, double s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
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
         throw new PropertyVetoException("InGTotalsWsTutorialRegistrationAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InGTotalsWsTutorialRegistrationAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InGTotalsWsTutorialRegistrationAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InGTotalsWsTutorialRegistrationAmount", null, null));
      }
      importView.InGTotalsWsTutorialRegistrationAmount[index] = DoubleAttr.valueOf(s);
   }
   public void setAsStringInGTotalsWsTutorialRegistrationAmount(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGTotalsWsTutorialRegistrationAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGTotalsWsTutorialRegistrationAmount", null, null));
      }
      try {
          setInGTotalsWsTutorialRegistrationAmount(index, new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGTotalsWsTutorialRegistrationAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGTotalsWsTutorialRegistrationAmount", null, null));
      }
   }
 
   public String getInGSignCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSignCsfStringsString1[index], 1);
   }
   public void setInGSignCsfStringsString1(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSignCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSignCsfStringsString1", null, null));
      }
      importView.InGSignCsfStringsString1[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInStudentFcCancellationsMkStudentNr() {
      return importView.InStudentFcCancellationsMkStudentNr;
   }
   public void setInStudentFcCancellationsMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStudentFcCancellationsMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStudentFcCancellationsMkStudentNr", null, null));
      }
      importView.InStudentFcCancellationsMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStudentFcCancellationsMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFcCancellationsMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsMkStudentNr", null, null));
      }
      try {
          setInStudentFcCancellationsMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFcCancellationsMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsMkStudentNr", null, null));
      }
   }
 
   public short getInStudentFcCancellationsMkAcademicYear() {
      return importView.InStudentFcCancellationsMkAcademicYear;
   }
   public void setInStudentFcCancellationsMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudentFcCancellationsMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStudentFcCancellationsMkAcademicYear", null, null));
      }
      importView.InStudentFcCancellationsMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentFcCancellationsMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFcCancellationsMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsMkAcademicYear", null, null));
      }
      try {
          setInStudentFcCancellationsMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFcCancellationsMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsMkAcademicYear", null, null));
      }
   }
 
   public short getInStudentFcCancellationsNrOfModules() {
      return importView.InStudentFcCancellationsNrOfModules;
   }
   public void setInStudentFcCancellationsNrOfModules(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InStudentFcCancellationsNrOfModules has more than 2 digits.",
               new PropertyChangeEvent (this, "InStudentFcCancellationsNrOfModules", null, null));
      }
      importView.InStudentFcCancellationsNrOfModules = ShortAttr.valueOf(s);
   }
   public void setAsStringInStudentFcCancellationsNrOfModules(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFcCancellationsNrOfModules is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsNrOfModules", null, null));
      }
      try {
          setInStudentFcCancellationsNrOfModules(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFcCancellationsNrOfModules is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsNrOfModules", null, null));
      }
   }
 
   public double getInStudentFcCancellationsAmount() {
      return importView.InStudentFcCancellationsAmount;
   }
   public void setInStudentFcCancellationsAmount(double s)
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
         throw new PropertyVetoException("InStudentFcCancellationsAmount has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudentFcCancellationsAmount", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InStudentFcCancellationsAmount has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InStudentFcCancellationsAmount", null, null));
      }
      importView.InStudentFcCancellationsAmount = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudentFcCancellationsAmount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudentFcCancellationsAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsAmount", null, null));
      }
      try {
          setInStudentFcCancellationsAmount(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudentFcCancellationsAmount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudentFcCancellationsAmount", null, null));
      }
   }
 
   public double getInStudent3gModemFee() {
      return importView.InStudent3gModemFee;
   }
   public void setInStudent3gModemFee(double s)
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
         throw new PropertyVetoException("InStudent3gModemFee has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudent3gModemFee", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudent3gModemFee has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InStudent3gModemFee", null, null));
      }
      importView.InStudent3gModemFee = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudent3gModemFee(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudent3gModemFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudent3gModemFee", null, null));
      }
      try {
          setInStudent3gModemFee(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudent3gModemFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudent3gModemFee", null, null));
      }
   }
 
   public double getInStudent3gSimFee() {
      return importView.InStudent3gSimFee;
   }
   public void setInStudent3gSimFee(double s)
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
         throw new PropertyVetoException("InStudent3gSimFee has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InStudent3gSimFee", null, null));
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStudent3gSimFee has more than 4 integral digits.",
               new PropertyChangeEvent (this, "InStudent3gSimFee", null, null));
      }
      importView.InStudent3gSimFee = DoubleAttr.valueOf(s);
   }
   public void setAsStringInStudent3gSimFee(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStudent3gSimFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudent3gSimFee", null, null));
      }
      try {
          setInStudent3gSimFee(new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStudent3gSimFee is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStudent3gSimFee", null, null));
      }
   }
 
   public String getIn3gActivatedCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.In3gActivatedCsfStringsString1, 1);
   }
   public void setIn3gActivatedCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("In3gActivatedCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "In3gActivatedCsfStringsString1", null, null));
      }
      importView.In3gActivatedCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getIn3gPaidCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.In3gPaidCsfStringsString1, 1);
   }
   public void setIn3gPaidCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("In3gPaidCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "In3gPaidCsfStringsString1", null, null));
      }
      importView.In3gPaidCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int OutGroupMax = 50;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGScreenDescriptionCsfStringsString20(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGScreenDescriptionCsfStringsString20[index], 20);
   }
 
   public String getOutGAllocCsfStringsString11(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAllocCsfStringsString11[index], 11);
   }
 
   public String getOutGYearCsfStringsString4(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGYearCsfStringsString4[index], 4);
   }
 
   public String getOutGBundleNrDocCsfStringsString8(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGBundleNrDocCsfStringsString8[index], 8);
   }
 
   public String getOutGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarAction[index], 1);
   }
 
   public Calendar getOutGDebitStudentFinancialTransactionBundleDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGDebitStudentFinancialTransactionBundleDate[index]);
   }
   public int getAsIntOutGDebitStudentFinancialTransactionBundleDate(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGDebitStudentFinancialTransactionBundleDate[index]);
   }
 
   public short getOutGDebitStudentFinancialTransactionBundleNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGDebitStudentFinancialTransactionBundleNr[index];
   }
 
   public double getOutGDebitStudentFinancialTransactionAmount(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGDebitStudentFinancialTransactionAmount[index];
   }
 
   public short getOutGDebitStudentFinancialTransactionDocumentSeqNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGDebitStudentFinancialTransactionDocumentSeqNr[index];
   }
 
   public short getOutGDebitStudentFinancialTransactionAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGDebitStudentFinancialTransactionAcademicYear[index];
   }
 
   public short getOutGDebitStudentFinancialTransactionAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGDebitStudentFinancialTransactionAcademicPeriod[index];
   }
 
   public String getOutGDebitStudentFinancialTransactionRegistrationTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGDebitStudentFinancialTransactionRegistrationTypeCode[index], 2);
   }
 
   public Calendar getOutGDebitStudentFinancialTransactionDateTime(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGDebitStudentFinancialTransactionDateTime[index]);
   }
   public String getAsStringOutGDebitStudentFinancialTransactionDateTime(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGDebitStudentFinancialTransactionDateTime[index]);
   }
 
   public short getOutGDebitStudentFinancialTransactionJournalSeqNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGDebitStudentFinancialTransactionJournalSeqNr[index];
   }
 
   public String getOutGDebitCreditCsfStringsString2(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGDebitCreditCsfStringsString2[index], 2);
   }
 
   public String getOutGTransTypeAllocationDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGTransTypeAllocationDescription[index], 30);
   }
 
   public double getOutGTotalsWsTutorialRegistrationAmount(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGTotalsWsTutorialRegistrationAmount[index];
   }
 
   public String getOutGSignCsfStringsString1(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSignCsfStringsString1[index], 1);
   }
 
   public String getOutHistoryIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutHistoryIefSuppliedFlag, 1);
   }
 
   public short getOutFinGlobalCurrentAcademicYear() {
      return exportView.OutFinGlobalCurrentAcademicYear;
   }
 
   public Calendar getOutStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toCalendar(exportView.OutStudentFinancialTransactionHiBundleDate);
   }
   public int getAsIntOutStudentFinancialTransactionHiBundleDate() {
      return DateAttr.toInt(exportView.OutStudentFinancialTransactionHiBundleDate);
   }
 
   public short getOutStudentFinancialTransactionHiBundleNr() {
      return exportView.OutStudentFinancialTransactionHiBundleNr;
   }
 
   public short getOutStudentFinancialTransactionHiDocumentSeqNr() {
      return exportView.OutStudentFinancialTransactionHiDocumentSeqNr;
   }
 
   public short getOutStudentFinancialTransactionHiJournalSeqNr() {
      return exportView.OutStudentFinancialTransactionHiJournalSeqNr;
   }
 
   public double getOutStudentFinancialTransactionHiAmount() {
      return exportView.OutStudentFinancialTransactionHiAmount;
   }
 
   public short getOutStudentFinancialTransactionHiBundleType() {
      return exportView.OutStudentFinancialTransactionHiBundleType;
   }
 
   public String getOutStudentFinancialTransactionHiReference() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiReference, 10);
   }
 
   public String getOutStudentFinancialTransactionHiRegistrationType() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiRegistrationType, 2);
   }
 
   public String getOutStudentFinancialTransactionHiMkGlFund() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiMkGlFund, 5);
   }
 
   public String getOutStudentFinancialTransactionHiMkGlEntity() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiMkGlEntity, 5);
   }
 
   public String getOutStudentFinancialTransactionHiMkGlAccount() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiMkGlAccount, 5);
   }
 
   public String getOutStudentFinancialTransactionHiMkGlItem() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiMkGlItem, 5);
   }
 
   public String getOutStudentFinancialTransactionHiPayMethod() {
      return FixedStringAttr.valueOf(exportView.OutStudentFinancialTransactionHiPayMethod, 7);
   }
 
   public Calendar getOutStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toCalendar(exportView.OutStudentFinancialTransactionHiDateTime);
   }
   public String getAsStringOutStudentFinancialTransactionHiDateTime() {
      return TimestampAttr.toString(exportView.OutStudentFinancialTransactionHiDateTime);
   }
 
   public short getOutStudentAccountHistoryMkAcademicYear() {
      return exportView.OutStudentAccountHistoryMkAcademicYear;
   }
 
   public int getOutStudentAccountHistoryMkStudentNr() {
      return exportView.OutStudentAccountHistoryMkStudentNr;
   }
 
   public String getOutStudentAccountHistoryMkAccountType() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountHistoryMkAccountType, 4);
   }
 
   public short getOutStudentAccountHistoryMkBranchCode() {
      return exportView.OutStudentAccountHistoryMkBranchCode;
   }
 
   public String getOutStudentAccountHistoryMkQualification() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountHistoryMkQualification, 5);
   }
 
   public double getOutStudentAccountHistoryBalance() {
      return exportView.OutStudentAccountHistoryBalance;
   }
 
   public double getOutStudentAccountHistoryMinRegistrationF() {
      return exportView.OutStudentAccountHistoryMinRegistrationF;
   }
 
   public double getOutStudentAccountHistoryDiverseTotal() {
      return exportView.OutStudentAccountHistoryDiverseTotal;
   }
 
   public double getOutStudentAccountHistoryCreditTotal() {
      return exportView.OutStudentAccountHistoryCreditTotal;
   }
 
   public double getOutStudentAccountHistoryRegTotal() {
      return exportView.OutStudentAccountHistoryRegTotal;
   }
 
   public double getOutStudentAccountHistorySemester1Final() {
      return exportView.OutStudentAccountHistorySemester1Final;
   }
 
   public double getOutStudentAccountHistoryFirstPayment() {
      return exportView.OutStudentAccountHistoryFirstPayment;
   }
 
   public double getOutStudentAccountHistoryFinalPayment() {
      return exportView.OutStudentAccountHistoryFinalPayment;
   }
 
   public String getOutStudentAccountHistoryComments() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountHistoryComments, 60);
   }
 
   public double getOutStudentAccountHistoryDueImmediately() {
      return exportView.OutStudentAccountHistoryDueImmediately;
   }
 
   public double getOutStudentAccountHistorySem6RegTotal() {
      return exportView.OutStudentAccountHistorySem6RegTotal;
   }
 
   public double getOutStudentAccountHistorySem6FirstPayment() {
      return exportView.OutStudentAccountHistorySem6FirstPayment;
   }
 
   public double getOutStudentAccountHistorySem6FinalPayment() {
      return exportView.OutStudentAccountHistorySem6FinalPayment;
   }
 
   public double getOutStudentAccountHistoryPrevSem6First() {
      return exportView.OutStudentAccountHistoryPrevSem6First;
   }
 
   public String getOutWsWorkstationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsWorkstationCode, 10);
   }
 
   public double getOutWsLibraryAmount() {
      return exportView.OutWsLibraryAmount;
   }
 
   public short getOutWsDocumentParagraphCode() {
      return exportView.OutWsDocumentParagraphCode;
   }
 
   public String getOutWsDocumentParagraphMkLanguageCode() {
      return StringAttr.valueOf(exportView.OutWsDocumentParagraphMkLanguageCode);
   }
 
   public String getOutWsDocumentParagraphWidthType() {
      return FixedStringAttr.valueOf(exportView.OutWsDocumentParagraphWidthType, 1);
   }
 
   public String getOutWsDocumentParagraphContent() {
      return StringAttr.valueOf(exportView.OutWsDocumentParagraphContent);
   }
 
   public int getOutWsUserNumber() {
      return exportView.OutWsUserNumber;
   }
 
   public String getOutWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutWsPrinterCode, 10);
   }
 
   public short getOutWsAccountTypeBranchCode() {
      return exportView.OutWsAccountTypeBranchCode;
   }
 
   public String getOutWsAccountTypeCode() {
      return FixedStringAttr.valueOf(exportView.OutWsAccountTypeCode, 4);
   }
 
   public double getOutStudentAccountBalance() {
      return exportView.OutStudentAccountBalance;
   }
 
   public short getOutStudentAccountRefundBlockedInd() {
      return exportView.OutStudentAccountRefundBlockedInd;
   }
 
   public String getOutStudentAccountComments() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountComments, 60);
   }
 
   public int getOutStudentAccountMkStudentNr() {
      return exportView.OutStudentAccountMkStudentNr;
   }
 
   public double getOutStudentAccountMinRegistrationFee() {
      return exportView.OutStudentAccountMinRegistrationFee;
   }
 
   public double getOutStudentAccountDiverseTotal() {
      return exportView.OutStudentAccountDiverseTotal;
   }
 
   public double getOutStudentAccountCreditTotal() {
      return exportView.OutStudentAccountCreditTotal;
   }
 
   public double getOutStudentAccountRegTotal() {
      return exportView.OutStudentAccountRegTotal;
   }
 
   public double getOutStudentAccountFirstPayment() {
      return exportView.OutStudentAccountFirstPayment;
   }
 
   public double getOutStudentAccountFinalPayment() {
      return exportView.OutStudentAccountFinalPayment;
   }
 
   public String getOutStudentAccountSrcLevyPaid() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountSrcLevyPaid, 1);
   }
 
   public double getOutStudentAccountSemester1Final() {
      return exportView.OutStudentAccountSemester1Final;
   }
 
   public String getOutStudentAccountSoftwareFeesCharged() {
      return FixedStringAttr.valueOf(exportView.OutStudentAccountSoftwareFeesCharged, 1);
   }
 
   public double getOutStudentAccountDueImmediately() {
      return exportView.OutStudentAccountDueImmediately;
   }
 
   public double getOutStudentAccountSem6RegTotal() {
      return exportView.OutStudentAccountSem6RegTotal;
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
 
   public int getOutWsStudentNr() {
      return exportView.OutWsStudentNr;
   }
 
   public String getOutWsStudentSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentSurname, 28);
   }
 
   public String getOutWsStudentInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentInitials, 10);
   }
 
   public short getOutWsStudentLibraryBlackList() {
      return exportView.OutWsStudentLibraryBlackList;
   }
 
   public String getOutWsStudentFinancialBlockFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentFinancialBlockFlag, 1);
   }
 
   public short getOutWsStudentUnisaUndergradYearsRegistered() {
      return exportView.OutWsStudentUnisaUndergradYearsRegistered;
   }
 
   public String getOutWsStudentMkCorrespondenceLanguage() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCorrespondenceLanguage, 2);
   }
 
   public String getOutWsStudentMkTitle() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkTitle, 10);
   }
 
   public String getOutWsStudentMkCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentMkCountryCode, 4);
   }
 
   public String getOutWsStudentExamFeesDebtFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentExamFeesDebtFlag, 1);
   }
 
   public short getOutWsStudentRdChequeCode() {
      return exportView.OutWsStudentRdChequeCode;
   }
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public short getOutWsStudentMkLastAcademicPeriod() {
      return exportView.OutWsStudentMkLastAcademicPeriod;
   }
 
   public short getOutWsStudentPhasedOutStatus() {
      return exportView.OutWsStudentPhasedOutStatus;
   }
 
   public short getOutWsStudentNsfasContractBlock() {
      return exportView.OutWsStudentNsfasContractBlock;
   }
 
   public String getOutWsStudentAdministrationOrd() {
      return FixedStringAttr.valueOf(exportView.OutWsStudentAdministrationOrd, 1);
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
 
   public String getOutCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsListLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsMaintLinkFlag, 1);
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
 
   public String getOutCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
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
 
   public String getOutCsfClientServerCommunicationsCancelFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsCancelFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsForceDatabaseRead() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsForceDatabaseRead, 1);
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
 
   public double getOutImmediateIefSuppliedTotalCurrency() {
      return exportView.OutImmediateIefSuppliedTotalCurrency;
   }
 
   public double getOutMarchIefSuppliedTotalCurrency() {
      return exportView.OutMarchIefSuppliedTotalCurrency;
   }
 
   public double getOutMayIefSuppliedTotalCurrency() {
      return exportView.OutMayIefSuppliedTotalCurrency;
   }
 
   public double getOutAugustIefSuppliedTotalCurrency() {
      return exportView.OutAugustIefSuppliedTotalCurrency;
   }
 
   public short getOutWsLearningCentreCode() {
      return exportView.OutWsLearningCentreCode;
   }
 
   public double getOutNovemberIefSuppliedTotalCurrency() {
      return exportView.OutNovemberIefSuppliedTotalCurrency;
   }
 
   public double getOutNextMarchIefSuppliedTotalCurrency() {
      return exportView.OutNextMarchIefSuppliedTotalCurrency;
   }
 
   public String getOutTestCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutTestCsfStringsString1, 1);
   }
 
   public String getOutWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutWsMethodResultReturnCode, 2);
   }
 
   public final int OutGrpMax = 50;
   public short getOutGrpCount() {
      return (short)(exportView.OutGrp_MA);
   };
 
   public short getOutGWsDocumentParagraphLineSequenceNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGWsDocumentParagraphLineSequenceNumber[index];
   }
 
   public String getOutGWsDocumentParagraphLineText(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGWsDocumentParagraphLineText[index]);
   }
 
   public String getOutGWsDocumentParagraphLineAmountFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsDocumentParagraphLineAmountFlag[index], 1);
   }
 
   public String getOutGWsDocumentParagraphLineDateFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsDocumentParagraphLineDateFlag[index], 1);
   }
 
   public String getOutGWsDocumentParagraphLineTextFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsDocumentParagraphLineTextFlag[index], 1);
   }
 
   public String getOutGWsDocumentParagraphLineNumberFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsDocumentParagraphLineNumberFlag[index], 1);
   }
 
   public int getOutStudentFcCancellationsMkStudentNr() {
      return exportView.OutStudentFcCancellationsMkStudentNr;
   }
 
   public short getOutStudentFcCancellationsMkAcademicYear() {
      return exportView.OutStudentFcCancellationsMkAcademicYear;
   }
 
   public short getOutStudentFcCancellationsNrOfModules() {
      return exportView.OutStudentFcCancellationsNrOfModules;
   }
 
   public double getOutStudentFcCancellationsAmount() {
      return exportView.OutStudentFcCancellationsAmount;
   }
 
   public double getOutStudent3gModemFee() {
      return exportView.OutStudent3gModemFee;
   }
 
   public double getOutStudent3gSimFee() {
      return exportView.OutStudent3gSimFee;
   }
 
   public String getOut3gActivatedCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.Out3gActivatedCsfStringsString1, 1);
   }
 
   public String getOut3gPaidCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.Out3gPaidCsfStringsString1, 1);
   }
 
};
