package Srads01h.Abean;
 
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
public class Srads01sMntAddress  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srads01sMntAddress");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srads01sMntAddress() {
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
 
 
   private Srads01sMntAddressOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srads01sMntAddressOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrads01sMntAddressOperation();
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
      private Srads01sMntAddress rP;
      operListener(Srads01sMntAddress r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srads01sMntAddress", "Listener heard that Srads01sMntAddressOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srads01sMntAddress ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srads01sMntAddress");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srads01sMntAddress ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srads01sMntAddress";
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

   Srads01h.SRADS01S_IA importView = Srads01h.SRADS01S_IA.getInstance();
   Srads01h.SRADS01S_OA exportView = Srads01h.SRADS01S_OA.getInstance();
   public String getInPhysicalOkIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPhysicalOkIefSuppliedFlag, 1);
   }
   public void setInPhysicalOkIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srads01sMntAddressIefSuppliedFlagPropertyEditor pe = new Srads01sMntAddressIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPhysicalOkIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPhysicalOkIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPhysicalOkIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPhysicalOkIefSuppliedFlag", null, null));
      }
      importView.InPhysicalOkIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInPhysicalAdrChgIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InPhysicalAdrChgIefSuppliedFlag, 1);
   }
   public void setInPhysicalAdrChgIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srads01sMntAddressIefSuppliedFlagPropertyEditor pe = new Srads01sMntAddressIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InPhysicalAdrChgIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InPhysicalAdrChgIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPhysicalAdrChgIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPhysicalAdrChgIefSuppliedFlag", null, null));
      }
      importView.InPhysicalAdrChgIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInAddressChgIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InAddressChgIefSuppliedFlag, 1);
   }
   public void setInAddressChgIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srads01sMntAddressIefSuppliedFlagPropertyEditor pe = new Srads01sMntAddressIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InAddressChgIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InAddressChgIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InAddressChgIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InAddressChgIefSuppliedFlag", null, null));
      }
      importView.InAddressChgIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public int getInSupervisorWsUserNumber() {
      return importView.InSupervisorWsUserNumber;
   }
   public void setInSupervisorWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSupervisorWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSupervisorWsUserNumber", null, null));
      }
      importView.InSupervisorWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSupervisorWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSupervisorWsUserNumber", null, null));
      }
      try {
          setInSupervisorWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSupervisorWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSupervisorWsUserNumber", null, null));
      }
   }
 
   public String getInSupervisorWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InSupervisorWsUserPassword, 12);
   }
   public void setInSupervisorWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InSupervisorWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InSupervisorWsUserPassword", null, null));
      }
      importView.InSupervisorWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
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
 
   public String getInWsAddressV2AddressReferenceFlag() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2AddressReferenceFlag, 1);
   }
   public void setInWsAddressV2AddressReferenceFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsAddressV2AddressReferenceFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2AddressReferenceFlag", null, null));
      }
      importView.InWsAddressV2AddressReferenceFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInWsAddressV2CategoryDescription() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2CategoryDescription, 28);
   }
   public void setInWsAddressV2CategoryDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2CategoryDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2CategoryDescription", null, null));
      }
      importView.InWsAddressV2CategoryDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public String getInWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2WorkNumber, 28);
   }
   public void setInWsAddressV2WorkNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2WorkNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2WorkNumber", null, null));
      }
      importView.InWsAddressV2WorkNumber = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsAddressV2FaxNumber() {
      return FixedStringAttr.valueOf(importView.InWsAddressV2FaxNumber, 28);
   }
   public void setInWsAddressV2FaxNumber(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsAddressV2FaxNumber must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsAddressV2FaxNumber", null, null));
      }
      importView.InWsAddressV2FaxNumber = FixedStringAttr.valueOf(s, (short)28);
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
 
   public short getInWsAddressV2ErrorCode() {
      return importView.InWsAddressV2ErrorCode;
   }
   public void setInWsAddressV2ErrorCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InWsAddressV2ErrorCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2ErrorCode", null, null));
      }
      importView.InWsAddressV2ErrorCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2ErrorCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2ErrorCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ErrorCode", null, null));
      }
      try {
          setInWsAddressV2ErrorCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2ErrorCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2ErrorCode", null, null));
      }
   }
 
   public int getInWsAddressV2UserNumber() {
      return importView.InWsAddressV2UserNumber;
   }
   public void setInWsAddressV2UserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InWsAddressV2UserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InWsAddressV2UserNumber", null, null));
      }
      importView.InWsAddressV2UserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInWsAddressV2UserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2UserNumber", null, null));
      }
      try {
          setInWsAddressV2UserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsAddressV2UserNumber", null, null));
      }
   }
 
   public short getInWsMagisterialDistrictCode() {
      return importView.InWsMagisterialDistrictCode;
   }
   public void setInWsMagisterialDistrictCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InWsMagisterialDistrictCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InWsMagisterialDistrictCode", null, null));
      }
      importView.InWsMagisterialDistrictCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsMagisterialDistrictCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsMagisterialDistrictCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMagisterialDistrictCode", null, null));
      }
      try {
          setInWsMagisterialDistrictCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsMagisterialDistrictCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsMagisterialDistrictCode", null, null));
      }
   }
 
   public String getInWsMagisterialDistrictEngDescription() {
      return FixedStringAttr.valueOf(importView.InWsMagisterialDistrictEngDescription, 28);
   }
   public void setInWsMagisterialDistrictEngDescription(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InWsMagisterialDistrictEngDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InWsMagisterialDistrictEngDescription", null, null));
      }
      importView.InWsMagisterialDistrictEngDescription = FixedStringAttr.valueOf(s, (short)28);
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
 
   public String getInPhysicalCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InPhysicalCsfStringsString1, 1);
   }
   public void setInPhysicalCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         throw new PropertyVetoException("InPhysicalCsfStringsString1 is required and may not be null or zero length.",
               new PropertyChangeEvent (this, "InPhysicalCsfStringsString1", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InPhysicalCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InPhysicalCsfStringsString1", null, null));
      }
      importView.InPhysicalCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInPhysicalWsAddressV2ReferenceNo() {
      return importView.InPhysicalWsAddressV2ReferenceNo;
   }
   public void setInPhysicalWsAddressV2ReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressV2ReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2ReferenceNo", null, null));
      }
      importView.InPhysicalWsAddressV2ReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressV2ReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2ReferenceNo", null, null));
      }
      try {
          setInPhysicalWsAddressV2ReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2ReferenceNo", null, null));
      }
   }
 
   public short getInPhysicalWsAddressV2Type() {
      return importView.InPhysicalWsAddressV2Type;
   }
   public void setInPhysicalWsAddressV2Type(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InPhysicalWsAddressV2Type has more than 2 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2Type", null, null));
      }
      importView.InPhysicalWsAddressV2Type = ShortAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressV2Type(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2Type", null, null));
      }
      try {
          setInPhysicalWsAddressV2Type(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2Type", null, null));
      }
   }
 
   public short getInPhysicalWsAddressV2Category() {
      return importView.InPhysicalWsAddressV2Category;
   }
   public void setInPhysicalWsAddressV2Category(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressV2Category has more than 3 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2Category", null, null));
      }
      importView.InPhysicalWsAddressV2Category = ShortAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressV2Category(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2Category", null, null));
      }
      try {
          setInPhysicalWsAddressV2Category(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2Category", null, null));
      }
   }
 
   public String getInPhysicalWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2AddressLine1, 28);
   }
   public void setInPhysicalWsAddressV2AddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressV2AddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2AddressLine1", null, null));
      }
      importView.InPhysicalWsAddressV2AddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2AddressLine2, 28);
   }
   public void setInPhysicalWsAddressV2AddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressV2AddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2AddressLine2", null, null));
      }
      importView.InPhysicalWsAddressV2AddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2AddressLine3, 28);
   }
   public void setInPhysicalWsAddressV2AddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressV2AddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2AddressLine3", null, null));
      }
      importView.InPhysicalWsAddressV2AddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2AddressLine4, 28);
   }
   public void setInPhysicalWsAddressV2AddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressV2AddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2AddressLine4", null, null));
      }
      importView.InPhysicalWsAddressV2AddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2AddressLine5, 28);
   }
   public void setInPhysicalWsAddressV2AddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressV2AddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2AddressLine5", null, null));
      }
      importView.InPhysicalWsAddressV2AddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInPhysicalWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2AddressLine6, 28);
   }
   public void setInPhysicalWsAddressV2AddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InPhysicalWsAddressV2AddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2AddressLine6", null, null));
      }
      importView.InPhysicalWsAddressV2AddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInPhysicalWsAddressV2PostalCode() {
      return importView.InPhysicalWsAddressV2PostalCode;
   }
   public void setInPhysicalWsAddressV2PostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressV2PostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2PostalCode", null, null));
      }
      importView.InPhysicalWsAddressV2PostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressV2PostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2PostalCode", null, null));
      }
      try {
          setInPhysicalWsAddressV2PostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2PostalCode", null, null));
      }
   }
 
   public int getInPhysicalWsAddressV2UserNumber() {
      return importView.InPhysicalWsAddressV2UserNumber;
   }
   public void setInPhysicalWsAddressV2UserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InPhysicalWsAddressV2UserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2UserNumber", null, null));
      }
      importView.InPhysicalWsAddressV2UserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInPhysicalWsAddressV2UserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InPhysicalWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2UserNumber", null, null));
      }
      try {
          setInPhysicalWsAddressV2UserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InPhysicalWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InPhysicalWsAddressV2UserNumber", null, null));
      }
   }
 
   public String getInPhysicalWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(importView.InPhysicalWsAddressV2CourierContactNo, 40);
   }
   public void setInPhysicalWsAddressV2CourierContactNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InPhysicalWsAddressV2CourierContactNo must be <= 40 characters.",
               new PropertyChangeEvent (this, "InPhysicalWsAddressV2CourierContactNo", null, null));
      }
      importView.InPhysicalWsAddressV2CourierContactNo = FixedStringAttr.valueOf(s, (short)40);
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
 
   public int getInCourierWsAddressV2ReferenceNo() {
      return importView.InCourierWsAddressV2ReferenceNo;
   }
   public void setInCourierWsAddressV2ReferenceNo(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InCourierWsAddressV2ReferenceNo has more than 8 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2ReferenceNo", null, null));
      }
      importView.InCourierWsAddressV2ReferenceNo = IntAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressV2ReferenceNo(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2ReferenceNo", null, null));
      }
      try {
          setInCourierWsAddressV2ReferenceNo(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressV2ReferenceNo is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2ReferenceNo", null, null));
      }
   }
 
   public short getInCourierWsAddressV2Type() {
      return importView.InCourierWsAddressV2Type;
   }
   public void setInCourierWsAddressV2Type(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InCourierWsAddressV2Type has more than 2 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2Type", null, null));
      }
      importView.InCourierWsAddressV2Type = ShortAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressV2Type(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2Type", null, null));
      }
      try {
          setInCourierWsAddressV2Type(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressV2Type is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2Type", null, null));
      }
   }
 
   public short getInCourierWsAddressV2Category() {
      return importView.InCourierWsAddressV2Category;
   }
   public void setInCourierWsAddressV2Category(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InCourierWsAddressV2Category has more than 3 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2Category", null, null));
      }
      importView.InCourierWsAddressV2Category = ShortAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressV2Category(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2Category", null, null));
      }
      try {
          setInCourierWsAddressV2Category(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressV2Category is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2Category", null, null));
      }
   }
 
   public String getInCourierWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2AddressLine1, 28);
   }
   public void setInCourierWsAddressV2AddressLine1(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressV2AddressLine1 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2AddressLine1", null, null));
      }
      importView.InCourierWsAddressV2AddressLine1 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2AddressLine2, 28);
   }
   public void setInCourierWsAddressV2AddressLine2(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressV2AddressLine2 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2AddressLine2", null, null));
      }
      importView.InCourierWsAddressV2AddressLine2 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2AddressLine3, 28);
   }
   public void setInCourierWsAddressV2AddressLine3(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressV2AddressLine3 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2AddressLine3", null, null));
      }
      importView.InCourierWsAddressV2AddressLine3 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2AddressLine4, 28);
   }
   public void setInCourierWsAddressV2AddressLine4(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressV2AddressLine4 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2AddressLine4", null, null));
      }
      importView.InCourierWsAddressV2AddressLine4 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2AddressLine5, 28);
   }
   public void setInCourierWsAddressV2AddressLine5(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressV2AddressLine5 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2AddressLine5", null, null));
      }
      importView.InCourierWsAddressV2AddressLine5 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInCourierWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2AddressLine6, 28);
   }
   public void setInCourierWsAddressV2AddressLine6(String s)
      throws PropertyVetoException {
      if (s.length() > 28) {
         throw new PropertyVetoException("InCourierWsAddressV2AddressLine6 must be <= 28 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2AddressLine6", null, null));
      }
      importView.InCourierWsAddressV2AddressLine6 = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInCourierWsAddressV2PostalCode() {
      return importView.InCourierWsAddressV2PostalCode;
   }
   public void setInCourierWsAddressV2PostalCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCourierWsAddressV2PostalCode has more than 4 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2PostalCode", null, null));
      }
      importView.InCourierWsAddressV2PostalCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressV2PostalCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2PostalCode", null, null));
      }
      try {
          setInCourierWsAddressV2PostalCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressV2PostalCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2PostalCode", null, null));
      }
   }
 
   public int getInCourierWsAddressV2UserNumber() {
      return importView.InCourierWsAddressV2UserNumber;
   }
   public void setInCourierWsAddressV2UserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InCourierWsAddressV2UserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2UserNumber", null, null));
      }
      importView.InCourierWsAddressV2UserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInCourierWsAddressV2UserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCourierWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2UserNumber", null, null));
      }
      try {
          setInCourierWsAddressV2UserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCourierWsAddressV2UserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCourierWsAddressV2UserNumber", null, null));
      }
   }
 
   public String getInCourierWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(importView.InCourierWsAddressV2CourierContactNo, 40);
   }
   public void setInCourierWsAddressV2CourierContactNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 40) {
         throw new PropertyVetoException("InCourierWsAddressV2CourierContactNo must be <= 40 characters.",
               new PropertyChangeEvent (this, "InCourierWsAddressV2CourierContactNo", null, null));
      }
      importView.InCourierWsAddressV2CourierContactNo = FixedStringAttr.valueOf(s, (short)40);
   }
 
   public String getOutPhysicalOkIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalOkIefSuppliedFlag, 1);
   }
 
   public String getOutPhysicalAdrChgIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalAdrChgIefSuppliedFlag, 1);
   }
 
   public String getOutAddressChgIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutAddressChgIefSuppliedFlag, 1);
   }
 
   public String getOutNameCsfStringsString50() {
      return FixedStringAttr.valueOf(exportView.OutNameCsfStringsString50, 50);
   }
 
   public String getOutPOBoxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPOBoxCsfStringsString1, 1);
   }
 
   public int getOutSupervisorWsUserNumber() {
      return exportView.OutSupervisorWsUserNumber;
   }
 
   public String getOutSupervisorWsUserPassword() {
      return FixedStringAttr.valueOf(exportView.OutSupervisorWsUserPassword, 12);
   }
 
   public int getOutWsAddressV2ReferenceNo() {
      return exportView.OutWsAddressV2ReferenceNo;
   }
 
   public String getOutWsAddressV2AddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2AddressReferenceFlag, 1);
   }
 
   public short getOutWsAddressV2Type() {
      return exportView.OutWsAddressV2Type;
   }
 
   public short getOutWsAddressV2Category() {
      return exportView.OutWsAddressV2Category;
   }
 
   public String getOutWsAddressV2CategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CategoryDescription, 28);
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
 
   public String getOutWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2HomeNumber, 28);
   }
 
   public String getOutWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2WorkNumber, 28);
   }
 
   public String getOutWsAddressV2FaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2FaxNumber, 28);
   }
 
   public String getOutWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2CellNumber, 20);
   }
 
   public String getOutWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutWsAddressV2EmailAddress, 60);
   }
 
   public short getOutWsAddressV2ErrorCode() {
      return exportView.OutWsAddressV2ErrorCode;
   }
 
   public int getOutWsAddressV2UserNumber() {
      return exportView.OutWsAddressV2UserNumber;
   }
 
   public int getOutLclWsAddressV2ReferenceNo() {
      return exportView.OutLclWsAddressV2ReferenceNo;
   }
 
   public String getOutLclWsAddressV2AddressReferenceFlag() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressReferenceFlag, 1);
   }
 
   public short getOutLclWsAddressV2Type() {
      return exportView.OutLclWsAddressV2Type;
   }
 
   public short getOutLclWsAddressV2Category() {
      return exportView.OutLclWsAddressV2Category;
   }
 
   public String getOutLclWsAddressV2CategoryDescription() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2CategoryDescription, 28);
   }
 
   public String getOutLclWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressLine1, 28);
   }
 
   public String getOutLclWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressLine2, 28);
   }
 
   public String getOutLclWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressLine3, 28);
   }
 
   public String getOutLclWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressLine4, 28);
   }
 
   public String getOutLclWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressLine5, 28);
   }
 
   public String getOutLclWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2AddressLine6, 28);
   }
 
   public short getOutLclWsAddressV2PostalCode() {
      return exportView.OutLclWsAddressV2PostalCode;
   }
 
   public String getOutLclWsAddressV2HomeNumber() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2HomeNumber, 28);
   }
 
   public String getOutLclWsAddressV2WorkNumber() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2WorkNumber, 28);
   }
 
   public String getOutLclWsAddressV2FaxNumber() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2FaxNumber, 28);
   }
 
   public String getOutLclWsAddressV2CellNumber() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2CellNumber, 20);
   }
 
   public String getOutLclWsAddressV2EmailAddress() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2EmailAddress, 60);
   }
 
   public String getOutLclWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(exportView.OutLclWsAddressV2CourierContactNo, 40);
   }
 
   public short getOutLclWsMagisterialDistrictCode() {
      return exportView.OutLclWsMagisterialDistrictCode;
   }
 
   public String getOutLclWsMagisterialDistrictEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutLclWsMagisterialDistrictEngDescription, 28);
   }
 
   public short getOutLclWsPostalCodeCode() {
      return exportView.OutLclWsPostalCodeCode;
   }
 
   public String getOutLclWsPostalCodeDescription() {
      return FixedStringAttr.valueOf(exportView.OutLclWsPostalCodeDescription, 28);
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
 
   public String getOutCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
 
   public String getOutLclWizfuncReportingControlPrinterCode() {
      return StringAttr.valueOf(exportView.OutLclWizfuncReportingControlPrinterCode);
   }
 
   public int getOutLclWsAddressChangeNoticeStudentNr() {
      return exportView.OutLclWsAddressChangeNoticeStudentNr;
   }
 
   public int getOutLclWsAddressChangeNoticeMkUserCode() {
      return exportView.OutLclWsAddressChangeNoticeMkUserCode;
   }
 
   public int getOutLclAdditionalExamArrangementMkStudentNr() {
      return exportView.OutLclAdditionalExamArrangementMkStudentNr;
   }
 
   public short getOutLclAdditionalExamArrangementExamYear() {
      return exportView.OutLclAdditionalExamArrangementExamYear;
   }
 
   public short getOutLclAdditionalExamArrangementMkExamPeriod() {
      return exportView.OutLclAdditionalExamArrangementMkExamPeriod;
   }
 
   public short getOutLclAdditionalExamArrangementSequenceNr() {
      return exportView.OutLclAdditionalExamArrangementSequenceNr;
   }
 
   public String getOutLclAdditionalExamArrangementMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutLclAdditionalExamArrangementMkStudyUnitCode, 7);
   }
 
   public int getOutLclLecturerStudentCardRequestMkStudentNr() {
      return exportView.OutLclLecturerStudentCardRequestMkStudentNr;
   }
 
   public String getOutLclLecturerStudentCardRequestChangeCode() {
      return FixedStringAttr.valueOf(exportView.OutLclLecturerStudentCardRequestChangeCode, 2);
   }
 
   public String getOutLclPrimaryIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutLclPrimaryIefSuppliedFlag, 1);
   }
 
   public short getOutLclWsExamPeriodCode() {
      return exportView.OutLclWsExamPeriodCode;
   }
 
   public String getOutLclWsMethodResultReturnCode() {
      return FixedStringAttr.valueOf(exportView.OutLclWsMethodResultReturnCode, 2);
   }
 
   public short getOutWsMagisterialDistrictCode() {
      return exportView.OutWsMagisterialDistrictCode;
   }
 
   public String getOutWsMagisterialDistrictEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsMagisterialDistrictEngDescription, 28);
   }
 
   public String getOutWsCountryCode() {
      return FixedStringAttr.valueOf(exportView.OutWsCountryCode, 4);
   }
 
   public String getOutWsCountryEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsCountryEngDescription, 28);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
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
 
   public String getOutPhysicalCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalCsfStringsString1, 1);
   }
 
   public int getOutPhysicalWsAddressV2ReferenceNo() {
      return exportView.OutPhysicalWsAddressV2ReferenceNo;
   }
 
   public short getOutPhysicalWsAddressV2Type() {
      return exportView.OutPhysicalWsAddressV2Type;
   }
 
   public short getOutPhysicalWsAddressV2Category() {
      return exportView.OutPhysicalWsAddressV2Category;
   }
 
   public String getOutPhysicalWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2AddressLine1, 28);
   }
 
   public String getOutPhysicalWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2AddressLine2, 28);
   }
 
   public String getOutPhysicalWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2AddressLine3, 28);
   }
 
   public String getOutPhysicalWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2AddressLine4, 28);
   }
 
   public String getOutPhysicalWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2AddressLine5, 28);
   }
 
   public String getOutPhysicalWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2AddressLine6, 28);
   }
 
   public short getOutPhysicalWsAddressV2PostalCode() {
      return exportView.OutPhysicalWsAddressV2PostalCode;
   }
 
   public int getOutPhysicalWsAddressV2UserNumber() {
      return exportView.OutPhysicalWsAddressV2UserNumber;
   }
 
   public String getOutPhysicalWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalWsAddressV2CourierContactNo, 40);
   }
 
   public String getOutPhysicalPOBoxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalPOBoxCsfStringsString1, 1);
   }
 
   public int getOutCourierWsAddressV2ReferenceNo() {
      return exportView.OutCourierWsAddressV2ReferenceNo;
   }
 
   public short getOutCourierWsAddressV2Type() {
      return exportView.OutCourierWsAddressV2Type;
   }
 
   public short getOutCourierWsAddressV2Category() {
      return exportView.OutCourierWsAddressV2Category;
   }
 
   public String getOutCourierWsAddressV2AddressLine1() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2AddressLine1, 28);
   }
 
   public String getOutCourierWsAddressV2AddressLine2() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2AddressLine2, 28);
   }
 
   public String getOutCourierWsAddressV2AddressLine3() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2AddressLine3, 28);
   }
 
   public String getOutCourierWsAddressV2AddressLine4() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2AddressLine4, 28);
   }
 
   public String getOutCourierWsAddressV2AddressLine5() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2AddressLine5, 28);
   }
 
   public String getOutCourierWsAddressV2AddressLine6() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2AddressLine6, 28);
   }
 
   public short getOutCourierWsAddressV2PostalCode() {
      return exportView.OutCourierWsAddressV2PostalCode;
   }
 
   public int getOutCourierWsAddressV2UserNumber() {
      return exportView.OutCourierWsAddressV2UserNumber;
   }
 
   public String getOutCourierWsAddressV2CourierContactNo() {
      return FixedStringAttr.valueOf(exportView.OutCourierWsAddressV2CourierContactNo, 40);
   }
 
};
