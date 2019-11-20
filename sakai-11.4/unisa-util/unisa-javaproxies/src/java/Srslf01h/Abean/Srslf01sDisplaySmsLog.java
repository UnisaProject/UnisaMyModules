package Srslf01h.Abean;
 
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
public class Srslf01sDisplaySmsLog  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srslf01sDisplaySmsLog");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   private static DecimalFormat decimalFormatter;
 
   public Srslf01sDisplaySmsLog() {
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
 
 
   private Srslf01sDisplaySmsLogOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srslf01sDisplaySmsLogOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrslf01sDisplaySmsLogOperation();
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
      private Srslf01sDisplaySmsLog rP;
      operListener(Srslf01sDisplaySmsLog r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srslf01sDisplaySmsLog", "Listener heard that Srslf01sDisplaySmsLogOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srslf01sDisplaySmsLog ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srslf01sDisplaySmsLog");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srslf01sDisplaySmsLog ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srslf01sDisplaySmsLog";
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
      importView.InSmsreqGroup_MA = IntAttr.valueOf(InSmsreqGroupMax);
      importView.InSmslogGroup_MA = IntAttr.valueOf(InSmslogGroupMax);
      exportView.OutSmsreqGroup_MA = IntAttr.getDefaultValue();
      exportView.OutSmslogGroup_MA = IntAttr.getDefaultValue();
   }

   Srslf01h.SRSLF01S_IA importView = Srslf01h.SRSLF01S_IA.getInstance();
   Srslf01h.SRSLF01S_OA exportView = Srslf01h.SRSLF01S_OA.getInstance();
   public String getInCsfStringsString500() {
      return StringAttr.valueOf(importView.InCsfStringsString500);
   }
   public void setInCsfStringsString500(String s)
      throws PropertyVetoException {
      if (s.length() > 500) {
         throw new PropertyVetoException("InCsfStringsString500 must be <= 500 characters.",
               new PropertyChangeEvent (this, "InCsfStringsString500", null, null));
      }
      importView.InCsfStringsString500 = StringAttr.valueOf(s, (short)500);
   }
 
   public int getInWsStaffV2Persno() {
      return importView.InWsStaffV2Persno;
   }
   public void setInWsStaffV2Persno(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStaffV2Persno has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStaffV2Persno", null, null));
      }
      importView.InWsStaffV2Persno = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStaffV2Persno(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStaffV2Persno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStaffV2Persno", null, null));
      }
      try {
          setInWsStaffV2Persno(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStaffV2Persno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStaffV2Persno", null, null));
      }
   }
 
   public String getInWsStaffV2Surname() {
      return FixedStringAttr.valueOf(importView.InWsStaffV2Surname, 28);
   }
   public void setInWsStaffV2Surname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsStaffV2Surname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsStaffV2Surname", null, null));
      }
      importView.InWsStaffV2Surname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsStaffV2Initials() {
      return FixedStringAttr.valueOf(importView.InWsStaffV2Initials, 10);
   }
   public void setInWsStaffV2Initials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStaffV2Initials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStaffV2Initials", null, null));
      }
      importView.InWsStaffV2Initials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStaffV2Title() {
      return FixedStringAttr.valueOf(importView.InWsStaffV2Title, 10);
   }
   public void setInWsStaffV2Title(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStaffV2Title must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStaffV2Title", null, null));
      }
      importView.InWsStaffV2Title = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStaffV2MkRcCode() {
      return FixedStringAttr.valueOf(importView.InWsStaffV2MkRcCode, 6);
   }
   public void setInWsStaffV2MkRcCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InWsStaffV2MkRcCode must be <= 6 characters.",
               new PropertyChangeEvent (this, "InWsStaffV2MkRcCode", null, null));
      }
      importView.InWsStaffV2MkRcCode = FixedStringAttr.valueOf(s, (short)6);
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
 
   public String getInStaffNameCsfStringsString30() {
      return FixedStringAttr.valueOf(importView.InStaffNameCsfStringsString30, 30);
   }
   public void setInStaffNameCsfStringsString30(String s)
      throws PropertyVetoException {
      if (s.length() > 30) {
         throw new PropertyVetoException("InStaffNameCsfStringsString30 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InStaffNameCsfStringsString30", null, null));
      }
      importView.InStaffNameCsfStringsString30 = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public int getInWsStaffPersno() {
      return importView.InWsStaffPersno;
   }
   public void setInWsStaffPersno(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InWsStaffPersno has more than 8 digits.",
               new PropertyChangeEvent (this, "InWsStaffPersno", null, null));
      }
      importView.InWsStaffPersno = IntAttr.valueOf(s);
   }
   public void setAsStringInWsStaffPersno(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStaffPersno", null, null));
      }
      try {
          setInWsStaffPersno(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsStaffPersno", null, null));
      }
   }
 
   public String getInWsStaffSurname() {
      return FixedStringAttr.valueOf(importView.InWsStaffSurname, 28);
   }
   public void setInWsStaffSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsStaffSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsStaffSurname", null, null));
      }
      importView.InWsStaffSurname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsStaffInitials() {
      return FixedStringAttr.valueOf(importView.InWsStaffInitials, 10);
   }
   public void setInWsStaffInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStaffInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStaffInitials", null, null));
      }
      importView.InWsStaffInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStaffTitle() {
      return FixedStringAttr.valueOf(importView.InWsStaffTitle, 10);
   }
   public void setInWsStaffTitle(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsStaffTitle must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsStaffTitle", null, null));
      }
      importView.InWsStaffTitle = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsStaffMkRcCode() {
      return FixedStringAttr.valueOf(importView.InWsStaffMkRcCode, 6);
   }
   public void setInWsStaffMkRcCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 6) {
         throw new PropertyVetoException("InWsStaffMkRcCode must be <= 6 characters.",
               new PropertyChangeEvent (this, "InWsStaffMkRcCode", null, null));
      }
      importView.InWsStaffMkRcCode = FixedStringAttr.valueOf(s, (short)6);
   }
 
   public final int InSmsreqGroupMax = 50;
   public short getInSmsreqGroupCount() {
      return (short)(importView.InSmsreqGroup_MA);
   };
 
   public void setInSmsreqGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InSmsreqGroupMax) {
         throw new PropertyVetoException("InSmsreqGroupCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InSmsreqGroupCount", null, null));
      } else {
         importView.InSmsreqGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInGSmsRequestBatchNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGSmsRequestBatchNr[index];
   }
   public void setInGSmsRequestBatchNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGSmsRequestBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InGSmsRequestBatchNr", null, null));
      }
      importView.InGSmsRequestBatchNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGSmsRequestBatchNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSmsRequestBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsRequestBatchNr", null, null));
      }
      try {
          setInGSmsRequestBatchNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSmsRequestBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsRequestBatchNr", null, null));
      }
   }
 
   public Calendar getInGSmsRequestRequestTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGSmsRequestRequestTimestamp[index]);
   }
   public String getAsStringInGSmsRequestRequestTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(importView.InGSmsRequestRequestTimestamp[index]);
   }
   public void setInGSmsRequestRequestTimestamp(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGSmsRequestRequestTimestamp[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGSmsRequestRequestTimestamp(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGSmsRequestRequestTimestamp(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGSmsRequestRequestTimestamp[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGSmsRequestRequestTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGSmsRequestRequestTimestamp", null, null));
         }
      }
   }
 
   public String getInGSmsRequestSmsMsg(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSmsRequestSmsMsg[index], 160);
   }
   public void setInGSmsRequestSmsMsg(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 160) {
         throw new PropertyVetoException("InGSmsRequestSmsMsg must be <= 160 characters.",
               new PropertyChangeEvent (this, "InGSmsRequestSmsMsg", null, null));
      }
      importView.InGSmsRequestSmsMsg[index] = FixedStringAttr.valueOf(s, (short)160);
   }
 
   public int getInGSmsRequestMessageCnt(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGSmsRequestMessageCnt[index];
   }
   public void setInGSmsRequestMessageCnt(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGSmsRequestMessageCnt has more than 7 digits.",
               new PropertyChangeEvent (this, "InGSmsRequestMessageCnt", null, null));
      }
      importView.InGSmsRequestMessageCnt[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGSmsRequestMessageCnt(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSmsRequestMessageCnt is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsRequestMessageCnt", null, null));
      }
      try {
          setInGSmsRequestMessageCnt(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSmsRequestMessageCnt is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsRequestMessageCnt", null, null));
      }
   }
 
   public double getInGSmsRequestTotalCost(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGSmsRequestTotalCost[index];
   }
   public void setInGSmsRequestTotalCost(int index, double s)
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
         throw new PropertyVetoException("InGSmsRequestTotalCost has more than 2 fractional digits.",
               new PropertyChangeEvent (this, "InGSmsRequestTotalCost", null, null));
      }
      if (java.lang.Math.abs(s) >= 100000000000.0) {
         throw new PropertyVetoException("InGSmsRequestTotalCost has more than 11 integral digits.",
               new PropertyChangeEvent (this, "InGSmsRequestTotalCost", null, null));
      }
      importView.InGSmsRequestTotalCost[index] = DoubleAttr.valueOf(s);
   }
   public void setAsStringInGSmsRequestTotalCost(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSmsRequestTotalCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsRequestTotalCost", null, null));
      }
      try {
          setInGSmsRequestTotalCost(index, new Double(s).doubleValue() );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSmsRequestTotalCost is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsRequestTotalCost", null, null));
      }
   }
 
   public String getInGReqCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGReqCsfLineActionBarAction[index], 1);
   }
   public void setInGReqCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGReqCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGReqCsfLineActionBarAction", null, null));
      }
      importView.InGReqCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGReqCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGReqCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGReqCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGReqCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGReqCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGReqCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int InSmslogGroupMax = 50;
   public short getInSmslogGroupCount() {
      return (short)(importView.InSmslogGroup_MA);
   };
 
   public void setInSmslogGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InSmslogGroupMax) {
         throw new PropertyVetoException("InSmslogGroupCount value is not a valid value. (0 to 50)",
               new PropertyChangeEvent (this, "InSmslogGroupCount", null, null));
      } else {
         importView.InSmslogGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public int getInGSmsLogMkBatchNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGSmsLogMkBatchNr[index];
   }
   public void setInGSmsLogMkBatchNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InGSmsLogMkBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InGSmsLogMkBatchNr", null, null));
      }
      importView.InGSmsLogMkBatchNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGSmsLogMkBatchNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsLogMkBatchNr", null, null));
      }
      try {
          setInGSmsLogMkBatchNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsLogMkBatchNr", null, null));
      }
   }
 
   public int getInGSmsLogReferenceNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return importView.InGSmsLogReferenceNr[index];
   }
   public void setInGSmsLogReferenceNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InGSmsLogReferenceNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InGSmsLogReferenceNr", null, null));
      }
      importView.InGSmsLogReferenceNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGSmsLogReferenceNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsLogReferenceNr", null, null));
      }
      try {
          setInGSmsLogReferenceNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGSmsLogReferenceNr", null, null));
      }
   }
 
   public String getInGSmsLogCellNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGSmsLogCellNr[index]);
   }
   public void setInGSmsLogCellNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InGSmsLogCellNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InGSmsLogCellNr", null, null));
      }
      importView.InGSmsLogCellNr[index] = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInGSmsLogMessage(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGSmsLogMessage[index]);
   }
   public void setInGSmsLogMessage(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 160) {
         throw new PropertyVetoException("InGSmsLogMessage must be <= 160 characters.",
               new PropertyChangeEvent (this, "InGSmsLogMessage", null, null));
      }
      importView.InGSmsLogMessage[index] = StringAttr.valueOf(s, (short)160);
   }
 
   public Calendar getInGSmsLogSentOn(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGSmsLogSentOn[index]);
   }
   public String getAsStringInGSmsLogSentOn(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(importView.InGSmsLogSentOn[index]);
   }
   public void setInGSmsLogSentOn(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      importView.InGSmsLogSentOn[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGSmsLogSentOn(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGSmsLogSentOn(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGSmsLogSentOn[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGSmsLogSentOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGSmsLogSentOn", null, null));
         }
      }
   }
 
   public String getInGSmsLogMessageStatus(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(importView.InGSmsLogMessageStatus[index]);
   }
   public void setInGSmsLogMessageStatus(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGSmsLogMessageStatus must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGSmsLogMessageStatus", null, null));
      }
      importView.InGSmsLogMessageStatus[index] = StringAttr.valueOf(s, (short)7);
   }
 
   public String getInGSmsCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSmsCsfLineActionBarAction[index], 1);
   }
   public void setInGSmsCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSmsCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSmsCsfLineActionBarAction", null, null));
      }
      importView.InGSmsCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGSmsCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGSmsCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGSmsCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGSmsCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGSmsCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGSmsCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInStartingSmsLogMkBatchNr() {
      return importView.InStartingSmsLogMkBatchNr;
   }
   public void setInStartingSmsLogMkBatchNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InStartingSmsLogMkBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InStartingSmsLogMkBatchNr", null, null));
      }
      importView.InStartingSmsLogMkBatchNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStartingSmsLogMkBatchNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingSmsLogMkBatchNr", null, null));
      }
      try {
          setInStartingSmsLogMkBatchNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingSmsLogMkBatchNr", null, null));
      }
   }
 
   public int getInStartingSmsLogReferenceNr() {
      return importView.InStartingSmsLogReferenceNr;
   }
   public void setInStartingSmsLogReferenceNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InStartingSmsLogReferenceNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InStartingSmsLogReferenceNr", null, null));
      }
      importView.InStartingSmsLogReferenceNr = IntAttr.valueOf(s);
   }
   public void setAsStringInStartingSmsLogReferenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingSmsLogReferenceNr", null, null));
      }
      try {
          setInStartingSmsLogReferenceNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingSmsLogReferenceNr", null, null));
      }
   }
 
   public String getInStartingSmsLogCellNr() {
      return StringAttr.valueOf(importView.InStartingSmsLogCellNr);
   }
   public void setInStartingSmsLogCellNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InStartingSmsLogCellNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InStartingSmsLogCellNr", null, null));
      }
      importView.InStartingSmsLogCellNr = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInStartingSmsLogMessage() {
      return StringAttr.valueOf(importView.InStartingSmsLogMessage);
   }
   public void setInStartingSmsLogMessage(String s)
      throws PropertyVetoException {
      if (s.length() > 160) {
         throw new PropertyVetoException("InStartingSmsLogMessage must be <= 160 characters.",
               new PropertyChangeEvent (this, "InStartingSmsLogMessage", null, null));
      }
      importView.InStartingSmsLogMessage = StringAttr.valueOf(s, (short)160);
   }
 
   public Calendar getInStartingSmsLogSentOn() {
      return TimestampAttr.toCalendar(importView.InStartingSmsLogSentOn);
   }
   public String getAsStringInStartingSmsLogSentOn() {
      return TimestampAttr.toString(importView.InStartingSmsLogSentOn);
   }
   public void setInStartingSmsLogSentOn(Calendar s)
      throws PropertyVetoException {
      importView.InStartingSmsLogSentOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInStartingSmsLogSentOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInStartingSmsLogSentOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InStartingSmsLogSentOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InStartingSmsLogSentOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InStartingSmsLogSentOn", null, null));
         }
      }
   }
 
   public String getInStartingSmsLogMessageStatus() {
      return StringAttr.valueOf(importView.InStartingSmsLogMessageStatus);
   }
   public void setInStartingSmsLogMessageStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InStartingSmsLogMessageStatus must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStartingSmsLogMessageStatus", null, null));
      }
      importView.InStartingSmsLogMessageStatus = StringAttr.valueOf(s, (short)7);
   }
 
   public int getInLowSmsLogMkBatchNr() {
      return importView.InLowSmsLogMkBatchNr;
   }
   public void setInLowSmsLogMkBatchNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InLowSmsLogMkBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InLowSmsLogMkBatchNr", null, null));
      }
      importView.InLowSmsLogMkBatchNr = IntAttr.valueOf(s);
   }
   public void setAsStringInLowSmsLogMkBatchNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowSmsLogMkBatchNr", null, null));
      }
      try {
          setInLowSmsLogMkBatchNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowSmsLogMkBatchNr", null, null));
      }
   }
 
   public int getInLowSmsLogReferenceNr() {
      return importView.InLowSmsLogReferenceNr;
   }
   public void setInLowSmsLogReferenceNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InLowSmsLogReferenceNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InLowSmsLogReferenceNr", null, null));
      }
      importView.InLowSmsLogReferenceNr = IntAttr.valueOf(s);
   }
   public void setAsStringInLowSmsLogReferenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowSmsLogReferenceNr", null, null));
      }
      try {
          setInLowSmsLogReferenceNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowSmsLogReferenceNr", null, null));
      }
   }
 
   public String getInLowSmsLogCellNr() {
      return StringAttr.valueOf(importView.InLowSmsLogCellNr);
   }
   public void setInLowSmsLogCellNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InLowSmsLogCellNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InLowSmsLogCellNr", null, null));
      }
      importView.InLowSmsLogCellNr = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInLowSmsLogMessage() {
      return StringAttr.valueOf(importView.InLowSmsLogMessage);
   }
   public void setInLowSmsLogMessage(String s)
      throws PropertyVetoException {
      if (s.length() > 160) {
         throw new PropertyVetoException("InLowSmsLogMessage must be <= 160 characters.",
               new PropertyChangeEvent (this, "InLowSmsLogMessage", null, null));
      }
      importView.InLowSmsLogMessage = StringAttr.valueOf(s, (short)160);
   }
 
   public Calendar getInLowSmsLogSentOn() {
      return TimestampAttr.toCalendar(importView.InLowSmsLogSentOn);
   }
   public String getAsStringInLowSmsLogSentOn() {
      return TimestampAttr.toString(importView.InLowSmsLogSentOn);
   }
   public void setInLowSmsLogSentOn(Calendar s)
      throws PropertyVetoException {
      importView.InLowSmsLogSentOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInLowSmsLogSentOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInLowSmsLogSentOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InLowSmsLogSentOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InLowSmsLogSentOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InLowSmsLogSentOn", null, null));
         }
      }
   }
 
   public String getInLowSmsLogMessageStatus() {
      return StringAttr.valueOf(importView.InLowSmsLogMessageStatus);
   }
   public void setInLowSmsLogMessageStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InLowSmsLogMessageStatus must be <= 7 characters.",
               new PropertyChangeEvent (this, "InLowSmsLogMessageStatus", null, null));
      }
      importView.InLowSmsLogMessageStatus = StringAttr.valueOf(s, (short)7);
   }
 
   public int getInHighSmsLogMkBatchNr() {
      return importView.InHighSmsLogMkBatchNr;
   }
   public void setInHighSmsLogMkBatchNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InHighSmsLogMkBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InHighSmsLogMkBatchNr", null, null));
      }
      importView.InHighSmsLogMkBatchNr = IntAttr.valueOf(s);
   }
   public void setAsStringInHighSmsLogMkBatchNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighSmsLogMkBatchNr", null, null));
      }
      try {
          setInHighSmsLogMkBatchNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighSmsLogMkBatchNr", null, null));
      }
   }
 
   public int getInHighSmsLogReferenceNr() {
      return importView.InHighSmsLogReferenceNr;
   }
   public void setInHighSmsLogReferenceNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InHighSmsLogReferenceNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InHighSmsLogReferenceNr", null, null));
      }
      importView.InHighSmsLogReferenceNr = IntAttr.valueOf(s);
   }
   public void setAsStringInHighSmsLogReferenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighSmsLogReferenceNr", null, null));
      }
      try {
          setInHighSmsLogReferenceNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighSmsLogReferenceNr", null, null));
      }
   }
 
   public String getInHighSmsLogCellNr() {
      return StringAttr.valueOf(importView.InHighSmsLogCellNr);
   }
   public void setInHighSmsLogCellNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InHighSmsLogCellNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InHighSmsLogCellNr", null, null));
      }
      importView.InHighSmsLogCellNr = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInHighSmsLogMessage() {
      return StringAttr.valueOf(importView.InHighSmsLogMessage);
   }
   public void setInHighSmsLogMessage(String s)
      throws PropertyVetoException {
      if (s.length() > 160) {
         throw new PropertyVetoException("InHighSmsLogMessage must be <= 160 characters.",
               new PropertyChangeEvent (this, "InHighSmsLogMessage", null, null));
      }
      importView.InHighSmsLogMessage = StringAttr.valueOf(s, (short)160);
   }
 
   public Calendar getInHighSmsLogSentOn() {
      return TimestampAttr.toCalendar(importView.InHighSmsLogSentOn);
   }
   public String getAsStringInHighSmsLogSentOn() {
      return TimestampAttr.toString(importView.InHighSmsLogSentOn);
   }
   public void setInHighSmsLogSentOn(Calendar s)
      throws PropertyVetoException {
      importView.InHighSmsLogSentOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInHighSmsLogSentOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInHighSmsLogSentOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InHighSmsLogSentOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InHighSmsLogSentOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InHighSmsLogSentOn", null, null));
         }
      }
   }
 
   public String getInHighSmsLogMessageStatus() {
      return StringAttr.valueOf(importView.InHighSmsLogMessageStatus);
   }
   public void setInHighSmsLogMessageStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InHighSmsLogMessageStatus must be <= 7 characters.",
               new PropertyChangeEvent (this, "InHighSmsLogMessageStatus", null, null));
      }
      importView.InHighSmsLogMessageStatus = StringAttr.valueOf(s, (short)7);
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
 
   public int getInSmsLogMkBatchNr() {
      return importView.InSmsLogMkBatchNr;
   }
   public void setInSmsLogMkBatchNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000000.0) {
         throw new PropertyVetoException("InSmsLogMkBatchNr has more than 7 digits.",
               new PropertyChangeEvent (this, "InSmsLogMkBatchNr", null, null));
      }
      importView.InSmsLogMkBatchNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsLogMkBatchNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogMkBatchNr", null, null));
      }
      try {
          setInSmsLogMkBatchNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsLogMkBatchNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogMkBatchNr", null, null));
      }
   }
 
   public int getInSmsLogSequenceNr() {
      return importView.InSmsLogSequenceNr;
   }
   public void setInSmsLogSequenceNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000.0) {
         throw new PropertyVetoException("InSmsLogSequenceNr has more than 6 digits.",
               new PropertyChangeEvent (this, "InSmsLogSequenceNr", null, null));
      }
      importView.InSmsLogSequenceNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsLogSequenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsLogSequenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogSequenceNr", null, null));
      }
      try {
          setInSmsLogSequenceNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsLogSequenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogSequenceNr", null, null));
      }
   }
 
   public int getInSmsLogReferenceNr() {
      return importView.InSmsLogReferenceNr;
   }
   public void setInSmsLogReferenceNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InSmsLogReferenceNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InSmsLogReferenceNr", null, null));
      }
      importView.InSmsLogReferenceNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsLogReferenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogReferenceNr", null, null));
      }
      try {
          setInSmsLogReferenceNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsLogReferenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogReferenceNr", null, null));
      }
   }
 
   public String getInSmsLogCellNr() {
      return StringAttr.valueOf(importView.InSmsLogCellNr);
   }
   public void setInSmsLogCellNr(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSmsLogCellNr must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSmsLogCellNr", null, null));
      }
      importView.InSmsLogCellNr = StringAttr.valueOf(s, (short)20);
   }
 
   public String getInSmsLogMessage() {
      return StringAttr.valueOf(importView.InSmsLogMessage);
   }
   public void setInSmsLogMessage(String s)
      throws PropertyVetoException {
      if (s.length() > 160) {
         throw new PropertyVetoException("InSmsLogMessage must be <= 160 characters.",
               new PropertyChangeEvent (this, "InSmsLogMessage", null, null));
      }
      importView.InSmsLogMessage = StringAttr.valueOf(s, (short)160);
   }
 
   public Calendar getInSmsLogSentOn() {
      return TimestampAttr.toCalendar(importView.InSmsLogSentOn);
   }
   public String getAsStringInSmsLogSentOn() {
      return TimestampAttr.toString(importView.InSmsLogSentOn);
   }
   public void setInSmsLogSentOn(Calendar s)
      throws PropertyVetoException {
      importView.InSmsLogSentOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInSmsLogSentOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSmsLogSentOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InSmsLogSentOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSmsLogSentOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InSmsLogSentOn", null, null));
         }
      }
   }
 
   public Calendar getInSmsLogDeliveredOn() {
      return TimestampAttr.toCalendar(importView.InSmsLogDeliveredOn);
   }
   public String getAsStringInSmsLogDeliveredOn() {
      return TimestampAttr.toString(importView.InSmsLogDeliveredOn);
   }
   public void setInSmsLogDeliveredOn(Calendar s)
      throws PropertyVetoException {
      importView.InSmsLogDeliveredOn = TimestampAttr.valueOf(s);
   }
   public void setAsStringInSmsLogDeliveredOn(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSmsLogDeliveredOn((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InSmsLogDeliveredOn = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSmsLogDeliveredOn has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InSmsLogDeliveredOn", null, null));
         }
      }
   }
 
   public int getInSmsLogWaspSequenceNr() {
      return importView.InSmsLogWaspSequenceNr;
   }
   public void setInSmsLogWaspSequenceNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InSmsLogWaspSequenceNr has more than 9 digits.",
               new PropertyChangeEvent (this, "InSmsLogWaspSequenceNr", null, null));
      }
      importView.InSmsLogWaspSequenceNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsLogWaspSequenceNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsLogWaspSequenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogWaspSequenceNr", null, null));
      }
      try {
          setInSmsLogWaspSequenceNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsLogWaspSequenceNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogWaspSequenceNr", null, null));
      }
   }
 
   public String getInSmsLogMessageStatus() {
      return StringAttr.valueOf(importView.InSmsLogMessageStatus);
   }
   public void setInSmsLogMessageStatus(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InSmsLogMessageStatus must be <= 7 characters.",
               new PropertyChangeEvent (this, "InSmsLogMessageStatus", null, null));
      }
      importView.InSmsLogMessageStatus = StringAttr.valueOf(s, (short)7);
   }
 
   public int getInSmsLogMkPersNr() {
      return importView.InSmsLogMkPersNr;
   }
   public void setInSmsLogMkPersNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InSmsLogMkPersNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InSmsLogMkPersNr", null, null));
      }
      importView.InSmsLogMkPersNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSmsLogMkPersNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSmsLogMkPersNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogMkPersNr", null, null));
      }
      try {
          setInSmsLogMkPersNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSmsLogMkPersNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSmsLogMkPersNr", null, null));
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
 
   public String getOutRcDescrCsfStringsString60() {
      return FixedStringAttr.valueOf(exportView.OutRcDescrCsfStringsString60, 60);
   }
 
   public String getOutWsGlEntityCode() {
      return FixedStringAttr.valueOf(exportView.OutWsGlEntityCode, 5);
   }
 
   public String getOutWsGlEntityDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsGlEntityDescription, 60);
   }
 
   public String getOutWsFinanceCheckDigitParmsCodeString() {
      return FixedStringAttr.valueOf(exportView.OutWsFinanceCheckDigitParmsCodeString, 5);
   }
 
   public String getOutWsFinanceCheckDigitParmsAction() {
      return FixedStringAttr.valueOf(exportView.OutWsFinanceCheckDigitParmsAction, 1);
   }
 
   public String getOutWsFinanceCheckDigitParmsResultCode() {
      return FixedStringAttr.valueOf(exportView.OutWsFinanceCheckDigitParmsResultCode, 2);
   }
 
   public int getOutWsStaffV2Persno() {
      return exportView.OutWsStaffV2Persno;
   }
 
   public String getOutWsStaffV2Surname() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffV2Surname, 28);
   }
 
   public String getOutWsStaffV2Initials() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffV2Initials, 10);
   }
 
   public String getOutWsStaffV2Title() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffV2Title, 10);
   }
 
   public String getOutWsStaffV2MkRcCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffV2MkRcCode, 6);
   }
 
   public int getOutSecurityWsUserNumber() {
      return exportView.OutSecurityWsUserNumber;
   }
 
   public String getOutSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPersonnelNo, 10);
   }
 
   public String getOutStaffNameCsfStringsString30() {
      return FixedStringAttr.valueOf(exportView.OutStaffNameCsfStringsString30, 30);
   }
 
   public int getOutWsStaffPersno() {
      return exportView.OutWsStaffPersno;
   }
 
   public String getOutWsStaffMkRcCode() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffMkRcCode, 6);
   }
 
   public String getOutWsStaffSurname() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffSurname, 28);
   }
 
   public String getOutWsStaffInitials() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffInitials, 10);
   }
 
   public String getOutWsStaffTitle() {
      return FixedStringAttr.valueOf(exportView.OutWsStaffTitle, 10);
   }
 
   public final int OutSmsreqGroupMax = 50;
   public short getOutSmsreqGroupCount() {
      return (short)(exportView.OutSmsreqGroup_MA);
   };
 
   public int getOutGSmsRequestBatchNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGSmsRequestBatchNr[index];
   }
 
   public Calendar getOutGSmsRequestRequestTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGSmsRequestRequestTimestamp[index]);
   }
   public String getAsStringOutGSmsRequestRequestTimestamp(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGSmsRequestRequestTimestamp[index]);
   }
 
   public String getOutGSmsRequestSmsMsg(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSmsRequestSmsMsg[index], 160);
   }
 
   public int getOutGSmsRequestMessageCnt(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGSmsRequestMessageCnt[index];
   }
 
   public double getOutGSmsRequestTotalCost(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGSmsRequestTotalCost[index];
   }
 
   public String getOutGReqCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGReqCsfLineActionBarAction[index], 1);
   }
 
   public String getOutGReqCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGReqCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public final int OutSmslogGroupMax = 50;
   public short getOutSmslogGroupCount() {
      return (short)(exportView.OutSmslogGroup_MA);
   };
 
   public int getOutGSmsLogMkBatchNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGSmsLogMkBatchNr[index];
   }
 
   public int getOutGSmsLogReferenceNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return exportView.OutGSmsLogReferenceNr[index];
   }
 
   public String getOutGSmsLogCellNr(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGSmsLogCellNr[index]);
   }
 
   public String getOutGSmsLogMessage(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGSmsLogMessage[index]);
   }
 
   public Calendar getOutGSmsLogSentOn(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGSmsLogSentOn[index]);
   }
   public String getAsStringOutGSmsLogSentOn(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGSmsLogSentOn[index]);
   }
 
   public String getOutGSmsLogMessageStatus(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGSmsLogMessageStatus[index]);
   }
 
   public String getOutGSmsCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSmsCsfLineActionBarAction[index], 1);
   }
 
   public String getOutGSmsCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (49 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 49, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGSmsCsfLineActionBarSelectionFlag[index], 1);
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
 
   public int getOutSmsLogMkBatchNr() {
      return exportView.OutSmsLogMkBatchNr;
   }
 
   public int getOutSmsLogSequenceNr() {
      return exportView.OutSmsLogSequenceNr;
   }
 
   public int getOutSmsLogReferenceNr() {
      return exportView.OutSmsLogReferenceNr;
   }
 
   public String getOutSmsLogCellNr() {
      return StringAttr.valueOf(exportView.OutSmsLogCellNr);
   }
 
   public String getOutSmsLogMessage() {
      return StringAttr.valueOf(exportView.OutSmsLogMessage);
   }
 
   public Calendar getOutSmsLogSentOn() {
      return TimestampAttr.toCalendar(exportView.OutSmsLogSentOn);
   }
   public String getAsStringOutSmsLogSentOn() {
      return TimestampAttr.toString(exportView.OutSmsLogSentOn);
   }
 
   public Calendar getOutSmsLogDeliveredOn() {
      return TimestampAttr.toCalendar(exportView.OutSmsLogDeliveredOn);
   }
   public String getAsStringOutSmsLogDeliveredOn() {
      return TimestampAttr.toString(exportView.OutSmsLogDeliveredOn);
   }
 
   public int getOutSmsLogWaspSequenceNr() {
      return exportView.OutSmsLogWaspSequenceNr;
   }
 
   public String getOutSmsLogMessageStatus() {
      return StringAttr.valueOf(exportView.OutSmsLogMessageStatus);
   }
 
   public int getOutSmsLogMkPersNr() {
      return exportView.OutSmsLogMkPersNr;
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
 
};
