package Menu95h.Abean;
 
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
public class Menu95S  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Menu95S");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Menu95S() {
      super();
      nativeDateFormatter.setLenient(false);
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
 
 
   private Menu95SOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Menu95SOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doMenu95SOperation();
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
      private Menu95S rP;
      operListener(Menu95S r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Menu95S", "Listener heard that Menu95SOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Menu95S ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Menu95S");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Menu95S ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Menu95S";
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
      importView.InMenuGroup_MA = IntAttr.valueOf(InMenuGroupMax);
      exportView.OutMenuGroup_MA = IntAttr.getDefaultValue();
   }

   Menu95h.MENU95S_IA importView = Menu95h.MENU95S_IA.getInstance();
   Menu95h.MENU95S_OA exportView = Menu95h.MENU95S_OA.getInstance();
   public String getInWsWorkstationCode() {
      return StringAttr.valueOf(importView.InWsWorkstationCode);
   }
   public void setInWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsWorkstationCode", null, null));
      }
      importView.InWsWorkstationCode = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInWsWorkstationMacCode() {
      return FixedStringAttr.valueOf(importView.InWsWorkstationMacCode, 16);
   }
   public void setInWsWorkstationMacCode(String s)
      throws PropertyVetoException {
      if (s.length() > 16) {
         throw new PropertyVetoException("InWsWorkstationMacCode must be <= 16 characters.",
               new PropertyChangeEvent (this, "InWsWorkstationMacCode", null, null));
      }
      importView.InWsWorkstationMacCode = FixedStringAttr.valueOf(s, (short)16);
   }
 
   public String getInWsWorkstationComputerHostname() {
      return StringAttr.valueOf(importView.InWsWorkstationComputerHostname);
   }
   public void setInWsWorkstationComputerHostname(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 15) {
         throw new PropertyVetoException("InWsWorkstationComputerHostname must be <= 15 characters.",
               new PropertyChangeEvent (this, "InWsWorkstationComputerHostname", null, null));
      }
      importView.InWsWorkstationComputerHostname = StringAttr.valueOf(s, (short)15);
   }
 
   public int getInOrderByFieldIefSuppliedCount() {
      return importView.InOrderByFieldIefSuppliedCount;
   }
   public void setInOrderByFieldIefSuppliedCount(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000000000.0) {
         throw new PropertyVetoException("InOrderByFieldIefSuppliedCount has more than 9 digits.",
               new PropertyChangeEvent (this, "InOrderByFieldIefSuppliedCount", null, null));
      }
      importView.InOrderByFieldIefSuppliedCount = IntAttr.valueOf(s);
   }
   public void setAsStringInOrderByFieldIefSuppliedCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOrderByFieldIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOrderByFieldIefSuppliedCount", null, null));
      }
      try {
          setInOrderByFieldIefSuppliedCount(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOrderByFieldIefSuppliedCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOrderByFieldIefSuppliedCount", null, null));
      }
   }
 
   public final int InMenuGroupMax = 200;
   public short getInMenuGroupCount() {
      return (short)(importView.InMenuGroup_MA);
   };
 
   public void setInMenuGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InMenuGroupMax) {
         throw new PropertyVetoException("InMenuGroupCount value is not a valid value. (0 to 200)",
               new PropertyChangeEvent (this, "InMenuGroupCount", null, null));
      } else {
         importView.InMenuGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGIefSuppliedSelectChar(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGIefSuppliedSelectChar[index], 1);
   }
   public void setInGIefSuppliedSelectChar(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGIefSuppliedSelectChar must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGIefSuppliedSelectChar", null, null));
      }
      importView.InGIefSuppliedSelectChar[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInGWsFunctionNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return importView.InGWsFunctionNumber[index];
   }
   public void setInGWsFunctionNumber(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InGWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InGWsFunctionNumber", null, null));
      }
      importView.InGWsFunctionNumber[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGWsFunctionNumber(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsFunctionNumber", null, null));
      }
      try {
          setInGWsFunctionNumber(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsFunctionNumber", null, null));
      }
   }
 
   public String getInGWsFunctionTrancode(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsFunctionTrancode[index], 8);
   }
   public void setInGWsFunctionTrancode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InGWsFunctionTrancode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InGWsFunctionTrancode", null, null));
      }
      importView.InGWsFunctionTrancode[index] = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInGWsFunctionDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsFunctionDescription[index], 28);
   }
   public void setInGWsFunctionDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InGWsFunctionDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InGWsFunctionDescription", null, null));
      }
      importView.InGWsFunctionDescription[index] = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInWsPrinterCode() {
      return FixedStringAttr.valueOf(importView.InWsPrinterCode, 10);
   }
   public void setInWsPrinterCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InWsPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InWsPrinterCode", null, null));
      }
      importView.InWsPrinterCode = FixedStringAttr.valueOf(s, (short)10);
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
 
   public short getInWsUserNumberOfLogonAttempts() {
      return importView.InWsUserNumberOfLogonAttempts;
   }
   public void setInWsUserNumberOfLogonAttempts(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InWsUserNumberOfLogonAttempts has more than 1 digits.",
               new PropertyChangeEvent (this, "InWsUserNumberOfLogonAttempts", null, null));
      }
      importView.InWsUserNumberOfLogonAttempts = ShortAttr.valueOf(s);
   }
   public void setAsStringInWsUserNumberOfLogonAttempts(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumberOfLogonAttempts", null, null));
      }
      try {
          setInWsUserNumberOfLogonAttempts(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InWsUserNumberOfLogonAttempts", null, null));
      }
   }
 
   public String getInWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(importView.InWsUserLoggedOnFlag, 1);
   }
   public void setInWsUserLoggedOnFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsUserLoggedOnFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsUserLoggedOnFlag", null, null));
      }
      importView.InWsUserLoggedOnFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(importView.InWsUserInUsedFlag, 1);
   }
   public void setInWsUserInUsedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InWsUserInUsedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InWsUserInUsedFlag", null, null));
      }
      importView.InWsUserInUsedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInWsUserLastLogonDate() {
      return DateAttr.toCalendar(importView.InWsUserLastLogonDate);
   }
   public int getAsIntInWsUserLastLogonDate() {
      return DateAttr.toInt(importView.InWsUserLastLogonDate);
   }
   public void setInWsUserLastLogonDate(Calendar s)
      throws PropertyVetoException {
      importView.InWsUserLastLogonDate = DateAttr.valueOf(s);
   }
   public void setAsStringInWsUserLastLogonDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInWsUserLastLogonDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInWsUserLastLogonDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InWsUserLastLogonDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InWsUserLastLogonDate", null, null));
         }
      }
   }
   public void setAsIntInWsUserLastLogonDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInWsUserLastLogonDate(temp);
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
 
   public String getInWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InWsUserPassword, 12);
   }
   public void setInWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InWsUserPassword", null, null));
      }
      importView.InWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public String getInWsUserPassword32cs() {
      return StringAttr.valueOf(importView.InWsUserPassword32cs);
   }
   public void setInWsUserPassword32cs(String s)
      throws PropertyVetoException {
      if (s.length() > 32) {
         throw new PropertyVetoException("InWsUserPassword32cs must be <= 32 characters.",
               new PropertyChangeEvent (this, "InWsUserPassword32cs", null, null));
      }
      importView.InWsUserPassword32cs = StringAttr.valueOf(s, (short)32);
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
 
   public String getInPhysicalMacWsWorkstationMacCode() {
      return FixedStringAttr.valueOf(importView.InPhysicalMacWsWorkstationMacCode, 16);
   }
   public void setInPhysicalMacWsWorkstationMacCode(String s)
      throws PropertyVetoException {
      if (s.length() > 16) {
         throw new PropertyVetoException("InPhysicalMacWsWorkstationMacCode must be <= 16 characters.",
               new PropertyChangeEvent (this, "InPhysicalMacWsWorkstationMacCode", null, null));
      }
      importView.InPhysicalMacWsWorkstationMacCode = FixedStringAttr.valueOf(s, (short)16);
   }
 
   public String getInWorkstationLookupStatusCsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InWorkstationLookupStatusCsfStringsString100, 100);
   }
   public void setInWorkstationLookupStatusCsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InWorkstationLookupStatusCsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InWorkstationLookupStatusCsfStringsString100", null, null));
      }
      importView.InWorkstationLookupStatusCsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public String getInFunnelgroupLookupStatusCsfStringsString100() {
      return FixedStringAttr.valueOf(importView.InFunnelgroupLookupStatusCsfStringsString100, 100);
   }
   public void setInFunnelgroupLookupStatusCsfStringsString100(String s)
      throws PropertyVetoException {
      if (s.length() > 100) {
         throw new PropertyVetoException("InFunnelgroupLookupStatusCsfStringsString100 must be <= 100 characters.",
               new PropertyChangeEvent (this, "InFunnelgroupLookupStatusCsfStringsString100", null, null));
      }
      importView.InFunnelgroupLookupStatusCsfStringsString100 = FixedStringAttr.valueOf(s, (short)100);
   }
 
   public short getInAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup() {
      return importView.InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup;
   }
   public void setInAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup has more than 3 digits.",
               new PropertyChangeEvent (this, "InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup", null, null));
      }
      importView.InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup = ShortAttr.valueOf(s);
   }
   public void setAsStringInAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup", null, null));
      }
      try {
          setInAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup", null, null));
      }
   }
 
   public String getOutWsActionCode() {
      return FixedStringAttr.valueOf(exportView.OutWsActionCode, 2);
   }
 
   public String getOutWsWorkstationCode() {
      return StringAttr.valueOf(exportView.OutWsWorkstationCode);
   }
 
   public String getOutWsWorkstationMacCode() {
      return FixedStringAttr.valueOf(exportView.OutWsWorkstationMacCode, 16);
   }
 
   public String getOutWsWorkstationComputerHostname() {
      return StringAttr.valueOf(exportView.OutWsWorkstationComputerHostname);
   }
 
   public int getOutOrderByFieldIefSuppliedCount() {
      return exportView.OutOrderByFieldIefSuppliedCount;
   }
 
   public String getOutCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsAction, 2);
   }
 
   public String getOutCsfClientServerCommunicationsServerDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerDevelopmentPhase, 1);
   }
 
   public final int OutMenuGroupMax = 200;
   public short getOutMenuGroupCount() {
      return (short)(exportView.OutMenuGroup_MA);
   };
 
   public String getOutGIefSuppliedSelectChar(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGIefSuppliedSelectChar[index], 1);
   }
 
   public int getOutGWsFunctionNumber(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return exportView.OutGWsFunctionNumber[index];
   }
 
   public String getOutGWsFunctionTrancode(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsFunctionTrancode[index], 8);
   }
 
   public String getOutGWsFunctionDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsFunctionDescription[index], 28);
   }
 
   public int getOutWsUserNumber() {
      return exportView.OutWsUserNumber;
   }
 
   public short getOutWsUserNumberOfLogonAttempts() {
      return exportView.OutWsUserNumberOfLogonAttempts;
   }
 
   public String getOutWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsUserLoggedOnFlag, 1);
   }
 
   public String getOutWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(exportView.OutWsUserInUsedFlag, 1);
   }
 
   public Calendar getOutWsUserLastLogonDate() {
      return DateAttr.toCalendar(exportView.OutWsUserLastLogonDate);
   }
   public int getAsIntOutWsUserLastLogonDate() {
      return DateAttr.toInt(exportView.OutWsUserLastLogonDate);
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
 
   public String getOutWsUserPassword() {
      return FixedStringAttr.valueOf(exportView.OutWsUserPassword, 12);
   }
 
   public String getOutWsUserPassword32cs() {
      return StringAttr.valueOf(exportView.OutWsUserPassword32cs);
   }
 
   public int getOutWsFunctionNumber() {
      return exportView.OutWsFunctionNumber;
   }
 
   public String getOutWsFunctionTrancode() {
      return FixedStringAttr.valueOf(exportView.OutWsFunctionTrancode, 8);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public String getOutWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutWsPrinterCode, 10);
   }
 
   public String getOutPhysicalMacWsWorkstationMacCode() {
      return FixedStringAttr.valueOf(exportView.OutPhysicalMacWsWorkstationMacCode, 16);
   }
 
   public String getOutWorkstationLookupStatusCsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutWorkstationLookupStatusCsfStringsString100, 100);
   }
 
   public String getOutFunnelgroupLookupStatusCsfStringsString100() {
      return FixedStringAttr.valueOf(exportView.OutFunnelgroupLookupStatusCsfStringsString100, 100);
   }
 
   public short getOutAssignedWsWkstationFunnelgroupConfigFunnelGroup() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelGroup;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigServerName() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigServerName);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigInUseFlag() {
      return FixedStringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigInUseFlag, 1);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigActiveFlag() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigActiveFlag);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode01() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode01);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport01() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport01;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription01() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription01);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode02() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode02);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport02() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport02;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription02() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription02);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode03() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode03);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport03() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport03;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription03() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription03);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode04() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode04);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport04() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport04;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription04() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription04);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode05() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode05);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport05() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport05;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription05() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription05);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode06() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode06);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport06() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport06;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription06() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription06);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode07() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode07);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport07() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport07;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription07() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription07);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode08() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode08);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport08() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport08;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription08() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription08);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode09() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode09);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport09() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport09;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription09() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription09);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode10() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode10);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport10() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport10;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription10() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription10);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode11() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode11);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport11() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport11;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription11() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription11);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode12() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode12);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport12() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport12;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription12() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription12);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode13() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode13);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport13() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport13;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription13() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription13);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode14() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode14);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport14() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport14;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription14() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription14);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode15() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode15);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport15() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport15;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription15() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription15);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode16() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode16);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport16() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport16;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription16() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription16);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode17() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode17);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport17() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport17;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription17() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription17);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode18() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode18);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport18() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport18;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription18() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription18);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode19() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode19);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport19() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport19;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription19() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription19);
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigTrancode20() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigTrancode20);
   }
 
   public int getOutAssignedWsWkstationFunnelgroupConfigFunnelport20() {
      return exportView.OutAssignedWsWkstationFunnelgroupConfigFunnelport20;
   }
 
   public String getOutAssignedWsWkstationFunnelgroupConfigDescription20() {
      return StringAttr.valueOf(exportView.OutAssignedWsWkstationFunnelgroupConfigDescription20);
   }
 
};
