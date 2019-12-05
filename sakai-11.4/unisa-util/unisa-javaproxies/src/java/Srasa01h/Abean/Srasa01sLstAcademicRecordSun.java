package Srasa01h.Abean;
 
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
public class Srasa01sLstAcademicRecordSun  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srasa01sLstAcademicRecordSun");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srasa01sLstAcademicRecordSun() {
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
 
 
   private Srasa01sLstAcademicRecordSunOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srasa01sLstAcademicRecordSunOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrasa01sLstAcademicRecordSunOperation();
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
      private Srasa01sLstAcademicRecordSun rP;
      operListener(Srasa01sLstAcademicRecordSun r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srasa01sLstAcademicRecordSun", "Listener heard that Srasa01sLstAcademicRecordSunOperation completed with " + 
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srasa01sLstAcademicRecordSun ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srasa01sLstAcademicRecordSun");
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
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srasa01sLstAcademicRecordSun ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srasa01sLstAcademicRecordSun";
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
      importView.InOrderByCsfRadioButtonsButton1 = ShortAttr.valueOf((short)1);
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
   }

   Srasa01h.SRASA01S_IA importView = Srasa01h.SRASA01S_IA.getInstance();
   Srasa01h.SRASA01S_OA exportView = Srasa01h.SRASA01S_OA.getInstance();
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
 
   public String getInLowAcademicRecordStudyUnitMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InLowAcademicRecordStudyUnitMkStudyUnitCode, 7);
   }
   public void setInLowAcademicRecordStudyUnitMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InLowAcademicRecordStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InLowAcademicRecordStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InLowAcademicRecordStudyUnitMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInLowAcademicRecordStudyUnitMkAcademicYear() {
      return importView.InLowAcademicRecordStudyUnitMkAcademicYear;
   }
   public void setInLowAcademicRecordStudyUnitMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InLowAcademicRecordStudyUnitMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InLowAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      importView.InLowAcademicRecordStudyUnitMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInLowAcademicRecordStudyUnitMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InLowAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      try {
          setInLowAcademicRecordStudyUnitMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InLowAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InLowAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
   }
 
   public String getInHighAcademicRecordStudyUnitMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InHighAcademicRecordStudyUnitMkStudyUnitCode, 7);
   }
   public void setInHighAcademicRecordStudyUnitMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InHighAcademicRecordStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InHighAcademicRecordStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InHighAcademicRecordStudyUnitMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInHighAcademicRecordStudyUnitMkAcademicYear() {
      return importView.InHighAcademicRecordStudyUnitMkAcademicYear;
   }
   public void setInHighAcademicRecordStudyUnitMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InHighAcademicRecordStudyUnitMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InHighAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      importView.InHighAcademicRecordStudyUnitMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInHighAcademicRecordStudyUnitMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InHighAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      try {
          setInHighAcademicRecordStudyUnitMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InHighAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InHighAcademicRecordStudyUnitMkAcademicYear", null, null));
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
 
   public short getInOrderByCsfRadioButtonsButton1() {
      return importView.InOrderByCsfRadioButtonsButton1;
   }
   public void setInOrderByCsfRadioButtonsButton1(short s)
      throws PropertyVetoException {
      Srasa01sLstAcademicRecordSunCsfRadioButtonsButton1PropertyEditor pe = new Srasa01sLstAcademicRecordSunCsfRadioButtonsButton1PropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InOrderByCsfRadioButtonsButton1 value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InOrderByCsfRadioButtonsButton1", null, null));
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InOrderByCsfRadioButtonsButton1 has more than 2 digits.",
               new PropertyChangeEvent (this, "InOrderByCsfRadioButtonsButton1", null, null));
      }
      importView.InOrderByCsfRadioButtonsButton1 = ShortAttr.valueOf(s);
   }
   public void setAsStringInOrderByCsfRadioButtonsButton1(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InOrderByCsfRadioButtonsButton1 is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOrderByCsfRadioButtonsButton1", null, null));
      }
      try {
          setInOrderByCsfRadioButtonsButton1(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InOrderByCsfRadioButtonsButton1 is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InOrderByCsfRadioButtonsButton1", null, null));
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
 
   public String getInStartingFromAcademicRecordStudyUnitMkStudyUnitCode() {
      return FixedStringAttr.valueOf(importView.InStartingFromAcademicRecordStudyUnitMkStudyUnitCode, 7);
   }
   public void setInStartingFromAcademicRecordStudyUnitMkStudyUnitCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InStartingFromAcademicRecordStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InStartingFromAcademicRecordStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InStartingFromAcademicRecordStudyUnitMkStudyUnitCode = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInStartingFromAcademicRecordStudyUnitMkAcademicYear() {
      return importView.InStartingFromAcademicRecordStudyUnitMkAcademicYear;
   }
   public void setInStartingFromAcademicRecordStudyUnitMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InStartingFromAcademicRecordStudyUnitMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InStartingFromAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      importView.InStartingFromAcademicRecordStudyUnitMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInStartingFromAcademicRecordStudyUnitMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InStartingFromAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingFromAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      try {
          setInStartingFromAcademicRecordStudyUnitMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InStartingFromAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InStartingFromAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
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
 
   public String getInFunctionTypePersonnelFlagCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InFunctionTypePersonnelFlagCsfStringsString1, 1);
   }
   public void setInFunctionTypePersonnelFlagCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InFunctionTypePersonnelFlagCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InFunctionTypePersonnelFlagCsfStringsString1", null, null));
      }
      importView.InFunctionTypePersonnelFlagCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public final int InGroupMax = 100;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 100)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInputGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InputGCsfLineActionBarAction[index], 1);
   }
   public void setInputGCsfLineActionBarAction(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InputGCsfLineActionBarAction must be <= 1 characters.",
               new PropertyChangeEvent (this, "InputGCsfLineActionBarAction", null, null));
      }
      importView.InputGCsfLineActionBarAction[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInGAcademicRecordStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAcademicRecordStudyUnitMkStudyUnitCode[index], 7);
   }
   public void setInGAcademicRecordStudyUnitMkStudyUnitCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 7) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitMkStudyUnitCode must be <= 7 characters.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkStudyUnitCode", null, null));
      }
      importView.InGAcademicRecordStudyUnitMkStudyUnitCode[index] = FixedStringAttr.valueOf(s, (short)7);
   }
 
   public short getInGAcademicRecordStudyUnitMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGAcademicRecordStudyUnitMkAcademicYear[index];
   }
   public void setInGAcademicRecordStudyUnitMkAcademicYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      importView.InGAcademicRecordStudyUnitMkAcademicYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitMkAcademicYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
      try {
          setInGAcademicRecordStudyUnitMkAcademicYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkAcademicYear", null, null));
      }
   }
 
   public short getInGAcademicRecordStudyUnitMkAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGAcademicRecordStudyUnitMkAcademicPeriod[index];
   }
   public void setInGAcademicRecordStudyUnitMkAcademicPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitMkAcademicPeriod has more than 2 digits.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkAcademicPeriod", null, null));
      }
      importView.InGAcademicRecordStudyUnitMkAcademicPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitMkAcademicPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkAcademicPeriod", null, null));
      }
      try {
          setInGAcademicRecordStudyUnitMkAcademicPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitMkAcademicPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitMkAcademicPeriod", null, null));
      }
   }
 
   public short getInGAcademicRecordStudyUnitFinalMark(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGAcademicRecordStudyUnitFinalMark[index];
   }
   public void setInGAcademicRecordStudyUnitFinalMark(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitFinalMark has more than 3 digits.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitFinalMark", null, null));
      }
      importView.InGAcademicRecordStudyUnitFinalMark[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitFinalMark(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitFinalMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitFinalMark", null, null));
      }
      try {
          setInGAcademicRecordStudyUnitFinalMark(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitFinalMark is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitFinalMark", null, null));
      }
   }
 
   public short getInGAcademicRecordStudyUnitCreditStatusCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGAcademicRecordStudyUnitCreditStatusCode[index];
   }
   public void setInGAcademicRecordStudyUnitCreditStatusCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCreditStatusCodePropertyEditor pe = new Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCreditStatusCodePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitCreditStatusCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitCreditStatusCode", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitCreditStatusCode has more than 1 digits.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitCreditStatusCode", null, null));
      }
      importView.InGAcademicRecordStudyUnitCreditStatusCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitCreditStatusCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitCreditStatusCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitCreditStatusCode", null, null));
      }
      try {
          setInGAcademicRecordStudyUnitCreditStatusCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitCreditStatusCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitCreditStatusCode", null, null));
      }
   }
 
   public short getInGAcademicRecordStudyUnitExemptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGAcademicRecordStudyUnitExemptionCode[index];
   }
   public void setInGAcademicRecordStudyUnitExemptionCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitExemptionCodePropertyEditor pe = new Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitExemptionCodePropertyEditor();
      if (pe.checkTag(String.valueOf(s)) == false) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitExemptionCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitExemptionCode", null, null));
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitExemptionCode has more than 1 digits.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitExemptionCode", null, null));
      }
      importView.InGAcademicRecordStudyUnitExemptionCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitExemptionCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitExemptionCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitExemptionCode", null, null));
      }
      try {
          setInGAcademicRecordStudyUnitExemptionCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitExemptionCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitExemptionCode", null, null));
      }
   }
 
   public String getInGAcademicRecordStudyUnitCancellationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGAcademicRecordStudyUnitCancellationCode[index], 2);
   }
   public void setInGAcademicRecordStudyUnitCancellationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCancellationCodePropertyEditor pe = new Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCancellationCodePropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitCancellationCode value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitCancellationCode", null, null));
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitCancellationCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitCancellationCode", null, null));
      }
      importView.InGAcademicRecordStudyUnitCancellationCode[index] = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public short getInGAcademicRecordStudyUnitSupplementaryExamReasonCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGAcademicRecordStudyUnitSupplementaryExamReasonCode[index];
   }
   public void setInGAcademicRecordStudyUnitSupplementaryExamReasonCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGAcademicRecordStudyUnitSupplementaryExamReasonCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitSupplementaryExamReasonCode", null, null));
      }
      importView.InGAcademicRecordStudyUnitSupplementaryExamReasonCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitSupplementaryExamReasonCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitSupplementaryExamReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitSupplementaryExamReasonCode", null, null));
      }
      try {
          setInGAcademicRecordStudyUnitSupplementaryExamReasonCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGAcademicRecordStudyUnitSupplementaryExamReasonCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitSupplementaryExamReasonCode", null, null));
      }
   }
 
   public Calendar getInGAcademicRecordStudyUnitResultDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toCalendar(importView.InGAcademicRecordStudyUnitResultDate[index]);
   }
   public int getAsIntInGAcademicRecordStudyUnitResultDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toInt(importView.InGAcademicRecordStudyUnitResultDate[index]);
   }
   public void setInGAcademicRecordStudyUnitResultDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      importView.InGAcademicRecordStudyUnitResultDate[index] = DateAttr.valueOf(s);
   }
   public void setAsStringInGAcademicRecordStudyUnitResultDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGAcademicRecordStudyUnitResultDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInGAcademicRecordStudyUnitResultDate(index, tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGAcademicRecordStudyUnitResultDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InGAcademicRecordStudyUnitResultDate", null, null));
         }
      }
   }
   public void setAsIntInGAcademicRecordStudyUnitResultDate(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInGAcademicRecordStudyUnitResultDate(index, temp);
   }
 
   public String getInGWsStudyUnitEngShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGWsStudyUnitEngShortDescription[index], 28);
   }
   public void setInGWsStudyUnitEngShortDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InGWsStudyUnitEngShortDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InGWsStudyUnitEngShortDescription", null, null));
      }
      importView.InGWsStudyUnitEngShortDescription[index] = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public short getInGStudyUnitResultTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return importView.InGStudyUnitResultTypeCode[index];
   }
   public void setInGStudyUnitResultTypeCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100.0) {
         throw new PropertyVetoException("InGStudyUnitResultTypeCode has more than 2 digits.",
               new PropertyChangeEvent (this, "InGStudyUnitResultTypeCode", null, null));
      }
      importView.InGStudyUnitResultTypeCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGStudyUnitResultTypeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudyUnitResultTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudyUnitResultTypeCode", null, null));
      }
      try {
          setInGStudyUnitResultTypeCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudyUnitResultTypeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudyUnitResultTypeCode", null, null));
      }
   }
 
   public String getInGStudyUnitResultTypeEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitResultTypeEngDescription[index], 50);
   }
   public void setInGStudyUnitResultTypeEngDescription(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 50) {
         throw new PropertyVetoException("InGStudyUnitResultTypeEngDescription must be <= 50 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitResultTypeEngDescription", null, null));
      }
      importView.InGStudyUnitResultTypeEngDescription[index] = FixedStringAttr.valueOf(s, (short)50);
   }
 
   public String getInGStudyUnitResultTypeEngAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGStudyUnitResultTypeEngAbbreviation[index], 10);
   }
   public void setInGStudyUnitResultTypeEngAbbreviation(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGStudyUnitResultTypeEngAbbreviation must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGStudyUnitResultTypeEngAbbreviation", null, null));
      }
      importView.InGStudyUnitResultTypeEngAbbreviation[index] = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInCreditsOnlyIefSuppliedFlag() {
      return FixedStringAttr.valueOf(importView.InCreditsOnlyIefSuppliedFlag, 1);
   }
   public void setInCreditsOnlyIefSuppliedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      Srasa01sLstAcademicRecordSunIefSuppliedFlagPropertyEditor pe = new Srasa01sLstAcademicRecordSunIefSuppliedFlagPropertyEditor();
      if (pe.checkTag(s) == false) {
         throw new PropertyVetoException("InCreditsOnlyIefSuppliedFlag value  \"" + s + "\"  is not a permitted value.",
               new PropertyChangeEvent (this, "InCreditsOnlyIefSuppliedFlag", null, null));
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCreditsOnlyIefSuppliedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCreditsOnlyIefSuppliedFlag", null, null));
      }
      importView.InCreditsOnlyIefSuppliedFlag = FixedStringAttr.valueOf(s, (short)1);
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
 
   public String getInFaxNrCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNrCsfStringsString132, 132);
   }
   public void setInFaxNrCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNrCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNrCsfStringsString132", null, null));
      }
      importView.InFaxNrCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailFromCsfStringsString132, 132);
   }
   public void setInEmailFromCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailFromCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailFromCsfStringsString132", null, null));
      }
      importView.InEmailFromCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailToCsfStringsString132, 132);
   }
   public void setInEmailToCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailToCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailToCsfStringsString132", null, null));
      }
      importView.InEmailToCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
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
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
 
   public int getOutStudentAcademicRecordMkStudentNr() {
      return exportView.OutStudentAcademicRecordMkStudentNr;
   }
 
   public String getOutStudentAcademicRecordMkQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutStudentAcademicRecordMkQualificationCode, 5);
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
 
   public short getOutWsStudentMkLastAcademicYear() {
      return exportView.OutWsStudentMkLastAcademicYear;
   }
 
   public String getOutStartingFromAcademicRecordStudyUnitMkStudyUnitCode() {
      return FixedStringAttr.valueOf(exportView.OutStartingFromAcademicRecordStudyUnitMkStudyUnitCode, 7);
   }
 
   public short getOutStartingFromAcademicRecordStudyUnitMkAcademicYear() {
      return exportView.OutStartingFromAcademicRecordStudyUnitMkAcademicYear;
   }
 
   public String getOutWsQualificationCode() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationCode, 5);
   }
 
   public String getOutWsQualificationEngDescription() {
      return FixedStringAttr.valueOf(exportView.OutWsQualificationEngDescription, 28);
   }
 
   public final int OutGroupMax = 100;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGCsfLineActionBarAction(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarAction[index], 1);
   }
 
   public short getOutGCsfLineActionBarLineReturnCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGCsfLineActionBarLineReturnCode[index];
   }
 
   public String getOutGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public String getOutGCsfLineActionBarTranslatedAction(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarTranslatedAction[index], 1);
   }
 
   public String getOutGAcademicRecordStudyUnitMkStudyUnitCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAcademicRecordStudyUnitMkStudyUnitCode[index], 7);
   }
 
   public short getOutGAcademicRecordStudyUnitMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGAcademicRecordStudyUnitMkAcademicYear[index];
   }
 
   public short getOutGAcademicRecordStudyUnitMkAcademicPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGAcademicRecordStudyUnitMkAcademicPeriod[index];
   }
 
   public short getOutGAcademicRecordStudyUnitFinalMark(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGAcademicRecordStudyUnitFinalMark[index];
   }
 
   public short getOutGAcademicRecordStudyUnitCreditStatusCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGAcademicRecordStudyUnitCreditStatusCode[index];
   }
 
   public short getOutGAcademicRecordStudyUnitExemptionCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGAcademicRecordStudyUnitExemptionCode[index];
   }
 
   public String getOutGAcademicRecordStudyUnitCancellationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGAcademicRecordStudyUnitCancellationCode[index], 2);
   }
 
   public short getOutGAcademicRecordStudyUnitSupplementaryExamReasonCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGAcademicRecordStudyUnitSupplementaryExamReasonCode[index];
   }
 
   public Calendar getOutGAcademicRecordStudyUnitResultDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toCalendar(exportView.OutGAcademicRecordStudyUnitResultDate[index]);
   }
   public int getAsIntOutGAcademicRecordStudyUnitResultDate(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return DateAttr.toInt(exportView.OutGAcademicRecordStudyUnitResultDate[index]);
   }
 
   public String getOutGWsStudyUnitEngShortDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGWsStudyUnitEngShortDescription[index], 28);
   }
 
   public short getOutGStudyUnitResultTypeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return exportView.OutGStudyUnitResultTypeCode[index];
   }
 
   public String getOutGStudyUnitResultTypeEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitResultTypeEngDescription[index], 50);
   }
 
   public String getOutGStudyUnitResultTypeEngAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (99 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 99, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGStudyUnitResultTypeEngAbbreviation[index], 10);
   }
 
   public String getOutFunctionTypePersonnelFlagCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutFunctionTypePersonnelFlagCsfStringsString1, 1);
   }
 
   public String getOutCreditsOnlyIefSuppliedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCreditsOnlyIefSuppliedFlag, 1);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutFaxNrCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNrCsfStringsString132, 132);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
};
