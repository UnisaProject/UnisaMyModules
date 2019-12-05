package Srslf05h.Abean;
 
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
public class Srslf05sDisplaySmsStaffInfo  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srslf05sDisplaySmsStaffInfo");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srslf05sDisplaySmsStaffInfo() {
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
 
 
   private Srslf05sDisplaySmsStaffInfoOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srslf05sDisplaySmsStaffInfoOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrslf05sDisplaySmsStaffInfoOperation();
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
      private Srslf05sDisplaySmsStaffInfo rP;
      operListener(Srslf05sDisplaySmsStaffInfo r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srslf05sDisplaySmsStaffInfo", "Listener heard that Srslf05sDisplaySmsStaffInfoOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srslf05sDisplaySmsStaffInfo ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srslf05sDisplaySmsStaffInfo");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srslf05sDisplaySmsStaffInfo ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srslf05sDisplaySmsStaffInfo";
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
   }

   Srslf05h.SRSLF051_IA importView = Srslf05h.SRSLF051_IA.getInstance();
   Srslf05h.SRSLF051_OA exportView = Srslf05h.SRSLF051_OA.getInstance();
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
 
   public String getInUserNameCsfStringsString30() {
      return FixedStringAttr.valueOf(importView.InUserNameCsfStringsString30, 30);
   }
   public void setInUserNameCsfStringsString30(String s)
      throws PropertyVetoException {
      if (s.length() > 30) {
         throw new PropertyVetoException("InUserNameCsfStringsString30 must be <= 30 characters.",
               new PropertyChangeEvent (this, "InUserNameCsfStringsString30", null, null));
      }
      importView.InUserNameCsfStringsString30 = FixedStringAttr.valueOf(s, (short)30);
   }
 
   public int getInUserWsStaffPersno() {
      return importView.InUserWsStaffPersno;
   }
   public void setInUserWsStaffPersno(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InUserWsStaffPersno has more than 8 digits.",
               new PropertyChangeEvent (this, "InUserWsStaffPersno", null, null));
      }
      importView.InUserWsStaffPersno = IntAttr.valueOf(s);
   }
   public void setAsStringInUserWsStaffPersno(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InUserWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUserWsStaffPersno", null, null));
      }
      try {
          setInUserWsStaffPersno(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InUserWsStaffPersno is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InUserWsStaffPersno", null, null));
      }
   }
 
   public String getInUserWsStaffSurname() {
      return FixedStringAttr.valueOf(importView.InUserWsStaffSurname, 28);
   }
   public void setInUserWsStaffSurname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InUserWsStaffSurname must be <= 28 characters.",
               new PropertyChangeEvent (this, "InUserWsStaffSurname", null, null));
      }
      importView.InUserWsStaffSurname = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInUserWsStaffInitials() {
      return FixedStringAttr.valueOf(importView.InUserWsStaffInitials, 10);
   }
   public void setInUserWsStaffInitials(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InUserWsStaffInitials must be <= 10 characters.",
               new PropertyChangeEvent (this, "InUserWsStaffInitials", null, null));
      }
      importView.InUserWsStaffInitials = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInUserWsStaffTitle() {
      return FixedStringAttr.valueOf(importView.InUserWsStaffTitle, 10);
   }
   public void setInUserWsStaffTitle(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InUserWsStaffTitle must be <= 10 characters.",
               new PropertyChangeEvent (this, "InUserWsStaffTitle", null, null));
      }
      importView.InUserWsStaffTitle = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInUserWsStaffEmailAddress() {
      return FixedStringAttr.valueOf(importView.InUserWsStaffEmailAddress, 60);
   }
   public void setInUserWsStaffEmailAddress(String s)
      throws PropertyVetoException {
      if (s.length() > 60) {
         throw new PropertyVetoException("InUserWsStaffEmailAddress must be <= 60 characters.",
               new PropertyChangeEvent (this, "InUserWsStaffEmailAddress", null, null));
      }
      importView.InUserWsStaffEmailAddress = FixedStringAttr.valueOf(s, (short)60);
   }
 
   public String getInUserWsStaffContactTelno() {
      return FixedStringAttr.valueOf(importView.InUserWsStaffContactTelno, 28);
   }
   public void setInUserWsStaffContactTelno(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InUserWsStaffContactTelno must be <= 28 characters.",
               new PropertyChangeEvent (this, "InUserWsStaffContactTelno", null, null));
      }
      importView.InUserWsStaffContactTelno = FixedStringAttr.valueOf(s, (short)28);
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
 
   public int getOutSmsMtnStatusCode() {
      return exportView.OutSmsMtnStatusCode;
   }
 
   public String getOutSmsMtnStatusStatus() {
      return StringAttr.valueOf(exportView.OutSmsMtnStatusStatus);
   }
 
   public String getOutSmsMtnStatusDescription() {
      return StringAttr.valueOf(exportView.OutSmsMtnStatusDescription);
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
 
   public String getOutUserNameCsfStringsString30() {
      return FixedStringAttr.valueOf(exportView.OutUserNameCsfStringsString30, 30);
   }
 
   public int getOutUserWsStaffPersno() {
      return exportView.OutUserWsStaffPersno;
   }
 
   public String getOutUserWsStaffSurname() {
      return FixedStringAttr.valueOf(exportView.OutUserWsStaffSurname, 28);
   }
 
   public String getOutUserWsStaffInitials() {
      return FixedStringAttr.valueOf(exportView.OutUserWsStaffInitials, 10);
   }
 
   public String getOutUserWsStaffTitle() {
      return FixedStringAttr.valueOf(exportView.OutUserWsStaffTitle, 10);
   }
 
   public short getOutUserWsStaffMkDeptCode() {
      return exportView.OutUserWsStaffMkDeptCode;
   }
 
   public String getOutUserWsStaffEmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutUserWsStaffEmailAddress, 60);
   }
 
   public String getOutUserWsStaffContactTelno() {
      return FixedStringAttr.valueOf(exportView.OutUserWsStaffContactTelno, 28);
   }
 
   public int getOutSmsLogMkBatchNr() {
      return exportView.OutSmsLogMkBatchNr;
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
 
   public String getOutSmsLogMessageStatus() {
      return StringAttr.valueOf(exportView.OutSmsLogMessageStatus);
   }
 
   public int getOutSmsLogMkPersNr() {
      return exportView.OutSmsLogMkPersNr;
   }
 
   public int getOutSmsLogSequenceNr() {
      return exportView.OutSmsLogSequenceNr;
   }
 
   public int getOutSmsLogMkMtnStatusCode() {
      return exportView.OutSmsLogMkMtnStatusCode;
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
 
   public short getOutCsfClientServerCommunicationsServerVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsServerRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerRevisionNumber;
   }
 
};
